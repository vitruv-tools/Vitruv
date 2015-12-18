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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle;

/**
 * {@link ShadowDeletionChangeHelper} determines whether a given deleted object causes implicit
 * deletions and generates a corresponding list of changes.
 */
public final class ShadowDeletionChangeHelper {
    private static final Logger LOGGER = Logger.getLogger(ShadowDeletionChangeHelper.class);

    private final List<EObject> additionalDetachedObjects = new ArrayList<>();
    private final Collection<EObject> detachedObjects;

    /**
     * Creates a new {@link ShadowDeletionChangeHelper} object.
     * 
     * @param detachedObjects
     *            The set of objects currently known to be deleted.
     */
    public ShadowDeletionChangeHelper(Collection<EObject> detachedObjects) {
        this.detachedObjects = detachedObjects;
    }

    /**
     * Get a list of {@link EChange} objects reflecting the implicit delete operations caused by the
     * deletion of <code>object</code>.
     * 
     * @param object
     *            An EMF object.
     * @return A list of {@link EChange} objects reflecting the implicit delete operations caused by
     *         the deletion of <code>object</code>. The list is ordered such that no deleted object
     *         is referenced after its deletion.
     */
    public List<EChange> getShadowResolvingChanges(EObject object) {
        LOGGER.trace("Adding shadowed delete operations for " + object);
        List<EChange> result = new ArrayList<>();
        addShadowResolvingChanges(object, result);
        return result;
    }

    private void addShadowResolvingChanges(EObject object, List<EChange> changeCollector) {
        for (EReference feature : object.eClass().getEAllReferences()) {
            // eIsSet does not work here since it does not return true for eGenericType features
            // in EOperation objects. This may be a bug in EMF.
            // TODO: Investigate possible EMF bug.
            if (feature.isContainment() && object.eGet(feature) != null) {
                if (feature.isMany()) {
                    processMultiplicityManyFeature(object, feature, changeCollector);
                } else {
                    processMultiplicityOneFeature(object, feature, changeCollector);
                }
            }
        }
    }

    private void processMultiplicityManyFeature(EObject affectedObject, EReference affectedFeature,
            List<EChange> changeCollector) {
        EList<?> children = (EList<?>) affectedObject.eGet(affectedFeature);
        for (Object childObj : children) {
            if (detachedObjects.contains(childObj)) {
                continue;
            }

            EObject child = (EObject) childObj;

            DeleteNonRootEObjectInList<EObject> deleteChange = ContainmentFactory.eINSTANCE
                    .createDeleteNonRootEObjectInList();
            InitializeEChange.setupUpdateEFeature(deleteChange, affectedObject, affectedFeature);
            InitializeEChange.setupRemoveFromEList(deleteChange, child, 0);

            additionalDetachedObjects.add(child);
            addShadowResolvingChanges(child, changeCollector);
            LOGGER.trace("\tAdding synthesized delete change for " + child + ", contained in " + affectedObject);
            changeCollector.add(deleteChange);
        }
    }

    private void processMultiplicityOneFeature(EObject object, EReference feature, List<EChange> changeCollector) {
        EObject child = (EObject) object.eGet(feature);
        if (child == null || detachedObjects.contains(child)) {
            return;
        }

        DeleteNonRootEObjectSingle<EObject> deleteChange = ContainmentFactory.eINSTANCE
                .createDeleteNonRootEObjectSingle();
        InitializeEChange.setupUpdateEFeature(deleteChange, object, feature);
        deleteChange.setOldValue(child);

        additionalDetachedObjects.add(object);
        addShadowResolvingChanges(child, changeCollector);
        LOGGER.trace("\tAdding synthesized delete change for " + child + ", contained in " + object);
        changeCollector.add(deleteChange);
    }

    /**
     * @return The list of objects additionally found to be detached from the model.
     */
    public List<EObject> getAdditionalDetachedObjects() {
        return additionalDetachedObjects;
    }
}
