package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.Change2CommandTransformingPreprocessor
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge
import java.util.concurrent.Callable
import org.eclipse.emf.common.command.Command
import org.palladiosimulator.pcm.repository.BasicComponent
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding
import org.emftext.language.java.members.Method
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.ConcreteChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.javaextension.change.feature.JavaFeatureEChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateReferenceEChange
import org.emftext.language.java.statements.StatementsPackage
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.RemoveEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference
import org.emftext.language.java.statements.Statement
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.TransactionalChange

class Java2PcmMethodBodyChangePreprocessor implements Change2CommandTransformingPreprocessor {

	private val Code2SEFFFactory code2SEFFfactory

	new(Code2SEFFFactory code2SEFFfactory) {
		this.code2SEFFfactory = code2SEFFfactory
	}

	override doesProcess(VitruviusChange change) {
		if (change instanceof TransactionalChange) {
			return match(change);
		}
		return false;
	}
	
	def match(TransactionalChange change) {
		System.err.println("Match transaction")
		val Iterable<JavaFeatureEChange> eChanges = change.EChanges.filter(UpdateReferenceEChange).filter[isContainment].filter(JavaFeatureEChange);
		if (eChanges.size != change.EChanges.size) {
			return false
		}

		val firstChange = eChanges.get(0);
		if (!(firstChange.oldAffectedEObject instanceof Method) ||
			!(firstChange.affectedEObject instanceof Method)) {
			return false
		}

		if (!eChanges.forall[affectedFeature == StatementsPackage.eINSTANCE.statementListContainer_Statements]) {
			return false
		}

		if (!eChanges.forall[affectedEObject == firstChange.affectedEObject]) {
			return false
		}

		if (!eChanges.forall[oldAffectedEObject == firstChange.oldAffectedEObject]) {
			return false
		}

		val deleteChanges = eChanges.filter(RemoveEReference)  // filter(DeleteNonRootEObjectInList)
		val addChanges = eChanges.filter(InsertEReference) // filter(CreateNonRootEObjectInList)

		if (addChanges.size + deleteChanges.size != change.EChanges.size) {
			return false
		}

		if (!deleteChanges.forall[oldValue instanceof Statement]) {
			return false
		}

		if (!addChanges.forall[newValue instanceof Statement]) {
			return false
		}
	System.err.println("Matched transaction")
		return true
	}

	override processChange(VitruviusChange change, UserInteracting userInteracting, Blackboard blackboard) {
		System.err.println("Process modification")
		val compositeChange = change as TransactionalChange;
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
		UserInteracting userInteracting, TransactionalChange compositeChange) {
		val CorrespondenceInstance<Correspondence> correspondenceInstance = blackboard.getCorrespondenceInstance();
		val ConcreteChange emfChange = compositeChange.getChanges().get(0) as ConcreteChange;
		val JavaFeatureEChange<?,?> eFeatureChange = emfChange.getEChanges().get(0) as JavaFeatureEChange<?,?>;
		val oldMethod = eFeatureChange.getOldAffectedEObject() as Method;
		val newMethod = eFeatureChange.getAffectedEObject() as Method;
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
			return methodBodyChanged.execute(blackboard, userInteracting);
		}
	}
		