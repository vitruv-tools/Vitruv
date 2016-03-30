package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.Change2CommandTransformingPreprocessor
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.JavaMethodBodyChangedChangeRefiner
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange
import java.util.concurrent.Callable
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import org.emftext.language.java.members.ClassMethod
import org.palladiosimulator.pcm.repository.BasicComponent
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.ClassMethodBodyChangedTransformation
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding
import org.eclipse.emf.common.command.Command
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm.transformations.BasicComponentForPackageMappingFinder
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm.transformations.FunctionClassificationStrategyForPackageMapping
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm.transformations.InterfaceOfExternalCallFinderForPackageMapping
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm.transformations.ResourceDemandingBehaviourForClassMethodFinderForPackageMapping
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence

class Java2PcmMethodBodyChangePreprocessor implements Change2CommandTransformingPreprocessor {
	
	override doesProcess(Change change) {
		if (change instanceof CompositeChange) {
            val JavaMethodBodyChangedChangeRefiner refiner = new JavaMethodBodyChangedChangeRefiner(null);
            return refiner.match(change);
        }
        return false;
	}
	
	override processChange(Change change, UserInteracting userInteracting, Blackboard blackboard) {
		val compositeChange = change as CompositeChange;
		val command = EMFCommandBridge.createVitruviusTransformationRecordingCommand(new Callable<TransformationResult>() {
			public override TransformationResult call() {
				return Java2PcmMethodBodyChangePreprocessor.this
                                .executeClassMethodBodyChangeRefiner(blackboard, userInteracting, compositeChange);
			}
		}) as Command;
        return #[command];
	}
	
	
	private def TransformationResult executeClassMethodBodyChangeRefiner(Blackboard blackboard, UserInteracting userInteracting, 
            CompositeChange compositeChange) {
        val CorrespondenceInstance<Correspondence> correspondenceInstance = blackboard.getCorrespondenceInstance();
        val EMFModelChange emfChange = compositeChange.getChanges().get(0) as EMFModelChange;
        val EFeatureChange<?> eFeatureChange = emfChange.getEChange() as EFeatureChange<?>;
        val ClassMethod oldMethod = eFeatureChange.getOldAffectedEObject() as ClassMethod;
        val ClassMethod newMethod = eFeatureChange.getNewAffectedEObject() as ClassMethod;
        val BasicComponentForPackageMappingFinder basicComponentFinder = new BasicComponentForPackageMappingFinder();
        val BasicComponent myBasicComponent = basicComponentFinder.findBasicComponentForMethod(newMethod,
                correspondenceInstance);
        val FunctionClassificationStrategyForPackageMapping classification = new FunctionClassificationStrategyForPackageMapping(
                basicComponentFinder, correspondenceInstance, myBasicComponent);
        val InterfaceOfExternalCallFinding interfaceOfExternalCallFinder = new InterfaceOfExternalCallFinderForPackageMapping(
                correspondenceInstance, myBasicComponent);
        val ResourceDemandingBehaviourForClassMethodFinding resourceDemandingBehaviourForClassMethodFinding = new ResourceDemandingBehaviourForClassMethodFinderForPackageMapping(
                correspondenceInstance);
        val ClassMethodBodyChangedTransformation methodBodyChanged = new ClassMethodBodyChangedTransformation(
                oldMethod, newMethod, basicComponentFinder, classification, interfaceOfExternalCallFinder,
                resourceDemandingBehaviourForClassMethodFinding);
        return methodBodyChanged.execute(blackboard, userInteracting, null);
    }
}