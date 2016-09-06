package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.jml

import edu.kit.ipd.sdq.vitruvius.domains.jml.language.ConcreteSyntaxHelper
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.Expression
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLFactory
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLInvariantExpression
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.Utilities
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopy
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener
import edu.kit.ipd.sdq.vitruvius.framework.util.command.TransformationResult
import java.util.ArrayList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.members.Member
import org.emftext.language.java.references.ElementReference
import org.emftext.language.java.references.ReferencesPackage
import org.emftext.language.java.statements.Statement

class JMLInvariantExpressionTransformations extends JML2JavaTransformationsBase {

	private static val LOGGER = Logger.getLogger(JMLInvariantExpressionTransformations)

	new(ShadowCopyFactory shadowCopyFactory, SynchronisationAbortedListener synchronisationAbortedListener) {
		super(shadowCopyFactory, synchronisationAbortedListener)
	}

	override protected getLogger() {
		LOGGER
	}

	override getClassOfMappedEObject() {
		JMLInvariantExpression
	}

	override setCorrespondenceForFeatures() {
		// nothing to do here
	}

	override replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue) {

		LOGGER.info("Starting transformation of ReplaceNonRootEObjectSingle change.")

		val changedObjects = new ArrayList<EObject>()

		if (affectedReference == JMLPackage.eINSTANCE.JMLExpressionHaving_Expr) {

			val affectedModelInstanceObject = (newAffectedEObject as JMLInvariantExpression).getModelInstanceElement
			LOGGER.trace(
				"The expression " + ConcreteSyntaxHelper.convertToConcreteSyntax(oldValue) +
					" has been changed to " + ConcreteSyntaxHelper.convertToConcreteSyntax(newValue) + ".")

			LOGGER.trace("Finding references to other elements in original expression.")
			val shadowCopy = shadowCopyFactory.create(correspondenceModel, true)
			val affectedObj = shadowCopy.shadowCopyCorrespondences.getJMLElement(affectedModelInstanceObject);
			shadowCopy.setupShadowCopyWithJMLSpecifications(true)
			val shadowCopyStatement = shadowCopy.shadowCopyCorrespondences.get(affectedObj)
			val elementReferencesOriginal = shadowCopyStatement.getChildrenOfType(ElementReference)

			LOGGER.trace("Finding references to other elements in changed expression.")
			val shadowCopy2 = shadowCopyFactory.create(correspondenceModel, true)
			val dummyElement = shadowCopy2.shadowCopyCorrespondences.getJMLElement(affectedModelInstanceObject);
			dummyElement.expr = EcoreUtil.copy(newValue) as Expression
			shadowCopy2.setupShadowCopyWithJMLSpecifications(true)
			val shadowCopyStatement2 = shadowCopy2.shadowCopyCorrespondences.get(dummyElement)
			val elementReferencesChanged = shadowCopyStatement2.getChildrenOfType(ElementReference)

			val addedReferences = elementReferencesChanged.filter[changed|
				!elementReferencesOriginal.exists[EcoreUtil.equals(changed, it)]]
			val addedReferencesWithoutHelper = addedReferences.filter[target instanceof Member].filter[
				!(target as Member).hasJMLHelperModifier(shadowCopy2)]

			val removedReferences = elementReferencesOriginal.filter[orig|
				!elementReferencesChanged.exists[EcoreUtil.equals(orig, it)]]
			val removedReferencesWithRemovableHelper = removedReferences.filter[target instanceof Member].filter[
				!(target as Member).referencesFromInvariantsExist(shadowCopy, it)]

			LOGGER.trace("Adding helper modifier to " + addedReferencesWithoutHelper.size + " methods.")
			for (addedReference : addedReferencesWithoutHelper) {
				val jmlElement = shadowCopy2.shadowCopyCorrespondences.get(addedReference.target as Member)
				if (jmlElement != null) {
					val jmlModelInstanceElement = jmlElement.modelInstanceElement
					val newModifier = JMLFactory.eINSTANCE.createJMLMemberModifier
					newModifier.modifier = JMLSpecMemberModifier.HELPER
					val jmlMemberDecl = Utilities.getMemberDecl(jmlModelInstanceElement)
					jmlMemberDecl.jmlModifiers += newModifier
					changedObjects += jmlModelInstanceElement
				}
			}

			LOGGER.trace("Removing helper modifier from " + removedReferencesWithRemovableHelper.size + " methods.")
			for (removedReference : removedReferencesWithRemovableHelper) {
				val jmlElement = shadowCopy.shadowCopyCorrespondences.get(removedReference.target as Member)
				if (jmlElement != null) {
					val jmlModelInstanceElement = jmlElement.modelInstanceElement
					val jmlMemberDecl = Utilities.getMemberDecl(jmlModelInstanceElement)
					jmlMemberDecl.jmlModifiers.removeAll(
						jmlMemberDecl.jmlModifiers.filter[modifier == JMLSpecMemberModifier.HELPER])
					changedObjects += jmlModelInstanceElement
				}
			}

			affectedModelInstanceObject.expr = EcoreUtil.copy(newValue) as Expression

		}
		return new TransformationResult
	}

	private def hasJMLHelperModifier(Member member, ShadowCopy shadowCopy) {
		val jmlMember = shadowCopy.shadowCopyCorrespondences.get(member)
		if (jmlMember == null) {
			return false;
		}

		val jmlMemberDecl = Utilities.getMemberDecl(jmlMember)
		if (jmlMemberDecl.jmlModifiers == null) {
			return false;
		}

		return jmlMemberDecl.jmlModifiers.exists[modifier == JMLSpecMemberModifier.HELPER]
	}

	private def referencesFromInvariantsExist(Member member, ShadowCopy shadowCopy, ElementReference referenceToIgnore) {
		var isReferencedByInvariants = false

		val results = shadowCopy.shadowCopyCorrespondences.findReferencesToJavaObject(member)
		for (setting : results) {
			if (setting.EObject != referenceToIgnore && setting.EObject instanceof ElementReference &&
				setting.EStructuralFeature == ReferencesPackage.eINSTANCE.elementReference_Target) {
				val parentStatement = setting.EObject.getParentOfType(Statement);
				if (parentStatement != null) {
					val jmlExpression = shadowCopy.shadowCopyCorrespondences.get(parentStatement)
					if (jmlExpression != null && jmlExpression instanceof JMLInvariantExpression) {
						isReferencedByInvariants = true
					}
				}

			}
		}

		return isReferencedByInvariants
	}

}
