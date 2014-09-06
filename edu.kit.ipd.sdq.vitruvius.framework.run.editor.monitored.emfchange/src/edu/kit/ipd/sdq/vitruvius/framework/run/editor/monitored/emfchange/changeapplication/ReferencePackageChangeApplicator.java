package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.InsertNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.RemoveNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.util.ReferenceSwitch;

/**
 * {@link ReferencePackageChangeApplicator} is a class capable of applying changes stemming from the
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference} package.
 */
class ReferencePackageChangeApplicator extends ReferenceSwitch<EObject> {
    private static final Logger LOGGER = Logger.getLogger(ReferencePackageChangeApplicator.class);

    private final ModelTranslator modelTranslator;

    public ReferencePackageChangeApplicator(ApplicatorConfiguration applicationRunData) {
        this.modelTranslator = applicationRunData.getTranslator();
    }

    private boolean isFeatureExemptFromExplicitUpdates(EStructuralFeature it) {
        return it.getName().equals("eContainingClass");
    }

    @Override
    public <T extends EObject> EObject caseUpdateSingleValuedNonContainmentEReference(
            UpdateSingleValuedNonContainmentEReference<T> update) {
        super.caseUpdateSingleValuedNonContainmentEReference(update);

        if (isFeatureExemptFromExplicitUpdates(update.getAffectedFeature())) {
            LOGGER.trace("Ignoring update reference: " + update + " (feature is exempt from explicit updates)");
        } else {
            LOGGER.trace("Updating reference " + update.getAffectedFeature().getName() + " in object corresponding to "
                    + update.getNewAffectedEObject());
            EObject targetObject = modelTranslator.lookupInTarget(update.getNewAffectedEObject());

            LOGGER.trace("\tApplying it to: " + targetObject);
            LOGGER.trace("\tNew value: " + update.getNewValue());

            EObject targetValue = modelTranslator.lookupInTarget(update.getNewValue());

            assert targetValue.getClass().equals(update.getNewValue().getClass());

            LOGGER.trace("\tTarget new value: " + targetValue);
            Util.setFeature(targetObject, update.getAffectedFeature().getName(), targetValue);
        }

        return update;
    }

    @Override
    public <T extends EObject> EObject caseInsertNonContainmentEReference(InsertNonContainmentEReference<T> update) {
        super.caseInsertNonContainmentEReference(update);

        EReference referenceFeature = update.getAffectedFeature();
        EObject targetInsertedObj;

        // Case 2: The feature is not a containment. Thus, the translator can
        // directly yield the object since it can be located via its containment.
        LOGGER.trace("\tInserting an object into a non-containment reference list: " + update);
        targetInsertedObj = modelTranslator.lookupInTarget(update.getNewValue());

        assert targetInsertedObj != null;

        EObject affectedContainer = modelTranslator.lookupInTarget(update.getNewAffectedEObject());
        @SuppressWarnings("unchecked")
        EList<EObject> featureList = (EList<EObject>) modelTranslator.getFeatureValue(referenceFeature,
                affectedContainer);
        featureList.add(update.getIndex(), targetInsertedObj);

        return update;
    }

    @Override
    public <T extends EObject> EObject caseRemoveNonContainmentEReference(RemoveNonContainmentEReference<T> update) {
        super.caseRemoveNonContainmentEReference(update);

        LOGGER.trace("Removing the object corresponding to " + update.getOldValue()
                + " from the multiplicity-many feature " + update.getAffectedFeature().getName() + " in "
                + update.getNewAffectedEObject());

        // Look up the target feature list
        EObject targetContainerObject = modelTranslator.lookupInTarget(update.getNewAffectedEObject());

        EList<?> feature = (EList<?>) modelTranslator.getFeatureValue(update.getAffectedFeature(),
                targetContainerObject);

        Object removedObj = feature.remove(update.getIndex());

        LOGGER.trace("\tRemoved: " + removedObj);

        return update;
    }

    @Override
    public EObject defaultCase(EObject object) {
        super.defaultCase(object);
        throw new UnsupportedOperationException("Cannot apply changes represented by " + object.eClass().getName()
                + ": Unimplemented");
    }

}
