package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*

@Utility
package class ParticipationRelationUtil {

	def package static createOperatorConstructorCall(
		ParticipationRelation relation,
		extension TypeProvider typeProvider) {
		XbaseFactory.eINSTANCE.createXConstructorCall => [
			val operatorType = relation.operator.jvmType.imported
			constructor = operatorType.findConstructor(EObject.arrayClass, EObject.arrayClass)
			explicitConstructorCall = true
			arguments += expressions(
				XbaseFactory.eINSTANCE.createXListLiteral => [
					elements += relation.leftClasses.map[variable(correspondingVariableName)]
				],
				XbaseFactory.eINSTANCE.createXListLiteral => [
					elements += relation.rightClasses.map[variable(correspondingVariableName)]
				]
			)
		]
	}

	def static callRelationOperation(ParticipationRelation relation, JvmOperation operation,
		extension TypeProvider typeProvider) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = createOperatorConstructorCall(relation, typeProvider)
			feature = operation
			explicitOperationCall = true
		]
	}
}
