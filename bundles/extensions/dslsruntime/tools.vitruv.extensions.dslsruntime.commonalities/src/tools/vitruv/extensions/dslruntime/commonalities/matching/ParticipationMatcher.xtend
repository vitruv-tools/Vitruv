package tools.vitruv.extensions.dslruntime.commonalities.matching

import com.google.common.base.Preconditions
import edu.kit.ipd.sdq.activextendannotations.Utility
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

import static tools.vitruv.framework.util.XtendAssertHelper.*

/**
 * Matches participation classes to their objects according to the specified
 * containment hierarchy.
 */
@Utility
class ParticipationMatcher {

	val static Logger logger = Logger.getLogger(ParticipationMatcher)

	/**
	 * Matches participation classes to their objects according to the given
	 * containment tree.
	 * <p>
	 * Participations can exist in either their own specified context, in which
	 * case the containment hierarchy is either rooted in a Resource or, for
	 * commonality participations, an intermediate model root. Or they can be
	 * referenced in an external commonality reference mapping, in which case
	 * the containment hierarchy is rooted in an externally specified object
	 * that already corresponds to some Intermediate. The type of this
	 * Intermediate can be specified via
	 * {@link ContainmentTree#setRootIntermediateType}.
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
	 * @param containmentTree
	 * 		the containment tree
	 * @param start
	 * 		an existing object inside the containment hierarchy that is used to
	 * 		find the containment hierarchy's root object
	 * @param followAttributeReferences
	 * 		If <code>true</code> and the containment tree specifies attribute
	 * 		references, attribute references outgoing from an object that
	 * 		matches the attribute reference root are followed in order to find
	 * 		candidate root objects for the matching to start at. For this to
	 * 		work as expected, the given start object should either already be
	 * 		the attribute reference root, or be contained by it.
	 * @param correspondenceModel
	 * 		the correspondence model
	 * @return candidate mappings between participation class names and their
	 * 		objects
	 */
	def static Iterable<ParticipationObjects> matchObjects(extension ContainmentTree containmentTree, EObject start,
		boolean followAttributeReferences, CorrespondenceModel correspondenceModel) {
		if (containmentTree.attributeReferenceRootNode !== null) {
			Preconditions.checkArgument(!containmentTree.attributeReferenceEdges.empty,
				"Containment tree specifies an attribute reference root node but no attribute reference edges!")
		}
		logger.trace('''ContainmentTree: «containmentTree»''')
		logger.trace('''Start object: «start»''')
		logger.trace('''followAttributeReferences: «followAttributeReferences»''')
		val rootNode = containmentTree.getRoot
		val candidateRootObjects = start.getCandidateRoots(containmentTree, correspondenceModel,
			followAttributeReferences)
		logger.trace('''Candidate root objects: «candidateRootObjects»''')

		val candidateMatches = candidateRootObjects.filter [ candidateRootObject |
			containmentTree.matchesRootNode(candidateRootObject, correspondenceModel)
		].flatMap [ rootObject |
			var rootMatch = new ParticipationObjects
			rootMatch.addObject(rootNode.name, rootObject)
			// TODO Ensure that the returned Iterable is as lazy as possible, so that matching can be aborted once a
			// valid match has been found. However, also ensure that partial matching results are getting cached once
			// they have been calculated in order to not perform the same matching again when calculating all possible
			// combinations of matched objects.
			return containmentTree.matchChilds(rootMatch, rootNode, rootObject, correspondenceModel, 1)
		]
		if (candidateMatches.empty) {
			logger.trace('''No candidate matches found.''')
		}

		return candidateMatches.map [ match |
			logger.trace('''Candidate match: «match»''')
			if (containmentTree.attributeReferenceRootNode !== null) {
				// Match the attribute reference root node and add its object to the result:
				if (!matchAttributeReferenceRoot(containmentTree, match, correspondenceModel)) {
					return null
				}
			}
			return match
		].filterNull // filter matches for which we could not match the attribute reference root
		.filter [ match |
			if (containmentTree.attributeReferenceRootNode !== null) {
				// Check that all attribute reference edges are fulfilled:
				return containmentTree.attributeReferenceEdges.forall [ attributeReferenceEdge |
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

	def private static boolean matchesRootNode(ContainmentTree containmentTree, EObject candidateRootObject,
		CorrespondenceModel correspondenceModel) {
		val rootNode = containmentTree.root
		// The root intermediate type restriction only applies to the root node if no attribute reference root node is
		// specified.
		var EClass rootIntermediateType = null
		if (containmentTree.attributeReferenceRootNode === null) {
			rootIntermediateType = containmentTree.rootIntermediateType
		}
		return rootNode.matchesObject(candidateRootObject, correspondenceModel, rootIntermediateType, 0)
	}

	// Returns true if the attribute reference root got matched:
	def private static boolean matchAttributeReferenceRoot(ContainmentTree containmentTree, ParticipationObjects match,
		CorrespondenceModel correspondenceModel) {
		val attributeReferenceRootNode = containmentTree.attributeReferenceRootNode
		assertTrue(attributeReferenceRootNode !== null)
		// We use a random attribute reference edge to find the attribute reference root object:
		val attributeReferenceEdge = containmentTree.attributeReferenceEdges.head
		assertTrue(attributeReferenceEdge !== null)
		val containedObject = match.getObject(attributeReferenceEdge.contained.name)
		assertTrue(containedObject !== null)
		assertTrue(match.getObject(attributeReferenceRootNode.name) === null) // not yet matched
		val operator = attributeReferenceEdge.operator
		val containerObject = operator.getContainer(containedObject)
		if (containerObject !== null && attributeReferenceRootNode.matchesObject(containerObject, correspondenceModel,
			containmentTree.rootIntermediateType, 0)) {
			match.addObject(attributeReferenceRootNode.name, containerObject)
			logger.trace('''Matched attribute reference root: «containerObject»''')
			return true
		}
		logger.trace('''Could not match attribute reference root: «Objects.toString(containerObject)»''')
		return false
	}

	def private static boolean isAttributeReferenceEdgeFulfilled(ParticipationObjects match,
		ContainmentTree.OperatorEdge attributeReferenceEdge) {
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
	 * model), or is not contained in any other container (as it is the case
	 * when we match non-commonality participations rooted inside a Resource).
	 * In the latter case, if the object is contained inside a Resource, this
	 * returns a new IntermediateResourceBridge as representation of that
	 * Resource root.
	 * <p>
	 * If the containment tree specifies an attribute reference root and we
	 * found an object that already corresponds to some Intermediate, we check
	 * if the object matches the expected attribute reference root and then
	 * query the operators of its attribute reference edges for its contained
	 * objects and continue the search for candidate root objects from there.
	 */
	def private static Iterable<? extends EObject> getCandidateRoots(EObject object, ContainmentTree containmentTree,
		CorrespondenceModel correspondenceModel, boolean followAttributeReferences) {
		assertTrue(object !== null && containmentTree !== null && correspondenceModel !== null)
		if (object.isIntermediateRoot) {
			return Collections.singleton(object)
		}

		if (!ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(correspondenceModel, object, null,
			Intermediate).empty) {
			val attributeReferenceRootNode = containmentTree.attributeReferenceRootNode
			if (attributeReferenceRootNode !== null) {
				if (followAttributeReferences && attributeReferenceRootNode.matchesObject(object, correspondenceModel,
					containmentTree.rootIntermediateType, 0)) {
					logger.trace('''Found attribute reference root: «object»''')
					logger.trace('''Following attribute references in order to find candidate root objects ...''')
					// Note on map and flatten: This has the same effect as flatMap, but we cannot use flatMap here,
					// because flatMap expects Iterable<R> as result, but we produce Iterable<? extends R>.
					return containmentTree.attributeReferenceEdges
						// Edges may share their operator. We need to invoke each operator only once:
						.map[operator].toSet
						.map[getContainedObjects(object)].flatten.toSet
						.map [ containedObject |
							// We only follow a single layer of attribute references. If we encounter another object
							// that matches the attribute reference node, we abort the search.
							containedObject.getCandidateRoots(containmentTree, correspondenceModel, false)
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
				resourceBridge.initialiseForModelElement(object)
				return Collections.singleton(resourceBridge)
			} else {
				throw new IllegalStateException("Could not find a valid root object to start the matching at."
					+ " The given start object is not contained inside a resource.")
			}
		}

		// continue searching the container:
		return container.getCandidateRoots(containmentTree, correspondenceModel, followAttributeReferences)
	}

	def private static isIntermediateRoot(EObject object) {
		return object.eClass.ESuperTypes.contains(IntermediateModelBasePackage.eINSTANCE.root)
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
	def private static Iterable<ParticipationObjects> matchChilds(extension ContainmentTree containmentTree,
		ParticipationObjects parentMatch, ContainmentTree.Node currentNode, EObject currentObject,
		CorrespondenceModel correspondenceModel, int depth) {
		val nextContainments = currentNode.containments.toList
		if (nextContainments.empty) {
			// We reached a leaf inside the containment tree, so there are no more child nodes to match:
			return Collections.singleton(parentMatch)
		}
		// For each containment reference, determine the candidate matches:
		return nextContainments.map [ containment |
			// TODO This is not lazy currently (see toList) to avoid doing the same matching multiple times for
			// subsequent iterations. Ideally this should be lazy, but cache already computed results for later
			// iterations.
			containmentTree.matchContainmentEdge(parentMatch, currentNode, currentObject, containment,
				correspondenceModel, depth).toList as Iterable<ParticipationObjects>
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

	def private static Iterable<ParticipationObjects> matchContainmentEdge(extension ContainmentTree containmentTree,
		ParticipationObjects parentMatch, ContainmentTree.Node currentNode, EObject currentObject,
		ContainmentTree.Edge containment, CorrespondenceModel correspondenceModel, int depth) {
		logger.trace('''«indent(depth)»Matching edge «containment.toSimpleString» ...''')
		currentObject.getMatchingObjects(containment, correspondenceModel, depth)
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
				return containmentTree.matchChilds(childMatch, childNode, childObject, correspondenceModel, depth + 1)
			]
	}

	def private static Iterable<? extends EObject> getMatchingObjects(EObject container,
		ContainmentTree.Edge containmentEdge, CorrespondenceModel correspondenceModel, int depth) {
		var Iterable<? extends EObject> candidateObjects = Collections.emptySet
		if (container.isResourceBridge) {
			candidateObjects = (container as IntermediateResourceBridge).emfResource?.contents
		} else if (containmentEdge instanceof ContainmentTree.ReferenceEdge) {
			val reference = containmentEdge.reference
			val rawValue = container.eGet(reference)
			if (reference.isMany) {
				candidateObjects = (rawValue as Iterable<? extends EObject>)
			} else {
				if (rawValue !== null) {
					candidateObjects = Collections.singleton(rawValue as EObject)
				}
			}
		} else if (containmentEdge instanceof ContainmentTree.OperatorEdge) {
			candidateObjects = containmentEdge.operator.getContainedObjects(container)
		}
		val containedNode = containmentEdge.contained
		return candidateObjects.filter[containedNode.matchesObject(it, correspondenceModel, null, depth)]
	}

	/**
	 * Checks:
	 * - Object is of expected type.
	 * - If correspondingIntermediateType is specified: A corresponding
	 *   Intermediate of that type has to exist.
	 * - Otherwise: The object does not correspond to any Intermediate instance yet.
	 */
	def private static matchesObject(ContainmentTree.Node node, EObject object,
		CorrespondenceModel correspondenceModel, EClass correspondingIntermediateType, int depth) {
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

	def private static boolean isResourceBridge(EObject object) {
		return (object instanceof IntermediateResourceBridge)
	}

	def private static indent(int depth) {
		return StringUtil.repeat('  ', depth)
	}
}
