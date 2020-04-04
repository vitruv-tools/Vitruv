package tools.vitruv.dsls.commonalities.generator

import java.util.function.Function
import org.eclipse.xtext.xbase.XExpression
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.TupleParticipation
import tools.vitruv.dsls.commonalities.language.TupleParticipationPart
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ParticipationRelationUtil.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ParticipationRelationInitializationBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(ParticipationClass participationClass) {
			return new ParticipationRelationInitializationBuilder(participationClass).injectMembers
		}
	}

	private new(ParticipationClass participationClass) {
		checkNotNull(participationClass, "participationClass is null")
		this.participationClass = participationClass
	}

	// Dummy constructor for Guice
	package new() {
		this.participationClass = null
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	static val RELATION_ENFORCE_METHOD = 'enforce'

	val ParticipationClass participationClass
	extension var TypeProvider typeProvider
	Function<ParticipationClass, XExpression> participationClassToObject

	def hasInitializer() {
		return participationClass.participation.hasRelationInitialization
	}

	def private dispatch hasRelationInitialization(Participation participation) {
		return false
	}

	def private dispatch boolean hasRelationInitialization(TupleParticipation declaration) {
		for (part : declaration.parts) {
			if (part.hasRelationInitialization) {
				return true
			}
		}
		return false
	}

	def private dispatch hasRelationInitialization(TupleParticipationPart declarationPart) {
		return false
	}

	def private dispatch hasRelationInitialization(ParticipationRelation relation) {
		if (relation.isContainment) return false // containments are handled separately
		return relation.leftClasses.size > 0 && relation.rightClasses.size > 0
			&& relation.rightClasses.indexOf(participationClass) == relation.rightClasses.size - 1
			&& relation.operator.findOptionalImplementedMethod(RELATION_ENFORCE_METHOD) !== null
	}

	def getInitializer(TypeProvider typeProvider,
		Function<ParticipationClass, XExpression> participationClassToObject) {
		this.typeProvider = typeProvider
		this.participationClassToObject = participationClassToObject
		return participationClass.participation.initializer
	}

	def private dispatch XExpression getInitializer(Participation participation) {
		return null
	}

	def private dispatch XExpression getInitializer(TupleParticipation declaration) {
		var XExpression result
		for (part : declaration.parts) {
			result = result.join(part.initializer)
		}
		return result
	}

	def private dispatch XExpression getInitializer(ParticipationRelation relation) {
		if (relation.rightClasses.indexOf(participationClass) == relation.rightClasses.size - 1) {
			return relation.enforceRelationCode
		}
		return null
	}

	def private dispatch XExpression getInitializer(TupleParticipationPart part) {
		return null
	}

	def private getEnforceRelationCode(ParticipationRelation participationRelation) {
		val enforceMethod = participationRelation.operator.findOptionalImplementedMethod(RELATION_ENFORCE_METHOD)
		if (enforceMethod !== null) {
			return participationRelation.callRelationOperation(enforceMethod, typeProvider, participationClassToObject)
		}
		return null
	}
}
