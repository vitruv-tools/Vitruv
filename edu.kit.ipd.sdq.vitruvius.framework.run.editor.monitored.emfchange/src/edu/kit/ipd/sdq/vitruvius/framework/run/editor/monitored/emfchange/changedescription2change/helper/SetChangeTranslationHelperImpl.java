package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.FeatureChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute;

/**
 * {@link SetChangeTranslationHelperImpl} translates single-value setting {@link FeatureChange}s to
 * the corresponding {@link Change} objects. When a containment change is modified and the object is
 * among the attached rsp. detached objects of the whole set of changes, a corresponding
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject} rsp.
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject} object is created
 * instead of a corresponding simple
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference} object.
 */
class SetChangeTranslationHelperImpl extends AbstractChangeTranslationHelper {
    private static final Logger LOGGER = Logger.getLogger(SetChangeTranslationHelperImpl.class);

    @Override
    public void addReferenceChange(EObject affectedObject, FeatureChange fc, List<Change> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing reference change to " + fc.getValue() + " on " + affectedObject + " in feature "
                + fc.getFeatureName());

        LOGGER.trace("\tDetected a change on a reference of multiplicity 0..1 or 1.");
        EReference referenceFeature = (EReference) fc.getFeature();

        if (referenceFeature.isContainment() && addedObjects.contains(fc.getReferenceValue())) {
            LOGGER.trace("\tInserting an EObject-creating change for " + fc.getReferenceValue());

            if (affectedObject.eGet(referenceFeature) != null // TODO: investigate eIsSet here
                    && orphanedObjects.contains(affectedObject.eGet(referenceFeature))) {
                LOGGER.trace("\tFeature was set, prepending a corresponding delete change for "
                        + affectedObject.eGet(referenceFeature));
                EChange deleteChange = EChangeFactory.createDeleteNonRootObject(affectedObject, referenceFeature,
                        (EObject) affectedObject.eGet(referenceFeature), null);
                target.add(EMFModelChangeFactory.createEMFModelChange(deleteChange));
            }

            EChange createChange = EChangeFactory.createCreateNonRootObject(affectedObject, referenceFeature,
                    fc.getReferenceValue(), fc.getValue());
            target.add(EMFModelChangeFactory.createEMFModelChange(createChange));
        } else {
            LOGGER.trace("\tInserting an ordinary reference-updating change.");
            EChange updateChange = EChangeFactory.createUpdateEReference(affectedObject, referenceFeature,
                    fc.getValue());
            target.add(EMFModelChangeFactory.createEMFModelChange(updateChange));
        }

    }

    @Override
    public void addAttributeChange(EObject affectedObject, FeatureChange fc, List<Change> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing attribute change " + fc + " on " + affectedObject);
        EAttribute feature = (EAttribute) fc.getFeature();

        UpdateEAttribute<Object> updateChange = EChangeFactory.createUpdateEAttribute(affectedObject, feature,
                fc.getValue());
        target.add(EMFModelChangeFactory.createEMFModelChange(updateChange));
    }
}
