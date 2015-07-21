package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Implements the multiton design pattern for a retrievable representation of a feature at the
 * instance level (equivalent to {@link org.eclipse.emf.ecore.EStructuralFeature.Setting}).
 *
 * Represents the instance of a feature (that is defined for a metaclass) for a given metaclass
 * instance (i.e. EObject) and provides methods for getting and setting the value for this feature
 * instance without hopping meta level.
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

    /**
     * Retrieves the feature instance for the given parent object and feature if it exists,
     * otherwise creates and stores a new instance.
     *
     * @param parentEObject
     *            the parent object for which a feature shall be instantiated
     * @param feature
     *            the feature to be instantiated
     * @return the pre-existing or new feature instance
     */
    public static synchronized FeatureInstance getInstance(final EObject parentEObject,
            final EStructuralFeature feature) {
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

    /**
     * Returns all feature instances that were created so far for the given parent object.
     *
     * @param parentEObject
     *            the parent object for which all feature instances shall be returned
     * @return all existing feature instances
     */
    public static synchronized Collection<FeatureInstance> getAllInstances(final EObject parentEObject) {
        Map<EStructuralFeature, FeatureInstance> feature2FeatureInstanceMap = INSTANCES.get(parentEObject);
        if (feature2FeatureInstanceMap == null) {
            return Collections.emptySet();
        } else {
            return feature2FeatureInstanceMap.values();
        }
    }

    /**
     * Replaces all feature instances for the given old object with feature instances for the given
     * new object. Does not copy and feature value but only recreates the feature instance
     * representations.
     *
     * @param oldEObject
     *            the old object for which instances shall be removed
     * @param newEObject
     *            the new object for which the new instances shall be created
     */
    public static synchronized void performParentObjectReplacement(final EObject oldEObject, final EObject newEObject) {
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

    /**
     * Get the value for the feature from the object.
     *
     * @return the value
     */
    public Object getValue() {
        return this.parentEObject.eGet(this.feature);
    }

    /**
     * Set the value for the feature of the object.
     *
     * @param newValue
     *            the value to be set
     */
    public void setValue(final Object newValue) {
        this.parentEObject.eSet(this.feature, newValue);
    }

    /**
     * Get the feature which instance is represented.
     *
     * @return
     */
    public EStructuralFeature getFeature() {
        return this.feature;
    }

}
