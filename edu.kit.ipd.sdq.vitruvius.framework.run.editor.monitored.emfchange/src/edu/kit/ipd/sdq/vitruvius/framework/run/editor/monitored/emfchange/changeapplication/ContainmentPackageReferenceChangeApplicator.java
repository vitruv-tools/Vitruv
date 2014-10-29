/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.InsertNonRootEObjectInContainmentList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.RemoveNonRootEObjectFromContainmentList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.util.ContainmentSwitch;

/**
 * {@link ContainmentPackageReferenceChangeApplicator} is a class capable of applying changes
 * stemming from the
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment} package.
 */
class ContainmentPackageReferenceChangeApplicator extends ContainmentSwitch<EObject> {
    private static final Logger LOGGER = Logger.getLogger(ContainmentPackageReferenceChangeApplicator.class);

    private final ModelTranslator modelTranslator;
    private final TransModelObjectCopier objCopier;

    private final Set<EObject> floatingRemovedObjects = new HashSet<>();
    private final Set<EObject> dummyObjects = new HashSet<>();

    private final Map<Object, EObject> objsRemovedFromContainmentList = new HashMap<>();
    private final Set<EObject> deletedObjects = new HashSet<>();

    private final Resource targetResource;

    public ContainmentPackageReferenceChangeApplicator(ApplicatorConfiguration applicationRunData) {
        this.modelTranslator = applicationRunData.getTranslator();
        this.objCopier = applicationRunData.getObjectCopier();
        this.targetResource = applicationRunData.getTarget();

        setupDeletedObjSet(applicationRunData.getSourceChanges());
        setupRemovedObjMap(targetResource, applicationRunData.getSourceChanges());
    }

    private void setupDeletedObjSet(Collection<Change> changes) {
        for (Change c : changes) {
            EMFModelChange modelC = (EMFModelChange) c;
            if (modelC.getEChange() instanceof DeleteNonRootEObjectSingle<?>) {
                DeleteNonRootEObjectSingle<?> deleteChange = (DeleteNonRootEObjectSingle<?>) modelC.getEChange();
                LOGGER.trace("Detected deleted object: " + deleteChange.getOldValue());
                deletedObjects.add(deleteChange.getOldValue());
            } else if (modelC.getEChange() instanceof DeleteNonRootEObjectInList<?>) {
                DeleteNonRootEObjectInList<?> deleteChange = (DeleteNonRootEObjectInList<?>) modelC.getEChange();
                LOGGER.trace("Detected deleted object: " + deleteChange.getOldValue());
                deletedObjects.add(deleteChange.getOldValue());
            }
        }
    }

    /**
     * Sets up <code>removedObjMap</code> and <code>removelOp2RemovedObj</code>.
     * 
     * @param target
     *            The resource containing the target model.
     * @param sourceChanges
     *            The {@link Change}s needing to be applied to the target model, relative to the
     *            source model.
     */
    private void setupRemovedObjMap(Resource target, Collection<Change> sourceChanges) {
        for (Change c : sourceChanges) {
            if (c instanceof EMFModelChange) {
                EMFModelChange modelC = (EMFModelChange) c;
                if (modelC.getEChange() instanceof RemoveFromEList<?>) {
                    RemoveFromEList<?> removeOp = (RemoveFromEList<?>) modelC.getEChange();
                    if (removeOp instanceof UpdateEReference<?>) {
                        UpdateEReference<?> updateRef = (UpdateEReference<?>) removeOp;
                        if (updateRef.getAffectedFeature().isContainment()) {
                            EObject removedObjectInTarget = target.getEObject(removeOp.getRemovedObjectURIFragment());
                            assert removedObjectInTarget != null : "Could not look up object with URI fragment "
                                    + removeOp.getRemovedObjectURIFragment() + " (old value: " + removeOp.getOldValue()
                                    + ")";
                            objsRemovedFromContainmentList.put(removeOp.getOldValue(), removedObjectInTarget);
                            LOGGER.trace("Matching for list remove operation: source=" + removeOp.getOldValue()
                                    + " with target=" + removedObjectInTarget);
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends EObject> EObject caseCreateNonRootEObjectSingle(CreateNonRootEObjectSingle<T> update) {
        super.caseCreateNonRootEObjectSingle(update);
        LOGGER.trace("Applying a Create-nonroot-single operation on feature " + update.getAffectedFeature().getName()
                + " in " + update.getNewAffectedEObject() + ", setting to " + update.getNewValue());

        EObject affectedObject = update.getNewAffectedEObject();
        EStructuralFeature feature = update.getAffectedFeature();

        System.err.println("Affected source object: " + affectedObject);
        System.err.println("Affected source object value: " + affectedObject.eGet(feature));
        System.err.println("...which should be " + update.getNewValue());

        assert update.getNewValue().eContainmentFeature() != null;

        objCopier.addCopyInTarget(update.getNewValue());
        return update;
    }

    private boolean isGettingRemovedFromAContainmentList(EObject object) {
        return objsRemovedFromContainmentList.containsKey(object);
    }

    private void replaceInTargetByDummy(EObject object) {
        // There is a matching remove operation later on. When adding it to the new
        // container, the "old" container gets modified, and the matching remove
        // operation
        // will cause the indices of remove operations on that list to be wrong.
        // Thus, insert a dummy copy there which can be removed regularly later on.

        EObject correspondingObj = objsRemovedFromContainmentList.get(object);

        EObject correspContainer = correspondingObj.eContainer();
        LOGGER.trace("\tInserting dummy object in " + correspContainer);
        @SuppressWarnings("unchecked")
        EList<EObject> containingList = (EList<EObject>) correspContainer.eGet(correspondingObj.eContainingFeature());
        int correspObjIdx = containingList.indexOf(correspondingObj);

        // The copier should not copy referenced items.
        EcoreUtil.Copier copier = new EcoreUtil.Copier(false, false);
        EObject dummy = copier.copy(correspondingObj);

        containingList.set(correspObjIdx, dummy);
        dummyObjects.add(dummy);

        LOGGER.trace("\tUsing a dummy object to preserve indices: " + dummy);
    }

    @Override
    public <T extends EObject> EObject caseReplaceNonRootEObjectSingle(ReplaceNonRootEObjectSingle<T> update) {
        super.caseReplaceNonRootEObjectSingle(update);

        LOGGER.trace("Applying a Replace-nonroot-single operation on feature " + update.getAffectedFeature().getName()
                + " in " + update.getNewAffectedEObject() + ", setting to " + update.getNewValue());

        EObject newVal = update.getNewValue();
        if (floatingRemovedObjects.contains(newVal)) {
            floatingRemovedObjects.remove(newVal);
        } else if (isGettingRemovedFromAContainmentList(newVal)) {
            replaceInTargetByDummy(newVal);
        }

        Util.setFeature(update.getNewAffectedEObject(), update.getAffectedFeature().getName(), update.getNewValue());
        return update;
    }

    @Override
    public <T extends EObject> EObject caseDeleteNonRootEObjectSingle(DeleteNonRootEObjectSingle<T> update) {
        super.caseDeleteNonRootEObjectSingle(update);

        LOGGER.trace("Applying a Delete-nonroot-single operation on feature " + update.getAffectedFeature().getName()
                + " in " + update.getNewAffectedEObject() + ", deleting " + update.getOldValue());

        if (deletedObjects.contains(update.getNewAffectedEObject())) {
            LOGGER.trace("\tDropping the operation since the affected object itself gets deleted later on.");
            return update;
        }

        EObject correspContainment = modelTranslator.lookupInTarget(update.getNewAffectedEObject());
        Util.setFeature(correspContainment, update.getAffectedFeature().getName(), null);
        return update;
    }

    private void addToContainmentList(EObject affectedEObject, EReference feature, EObject newValue, int index) {
        @SuppressWarnings("unchecked")
        EList<EObject> featureList = (EList<EObject>) modelTranslator.getFeatureValue(feature, affectedEObject);
        featureList.add(index, newValue);
    }

    @Override
    public <T extends EObject> EObject caseCreateNonRootEObjectInList(CreateNonRootEObjectInList<T> update) {
        super.caseCreateNonRootEObjectInList(update);

        LOGGER.trace("Applying a Create-nonroot-many operation on feature " + update.getAffectedFeature().getName()
                + " in " + update.getNewAffectedEObject() + ", creating " + update.getNewValue());

        objCopier.addCopyInTarget(update.getNewValue(), update.getIndex());

        return update;
    }

    @Override
    public <T extends EObject> EObject caseInsertNonRootEObjectInContainmentList(
            InsertNonRootEObjectInContainmentList<T> update) {
        super.caseInsertNonRootEObjectInContainmentList(update);

        LOGGER.trace("Applying an Insert-nonroot-many operation on feature " + update.getAffectedFeature().getName()
                + " in " + update.getNewAffectedEObject() + ", inserting " + update.getNewValue());

        if (floatingRemovedObjects.contains(update.getNewValue())) {
            floatingRemovedObjects.remove(update.getNewValue());
        } else if (isGettingRemovedFromAContainmentList(update.getNewValue())) {
            replaceInTargetByDummy(update.getNewValue());
        }

        EObject correspondingContainment = modelTranslator.lookupInTarget(update.getNewAffectedEObject());
        EObject correspondingNewValue = objsRemovedFromContainmentList.get(update.getNewValue());

        addToContainmentList(correspondingContainment, update.getAffectedFeature(), correspondingNewValue,
                update.getIndex());

        return update;
    }

    @Override
    public <T extends EObject> EObject caseDeleteNonRootEObjectInList(DeleteNonRootEObjectInList<T> update) {
        super.caseDeleteNonRootEObjectInList(update);

        LOGGER.trace("Applying a Delete-nonroot-many operation on feature " + update.getAffectedFeature().getName()
                + " in " + update.getNewAffectedEObject() + ", removing " + update.getOldValue());

        if (deletedObjects.contains(update.getNewAffectedEObject())) {
            LOGGER.trace("\tDropping the operation since the affected object itself gets deleted later on.");
            return update;
        }

        // The model translator does not work on deleted objects, since they are no longer part of
        // the source model. Thus, they are looked up by their URI fragment.
        EObject correspondingObj = targetResource.getEObject(update.getRemovedObjectURIFragment());
        assert correspondingObj != null;

        EObject container = correspondingObj.eContainer();
        EList<?> containingList = (EList<?>) container.eGet(correspondingObj.eContainingFeature());
        containingList.remove(update.getIndex());

        return update;
    }

    @Override
    public <T extends EObject> EObject caseRemoveNonRootEObjectFromContainmentList(
            RemoveNonRootEObjectFromContainmentList<T> update) {
        super.caseRemoveNonRootEObjectFromContainmentList(update);

        LOGGER.trace("Applying a Remove-nonroot-many operation on feature " + update.getAffectedFeature().getName()
                + " in " + update.getNewAffectedEObject() + ", removing " + update.getOldValue());

        EObject correspondingObj = targetResource.getEObject(update.getRemovedObjectURIFragment());
        LOGGER.trace("\tCorresponding object to be removed: " + correspondingObj);

        EObject container = correspondingObj.eContainer();
        EList<?> containingList = (EList<?>) container.eGet(correspondingObj.eContainingFeature());
        containingList.remove(update.getIndex());

        if (!dummyObjects.remove(update.getOldValue())) {
            LOGGER.trace("\tAdding the object to the floating removed objects");
            floatingRemovedObjects.add(update.getOldValue());
        } else {
            LOGGER.trace("\tNot adding the object to the floating removed objects.");
        }

        return update;
    }

    @Override
    public EObject defaultCase(EObject object) {
        super.defaultCase(object);
        throw new UnsupportedOperationException("Cannot apply changes represented by " + object.eClass().getName()
                + ": Unimplemented");
    }

}
