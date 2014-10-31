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

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.util.FeatureSwitch;

/**
 * {@link FeaturePackageChangeApplicator} is a class capable of applying changes stemming from the
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature} package.
 */
class FeaturePackageChangeApplicator extends FeatureSwitch<EObject> {
    private static final Logger LOGGER = Logger.getLogger(FeaturePackageChangeApplicator.class);

    private final ModelTranslator modelTranslator;

    public FeaturePackageChangeApplicator(ApplicatorConfiguration applicationRunData) {
        this.modelTranslator = applicationRunData.getTranslator();
    }

    private void unsetFeature(EObject affectedObject, EStructuralFeature feature) {
        LOGGER.trace("Applying an Unset operation on feature " + feature.getName() + " in " + affectedObject);
        EObject correspondingObj = modelTranslator.lookupInTarget(affectedObject);
        String featureName = feature.getName();
        EStructuralFeature correspondingFeature = correspondingObj.eClass().getEStructuralFeature(featureName);
        correspondingObj.eUnset(correspondingFeature);
    }

    @Override
    public <T extends Object> EObject caseUnsetEAttribute(UnsetEAttribute<T> update) {
        super.caseUnsetEAttribute(update);
        unsetFeature(update.getNewAffectedEObject(), update.getAffectedFeature());
        return update;
    }

    @Override
    public <T extends EObject> EObject caseUnsetNonContainmentEReference(UnsetNonContainmentEReference<T> update) {
        super.caseUnsetNonContainmentEReference(update);
        unsetFeature(update.getNewAffectedEObject(), update.getAffectedFeature());
        return update;
    }

    @Override
    public <T extends EObject> EObject caseUnsetContainmentEReference(UnsetContainmentEReference<T> update) {
        super.caseUnsetContainmentEReference(update);
        unsetFeature(update.getNewAffectedEObject(), update.getAffectedFeature());
        return update;
    }

    @Override
    public EObject defaultCase(EObject object) {
        super.defaultCase(object);
        throw new UnsupportedOperationException("Cannot apply changes represented by " + object.eClass().getName()
                + ": Unimplemented");
    }
}
