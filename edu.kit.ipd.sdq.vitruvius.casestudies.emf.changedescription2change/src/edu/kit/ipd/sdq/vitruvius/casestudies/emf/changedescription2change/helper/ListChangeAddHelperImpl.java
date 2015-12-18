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

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.InsertEAttributeValue;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.InsertNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.InsertNonRootEObjectInContainmentList;

/**
 * {@link ListChangeAddHelperImpl} translates additions to many-multiplicity features to the
 * corresponding {@link EChange} objects.
 */
class ListChangeAddHelperImpl implements IListChangeTranslationHelper {
    private static final Logger LOGGER = Logger.getLogger(ListChangeAddHelperImpl.class);

    private UpdateEReference<EObject> createContainmentCreateChange(EObject addedObject, EReference feature, int index) {
        LOGGER.trace("\tFeature " + feature.getName() + " is containment and the added object is new.");
        CreateNonRootEObjectInList<EObject> createChange = ContainmentFactory.eINSTANCE
                .createCreateNonRootEObjectInList();
        createChange.setNewValue(addedObject);
        createChange.setIndex(index);
        return createChange;
    }

    private UpdateEReference<EObject> createContainmentInsertChange(EObject addedObject, EReference feature, int index) {
        LOGGER.trace("\tFeature " + feature.getName() + " is containment and the added object is not new.");
        InsertNonRootEObjectInContainmentList<EObject> insertChange = ContainmentFactory.eINSTANCE
                .createInsertNonRootEObjectInContainmentList();
        insertChange.setNewValue(addedObject);
        insertChange.setIndex(index);
        return insertChange;
    }

    private UpdateEReference<EObject> createNonContainmentChange(EObject addedObject, EReference feature, int index) {
        InsertNonContainmentEReference<EObject> insertChange = ReferenceFactory.eINSTANCE
                .createInsertNonContainmentEReference();
        insertChange.setNewValue(addedObject);
        insertChange.setIndex(index);
        return insertChange;
    }

    private UpdateEReference<EObject> createAddChange(Collection<EObject> createdObjects, EObject addedObject,
            EReference feature, int index) {
        if (feature.isContainment()) {
            if (createdObjects.contains(addedObject)) {
                // The object has been created.
                return createContainmentCreateChange(addedObject, feature, index);
            } else {
                // The object just gets moved between containments.
                return createContainmentInsertChange(addedObject, feature, index);
            }
        } else {
            return createNonContainmentChange(addedObject, feature, index);
        }
    }

    @Override
    public void addReferenceChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing reference add list change " + lc);
        LOGGER.trace("\tReference values: " + lc.getReferenceValues());

        int i = lc.getIndex();
        for (EObject obj : lc.getReferenceValues()) {
            LOGGER.trace("\tProcessing: " + obj);
            UpdateEReference<EObject> update = createAddChange(addedObjects, obj, (EReference) fc.getFeature(), i);
            InitializeEChange.setupUpdateEFeature(update, affectedObject, (EReference) fc.getFeature());
            target.add(update);
            i++;
        }
    }

    @Override
    public void addAttributeChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing attribute add list change " + lc);
        EAttribute attrFeature = (EAttribute) fc.getFeature();

        for (Object obj : lc.getValues()) {
            InsertEAttributeValue<Object> insertChange = AttributeFactory.eINSTANCE.createInsertEAttributeValue();
            insertChange.setIndex(lc.getIndex());
            insertChange.setNewValue(obj);
            InitializeEChange.setupUpdateEFeature(insertChange, affectedObject, attrFeature);
            target.add(insertChange);
        }
    }

}
