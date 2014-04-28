package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Implements the multiton design pattern.
 * 
 * WARNING: This multiton is automatically updated whenever the used EObjects are replaced!
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

    public static synchronized Collection<FeatureInstance> getAllInstances(final EObject parentEObject) {
        Map<EStructuralFeature, FeatureInstance> feature2FeatureInstanceMap = INSTANCES.get(parentEObject);
        if (feature2FeatureInstanceMap == null) {
            return Collections.emptySet();
        } else {
            return feature2FeatureInstanceMap.values();
        }
    }

    public static synchronized void update(final EObject oldEObject, final EObject newEObject) {
        Map<EStructuralFeature, FeatureInstance> oldFeature2FeatureInstanceMap = INSTANCES.remove(oldEObject);
        if (oldFeature2FeatureInstanceMap != null) {
            Set<EStructuralFeature> mappedFeatures = oldFeature2FeatureInstanceMap.keySet();
            Map<EStructuralFeature, FeatureInstance> newFeature2FeatureInstanceMap = new HashMap<EStructuralFeature, FeatureInstance>();
            for (EStructuralFeature mappedFeature : mappedFeatures) {
                FeatureInstance newFeatureInstance = new FeatureInstance(newEObject, mappedFeature);
                newFeature2FeatureInstanceMap.put(mappedFeature, newFeatureInstance);
            }
            INSTANCES.put(newEObject, newFeature2FeatureInstanceMap);
        }
    }

    public Object getValue() {
        return this.parentEObject.eGet(this.feature);
    }

    public void setValue(final Object newValue) {
        this.parentEObject.eSet(this.feature, newValue);
    }

    public EStructuralFeature getFeature() {
        return this.feature;
    }

}
