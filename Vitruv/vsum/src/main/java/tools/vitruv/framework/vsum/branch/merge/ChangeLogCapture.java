package tools.vitruv.framework.vsum.branch.merge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.hid.internal.HierarchicalIdResolver;
import tools.vitruv.change.atomic.resolve.AtomicEChangeResolverHelper;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;

/**
 * Extension point for Tural's consequential footprint component (merge pipeline steps 5-8).
 *
 * <p>This class is <b>not registered in production</b>. The canonical listener for change
 * capture in production is
 * {@link tools.vitruv.framework.vsum.branch.storage.SemanticChangeBuffer}.
 * {@code ChangeLogCapture} provides richer data for the interleaved replay algorithm: it
 * records consequential footprints (element+feature pairs touched by Reactions) and
 * cascade-deleted child UUIDs, both of which are needed for the indirect conflict
 * detection steps. Tural's scope is to integrate {@code ChangeLogCapture} as the
 * active {@link ChangePropagationListener} so that footprints are captured at commit time
 * and stored alongside the changelog entries.
 *
 * <hr>
 *
 * <p>Captures primary EChanges during {@code propagateChange()} calls and converts
 * them to HierarchicalId-based representation. Also tracks UUID→HierarchicalId
 * mappings for cross-branch element identity during merge conflict detection.
 *
 * <p>Conversion happens in {@code finishedChangePropagation()} (after changes applied)
 * because newly created objects don't have UUID→EObject mappings until after application.
 *
 * <h3>Deletion handling</h3>
 * When an element is deleted (removed from its containment reference), EMF removes
 * it from the model during propagation. After that, the {@link UuidResolver} can no
 * longer resolve the element's UUID to an EObject, so the normal Uuid→EObject→HierarchicalId
 * conversion path in {@code finishedChangePropagation()} would fail and the deletion
 * changes would be silently dropped from the changelog.
 *
 * <p>To handle this, {@code startedChangePropagation()} <b>pre-captures</b> UUID→HierarchicalId
 * mappings for all elements referenced by the incoming changes (while they still exist
 * in the model). The conversion in {@code finishedChangePropagation()} then falls back to
 * these pre-captured IDs when normal resolution fails. This ensures that deletion changes
 * ({@code RemoveEReference}, {@code DeleteEObject}, {@code RemoveRootEObject}) are properly
 * recorded in the changelog with correct HierarchicalIds.
 *
 * <h3>Cascade containment deletion tracking</h3>
 * When an element is removed from a containment reference ({@code RemoveEReference} or
 * {@code RemoveRootEObject}), EMF implicitly removes all its contained children. These
 * children's UUIDs are <b>not</b> recorded as separate changes by the ChangeRecorder, so
 * without additional tracking, conflicts on cascade-deleted children would go undetected
 * (the parent's UUID differs from each child's UUID).
 *
 * <p>{@code startedChangePropagation()} detects removal changes (via
 * {@link tools.vitruv.change.atomic.eobject.EObjectSubtractedEChange EObjectSubtractedEChange})
 * and walks {@code eAllContents()} of the removed element to collect all descendant UUIDs.
 * These are stored in the {@link #cascadeDeletedUuids} map (parent UUID → child UUIDs),
 * drained via {@link #drainCascadeDeletedUuids()}, and attached to the corresponding
 * {@link SemanticChangeLog.ChangeDto#cascadeDeletedUuids} field during changelog serialization.
 * The {@link UuidConflictDetector} then includes these cascade UUIDs in the deleted set,
 * enabling static conflict detection.
 */
public class ChangeLogCapture implements ChangePropagationListener {

    private static final Logger LOGGER = LogManager.getLogger(ChangeLogCapture.class);

    private final UuidResolver uuidResolver;
    private final HierarchicalIdResolver hierarchicalIdResolver;
    private final List<EChange<HierarchicalId>> bufferedChanges = new ArrayList<>();
    private final Map<String, String> uuidToHidMapping = new HashMap<>();

    private VitruviusChange<Uuid> pendingChange;
    private final List<String> pendingUuidStrings = new ArrayList<>();
    /** Pre-captured UUID→HierarchicalId mappings for elements that may be deleted during propagation. */
    private final Map<Uuid, HierarchicalId> preCapturedIds = new HashMap<>();
    /** Cascade-deleted child UUIDs: parentUuidString → list of child UUID strings. */
    private final Map<String, List<String>> cascadeDeletedUuids = new HashMap<>();
    /** Consequential footprints: UUID#feature pairs modified by Reactions during propagation. */
    private final Set<String> consequentialFootprints = new HashSet<>();

    public ChangeLogCapture(UuidResolver uuidResolver, HierarchicalIdResolver hierarchicalIdResolver) {
        this.uuidResolver = uuidResolver;
        this.hierarchicalIdResolver = hierarchicalIdResolver;
    }

    public static ChangeLogCapture create(UuidResolver uuidResolver, ResourceSet resourceSet) {
        return new ChangeLogCapture(uuidResolver, HierarchicalIdResolver.create(resourceSet));
    }

    @Override
    public void startedChangePropagation(VitruviusChange<Uuid> changeToPropagate) {
        pendingChange = changeToPropagate;
        preCapturedIds.clear();
        cascadeDeletedUuids.clear();
        // Pre-capture UUID strings and UUID→HierarchicalId mappings from the input
        // (before reactions may modify or delete model elements).
        // This is critical for deletion changes: after propagation the deleted elements
        // are no longer resolvable, so we capture their IDs while they still exist.
        for (EChange<Uuid> change : changeToPropagate.getEChanges()) {
            extractUuids(change).forEach(uuid -> {
                pendingUuidStrings.add(uuid.toString());
                try {
                    EObject eObject = uuidResolver.getEObject(uuid);
                    if (eObject != null) {
                        HierarchicalId hid = hierarchicalIdResolver.getAndUpdateId(eObject);
                        preCapturedIds.put(uuid, hid);
                    }
                } catch (Exception e) {
                    LOGGER.debug("Could not pre-capture HierarchicalId for UUID {}: {}",
                            uuid, e.getMessage());
                }
            });

            // For removal changes, walk the removed element's containment tree to capture
            // cascade-deleted child UUIDs. When a parent is removed from its containment
            // reference, EMF implicitly removes all contained children. These children's
            // UUIDs are NOT recorded as separate changes, so we must capture them here.
            if (change instanceof tools.vitruv.change.atomic.eobject.EObjectSubtractedEChange<Uuid> sc
                    && sc.getOldValue() != null) {
                Uuid removedUuid = sc.getOldValue();
                try {
                    EObject removedObj = uuidResolver.getEObject(removedUuid);
                    if (removedObj != null) {
                        List<String> childUuids = new ArrayList<>();
                        removedObj.eAllContents().forEachRemaining(child -> {
                            try {
                                var childUuid = uuidResolver.getUuid(child);
                                if (childUuid != null) {
                                    childUuids.add(childUuid.toString());
                                    // Also pre-capture the child's HierarchicalId
                                    HierarchicalId childHid =
                                            hierarchicalIdResolver.getAndUpdateId(child);
                                    preCapturedIds.put(childUuid, childHid);
                                }
                            } catch (Exception e) {
                                LOGGER.debug("Could not capture cascade child UUID: {}",
                                        e.getMessage());
                            }
                        });
                        if (!childUuids.isEmpty()) {
                            cascadeDeletedUuids.put(removedUuid.toString(), childUuids);
                            LOGGER.debug("Captured {} cascade-deleted child UUIDs for parent {}",
                                    childUuids.size(), removedUuid);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.debug("Could not walk containment tree for UUID {}: {}",
                            removedUuid, e.getMessage());
                }
            }
        }
    }

    @Override
    public void finishedChangePropagation(Iterable<PropagatedChange> propagatedChanges) {
        if (pendingChange == null) return;
        try {
            List<EChange<Uuid>> uuidChanges = pendingChange.getEChanges();
            int captured = 0;
            for (EChange<Uuid> uuidChange : uuidChanges) {
                try {
                    EChange<HierarchicalId> hidChange = convertToHierarchicalId(uuidChange);
                    bufferedChanges.add(hidChange);
                    captured++;
                } catch (Exception e) {
                    LOGGER.debug("Skipping change (UUID may reference deleted element): {}",
                            e.getMessage());
                }
            }
            // Build UUID→HierarchicalId mapping using the pre-captured UUIDs
            buildUuidMappingFromStrings();
            LOGGER.debug("Captured {}/{} primary changes for changelog", captured, uuidChanges.size());

            // Extract consequential footprints from reaction-derived changes
            for (PropagatedChange pc : propagatedChanges) {
                VitruviusChange<EObject> consequential = pc.getConsequentialChanges();
                if (consequential == null || !consequential.containsConcreteChange()) continue;
                for (EChange<EObject> ec : consequential.getEChanges()) {
                    if (!(ec instanceof tools.vitruv.change.atomic.feature.FeatureEChange<EObject, ?> fc))
                        continue;
                    EObject element = fc.getAffectedElement();
                    String featureName = fc.getAffectedFeature() != null
                            ? fc.getAffectedFeature().getName() : null;
                    if (element == null || featureName == null) continue;
                    try {
                        String uuid = uuidResolver.getUuid(element).toString();
                        consequentialFootprints.add(uuid + "#" + featureName);
                    } catch (IllegalStateException e) {
                        // Element not in resolver (transiently created by reaction) -- skip
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Failed to capture changes for changelog: {}", e.getMessage(), e);
        } finally {
            pendingChange = null;
            pendingUuidStrings.clear();
            preCapturedIds.clear();
            // Note: cascadeDeletedUuids is NOT cleared here; it is drained
            // by drainCascadeDeletedUuids() alongside drainChanges().
        }
    }

    public List<EChange<HierarchicalId>> drainChanges() {
        List<EChange<HierarchicalId>> result = new ArrayList<>(bufferedChanges);
        bufferedChanges.clear();
        return Collections.unmodifiableList(result);
    }

    /**
     * Returns the UUID→HierarchicalId mapping accumulated during capture.
     * Drained alongside changes.
     */
    public Map<String, String> drainUuidMapping() {
        Map<String, String> result = new HashMap<>(uuidToHidMapping);
        uuidToHidMapping.clear();
        return result;
    }

    /**
     * Returns the cascade-deleted UUIDs accumulated during capture.
     * Maps parent UUID string → list of child UUID strings for elements that are
     * implicitly removed when the parent is deleted from its containment reference.
     */
    public Map<String, List<String>> drainCascadeDeletedUuids() {
        Map<String, List<String>> result = new HashMap<>(cascadeDeletedUuids);
        cascadeDeletedUuids.clear();
        return result;
    }

    /**
     * Returns the consequential footprints accumulated during capture.
     * Each entry is a UUID#feature string representing an element-feature pair
     * modified by Reactions during change propagation.
     */
    public Set<String> drainConsequentialFootprints() {
        Set<String> result = new HashSet<>(consequentialFootprints);
        consequentialFootprints.clear();
        return result;
    }

    public int getBufferedChangeCount() {
        return bufferedChanges.size();
    }

    private EChange<HierarchicalId> convertToHierarchicalId(EChange<Uuid> uuidChange) {
        return AtomicEChangeResolverHelper.resolveChange(
                uuidChange,
                uuid -> {
                    // Try normal resolution first (works for creates and modifications)
                    try {
                        EObject eObject = uuidResolver.getEObject(uuid);
                        if (eObject != null) {
                            return hierarchicalIdResolver.getAndUpdateId(eObject);
                        }
                    } catch (Exception e) {
                        // Element may have been deleted during propagation -- fall through
                    }
                    // Fall back to pre-captured ID (captured in startedChangePropagation
                    // before the element was removed from the model)
                    HierarchicalId preCaptured = preCapturedIds.get(uuid);
                    if (preCaptured != null) {
                        return preCaptured;
                    }
                    throw new IllegalStateException(
                            "Cannot resolve UUID " + uuid + " -- element deleted and no pre-captured ID");
                },
                resource -> resource
        );
    }

    /**
     * Builds UUID→HierarchicalId mappings from pre-captured UUID strings.
     * Uses pre-captured IDs (from startedChangePropagation) for elements
     * that may have been deleted during propagation, and tries post-propagation
     * resolution for elements that still exist (e.g., newly created ones).
     */
    private void buildUuidMappingFromStrings() {
        // First, add all pre-captured mappings (these are reliable for deleted elements)
        for (var entry : preCapturedIds.entrySet()) {
            uuidToHidMapping.put(entry.getKey().toString(), entry.getValue().getId());
        }

        // Also build mapping from the successfully converted HID changes
        if (!pendingUuidStrings.isEmpty() && !bufferedChanges.isEmpty()) {
            int hidIdx = bufferedChanges.size() - pendingUuidStrings.size();
            if (hidIdx < 0) hidIdx = 0;
            for (int i = 0; i < pendingUuidStrings.size() && (hidIdx + i) < bufferedChanges.size(); i++) {
                EChange<HierarchicalId> hidChange = bufferedChanges.get(hidIdx + i);
                String uuidStr = pendingUuidStrings.get(i);
                String hidStr = extractHidFromChange(hidChange);
                if (hidStr != null) {
                    uuidToHidMapping.put(uuidStr, hidStr);
                }
            }
        }
    }

    private String extractHidFromChange(EChange<HierarchicalId> change) {
        if (change instanceof tools.vitruv.change.atomic.feature.FeatureEChange<HierarchicalId, ?> fc
                && fc.getAffectedElement() != null) {
            return fc.getAffectedElement().getId();
        }
        if (change instanceof tools.vitruv.change.atomic.eobject.EObjectExistenceEChange<HierarchicalId> ec
                && ec.getAffectedElement() != null) {
            return ec.getAffectedElement().getId();
        }
        return null;
    }

    private List<Uuid> extractUuids(EChange<Uuid> change) {
        List<Uuid> uuids = new ArrayList<>();
        if (change instanceof tools.vitruv.change.atomic.feature.FeatureEChange<Uuid, ?> fc
                && fc.getAffectedElement() != null) {
            uuids.add(fc.getAffectedElement());
        }
        if (change instanceof tools.vitruv.change.atomic.eobject.EObjectExistenceEChange<Uuid> ec
                && ec.getAffectedElement() != null) {
            uuids.add(ec.getAffectedElement());
        }
        if (change instanceof tools.vitruv.change.atomic.eobject.EObjectAddedEChange<Uuid> ac
                && ac.getNewValue() != null) {
            uuids.add(ac.getNewValue());
        }
        if (change instanceof tools.vitruv.change.atomic.eobject.EObjectSubtractedEChange<Uuid> sc
                && sc.getOldValue() != null) {
            uuids.add(sc.getOldValue());
        }
        return uuids;
    }
}
