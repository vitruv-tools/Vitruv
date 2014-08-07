package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface TUIDCalculatorAndResolver {
    String calculateTUIDFromEObject(EObject eObject);

    VURI getModelVURIContainingIdentifiedEObject(String tuid);

    EObject resolveEObjectFromRootAndFullTUID(EObject root, String tuid);

    boolean isValidTUID(String tuid);
}
