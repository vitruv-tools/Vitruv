package tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel

import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Root

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class IntermediateContainmentReactionsHelper extends ReactionsGenerationHelper {

	package new() {
	}

	/**
	 * Gets the intermediate's container if it is contained by the given commonality reference, or <code>null</code> if
	 * no such container exists.
	 */
	def XExpression getIntermediateContainer(extension TypeProvider typeProvider, XExpression containedIntermediate,
		CommonalityReference commonalityReference) {
		return XbaseFactory.eINSTANCE.createXIfExpression => [
			^if = isIntermediateContainmentReferenceMatching(typeProvider, containedIntermediate, commonalityReference)
			then = XbaseFactory.eINSTANCE.createXCastedExpression => [
				val containerCommonality = commonalityReference.declaringCommonality
				target = getEContainer(typeProvider, containedIntermediate.copy)
				type = jvmTypeReferenceBuilder.typeRef(containerCommonality.changeClass.javaClassName)
			]
			^else = nullLiteral
		]
	}

	/**
	 * Checks if the reference containing the given intermediate matches the specified commonality reference.
	 */
	def XExpression isIntermediateContainmentReferenceMatching(extension TypeProvider typeProvider,
		XExpression containedIntermediate, CommonalityReference commonalityReference) {
		val commonalityEReference = commonalityReference.correspondingEReference
		return getEContainmentFeature(typeProvider, containedIntermediate)
			.equals(typeProvider.getEReference(commonalityEReference), typeProvider)
	}

	/**
	 * Checks if the given intermediate is contained by the commonality reference of the given container intermediate.
	 * <p>
	 * The container commonality might have multiple commonality references which are able to contain the intermediate.
	 * We therefore not only compare the container, but also check if the containment reference matches the expected
	 * commonality reference.
	 */
	def XExpression isIntermediateContainerMatching(extension TypeProvider typeProvider,
		XExpression containedIntermediate, XExpression containerIntermediate,
		CommonalityReference commonalityReference) {
		val checkContainer = getEContainer(typeProvider, containedIntermediate)
			.equals(containerIntermediate, typeProvider)
		val checkContainmentReference = isIntermediateContainmentReferenceMatching(typeProvider,
			containedIntermediate.copy, commonalityReference)
		return checkContainer.and(checkContainmentReference, typeProvider)
	}

	/**
	 * Checks if the given intermediate is contained by the intermediate model root.
	 */
	def XExpression isIntermediateContainedAtRoot(extension TypeProvider typeProvider, XExpression intermediate) {
		// Note: Also deals with the container being null for some reason.
		return XbaseFactory.eINSTANCE.createXInstanceOfExpression => [
			expression = getEContainer(typeProvider, intermediate)
			type = jvmTypeReferenceBuilder.typeRef(Root)
		]
	}
}
