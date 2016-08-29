package edu.kit.ipd.sdq.vitruvius.framework.correspondence.tuid;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public interface TUIDCalculatorAndResolver {

    boolean hasTUID(EObject eObject);

    String calculateTUIDFromEObject(EObject eObject);

    /**
     * syntactic sugar for map[{@link #calculateTUIDFromEObject(EObject)}]
     * 
     * @param eObjects
     * @return
     */
    List<String> calculateTUIDsFromEObjects(List<EObject> eObjects);

    /**
     * Calculates the TUID for an EObject. The given prefix is the TUID part, which should be used
     * instead of the part, which would be inferred from the virtualRootObject. Please note: This
     * method is only available for TUID calculators, which utilize some way of hierarchy.
     *
     * @param eObject
     *            The EObject for the TUID calculation.
     * @param virtualRootObject
     *            The virtual root object, whichs TUID part is described by the prefix parameter. If
     *            the parameter is null the real root object of the given EObject is assumed.
     * @param prefix
     *            The prefix, which describes the TUID part for the virtual root object. It has to
     *            contain the TUID calculator identifier and the model URI at least.
     * @return The calculated TUID string.
     */
    String calculateTUIDFromEObject(EObject eObject, EObject virtualRootObject, String prefix);

    /**
     * Returns the VURI for the model that contains the EObject with the given TUID if it exists and
     * otherwise <code>null</code>.
     *
     * @param tuid
     * @return the VURI if it exists, otherwise <code>null</code>
     */
    String getModelVURIContainingIdentifiedEObject(String tuid);

    EObject resolveEObjectFromRootAndFullTUID(EObject root, String tuid);

    boolean isValidTUID(String tuid);

    /**
     * Notifies the calculator and resolver that the given root EObject should be removed from the
     * cache.
     *
     * @param root
     *            the root EObject that shall be removed
     */
    void removeRootFromCache(EObject root);

    /**
     * Removes the root for the given tuid from the cache if it is cached.
     *
     * @param tuid
     */
    void removeIfRootAndCached(String tuid);
}
