package edu.kit.ipd.sdq.vitruvius.framework.contracts.change

import org.eclipse.emf.ecore.change.ChangeDescription

interface EMFModelChange extends ConcreteChange {
    def ChangeDescription getChangeDescription();
}
