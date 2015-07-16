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
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.FeatureChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle;

/**
 * {@link SetChangeTranslationHelperImpl} translates single-value setting {@link FeatureChange}s to
 * the corresponding {@link EChange} objects. When a containment change is modified and the object
 * is among the attached rsp. detached objects of the whole set of changes, a corresponding
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject} rsp.
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject} object is created
 * instead of a corresponding simple
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference} object.
 */
class SetChangeTranslationHelperImpl extends AbstractChangeTranslationHelper {
    private static final Logger LOGGER = Logger.getLogger(SetChangeTranslationHelperImpl.class);

    private UpdateEReference<EObject> createContainmentDeleteChange(EObject affectedObject, EReference feature) {
        DeleteNonRootEObjectSingle<EObject> deleteChange = ContainmentFactory.eINSTANCE
                .createDeleteNonRootEObjectSingle();
        InitializeEChange.setupUpdateEReference(deleteChange, affectedObject, feature);
        deleteChange.setOldValue((EObject) affectedObject.eGet(feature));

        return deleteChange;
    }

    private UpdateEReference<EObject> createContainmentCreateChange(EObject newValue, EObject affectedObject,
            EReference feature) {
        CreateNonRootEObjectSingle<EObject> createChange = ContainmentFactory.eINSTANCE
                .createCreateNonRootEObjectSingle();
        InitializeEChange.setupUpdateEReference(createChange, affectedObject, feature);
        createChange.setNewValue(newValue);
        return createChange;
    }

    private UpdateEReference<EObject> createNonContainmentInsertSingleValuedUpdate(EObject newValue,
            EObject affectedObject, EReference feature) {
        UpdateSingleValuedNonContainmentEReference<EObject> update = ReferenceFactory.eINSTANCE
                .createUpdateSingleValuedNonContainmentEReference();
        InitializeEChange.setupUpdateEReference(update, affectedObject, feature);
        update.setNewValue(newValue);
        return update;
    }

    @Override
    public void addReferenceChange(EObject affectedObject, FeatureChange fc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing reference change to " + fc.getValue() + " on " + affectedObject + " in feature "
                + fc.getFeatureName());

        EReference feature = (EReference) fc.getFeature();

        if (feature.isContainment() && addedObjects.contains(fc.getReferenceValue())) {
            LOGGER.trace("\tInserting an EObject-creating change for " + fc.getReferenceValue());

            if (affectedObject.eGet(feature) != null && orphanedObjects.contains(affectedObject.eGet(feature))) {
                LOGGER.trace("\tFeature was set, prepending a corresponding delete change for "
                        + affectedObject.eGet(feature));
                UpdateEReference<EObject> deleteChange = createContainmentDeleteChange(affectedObject, feature);
                target.add(deleteChange);
            }

            UpdateEReference<EObject> createChange = createContainmentCreateChange(fc.getReferenceValue(),
                    affectedObject, feature);
            target.add(createChange);
        } else {
            LOGGER.trace("\tInserting an ordinary reference-updating change.");
            UpdateEReference<EObject> update = createNonContainmentInsertSingleValuedUpdate(fc.getReferenceValue(),
                    affectedObject, feature);
            target.add(update);
        }

    }

    @Override
    public void addAttributeChange(EObject affectedObject, FeatureChange fc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing attribute change " + fc + " on " + affectedObject);
        EAttribute feature = (EAttribute) fc.getFeature();

        UpdateSingleValuedEAttribute<Object> updateChange = AttributeFactory.eINSTANCE
                .createUpdateSingleValuedEAttribute();
        InitializeEChange.setupUpdateEAttribute(updateChange, affectedObject, feature);
        updateChange.setNewValue(fc.getValue());
        target.add(updateChange);
    }
}
