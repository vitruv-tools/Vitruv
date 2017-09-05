package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.function.Function
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider

import static tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*

@Utility
package class ParticipationRelationUtil {

	def package static createOperatorConstructorCall(ParticipationRelation relation,
		extension RoutineTypeProvider typeProvider,
		Function<ParticipationClass, XFeatureCall> participationClassReferenceProvider) {
			XbaseFactory.eINSTANCE.createXConstructorCall => [
				constructor = relation.operator.imported.findConstructor(EObject.arrayClass, EObject.arrayClass)
				explicitConstructorCall = true
				arguments += expressions(
					XbaseFactory.eINSTANCE.createXListLiteral => [
						elements += relation.leftClasses.map(participationClassReferenceProvider)
					],
					XbaseFactory.eINSTANCE.createXListLiteral => [
						elements += relation.rightClasses.map(participationClassReferenceProvider)
					]
				)
			]
		}
	}
	