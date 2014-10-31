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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;

/**
 * A factory for {@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange} objects.
 */
final class InitializeEChange {

    private InitializeEChange() {
    }

    protected static <T extends EObject> void setupUpdateEReference(UpdateEReference<T> update, EObject affectedObject,
            EReference affectedFeature) {
        update.setOldAffectedEObject(affectedObject);
        update.setNewAffectedEObject(affectedObject);
        update.setAffectedFeature(affectedFeature);
    }

    protected static <T extends Object> void setupUpdateEAttribute(UpdateEAttribute<T> update, EObject affectedObject,
            EAttribute affectedFeature) {
        update.setOldAffectedEObject(affectedObject);
        update.setNewAffectedEObject(affectedObject);
        update.setAffectedFeature(affectedFeature);
    }

    protected static <T extends Object> void setupRemoveFromEList(RemoveFromEList<T> update, T oldValue, int index) {
        update.setOldValue(oldValue);
        update.setIndex(index);
        if (oldValue instanceof EObject) {
            EObject eOldValue = (EObject) oldValue;

            assert eOldValue.eResource() != null;
            update.setRemovedObjectURIFragment(eOldValue.eResource().getURIFragment(eOldValue));
        }
    }

    protected static <T extends Object> void setupInsertInEList(InsertInEList<T> update, T newValue, int index) {
        update.setNewValue(newValue);
        update.setIndex(index);
    }

    protected static <T extends EObject> void setupUpdateSingleValuedNonContainmentEReference(
            UpdateSingleValuedNonContainmentEReference<T> update, T oldValue, T newValue) {
        update.setOldValue(oldValue);
        update.setNewValue(newValue);
    }

    protected static <T extends Object> void setupUpdateSingleValuedEAttribute(UpdateSingleValuedEAttribute<T> update,
            T oldValue, T newValue) {
        update.setOldValue(oldValue);
        update.setNewValue(newValue);
    }

    protected static <T extends EObject> void setupCreateNonRootEObjectSingle(CreateNonRootEObjectSingle<T> update,
            T newValue) {
        update.setNewValue(newValue);
    }
}
