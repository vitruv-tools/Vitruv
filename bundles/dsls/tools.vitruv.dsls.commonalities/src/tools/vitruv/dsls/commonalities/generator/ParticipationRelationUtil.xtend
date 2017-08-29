package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import org.eclipse.emf.ecore.EObject
import java.util.function.Function
import tools.vitruv.dsls.commonalities.language.elements.ParticipationClass

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import tools.vitruv.dsls.commonalities.language.ParticipationRelationDeclaration
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider

@Utility
package class ParticipationRelationUtil {

	def package static createOperatorConstructorCall(ParticipationRelationDeclaration relation,
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
	