package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.domains.java.echange.feature.JavaFeatureEChange
import tools.vitruv.framework.change.processing.impl.AbstractChangeProcessor
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.processing.ChangeProcessorResult
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.tuid.TUID
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
import java.util.ArrayList
import tools.vitruv.framework.util.command.EMFCommandBridge

class TUIDUpdatePreprocessor extends AbstractChangeProcessor {

	new(UserInteracting userInteracting) {
		super(userInteracting)
	}
	
	override transformChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		val commands = new ArrayList<VitruviusRecordingCommand>();
		for (eChange : change.EChanges) {
			if (eChange instanceof JavaFeatureEChange<?, ?>) {
				val typedChange = eChange as JavaFeatureEChange<?, ?>;
				val oldAffectedEObject = typedChange.oldAffectedEObject
				val newAffectedEObject = typedChange.affectedEObject
				if (null != oldAffectedEObject && null != newAffectedEObject) {
					commands += EMFCommandBridge.createVitruviusRecordingCommand(
						[| 
							TUID.updateTuid(oldAffectedEObject, newAffectedEObject);
							return null;
						]); 
				}
			}
		}
		return new ChangeProcessorResult(change, commands);
	}

}
