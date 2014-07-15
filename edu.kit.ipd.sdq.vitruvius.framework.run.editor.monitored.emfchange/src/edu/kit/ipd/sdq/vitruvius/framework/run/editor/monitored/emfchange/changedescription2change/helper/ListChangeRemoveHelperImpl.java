package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.RemoveFromEList;

/**
 * {@link ListChangeRemoveHelperImpl} translates deletions from many-multiplicity features to the
 * corresponding {@link Change} objects. If a containment reference is updated for an object among
 * the detached objects of the whole set of changes, a {@link DeleteNonRootEObject} change is
 * created containing an {@link RemoveFromEList} change; otherwise, just an {@link RemoveFromEList}
 * change is created.
 */
class ListChangeRemoveHelperImpl implements IListChangeTranslationHelper {
    private static final Logger LOGGER = Logger.getLogger(ListChangeRemoveHelperImpl.class);

    @Override
    public void addReferenceChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<Change> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing reference remove list change " + lc);

        @SuppressWarnings("unchecked")
        EList<Object> originalList = (EList<Object>) affectedObject.eGet(fc.getFeature());
        List<Object> deletedObjects = new ArrayList<Object>(originalList);

        lc.applyAndReverse(originalList); // TODO: read lc source code, is this okay?
        deletedObjects.removeAll(originalList);
        lc.applyAndReverse(originalList);

        for (Object obj : deletedObjects) {
            EObject eObj = (EObject) obj;

            EReference referenceFeature = (EReference) fc.getFeature();
            RemoveFromEList<EReference> listChange = EChangeFactory.createRemoveFromEList(affectedObject,
                    referenceFeature, eObj, lc.getIndex());

            if (referenceFeature.isContainment() && orphanedObjects.contains(obj)) {
                DeleteNonRootEObject<EObject> removeChange = EChangeFactory.createDeleteNonRootObject(affectedObject,
                        referenceFeature, eObj, fc.getReferenceValue());
                removeChange.setListUpdate(listChange);
                target.add(EMFModelChangeFactory.createEMFModelChange(removeChange));
            } else {
                target.add(EMFModelChangeFactory.createEMFModelChange(listChange));
            }
        }
    }

    @Override
    public void addAttributeChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<Change> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing attribute remove list change " + lc);
        LOGGER.trace("\t" + lc.getValues().size() + " values");
        EAttribute attrFeature = (EAttribute) fc.getFeature();

        RemoveFromEList<EAttribute> removeChange = EChangeFactory.createRemoveFromEList(affectedObject, attrFeature,
                null, lc.getIndex());
        target.add(EMFModelChangeFactory.createEMFModelChange(removeChange));
    }

}
