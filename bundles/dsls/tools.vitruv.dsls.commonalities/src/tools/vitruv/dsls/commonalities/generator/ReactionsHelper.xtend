package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.ReactionTypeProvider
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.TriggerBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelManagement
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*

@Utility
package class ReactionsHelper {

	// Picks the correct 'inserted as root' trigger depending on whether the given change class belongs to a
	// commonality or a regular participation class
	def static afterElementInsertedAsRoot(TriggerBuilder reactionTriggerBuilder, EClass changeClass) {
		if (changeClass.ESuperTypes.contains(IntermediateModelBasePackage.eINSTANCE.intermediate)) {
			// trigger for Commonality root insert:
			reactionTriggerBuilder.afterElement(changeClass).insertedIn(
				IntermediateModelBasePackage.eINSTANCE.root_Intermediates)
		} else {
			// trigger for non-commonality participation class root insert:
			reactionTriggerBuilder.afterElement(changeClass).insertedAsRoot
		}
	}

	def static assignStagingId(extension RoutineTypeProvider typeProvider, XFeatureCall element) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = element
			feature = typeProvider.findMethod(IntermediateModelManagement, 'claimStagingId').staticExtensionWildcardImported
			explicitOperationCall = true
		]
	}

	// TODO unused
	def static hasResource(extension ReactionTypeProvider typeProvider, XFeatureCall element) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = element
				feature = typeProvider.findMethod(EClass, 'eResource')
			]
			feature = typeProvider.findMethod(ObjectExtensions, 'operator_tripleNotEquals')
			rightOperand = XbaseFactory.eINSTANCE.createXNullLiteral
		]
	}
}
