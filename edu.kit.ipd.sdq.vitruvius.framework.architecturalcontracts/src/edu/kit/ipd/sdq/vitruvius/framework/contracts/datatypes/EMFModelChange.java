package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import org.eclipse.emf.ecore.EObject;

public class EMFModelChange extends Change {

    public EMFModelChange(final EObject oldValue, final EObject newValue) {
        super(oldValue, newValue);
    }
}
