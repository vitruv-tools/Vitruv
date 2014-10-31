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

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.RemoveEAttributeValue;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.RemoveNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.RemoveNonRootEObjectFromContainmentList;

/**
 * {@link ListChangeRemoveHelperImpl} translates deletions from many-multiplicity features to the
 * corresponding {@link EChange} objects.
 */
class ListChangeRemoveHelperImpl implements IListChangeTranslationHelper {
    private static final Logger LOGGER = Logger.getLogger(ListChangeRemoveHelperImpl.class);

    private List<Object> getDeletedObjects(EObject affectedObject, EReference feature, ListChange lc) {
        @SuppressWarnings("unchecked")
        EList<Object> originalList = (EList<Object>) affectedObject.eGet(feature);
        List<Object> deletedObjects = new ArrayList<Object>(originalList);

        lc.applyAndReverse(originalList);
        deletedObjects.removeAll(originalList);
        lc.applyAndReverse(originalList);

        return deletedObjects;
    }

    private UpdateEReference<EObject> createContainmentDeleteChange(EObject removedObject, EReference feature, int index) {
        DeleteNonRootEObjectInList<EObject> removeChange = ContainmentFactory.eINSTANCE
                .createDeleteNonRootEObjectInList();
        InitializeEChange.setupRemoveFromEList(removeChange, removedObject, index);
        return removeChange;
    }

    private UpdateEReference<EObject> createContainmentRemoveChange(EObject removedObject, EReference feature, int index) {
        RemoveNonRootEObjectFromContainmentList<EObject> removeChange = ContainmentFactory.eINSTANCE
                .createRemoveNonRootEObjectFromContainmentList();
        InitializeEChange.setupRemoveFromEList(removeChange, removedObject, index);
        return removeChange;
    }

    private UpdateEReference<EObject> createNonContainmentRemoveChange(EObject removedObject, EReference feature,
            int index) {
        RemoveNonContainmentEReference<EObject> removeChange = ReferenceFactory.eINSTANCE
                .createRemoveNonContainmentEReference();
        InitializeEChange.setupRemoveFromEList(removeChange, removedObject, index);
        return removeChange;
    }

    private UpdateEReference<EObject> createRemoveChange(Collection<EObject> orphanedObjects, EObject removedObject,
            EReference feature, int index) {
        if (feature.isContainment()) {
            if (orphanedObjects.contains(removedObject)) {
                // The object has been deleted.
                return createContainmentDeleteChange(removedObject, feature, index);
            } else {
                // The object has just been moved between containments.
                return createContainmentRemoveChange(removedObject, feature, index);
            }
        } else {
            return createNonContainmentRemoveChange(removedObject, feature, index);
        }
    }

    @Override
    public void addReferenceChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing reference remove list change " + lc);

        EReference feature = (EReference) fc.getFeature();
        List<Object> deletedObjects = getDeletedObjects(affectedObject, feature, lc);

        for (Object obj : deletedObjects) {
            EObject eObj = (EObject) obj;
            UpdateEReference<EObject> update = createRemoveChange(orphanedObjects, eObj, feature, lc.getIndex());
            InitializeEChange.setupUpdateEReference(update, affectedObject, feature);
            target.add(update);
        }
    }

    @Override
    public void addAttributeChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing attribute remove list change " + lc);
        LOGGER.trace("\t" + lc.getValues().size() + " values");
        EAttribute attrFeature = (EAttribute) fc.getFeature();

        if (lc.getValues().isEmpty()) {
            RemoveEAttributeValue<Object> update = AttributeFactory.eINSTANCE.createRemoveEAttributeValue();
            InitializeEChange.setupUpdateEAttribute(update, affectedObject, attrFeature);
            update.setIndex(lc.getIndex());
            target.add(update);
        } else {
            for (Object o : lc.getValues()) {
                RemoveEAttributeValue<Object> update = AttributeFactory.eINSTANCE.createRemoveEAttributeValue();
                InitializeEChange.setupUpdateEAttribute(update, affectedObject, attrFeature);
                update.setIndex(lc.getIndex());
                update.setOldValue(o);
                target.add(update);
            }
        }
    }

}
