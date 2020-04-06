package tools.vitruv.dsls.commonalities.generator

import java.util.Collections
import java.util.HashMap
import java.util.LinkedHashSet
import java.util.Map
import java.util.Optional
import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.EqualsHashCode
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtend.lib.annotations.ToString
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.extensions.Containment

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ParticipationContextHelper extends ReactionsGenerationHelper {

	// Cached for the current generation context:
	val Map<Participation, ParticipationRoot> participationRoots = new HashMap
	val Map<Participation, Optional<ParticipationContext>> participationContexts = new HashMap
	val Map<CommonalityReferenceMapping, ParticipationRoot> referenceParticipationRoots = new HashMap
	val Map<CommonalityReferenceMapping, Optional<ParticipationContext>> referenceParticipationContexts = new HashMap

	package new() {
	}

	/**
	 * Represents a participation in the context of a specific containment
	 * hierarchy.
	 * <p>
	 * If a commonality is referenced by other commonalities, its
	 * participations need to be matched in differently rooted containment
	 * hierarchies: The participation may specify a containment hierarchy that
	 * roots inside a <code>Resource</code>, and/or it may be referenced by
	 * external commonality reference mappings, which each specify different
	 * containment contexts for the participation's root objects. This class
	 * represents the participation adapted to one of those various contexts.
	 */
	@FinalFieldsConstructor
	@EqualsHashCode
	@ToString
	public static class ParticipationContext {

		@Accessors(PUBLIC_GETTER)
		val Participation participation
		@Accessors(PUBLIC_GETTER)
		val ParticipationRoot root // not null
		@Accessors(PUBLIC_GETTER)
		val Set<Containment> nonRootContainments = new LinkedHashSet
	
		def getAllContainments() {
			return root.containments + nonRootContainments
		}

		def isForReferenceMapping() {
			return (referenceMapping !== null)
		}

		def getReferenceMapping() {
			return root.referenceMapping
		}

		def getReferencingCommonality() {
			return referenceMapping?.containingCommonality
		}

		/**
		 * Note: In the context of an external reference mapping, this may
		 * include participation classes that originate from an external
		 * participation.
		 */
		def getParticipationClasses() {
			// Note: Contains no duplicates, since there is exactly one
			// containment relationship for every participation class
			return Collections.singleton(rootContainerClass)
				+ allContainments.map[contained]
		}

		def getRootContainerClass() {
			return root.classes.last
		}

		def isRootContainerClass(ParticipationClass participationClass) {
			return (participationClass == rootContainerClass)
		}

		def isRootClass(ParticipationClass participationClass) {
			return root.isRootClass(participationClass)
		}
	}

	def private calculateNonRootContainments(ParticipationContext participationContext) {
		val participation = participationContext.participation
		val participationRoot = participation.participationRoot
		participationContext.nonRootContainments += participation.containments.filter [
			!participationRoot.isRootClass(container) && !participationRoot.isRootClass(contained)
		]
	}

	/**
	 * The variable <code>root</code> portion of a participation's containment
	 * context.
	 * <p>
	 * In the context of participation matching the term 'root' has a different
	 * meaning than the term 'root (container) class(es)':</br>
	 * Usually a participation will specify <code>Resource</code> as its root
	 * container class. This Resource participation class is then also the
	 * 'root' of the participation. However, while participations only support
	 * a single Resource root container class (TODO currently), their 'root'
	 * may actually comprise multiple participation classes. For example, when
	 * marking a participation class as <code>root</code> (TODO not actually
	 * supported yet) or as <code>singleton</code> (via the keyword
	 * <code>single</code>) (TODO not actually supported yet), then this
	 * participation class resembles the <code>head</code> of a chain of root
	 * participation classes, in which each class in that chain is related with
	 * another root class via a containment relationship.
	 * <p>
	 * For example, in a participation of the form <code>{'A', 'B', 'single C',
	 * 'Resource'}</code> with containment relations <code>{'A in C', 'B in C',
	 * 'C in Resource'}</code>, the <code>'Resource'</code> class is the root
	 * container, <code>{'C', 'Resource'}</code> is the set of root classes and
	 * <code>{'A', 'B'}</code> is the set of non-root classes.
	 * <p>
	 * When commonalities are referenced by other commonalities (in the form of
	 * commonality references), the mappings for these references specify
	 * different roots for the referenced participations. These roots
	 * substitute the referenced participation's own root during the matching
	 * process.
	 * <p>
	 * It is also possible for a participation to not specify any root (i.e. by
	 * not specifying any participation class of type <code>Resource</code>).
	 * In that case the participation can only exist in contexts in which its
	 * commonality is externally referenced by other commonalities.
	 */
	@EqualsHashCode
	@ToString
	public static class ParticipationRoot {

		/**
		 * The external commonality reference mapping that specifies this root,
		 * or <code>null</code> if this root is specified by the participation
		 * itself.
		 */
		@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
		var CommonalityReferenceMapping referenceMapping
		/**
		 * Note: In the context of an external reference mapping, these
		 * participation classes may originate from an external
		 * participation.
		 * <p>
		 * These are ordered according to their chain of containment
		 * relationships, with the head class as first element.
		 */
		@Accessors(PUBLIC_GETTER)
		val Set<ParticipationClass> classes = new LinkedHashSet
		/**
		 * Containment relationships within the root itself, as well as between
		 * the root's head class and non-root participation classes.
		 */
		@Accessors(PUBLIC_GETTER)
		val Set<Containment> containments = new LinkedHashSet

		/**
		 * Whether the root is empty.
		 * <p>
		 * For instance, this is the case if the participation does not specify
		 * a Resource class.
		 */
		def isEmpty() {
			return classes.empty
		}

		def isRootClass(ParticipationClass participationClass) {
			return classes.contains(participationClass)
		}

		/**
		 * Gets the root participation class that is located at the boundary
		 * between root and non-root participation classes.
		 * <p>
		 * All containment relationships between root and non-root classes use
		 * this class as their container.
		 */
		def getHead() {
			return classes.head
		}

		/**
		 * Gets all non-root participation classes that are located at the
		 * boundary between non-root classes and the root's head class.
		 */
		def getNonRootBoundaryClasses() {
			return boundaryContainments.map[contained]
		}
	
		/**
		 * Gets all containment relations between the root's head class and
		 * non-root classes.
		 */
		def getBoundaryContainments() {
			val rootHead = head
			return containments.filter[container == rootHead]
		}
	}

	/**
	 * Optional: Empty if the participation specifies no containment context.
	 */
	def getParticipationContext(Participation participation) {
		return participationContexts.computeIfAbsent(participation) [
			val participationRoot = participation.participationRoot
			if (participationRoot.empty) {
				return Optional.empty
			}

			if (participation.nonRootClasses.empty) {
				// Participation has no non-root classes
				// TODO enforce that this is never the case via validation?
				return Optional.empty
			}
		
			return Optional.of(new ParticipationContext(participation, participationRoot) => [
				calculateNonRootContainments()
			])
		]
	}

	/**
	 * Gets the participation's root.
	 * <p>
	 * Empty if the participation does not specify a root.
	 */
	def getParticipationRoot(Participation participation) {
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
		// TODO We also interpret 'single' as root marker, but that isn't
		// supported yet either.
		return false
	}

	/**
	 * Optional: Empty if no valid participation context is found.
	 */
	def getReferenceParticipationContext(CommonalityReferenceMapping mapping) {
		return referenceParticipationContexts.computeIfAbsent(mapping) [
			val referencedParticipation = mapping.referencedParticipation
			if (referencedParticipation === null) {
				return Optional.empty
			}

			val referenceParticipationRoot = mapping.referenceParticipationRoot
			if (referenceParticipationRoot.empty) {
				return Optional.empty
			}

			// Ensure that the referenced participation has non-root classes:
			// TODO move into validation?
			val nonRootBoundaryClasses = referenceParticipationRoot.nonRootBoundaryClasses
			if (nonRootBoundaryClasses.empty) {
				return Optional.empty
			}

			// Check that all referenced participation classes are assignment compatible:
			// TODO move into validation?
			if (!nonRootBoundaryClasses.filter[!mapping.isAssignmentCompatible(it)].empty) {
				return Optional.empty
			}
		
			return Optional.of(new ParticipationContext(referencedParticipation, referenceParticipationRoot) => [
				calculateNonRootContainments()
			])
		]
	}

	def private static getReferencedParticipation(CommonalityReferenceMapping mapping) {
		val participationDomainName = mapping.participation.domainName
		val referencedCommonality = mapping.referencedCommonality
		return referencedCommonality.participations.findFirst [
			it.domainName == participationDomainName
			// TODO Validation that there is a matching participation for every reference mapping
			// TODO Support for multiple matching referenced participations? (i.e. multiple participations with the
			// same domain)
		]
	}

	def private isAssignmentCompatible(CommonalityReferenceMapping mapping, ParticipationClass referencedClass) {
		val referenceType = mapping.reference.type
		return referenceType.isSuperTypeOf(referencedClass.superMetaclass)
	}

	def private getReferenceParticipationRoot(CommonalityReferenceMapping mapping) {
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
	def private getReferenceContainments(CommonalityReferenceMapping mapping) {
		val participation = mapping.referencedParticipation
		if (participation === null) return Collections.emptyList

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
	 */
	def private getNonRootBoundaryClasses(Participation participation) {
		val participationRoot = participation.participationRoot
		if (participationRoot.empty) {
			return participation.rootContainerClasses
		} else {
			return participationRoot.nonRootBoundaryClasses
		}
	}

	def getNonRootClasses(Participation participation) {
		val participationRoot = participation.participationRoot // can be empty
		if (participationRoot.empty) {
			return participation.classes
		} else {
			val rootClasses = participationRoot.classes
			return participation.classes.filter[!rootClasses.contains(it)]
		}
	}

	def isRootClass(ParticipationClass participationClass) {
		return participationClass.participation.participationRoot.isRootClass(participationClass)
	}
}