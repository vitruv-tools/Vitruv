package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.domains.java.change.feature.JavaFeatureEChange
import edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor.AbstractChangeProcessor
import edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.ConcreteChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel
import edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor.ChangeProcessorResult

class TUIDUpdatePreprocessor extends AbstractChangeProcessor {

	new(UserInteracting userInteracting) {
		super(userInteracting)
	}
	
	override transformChange(ConcreteChange change, CorrespondenceModel correspondenceModel) {
		for (eChange : change.EChanges) {
			if (eChange instanceof JavaFeatureEChange<?, ?>) {
				val typedChange = eChange as JavaFeatureEChange<?, ?>;
				val oldAffectedEObject = typedChange.oldAffectedEObject
				val newAffectedEObject = typedChange.affectedEObject
				if (null != oldAffectedEObject && null != newAffectedEObject) {
					//EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(
						//[| 
							correspondenceModel.updateTUID(oldAffectedEObject, newAffectedEObject);// return null;
						//], 
					//	blackboard.modelProviding);
				}
			}
		}
		return new ChangeProcessorResult(change, #[]);
	}

}
