package tools.vitruv.dsls.commonalities.generator

import java.util.Collections
import java.util.function.Function
import org.eclipse.xtext.xbase.XExpression
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.TupleParticipation
import tools.vitruv.dsls.commonalities.language.TupleParticipationPart
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ParticipationRelationUtil.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ParticipationRelationInitializationHelper extends ReactionsGenerationHelper {

	static val RELATION_ENFORCE_METHOD = 'enforce'

	package new() {
	}

	def getParticipationRelationsInitializers(ParticipationClass participationClass) {
		return participationClass.participation.getParticipationRelationsInitializers(participationClass)
	}

	def private dispatch getParticipationRelationsInitializers(Participation participation,
		ParticipationClass participationClass) {
		return Collections.emptyList
	}

	def private dispatch getParticipationRelationsInitializers(TupleParticipation participation,
		ParticipationClass participationClass) {
		return participation.parts.map[getParticipationRelationInitializer(participationClass)].filterNull
	}

	def private dispatch Function<TypeProvider, XExpression> getParticipationRelationInitializer(
		ParticipationRelation relation, ParticipationClass participationClass) {
		if (relation.isContainment) return null // containments are handled separately
		if (relation.leftClasses.empty || relation.rightClasses.empty) return null
		if (relation.rightClasses.indexOf(participationClass) == relation.rightClasses.size - 1) {
			return [ extension TypeProvider it |
				enforceRelation(relation)
			]
		}
		return null
	}

	def private dispatch Function<TypeProvider, XExpression> getParticipationRelationInitializer(
		TupleParticipationPart part, ParticipationClass participationClass) {
		return null
	}

	def private XExpression enforceRelation(TypeProvider typeProvider, ParticipationRelation participationRelation) {
		val enforceMethod = participationRelation.operator.findMethod(RELATION_ENFORCE_METHOD)
		return participationRelation.callRelationOperation(enforceMethod, typeProvider)
	}
}
