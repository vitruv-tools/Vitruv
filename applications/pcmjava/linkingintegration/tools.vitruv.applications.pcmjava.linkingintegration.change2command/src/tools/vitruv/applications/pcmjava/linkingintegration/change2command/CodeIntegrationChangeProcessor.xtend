package tools.vitruv.applications.pcmjava.linkingintegration.change2command

import tools.vitruv.framework.change.processing.impl.AbstractChangeProcessor
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.correspondence.CorrespondenceModel
import java.util.ArrayList
import tools.vitruv.applications.pcmjava.linkingintegration.change2command.internal.IntegrationChange2CommandTransformer
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.util.command.TransformationResult
import tools.vitruv.applications.pcmjava.linkingintegration.change2command.internal.IntegrationChange2CommandResult

class CodeIntegrationChangeProcessor extends AbstractChangeProcessor {
	private val IntegrationChange2CommandTransformer integrationTransformer;
	
	new(UserInteracting userInteracting) {
		super(userInteracting);
		this.integrationTransformer = new IntegrationChange2CommandTransformer(getUserInteracting());
	}
	
	override doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		return true;
	}
	
	override propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		val propagationResult = new TransformationResult();
		val integrationResult = change.performIntegration(correspondenceModel);
		propagationResult.integrateTransformationResult(integrationResult.propagationResult);
		return propagationResult;
	}
	
	def dispatch IntegrationChange2CommandResult performIntegration(CompositeTransactionalChange change, CorrespondenceModel correspondenceModel) {
		val propagationResult = new TransformationResult();
		val integratedChanges = new ArrayList<TransactionalChange>();
		for (innerChange : change.changes) {
			val integrationResult = innerChange.performIntegration(correspondenceModel);
			if (integrationResult.isIntegrationChange) {
				integratedChanges += innerChange;
				propagationResult.integrateTransformationResult(integrationResult.propagationResult);
			}
		}
		for (integratedChange : integratedChanges) {
			change.removeChange(integratedChange);
		}
		return new IntegrationChange2CommandResult(propagationResult);
	}
	
	def dispatch IntegrationChange2CommandResult performIntegration(ConcreteChange change, CorrespondenceModel correspondenceModel) {
		// Special behavior for changes to integrated elements
		return integrationTransformer.compute(change.EChange, correspondenceModel);
//		} else {
//			nonIntegratedEChanges += eChange;
//		}
//		val resultingChange = if (nonIntegratedEChanges.isEmpty) {
//			VitruviusChangeFactory.instance.createEmptyChange(change.getURI);
//		} else if (nonIntegratedEChanges.size == 1) {
//			VitruviusChangeFactory.instance.createConcreteChange(nonIntegratedEChanges.get(0), change.getURI);
//		} else {
//			val transactionalChange = VitruviusChangeFactory.instance.createCompositeTransactionalChange();
//			for (eChange : nonIntegratedEChanges) {
//				transactionalChange.addChange(VitruviusChangeFactory.instance.createConcreteChange(eChange, change.getURI));
//			}
//			transactionalChange;
//		}
//		
//		return new ChangeProcessorResult(resultingChange, commands);
	}
	
}
