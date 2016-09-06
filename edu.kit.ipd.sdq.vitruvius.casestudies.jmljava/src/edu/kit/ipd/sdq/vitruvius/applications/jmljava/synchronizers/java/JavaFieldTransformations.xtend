package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.java

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ClassOrInterfaceDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.VariableDeclarator
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
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersPackage
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.types.TypeReference

import static extension edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil.*

class JavaFieldTransformations extends Java2JMLTransformationBase {

	static val LOGGER = Logger.getLogger(JavaFieldTransformations)

	@Inject
	new(ShadowCopyFactory shadowCopyFactory, SynchronisationAbortedListener synchronisationAbortedListener) {
		super(shadowCopyFactory, synchronisationAbortedListener)
	}

	override protected getLogger() {
		return LOGGER
	}

	override getClassOfMappedEObject() {
		return Field
	}

	override setCorrespondenceForFeatures() {
		featureCorrespondenceMap.put(MembersPackage.eINSTANCE.method.getEStructuralFeature("annotationsAndModifiers"),
			JMLPackage.eINSTANCE.modifiable_Modifiers)
		featureCorrespondenceMap.put(MembersPackage.eINSTANCE.field.getEStructuralFeature("name"),
			JMLPackage.eINSTANCE.identifierHaving_Identifier)
		featureCorrespondenceMap.put(MembersPackage.eINSTANCE.method.getEStructuralFeature("typeReference"),
			JMLPackage.eINSTANCE.typed_Type)
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val changedObjects = new ArrayList<EObject>()

		LOGGER.trace(
			"UpdateSingleValuedEAttribute\t" + affectedEObject + "." + affectedAttribute.name + " (" + oldValue + " -> " +
				newValue + ")")

		val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedAttribute)
		if (jmlFeature == JMLPackage.eINSTANCE.identifierHaving_Identifier) {
			
			// perform renaming of field even if there is no corresponding field
			// (the field might be used in specifications anyway)
			val dummy = (affectedEObject as Field).clone
			dummy.name = newValue as String

			val jmlCoid = dummy.containingConcreteClassifier.
				getSingleCorrespondingEObjectOfType(ClassOrInterfaceDeclaration)
			if (jmlCoid != null && MatchingModelElementsFinder.findMatchingMember(dummy, jmlCoid.getChildrenOfType(MemberDeclWithModifier)) != null) {
				LOGGER.info("Aborted transformation because of name clash with JML.")
				userInteracting.showMessage(UserInteractionType.MODAL, "There already is a field in JML, which has the same name.");
				syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
				return new TransformationResult
			}

			changedObjects.addAll((affectedEObject as Field).renameInAllJMLSpecifications(newValue as String))
			
			// rename the corresponding fields
			val jmlVariableDeclarations = correspondenceModel.getCorrespondingEObjects(affectedEObject).
				filter(VariableDeclarator).filter[identifier.equals(oldValue as String)]
			for (jmlVariableDeclaration : jmlVariableDeclarations) {
				LOGGER.trace("Updating " + jmlVariableDeclaration)
				val oldTUID = correspondenceModel.calculateTUIDFromEObject(jmlVariableDeclaration)
				jmlVariableDeclaration.identifier = newValue as String
				val newTUID = correspondenceModel.calculateTUIDFromEObject(jmlVariableDeclaration)
				correspondenceModel.updateTUID(oldTUID, newTUID)
				changedObjects.add(jmlVariableDeclaration)
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

	override replaceNonRootEObjectSingle(EObject newAffectedEobejct, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue) {
		val changedObjects = new ArrayList<EObject>()
		val javaField = newAffectedEobejct as Field

		LOGGER.trace(
			"UpdateSingleValuedNonContainmentEReference\t" + newAffectedEobejct + "." + affectedReference.name + " (" +
				oldValue + " -> " + newValue + ")")

		val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedReference)

		if (jmlFeature == JMLPackage.eINSTANCE.typed_Type) {
				CommonSynchronizerTransformations.replaceNonRootEObjectSingleType(javaField, oldValue as TypeReference,
					newValue as TypeReference, correspondenceModel)
		}
		return new TransformationResult
	}

}
