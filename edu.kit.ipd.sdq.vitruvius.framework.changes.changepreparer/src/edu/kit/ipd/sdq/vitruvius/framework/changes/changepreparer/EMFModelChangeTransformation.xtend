package edu.kit.ipd.sdq.vitruvius.framework.changes.changepreparer

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import org.eclipse.emf.ecore.change.ChangeDescription
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.VitruviusChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.ProcessableChangeFactory

class EMFModelChangeTransformation {
	var VitruviusChange vitruviusChange;

	/**
	 * Model must be in the state before the change when instantiating the transformation.
	 */
	new(EMFModelChange emfModelChange) {
		transform(emfModelChange);
	}
	
	public def VitruviusChange getChange() {
		return vitruviusChange;
	}

	private def VitruviusChange encloseObjectsInChange(ChangeDescription changeDescription, Iterable<EChange> eChanges, VURI vuri) {
		ProcessableChangeFactory.instance.createVitruviusChange(changeDescription, eChanges.toList, vuri);
	}

	private def void transform(EMFModelChange emfModelChange) {
		val changeDescription = emfModelChange.changeDescription;
		val transformedEChanges = new ChangeDescription2EChangesTransformation(changeDescription).transform()
		vitruviusChange = encloseObjectsInChange(changeDescription, transformedEChanges, emfModelChange.URI);
	}
}
