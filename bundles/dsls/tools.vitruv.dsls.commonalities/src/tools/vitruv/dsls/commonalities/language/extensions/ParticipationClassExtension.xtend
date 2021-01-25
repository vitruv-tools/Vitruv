package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.List
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.elements.ResourceMetaclass

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.OperandExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationPartExtension.*

@Utility
package class ParticipationClassExtension {

	static def ParticipationRelation getOptionalParticipationRelation(ParticipationClass participationClass) {
		participationClass.getOptionalDirectEContainer(ParticipationRelation)
	}

	static def getDomain(ParticipationClass participationClass) {
		participationClass.superMetaclass?.domain
	}

	static def getParticipation(ParticipationClass participationClass) {
		if (participationClass.eIsProxy) return null
		participationClass.getEContainer(Participation)
	}

	static def Commonality getParticipatingCommonality(ParticipationClass participationClass) {
		val metaclass = participationClass.superMetaclass
		if (metaclass instanceof Commonality) {
			return metaclass
		}
		return null
	}

	// Assertion: Every participation class has at most one container class.
	/**
	 * Returns the participation class that contains the given participation
	 * class according to the specified containment relationships.
	 * <p>
	 * Returns <code>null</code> if no container class is found.
	 */
	static def ParticipationClass getDeclaredContainerClass(ParticipationClass contained) {
		contained.participation.allContainmentRelations
			.findFirst [leftParts.contains(contained)]
			?.declaredContainerClass
		?: contained.participation.allContainmentConditions
			.findFirst[leftOperand.participationClass == contained]
			?.rightOperands?.head
			?.participationClass
	}

	/**
	 * Gets the root participation class that (transitively) contains the given
	 * participation class.
	 * <p>
	 * Returns the given participation class itself if it has no container
	 * class.
	 */
	static def getRootDeclaredContainerClass(ParticipationClass participationClass) {
		var current = participationClass
		var container = current.declaredContainerClass
		while (container !== null) {
			current = container
			container = current.declaredContainerClass
		}
		return current
	}

	/**
	 * Gets all participation classes along the chain of container classes of the given participation class.
	 * <p>
	 * This includes the direct and transitive container classes.
	 * <p>
	 * Empty if the given participation class has no container class.
	 */
	static def Iterable<ParticipationClass> getTransitiveContainerClasses(ParticipationClass participationClass) {
		val directContainer = participationClass.declaredContainerClass
		return if (directContainer !== null) {
			List.of(directContainer) + directContainer.transitiveContainerClasses
		} else {
			emptyList()
		}
	}

	// Assertion: Every participation class has at most one container class.
	/**
	 * Gets the participation classes that are (directly) contained by the given participation class.
	 * <p>
	 * Empty if there are no contained classes.
	 */
	static def getContainedClasses(ParticipationClass container) {
		container.participation.allContainmentRelations
			.filter [declaredContainerClass == container]
			.flatMap [leftParts.filter(ParticipationClass)]
		+ container.participation.allContainmentConditions
			.filter [rightOperands.map [participationClass].contains(container)]
			.map [leftOperand]
			.map [participationClass]
			.filterNull
	}

	/**
	 * Starting at the given class, this finds the leaf participation classes
	 * that are (transitively) contained by the given class and don't contain
	 * any other participation classes themselves.
	 * <p>
	 * This returns the given participation class itself if it contains no
	 * other participation classes.
	 */
	static def Iterable<ParticipationClass> getLeafClasses(ParticipationClass participationClass) {
		val containedClasses = participationClass.containedClasses
		return if (containedClasses.isEmpty) {
			List.of(participationClass)
		} else {
	 		containedClasses.flatMap [leafClasses]
	 	}
	}

	/**
	 * Gets all participation classes that are directly and transitively contained by the given participation class.
	 * <p>
	 * Empty if the given participation class contains no other classes.
	 */
	static def Iterable<ParticipationClass> getTransitiveContainedClasses(ParticipationClass participationClass) {
		val directContained = participationClass.containedClasses
		return directContained + directContained.flatMap [transitiveContainedClasses]
	}

	static def isForResource(ParticipationClass participationClass) {
		participationClass.superMetaclass instanceof ResourceMetaclass
	}

	static def isInSingletonRoot(ParticipationClass participationClass) {
		participationClass.participation.singletonRootClasses.contains(participationClass)
	}
}
