package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.JavaMethodBodyChangedChangeRefiner
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.Change2CommandTransformingPreprocessor
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import java.util.concurrent.Callable
import org.eclipse.emf.common.command.Command
import org.emftext.language.java.members.ClassMethod
import org.palladiosimulator.pcm.repository.BasicComponent
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding

class Java2PcmMethodBodyChangePreprocessor implements Change2CommandTransformingPreprocessor {

	private val Code2SEFFFactory code2SEFFfactory

	new(Code2SEFFFactory code2SEFFfactory) {
		this.code2SEFFfactory = code2SEFFfactory
	}

	override doesProcess(Change change) {
		if (change instanceof CompositeChange) {
			val JavaMethodBodyChangedChangeRefiner refiner = new JavaMethodBodyChangedChangeRefiner(null);
			return refiner.match(change);
		}
		return false;
	}

	override processChange(Change change, UserInteracting userInteracting, Blackboard blackboard) {
		val compositeChange = change as CompositeChange;
		val command = EMFCommandBridge.createVitruviusTransformationRecordingCommand(
			new Callable<TransformationResult>() {
				public override TransformationResult call() {
					return Java2PcmMethodBodyChangePreprocessor.this
					.executeClassMethodBodyChangeRefiner(blackboard, userInteracting, compositeChange);
				}
			}) as Command;
		return #[command];
	}

	private def TransformationResult executeClassMethodBodyChangeRefiner(Blackboard blackboard,
		UserInteracting userInteracting, CompositeChange compositeChange) {
		val CorrespondenceInstance<Correspondence> correspondenceInstance = blackboard.getCorrespondenceInstance();
		val EMFModelChange emfChange = compositeChange.getChanges().get(0) as EMFModelChange;
		val EFeatureChange<?> eFeatureChange = emfChange.getEChange() as EFeatureChange<?>;
		val ClassMethod oldMethod = eFeatureChange.getOldAffectedEObject() as ClassMethod;
		val ClassMethod newMethod = eFeatureChange.getNewAffectedEObject() as ClassMethod;
		val basicComponentFinding = code2SEFFfactory.createBasicComponentFinding
		val BasicComponent myBasicComponent = basicComponentFinding.findBasicComponentForMethod(newMethod,
			correspondenceInstance);
		val classification = code2SEFFfactory.createAbstractFunctionClassificationStrategy(basicComponentFinding,
			correspondenceInstance, myBasicComponent);
		val InterfaceOfExternalCallFinding interfaceOfExternalCallFinder = code2SEFFfactory.
			createInterfaceOfExternalCallFinding(correspondenceInstance,
				myBasicComponent);
		val ResourceDemandingBehaviourForClassMethodFinding resourceDemandingBehaviourForClassMethodFinding = code2SEFFfactory.
			createResourceDemandingBehaviourForClassMethodFinding(correspondenceInstance);
		val ClassMethodBodyChangedTransformation methodBodyChanged = new ClassMethodBodyChangedTransformation(
			oldMethod, newMethod, basicComponentFinding, classification, interfaceOfExternalCallFinder,
			resourceDemandingBehaviourForClassMethodFinding);
			return methodBodyChanged.execute(blackboard, userInteracting, null);
		}
	}
		