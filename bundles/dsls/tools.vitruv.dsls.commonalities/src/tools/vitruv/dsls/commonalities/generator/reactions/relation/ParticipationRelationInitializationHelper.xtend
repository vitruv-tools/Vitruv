package tools.vitruv.dsls.commonalities.generator.reactions.relation

import org.eclipse.xtext.xbase.XExpression
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.commonalities.participation.ParticipationContext.ContextClass
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.commonalities.generator.reactions.relation.ParticipationRelationOperatorHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import tools.vitruv.dsls.commonalities.language.ParticipationClass

class ParticipationRelationInitializationHelper extends ReactionsGenerationHelper {

	static val RELATION_ENFORCE_METHOD = 'enforce'

	package new() {
	}

	def getParticipationRelationsInitializers(ParticipationContext participationContext, ContextClass contextClass) {
		participationContext.participation.getParticipationRelationsInitializers(participationContext, contextClass)
	}

	private def getParticipationRelationsInitializers(Participation participation,
		ParticipationContext participationContext, ContextClass contextClass) {
		return participation.parts.map [
			getParticipationRelationInitializer(participationContext, contextClass)
		].filterNull
	}

	private def dispatch (TypeProvider)=>XExpression getParticipationRelationInitializer(ParticipationRelation relation,
		ParticipationContext participationContext, ContextClass contextClass) {
		if (relation.isContainment) return null // containments are handled separately
		if (relation.leftParts.isEmpty || relation.rightParts.isEmpty) return null

		val participationClass = contextClass.participationClass
		if (relation.rightParts.indexOf(participationClass) != (relation.rightParts.size - 1)) {
			return null
		}

		// Exclude relations whose operands refer to participation classes that
		// are not involved in the current participation context:
		val relationClasses = relation.leftParts + relation.rightParts
		if (relationClasses.exists [ relationClass |
			!participationContext.classes.exists [
				it.participationClass == relationClass
			]
		]) {
			return null
		}

		return [enforceRelation(relation)]
	}

	private def dispatch (TypeProvider)=>XExpression getParticipationRelationInitializer(
		ParticipationClass pClass, ParticipationContext participationContext, ContextClass contextClass) {
		return null
	}

	private def XExpression enforceRelation(TypeProvider typeProvider, ParticipationRelation participationRelation) {
		val operatorType = participationRelation.operator
		val enforceMethod = operatorType.findMethod(RELATION_ENFORCE_METHOD)
		return participationRelation.callRelationOperation(enforceMethod, typeProvider)
	}
}
