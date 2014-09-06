package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.InsertEAttributeValue;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.RemoveEAttributeValue;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.util.AttributeSwitch;

/**
 * {@link AttributePackageChangeApplicator} is a class capable of applying changes stemming from the
 * {@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute} package.
 */
class AttributePackageChangeApplicator extends AttributeSwitch<EObject> {
    private static final Logger LOGGER = Logger.getLogger(AttributePackageChangeApplicator.class);

    private final ModelTranslator translator;

    public AttributePackageChangeApplicator(ApplicatorConfiguration applicationRunData) {
        this.translator = applicationRunData.getTranslator();
    }

    @Override
    public <T extends Object> EObject caseUpdateSingleValuedEAttribute(UpdateSingleValuedEAttribute<T> update) {
        super.caseUpdateSingleValuedEAttribute(update);

        LOGGER.trace("Updating attribute " + update.getAffectedFeature().getName() + ", source: "
                + update.getNewAffectedEObject());

        EObject targetObject = translator.lookupInTarget(update.getNewAffectedEObject());

        LOGGER.trace("\tApplying it to: " + targetObject);
        LOGGER.trace("\tNew value: " + update.getNewValue());

        Util.setFeature(targetObject, update.getAffectedFeature().getName(), update.getNewValue());

        return update;
    }

    @Override
    public <T extends Object> EObject caseInsertEAttributeValue(InsertEAttributeValue<T> update) {
        super.caseInsertEAttributeValue(update);

        LOGGER.trace("Updating attribute list, inserting object corresponding to " + update.getNewValue()
                + " in feature " + update.getAffectedFeature().getName() + " in " + update.getNewAffectedEObject());
        Object insertedObject = update.getNewValue();
        EObject affectedContainer = translator.lookupInTarget(update.getNewAffectedEObject());

        @SuppressWarnings("unchecked")
        EList<Object> featureList = (EList<Object>) translator.getFeatureValue(update.getAffectedFeature(),
                affectedContainer);
        featureList.add(update.getIndex(), insertedObject);

        return update;
    }

    @Override
    public <T extends Object> EObject caseRemoveEAttributeValue(RemoveEAttributeValue<T> update) {
        super.caseRemoveEAttributeValue(update);

        LOGGER.trace("Removing the object corresponding to " + update.getOldValue()
                + " from the multiplicity-many feature " + update.getAffectedFeature().getName() + " in "
                + update.getNewAffectedEObject());

        // Look up the target feature list
        EObject targetContainerObject = translator.lookupInTarget(update.getNewAffectedEObject());

        EList<?> feature = (EList<?>) translator.getFeatureValue(update.getAffectedFeature(), targetContainerObject);

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
