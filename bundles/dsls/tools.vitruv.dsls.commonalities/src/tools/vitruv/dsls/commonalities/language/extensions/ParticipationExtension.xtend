package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collections
import tools.vitruv.dsls.commonalities.language.Concept
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.SimpleParticipation
import tools.vitruv.dsls.commonalities.language.SimpleTupleParticipationPart
import tools.vitruv.dsls.commonalities.language.TupleParticipation

import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationClassExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationConditionExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationRelationExtension.*

@Utility
class ParticipationExtension {

	def static dispatch Iterable<ParticipationClass> getClasses(SimpleParticipation participation) {
		Collections.singleton(participation.participationClass)
	}

	def static dispatch Iterable<ParticipationClass> getClasses(TupleParticipation participation) {
		participation.parts.flatMap[containedClasses]
	}

	def private static dispatch Iterable<ParticipationClass> getContainedClasses(
		SimpleTupleParticipationPart participationPart) {
		Collections.singleton(participationPart.participationClass)
	}

	def private static dispatch Iterable<ParticipationClass> getContainedClasses(
		ParticipationRelation participationPart) {
		participationPart.leftClasses + participationPart.rightClasses
	}

	def static dispatch getDomainName(SimpleParticipation participation) {
		participation?.participationClass?.superMetaclass?.domain?.name
	}

	def static dispatch getDomainName(TupleParticipation participation) {
		participation.domainName
	}

	def static getDomain(Participation participation) {
		participation.classes.head?.superMetaclass?.domain
	}

	def static isCommonalityParticipation(Participation participation) {
		(participation.domain instanceof Concept)
	}

	def static dispatch Iterable<ParticipationRelation> getAllRelations(SimpleParticipation participation) {
		return #[]
	}

	def static dispatch Iterable<ParticipationRelation> getAllRelations(TupleParticipation participation) {
		return participation.parts.flatMap[relations]
	}

	def private static dispatch Iterable<ParticipationRelation> getRelations(
		SimpleTupleParticipationPart participationPart) {
		return #[]
	}

	def private static dispatch Iterable<ParticipationRelation> getRelations(ParticipationRelation participationPart) {
		return #[participationPart]
	}

	def static getAllContainmentRelations(Participation participation) {
		return participation.allRelations.filter[isContainment]
	}

	def static getAllNonContainmentRelations(Participation participation) {
		return participation.allRelations.filter[!isContainment]
	}

	def static getAllContainmentConditions(Participation participation) {
		return participation.conditions.filter[isContainment]
	}

	def static getAllNonContainmentConditions(Participation participation) {
		return participation.conditions.filter[!isContainment]
	}

	def static getContainments(Participation participation) {
		return participation.allContainmentRelations.flatMap[getContainments]
			+ participation.allContainmentConditions.map[getContainment].filterNull
	}

	// If the participation has a Resource class, that class needs to be the
	// only root container class. Otherwise, the participation may have
	// multiple non-Resource root container classes.
	// TODO support multiple Resource roots?
	def static getRootContainerClasses(Participation participation) {
		return participation.classes.map[it.rootContainerClass].toSet
	}

	def static hasResourceClass(Participation participation) {
		return (participation.resourceClass !== null)
	}

	// There can only be at most one resource class per participation (currently).
	def static getResourceClass(Participation participation) {
		return participation.classes.findFirst[isForResource]
	}

	def static hasSingletonClass(Participation participation) {
		return (participation.singletonClass !== null)
	}

	// There can only be at most one singleton class per participation (currently).
	def static getSingletonClass(Participation participation) {
		return participation.classes.findFirst[isSingleton]
	}

	/**
	 * A class marked as singleton and its containers also act as root of the
	 * participation. This returns these classes.
	 */
	def static getSingletonRootClasses(Participation participation) {
		val singletonClass = participation.singletonClass
		if (singletonClass === null) return Collections.emptyList
		return Collections.singleton(singletonClass) + singletonClass.transitiveContainerClasses
	}
}
