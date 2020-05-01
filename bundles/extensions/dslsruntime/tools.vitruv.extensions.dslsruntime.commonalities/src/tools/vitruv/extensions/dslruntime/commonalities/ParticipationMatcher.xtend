package tools.vitruv.extensions.dslruntime.commonalities

import com.google.common.base.Preconditions
import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtend.lib.annotations.ToString
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.IReferenceMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesFactory
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static extension tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.ReferenceMappingOperatorHelper.*

/**
 * Matches participation classes to their objects according to the containment
 * hierarchy specified at compile time.
 */
@Utility
class ParticipationMatcher {

	val static Logger logger = Logger.getLogger(ParticipationMatcher) => [level = Level.TRACE]

	/**
	 * Represents the containment hierarchy of participation classes.
	 * <p>
	 * Participation classes are represented as nodes with according name and
	 * type. Containment relations are represented as directed edges between
	 * nodes. Each edge provides either the EReference that realizes the
	 * containment relationship, or an operator that can be queried for the
	 * contained objects.
	 * <p>
	 * Assumptions:
	 * <ul>
	 * <li>Each object is contained by at most one other object.
	 * <li>No cyclic containments.
	 * <li>No self containment (no loops).
	 * <li>There is at most one root container class (i.e. Resource) per
	 * participation. TODO support multiple root containers?
	 * </ul>
	 * <p>
	 * A containment relation can also be defined implicitly according to the
	 * attributes of the involved objects (eg. in Java sub packages exist
	 * independently from each other and their 'containment' relationship is
	 * expressed implicitly by their namespaces and name attributes). We
	 * support the containment tree to define one additional (external) root
	 * node that is connected by such attribute references to nodes of the main
	 * containment tree.
	 */
	@ToString
	static class ContainmentTree {

		@Data
		static class Node {

			val String name
			val EClass type

			def String toSimpleString() {
				return name
			}
		}

		interface Edge {

			def Node getContained()
			def Node getContainer()
			def String toSimpleString()
		}

		@Data
		static class ReferenceEdge implements Edge {

			val Node contained
			val Node container
			val EReference reference

			override toSimpleString() {
				return '''«container.toSimpleString»[«reference.name»] -> «contained.toSimpleString»'''
			}
		}

		@Data
		static class OperatorEdge implements Edge {

			val Node contained
			val Node container
			val IReferenceMappingOperator operator

			override toSimpleString() {
				return '''«container.toSimpleString»[operator: «operator.class.simpleName
					»] -> «contained.toSimpleString»'''
			}
		}

		val Map<String, Node> nodes = new HashMap
		val Set<Edge> edges = new HashSet
		// The specific Intermediate type that the root node has to already
		// correspond to (can be null). If an attribute reference root node is
		// specified, this applies to that node instead.
		var EClass rootIntermediateType = null
		var Node attributeReferenceRootNode = null
		var Set<OperatorEdge> attributeReferenceEdges = new HashSet
		var Node root = null // lazily calculated

		new() {
		}

		private def commonPreValidateNode(String name, EClass type) {
			Preconditions.checkArgument(!name.nullOrEmpty, "name is null or empty")
			Preconditions.checkNotNull(type, "type is null")
			Preconditions.checkArgument(!nodes.containsKey(name) && name != attributeReferenceRootNode?.name,
				"There already exists a node with this name: " + name)
		}

		def addNode(String name, EClass type) {
			commonPreValidateNode(name, type)
			val newNode = new Node(name, type)
			nodes.put(name, newNode)
			root = null // reset lazy calculated root
			return newNode
		}

		private def validateNodeExists(String nodeName) {
			Preconditions.checkArgument(nodes.containsKey(nodeName), "There is no node with this name: " + nodeName)
		}

		private def commonPreValidateEdge(String containedName, String containerName) {
			validateNodeExists(containedName)
			validateNodeExists(containerName)
			Preconditions.checkArgument(containedName != containerName, "Container cannot contain itself (no loops)")
		}

		private def addEdge(Edge newEdge) {
			Preconditions.checkArgument(!edges.contains(newEdge), "The edge already exists")
			edges.add(newEdge)
			root = null // reset lazy calculated root
		}

		def addReferenceEdge(String containedName, String containerName, EReference reference) {
			commonPreValidateEdge(containedName, containerName)
			val contained = nodes.get(containedName)
			val container = nodes.get(containerName)
			// assert: contained !== null && container !== null
			Preconditions.checkNotNull(reference, "Containment reference is null")
			Preconditions.checkArgument(reference.isContainment, "The given reference is not a containment")
			Preconditions.checkArgument(container.type.EAllReferences.contains(reference),
				"Containment reference belongs to a different container class")

			val newEdge = new ReferenceEdge(contained, container, reference)
			addEdge(newEdge)
			return newEdge
		}

		def addOperatorEdge(String containedName, String containerName, IReferenceMappingOperator operator) {
			commonPreValidateEdge(containedName, containerName)
			val contained = nodes.get(containedName)
			val container = nodes.get(containerName)
			// assert: contained !== null && container !== null
			Preconditions.checkNotNull(operator, "Operator is null")
			Preconditions.checkArgument(!operator.isAttributeReference,
				"Operator edge cannot use an attribute reference operator!")

			val newEdge = new OperatorEdge(contained, container, operator)
			addEdge(newEdge)
			return newEdge
		}

		def setRootIntermediateType(EClass type) {
			Preconditions.checkState(rootIntermediateType === null, "The root intermediate type has already been set!")
			this.rootIntermediateType = type
		}

		def setAttributeReferenceRootNode(String name, EClass type) {
			Preconditions.checkState(attributeReferenceRootNode === null,
				"The attribute reference root node has already been set!")
			commonPreValidateNode(name, type)

			attributeReferenceRootNode = new Node(name, type)
			return attributeReferenceRootNode
		}

		def addAttributeReferenceEdge(String containedName, IReferenceMappingOperator operator) {
			Preconditions.checkState(attributeReferenceRootNode !== null,
				"The attribute reference root node has not yet been set!")
			validateNodeExists(containedName)
			Preconditions.checkArgument(containedName != attributeReferenceRootNode.name,
				"The attribute reference root node cannot contain itself (no loops)")
			Preconditions.checkArgument(operator.isAttributeReference,
				"Attribute reference edge needs to use an attribute reference operator!")

			val contained = nodes.get(containedName)
			val container = attributeReferenceRootNode
			val newEdge = new OperatorEdge(contained, container, operator)
			Preconditions.checkArgument(!attributeReferenceEdges.contains(newEdge), "The edge already exists")
			attributeReferenceEdges.add(newEdge)
			return newEdge
		}

		// returns null if there are no nodes
		def getRoot() {
			if (root === null) {
				// start at random node:
				var current = nodes.values.head // null if there are no nodes
				var container = current?.container
				while (container !== null) {
					current = container
					container = current.container
				}
				root = current
			}
			return root
		}

		// returns null if the given node is null, does not belong to this tree, or has no container
		def Node getContainer(Node node) {
			if (node == attributeReferenceRootNode) return null
			return edges.findFirst[contained == node]?.container
		}

		def getNode(String name) {
			if (name == attributeReferenceRootNode?.name) return attributeReferenceRootNode
			return nodes.get(name)
		}

		def Iterable<? extends Edge> getContainments(Node node) {
			if (node == attributeReferenceRootNode) return attributeReferenceEdges
			return edges.filter[container == node]
		}
	}

	/**
	 * Mapping between containment tree nodes and their objects.
	 */
	static class ParticipationObjects {

		val Map<String, EObject> objects = new HashMap

		new() {
		}

		private new(ParticipationObjects other) {
			this.merge(other)
		}

		def private setObject(String nodeName, EObject object) {
			objects.put(nodeName, object)
		}

		def <T> T getObject(String nodeName) {
			return objects.get(nodeName) as T
		}

		def copy() {
			return new ParticipationObjects(this)
		}

		/**
		 * Adds the entries of the given mapping to this mapping.
		 */
		def merge(ParticipationObjects other) {
			this.objects.putAll(other.objects)
			return this
		}

		override toString() {
			return '''«this.class.simpleName»: «objects»'''
		}
	}

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
		val rootNode = containmentTree.getRoot
		val candidateRootObjects = start.getCandidateRoots(containmentTree, correspondenceModel, followAttributeReferences)
		logger.trace('''Candidate root objects: «candidateRootObjects»''')
		return candidateRootObjects.filter[ rootObject |
			// If we have an attribute reference root node, the root
			// intermediate type applies to it instead of the found root
			// objects:
			var EClass rootIntermediateType = null
			if (containmentTree.attributeReferenceRootNode === null) {
				rootIntermediateType = containmentTree.rootIntermediateType
			}
			rootNode.matchesObject(rootObject, correspondenceModel, rootIntermediateType)
		].flatMap [ rootObject |
			var rootMatch = new ParticipationObjects
			rootMatch.setObject(rootNode.name, rootObject)
			// TODO Ensure that the returned Iterable is as lazy as possible, so
			// that matching can be aborted once a valid match has been found.
			// However, also ensure that partial matching results are getting
			// cached once they have been calculated in order to not perform the
			// same matching again when calculating all possible combinations of
			// matched objects.
			return containmentTree.matchChilds(rootMatch, rootNode, rootObject, correspondenceModel)
		].map [ match|
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
					isAttributeReferenceEdgeFulfilled(match, attributeReferenceEdge)
				]
			} else {
				return true
			}
		]
	}

	// Returns true if the attribute reference root got matched:
	def private static boolean matchAttributeReferenceRoot(ContainmentTree containmentTree, ParticipationObjects match,
		CorrespondenceModel correspondenceModel) {
		val attributeReferenceRootNode = containmentTree.attributeReferenceRootNode
		// assert: attributeReferenceRootNode !== null
		// We use a random attribute reference edge to find the attribute reference root object:
		val attributeReferenceEdge = containmentTree.attributeReferenceEdges.head
		// assert: attributeReferenceEdge !== null
		val containedObject = match.getObject(attributeReferenceEdge.contained.name)
		// assert: containedEObject !== null
		// assert: match.getObject(attributeReferenceRootNode.name) === null (not yet matched)
		val operator = attributeReferenceEdge.operator
		val containerObject = operator.getContainer(containedObject)
		if (containerObject !== null
			&& attributeReferenceRootNode.matchesObject(containerObject, correspondenceModel, containmentTree.rootIntermediateType)) {
			match.setObject(attributeReferenceRootNode.name, containerObject)
			return true
		}
		return false
	}

	def private static boolean isAttributeReferenceEdgeFulfilled(ParticipationObjects match,
		ContainmentTree.OperatorEdge attributeReferenceEdge) {
		val containerObject = match.getObject(attributeReferenceEdge.container.name)
		// assert: containerObject !== null (we expect that attribute reference root has already been matched)
		val containedObject = match.getObject(attributeReferenceEdge.contained.name)
		// assert: containedObject !== null
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
		// assert: object !== null && containmentTree !== null && correspondenceModel !== null
		if (object.isIntermediateRoot) {
			return Collections.singleton(object)
		}

		if (!ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(correspondenceModel, object, null,
			Intermediate).empty) {
			val attributeReferenceRootNode = containmentTree.attributeReferenceRootNode
			if (attributeReferenceRootNode !== null) {
				if (followAttributeReferences
					&& attributeReferenceRootNode.matchesObject(object, correspondenceModel, containmentTree.rootIntermediateType)) {
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
			// If the object is contained inside a resource, we create
			// a ResourceBridge as representation of the Resource root:
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
	 * There may be multiple matching objects at every containment reference.
	 * Each object choice is followed and checked if it can complete the
	 * expected containment structure. This may therefore return multiple
	 * candidate matches.
	 */
	def private static Iterable<ParticipationObjects> matchChilds(extension ContainmentTree containmentTree,
		ParticipationObjects parentMatch, ContainmentTree.Node currentNode, EObject currentObject,
		CorrespondenceModel correspondenceModel) {
		val nextContainments = currentNode.containments.toList
		if (nextContainments.empty) {
			// we reached a leaf inside the containment tree, so there are no
			// more childs to match:
			return Collections.singleton(parentMatch)
		}
		return nextContainments.map [ containment |
			// TODO This is not lazy currently (see toList) to avoid doing the
			// same matching multiple times for subsequent iterations. Ideally
			// this should be lazy, but cache already computed results for
			// later iterations.
			currentObject.getMatchingObjects(containment, correspondenceModel).flatMap [ childObject |
				logger.trace('''Edge «containment.toSimpleString»: Found matching object «childObject»''')
				val childNode = containment.contained
				val childMatch = parentMatch.copy
				childMatch.setObject(childNode.name, childObject)
				return containmentTree.matchChilds(childMatch, childNode, childObject, correspondenceModel)
			].toList as Iterable<ParticipationObjects>
		].reduce [ prevMatches, nextMatches | // TODO reduce is not lazy!
			prevMatches.flatMap [ prevMatch |
				nextMatches.map [ nextMatch |
					prevMatch.copy.merge(nextMatch)
				]
			]
		]
	}

	def private static Iterable<? extends EObject> getMatchingObjects(EObject container,
		ContainmentTree.Edge containmentEdge, CorrespondenceModel correspondenceModel) {
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
		return candidateObjects.filter[containedNode.matchesObject(it, correspondenceModel, null)]
	}

	/**
	 * Checks:
	 * - Object is of expected type.
	 * - If correspondingIntermediateType is specified: A corresponding
	 *   Intermediate of that type has to exist.
	 * - Otherwise: The object does not correspond to any Intermediate instance yet.
	 */
	def private static matchesObject(ContainmentTree.Node node, EObject object,
		CorrespondenceModel correspondenceModel, EClass correspondingIntermediateType) {
		if (!node.type.isInstance(object)) { // also checks for null object
			logger.trace('''Node «node.toSimpleString»: Object is of wrong type «object»''')
			return false
		}

		// Note: Not checking for correspondences for our dynamically created
		// (and only partially setup) resource bridge.
		if (!object.isResourceBridge) {
			var correspondingIntermediate = ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(
				correspondenceModel, object, null, Intermediate).head
			if (correspondingIntermediateType != correspondingIntermediate?.eClass) {
				if (correspondingIntermediateType === null) {
					logger.trace('''Node «node.toSimpleString»: Object already corresponds to an Intermediate «object»''')
				} else {
					logger.trace('''Node «node.toSimpleString»: Object has no matching Intermediate correspondence «object»''')
				}
				return false
			}
		}
		logger.trace('''Node «node.toSimpleString»: Found matching object «object»''')
		return true
	}

	def private static boolean isResourceBridge(EObject object) {
		return (object instanceof IntermediateResourceBridge)
	}
}
