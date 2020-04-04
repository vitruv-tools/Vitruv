package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.function.Function
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*

@Utility
package class ParticipationRelationUtil {

	def package static createOperatorConstructorCall(
		ParticipationRelation relation,
		extension TypeProvider typeProvider,
		Function<ParticipationClass, XExpression> participationClassToObject
	) {
		XbaseFactory.eINSTANCE.createXConstructorCall => [
			constructor = relation.operator.imported.findConstructor(EObject.arrayClass, EObject.arrayClass)
			explicitConstructorCall = true
			arguments += expressions(
				XbaseFactory.eINSTANCE.createXListLiteral => [
					elements += relation.leftClasses.map(participationClassToObject)
				],
				XbaseFactory.eINSTANCE.createXListLiteral => [
					elements += relation.rightClasses.map(participationClassToObject)
				]
			)
		]
	}

	def static callRelationOperation(ParticipationRelation relation, JvmOperation operation,
		extension TypeProvider typeProvider,
		Function<ParticipationClass, XExpression> participationClassToObject) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = createOperatorConstructorCall(relation, typeProvider, participationClassToObject)
			feature = operation
			explicitOperationCall = true
		]
	}
}
