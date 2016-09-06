package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.java

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ClassOrInterfaceDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.DeclaredException
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.FormalParameterDecl
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MethodDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.RegularModifier
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.correspondences.Java2JMLCorrespondenceAdder
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.correspondences.MatchingModelElementsFinder
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener
import edu.kit.ipd.sdq.vitruvius.framework.util.command.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteractionType
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

import static extension edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*

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
				newValue as TypeReference, correspondenceModel)
		}
		return new TransformationResult
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
				return new TransformationResult
			}

			changedObjects.addAll((affectedEObject as Method).renameInAllJMLSpecifications(newValue as String))

			val jmlMethodDeclarations = correspondenceModel.getCorrespondingEObjects(affectedEObject).
				filter(MethodDeclaration)
			for (jmlMethodDeclaration : jmlMethodDeclarations) {
				LOGGER.trace("Updating " + jmlMethodDeclaration)
				val jmlMethodDeclarationTUIDOld = correspondenceModel.calculateTUIDFromEObject(jmlMethodDeclaration)
				jmlMethodDeclaration.identifier = newValue as String
				correspondenceModel.updateTUID(jmlMethodDeclarationTUIDOld, correspondenceModel.calculateTUIDFromEObject(jmlMethodDeclaration));
				changedObjects.add(jmlMethodDeclaration)
			}
		}
		return new TransformationResult
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
				val jmlMethodDeclarationTUIDOld = correspondenceModel.calculateTUIDFromEObject(jmlMethodDeclaration)
				val jmlParameter = CommonSynchronizerTasks.createJMLParameter(newValue as Parameter)
				jmlMethodDeclaration.parameters.add(index, jmlParameter)

				Java2JMLCorrespondenceAdder.addCorrespondences(newValue as Parameter, jmlParameter,
					correspondenceModel)
				correspondenceModel.updateTUID(jmlMethodDeclarationTUIDOld, correspondenceModel.calculateTUIDFromEObject(jmlMethodDeclaration))

				changedObjects.add(jmlMethodDeclaration)
			}
		} else if (jmlFeature == JMLPackage.eINSTANCE.modifiable_Modifiers) {
			CommonSynchronizerTransformations.createNonRootEObjectInList(oldAffectedEObject, newValue as Modifier,
				correspondenceModel)
			return new TransformationResult
		} else if (jmlFeature == JMLPackage.eINSTANCE.methodDeclaration_Exceptions) {
			val jmlMethodDeclaration = getSingleCorrespondingEObjectOfType(oldAffectedEObject, MethodDeclaration)
			if (jmlMethodDeclaration != null) {
				LOGGER.trace("Creating " + newValue)

				val jmlException = CommonSynchronizerTasks.createJMLException(newValue as NamespaceClassifierReference)
				jmlMethodDeclaration.exceptions.add(index, jmlException)

				Java2JMLCorrespondenceAdder.addCorrespondences(newValue as NamespaceClassifierReference, jmlException,
					correspondenceModel)

				changedObjects.add(jmlMethodDeclaration)
			}
		}
		return new TransformationResult
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
					return new TransformationResult
				}

				val jmlMethodDeclarationOldTUID = correspondenceModel.calculateTUIDFromEObject(jmlMethodDeclaration)

				val jmlParameter = getSingleCorrespondingEObjectOfType(oldValue, FormalParameterDecl)

				correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(jmlParameter.toSet)
				correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(oldValue.toSet)

				jmlMethodDeclaration.parameters.remove(jmlParameter)

				correspondenceModel.updateTUID(jmlMethodDeclarationOldTUID, correspondenceModel.calculateTUIDFromEObject(jmlMethodDeclaration))

				changedObjects.add(jmlMethodDeclaration)
			}
		} else if (jmlFeature == JMLPackage.eINSTANCE.modifiable_Modifiers) {
			val jmlMemberDeclWithModifier = getSingleCorrespondingEObjectOfType(oldAffectedEObject,
				MemberDeclWithModifier)
			if (jmlMemberDeclWithModifier != null) {
				LOGGER.trace("Deleting " + oldValue)

				val jmlModifier = getSingleCorrespondingEObjectOfType(oldValue, RegularModifier)

				correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(jmlModifier.toSet)
				correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(oldValue.toSet)

				jmlMemberDeclWithModifier.modifiers.remove(jmlModifier)

				changedObjects.add(jmlMemberDeclWithModifier)
			}
		} else if (jmlFeature == JMLPackage.eINSTANCE.methodDeclaration_Exceptions) {
			val jmlMethodDeclaration = getSingleCorrespondingEObjectOfType(oldAffectedEObject, MethodDeclaration)
			if (jmlMethodDeclaration != null) {
				LOGGER.trace("Deleting " + oldValue)

				val jmlException = getSingleCorrespondingEObjectOfType(oldValue, DeclaredException)

				correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(jmlException.toSet)
				correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(oldValue.toSet)

				jmlMethodDeclaration.exceptions.remove(jmlException)

				changedObjects.add(jmlMethodDeclaration)
			}
		}
		return new TransformationResult
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
				newValue as Modifier, correspondenceModel)
			return new TransformationResult
		}

		return new TransformationResult
	}
}