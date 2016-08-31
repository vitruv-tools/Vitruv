package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff

import edu.kit.ipd.sdq.vitruvius.framework.util.command.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.util.command.EMFCommandBridge
import java.util.concurrent.Callable
import org.eclipse.emf.common.command.Command
import org.palladiosimulator.pcm.repository.BasicComponent
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding
import org.emftext.language.java.members.Method
import edu.kit.ipd.sdq.vitruvius.framework.change.description.ConcreteChange
import edu.kit.ipd.sdq.vitruvius.domains.java.echange.feature.JavaFeatureEChange
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.UpdateReferenceEChange
import org.emftext.language.java.statements.StatementsPackage
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.RemoveEReference
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.InsertEReference
import org.emftext.language.java.statements.Statement
import edu.kit.ipd.sdq.vitruvius.framework.change.description.TransactionalChange
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.ChangeProcessorResult
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl.AbstractChangeProcessor
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChangeFactory

class Java2PcmMethodBodyChangePreprocessor extends AbstractChangeProcessor {

	private val Code2SEFFFactory code2SEFFfactory

	new(UserInteracting userInteracting, Code2SEFFFactory code2SEFFfactory) {
		super(userInteracting);
		this.code2SEFFfactory = code2SEFFfactory
	}

	override transformChange(ConcreteChange change, CorrespondenceModel correspondenceModel) {
		if (change instanceof TransactionalChange && match(change as TransactionalChange)) {
			val compositeChange = change as TransactionalChange;
			val command = EMFCommandBridge.createVitruviusTransformationRecordingCommand(
			new Callable<TransformationResult>() {
				public override TransformationResult call() {
					return Java2PcmMethodBodyChangePreprocessor.this
						.executeClassMethodBodyChangeRefiner(correspondenceModel, userInteracting, compositeChange);
				}
			});
			return new ChangeProcessorResult(change, #[command]);//VitruviusChangeFactory.instance.createEmptyChange(change.URI), #[command]);
		}
		return new ChangeProcessorResult(change, #[]);
	}
		
	def match(TransactionalChange change) {
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
		return true
	}

	private def TransformationResult executeClassMethodBodyChangeRefiner(CorrespondenceModel correspondenceModel,
		UserInteracting userInteracting, TransactionalChange compositeChange) {
		val ConcreteChange emfChange = compositeChange.getChanges().get(0) as ConcreteChange;
		val JavaFeatureEChange<?,?> eFeatureChange = emfChange.getEChanges().get(0) as JavaFeatureEChange<?,?>;
		val oldMethod = eFeatureChange.getOldAffectedEObject() as Method;
		val newMethod = eFeatureChange.getAffectedEObject() as Method;
		val basicComponentFinding = code2SEFFfactory.createBasicComponentFinding
		val BasicComponent myBasicComponent = basicComponentFinding.findBasicComponentForMethod(newMethod,
			correspondenceModel);
		val classification = code2SEFFfactory.createAbstractFunctionClassificationStrategy(basicComponentFinding,
			correspondenceModel, myBasicComponent);
		val InterfaceOfExternalCallFinding interfaceOfExternalCallFinder = code2SEFFfactory.
			createInterfaceOfExternalCallFinding(correspondenceModel,
				myBasicComponent);
		val ResourceDemandingBehaviourForClassMethodFinding resourceDemandingBehaviourForClassMethodFinding = code2SEFFfactory.
			createResourceDemandingBehaviourForClassMethodFinding(correspondenceModel);
		val ClassMethodBodyChangedTransformation methodBodyChanged = new ClassMethodBodyChangedTransformation(
			oldMethod, newMethod, basicComponentFinding, classification, interfaceOfExternalCallFinder,
			resourceDemandingBehaviourForClassMethodFinding);
			return methodBodyChanged.execute(correspondenceModel, userInteracting);
		}
		
		
	}
		