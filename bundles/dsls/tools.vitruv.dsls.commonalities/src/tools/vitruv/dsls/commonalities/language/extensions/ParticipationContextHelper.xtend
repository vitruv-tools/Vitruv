package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Map
import java.util.Optional
import java.util.WeakHashMap
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@Utility
class ParticipationContextHelper {

	static val Map<Participation, ParticipationRoot> participationRoots = new WeakHashMap
	static val Map<Participation, Optional<ParticipationContext>> participationContexts = new WeakHashMap
	static val Map<CommonalityReferenceMapping, ParticipationRoot> referenceParticipationRoots = new WeakHashMap
	static val Map<CommonalityReferenceMapping, ParticipationContext> referenceParticipationContexts = new WeakHashMap

	/**
	 * Optional: Empty if the participation specifies no containment context.
	 */
	def static getParticipationContext(Participation participation) {
		return participationContexts.computeIfAbsent(participation) [
			val participationRoot = participation.participationRoot
			// Note: Commonality participations have an implicit root
			// container. Even though their 'participation root' is empty, they
			// implicitly specify an own participation context.
			if (participationRoot.empty && !participation.isCommonalityParticipation) {
				return Optional.empty
			}
			// assert: !participation.nonRootClasses.empty (ensured via validation)
			return Optional.of(new ParticipationContext(participation, participationRoot))
		]
	}

	/**
	 * Gets the participation's root.
	 * <p>
	 * Empty if the participation does not specify a root.
	 */
	def static getParticipationRoot(Participation participation) {
		return participationRoots.computeIfAbsent(participation) [
			val participationRoot = new ParticipationRoot
			if (!participation.hasResourceClass) {
				return participationRoot // empty root
			}

			// We start at a random class and then walk towards one of the leaf
			// classes:
			val leaf = participation.classes.head.leafClasses.head
			// From there we walk along the chain of containers until a class is
			// either marked as 'root' or we are at the Resource root class
			// assert: participation with a Resource contain only a single root
			// container (enforced via validation)
			// TODO support multiple resource roots
			var current = leaf
			var container = current.containerClass
			while (!current.hasRootMarker && container !== null) {
				current = container
				container = current.containerClass
			}
			// assert: current.hasRootMarker || current.forResource
			// The current class (and all of its transitive container classes) are
			// part of the root:
			val rootStart = current
			participationRoot.classes += rootStart
			participationRoot.classes += rootStart.transitiveContainerClasses
			participationRoot.containments += participation.containments.filter [
				participationRoot.classes.contains(it.contained) || participationRoot.classes.contains(it.container)
			]
			return participationRoot
		]
	}

	def private static boolean hasRootMarker(ParticipationClass participationClass) {
		// TODO The language does not support a 'root marker' yet
		// The singleton marker 'single' indicates the head of the root as well:
		return participationClass.isSingleton
	}

	/**
	 * Optional: Empty if no valid participation context is found.
	 */
	def static getReferenceParticipationContext(CommonalityReferenceMapping mapping) {
		return referenceParticipationContexts.computeIfAbsent(mapping) [
			val referencedParticipation = mapping.referencedParticipation
			// assert: referencedParticipation != null
			// assert: !referencedParticipation.nonRootBoundaryClasses.empty
			// assert: The referenced participation classes are assignment
			// compatible. (ensured via validation)
			val referenceParticipationRoot = mapping.referenceParticipationRoot
			// assert: !referenceParticipationRoot.empty
			return new ParticipationContext(referencedParticipation, referenceParticipationRoot)
		]
	}

	def private static getReferenceParticipationRoot(CommonalityReferenceMapping mapping) {
		return referenceParticipationRoots.computeIfAbsent(mapping) [
			val referenceParticipationRoot = new ParticipationRoot
			referenceParticipationRoot.referenceMapping = mapping
			// TODO Support for reference mappings that specify roots with more
			// than one class? In case this is supported as some point, we also
			// need to deal with the correspondences for objects of these classes.
			// For example, the correspondence tag that gets created for
			// participation classes currently assumes that its counterpart is the
			// commonality in which it has been defined.
			referenceParticipationRoot.classes += mapping.reference.participationClass
			referenceParticipationRoot.containments += mapping.getReferenceContainments
			return referenceParticipationRoot
		]
	}

	/**
	 * Gets the cross-commonality containment relationships for the given
	 * reference mapping.
	 * <p>
	 * The referenced participation's own root is used to determine the
	 * non-root participation classes at the boundary between root and non-root
	 * classes. These are the classes for which implicit containment
	 * relationships with the root specified by the reference mapping exist.
	 */
	def private static getReferenceContainments(CommonalityReferenceMapping mapping) {
		val participation = mapping.referencedParticipation
		// assert: participation != null
		val container = mapping.reference.participationClass
		return participation.getNonRootBoundaryClasses.map [ contained |
			new Containment(contained, container, mapping.reference)
		]
	}

	/**
	 * Gets all non-root participation classes that are located at the boundary
	 * between non-root classes and the root's head class.
	 * <p>
	 * If the given participation does not specify an own root, this returns
	 * the participation's root container classes.
	 * <p>
	 * Since participations are not empty, have at least one root container
	 * class and at least one non-root class, the result is expected to not be
	 * empty.
	 */
	def static getNonRootBoundaryClasses(Participation participation) {
		val participationRoot = participation.participationRoot
		if (participationRoot.empty) {
			return participation.rootContainerClasses
		} else {
			return participationRoot.boundaryContainments.map[contained]
		}
	}

	def static getNonRootClasses(Participation participation) {
		val participationRoot = participation.participationRoot // can be empty
		return participation.classes.filter[!participationRoot.isRootClass(it)]
	}

	def static isRootClass(ParticipationClass participationClass) {
		return participationClass.participation.participationRoot.isRootClass(participationClass)
	}

	def static getNonRootContainments(Participation participation) {
		val participationRoot = participation.participationRoot
		return participation.containments.filter [
			!participationRoot.isRootClass(container) && !participationRoot.isRootClass(contained)
		]
	}
}
