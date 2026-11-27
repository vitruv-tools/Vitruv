package tools.vitruv.framework.vsum.branch.merge;

import java.util.List;

import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.eobject.EObjectExistenceEChange;
import tools.vitruv.change.atomic.feature.FeatureEChange;
import tools.vitruv.change.atomic.feature.attribute.InsertEAttributeValue;
import tools.vitruv.change.atomic.feature.attribute.RemoveEAttributeValue;
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.change.atomic.feature.reference.InsertEReference;
import tools.vitruv.change.atomic.feature.reference.RemoveEReference;
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RemoveRootEObject;
import tools.vitruv.change.atomic.root.RootEChange;

/**
 * Container for the {@link ChangeDto} format used by the semantic merge engine.
 *
 * <p>The canonical changelog format is written by
 * {@link tools.vitruv.framework.vsum.branch.SemanticChangelogManager}.
 * {@link SemanticChangeEntryToChangeDtoConverter} converts each
 * {@link tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry} into
 * a {@link ChangeDto} before it is passed to the engine.
 *
 * <p>Note: XMI serialization was attempted but fails because the EChange Ecore metamodel
 * defines element references via generic {@code ETypeParameter} with bounds
 * {@code EJavaObject}, preventing proper cross-reference serialization of
 * HierarchicalId objects.
 */
public class SemanticChangeLog {

    public static class ChangeDto {
        public String changeType;
        public String affectedElementId;
        public String affectedElementUuid;
        public String affectedEClassName;
        public String featureName;
        public String oldValueId;
        public String newValueId;
        public Object oldLiteralValue;
        public Object newLiteralValue;
        public int index = -1;
        public String resourceUri;
        public String affectedEObjectType;
        /**
         * UUIDs of all contained children that are implicitly cascade-deleted when
         * this element is removed from its containment reference. Populated at changelog
         * capture time by walking {@code eAllContents()} of the removed element.
         * Used by {@link UuidConflictDetector} to detect conflicts on child elements.
         * Null if this change is not a removal or the element has no children.
         */
        public List<String> cascadeDeletedUuids;

        @SuppressWarnings("unchecked")
        public static ChangeDto fromEChange(EChange<HierarchicalId> change) {
            ChangeDto dto = new ChangeDto();
            dto.changeType = change.getClass().getSimpleName().replaceAll("Impl$", "");

            if (change instanceof FeatureEChange<HierarchicalId, ?> fc) {
                dto.affectedElementId = idToString(fc.getAffectedElement());
                dto.featureName = fc.getAffectedFeature() != null
                        ? fc.getAffectedFeature().getName() : null;
                if (fc.getAffectedFeature() != null && fc.getAffectedFeature().getEContainingClass() != null) {
                    dto.affectedEClassName = fc.getAffectedFeature().getEContainingClass().getName();
                }
            }
            if (change instanceof EObjectExistenceEChange<HierarchicalId> ec) {
                dto.affectedElementId = idToString(ec.getAffectedElement());
                dto.affectedEObjectType = ec.getAffectedEObjectType() != null
                        ? ec.getAffectedEObjectType().getName() : null;
            }
            if (change instanceof InsertEAttributeValue<HierarchicalId, ?> ia) {
                dto.newLiteralValue = ia.getNewValue();
                dto.index = ia.getIndex();
            }
            if (change instanceof RemoveEAttributeValue<HierarchicalId, ?> ra) {
                dto.oldLiteralValue = ra.getOldValue();
                dto.index = ra.getIndex();
            }
            if (change instanceof ReplaceSingleValuedEAttribute<HierarchicalId, ?> rsa) {
                dto.oldLiteralValue = rsa.getOldValue();
                dto.newLiteralValue = rsa.getNewValue();
            }
            if (change instanceof InsertEReference<HierarchicalId> ir) {
                dto.newValueId = idToString(ir.getNewValue());
                dto.index = ir.getIndex();
            }
            if (change instanceof RemoveEReference<HierarchicalId> rr) {
                dto.oldValueId = idToString(rr.getOldValue());
                dto.index = rr.getIndex();
            }
            if (change instanceof ReplaceSingleValuedEReference<HierarchicalId> rsr) {
                dto.oldValueId = idToString(rsr.getOldValue());
                dto.newValueId = idToString(rsr.getNewValue());
            }
            if (change instanceof RootEChange<HierarchicalId> rc) {
                dto.resourceUri = rc.getUri();
                dto.index = rc.getIndex();
            }
            if (change instanceof InsertRootEObject<HierarchicalId> iro) {
                dto.newValueId = idToString(iro.getNewValue());
            }
            if (change instanceof RemoveRootEObject<HierarchicalId> rro) {
                dto.oldValueId = idToString(rro.getOldValue());
            }
            return dto;
        }

        private static String idToString(HierarchicalId id) {
            return id != null ? id.getId() : null;
        }

        @Override
        public String toString() {
            return changeType + "{element=" + affectedElementId
                    + (featureName != null ? ", feature=" + featureName : "")
                    + "}";
        }
    }
}
