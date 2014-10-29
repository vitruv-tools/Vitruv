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

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.change.FeatureChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeatureFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.RemoveEAttributeValue;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.RemoveNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle;

/**
 * {@link UnsetChangeTranslationHelper} contains feature-unset operations to the corresponding
 * {@link EChange} objects.
 */
class UnsetChangeTranslationHelper implements IChangeTranslationHelper {

    private static final Logger LOGGER = Logger.getLogger(UnsetChangeTranslationHelper.class);

    private boolean isRemoveAllInListChange(EObject affectedObject, FeatureChange fc) {
        return fc.getFeature().isMany();
    }

    private void addReferenceRemoveOperations(EObject affectedObject, FeatureChange fc, List<EObject> orphanedObjects,
            List<EChange> target) {
        EReference referenceFeature = (EReference) fc.getFeature();
        EList<?> featureList = (EList<?>) affectedObject.eGet(referenceFeature);

        for (Object remObj : featureList) {
            if (referenceFeature.isContainment() && orphanedObjects.contains(remObj)) {
                LOGGER.trace("\tCreating a delete operation for " + remObj);
                DeleteNonRootEObjectInList<EObject> deleteChange = ContainmentFactory.eINSTANCE
                        .createDeleteNonRootEObjectInList();
                InitializeEChange.setupUpdateEReference(deleteChange, affectedObject, referenceFeature);
                InitializeEChange.setupRemoveFromEList(deleteChange, (EObject) remObj, 0);
                target.add(deleteChange);
            } else {
                RemoveNonContainmentEReference<EObject> deleteChange = ReferenceFactory.eINSTANCE
                        .createRemoveNonContainmentEReference();
                InitializeEChange.setupUpdateEReference(deleteChange, affectedObject, referenceFeature);
                deleteChange.setOldValue((EObject) remObj);
                deleteChange.setIndex(0);
                target.add(deleteChange);
            }
        }
    }

    @Override
    public void addChange(EObject affectedObject, FeatureChange fc, List<EChange> target, List<EObject> addedObjects,
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
                    RemoveEAttributeValue<Object> removeChange = AttributeFactory.eINSTANCE
                            .createRemoveEAttributeValue();
                    InitializeEChange.setupUpdateEAttribute(removeChange, affectedObject, feature);
                    removeChange.setOldValue(attrFeature.get(i));
                    removeChange.setIndex(0);
                    target.add(removeChange);
                }
            }
        } else if (fc.getFeature() instanceof EReference) {
            EReference ref = (EReference) fc.getFeature();
            EObject oldValue = (EObject) affectedObject.eGet(ref);
            if (ref.isContainment() && orphanedObjects.contains(oldValue)) {
                DeleteNonRootEObjectSingle<EObject> deleteChange = ContainmentFactory.eINSTANCE
                        .createDeleteNonRootEObjectSingle();
                InitializeEChange.setupUpdateEReference(deleteChange, affectedObject, ref);
                deleteChange.setOldValue(oldValue);
                target.add(deleteChange);
            }
        }

        if (fc.getFeature() instanceof EReference) {
            EReference feature = (EReference) fc.getFeature();
            if (feature.isContainment()) {
                UnsetContainmentEReference<EStructuralFeature> unsetFeature = FeatureFactory.eINSTANCE
                        .createUnsetContainmentEReference();
                unsetFeature.setAffectedFeature(feature);
                unsetFeature.setOldAffectedEObject(affectedObject);
                unsetFeature.setNewAffectedEObject(affectedObject);
                target.add(unsetFeature);
            } else {
                UnsetNonContainmentEReference<EStructuralFeature> unsetFeature = FeatureFactory.eINSTANCE
                        .createUnsetNonContainmentEReference();
                unsetFeature.setAffectedFeature(feature);
                unsetFeature.setOldAffectedEObject(affectedObject);
                unsetFeature.setNewAffectedEObject(affectedObject);
                target.add(unsetFeature);
            }
        } else if (fc.getFeature() instanceof EAttribute) {
            UnsetEAttribute<EStructuralFeature> unsetFeature = FeatureFactory.eINSTANCE.createUnsetEAttribute();
            unsetFeature.setAffectedFeature((EAttribute) fc.getFeature());
            unsetFeature.setOldAffectedEObject(affectedObject);
            unsetFeature.setNewAffectedEObject(affectedObject);
            target.add(unsetFeature);
        }

    }

}
