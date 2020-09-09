package tools.vitruv.extensions.dslruntime.commonalities.matching

import edu.kit.ipd.sdq.commons.util.java.lang.StringUtil
import java.util.Collections
import java.util.Objects
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesFactory
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ResourceAccess

import static com.google.common.base.Preconditions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

/**
 * Matches participation classes to their objects according to the specified
 * containment hierarchy.
 */
class ParticipationMatcher {

	val static Logger logger = Logger.getLogger(ParticipationMatcher)

	val ContainmentContext containmentContext
	val EObject startObject
	val boolean followAttributeReferences
	val CorrespondenceModel correspondenceModel
	val ResourceAccess resourceAccess

	/**
	 * Creates a new {@link ParticipationMatcher}.
	 * 
	 * @param containmentContext
	 * 		the containment context
	 * @param start
	 * 		an existing object inside the containment hierarchy that is used to
	 * 		find the containment hierarchy's root object
	 * @param followAttributeReferences
	 * 		If <code>true</code> and the containment context specifies attribute
	 * 		references, attribute references outgoing from an object that
	 * 		matches the attribute reference root are followed in order to find
	 * 		candidate root objects for the matching to start at. For this to
	 * 		work as expected, the given start object should either already be
	 * 		the attribute reference root, or be contained by it.
	 * @param correspondenceModel
	 * 		the correspondence model
	 */
	new(ContainmentContext containmentContext, EObject startObject, boolean followAttributeReferences,
		CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess) {
		checkNotNull(containmentContext, "containmentContext is null")
		checkNotNull(startObject, "startObject is null")
		checkNotNull(correspondenceModel, "correspondenceModel is null")
		checkNotNull(resourceAccess, "resourceAccess is null")
		this.containmentContext = containmentContext
		this.startObject = startObject
		this.followAttributeReferences = followAttributeReferences
		this.correspondenceModel = correspondenceModel
		this.resourceAccess = resourceAccess
	}

	/**
	 * Matches participation classes to their objects.
	 * <p>
	 * Participations can exist in either their own specified context, in which
	 * case the containment hierarchy is either rooted in a Resource or, for
	 * commonality participations, an intermediate model root. Or they can be
	 * referenced in external commonality reference mappings, in which case the
	 * containment hierarchy is rooted in one or more externally specified
	 * objects that already correspond to some Intermediate. The type of this
	 * Intermediate can be specified via
	 * {@link ContainmentContext#setRootIntermediateType}.
	 * <p>
	 * All other objects that need to be matched are expected to not already
	 * correspond to some Intermediate. Any objects that already correspond to
	 * an Intermediate are therefore ignored.
	 * <p>
	 * A reference mapping may also use an attribute reference operator, in
	 * which case the referenced participation uses its own declared root
	 * context, but the matching needs to additionally check that the attribute
	 * references are established according to that operator.
	 * <p>
	 * The given start object has to already reside inside the containment
	 * hierarchy. It is used to find a candidate for the containment
	 * hierarchy's root object, at which the matching procedure starts.
	 * <p>
	 * There may be multiple matching objects at every containment reference.
	 * Each object choice is followed and checked if it can complete the
	 * expected containment structure. The matching may therefore return
	 * multiple candidate results.
	 * 
	 * @return candidate mappings between participation class names and their
	 * 		objects
	 */
	def Iterable<ParticipationObjects> matchObjects() {
		if (containmentContext.attributeReferenceRootNode !== null) {
			checkArgument(!containmentContext.attributeReferenceEdges.empty,
				"Containment context specifies an attribute reference root node but no attribute reference edges!")
		}
		logger.trace('''ContainmentContext: «containmentContext»''')
		logger.trace('''Start object: «startObject»''')
		logger.trace('''followAttributeReferences: «followAttributeReferences»''')
		// TODO Actually support multiple roots
		val rootNode = containmentContext.roots.head
		val candidateRootObjects = startObject.getCandidateRoots(followAttributeReferences)
		logger.trace('''Candidate root objects: «candidateRootObjects»''')

		val candidateMatches = candidateRootObjects.filter [ candidateRootObject |
			candidateRootObject.matchesRootNode()
		].flatMap [ rootObject |
			var rootMatch = new ParticipationObjects
			rootMatch.addObject(rootNode.name, rootObject)
			// TODO Ensure that the returned Iterable is as lazy as possible, so that matching can be aborted once a
			// valid match has been found. However, also ensure that partial matching results are getting cached once
			// they have been calculated in order to not perform the same matching again when calculating all possible
			// combinations of matched objects.
			return matchChilds(rootMatch, rootNode, rootObject, 1)
		]
		if (candidateMatches.empty) {
			logger.trace('''No candidate matches found.''')
		}

		return candidateMatches.map [ match |
			logger.trace('''Candidate match: «match»''')
			if (containmentContext.attributeReferenceRootNode !== null) {
				// Match the attribute reference root node and add its object to the result:
				if (!match.matchAttributeReferenceRoot()) {
					return null
				}
			}
			return match
		].filterNull // filter matches for which we could not match the attribute reference root
		.filter [ match |
			if (containmentContext.attributeReferenceRootNode !== null) {
				// Check that all attribute reference edges are fulfilled:
				return containmentContext.attributeReferenceEdges.forall [ attributeReferenceEdge |
					val attributeEdgeFulfilled = isAttributeReferenceEdgeFulfilled(match, attributeReferenceEdge)
					logger.trace('''Attribute reference edge «attributeReferenceEdge.toSimpleString» fulfilled: «
						attributeEdgeFulfilled»''')
					return attributeEdgeFulfilled
				]
			} else {
				return true
			}
		]
	}

	private def boolean matchesRootNode(EObject candidateRootObject) {
		// TODO Actually support multiple roots
		val rootNode = containmentContext.roots.head
		// The root intermediate type restriction only applies to the root node if no attribute reference root node is
		// specified.
		var EClass rootIntermediateType = null
		if (containmentContext.attributeReferenceRootNode === null) {
			rootIntermediateType = containmentContext.rootIntermediateType
		}
		return rootNode.matchesObject(candidateRootObject, rootIntermediateType, 0)
	}

	// Returns true if the attribute reference root got matched:
	private def boolean matchAttributeReferenceRoot(ParticipationObjects match) {
		val attributeReferenceRootNode = containmentContext.attributeReferenceRootNode
		assertTrue(attributeReferenceRootNode !== null)
		// We use a random attribute reference edge to find the attribute reference root object:
		val attributeReferenceEdge = containmentContext.attributeReferenceEdges.head
		assertTrue(attributeReferenceEdge !== null)
		val containedObject = match.getObject(attributeReferenceEdge.contained.name)
		assertTrue(containedObject !== null)
		assertTrue(match.getObject(attributeReferenceRootNode.name) === null) // not yet matched
		val operator = attributeReferenceEdge.operator
		val containerObject = operator.getContainer(containedObject)
		if (containerObject !== null && attributeReferenceRootNode.matchesObject(containerObject,
			containmentContext.rootIntermediateType, 0)) {
			match.addObject(attributeReferenceRootNode.name, containerObject)
			logger.trace('''Matched attribute reference root: «containerObject»''')
			return true
		}
		logger.trace('''Could not match attribute reference root: «Objects.toString(containerObject)»''')
		return false
	}

	private def boolean isAttributeReferenceEdgeFulfilled(ParticipationObjects match,
		ContainmentContext.OperatorEdge attributeReferenceEdge) {
		val containerObject = match.getObject(attributeReferenceEdge.container.name)
		assertTrue(containerObject !== null) // We expect that attribute reference root has already been matched
		val containedObject = match.getObject(attributeReferenceEdge.contained.name)
		assertTrue(containedObject !== null)
		return attributeReferenceEdge.operator.isContained(containerObject, containedObject)
	}

	/**
	 * Finds candidate starting objects for our matching procedure.
	 * <p>
	 * This searches the given object itself and its containers for the first
	 * object that either already corresponds to some Intermediate (as it is
	 * the case when we match participations in the context of commonality
	 * reference mappings), or is an intermediate model root (as it is the case
	 * when matching commonality participations rooted in their intermediate
	 * model root), or is not contained in any other container (as it is the
	 * case when we match non-commonality participations rooted inside a
	 * Resource). In the latter case, if the object is contained inside a
	 * Resource, this returns a new IntermediateResourceBridge as
	 * representation of that Resource root.
	 * <p>
	 * If the containment context specifies an attribute reference root and we
	 * found an object that already corresponds to some Intermediate, we check
	 * if the object matches the expected attribute reference root and then
	 * query the operators of its attribute reference edges for its contained
	 * objects and continue the search for candidate root objects from there.
	 */
	private def Iterable<? extends EObject> getCandidateRoots(EObject object, boolean followAttributeReferences) {
		assertTrue(object !== null)
		if (object.isIntermediateRoot) {
			return Collections.singleton(object)
		}

		if (!ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(correspondenceModel, object, null,
			Intermediate).empty) {
			val attributeReferenceRootNode = containmentContext.attributeReferenceRootNode
			if (attributeReferenceRootNode !== null) {
				if (followAttributeReferences && attributeReferenceRootNode.matchesObject(object,
					containmentContext.rootIntermediateType, 0)) {
					logger.trace('''Found attribute reference root: «object»''')
					logger.trace('''Following attribute references in order to find candidate root objects ...''')
					// Note on map and flatten: This has the same effect as flatMap, but we cannot use flatMap here,
					// because flatMap expects Iterable<R> as result, but we produce Iterable<? extends R>.
					return containmentContext.attributeReferenceEdges
						// Edges may share their operator. We need to invoke each operator only once:
						.map[operator].toSet
						.map[getContainedObjects(object)].flatten.toSet
						.map [ containedObject |
							// We only follow a single layer of attribute references. If we encounter another object
							// that matches the attribute reference node, we abort the search.
							containedObject.getCandidateRoots(false)
						].flatten.toSet
				} else {
					return Collections.emptySet
				}
			} else {
				return Collections.singleton(object)
			}
		}

		var EObject container = object.eContainer
		if (container === null) {
			// If the object is contained inside a resource, we create a ResourceBridge as representation of the
			// Resource root:
			if (object.eResource !== null) {
				val resourceBridge = ResourcesFactory.eINSTANCE.createIntermediateResourceBridge
				setupResourceBridge(resourceBridge)
				resourceBridge.initialiseForModelElement(object)
				return Collections.singleton(resourceBridge)
			} else {
				throw new IllegalStateException("Could not find a valid root object to start the matching at."
					+ " The given start object is not contained inside a resource.")
			}
		}

		// Continue searching the container:
		return container.getCandidateRoots(followAttributeReferences)
	}

	private static def isIntermediateRoot(EObject object) {
		return object.eClass.ESuperTypes.contains(IntermediateModelBasePackage.eINSTANCE.root)
	}

	private def setupResourceBridge(IntermediateResourceBridge resourceBridge) {
		resourceBridge.correspondenceModel = correspondenceModel
		resourceBridge.resourceAccess = resourceAccess
		// intermediateNS gets setup later
	}

	/**
	 * For each outgoing containment reference of the current node there may be
	 * multiple matching candidate objects. Objects that have already been
	 * matched are ignored. Each remaining object choice is followed and
	 * checked if it can complete the expected containment structure.
	 * <p>
	 * The resulting candidate matches for the individual containment
	 * references are combined via their cartesian product. Any combinations
	 * which would match the same object to more than one node are ignored.
	 * <p>
	 * The result is a set of candidate matches for the current parent match
	 * and node.
	 */
	private def Iterable<ParticipationObjects> matchChilds(ParticipationObjects parentMatch,
		ContainmentContext.Node currentNode, EObject currentObject, int depth) {
		val nextContainments = containmentContext.getContainments(currentNode).toList
		if (nextContainments.empty) {
			// We reached a leaf inside the containment context, so there are no more child nodes to match:
			return Collections.singleton(parentMatch)
		}
		// For each containment reference, determine the candidate matches:
		return nextContainments.map [ containment |
			// TODO This is not lazy currently (see toList) to avoid doing the same matching multiple times for
			// subsequent iterations. Ideally this should be lazy, but cache already computed results for later
			// iterations.
			matchContainmentEdge(parentMatch, currentNode, currentObject, containment, depth).toList
				as Iterable<ParticipationObjects>
		].reduce [ prevMatches, nextMatches | // TODO reduce is not lazy!
			// Cartesian product, skipping any incompatible combinations of matches:
			prevMatches.flatMap [ prevMatch |
				nextMatches.filter [ nextMatch |
					prevMatch.canBeMerged(nextMatch)
				].map [ nextMatch |
					prevMatch.copy.merge(nextMatch)
				]
			]
		]
	}

	private def Iterable<ParticipationObjects> matchContainmentEdge(ParticipationObjects parentMatch,
		ContainmentContext.Node currentNode, EObject currentObject, ContainmentContext.Edge containment, int depth) {
		logger.trace('''«indent(depth)»Matching edge «containment.toSimpleString» ...''')
		currentObject.getMatchingObjects(containment, depth)
			.filter [ childObject |
				// Skip already matched objects:
				val alreadyMatched = parentMatch.objects.contains(childObject)
				if (alreadyMatched) {
					logger.trace('''«indent(depth)»Edge «containment.toSimpleString»: Ignoring already matched object «
						childObject»''')
				} else {
					logger.trace('''«indent(depth)»Edge «containment.toSimpleString»: Found matching object «
						childObject»''')
				}
				!alreadyMatched
			].flatMap [ childObject |
				// Create a new match for the picked object:
				val childNode = containment.contained
				val childMatch = parentMatch.copy
				childMatch.addObject(childNode.name, childObject)
				//  Try to complete the match:
				return matchChilds(childMatch, childNode, childObject, depth + 1)
			]
	}

	private def Iterable<? extends EObject> getMatchingObjects(EObject container,
		ContainmentContext.Edge containmentEdge, int depth) {
		var Iterable<? extends EObject> candidateObjects = Collections.emptySet
		if (container.isResourceBridge) {
			candidateObjects = (container as IntermediateResourceBridge).emfResource?.contents
		} else if (containmentEdge instanceof ContainmentContext.ReferenceEdge) {
			val reference = containmentEdge.reference
			val rawValue = container.eGet(reference)
			if (reference.isMany) {
				candidateObjects = (rawValue as Iterable<? extends EObject>)
			} else {
				if (rawValue !== null) {
					candidateObjects = Collections.singleton(rawValue as EObject)
				}
			}
		} else if (containmentEdge instanceof ContainmentContext.OperatorEdge) {
			candidateObjects = containmentEdge.operator.getContainedObjects(container)
		}
		val containedNode = containmentEdge.contained
		return candidateObjects.filter[containedNode.matchesObject(it, null, depth)]
	}

	/**
	 * Checks:
	 * - Object is of expected type.
	 * - If correspondingIntermediateType is specified: A corresponding
	 *   Intermediate of that type has to exist.
	 * - Otherwise: The object does not correspond to any Intermediate instance yet.
	 */
	private def matchesObject(ContainmentContext.Node node, EObject object, EClass correspondingIntermediateType,
		int depth) {
		if (!node.type.isInstance(object)) { // also checks for null object
			logger.trace('''«indent(depth)»Node «node.toSimpleString»: Object is of wrong type «object»''')
			return false
		}

		// Note: Not checking for correspondences for our dynamically created (and only partially setup)
		// ResourceBridge.
		if (!object.isResourceBridge) {
			var correspondingIntermediate = ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(
				correspondenceModel, object, null, Intermediate).head
			if (correspondingIntermediateType != correspondingIntermediate?.eClass) {
				if (correspondingIntermediateType === null) {
					logger.trace('''«indent(depth)»Node «node.toSimpleString»: Object already corresponds to an «
						»Intermediate «object»''')
				} else {
					logger.trace('''«indent(depth)»Node «node.toSimpleString»: Object has no matching Intermediate «
						»correspondence «object»''')
				}
				return false
			}
		}
		logger.trace('''«indent(depth)»Node «node.toSimpleString»: Found matching object «object»''')
		return true
	}

	private static def boolean isResourceBridge(EObject object) {
		return (object instanceof IntermediateResourceBridge)
	}

	private static def indent(int depth) {
		return StringUtil.repeat('  ', depth)
	}
}
