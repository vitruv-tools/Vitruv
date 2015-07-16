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

package edu.kit.ipd.sdq.vitruvius.casestudies.emf.changedescription2change.helper;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.InsertNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.RemoveNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.InsertNonRootEObjectInContainmentList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.RemoveNonRootEObjectFromContainmentList;

/**
 * {@link ListChangeMoveHelperImpl} translates moves within many-multiplicity features to the
 * corresponding {@link EChange} operations.
 */
class ListChangeMoveHelperImpl implements IListChangeTranslationHelper {
    private static final Logger LOGGER = Logger.getLogger(ListChangeMoveHelperImpl.class);

    private EChange createContainmentRemoveChange(EObject movedObject, EObject affectedObject, EReference feature,
            int index) {
        RemoveNonRootEObjectFromContainmentList<EObject> removeChange = ContainmentFactory.eINSTANCE
                .createRemoveNonRootEObjectFromContainmentList();
        InitializeEChange.setupUpdateEReference(removeChange, affectedObject, feature);
        InitializeEChange.setupRemoveFromEList(removeChange, movedObject, index);
        return removeChange;
    }

    private EChange createContainmentInsertChange(EObject movedObject, EObject affectedObject, EReference feature,
            int index) {
        InsertNonRootEObjectInContainmentList<EObject> insertChange = ContainmentFactory.eINSTANCE
                .createInsertNonRootEObjectInContainmentList();
        InitializeEChange.setupUpdateEReference(insertChange, affectedObject, feature);
        InitializeEChange.setupInsertInEList(insertChange, movedObject, index);
        return insertChange;
    }

    private EChange createNonContainmentRemoveChange(EObject movedObject, EObject affectedObject, EReference feature,
            int index) {
        RemoveNonContainmentEReference<EObject> removeChange = ReferenceFactory.eINSTANCE
                .createRemoveNonContainmentEReference();
        InitializeEChange.setupUpdateEReference(removeChange, affectedObject, feature);
        InitializeEChange.setupRemoveFromEList(removeChange, movedObject, index);
        return removeChange;
    }

    private EChange createNonContainmentInsertChange(EObject movedObject, EObject affectedObject, EReference feature,
            int index) {
        InsertNonContainmentEReference<EObject> insertChange = ReferenceFactory.eINSTANCE
                .createInsertNonContainmentEReference();
        InitializeEChange.setupUpdateEReference(insertChange, affectedObject, feature);
        InitializeEChange.setupInsertInEList(insertChange, movedObject, index);
        return insertChange;
    }

    private EChange createInsertChange(EObject movedObject, EObject affectedObject, EReference feature, int index) {
        if (feature.isContainment()) {
            return createContainmentInsertChange(movedObject, affectedObject, feature, index);
        } else {
            return createNonContainmentInsertChange(movedObject, affectedObject, feature, index);
        }
    }

    private EChange createRemoveChange(EObject movedObject, EObject affectedObject, EReference feature, int index) {
        if (feature.isContainment()) {
            return createContainmentRemoveChange(movedObject, affectedObject, feature, index);
        } else {
            return createNonContainmentRemoveChange(movedObject, affectedObject, feature, index);
        }
    }

    @Override
    public void addReferenceChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing move list change " + lc);

        EList<?> affectedList = (EList<?>) affectedObject.eGet(fc.getFeature());
        EReference affectedReference = (EReference) fc.getFeature();
        EObject eMovedObj = (EObject) affectedList.get(lc.getMoveToIndex());

        EChange removeChange = createRemoveChange(eMovedObj, affectedObject, affectedReference, lc.getMoveToIndex());
        EChange insertChange = createInsertChange(eMovedObj, affectedObject, affectedReference, lc.getIndex());

        target.add(removeChange);
        target.add(insertChange);
    }

    @Override
    public void addAttributeChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        throw new UnsupportedOperationException("Feature not implemented");
    }

}
