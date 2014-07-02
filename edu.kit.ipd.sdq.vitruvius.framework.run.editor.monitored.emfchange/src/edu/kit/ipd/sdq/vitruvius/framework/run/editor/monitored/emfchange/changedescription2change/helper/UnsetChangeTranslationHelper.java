package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.change.FeatureChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UnsetEFeature;

/**
 * {@link UnsetChangeTranslationHelper} contains feature-unset operations to the corresponding
 * {@link Change} objects.
 */
public class UnsetChangeTranslationHelper implements IChangeTranslationHelper {
    @Override
    public void addChange(EObject affectedObject, FeatureChange fc, List<Change> target, List<EObject> addedObjects,
            List<EObject> orphanedObjects) {

        // TODO: what about unset on containment reference?

        UnsetEFeature<EStructuralFeature> unsetFeature = ChangeFactory.eINSTANCE.createUnsetEFeature();
        unsetFeature.setAffectedEObject(affectedObject);
        unsetFeature.setAffectedFeature(fc.getFeature());
        target.add(new EMFModelChange(unsetFeature));
    }
}
