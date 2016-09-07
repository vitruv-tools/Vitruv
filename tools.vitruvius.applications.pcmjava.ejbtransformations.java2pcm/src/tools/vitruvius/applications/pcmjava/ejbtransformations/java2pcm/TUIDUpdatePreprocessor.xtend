package tools.vitruvius.applications.pcmjava.ejbtransformations.java2pcm

import tools.vitruvius.framework.userinteraction.UserInteracting
import tools.vitruvius.domains.java.echange.feature.JavaFeatureEChange
import tools.vitruvius.framework.change.processing.impl.AbstractChangeProcessor
import tools.vitruvius.framework.change.description.ConcreteChange
import tools.vitruvius.framework.correspondence.CorrespondenceModel
import tools.vitruvius.framework.change.processing.ChangeProcessorResult

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
