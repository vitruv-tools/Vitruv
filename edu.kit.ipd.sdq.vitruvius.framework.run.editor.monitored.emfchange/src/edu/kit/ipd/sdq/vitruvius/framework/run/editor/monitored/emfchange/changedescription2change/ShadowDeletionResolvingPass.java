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

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.IObjectChange.EChangeCompoundObjectChange;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper.ShadowDeletionChangeHelper;

/**
 * {@link ShadowDeletionResolvingPass} resolves implicit delete operations by creating appropriate
 * EChange objects stored in {@link IObjectChange} objects.. As these {@link IObjectChange} objects
 * may contain delete operations affecting various objects (each affected object getting deleted by
 * one of the contained delete operations), they report the object during whose deletion-resolving
 * operation the changes were made as their "affected" object. The contents of these IObjectChange
 * objects are ordered that no object gets referenced after its deletion.
 */
class ShadowDeletionResolvingPass implements IObjectChangePass {

    private final Collection<EObject> detachedObjects;

    /**
     * Constructs a new {@link ShadowDeletionResolvingPass} object.
     * 
     * @param detachedObjects
     *            The collection of objects already marked as detached from the model.
     */
    public ShadowDeletionResolvingPass(Collection<EObject> detachedObjects) {
        this.detachedObjects = detachedObjects;
    }

    private IObjectChange createObjectChange(EObject affectedObject, List<EChange> eChanges) {
        return new EChangeCompoundObjectChange(affectedObject, eChanges, true);
    }

    @Override
    public List<IObjectChange> runPass(Collection<IObjectChange> changes) {
        List<IObjectChange> result = new ArrayList<>(changes);

        ShadowDeletionChangeHelper helper = new ShadowDeletionChangeHelper(detachedObjects);

        for (EObject detachedObj : detachedObjects) {
            List<EChange> additionalChanges = helper.getShadowResolvingChanges(detachedObj);
            result.add(0, createObjectChange(detachedObj, additionalChanges));
        }

        detachedObjects.addAll(helper.getAdditionalDetachedObjects());

        return result;
    }
}
