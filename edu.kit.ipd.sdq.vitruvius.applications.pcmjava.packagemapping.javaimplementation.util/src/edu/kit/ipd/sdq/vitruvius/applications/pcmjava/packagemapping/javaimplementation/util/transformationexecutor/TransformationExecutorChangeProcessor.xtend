package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.packagemapping.javaimplementation.util.transformationexecutor

import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl.AbstractEChangeProcessor
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange
import java.util.concurrent.Callable
import edu.kit.ipd.sdq.vitruvius.framework.command.util.EMFCommandBridge
import edu.kit.ipd.sdq.vitruvius.framework.command.TransformationResult

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
