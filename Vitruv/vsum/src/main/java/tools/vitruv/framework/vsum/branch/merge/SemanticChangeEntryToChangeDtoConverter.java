package tools.vitruv.framework.vsum.branch.merge;

import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeType;

/**
 * Converts a {@link SemanticChangeEntry} from our JSON changelog format into a
 * {@link SemanticChangeLog.ChangeDto} expected by the merge engine.
 *
 * <p>Our format stores changes as {@link SemanticChangeEntry} records keyed by
 * {@link SemanticChangeType}. The merge engine works with {@link SemanticChangeLog.ChangeDto}
 * records keyed by EMF class simple names. This converter bridges the two.
 *
 * <p>Field mapping:
 * <ul>
 *   <li>{@code emfType} maps to {@code changeType}</li>
 *   <li>{@code hierarchicalId} maps to {@code affectedElementId}</li>
 *   <li>{@code elementUuid} maps to {@code affectedElementUuid} (primary conflict detection key)</li>
 *   <li>{@code eClass} (with optional {@code nsPrefix::} stripped) maps to {@code affectedEClassName}</li>
 *   <li>{@code feature} maps to {@code featureName}</li>
 *   <li>{@code from} maps to {@code oldLiteralValue} for attribute changes, {@code oldValueId}
 *       for reference changes</li>
 *   <li>{@code to} / {@code referencedElementUuid} maps to {@code newLiteralValue} for attribute
 *       changes, {@code newValueId} for reference changes</li>
 *   <li>{@code position} maps to {@code index}</li>
 * </ul>
 *
 * <p>{@code resourceUri} and {@code cascadeDeletedUuids} are not populated -- they are not
 * stored in our format. {@code resourceUri} is not needed for conflict detection.
 * {@code cascadeDeletedUuids} capture is Tural's scope.
 */
public class SemanticChangeEntryToChangeDtoConverter {

    private SemanticChangeEntryToChangeDtoConverter() {}

    /**
     * Converts a single {@link SemanticChangeEntry} to a {@link SemanticChangeLog.ChangeDto}.
     *
     * @param entry the entry to convert; must not be null.
     * @return a populated ChangeDto ready for use by the merge engine.
     */
    public static SemanticChangeLog.ChangeDto convert(SemanticChangeEntry entry) {
        SemanticChangeLog.ChangeDto dto = new SemanticChangeLog.ChangeDto();

        dto.changeType = entry.getEmfType();
        dto.affectedElementId = entry.getHierarchicalId();
        dto.affectedElementUuid = entry.getElementUuid();
        dto.affectedEClassName = stripNsPrefix(entry.getEClass());
        dto.featureName = entry.getFeature();
        dto.index = entry.getPosition();

        SemanticChangeType type = entry.getChangeType();
        if (type != null && isReferenceChange(type)) {
            // Reference changes: from/to hold UUIDs of the referenced elements
            dto.oldValueId = entry.getFrom();
            dto.newValueId = entry.getTo() != null
                    ? entry.getTo() : entry.getReferencedElementUuid();
        } else {
            // Attribute and lifecycle changes: from/to hold string representations of values
            dto.oldLiteralValue = entry.getFrom();
            dto.newLiteralValue = entry.getTo();
        }

        return dto;
    }

    /**
     * Strips the metamodel namespace prefix from an eClass string.
     * Our format stores eClass as {@code "nsPrefix::ClassName"};
     * {@link SemanticChangeLog.ChangeDto#affectedEClassName} expects just {@code "ClassName"}.
     */
    private static String stripNsPrefix(String eClass) {
        if (eClass == null) return null;
        int sep = eClass.indexOf("::");
        return sep >= 0 ? eClass.substring(sep + 2) : eClass;
    }

    private static boolean isReferenceChange(SemanticChangeType type) {
        return type == SemanticChangeType.REFERENCE_CHANGED
                || type == SemanticChangeType.REFERENCE_SET
                || type == SemanticChangeType.REFERENCE_CLEARED
                || type == SemanticChangeType.REFERENCE_VALUE_INSERTED
                || type == SemanticChangeType.REFERENCE_VALUE_REMOVED;
    }
}
