package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.util.NoSuchElementException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class Utils {
    public static EStructuralFeature getStructuralFeatureInTargetContainer(EObject targetContainer, EObject srcObject) {
        String sourceFeatureName = srcObject.eContainingFeature().getName();
        EStructuralFeature targetFeature = targetContainer.eClass().getEStructuralFeature(sourceFeatureName);
        if (targetFeature == null) {
            throw new NoSuchElementException();
        }
        return targetFeature;
    }
}
