package tools.vitruvius.applications.jmljava.synchronizers.jml

import tools.vitruvius.domains.jml.language.ConcreteSyntaxHelper
import tools.vitruvius.domains.jml.language.jML.JMLInvariantExpression
import tools.vitruvius.domains.jml.language.jML.JMLMemberModifier
import tools.vitruvius.domains.jml.language.jML.JMLPackage
import tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier
import tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
import tools.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyCorrespondences
import tools.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import tools.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener
import tools.vitruvius.framework.util.command.TransformationResult
import tools.vitruvius.framework.userinteraction.UserInteractionType
import java.util.ArrayList
import java.util.Collection
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature.Setting
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.statements.LocalVariableStatement

class JMLMemberDeclarationWithModifierTransformations extends JML2JavaTransformationsBase {

	private static val LOGGER = Logger.getLogger(JMLMemberDeclarationWithModifierTransformations)

	public new(ShadowCopyFactory shadowCopyFactory, SynchronisationAbortedListener synchronisationAbortedListener) {
		super(shadowCopyFactory, synchronisationAbortedListener)
	}

	override protected getLogger() {
		LOGGER
	}

	override getClassOfMappedEObject() {
		MemberDeclWithModifier
	}

	override setCorrespondenceForFeatures() {
		// nothing to do here
	}

	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {

		val changedObjects = new ArrayList<EObject>()
		LOGGER.trace(
			"CreateNonRootEObjectInList\t" + oldAffectedEObject + "." + affectedReference.name + " (" + newValue +
				" @ " + index + ")")

		if (affectedReference == JMLPackage.eINSTANCE.memberDeclWithModifier_JmlModifiers) {

			val newJMLMemberModifier = newValue as JMLMemberModifier
			if (newJMLMemberModifier.modifier == JMLSpecMemberModifier.HELPER) {
				val memberDecl = (oldAffectedEObject as MemberDeclWithModifier).modelInstanceElement
				memberDecl.jmlModifiers.add(index, newJMLMemberModifier)
				changedObjects.add(memberDecl)
			} else if (newJMLMemberModifier.modifier == JMLSpecMemberModifier.PURE) {
//				// handle Java methods and model methods
//				val correspondingJavaMethod = getSingleCorrespondingEObjectOfType(affectedEObject, ClassMethod)
//				if (correspondingJavaMethod == null) {
//					// model method
//				} else {
//					// regular method
//					if (correspondingJavaMethod.statements.exists[CommonSynchronizerTasksJML.isStatementAssignmentToFieldOrCallToNonPureMethod(it, correspondenceInstance)]) {
//						userInteracting.showMessage(UserInteractionType.MODAL, "The modifier can not be added since the method does not have the query property.")
//						return createTransformationChangeResultAborted()
//					}
//				}
				val shadowCopy = shadowCopyFactory.create(correspondenceModel)
				val affectedObj = shadowCopy.shadowCopyCorrespondences.getJMLElement(
					oldAffectedEObject as MemberDeclWithModifier)
				shadowCopy.setupShadowCopyWithJMLSpecifications(false)
				val javaMember = shadowCopy.shadowCopyCorrespondences.getMember(
					affectedObj.eContainer as JMLSpecifiedElement)
				val result = CommonSynchronizerTasksJML.adjustPureModifiersForMethod(false, true,
					javaMember as ClassMethod, shadowCopy, correspondenceModel)
				if (result.size() == 0) {
					userInteracting.showMessage(UserInteractionType.MODAL,
						"The modifier can not be added since the method does not have the query property.")
					syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
					return new TransformationResult
				}
				changedObjects.addAll(result)

//				val memberDecl = (affectedEObject as MemberDeclWithModifier).modelInstanceElement
//				memberDecl.jmlModifiers.add(index, newJMLMemberModifier)
//				changedObjects.add(memberDecl)
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

		if (affectedReference == JMLPackage.eINSTANCE.memberDeclWithModifier_JmlModifiers) {

			val oldJMLMemberModifier = oldValue as JMLMemberModifier
			if (oldJMLMemberModifier.modifier == JMLSpecMemberModifier.HELPER) {
				val shadowCopy = shadowCopyFactory.create(correspondenceModel)
				val affectedObj = shadowCopy.shadowCopyCorrespondences.getJMLElement(
					oldAffectedEObject as MemberDeclWithModifier)
				shadowCopy.setupShadowCopyWithJMLSpecifications(false)
				val javaMember = shadowCopy.shadowCopyCorrespondences.getMember(
					affectedObj.eContainer as JMLSpecifiedElement)
				val references = shadowCopy.shadowCopyCorrespondences.findReferencesToJavaObject(javaMember)
				if (references.exists[isReferenceFromInvariant(shadowCopy.shadowCopyCorrespondences)]) {
					userInteracting.showMessage(UserInteractionType.MODAL,
						"The modifier can not be deleted since this element is referenced by an invariant.")
					syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
					return new TransformationResult
				}
				val memberDecl = (oldAffectedEObject as MemberDeclWithModifier).modelInstanceElement
				memberDecl.jmlModifiers.remove(index)
				changedObjects.add(memberDecl)
			} else if (oldJMLMemberModifier.modifier == JMLSpecMemberModifier.PURE) {
				val shadowCopy = shadowCopyFactory.create(correspondenceModel)
				val affectedObj = shadowCopy.shadowCopyCorrespondences.getJMLElement(
					oldAffectedEObject as MemberDeclWithModifier)
				shadowCopy.setupShadowCopyWithJMLSpecifications(false)
				val javaMember = shadowCopy.shadowCopyCorrespondences.getMember(
					affectedObj.eContainer as JMLSpecifiedElement)
				var Collection<EObject> result;
				try {
					result = CommonSynchronizerTasksJML.adjustPureModifiersForMethod(true, false,
						javaMember as ClassMethod, shadowCopy, correspondenceModel)
				} catch (CommonSynchronizerTasksJML.OperationNotApplicableException e) {
					userInteracting.showMessage(UserInteractionType.MODAL,
						"The modifier can not be removed since the method is used in the specification " +
							ConcreteSyntaxHelper.convertToConcreteSyntax(e.causingObject as EObject) + ".")
					syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
					return new TransformationResult
				}
				changedObjects.addAll(result)
			}
		}
		return new TransformationResult
	}

	/**
	 * Checks if the given reference is inside an invariant.
	 */
	private static def isReferenceFromInvariant(Setting setting, ShadowCopyCorrespondences correspondences) {
		val possibleJMLDummy = setting.EObject.getParentOfType(LocalVariableStatement)
		if (possibleJMLDummy == null) {
			return false
		}

		val jmlExpression = correspondences.get(possibleJMLDummy)
		if (jmlExpression == null) {
			return false
		}

		return jmlExpression instanceof JMLInvariantExpression
	}

}