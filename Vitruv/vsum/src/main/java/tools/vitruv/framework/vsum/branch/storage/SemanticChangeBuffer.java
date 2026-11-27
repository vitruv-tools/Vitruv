package tools.vitruv.framework.vsum.branch.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.eobject.DeleteEObject;
import tools.vitruv.change.atomic.eobject.EObjectExistenceEChange;
import tools.vitruv.change.atomic.feature.FeatureEChange;
import tools.vitruv.change.atomic.root.RemoveRootEObject;
import tools.vitruv.change.atomic.root.RootEChange;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;

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
 * by replaying the original atomic changes through Vitruvius, so storing them would be redundant.
 *
 * <p>Thread-safety: all public methods and the listener callbacks are {@code synchronized} on
 * {@code this}.  {@link #finishedChangePropagation} is called from the VSUM/model thread while
 * {@link #drainChanges}, {@link #drainAnnotatedChanges}, {@link #hasChanges}, and {@link #size}
 * may be called from a background watcher thread (e.g. {@code VsumPostCommitWatcher}).
 */
public class SemanticChangeBuffer implements ChangePropagationListener {

  private static final Logger LOGGER = LogManager.getLogger(SemanticChangeBuffer.class);

  /**
   * An EChange paired with its detected origin. Since this buffer stores only original
   * changes per the thesis contract, the origin is always {@link ChangeOrigin#ORIGINAL}.
   * The wrapper exists to provide a compatible interface for consumers that also handle
   * consequential changes (e.g. Tural's conflict resolution layer).
   */
  public static class AnnotatedEChange {
    private final EChange<EObject> change;
    private final ChangeOrigin origin;

    public AnnotatedEChange(EChange<EObject> change, ChangeOrigin origin) {
      this.change = change;
      this.origin = origin;
    }

    public EChange<EObject> getChange() {
      return change;
    }

    public ChangeOrigin getOrigin() {
      return origin;
    }
  }

  /**
   * Atomic result of a {@link #drainChanges()} call. Bundles the accumulated changes together
   * with the pre-captured deletion UUID overrides so callers receive both atomically.
   */
  public static class DrainResult {
    private final Map<String, List<EChange<EObject>>> changesByResource;
    private final Map<EChange<EObject>, String> deletionUuidOverrides;

    DrainResult(Map<String, List<EChange<EObject>>> changesByResource,
        Map<EChange<EObject>, String> deletionUuidOverrides) {
      this.changesByResource = changesByResource;
      this.deletionUuidOverrides = deletionUuidOverrides;
    }

    /** Convenience factory: wraps an existing map with an empty override map. */
    public static DrainResult of(Map<String, List<EChange<EObject>>> changesByResource) {
      return new DrainResult(changesByResource, Map.of());
    }

    public Map<String, List<EChange<EObject>>> changesByResource() { return changesByResource; }
    public Map<EChange<EObject>, String> deletionUuidOverrides() { return deletionUuidOverrides; }
  }

  /**
   * Accumulated changes, keyed by the string form of the resource URI.
   * LinkedHashMap preserves insertion order so replay is deterministic.
   */
  private final Map<String, List<EChange<EObject>>> changesByResource = new LinkedHashMap<>();

  /**
   * Total number of atomic changes accumulated since the last drain.
   */
  private int totalChanges = 0;

  /**
   * Holds the UUID-form change between {@link #startedChangePropagation} and
   * {@link #finishedChangePropagation} so we can extract deletion UUIDs before apply.
   */
  private VitruviusChange<Uuid> pendingUuidChange = null;

  /**
   * Pre-captured UUID strings for deletion-type EChange instances, keyed by EChange instance
   * identity. Populated during {@link #finishedChangePropagation} by correlating with
   * {@link #pendingUuidChange}, and drained alongside {@link #changesByResource}.
   */
  private final Map<EChange<EObject>, String> deletionUuidOverrides = new IdentityHashMap<>();

  @Override
  public synchronized void startedChangePropagation(VitruviusChange<Uuid> changeToPropagate) {
    this.pendingUuidChange = changeToPropagate;
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
  public synchronized void finishedChangePropagation(
      Iterable<PropagatedChange> propagatedChanges) {

    // Grab and clear the pre-captured UUID-form change so we can correlate by index.
    List<EChange<Uuid>> uuidFormChanges = pendingUuidChange != null
        ? pendingUuidChange.getEChanges() : List.of();
    pendingUuidChange = null;

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

    // Correlate by index: the Vitruvius resolver maps each EChange<Uuid> to exactly one
    // EChange<EObject> in the same position. Reaction PCs are skipped and do NOT advance
    // the index, because they have no corresponding entry in the UUID-form input.
    int uuidIdx = 0;
    for (PropagatedChange pc : pcList) {
      VitruviusChange<EObject> original = pc.getOriginalChange();
      List<EChange<EObject>> eChanges = original.getEChanges();
      if (!eChanges.isEmpty() && reactionEChanges.containsAll(eChanges)) {
        continue;
      }
      for (EChange<EObject> eChange : eChanges) {
        if (uuidIdx < uuidFormChanges.size()) {
          String preUuid = extractDeletionUuid(uuidFormChanges.get(uuidIdx));
          if (preUuid != null) {
            deletionUuidOverrides.put(eChange, preUuid);
          }
        }
        uuidIdx++;
      }
      collectFromVitruviusChange(original);
    }
  }

  /**
   * Extracts the UUID string from a deletion-type EChange&lt;Uuid&gt;, or returns null if the
   * change is not a deletion. Covers DeleteEObject and RemoveRootEObject, which are the two
   * change types where the affected element is unregistered from the UUID resolver after apply.
   */
  @SuppressWarnings("unchecked")
  private String extractDeletionUuid(EChange<Uuid> change) {
    if (change instanceof DeleteEObject<?> && change instanceof EObjectExistenceEChange<?> e) {
      Object affected = e.getAffectedElement();
      return affected instanceof Uuid uuid ? uuid.toString() : null;
    }
    if (change instanceof RemoveRootEObject<?> r) {
      Object oldVal = r.getOldValue();
      return oldVal instanceof Uuid uuid ? uuid.toString() : null;
    }
    return null;
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
  public synchronized DrainResult drainChanges() {
    Map<String, List<EChange<EObject>>> snapshot = new LinkedHashMap<>();
    changesByResource.forEach((uri, changes) ->
        snapshot.put(uri, Collections.unmodifiableList(new ArrayList<>(changes))));
    changesByResource.clear();
    int drained = totalChanges;
    totalChanges = 0;

    Map<EChange<EObject>, String> overrideSnapshot = new IdentityHashMap<>(deletionUuidOverrides);
    deletionUuidOverrides.clear();

    LOGGER.info("Drained {} atomic change(s) from buffer for {} resource(s), "
        + "{} deletion UUID override(s)", drained, snapshot.size(), overrideSnapshot.size());
    return new DrainResult(
        Collections.unmodifiableMap(snapshot),
        Collections.unmodifiableMap(overrideSnapshot));
  }

  /**
   * Returns an origin-annotated snapshot of the buffer and clears it. Since this buffer
   * stores only original changes, every entry carries {@link ChangeOrigin#ORIGINAL}.
   * This method exists to provide a compatible interface for consumers that distinguish
   * original from consequential changes.
   *
   * @return immutable map of resource URI to ordered annotated changes.
   */
  public synchronized Map<String, List<AnnotatedEChange>> drainAnnotatedChanges() {
    Map<String, List<AnnotatedEChange>> snapshot = new LinkedHashMap<>();
    changesByResource.forEach((uri, changes) -> {
      List<AnnotatedEChange> annotated = new ArrayList<>();
      for (EChange<EObject> change : changes) {
        annotated.add(new AnnotatedEChange(change, ChangeOrigin.ORIGINAL));
      }
      snapshot.put(uri, Collections.unmodifiableList(annotated));
    });
    changesByResource.clear();
    int drained = totalChanges;
    totalChanges = 0;
    deletionUuidOverrides.clear();
    LOGGER.info("Drained {} annotated change(s) from buffer for {} resource(s)",
        drained, snapshot.size());
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
   *   <li>For {@link EObjectExistenceEChange} (Create/Delete): uses the affected element's
   *       resource. For delete changes the element may have been detached; falls back to
   *       {@code "unknown-resource"} in that case.</li>
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
    return "unknown-resource";
  }
}
