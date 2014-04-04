package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface TUIDCalculatorAndResolver {
    String EOBJECT_SEPERATOR = "#";

    String getTUID(EObject eObject);

    VURI getModelVURIContainingIdentifiedEObject(String tuid);

    EObject getIdentifiedEObjectWithinRootEObject(EObject root, String tuid);
}
