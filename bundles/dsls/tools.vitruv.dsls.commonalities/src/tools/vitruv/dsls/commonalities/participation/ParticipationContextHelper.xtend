package tools.vitruv.dsls.commonalities.participation

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Map
import java.util.Optional
import java.util.WeakHashMap
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.OperatorReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.SimpleReferenceMapping

import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@Utility
class ParticipationContextHelper {

	// TODO This is also used during validation. If the participations and mappings can still change, this cache may
	// become outdated. Add adapters to the key objects that invalidate the cache on changes to the object?
	static val Map<Participation, ParticipationRoot> participationRoots = new WeakHashMap
	static val Map<Participation, Optional<ParticipationContext>> participationContexts = new WeakHashMap
	// (CommonalityReference, domain name) -> ParticipationRoot/Context
	static val Map<Pair<CommonalityReference, String>, ParticipationRoot> referenceParticipationRoots = new WeakHashMap
	static val Map<Pair<CommonalityReference, String>, ParticipationContext> referenceParticipationContexts = new WeakHashMap

	/**
	 * Optional: Empty if the participation specifies no root containment context.
	 */
	static def getParticipationContext(Participation participation) {
		return participationContexts.computeIfAbsent(participation) [
			val participationRoot = participation.participationRoot
			// Note: Commonality participations have an implicit root
			// container. Even though their 'participation root' is empty, they
			// implicitly specify an own participation context.
			if (participationRoot.empty && !participation.isCommonalityParticipation) {
				return Optional.empty
			}
			assertTrue(!participation.nonRootClasses.empty) // Ensured via validation
			return Optional.of(new ParticipationContext(participation, participationRoot))
		]
	}

	/**
	 * Gets the participation's root.
	 * <p>
	 * Empty if the participation does not specify a root.
	 */
	static def getParticipationRoot(Participation participation) {
		return participationRoots.computeIfAbsent(participation) [
			val participationRoot = new ParticipationRoot
			if (!participation.hasResourceClass) {
				return participationRoot // Empty root
			}

			// We start at a random class and then walk towards one of the leaf
			// classes:
			val leaf = participation.allClasses.head.leafClasses.head
			// From there we walk along the chain of containers until a class is
			// either marked as 'root' or we are at the Resource root class
			// assert: Participations with a Resource contain only a single root
			// container (enforced via validation)
			// TODO support multiple resource roots
			var current = leaf
			var container = current.declaredContainerClass
			while (!current.hasRootMarker && container !== null) {
				current = container
				container = current.declaredContainerClass
			}
			assertTrue(current.hasRootMarker || current.forResource)
			// The current class (and all of its transitive container classes) are
			// part of the root:
			val rootStart = current
			participationRoot.classes += rootStart
			participationRoot.classes += rootStart.transitiveContainerClasses
			val containments = participation.containments.toList
			participationRoot.rootContainments += containments.filter [
				participationRoot.isRootClass(it.contained) && participationRoot.isRootClass(it.container)
			]
			participationRoot.boundaryContainments += containments.filter [
				!participationRoot.isRootClass(it.contained) && participationRoot.isRootClass(it.container)
			]
			return participationRoot
		]
	}

	private static def boolean hasRootMarker(ParticipationClass participationClass) {
		// TODO The language does not support a 'root marker' yet
		// The singleton marker 'single' indicates the head of the root as well:
		return participationClass.isSingleton
	}

	static def getReferenceParticipationContexts(CommonalityReference reference) {
		return reference.mappings
			.map [participation.domainName].filterNull.toSet
			.map [reference.getReferenceParticipationContext(it)]
	}

	static def getReferenceParticipationContext(CommonalityReference reference, String domainName) {
		val referenceDomainPair = Pair.of(reference, domainName)
		return referenceParticipationContexts.computeIfAbsent(referenceDomainPair) [
			// Throws an exception if there are no mappings for the specified domain:
			val referenceParticipationRoot = reference.getReferenceParticipationRoot(domainName)
			assertTrue(referenceParticipationRoot !== null)
			assertTrue(!referenceParticipationRoot.empty)

			val referenceMappings = referenceParticipationRoot.referenceMappings
			assertTrue(!referenceMappings.empty)
			val referencedParticipation = referenceMappings.head.referencedParticipation
			// assert: The referenced participation classes are assignment compatible. (ensured via validation)
			return new ParticipationContext(referencedParticipation, referenceParticipationRoot)
		]
	}

	private static def getReferenceParticipationRoot(CommonalityReference reference, String domainName) {
		val referenceDomainPair = Pair.of(reference, domainName)
		return referenceParticipationRoots.computeIfAbsent(referenceDomainPair) [
			val mappings = reference.getMappings(domainName)
			if (mappings.empty) {
				throw new IllegalArgumentException('''Reference '«reference.name»' has no mappings for domain '«domainName»'.''')
			}
			if (mappings.size == 1) {
				val mapping = mappings.head
				if (mapping instanceof OperatorReferenceMapping) {
					if (mapping.isAttributeReference) {
						return mapping.attributeReferenceParticipationRoot
					}
				}
			}

			val referenceParticipationRoot = new ParticipationRoot
			referenceParticipationRoot.referenceMappings += mappings

			referenceParticipationRoot.classes += mappings.map[participationClass]
			referenceParticipationRoot.boundaryContainments += mappings.map[referenceContainments].flatten
			return referenceParticipationRoot
		]
	}

	// TODO Support the combination of multiple attribute reference mappings.
	private static def getAttributeReferenceParticipationRoot(OperatorReferenceMapping mapping) {
		assertTrue(mapping.isAttributeReference)
		val referenceParticipationRoot = new ParticipationRoot
		referenceParticipationRoot.referenceMappings += mapping

		// Copy the referenced participation's own root:
		val referencedParticipation = mapping.referencedParticipation
		// Assert: The referenced participation specifies a root context
		assertTrue(!referencedParticipation.participationContext.empty)
		val participationRoot = referencedParticipation.participationRoot
		referenceParticipationRoot.classes += participationRoot.classes
		referenceParticipationRoot.rootContainments += participationRoot.rootContainments
		referenceParticipationRoot.boundaryContainments += participationRoot.boundaryContainments

		// Add attribute reference root and containments:
		referenceParticipationRoot.attributeReferenceRoot = mapping.participationClass
		referenceParticipationRoot.attributeReferenceContainments += mapping.operatorContainments
		return referenceParticipationRoot
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
	private static def dispatch getReferenceContainments(SimpleReferenceMapping mapping) {
		val participation = mapping.referencedParticipation
		assertTrue(participation !== null)
		val container = mapping.participationClass
		// TODO Take reference targets ('via' keyword) into account
		return participation.nonRootBoundaryClasses.map [ contained |
			new ReferenceContainment(container, contained, mapping.reference)
		]
	}

	private static def dispatch getReferenceContainments(OperatorReferenceMapping mapping) {
		return mapping.operatorContainments
	}

	private static def getOperatorContainments(OperatorReferenceMapping mapping) {
		val container = mapping.participationClass
		return mapping.referencedParticipationClasses.map [ contained |
			new OperatorContainment(container, contained, mapping)
		]
	}

	/**
	 * Gets all non-root participation classes that are located at the boundary
	 * between non-root classes and the (containment context specific) root
	 * classes.
	 * <p>
	 * If the given participation does not specify an own root, this returns
	 * the participation's root container classes.
	 * <p>
	 * Since participations are not empty, have at least one root container
	 * class and at least one non-root class, the result is expected to not be
	 * empty.
	 */
	static def getNonRootBoundaryClasses(Participation participation) {
		val participationRoot = participation.participationRoot
		if (participationRoot.empty) {
			return participation.rootContainerClasses
		} else {
			return participationRoot.boundaryContainments.map[contained]
		}
	}

	static def getNonRootClasses(Participation participation) {
		val participationRoot = participation.participationRoot // can be empty
		return participation.allClasses.filter[!participationRoot.isRootClass(it)]
	}

	static def isRootClass(ParticipationClass participationClass) {
		return participationClass.participation.participationRoot.isRootClass(participationClass)
	}

	static def getNonRootContainments(Participation participation) {
		val participationRoot = participation.participationRoot
		return participation.containments.filter [
			!participationRoot.isRootClass(container) && !participationRoot.isRootClass(contained)
		]
	}
}
