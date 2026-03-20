package tools.vitruv.framework.vsum.branch.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.eobject.EObjectExistenceEChange;
import tools.vitruv.change.atomic.feature.FeatureEChange;
import tools.vitruv.change.atomic.root.RootEChange;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.VitruviusChange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Accumulates atomic {@code EChange<EObject>} instances between commits, grouped by the URI
 * of the resource they affect.
 *
 * <p>Register an instance of this class as a {@link ChangePropagationListener} on the
 * {@link tools.vitruv.framework.vsum.branch.BranchAwareVirtualModel} to automatically collect
 * changes after each {@code propagateChange()} call. At commit time, call {@link #drainChanges()}
 * to retrieve and clear the buffer so the changes can be serialized into the semantic changelog.
 *
 * <p>Only {@link PropagatedChange#getOriginalChange()} is collected.  Consequential changes
 * (reactions / consistency preservation) are intentionally omitted: they can be re-derived
 * by replaying the original atomic changes through Vitruvius, so storing them would be redundant
 *
 * <p>Thread-safety: all public methods and the listener callbacks are {@code synchronized} on
 * {@code this}.  {@link #finishedChangePropagation} is called from the VSUM/model thread while
 * {@link #drainChanges}, {@link #hasChanges}, and {@link #size} may be called from a background
 * watcher thread (e.g. {@code VsumPostCommitWatcher}).
 */
public class SemanticChangeBuffer implements ChangePropagationListener {

    private static final Logger LOGGER = LogManager.getLogger(SemanticChangeBuffer.class);

    /**
     * Accumulated changes, keyed by the string form of the resource URI.
     * LinkedHashMap preserves insertion order so replay is deterministic.
     */
    private final Map<String, List<EChange<EObject>>> changesByResource = new LinkedHashMap<>();

    /**
     * Total number of atomic changes accumulated since the last drain.
     */
    private int totalChanges = 0;

    @Override
    public synchronized void startedChangePropagation(VitruviusChange<Uuid> changeToPropagate) {
        // nothing to do here - we collect after propagation is complete.
    }

    /**
     * Collects the original atomic changes from every {@link PropagatedChange} that user initiated.
     *
     * <p>Reaction changes are identified by checking whether their {@code EChange} instances
     * appear in the {@code consequentialChanges} of any other {@link PropagatedChange}.
     * Because the Vitruvius propagator reuses the same {@code EChange} object instances
     * for both the {@code consequentialChanges} composite and the subsequent
     * {@link PropagatedChange#getOriginalChange()}, an identity check is sufficient.
     */
    @Override
    public synchronized void finishedChangePropagation(Iterable<PropagatedChange> propagatedChanges) {
        List<PropagatedChange> pcList = new ArrayList<>();
        propagatedChanges.forEach(pcList::add);

        // Collect all EChange instances that are inside consequentialChanges of any PC.
        // These represent reaction/consistency-preservation outputs and must be excluded.
        Set<Object> reactionEChanges = Collections.newSetFromMap(new IdentityHashMap<>());
        for (PropagatedChange pc : pcList) {
            VitruviusChange<EObject> consequential = pc.getConsequentialChanges();
            if (consequential != null) {
                reactionEChanges.addAll(consequential.getEChanges());
            }
        }

        // Only collect from PropagatedChanges whose EChanges are NOT reaction outputs.
        for (PropagatedChange pc : pcList) {
            VitruviusChange<EObject> original = pc.getOriginalChange();
            List<EChange<EObject>> eChanges = original.getEChanges();
            if (!eChanges.isEmpty() && reactionEChanges.containsAll(eChanges)) {
                LOGGER.debug("Skipping reaction PropagatedChange ({} EChange(s)) - not user-initiated", eChanges.size());
                continue;
            }
            collectFromVitruviusChange(original);
        }
        LOGGER.debug("Buffer now holds {} atomic change(s) across {} resource(s)", totalChanges, changesByResource.size());
    }

    /**
     * Returns an unmodifiable snapshot of the current buffer contents and clears the buffer.
     * The returned map is keyed by resource URI string and preserves insertion order.
     * Each value list is an ordered list of atomic EChanges for that resource.
     *
     * <p>Call this method once per commit, immediately before writing the changelog.
     *
     * @return immutable map of resource URI to ordered atomic changes.
     */
    public synchronized Map<String, List<EChange<EObject>>> drainChanges() {
        Map<String, List<EChange<EObject>>> snapshot = new LinkedHashMap<>();
        changesByResource.forEach((uri, changes) -> snapshot.put(uri, Collections.unmodifiableList(new ArrayList<>(changes))));
        changesByResource.clear();
        int drained = totalChanges;
        totalChanges = 0;
        LOGGER.info("Drained {} atomic change(s) from buffer for {} resource(s)", drained, snapshot.size());
        return Collections.unmodifiableMap(snapshot);
    }

    /**
     * Returns true if the buffer contains at least one change.
     */
    public synchronized boolean hasChanges() {
        return totalChanges > 0;
    }

    /**
     * Returns the total number of atomic changes currently in the buffer.
     */
    public synchronized int size() {
        return totalChanges;
    }

    @SuppressWarnings("unchecked")
    private void collectFromVitruviusChange(VitruviusChange<EObject> vitruviusChange) {
        if (vitruviusChange == null) {
            return;
        }
        List<EChange<EObject>> eChanges = vitruviusChange.getEChanges();
        for (EChange<EObject> change : eChanges) {
            String resourceUri = resolveResourceUri(change);
            changesByResource.computeIfAbsent(resourceUri, k -> new ArrayList<>()).add(change);
            totalChanges++;
        }
    }

    /**
     * Determines the resource URI string for a given EChange.
     *
     * <ul>
     *   <li>For {@link RootEChange} (InsertRootEObject / RemoveRootEObject): uses
     *       {@link RootEChange#getUri()} directly, as root changes carry the target resource
     *       URI and do not have a traditional "affected element".</li>
     *   <li>For {@link FeatureEChange}: uses the resource of the affected element.</li>
     *   <li>For {@link EObjectExistenceEChange} (Create/Delete): uses the affected element's resource.
     *       For delete changes the element may have been detached; falls back to {@code "unknown-resource"} in that case.</li>
     *   <li>For anything else: falls back to {@code "unknown-resource"}.</li>
     * </ul>
     */
    private String resolveResourceUri(EChange<EObject> change) {
        if (change instanceof RootEChange<?> r) {
            String uri = r.getUri();
            return uri != null ? uri : "unknown-resource";
        }

        EObject element = null;
        if (change instanceof FeatureEChange<?, ?> f) {
            element = (EObject) f.getAffectedElement();
        } else if (change instanceof EObjectExistenceEChange<?> e) {
            element = (EObject) e.getAffectedElement();
        }

        if (element != null && element.eResource() != null) {
            URI uri = element.eResource().getURI();
            return uri != null ? uri.toString() : "unknown-resource";
        }

        LOGGER.debug("Cannot determine resource URI for change of type '{}', filing under 'unknown-resource'", change.getClass().getSimpleName());
        return "unknown-resource";
    }
}
