package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface TUIDCalculatorAndResolver {
    String calculateTUIDFromEObject(EObject eObject);

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

    VURI getModelVURIContainingIdentifiedEObject(String tuid);

    EObject resolveEObjectFromRootAndFullTUID(EObject root, String tuid);

    boolean isValidTUID(String tuid);
}
