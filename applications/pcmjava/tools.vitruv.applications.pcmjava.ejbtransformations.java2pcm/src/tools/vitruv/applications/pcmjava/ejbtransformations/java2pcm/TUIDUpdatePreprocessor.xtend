package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.domains.java.echange.feature.JavaFeatureEChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.change.processing.impl.AbstractEChangeProcessor
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.command.TransformationResult

class TUIDUpdatePreprocessor extends AbstractEChangeProcessor {

	new(UserInteracting userInteracting) {
		super(userInteracting)
	}
	
	override doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
		return change instanceof JavaFeatureEChange<?, ?>;
	}
	
	override propagateChange(EChange change, CorrespondenceModel correspondenceModel) {
		if (change instanceof JavaFeatureEChange<?, ?>) {
			val oldAffectedEObject = change.oldAffectedEObject
			val newAffectedEObject = change.affectedEObject
			if (null != oldAffectedEObject && null != newAffectedEObject) {
				TuidManager.instance.updateTuid(oldAffectedEObject, newAffectedEObject);
			}
		}
		return new TransformationResult();
	}

}
