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

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils;

import java.util.Collection;

import org.eclipse.emf.ecore.EStructuralFeature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute;

public final class ChangeAssert {
    private ChangeAssert() {
    }

    public static void printChangeList(Collection<Change> changes) {
        System.err.println("Change-list related assertion failure, got change list:");
        for (Change c : changes) {
            EChange change = c.getEChanges().get(0);
            System.err.println("\t" + change);
            if (change instanceof FeatureEChange<?, ?>) {
                FeatureEChange<?, ?> fc = (FeatureEChange<?, ?>) change;
                System.err.println("\t\tAffected: " + fc.getAffectedEObject());
                System.err.println("\t\tIn feature: " + fc.getAffectedFeature());
            }
        }
    }

    public static void assertContainsSingleValuedAttributeChange(Collection<Change> changes, EStructuralFeature feature,
            Object newValue) {
        assert feature != null;

        for (Change c : changes) {
            EChange innerChange = c.getEChanges().get(0);
            if (innerChange instanceof ReplaceSingleValuedEAttribute<?, ?>) {
                ReplaceSingleValuedEAttribute<?, ?> attrChange = (ReplaceSingleValuedEAttribute<?, ?>) innerChange;
                if (attrChange.getNewValue() != null && attrChange.getNewValue().equals(newValue)
                        && attrChange.getAffectedFeature() == feature) {
                    return;
                }
            }
        }

        assert false : "Could not find attribute update for " + feature.getName() + "=" + newValue;
    }

}
