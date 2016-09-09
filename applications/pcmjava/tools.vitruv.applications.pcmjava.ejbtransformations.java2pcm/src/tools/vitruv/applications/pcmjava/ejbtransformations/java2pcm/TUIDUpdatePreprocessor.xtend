package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.domains.java.echange.feature.JavaFeatureEChange
import tools.vitruv.framework.change.processing.impl.AbstractChangeProcessor
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.processing.ChangeProcessorResult
import tools.vitruv.framework.change.description.TransactionalChange

class TUIDUpdatePreprocessor extends AbstractChangeProcessor {

	new(UserInteracting userInteracting) {
		super(userInteracting)
	}
	
	override transformChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
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
