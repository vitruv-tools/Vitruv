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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

/**
 * {@link ListFeatureChangeTranslationHelperImpl} is used for {@link FeatureChange} objects
 * containing {@link ListChange}s. It categorizes the list-related changes by {@link ListChangeKind}
 * and uses the translation helpers provided by the corresponding {@link ListChangeKind} object to
 * produce the appropriate {@link EChange} objects.
 */
class ListFeatureChangeTranslationHelperImpl extends AbstractChangeTranslationHelper {
    private static final Logger LOGGER = Logger.getLogger(ListFeatureChangeTranslationHelperImpl.class);

    @Override
    public void addReferenceChange(EObject affectedObject, FeatureChange fc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing add/remove reference feature change " + fc + " with " + fc.getListChanges().size()
                + " list changes");
        for (ListChange lc : fc.getListChanges()) {
            IListChangeTranslationHelper helper = ListChangeKind.determineChangeKind(lc).getTranslationHelper();
            helper.addReferenceChange(affectedObject, fc, lc, target, addedObjects, orphanedObjects);
        }
    }

    @Override
    public void addAttributeChange(EObject affectedObject, FeatureChange fc, List<EChange> target,
            List<EObject> addedObjects, List<EObject> orphanedObjects) {
        LOGGER.trace("Processing add/remove attribute feature change " + fc + " with " + fc.getListChanges().size()
                + " list changes");
        for (ListChange lc : fc.getListChanges()) {
            IListChangeTranslationHelper helper = ListChangeKind.determineChangeKind(lc).getTranslationHelper();
            helper.addAttributeChange(affectedObject, fc, lc, target, addedObjects, orphanedObjects);
        }
    }
}
