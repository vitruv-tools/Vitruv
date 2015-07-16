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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

/**
 * {@link IListChangeTranslationHelper} is an interface for helpers translating {@link ListChange}
 * objects obtained from a {@link FeatureChange} to the corresponding {@link EChange} objects.
 */
interface IListChangeTranslationHelper {
    /**
     * Translates a given {@link FeatureChange} to its {@link EChange} representation for an
     * {@link org.eclipse.emf.ecore.EAttribute} feature.
     * 
     * @param affectedObject
     *            The {@link EObject} affected by the change.
     * @param fc
     *            The {@link FeatureChange} to be translated.
     * @param lc
     *            The {@link ListChange} contained in <code>fc</code> to be translated.
     * @param target
     *            The {@link List} of {@link EChange} objects where the results should be added.
     * @param addedObjects
     *            The set of {@link EObject EObjects} which are attached to the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     * @param orphanedObjects
     *            The set of {@link EObject EObjects} which are detached from the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     */
    public void addReferenceChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects);

    /**
     * Translates a given {@link FeatureChange} to its {@link EChange} representation for an
     * {@link org.eclipse.emf.ecore.EAttribute} feature.
     * 
     * @param affectedObject
     *            The {@link EObject} affected by the change.
     * @param fc
     *            The {@link FeatureChange} to be translated.
     * @param lc
     *            The {@link ListChange} contained in <code>fc</code> to be translated.
     * @param target
     *            The {@link List} of {@link EChange} objects where the results should be added.
     * @param addedObjects
     *            The set of {@link EObject EObjects} which are attached to the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     * @param orphanedObjects
     *            The set of {@link EObject EObjects} which are detached from the model instance by
     *            the changes currently being processed (i.e., not only by <code>fc</code>).
     */
    public void addAttributeChange(EObject affectedObject, FeatureChange fc, ListChange lc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects);
}
