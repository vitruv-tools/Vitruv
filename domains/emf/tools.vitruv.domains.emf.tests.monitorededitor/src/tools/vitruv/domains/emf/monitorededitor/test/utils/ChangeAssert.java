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

package tools.vitruv.domains.emf.monitorededitor.test.utils;

import java.util.Collection;

import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.framework.change.description.VitruviusChange;

public final class ChangeAssert {
    private ChangeAssert() {
    }

    public static void printChangeList(Collection<VitruviusChange> changes) {
        System.err.println("Change-list related assertion failure, got change list:");
        for (VitruviusChange c : changes) {
            EChange change = c.getEChanges().get(0);
            System.err.println("\t" + change);
            if (change instanceof FeatureEChange<?, ?>) {
                FeatureEChange<?, ?> fc = (FeatureEChange<?, ?>) change;
                System.err.println("\t\tAffected: " + fc.getAffectedEObject());
                System.err.println("\t\tIn feature: " + fc.getAffectedFeature());
            }
        }
    }

    public static void assertContainsSingleValuedAttributeChange(Collection<VitruviusChange> changes, EStructuralFeature feature,
            Object newValue) {
        assert feature != null;

        for (VitruviusChange c : changes) {
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
