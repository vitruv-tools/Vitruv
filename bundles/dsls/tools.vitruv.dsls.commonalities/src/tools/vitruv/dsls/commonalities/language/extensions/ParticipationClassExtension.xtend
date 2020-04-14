package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collections
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.elements.ResourceMetaclass

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationConditionOperandExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationExtension.*

@Utility
package class ParticipationClassExtension {

	def static ParticipationRelation getOptionalParticipationRelation(ParticipationClass participationClass) {
		return participationClass.getOptionalDirectContainer(ParticipationRelation)
	}

	def static getDomain(ParticipationClass participationClass) {
		participationClass.superMetaclass?.domain
	}

	def static getParticipation(ParticipationClass participationClass) {
		if (participationClass.eIsProxy) return null
		return participationClass.getContainer(Participation)
	}

	def static Commonality getParticipatingCommonality(ParticipationClass participationClass) {
		val metaclass = participationClass.superMetaclass
		if (metaclass instanceof Commonality) {
			return metaclass
		}
		return null
	}

	// assumption: contained in max one container
	/**
	 * Returns the participation class that contains the given participation
	 * class according to the specified containment relationships.
	 * <p>
	 * Returns null if no container class is found.
	 */
	def static ParticipationClass getContainerClass(ParticipationClass contained) {
		var container = contained.participation.allContainmentRelations
			.findFirst[leftClasses.contains(contained)]
			?.rightClasses?.head
		if (container === null) {
			container = contained.participation.allContainmentConditions
				.findFirst[leftOperand.participationClass == contained]
				?.rightOperands?.head
				?.participationClass
		}
		return container
	}

	/**
	 * Gets the root participation class that (transitively) contains the given
	 * participation class.
	 * <p>
	 * This returns the given participation class itself if it has no
	 * container.
	 */
	def static getRootContainerClass(ParticipationClass participationClass) {
		var current = participationClass
		var container = current.containerClass
		while (container !== null) {
			current = container
			container = current.containerClass
		}
		return current
	}

	/**
	 * Includes the direct and transitive containers.
	 * <p>
	 * Empty if the given participation class has no container.
	 */
	def static Iterable<ParticipationClass> getTransitiveContainerClasses(ParticipationClass participationClass) {
		val directContainer = participationClass.containerClass
		if (directContainer === null) return Collections.emptyList()
		val directContainerCollection = Collections.singleton(directContainer)
		return directContainerCollection + directContainerCollection.flatMap[transitiveContainerClasses]
	}

	// assumption: every participation class in contained in max one container
	/**
	 * Returns empty if there are no contained classes.
	 */
	def static getContainedClasses(ParticipationClass container) {
		return container.participation.allContainmentRelations
			.filter[rightClasses.contains(container)]
			.flatMap[leftClasses]
		+ container.participation.allContainmentConditions
				.filter[rightOperands.map[participationClass].contains(container)]
				.map[leftOperand].map[participationClass].filterNull
	}

	/**
	 * Starting at the given class, this finds the leaf participation classes
	 * that are (transitively) contained by the given class and don't contain
	 * any other participation classes themselves.
	 * <p>
	 * This returns the given participation class itself if it contains no
	 * other participation classes.
	 */
	def static Iterable<ParticipationClass> getLeafClasses(ParticipationClass participationClass) {
		val containedClasses = participationClass.containedClasses
		if (containedClasses.empty) return Collections.singleton(participationClass)
		else return containedClasses.flatMap[leafClasses]
	}

	/**
	 * Includes the directly and transitively contained classes.
	 * <p>
	 * Empty if the given participation class contains no other classes.
	 */
	def static Iterable<ParticipationClass> getTransitiveContainedClasses(ParticipationClass participationClass) {
		val directContained = participationClass.containedClasses
		return directContained + directContained.flatMap[transitiveContainedClasses]
	}

	def static isForResource(ParticipationClass participationClass) {
		return (participationClass.superMetaclass instanceof ResourceMetaclass)
	}

	def static isInSingletonRoot(ParticipationClass participationClass) {
		return participationClass.participation.singletonRootClasses.exists[it == participationClass]
	}
}
