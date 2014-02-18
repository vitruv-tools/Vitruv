package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Implements the multiton design pattern.
 * 
 * @author kramerm
 * 
 */
public class FeatureInstance {
    private static final Map<EObject, Map<EStructuralFeature, FeatureInstance>> INSTANCES = new HashMap<EObject, Map<EStructuralFeature, FeatureInstance>>();

    private EObject parentEObject;
    private EStructuralFeature feature;

    /** Multiton classes should not have a public or default constructor. */
    private FeatureInstance(final EObject parentEObject, final EStructuralFeature feature) {
        this.parentEObject = parentEObject;
        this.feature = feature;
    }

    public static synchronized FeatureInstance getInstance(final EObject parentEObject, final EStructuralFeature feature) {
        Map<EStructuralFeature, FeatureInstance> instanceMap = INSTANCES.get(parentEObject);
        FeatureInstance instance;
        if (instanceMap == null) {
            instanceMap = new HashMap<EStructuralFeature, FeatureInstance>();
            INSTANCES.put(parentEObject, instanceMap);
        }
        instance = instanceMap.get(feature);
        if (instance == null) {
            instance = new FeatureInstance(parentEObject, feature);
            instanceMap.put(feature, instance);
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public Object getValue() {
        return this.parentEObject.eGet(this.feature);
    }

    public void setValue(final Object newValue) {
        this.parentEObject.eSet(this.feature, newValue);
    }
}
