package tools.vitruv.framework.vsum.branch.merge;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.TypeInferringAtomicEChangeFactory;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.eobject.DeleteEObject;
import tools.vitruv.change.atomic.eobject.EobjectFactory;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RemoveRootEObject;
import tools.vitruv.change.atomic.root.RootFactory;

/**
 * Reconstructs live {@link EChange}<{@link HierarchicalId}> objects from serialized
 * {@link SemanticChangeLog.ChangeDto} entries.
 *
 * <p>Follows the same construction patterns as {@code AtomicEChangeCopier.copyOld()} --
 * creates empty EChange instances via EMF factories and sets their fields from the DTO.
 * The resulting EChanges can be passed to
 * {@code VitruviusChangeResolverFactory.forHierarchicalIds(resourceSet).resolveAndApply()}
 * for resolution and application.
 *
 * <p>EClass resolution is done by scanning {@code EPackage.Registry.INSTANCE} for the
 * class name. For the prototype, this works because our metamodel class names are unique.
 */
public class ChangeDtoDeserializer {

    private static final Logger LOGGER = LogManager.getLogger(ChangeDtoDeserializer.class);
    private static final TypeInferringAtomicEChangeFactory FACTORY =
            TypeInferringAtomicEChangeFactory.getInstance();

    private final String sourceUriPrefix;
    private final String targetUriPrefix;
    private int createCounter = 0;
    // Maps original DTO element IDs to cache IDs for newly created objects
    private final java.util.Map<String, String> createdElementCacheIds = new java.util.HashMap<>();

    /**
     * @param sourceUriPrefix URI prefix in the captured HierarchicalIds (from the source branch)
     * @param targetUriPrefix URI prefix in the target VSUM's resources
     */
    public ChangeDtoDeserializer(String sourceUriPrefix, String targetUriPrefix) {
        this.sourceUriPrefix = sourceUriPrefix;
        this.targetUriPrefix = targetUriPrefix;
    }

    /** Deserializes without URI normalization (same-dir usage). */
    public ChangeDtoDeserializer() {
        this(null, null);
    }

    /**
     * Deserializes a list of DTOs into live EChange objects.
     */
    public List<EChange<HierarchicalId>> deserializeAll(List<SemanticChangeLog.ChangeDto> dtos) {
        List<EChange<HierarchicalId>> changes = new ArrayList<>();
        for (SemanticChangeLog.ChangeDto dto : dtos) {
            try {
                changes.add(deserialize(dto));
            } catch (Exception e) {
                LOGGER.error("Failed to deserialize DTO: {} -- {}", dto, e.getMessage());
                throw new IllegalStateException("Cannot deserialize change DTO: " + dto, e);
            }
        }
        return changes;
    }

    @SuppressWarnings("unchecked")
    public EChange<HierarchicalId> deserialize(SemanticChangeLog.ChangeDto dto) {
        return switch (dto.changeType) {
            case "CreateEObject" -> deserializeCreate(dto);
            case "DeleteEObject" -> deserializeDelete(dto);
            case "InsertRootEObject" -> deserializeInsertRoot(dto);
            case "RemoveRootEObject" -> deserializeRemoveRoot(dto);
            case "InsertEReference" -> deserializeInsertReference(dto);
            case "RemoveEReference" -> deserializeRemoveReference(dto);
            case "ReplaceSingleValuedEReference" -> deserializeReplaceReference(dto);
            case "InsertEAttributeValue" -> deserializeInsertAttribute(dto);
            case "RemoveEAttributeValue" -> deserializeRemoveAttribute(dto);
            case "ReplaceSingleValuedEAttribute" -> deserializeReplaceAttribute(dto);
            default -> throw new IllegalArgumentException("Unknown change type: " + dto.changeType);
        };
    }

    /**
     * CreateEObject must use a cache-prefixed HierarchicalId because the resolver
     * creates a fresh EObject and checks its auto-generated ID matches.
     * The auto-generated ID for a new object is "cache:/N" (N = creation order).
     */
    private EChange<HierarchicalId> deserializeCreate(SemanticChangeLog.ChangeDto dto) {
        CreateEObject<HierarchicalId> c = EobjectFactory.eINSTANCE.createCreateEObject();
        String cacheId = "cache:/" + createCounter++;
        c.setAffectedElement(new HierarchicalId(cacheId));
        // affectedEObjectType is populated by Tural's serializer but not by our
        // SemanticChangeEntryToChangeDtoConverter; fall back to affectedEClassName.
        String typeName = dto.affectedEObjectType != null
                ? dto.affectedEObjectType : dto.affectedEClassName;
        c.setAffectedEObjectType(resolveEClass(typeName));
        // Remember mapping: original DTO ID → cache ID (for subsequent InsertEReference).
        // Store both the file-based HID and the UUID because InsertEReference.newValueId
        // is a UUID (from SemanticChangeEntryToChangeDtoConverter), not a file HID.
        if (dto.affectedElementId != null) {
            createdElementCacheIds.put(dto.affectedElementId, cacheId);
        }
        if (dto.affectedElementUuid != null) {
            createdElementCacheIds.put(dto.affectedElementUuid, cacheId);
        }
        return c;
    }

    private EChange<HierarchicalId> deserializeDelete(SemanticChangeLog.ChangeDto dto) {
        DeleteEObject<HierarchicalId> c = EobjectFactory.eINSTANCE.createDeleteEObject();
        c.setAffectedElement(hid(dto.affectedElementId));
        c.setAffectedEObjectType(resolveEClass(dto.affectedEObjectType));
        return c;
    }

    private EChange<HierarchicalId> deserializeInsertRoot(SemanticChangeLog.ChangeDto dto) {
        InsertRootEObject<HierarchicalId> c = RootFactory.eINSTANCE.createInsertRootEObject();
        c.setNewValue(hidOrCache(dto.newValueId));
        c.setUri(normalizeUri(dto.resourceUri));
        c.setIndex(dto.index);
        return c;
    }

    private EChange<HierarchicalId> deserializeRemoveRoot(SemanticChangeLog.ChangeDto dto) {
        RemoveRootEObject<HierarchicalId> c = RootFactory.eINSTANCE.createRemoveRootEObject();
        c.setOldValue(hidOrCache(dto.oldValueId));
        c.setUri(normalizeUri(dto.resourceUri));
        c.setIndex(dto.index);
        return c;
    }

    @SuppressWarnings("unchecked")
    private EChange<HierarchicalId> deserializeInsertReference(SemanticChangeLog.ChangeDto dto) {
        EReference ref = resolveReference(dto.affectedEClassName, dto.featureName);
        return (EChange<HierarchicalId>) (EChange<?>) FACTORY.createInsertReferenceChange(
                hid(dto.affectedElementId), ref, hidOrCache(dto.newValueId), dto.index);
    }

    @SuppressWarnings("unchecked")
    private EChange<HierarchicalId> deserializeRemoveReference(SemanticChangeLog.ChangeDto dto) {
        EReference ref = resolveReference(dto.affectedEClassName, dto.featureName);
        return (EChange<HierarchicalId>) (EChange<?>) FACTORY.createRemoveReferenceChange(
                hid(dto.affectedElementId), ref, hidOrCache(dto.oldValueId), dto.index);
    }

    @SuppressWarnings("unchecked")
    private EChange<HierarchicalId> deserializeReplaceReference(SemanticChangeLog.ChangeDto dto) {
        EReference ref = resolveReference(dto.affectedEClassName, dto.featureName);
        return (EChange<HierarchicalId>) (EChange<?>) FACTORY.createReplaceSingleReferenceChange(
                hid(dto.affectedElementId), ref, hidOrCache(dto.oldValueId), hidOrCache(dto.newValueId));
    }

    @SuppressWarnings("unchecked")
    private EChange<HierarchicalId> deserializeInsertAttribute(SemanticChangeLog.ChangeDto dto) {
        EAttribute attr = resolveAttribute(dto.affectedEClassName, dto.featureName);
        Object value = convertValue(dto.newLiteralValue, attr);
        return (EChange<HierarchicalId>) (EChange<?>) FACTORY.createInsertAttributeChange(
                hid(dto.affectedElementId), attr, dto.index, value);
    }

    @SuppressWarnings("unchecked")
    private EChange<HierarchicalId> deserializeRemoveAttribute(SemanticChangeLog.ChangeDto dto) {
        EAttribute attr = resolveAttribute(dto.affectedEClassName, dto.featureName);
        Object value = convertValue(dto.oldLiteralValue, attr);
        return (EChange<HierarchicalId>) (EChange<?>) FACTORY.createRemoveAttributeChange(
                hid(dto.affectedElementId), attr, dto.index, value);
    }

    @SuppressWarnings("unchecked")
    private EChange<HierarchicalId> deserializeReplaceAttribute(SemanticChangeLog.ChangeDto dto) {
        EAttribute attr = resolveAttribute(dto.affectedEClassName, dto.featureName);
        Object oldVal = convertValue(dto.oldLiteralValue, attr);
        Object newVal = convertValue(dto.newLiteralValue, attr);
        return (EChange<HierarchicalId>) (EChange<?>) FACTORY.createReplaceSingleAttributeChange(
                hid(dto.affectedElementId), attr, oldVal, newVal);
    }

    /**
     * Resolves an ID to a HierarchicalId using the normalized resource-based path.
     * Used for affectedElement in feature/attribute changes (element is already in a resource).
     */
    private HierarchicalId hid(String idString) {
        if (idString == null) return null;
        return new HierarchicalId(normalizeId(idString));
    }

    /**
     * Resolves an ID, returning cache ID if the element was just created.
     * Used for newValue/oldValue in reference changes (element may still be in staging area).
     */
    private HierarchicalId hidOrCache(String idString) {
        if (idString == null) return null;
        String cacheId = createdElementCacheIds.get(idString);
        if (cacheId != null) {
            return new HierarchicalId(cacheId);
        }
        return new HierarchicalId(normalizeId(idString));
    }

    /**
     * Normalizes a HierarchicalId string so it resolves against the target VSUM's resources.
     * Extracts the model filename + fragment from the source ID and reconstructs with
     * the target URI prefix, since the source IDs contain absolute paths from a
     * different temp directory.
     */
    private String normalizeId(String id) {
        if (id == null || targetUriPrefix == null) return id;
        if (!id.contains("file:")) return id; // only normalize file URIs, not cache IDs

        // Split on # to separate resource URI from fragment
        int hashIdx = id.indexOf('#');
        String resourcePart = hashIdx >= 0 ? id.substring(0, hashIdx) : id;
        String fragment = hashIdx >= 0 ? id.substring(hashIdx) : "";

        // Extract just the filename from the resource URI
        int lastSlash = resourcePart.lastIndexOf('/');
        if (lastSlash >= 0) {
            String fileName = resourcePart.substring(lastSlash + 1);
            return targetUriPrefix + "/" + fileName + fragment;
        }
        return id;
    }

    private String normalizeUri(String uri) {
        if (uri == null || targetUriPrefix == null) return uri;
        if (!uri.contains("file:")) return uri;

        int lastSlash = uri.lastIndexOf('/');
        if (lastSlash >= 0) {
            String fileName = uri.substring(lastSlash + 1);
            return targetUriPrefix + "/" + fileName;
        }
        return uri;
    }

    private EClass resolveEClass(String className) {
        if (className == null) {
            throw new IllegalArgumentException("Cannot resolve null class name");
        }
        for (Object value : EPackage.Registry.INSTANCE.values()) {
            EPackage pkg = null;
            if (value instanceof EPackage p) {
                pkg = p;
            } else if (value instanceof EPackage.Descriptor desc) {
                try {
                    pkg = desc.getEPackage();
                } catch (Exception e) {
                    continue;
                }
            }
            if (pkg != null) {
                EClassifier classifier = pkg.getEClassifier(className);
                if (classifier instanceof EClass eClass) {
                    return eClass;
                }
            }
        }
        throw new IllegalStateException("EClass not found in EPackage registry: " + className);
    }

    private EAttribute resolveAttribute(String className, String featureName) {
        EClass eClass = resolveEClass(className);
        EStructuralFeature feature = eClass.getEStructuralFeature(featureName);
        if (feature instanceof EAttribute attr) return attr;
        throw new IllegalStateException("EAttribute '%s' not found on %s".formatted(featureName, className));
    }

    private EReference resolveReference(String className, String featureName) {
        EClass eClass = resolveEClass(className);
        EStructuralFeature feature = eClass.getEStructuralFeature(featureName);
        if (feature instanceof EReference ref) return ref;
        throw new IllegalStateException("EReference '%s' not found on %s".formatted(featureName, className));
    }

    /**
     * Converts a DTO value (which Gson may have deserialized as Double for numbers)
     * back to the correct type for the attribute.
     */
    private Object convertValue(Object raw, EAttribute attr) {
        if (raw == null) return null;
        EDataType type = attr.getEAttributeType();
        String stringValue = raw.toString();
        // Gson deserializes all numbers as Double. Convert to int string if the target type is integer.
        String typeName = type.getName();
        if (raw instanceof Double d && ("EInt".equals(typeName) || "EIntegerObject".equals(typeName)
                || "int".equals(typeName) || "Integer".equals(typeName))) {
            stringValue = String.valueOf(d.intValue());
        }
        return EcoreUtil.createFromString(type, stringValue);
    }
}
