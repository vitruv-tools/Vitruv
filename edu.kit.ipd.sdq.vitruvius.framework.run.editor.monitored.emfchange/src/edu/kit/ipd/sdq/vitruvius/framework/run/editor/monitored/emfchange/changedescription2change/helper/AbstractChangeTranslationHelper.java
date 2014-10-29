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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.FeatureChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

/**
 * {@link AbstractChangeTranslationHelper} classifies changes into {@link EReference}-related and
 * {@link EAttribute}-related changes.
 */
abstract class AbstractChangeTranslationHelper implements IChangeTranslationHelper {

    @Override
    public void addChange(EObject affectedObject, FeatureChange fc, List<EChange> target, List<EObject> addedObjects,
            List<EObject> orphanedObjects) {
        if (fc.getFeature() instanceof EReference) {
            addReferenceChange(affectedObject, fc, target, addedObjects, orphanedObjects);
        } else if (fc.getFeature() instanceof EAttribute) {
            addAttributeChange(affectedObject, fc, target, addedObjects, orphanedObjects);
        } else {
            throw new UnsupportedOperationException(
                    "Don't know how to translate add/remove operation for non-reference, non-attribute features");
        }
    }

    /**
     * Converts an {@link EReference}-related {@link FeatureChange} to its {@link EChange}
     * representation.
     * 
     * @param affectedObject
     *            The {@link EObject} affected by the change.
     * @param fc
     *            The {@link FeatureChange} to be converted.
     * @param target
     *            The target {@link List} where the results should be added.
     * @param addedObjects
     *            The set of {@link EObject EObjects} which are attached to the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     * @param orphanedObjects
     *            The set of {@link EObject EObjects} which are detached from the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     */
    public abstract void addReferenceChange(EObject affectedObject, FeatureChange fc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects);

    /**
     * Converts an {@link EAttribute}-related {@link FeatureChange} to its {@link EChange}
     * representation.
     * 
     * @param affectedObject
     *            The {@link EObject} affected by the change.
     * @param fc
     *            The {@link FeatureChange} to be converted.
     * @param target
     *            The target {@link List} where the results should be added.
     * @param attachedObjects
     *            The set of {@link EObject EObjects} which are attached to the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     * @param detachedObjects
     *            The set of {@link EObject EObjects} which are detached from the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     */
    public abstract void addAttributeChange(EObject affectedObject, FeatureChange fc, List<EChange> target,
            List<EObject> attachedObjects, List<EObject> detachedObjects);

}
