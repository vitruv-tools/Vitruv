package tools.vitruv.framework.vsum.branch.storage;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.eobject.DeleteEObject;
import tools.vitruv.change.atomic.eobject.EObjectExistenceEChange;
import tools.vitruv.change.atomic.feature.FeatureEChange;
import tools.vitruv.change.atomic.feature.attribute.InsertEAttributeValue;
import tools.vitruv.change.atomic.feature.attribute.RemoveEAttributeValue;
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.change.atomic.feature.list.UpdateSingleListEntryEChange;
import tools.vitruv.change.atomic.feature.reference.InsertEReference;
import tools.vitruv.change.atomic.feature.reference.RemoveEReference;
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RemoveRootEObject;
import tools.vitruv.change.atomic.hid.internal.HierarchicalIdResolver;
import tools.vitruv.change.atomic.uuid.UuidResolver;

/**
 * Converts a list of resolved {@code EChange<EObject>} instances into {@link SemanticChangeEntry}
 * records suitable for JSON serialization in the semantic changelog.
 *
 * <p>Each EMF change subtype is mapped to a {@link SemanticChangeType}.
 * The UUID of each affected element is resolved via {@link UuidResolver}.
 * If UUID resolution fails for a given element (for example because the element has been deleted
 * and unregistered from the resolver), the entry is still produced but the {@code elementUuid}
 * field is set to {@code "unknown"} and a warning is logged.
 *
 * <p>This class is stateless and thread-safe once constructed.
 */
public class EChangeToEntryConverter {

  private static final Logger LOGGER = LogManager.getLogger(EChangeToEntryConverter.class);

  private final UuidResolver uuidResolver;

  /**
   * Optional resolver for computing {@link tools.vitruv.change.atomic.hid.HierarchicalId}
   * strings. When {@code null}, the {@code hierarchicalId} field on produced entries is
   * left {@code null}.
   */
  private final HierarchicalIdResolver hierarchicalIdResolver;

  /**
   * Pre-captured UUID strings for deletion-type EChange instances, keyed by EChange identity.
   * Consulted as a fallback in {@link #resolveUuid(EObject, EChange)} when the element has
   * already been unregistered from the UUID resolver after deletion.
   */
  private final Map<EChange<EObject>, String> deletionUuidOverrides;

  /**
   * Creates a converter that uses the given resolver for UUID lookups.
   * Hierarchical IDs will not be populated.
   *
   * @param uuidResolver the resolver to use, must not be null.
   */
  public EChangeToEntryConverter(UuidResolver uuidResolver) {
    this(uuidResolver, null, null);
  }

  /**
   * Creates a converter that uses the given resolvers for UUID and HierarchicalId lookups.
   *
   * @param uuidResolver the UUID resolver to use, must not be null.
   * @param hierarchicalIdResolver the HierarchicalId resolver to use, or {@code null} to skip.
   */
  public EChangeToEntryConverter(UuidResolver uuidResolver,
      HierarchicalIdResolver hierarchicalIdResolver) {
    this(uuidResolver, hierarchicalIdResolver, null);
  }

  /**
   * Creates a converter with pre-captured deletion UUID overrides. Use this constructor when
   * deletion UUIDs were captured before element removal (via {@link SemanticChangeBuffer}).
   *
   * @param uuidResolver the UUID resolver to use, must not be null.
   * @param hierarchicalIdResolver the HierarchicalId resolver to use, or {@code null} to skip.
   * @param deletionUuidOverrides pre-captured UUID strings keyed by EChange identity, or
   *     {@code null} to disable override lookup.
   */
  public EChangeToEntryConverter(UuidResolver uuidResolver,
      HierarchicalIdResolver hierarchicalIdResolver,
      Map<EChange<EObject>, String> deletionUuidOverrides) {
    this.uuidResolver = checkNotNull(uuidResolver, "uuidResolver must not be null");
    this.hierarchicalIdResolver = hierarchicalIdResolver;
    this.deletionUuidOverrides = deletionUuidOverrides != null ? deletionUuidOverrides : Map.of();
  }

  /**
   * Converts a list of atomic EChanges into {@link SemanticChangeEntry} records.
   * The {@code index} field on each entry reflects the position of the change in the input list.
   *
   * @param eChanges ordered list of atomic changes, must not be null.
   * @return an ordered list of entries with the same size as the input.
   */
  public List<SemanticChangeEntry> convert(List<EChange<EObject>> eChanges) {
    checkNotNull(eChanges, "eChanges must not be null");
    List<SemanticChangeEntry> result = new ArrayList<>(eChanges.size());
    for (int i = 0; i < eChanges.size(); i++) {
      result.add(convertSingle(eChanges.get(i), i));
    }
    return result;
  }

  /**
   * Converts a single EChange into a {@link SemanticChangeEntry}.
   */
  private SemanticChangeEntry convertSingle(EChange<EObject> change, int index) {
    if (change instanceof CreateEObject<?> c) {
      return handleExistence(c, index, SemanticChangeType.ELEMENT_CREATED, "CreateEObject");
    }
    if (change instanceof DeleteEObject<?> d) {
      return handleDelete(d, index);
    }
    if (change instanceof ReplaceSingleValuedEAttribute<?, ?> r) {
      return handleReplaceAttribute(r, index);
    }
    if (change instanceof InsertEAttributeValue<?, ?> i) {
      return handleInsertAttribute(i, index);
    }
    if (change instanceof RemoveEAttributeValue<?, ?> r) {
      return handleRemoveAttribute(r, index);
    }
    if (change instanceof ReplaceSingleValuedEReference<?> r) {
      return handleReplaceReference(r, index);
    }
    if (change instanceof InsertEReference<?> i) {
      return handleInsertReference(i, index);
    }
    if (change instanceof RemoveEReference<?> r) {
      return handleRemoveReference(r, index);
    }
    if (change instanceof InsertRootEObject<?> i) {
      return handleInsertRoot(i, index);
    }
    if (change instanceof RemoveRootEObject<?> r) {
      return handleRemoveRoot(r, index);
    }
    // Fallback for any EChange subtype not explicitly handled above
    // (e.g. PermuteContainmentEReference or future additions to the Vitruv change model).
    return handleUnknown(change, index);
  }

  // Handle lifecycle changes
  private SemanticChangeEntry handleExistence(
      EObjectExistenceEChange<?> change, int index, SemanticChangeType type, String emfType) {
    EObject element = (EObject) change.getAffectedElement();
    return SemanticChangeEntry.builder()
        .index(index)
        .changeType(type)
        .emfType(emfType)
        .elementUuid(resolveUuid(element))
        .eClass(formatEClass(element))
        .hierarchicalId(resolveHierarchicalId(element))
        .changeOrigin("original")
        .build();
  }

  @SuppressWarnings("unchecked")
  private SemanticChangeEntry handleDelete(DeleteEObject<?> change, int index) {
    EObject element = (EObject) change.getAffectedElement();
    List<String> cascadeUuids = collectCascadeUuids(element);
    return SemanticChangeEntry.builder()
        .index(index)
        .changeType(SemanticChangeType.ELEMENT_DELETED)
        .emfType("DeleteEObject")
        .elementUuid(resolveUuid(element, (EChange<EObject>) change))
        .eClass(formatEClass(element))
        .hierarchicalId(resolveHierarchicalId(element))
        .cascadeDeletedUuids(cascadeUuids.isEmpty() ? null : cascadeUuids)
        .changeOrigin("original")
        .build();
  }

  /**
   * Walks {@code eAllContents()} of the given element and collects the Vitruvius UUID
   * of every descendant whose UUID can be resolved. Called before the element is
   * detached from the model so the containment tree is still intact.
   */
  private List<String> collectCascadeUuids(EObject element) {
    List<String> uuids = new ArrayList<>();
    if (element == null) {
      return uuids;
    }
    var contents = element.eAllContents();
    if (contents != null) {
      contents.forEachRemaining(child -> {
        String uuid = resolveUuid(child);
        if (uuid != null && !"unknown".equals(uuid)) {
          uuids.add(uuid);
        }
      });
    }
    return uuids;
  }

  // Handle single-valued attribute changes

  @SuppressWarnings("unchecked")
  private SemanticChangeEntry handleReplaceAttribute(
      ReplaceSingleValuedEAttribute<?, ?> change, int index) {
    EObject element = (EObject) change.getAffectedElement();
    Object oldValue = ((ReplaceSingleValuedEAttribute<Object, Object>) change).getOldValue();
    Object newValue = ((ReplaceSingleValuedEAttribute<Object, Object>) change).getNewValue();

    SemanticChangeType type;
    if (oldValue == null && newValue != null) {
      type = SemanticChangeType.ATTRIBUTE_SET;
    } else if (oldValue != null && newValue == null) {
      type = SemanticChangeType.ATTRIBUTE_CLEARED;
    } else {
      type = SemanticChangeType.ATTRIBUTE_CHANGED;
    }

    return SemanticChangeEntry.builder()
        .index(index)
        .changeType(type)
        .emfType("ReplaceSingleValuedEAttribute")
        .elementUuid(resolveUuid(element))
        .eClass(formatEClass(element))
        .feature(featureName(change))
        .from(formatValue(oldValue))
        .to(formatValue(newValue))
        .containerUuid(resolveContainerUuid(element))
        .hierarchicalId(resolveHierarchicalId(element))
        .changeOrigin("original")
        .build();
  }

  @SuppressWarnings("unchecked")
  private SemanticChangeEntry handleInsertAttribute(
      InsertEAttributeValue<?, ?> change, int index) {
    EObject element = (EObject) change.getAffectedElement();
    Object newValue = ((InsertEAttributeValue<Object, Object>) change).getNewValue();

    return SemanticChangeEntry.builder()
        .index(index)
        .changeType(SemanticChangeType.ATTRIBUTE_VALUE_INSERTED)
        .emfType("InsertEAttributeValue")
        .elementUuid(resolveUuid(element))
        .eClass(formatEClass(element))
        .feature(featureName(change))
        .to(formatValue(newValue))
        .position(((UpdateSingleListEntryEChange<?, ?>) change).getIndex())
        .containerUuid(resolveContainerUuid(element))
        .hierarchicalId(resolveHierarchicalId(element))
        .changeOrigin("original")
        .build();
  }

  @SuppressWarnings("unchecked")
  private SemanticChangeEntry handleRemoveAttribute(
      RemoveEAttributeValue<?, ?> change, int index) {
    EObject element = (EObject) change.getAffectedElement();
    Object oldValue = ((RemoveEAttributeValue<Object, Object>) change).getOldValue();

    return SemanticChangeEntry.builder()
        .index(index)
        .changeType(SemanticChangeType.ATTRIBUTE_VALUE_REMOVED)
        .emfType("RemoveEAttributeValue")
        .elementUuid(resolveUuid(element))
        .eClass(formatEClass(element))
        .feature(featureName(change))
        .from(formatValue(oldValue))
        .position(((UpdateSingleListEntryEChange<?, ?>) change).getIndex())
        .containerUuid(resolveContainerUuid(element))
        .hierarchicalId(resolveHierarchicalId(element))
        .changeOrigin("original")
        .build();
  }

  // Handle single-valued reference changes

  @SuppressWarnings("unchecked")
  private SemanticChangeEntry handleReplaceReference(
      ReplaceSingleValuedEReference<?> change, int index) {
    EObject element = (EObject) change.getAffectedElement();
    EObject oldRef = (EObject) ((ReplaceSingleValuedEReference<Object>) change).getOldValue();
    EObject newRef = (EObject) ((ReplaceSingleValuedEReference<Object>) change).getNewValue();

    SemanticChangeType type;
    if (oldRef == null && newRef != null) {
      type = SemanticChangeType.REFERENCE_SET;
    } else if (oldRef != null && newRef == null) {
      type = SemanticChangeType.REFERENCE_CLEARED;
    } else {
      type = SemanticChangeType.REFERENCE_CHANGED;
    }
    return SemanticChangeEntry.builder()
        .index(index)
        .changeType(type)
        .emfType("ReplaceSingleValuedEReference")
        .elementUuid(resolveUuid(element))
        .eClass(formatEClass(element))
        .feature(featureName(change))
        .from(oldRef != null ? resolveUuid(oldRef) : null)
        .to(newRef != null ? resolveUuid(newRef) : null)
        .containerUuid(resolveContainerUuid(element))
        .hierarchicalId(resolveHierarchicalId(element))
        .changeOrigin("original")
        .build();
  }

  // Handle multi-valued reference changes

  @SuppressWarnings("unchecked")
  private SemanticChangeEntry handleInsertReference(InsertEReference<?> change, int index) {
    EObject element = (EObject) change.getAffectedElement();
    EObject inserted = (EObject) ((InsertEReference<Object>) change).getNewValue();
    String insertedUuid = inserted != null ? resolveUuid(inserted) : null;

    return SemanticChangeEntry.builder()
        .index(index)
        .changeType(SemanticChangeType.REFERENCE_VALUE_INSERTED)
        .emfType("InsertEReference")
        .elementUuid(resolveUuid(element))
        .eClass(formatEClass(element))
        .feature(featureName(change))
        .referencedElementUuid(insertedUuid)
        .to(insertedUuid)
        .position(((UpdateSingleListEntryEChange<?, ?>) change).getIndex())
        .containerUuid(resolveContainerUuid(element))
        .hierarchicalId(resolveHierarchicalId(element))
        .changeOrigin("original")
        .build();
  }

  @SuppressWarnings("unchecked")
  private SemanticChangeEntry handleRemoveReference(RemoveEReference<?> change, int index) {
    EObject element = (EObject) change.getAffectedElement();
    EObject removed = (EObject) ((RemoveEReference<Object>) change).getOldValue();
    String removedUuid = removed != null ? resolveUuid(removed) : null;

    return SemanticChangeEntry.builder()
        .index(index)
        .changeType(SemanticChangeType.REFERENCE_VALUE_REMOVED)
        .emfType("RemoveEReference")
        .elementUuid(resolveUuid(element))
        .eClass(formatEClass(element))
        .feature(featureName(change))
        .referencedElementUuid(removedUuid)
        .from(removedUuid)
        .position(((UpdateSingleListEntryEChange<?, ?>) change).getIndex())
        .containerUuid(resolveContainerUuid(element))
        .hierarchicalId(resolveHierarchicalId(element))
        .changeOrigin("original")
        .build();
  }

  // Handle root EObject changes

  @SuppressWarnings("unchecked")
  private SemanticChangeEntry handleInsertRoot(InsertRootEObject<?> change, int index) {
    EObject element = (EObject) change.getNewValue();
    return SemanticChangeEntry.builder()
        .index(index)
        .changeType(SemanticChangeType.ROOT_INSERTED)
        .emfType("InsertRootEObject")
        .elementUuid(resolveUuid(element))
        .eClass(formatEClass(element))
        .to(change.getUri())
        .hierarchicalId(resolveHierarchicalId(element))
        .changeOrigin("original")
        .build();
  }

  @SuppressWarnings("unchecked")
  private SemanticChangeEntry handleRemoveRoot(RemoveRootEObject<?> change, int index) {
    EObject element = (EObject) change.getOldValue();
    return SemanticChangeEntry.builder()
        .index(index)
        .changeType(SemanticChangeType.ROOT_REMOVED)
        .emfType("RemoveRootEObject")
        .elementUuid(resolveUuid(element, (EChange<EObject>) change))
        .eClass(formatEClass(element))
        .from(change.getUri())
        .hierarchicalId(resolveHierarchicalId(element))
        .changeOrigin("original")
        .build();
  }

  // Fallback
  private SemanticChangeEntry handleUnknown(EChange<EObject> change, int index) {
    LOGGER.warn("Unrecognized EChange type '{}', producing minimal entry",
        change.getClass().getSimpleName());
    String emfType = change.getClass().getSimpleName();
    String uuid = null;
    String eClass = null;
    if (change instanceof FeatureEChange<?, ?> f) {
      EObject element = (EObject) f.getAffectedElement();
      uuid = resolveUuid(element);
      eClass = formatEClass(element);
    }
    return SemanticChangeEntry.builder()
        .index(index)
        .changeType(SemanticChangeType.ELEMENT_REORDERED)
        .emfType(emfType)
        .elementUuid(uuid)
        .eClass(eClass)
        .changeOrigin("original")
        .build();
  }

  /**
   * Resolves the {@link tools.vitruv.change.atomic.hid.HierarchicalId} string for the given
   * element. Returns {@code null} if the resolver is not configured or resolution fails.
   */
  private String resolveHierarchicalId(EObject element) {
    if (element == null || hierarchicalIdResolver == null) {
      return null;
    }
    try {
      return hierarchicalIdResolver.getAndUpdateId(element).getId();
    } catch (Exception e) {
      LOGGER.debug("HierarchicalId resolution failed for element of type '{}': {}",
          element.eClass() != null ? element.eClass().getName() : "?", e.getMessage());
      return null;
    }
  }

  private String featureName(FeatureEChange<?, ?> change) {
    return change.getAffectedFeature() != null ? change.getAffectedFeature().getName() : null;
  }

  /**
   * Resolves the UUID of the immediate EMF container of the given element.
   * Returns {@code null} if the element has no container (root element) or if
   * UUID resolution fails for the container.
   */
  private String resolveContainerUuid(EObject element) {
    if (element == null) {
      return null;
    }
    EObject container = element.eContainer();
    if (container == null) {
      return null;
    }
    try {
      if (uuidResolver.hasUuid(container)) {
        return uuidResolver.getUuid(container).toString();
      }
    } catch (Exception e) {
      LOGGER.debug("Container UUID resolution failed for container of type '{}': {}",
          container.eClass() != null ? container.eClass().getName() : "?", e.getMessage());
    }
    return null;
  }

  /**
   * Resolves the Vitruvius UUID string for the given EObject.
   * Returns {@code "unknown"} and logs a warning if resolution fails.
   */
  private String resolveUuid(EObject element) {
    return resolveUuid(element, null);
  }

  /**
   * Resolves the Vitruvius UUID string for the given EObject, with a pre-captured override
   * fallback for deletion-type changes where the element is already unregistered.
   *
   * @param element the EObject whose UUID to resolve.
   * @param change  the EChange that produced this element lookup, used to find a pre-captured
   *                UUID override; may be {@code null} to skip override lookup.
   */
  private String resolveUuid(EObject element, EChange<EObject> change) {
    if (element == null) {
      return null;
    }
    try {
      if (uuidResolver.hasUuid(element)) {
        return uuidResolver.getUuid(element).toString();
      }
      if (change != null) {
        String override = deletionUuidOverrides.get(change);
        if (override != null) {
          LOGGER.debug("Using pre-captured UUID '{}' for detached element of type '{}'",
              override, element.eClass() != null ? element.eClass().getName() : "?");
          return override;
        }
      }
      String eClassName = element.eClass() != null ? element.eClass().getName() : "null";
      boolean isProxy = element.eIsProxy();
      String resourceUri = element.eResource() != null
          ? element.eResource().getURI().toString() : "NO_RESOURCE";
      LOGGER.warn(
          "No UUID for element type='{}' isProxy={} resource='{}' identity={}",
          eClassName, isProxy, resourceUri, System.identityHashCode(element));
      return "unknown";
    } catch (Exception e) {
      LOGGER.warn("UUID resolution failed for element of type '{}': {}",
          element.eClass() != null ? element.eClass().getName() : "?", e.getMessage());
      return "unknown";
    }
  }

  /**
   * Formats the EClass of an element as {@code "nsPrefix::ClassName"}.
   * Returns {@code null} if the element is null or has no EClass.
   */
  private String formatEClass(EObject element) {
    if (element == null || element.eClass() == null) {
      return null;
    }
    String nsPrefix = element.eClass().getEPackage() != null
        ? element.eClass().getEPackage().getNsPrefix() : null;
    String name = element.eClass().getName();
    return nsPrefix != null && !nsPrefix.isEmpty() ? nsPrefix + "::" + name : name;
  }

  /**
   * Converts an attribute value to its string representation for the JSON changelog.
   * Returns {@code null} if the value is null.
   */
  private String formatValue(Object value) {
    return value != null ? value.toString() : null;
  }
}
