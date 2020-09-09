package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collections
import tools.vitruv.dsls.commonalities.language.Concept
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.SimpleParticipation
import tools.vitruv.dsls.commonalities.language.SimpleTupleParticipationPart
import tools.vitruv.dsls.commonalities.language.TupleParticipation

import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationClassExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationConditionExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationRelationExtension.*

@Utility
package class ParticipationExtension {

	static def dispatch Iterable<ParticipationClass> getClasses(SimpleParticipation participation) {
		Collections.singleton(participation.participationClass)
	}

	static def dispatch Iterable<ParticipationClass> getClasses(TupleParticipation participation) {
		participation.parts.flatMap[containedClasses]
	}

	private static def dispatch Iterable<ParticipationClass> getContainedClasses(
		SimpleTupleParticipationPart participationPart) {
		Collections.singleton(participationPart.participationClass)
	}

	private static def dispatch Iterable<ParticipationClass> getContainedClasses(
		ParticipationRelation participationPart) {
		participationPart.leftClasses + participationPart.rightClasses
	}

	static def dispatch getDomainName(SimpleParticipation participation) {
		participation?.participationClass?.superMetaclass?.domain?.name
	}

	static def dispatch getDomainName(TupleParticipation participation) {
		participation.domainName
	}

	static def getDomain(Participation participation) {
		participation.classes.head?.superMetaclass?.domain
	}

	static def isCommonalityParticipation(Participation participation) {
		return (participation.participationConcept !== null)
	}

	static def Concept getParticipationConcept(Participation participation) {
		val domain = participation.domain
		return (domain instanceof Concept) ? domain : null
	}

	static def dispatch Iterable<ParticipationRelation> getAllRelations(SimpleParticipation participation) {
		return #[]
	}

	static def dispatch Iterable<ParticipationRelation> getAllRelations(TupleParticipation participation) {
		return participation.parts.flatMap[relations]
	}

	private static def dispatch Iterable<ParticipationRelation> getRelations(
		SimpleTupleParticipationPart participationPart) {
		return #[]
	}

	private static def dispatch Iterable<ParticipationRelation> getRelations(ParticipationRelation participationPart) {
		return #[participationPart]
	}

	static def Iterable<ParticipationRelation> getAllContainmentRelations(Participation participation) {
		return participation.allRelations.filter[isContainment]
	}

	static def Iterable<ParticipationRelation> getAllNonContainmentRelations(Participation participation) {
		return participation.allRelations.filter[!isContainment]
	}

	static def Iterable<ParticipationCondition> getAllContainmentConditions(Participation participation) {
		return participation.conditions.filter[isContainment]
	}

	static def Iterable<ParticipationCondition> getAllNonContainmentConditions(Participation participation) {
		return participation.conditions.filter[!isContainment]
	}

	static def getContainments(Participation participation) {
		return participation.allContainmentRelations.flatMap[getContainments]
			+ participation.allContainmentConditions.map[getContainment].filterNull
	}

	// If the participation has a Resource class, that class needs to be the
	// only root container class. Otherwise, the participation may have
	// multiple non-Resource root container classes.
	// TODO support multiple Resource roots?
	static def getRootContainerClasses(Participation participation) {
		return participation.classes.map[it.rootContainerClass].toSet
	}

	static def hasResourceClass(Participation participation) {
		return (participation.resourceClass !== null)
	}

	// There can only be at most one resource class per participation (currently).
	static def getResourceClass(Participation participation) {
		return participation.classes.findFirst[isForResource]
	}

	static def getResourceContainments(Participation participation) {
		return participation.containments.filter[container.isForResource]
	}

	static def hasSingletonClass(Participation participation) {
		return (participation.singletonClass !== null)
	}

	// There can only be at most one singleton class per participation (currently).
	static def getSingletonClass(Participation participation) {
		return participation.classes.findFirst[isSingleton]
	}

	/**
	 * A class marked as singleton and its containers also act as root of the
	 * participation. This returns these classes.
	 */
	static def getSingletonRootClasses(Participation participation) {
		val singletonClass = participation.singletonClass
		if (singletonClass === null) return Collections.emptyList
		return Collections.singleton(singletonClass) + singletonClass.transitiveContainerClasses
	}
}
