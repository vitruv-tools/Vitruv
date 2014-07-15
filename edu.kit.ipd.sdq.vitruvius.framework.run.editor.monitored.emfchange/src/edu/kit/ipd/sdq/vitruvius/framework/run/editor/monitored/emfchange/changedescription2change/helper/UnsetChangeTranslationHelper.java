package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.change.FeatureChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UnsetEFeature;

/**
 * {@link UnsetChangeTranslationHelper} contains feature-unset operations to the corresponding
 * {@link Change} objects.
 */
public class UnsetChangeTranslationHelper implements IChangeTranslationHelper {

    private static final Logger LOGGER = Logger.getLogger(UnsetChangeTranslationHelper.class);

    private boolean isRemoveAllInListChange(EObject affectedObject, FeatureChange fc) {
        return fc.getFeature().isMany();
    }

    private void addReferenceRemoveOperations(EObject affectedObject, FeatureChange fc, List<EObject> orphanedObjects,
            List<Change> target) {
        EReference referenceFeature = (EReference) fc.getFeature();
        EList<?> featureList = (EList<?>) affectedObject.eGet(referenceFeature);

        for (Object remObj : featureList) {
            RemoveFromEList<EReference> indexChange = EChangeFactory.createRemoveFromEList(affectedObject,
                    (EReference) fc.getFeature(), (EObject) remObj, 0);
            if (referenceFeature.isContainment() && orphanedObjects.contains(remObj)) {
                LOGGER.trace("\tCreating a delete operation for " + remObj);
                DeleteNonRootEObject<Object> deleteChange = EChangeFactory.createDeleteNonRootObject(affectedObject,
                        referenceFeature, (EObject) remObj, null);
                deleteChange.setListUpdate(indexChange);
                target.add(EMFModelChangeFactory.createEMFModelChange(deleteChange));
            } else {
                target.add(EMFModelChangeFactory.createEMFModelChange(indexChange));
            }
        }
    }

    @Override
    public void addChange(EObject affectedObject, FeatureChange fc, List<Change> target, List<EObject> addedObjects,
            List<EObject> orphanedObjects) {

        if (isRemoveAllInListChange(affectedObject, fc)) {
            // Removed all elements in a list, ChangeRecorder handles this as "set empty list" but
            // discrete remove operations are needed.
            LOGGER.trace("\tDetected a list-clearing reference change.");
            if (fc.getFeature() instanceof EReference) {
                addReferenceRemoveOperations(affectedObject, fc, orphanedObjects, target);
            } else {
                EAttribute feature = (EAttribute) fc.getFeature();
                EList<?> attrFeature = (EList<?>) affectedObject.eGet(fc.getFeature());
                for (int i = 0; i < attrFeature.size(); i++) {
                    RemoveFromEList<EAttribute> removeChange = EChangeFactory.createRemoveFromEList(affectedObject,
                            feature, attrFeature.get(i), 0);
                    target.add(EMFModelChangeFactory.createEMFModelChange(removeChange));
                }
            }
        } else if (fc.getFeature() instanceof EReference) {
            EReference ref = (EReference) fc.getFeature();
            EObject oldValue = (EObject) affectedObject.eGet(ref);
            if (ref.isContainment() && orphanedObjects.contains(oldValue)) {
                DeleteNonRootEObject<Object> deleteChange = EChangeFactory.createDeleteNonRootObject(affectedObject,
                        ref, oldValue, fc.getValue());
                target.add(EMFModelChangeFactory.createEMFModelChange(deleteChange));
            }
        }

        // TODO: what about unset on containment reference?
        UnsetEFeature<EStructuralFeature> unsetFeature = ChangeFactory.eINSTANCE.createUnsetEFeature();
        unsetFeature.setAffectedEObject(affectedObject);
        unsetFeature.setAffectedFeature(fc.getFeature());
        target.add(new EMFModelChange(unsetFeature));
    }

}
