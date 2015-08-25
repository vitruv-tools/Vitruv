package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ClassOrInterfaceDeclaration
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.DeclaredException
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.FormalParameterDecl
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLPackage
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberDeclWithModifier
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MethodDeclaration
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.RegularModifier
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.correspondences.Java2JMLCorrespondenceAdder
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.correspondences.MatchingModelElementsFinder
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.ArrayList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.members.MembersPackage
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.statements.StatementsPackage
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference

class JavaMethodTransformations extends Java2JMLTransformationBase {

	val static LOGGER = Logger.getLogger(JavaMethodTransformations)

	@Inject
	new(ShadowCopyFactory shadowCopyFactory, SynchronisationAbortedListener synchronisationAbortedListener) {
		super(shadowCopyFactory, synchronisationAbortedListener)
	}

	override getClassOfMappedEObject() {
		return Method
	}

	override protected getLogger() {
		return LOGGER
	}

	override setCorrespondenceForFeatures() {
		featureCorrespondenceMap.put(MembersPackage.eINSTANCE.method.getEStructuralFeature("annotationsAndModifiers"),
			JMLPackage.eINSTANCE.modifiable_Modifiers)
		featureCorrespondenceMap.put(MembersPackage.eINSTANCE.method.getEStructuralFeature("parameters"),
			JMLPackage.eINSTANCE.methodDeclaration_Parameters)
		featureCorrespondenceMap.put(MembersPackage.eINSTANCE.method.getEStructuralFeature("name"),
			JMLPackage.eINSTANCE.identifierHaving_Identifier)
		featureCorrespondenceMap.put(MembersPackage.eINSTANCE.method.getEStructuralFeature("typeReference"),
			JMLPackage.eINSTANCE.typed_Type)
		featureCorrespondenceMap.put(MembersPackage.eINSTANCE.method.getEStructuralFeature("exceptions"),
			JMLPackage.eINSTANCE.methodDeclaration_Exceptions)
		featureCorrespondenceMap.put(StatementsPackage.eINSTANCE.statementListContainer_Statements,
			JMLPackage.eINSTANCE.methodDeclaration_Methodbody)
	}

	override replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue) {
		val changedObjects = new ArrayList<EObject>()
		val javaMethod = newAffectedEObject as Method

		LOGGER.trace(
			"UpdateSingleValuedNonContainmentEReference\t" + newAffectedEObject + "." + affectedReference.name + " (" +
				oldValue + " -> " + newValue + ")")

		val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedReference)

		if (jmlFeature == JMLPackage.eINSTANCE.typed_Type) {
			CommonSynchronizerTransformations.replaceNonRootEObjectSingleType(javaMethod, oldValue as TypeReference,
				newValue as TypeReference, blackboard.correspondenceInstance)
		}
		TransformationUtils.saveNonRootEObject(changedObjects)
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val changedObjects = new ArrayList<EObject>()

		LOGGER.trace(
			"UpdateSingleValuedEAttribute\t" + affectedEObject + "." + affectedAttribute.name + " (" + oldValue +
				" -> " + newValue + ")")

		val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedAttribute)
		if (jmlFeature == JMLPackage.eINSTANCE.identifierHaving_Identifier) {
			LOGGER.trace("Updating JML specifications")

			val dummy = (affectedEObject as Method).clone
			dummy.name = newValue as String

			val jmlCoid = dummy.containingConcreteClassifier.
				getSingleCorrespondingEObjectOfType(ClassOrInterfaceDeclaration)
			if (jmlCoid != null &&
				MatchingModelElementsFinder.findMatchingMember(dummy,
					jmlCoid.getChildrenOfType(MemberDeclWithModifier)) != null) {
				LOGGER.info("Aborted transformation because of name clash with JML.")
				userInteracting.showMessage(UserInteractionType.MODAL,
					"There already is a method in JML, which has the same name.");
				this.syncAbortedListener.synchronisationAborted(super.synchAbortChange)
				return
			}

			changedObjects.addAll((affectedEObject as Method).renameInAllJMLSpecifications(newValue as String))

			val jmlMethodDeclarations = blackboard.correspondenceInstance.getAllCorrespondingEObjects(affectedEObject).
				filter(MethodDeclaration)
			for (jmlMethodDeclaration : jmlMethodDeclarations) {
				LOGGER.trace("Updating " + jmlMethodDeclaration)
				val jmlMethodDeclarationTUIDOld = jmlMethodDeclaration.TUID
				jmlMethodDeclaration.identifier = newValue as String
				blackboard.correspondenceInstance.update(jmlMethodDeclarationTUIDOld, jmlMethodDeclaration.TUID);
				changedObjects.add(jmlMethodDeclaration)
			}
		}
		TransformationUtils.saveNonRootEObject(changedObjects)
	}

	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		val changedObjects = new ArrayList<EObject>()

		LOGGER.trace(
			"CreateNonRootEObjectInList\t" + oldAffectedEObject + "." + affectedReference.name + " (" + newValue +
				" @ " + index + ")")

		val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedReference)

		if (jmlFeature == JMLPackage.eINSTANCE.methodDeclaration_Parameters) {
			val jmlMethodDeclaration = getSingleCorrespondingEObjectOfType(oldAffectedEObject, MethodDeclaration)
			if (jmlMethodDeclaration != null) {
				LOGGER.trace("Creating " + newValue)
				val jmlMethodDeclarationTUIDOld = jmlMethodDeclaration.TUID
				val jmlParameter = CommonSynchronizerTasks.createJMLParameter(newValue as Parameter)
				jmlMethodDeclaration.parameters.add(index, jmlParameter)

				Java2JMLCorrespondenceAdder.addCorrespondences(newValue as Parameter, jmlParameter,
					blackboard.correspondenceInstance)
				blackboard.correspondenceInstance.update(jmlMethodDeclarationTUIDOld, jmlMethodDeclaration.TUID)

				changedObjects.add(jmlMethodDeclaration)
			}
		} else if (jmlFeature == JMLPackage.eINSTANCE.modifiable_Modifiers) {
			CommonSynchronizerTransformations.createNonRootEObjectInList(oldAffectedEObject, newValue as Modifier,
				blackboard.correspondenceInstance)
			return
		} else if (jmlFeature == JMLPackage.eINSTANCE.methodDeclaration_Exceptions) {
			val jmlMethodDeclaration = getSingleCorrespondingEObjectOfType(oldAffectedEObject, MethodDeclaration)
			if (jmlMethodDeclaration != null) {
				LOGGER.trace("Creating " + newValue)

				val jmlException = CommonSynchronizerTasks.createJMLException(newValue as NamespaceClassifierReference)
				jmlMethodDeclaration.exceptions.add(index, jmlException)

				Java2JMLCorrespondenceAdder.addCorrespondences(newValue as NamespaceClassifierReference, jmlException,
					blackboard.correspondenceInstance)

				changedObjects.add(jmlMethodDeclaration)
			}
		}
		TransformationUtils.saveNonRootEObject(changedObjects)
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		val changedObjects = new ArrayList<EObject>()

		LOGGER.trace(
			"DeleteNonRootEObjectInList\t" + oldAffectedEObject + "." + affectedReference.name + " (" + oldValue +
				" @ " + index + ")")

		val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedReference)

		if (jmlFeature == JMLPackage.eINSTANCE.methodDeclaration_Parameters) {
			val jmlMethodDeclaration = getSingleCorrespondingEObjectOfType(oldAffectedEObject, MethodDeclaration)
			if (jmlMethodDeclaration != null) {
				LOGGER.trace("Deleting " + oldValue)

				if (!simulateElementRemoval(oldValue as Parameter)) {
					userInteracting.showMessage(UserInteractionType.MODAL,
						"The change is not allowed since this parameter is referenced in this method or in specifications."
					);
					syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
					return
				}

				val jmlMethodDeclarationOldTUID = jmlMethodDeclaration.TUID

				val jmlParameter = getSingleCorrespondingEObjectOfType(oldValue, FormalParameterDecl)

				blackboard.correspondenceInstance.removeDirectAndChildrenCorrespondencesOnBothSides(jmlParameter)
				blackboard.correspondenceInstance.removeDirectAndChildrenCorrespondencesOnBothSides(oldValue)

				jmlMethodDeclaration.parameters.remove(jmlParameter)

				blackboard.correspondenceInstance.update(jmlMethodDeclarationOldTUID, jmlMethodDeclaration.TUID)

				changedObjects.add(jmlMethodDeclaration)
			}
		} else if (jmlFeature == JMLPackage.eINSTANCE.modifiable_Modifiers) {
			val jmlMemberDeclWithModifier = getSingleCorrespondingEObjectOfType(oldAffectedEObject,
				MemberDeclWithModifier)
			if (jmlMemberDeclWithModifier != null) {
				LOGGER.trace("Deleting " + oldValue)

				val jmlModifier = getSingleCorrespondingEObjectOfType(oldValue, RegularModifier)

				blackboard.correspondenceInstance.removeDirectAndChildrenCorrespondencesOnBothSides(jmlModifier)
				blackboard.correspondenceInstance.removeDirectAndChildrenCorrespondencesOnBothSides(oldValue)

				jmlMemberDeclWithModifier.modifiers.remove(jmlModifier)

				changedObjects.add(jmlMemberDeclWithModifier)
			}
		} else if (jmlFeature == JMLPackage.eINSTANCE.methodDeclaration_Exceptions) {
			val jmlMethodDeclaration = getSingleCorrespondingEObjectOfType(oldAffectedEObject, MethodDeclaration)
			if (jmlMethodDeclaration != null) {
				LOGGER.trace("Deleting " + oldValue)

				val jmlException = getSingleCorrespondingEObjectOfType(oldValue, DeclaredException)

				blackboard.correspondenceInstance.removeDirectAndChildrenCorrespondencesOnBothSides(jmlException)
				blackboard.correspondenceInstance.removeDirectAndChildrenCorrespondencesOnBothSides(oldValue)

				jmlMethodDeclaration.exceptions.remove(jmlException)

				changedObjects.add(jmlMethodDeclaration)
			}
		}
		TransformationUtils.saveNonRootEObject(changedObjects)
	}

	override replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects) {
		val changedObjects = new ArrayList<EObject>()

		LOGGER.trace(
			"ReplaceNonRootEObjectInList\t" + affectedEObject + "." + affectedReference.name + " (" + oldValue + " @ " +
				index + " -> " + newValue + ")")

		val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedReference)

		if (jmlFeature == JMLPackage.eINSTANCE.modifiable_Modifiers) {
			CommonSynchronizerTransformations.replaceNonRootEObjectInList(affectedEObject, oldValue as Modifier,
				newValue as Modifier, blackboard.correspondenceInstance)
			return
		}

		TransformationUtils.saveNonRootEObject(changedObjects)
	}
}