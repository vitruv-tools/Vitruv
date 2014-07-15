package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

/**
 * {@link ListChangeMoveHelperImpl} translates moves within many-multiplicity features to the
 * corresponding {@link Change} operations.
 */
class ListChangeMoveHelperImpl implements IListChangeTranslationHelper {
    private static final Logger LOGGER = Logger.getLogger(ListChangeMoveHelperImpl.class);

    @Override
    public void addReferenceChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<Change> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing move list change " + lc);
        EList<?> affectedList = (EList<?>) affectedObject.eGet(fc.getFeature());
        EReference affectedReference = (EReference) fc.getFeature();
        EObject eMovedObj = (EObject) affectedList.get(lc.getMoveToIndex());

        EChange removeChange = EChangeFactory.createRemoveFromEList(affectedObject, affectedReference, eMovedObj,
                lc.getMoveToIndex());
        EChange insertChange = EChangeFactory.createInsertInEList(affectedObject, affectedReference, eMovedObj,
                lc.getIndex());

        target.add(EMFModelChangeFactory.createEMFModelChange(removeChange));
        target.add(EMFModelChangeFactory.createEMFModelChange(insertChange));
    }

    @Override
    public void addAttributeChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<Change> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.error("Unimplemented");
    }

}
