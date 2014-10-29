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

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.util.NoSuchElementException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * {@link Util} contains various utility methods.
 */
final class Util {

    private Util() {
    }

    /**
     * Retrieves the target model's containment feature of a source model's object.
     * 
     * @param targetContainer
     *            The target model object having a feature corresponding to <code>srcObject</code>'s
     *            containment feature.
     * @param srcObject
     *            An object contained in the source model.
     * @return The corresponding containment feature in the target model.
     * 
     * @throws NoSuchElementException
     *             No such feature could be found in the target model containment object.
     */
    public static EStructuralFeature getStructuralFeatureInTargetContainer(EObject targetContainer, EObject srcObject) {
        String sourceFeatureName = srcObject.eContainingFeature().getName();
        EStructuralFeature targetFeature = targetContainer.eClass().getEStructuralFeature(sourceFeatureName);
        if (targetFeature == null) {
            throw new NoSuchElementException();
        }
        return targetFeature;
    }

    public static void setFeature(EObject object, String featureName, Object newValue) {
        EStructuralFeature affectedFeature = object.eClass().getEStructuralFeature(featureName);
        assert affectedFeature != null : "Could not find feature " + featureName + " in " + object.eClass();

        object.eSet(affectedFeature, newValue);
    }
}
