package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavaejb.responses.ejbjava2pcm

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.Change2CommandTransformingPreprocessor
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.javaextension.change.feature.JavaFeatureEChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge

class TUIDUpdatePreprocessor implements Change2CommandTransformingPreprocessor {

	override doesProcess(VitruviusChange change) {
		return true;
	}

	override processChange(VitruviusChange change, UserInteracting userInteracting, Blackboard blackboard) {
		for (eChange : change.EChanges) {
			if (eChange instanceof JavaFeatureEChange<?, ?>) {
				val typedChange = eChange as JavaFeatureEChange<?, ?>;
				val oldAffectedEObject = typedChange.oldAffectedEObject
				val newAffectedEObject = typedChange.affectedEObject
				if (null != oldAffectedEObject && null != newAffectedEObject) {
					EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(
						[| blackboard.correspondenceInstance.updateTUID(oldAffectedEObject, newAffectedEObject); return null;], 
						blackboard.modelProviding);
				}
			}
		}
		return #[]
	}

}
