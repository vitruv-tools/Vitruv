package tools.vitruv.applications.pcmjava.pojotransformations.gplimplementation.util.transformationexecutor

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.processing.impl.AbstractEChangeProcessor
import tools.vitruv.framework.change.echange.EChange
import java.util.concurrent.Callable
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.command.TransformationResult

class TransformationExecutorChangeProcessor extends AbstractEChangeProcessor {
	private val TransformationExecutor transformationExecutor;

	new(UserInteracting userInteracting) {
		super(userInteracting);
		this.transformationExecutor = new TransformationExecutor();

	}

	public def void addMapping(EObjectMappingTransformation transformation) {
		this.transformationExecutor.addMapping(transformation);
		this.transformationExecutor.userInteracting = userInteracting;
	}

	override protected transformChange(EChange change, CorrespondenceModel correspondenceModel) {
		this.transformationExecutor.setCorrespondenceModel(correspondenceModel);
		val executor = this.transformationExecutor;
		val command = EMFCommandBridge.createVitruviusTransformationRecordingCommand(
			new Callable<TransformationResult>() {
				public override TransformationResult call() {
					return executor.executeTransformationForChange(change);
				}
			});
		return #[command];
	}

}
