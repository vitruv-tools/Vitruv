package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.processing.impl.AbstractEChangeProcessor
import tools.vitruv.framework.change.echange.EChange

abstract class TransformationExecutorChangeProcessor extends AbstractEChangeProcessor {
	private val TransformationExecutor transformationExecutor;

	new(UserInteracting userInteracting) {
		super(userInteracting);
		this.transformationExecutor = new TransformationExecutor();
	} 

	public def void addMapping(EObjectMappingTransformation transformation) {
		this.transformationExecutor.addMapping(transformation);
		this.transformationExecutor.userInteracting = userInteracting;
	}

	override protected doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
		return true;
	}

	override protected propagateChange(EChange change, CorrespondenceModel correspondenceModel) {
		this.transformationExecutor.setCorrespondenceModel(correspondenceModel);
		val executor = this.transformationExecutor;
		return executor.executeTransformationForChange(change);
	}
	
	override setUserInteracting(UserInteracting userInteracting) {
		super.userInteracting = userInteracting;
		if (transformationExecutor != null) this.transformationExecutor.userInteracting = userInteracting
	}

}
