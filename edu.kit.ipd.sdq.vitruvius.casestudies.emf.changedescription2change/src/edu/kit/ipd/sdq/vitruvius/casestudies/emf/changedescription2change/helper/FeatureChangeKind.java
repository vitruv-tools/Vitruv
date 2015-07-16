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

import org.eclipse.emf.ecore.change.FeatureChange;

/**
 * {@link FeatureChangeKind} associates types of changes with helper objects converting
 * {@link FeatureChange} objects to lists of changes.
 */
public enum FeatureChangeKind {
    LIST_CHANGE(new ListFeatureChangeTranslationHelperImpl()), SET(new SetChangeTranslationHelperImpl()), UNSET(
            new UnsetChangeTranslationHelper());

    private final IChangeTranslationHelper helper;

    private FeatureChangeKind(IChangeTranslationHelper helper) {
        this.helper = helper;
    }

    /**
     * @return The {@link FeatureChange}-to-
     *         {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change} converter
     *         associated with this type of change.
     */
    public IChangeTranslationHelper getTranslationHelper() {
        return helper;
    }

    /**
     * Determines the kind of change a given {@link FeatureChange} represents.
     * 
     * @param featureChange
     *            A {@link FeatureChange} object.
     * @return The kind of change <code>featureChange</code> represents.
     */
    public static FeatureChangeKind determineChangeKind(FeatureChange featureChange) {
        if (featureChange.getListChanges().isEmpty()) {
            if (featureChange.isSet()) {
                return SET;
            } else {
                return UNSET;
            }
        } else {
            return LIST_CHANGE;
        }
    }
}
