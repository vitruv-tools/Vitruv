package tools.vitruv.dsls.commonalities.generator.reactions.relation

import java.util.Collections
import java.util.function.Function
import org.eclipse.xtext.xbase.XExpression
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.TupleParticipation
import tools.vitruv.dsls.commonalities.language.TupleParticipationPart
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.commonalities.participation.ParticipationContext.ContextClass
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.commonalities.generator.reactions.relation.ParticipationRelationOperatorHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ParticipationRelationInitializationHelper extends ReactionsGenerationHelper {

	static val RELATION_ENFORCE_METHOD = 'enforce'

	package new() {
	}

	def getParticipationRelationsInitializers(ParticipationContext participationContext, ContextClass contextClass) {
		val participation = participationContext.participation
		return participation.getParticipationRelationsInitializers(participationContext, contextClass)
	}

	private def dispatch getParticipationRelationsInitializers(Participation participation,
		ParticipationContext participationContext, ContextClass contextClass) {
		return Collections.emptyList
	}

	private def dispatch getParticipationRelationsInitializers(TupleParticipation participation,
		ParticipationContext participationContext, ContextClass contextClass) {
		return participation.parts.map [
			getParticipationRelationInitializer(participationContext, contextClass)
		].filterNull
	}

	private def dispatch Function<TypeProvider, XExpression> getParticipationRelationInitializer(
		ParticipationRelation relation, ParticipationContext participationContext, ContextClass contextClass) {
		if (relation.isContainment) return null // containments are handled separately
		if (relation.leftClasses.empty || relation.rightClasses.empty) return null

		val participationClass = contextClass.participationClass
		if (relation.rightClasses.indexOf(participationClass) != (relation.rightClasses.size - 1)) {
			return null
		}

		// Exclude relations whose operands refer to participation classes that
		// are not involved in the current participation context:
		val relationClasses = relation.leftClasses + relation.rightClasses
		if (relationClasses.exists [ relationClass |
			!participationContext.classes.exists [
				it.participationClass == relationClass
			]
		]) {
			return null
		}

		return [ extension TypeProvider it |
			enforceRelation(relation)
		]
	}

	private def dispatch Function<TypeProvider, XExpression> getParticipationRelationInitializer(
		TupleParticipationPart part, ParticipationContext participationContext, ContextClass contextClass) {
		return null
	}

	private def XExpression enforceRelation(TypeProvider typeProvider, ParticipationRelation participationRelation) {
		val operatorType = participationRelation.operator.jvmType
		val enforceMethod = operatorType.findMethod(RELATION_ENFORCE_METHOD)
		return participationRelation.callRelationOperation(enforceMethod, typeProvider)
	}
}
