package tools.vitruv.dsls.commonalities.generator.reactions.relation

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import tools.vitruv.dsls.commonalities.language.ParticipationClass

@Utility
package class ParticipationRelationOperatorHelper {

	// TODO support nested operators
	private static def constructOperator(ParticipationRelation relation, extension TypeProvider typeProvider) {
		XbaseFactory.eINSTANCE.createXConstructorCall => [
			val operatorType = relation.operator.imported
			constructor = operatorType.findConstructor(EObject.arrayClass, EObject.arrayClass)
			explicitConstructorCall = true
			arguments += expressions(
				XbaseFactory.eINSTANCE.createXListLiteral => [
					elements += relation.leftParts.filter(ParticipationClass).map[variable(correspondingVariableName)]
				],
				XbaseFactory.eINSTANCE.createXListLiteral => [
					elements += relation.rightParts.filter(ParticipationClass).map[variable(correspondingVariableName)]
				]
			)
		]
	}

	static def callRelationOperation(ParticipationRelation relation, JvmOperation operation,
		extension TypeProvider typeProvider) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = constructOperator(relation, typeProvider)
			feature = operation
			explicitOperationCall = true
		]
	}
}
