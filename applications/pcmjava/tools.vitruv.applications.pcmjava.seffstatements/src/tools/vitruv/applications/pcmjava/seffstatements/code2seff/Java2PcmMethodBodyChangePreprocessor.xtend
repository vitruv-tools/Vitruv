package tools.vitruv.applications.pcmjava.seffstatements.code2seff

import tools.vitruv.framework.util.command.TransformationResult
import tools.vitruv.framework.userinteraction.UserInteracting
import org.palladiosimulator.pcm.repository.BasicComponent
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding
import org.emftext.language.java.members.Method
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.domains.java.echange.feature.JavaFeatureEChange
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange
import org.emftext.language.java.statements.StatementsPackage
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import org.emftext.language.java.statements.Statement
import tools.vitruv.framework.correspondence.CorrespondenceModel
import java.util.ArrayList
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.MetamodelPair
import org.emftext.language.java.JavaPackage
import org.palladiosimulator.pcm.PcmPackage
import tools.vitruv.framework.change.processing.impl.AbstractChangePropagationSpecification

class Java2PcmMethodBodyChangePreprocessor extends AbstractChangePropagationSpecification {
	private val Code2SEFFFactory code2SEFFfactory;
	private val MetamodelPair metamodelPair;
	
	new(UserInteracting userInteracting, Code2SEFFFactory code2SEFFfactory) {
		super(userInteracting);
		this.metamodelPair = new MetamodelPair(JavaPackage.eNS_URI, PcmPackage.eNS_URI);
		this.code2SEFFfactory = code2SEFFfactory
	}

	override getMetamodelPair() {
		return metamodelPair;
	}
	
	override propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		if (doesHandleChange(change, correspondenceModel)) {
			val compositeChange = change as CompositeTransactionalChange;
			// TODO HK We should exchange the change with an empty one here
			return executeClassMethodBodyChangeRefiner(correspondenceModel, userInteracting, compositeChange);
		}
		return new TransformationResult();
	}

	override doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		if (!(change instanceof CompositeTransactionalChange)) {
			return false;
		}
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
		UserInteracting userInteracting, CompositeTransactionalChange compositeChange) {
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
		