package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class FeatureInstance<T> {
    private EObject parentEObject;
    private EStructuralFeature feature;

    public FeatureInstance(final EObject parentEObject, final EStructuralFeature feature) {
        this.parentEObject = parentEObject;
        @SuppressWarnings("unchecked")
        Class<T> instanceClass = (Class<T>) feature.getEType().getInstanceClass();
        this.feature = feature;
    }

    @SuppressWarnings("unchecked")
    public T getValue() {
        return (T) this.parentEObject.eGet(this.feature);
    }

    public void setValue(final T newValue) {
        this.parentEObject.eSet(this.feature, newValue);
    }
}
