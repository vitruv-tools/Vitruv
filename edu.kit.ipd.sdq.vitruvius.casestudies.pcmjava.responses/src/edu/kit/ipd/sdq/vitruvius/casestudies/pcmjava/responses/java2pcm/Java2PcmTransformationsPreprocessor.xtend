package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.Change2CommandTransformingPreprocessor
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationExecuter
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.RepositoryMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.PackageMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.CompilationUnitMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ClassMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.InterfaceMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.MethodMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ModifierMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.FieldMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ClassMethodMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.TypeReferenceMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.DefaultEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ParameterMappingTransformation
import java.util.concurrent.Callable
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import org.eclipse.emf.common.command.Command
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.GeneralChange

class Java2PcmTransformationsPreprocessor implements Change2CommandTransformingPreprocessor {
	override processChange(VitruviusChange change, UserInteracting userInteracting, Blackboard blackboard) {
		if ((change as GeneralChange).EChanges.size > 1) {
			throw new IllegalStateException("There must be only one EChange in Java model changes");
		}
		val eChange = (change as GeneralChange).EChanges.get(0);
		val transformationExecuter = new TransformationExecuter();
		transformationExecuter.addMapping(new RepositoryMappingTransformation());
		// JaMoPP2PCM
		transformationExecuter.addMapping(new PackageMappingTransformation());
		transformationExecuter.addMapping(new CompilationUnitMappingTransformation());
		transformationExecuter.addMapping(new ClassMappingTransformation());
		transformationExecuter.addMapping(new InterfaceMappingTransformation());
		transformationExecuter.addMapping(new MethodMappingTransformation());
		transformationExecuter.addMapping(new ParameterMappingTransformation());
		transformationExecuter.addMapping(new ModifierMappingTransformation());
		transformationExecuter.addMapping(new FieldMappingTransformation());
		transformationExecuter.addMapping(new ClassMethodMappingTransformation());
		transformationExecuter.addMapping(new TypeReferenceMappingTransformation());
		transformationExecuter.addMapping(new DefaultEObjectMappingTransformation());
		transformationExecuter.userInteracting = userInteracting;
		transformationExecuter.blackboard = blackboard;
		val command = EMFCommandBridge.createVitruviusTransformationRecordingCommand(new Callable<TransformationResult>() {
			public override TransformationResult call() {
				return transformationExecuter.executeTransformationForChange(eChange);
			}
		}) as Command;
		return #[command];
	}
	
	override doesProcess(VitruviusChange change) {
		return change instanceof GeneralChange;
	}

}
