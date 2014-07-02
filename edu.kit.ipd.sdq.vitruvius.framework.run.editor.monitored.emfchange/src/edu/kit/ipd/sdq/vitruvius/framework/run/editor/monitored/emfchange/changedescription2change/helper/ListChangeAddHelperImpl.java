package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.InsertInEList;

/**
 * {@link ListChangeAddHelperImpl} translates additions to many-multiplicity features to the
 * corresponding {@link Change} objects. If a containment reference is updated for an object among
 * the attached objects of the whole set of changes, a {@link CreateNonRootEObject} change is
 * created containing an {@link InsertInEList} change; otherwise, just an {@link InsertInEList}
 * change is created.
 */
class ListChangeAddHelperImpl implements IListChangeTranslationHelper {
    private static final Logger LOGGER = Logger.getLogger(ListChangeAddHelperImpl.class);

    @Override
    public void addReferenceChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<Change> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing reference add list change " + lc);

        for (EObject obj : lc.getReferenceValues()) {
            EReference referenceFeature = (EReference) fc.getFeature();
            InsertInEList<EReference> insertChange = EChangeFactory.createInsertInEList(affectedObject,
                    referenceFeature, obj, lc.getIndex());

            if (referenceFeature.isContainment() && addedObjects.contains(obj)) {
                LOGGER.trace("\tFeature " + referenceFeature.getName() + " is containment and the added object is new.");
                CreateNonRootEObject<EObject> createChange = EChangeFactory.createCreateNonRootObject(affectedObject,
                        referenceFeature, obj, fc.getReferenceValue());
                createChange.setListUpdate(insertChange);
                target.add(EMFModelChangeFactory.createEMFModelChange(createChange));
            } else {
                target.add(EMFModelChangeFactory.createEMFModelChange(insertChange));
            }
        }
    }

    @Override
    public void addAttributeChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<Change> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing attribute add list change " + lc);
        EAttribute attrFeature = (EAttribute) fc.getFeature();

        for (Object obj : lc.getValues()) {
            InsertInEList<EAttribute> insertChange = EChangeFactory.createInsertInEList(affectedObject, attrFeature,
                    obj, lc.getIndex());
            target.add(EMFModelChangeFactory.createEMFModelChange(insertChange));
        }
    }

}
