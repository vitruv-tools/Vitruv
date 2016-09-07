package tools.vitruvius.applications.pcmjava.seffstatements.code2seff

import tools.vitruvius.framework.util.command.TransformationResult
import tools.vitruvius.framework.userinteraction.UserInteracting
import tools.vitruvius.framework.util.command.EMFCommandBridge
import java.util.concurrent.Callable
import org.palladiosimulator.pcm.repository.BasicComponent
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding
import org.emftext.language.java.members.Method
import tools.vitruvius.framework.change.description.ConcreteChange
import tools.vitruvius.domains.java.echange.feature.JavaFeatureEChange
import tools.vitruvius.framework.change.echange.feature.reference.UpdateReferenceEChange
import org.emftext.language.java.statements.StatementsPackage
import tools.vitruvius.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference
import org.emftext.language.java.statements.Statement
import tools.vitruvius.framework.change.description.TransactionalChange
import tools.vitruvius.framework.correspondence.CorrespondenceModel
import tools.vitruvius.framework.change.processing.ChangeProcessorResult
import tools.vitruvius.framework.change.processing.impl.AbstractChangeProcessor
import java.util.ArrayList

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
						return Java2PcmMethodBodyChangePreprocessor.this.
							executeClassMethodBodyChangeRefiner(correspondenceModel, userInteracting, compositeChange);
					}
				});
			return new ChangeProcessorResult(change, #[command]); // VitruviusChangeFactory.instance.createEmptyChange(change.URI), #[command]);
		}
		return new ChangeProcessorResult(change, #[]);
	}

	def match(TransactionalChange change) {
		val eChanges = new ArrayList<JavaFeatureEChange<?, ?>>();
		for (eChange : change.EChanges) {
			if (eChange instanceof UpdateReferenceEChange<?>) {
				if (eChange.isContainment) {
					if (eChange instanceof JavaFeatureEChange<?, ?>) {
						val typedChange = eChange as JavaFeatureEChange<?, ?>;
						eChanges += typedChange;
					}
				}
			}
		}
		if (eChanges.size != change.EChanges.size) {
			return false
		}

		val firstChange = eChanges.get(0);
		if (!(firstChange.oldAffectedEObject instanceof Method) || !(firstChange.affectedEObject instanceof Method)) {
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

		val deleteChanges = new ArrayList<RemoveEReference<?, ?>>;
		eChanges.forEach [
			if (it instanceof RemoveEReference<?, ?>) {
				val typedChange = it as RemoveEReference<?, ?>;
				deleteChanges += typedChange
			}
		]
		val addChanges = new ArrayList<InsertEReference<?, ?>>;
		eChanges.forEach [
			if (it instanceof InsertEReference<?, ?>) {
				val typedChange = it as InsertEReference<?, ?>;
				addChanges += typedChange
			}
		]

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
		val JavaFeatureEChange<?, ?> eFeatureChange = emfChange.getEChanges().get(0) as JavaFeatureEChange<?, ?>;
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
		