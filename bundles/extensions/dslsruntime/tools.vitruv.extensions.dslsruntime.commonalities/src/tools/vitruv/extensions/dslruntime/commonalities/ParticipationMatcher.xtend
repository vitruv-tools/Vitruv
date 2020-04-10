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
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesFactory
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage

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
	 * nodes. Each edge also stores the EReference that realizes the
	 * containment relationship.
	 * <p>
	 * Assumptions:
	 * <ul>
	 * <li>Each object is contained in max one other object.
	 * <li>No cyclic containments.
	 * <li>No self containment (no loops).
	 * <li>There is at most one root container class (i.e. Resource) per
	 * participation. TODO support multiple root containers?
	 * </ul>
	 */
	static class ContainmentTree {

		@Data
		static class Node {

			val String name
			val EClass type

			def String toSimpleString() {
				return name
			}
		}

		@Data
		static class Edge {

			val Node contained
			val Node container
			val EReference reference

			def String toSimpleString() {
				return '''«contained.toSimpleString» -> «container.toSimpleString»[«reference.name»]'''
			}
		}

		val Map<String, Node> nodes = new HashMap
		val Set<Edge> edges = new HashSet
		// The specific Intermediate type that the root node has to already
		// correspond to (can be null:)
		var EClass rootIntermediateType = null
		var Node root = null // lazily calculated

		new() {
		}

		def addNode(String name, EClass type) {
			Preconditions.checkArgument(!name.nullOrEmpty, "name is null or empty")
			Preconditions.checkNotNull(type, "type is null")
			Preconditions.checkArgument(!nodes.containsKey(name), "There already exists a node with this name: " + name)

			val newNode = new Node(name, type)
			nodes.put(name, newNode)
			root = null // reset lazy calculated root
			return newNode
		}

		def addEdge(String containedName, String containerName, EReference reference) {
			val contained = nodes.get(containedName)
			Preconditions.checkNotNull(contained, "There is no node with this name: " + containedName)
			val container = nodes.get(containerName)
			Preconditions.checkNotNull(container, "There is no node with this name: " + containerName)
			Preconditions.checkArgument(containedName != containerName, "Container cannot contain itself (no loops)")
			Preconditions.checkNotNull(reference, "Containment reference is null")
			Preconditions.checkArgument(reference.isContainment, "The given reference is not a containment")
			Preconditions.checkArgument(container.type.EAllReferences.contains(reference),
				"Containment reference belongs to a different container class")

			val newEdge = new Edge(contained, container, reference)
			Preconditions.checkArgument(!edges.contains(newEdge), "The edge already exists")
			edges.add(newEdge)
			root = null // reset lazy calculated root
			return newEdge
		}

		def setRootIntermediateType(EClass type) {
			this.rootIntermediateType = type
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
			return edges.findFirst[contained == node]?.container
		}

		def getNode(String name) {
			return nodes.get(name)
		}

		def Iterable<Edge> getContainments(Node node) {
			return edges.filter[container == node]
		}

		override toString() {
			val builder = new StringBuilder
			builder.append("ContainmentTree: {")
			builder.append("rootIntermediateType: ").append(rootIntermediateType)
			builder.append("nodes: ").append(nodes)
			builder.append(", edges: ").append(edges)
			builder.append("}")
			return builder.toString
		}
	}

	/**
	 * Mapping between participation classes and their object.
	 */
	static class ParticipationObjects {

		val Map<String, EObject> objects = new HashMap

		new() {
		}

		private new(ParticipationObjects other) {
			this.merge(other)
		}

		def private setObject(String participationClassName, EObject object) {
			objects.put(participationClassName, object)
		}

		def <T> T getObject(String participationClassName) {
			return objects.get(participationClassName) as T
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
	 * commonality participations, an intermediate model root, or they can be
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
	 * @return candidate mappings between participation class names and their
	 * 		objects
	 */
	def static Iterable<ParticipationObjects> matchObjects(extension ContainmentTree containmentTree, EObject start,
		CorrespondenceModel correspondenceModel) {
		logger.trace('''ContainmentTree: «containmentTree»''')
		val rootNode = containmentTree.getRoot
		val rootObject = start.getRoot(correspondenceModel)
		logger.trace('''Start object: «start»''')
		logger.trace('''Root object: «rootObject»''')
		if (!rootNode.matchesObject(rootObject, correspondenceModel, containmentTree.rootIntermediateType)) {
			return Collections.emptyList
		}
		var rootMatch = new ParticipationObjects
		rootMatch.setObject(rootNode.name, rootObject)
		// TODO Ensure that the returned Iterable is as lazy as possible, so
		// that matching can be aborted once a valid match has been found.
		// However, also ensure that partial matching results are getting
		// cached once they have been calculated in order to not perform the
		// same matching again when calculating all possible combinations of
		// matched objects.
		return containmentTree.matchChilds(rootMatch, rootNode, rootObject, correspondenceModel)
	}

	/**
	 * Finds the starting object for our matching procedure.
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
	 */
	def private static EObject getRoot(EObject object, CorrespondenceModel correspondenceModel) {
		// assert: object !== null && correspondenceModel !== null
		if (object.isIntermediateRoot) {
			return object
		}

		if (!ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(correspondenceModel, object, null,
			Intermediate).empty) {
			return object
		}

		var EObject container = object.eContainer
		if (container === null) {
			// If the object is contained inside a resource, we create
			// a ResourceBridge as representation of the Resource root:
			if (object.eResource !== null) {
				val resourceBridge = ResourcesFactory.eINSTANCE.createIntermediateResourceBridge
				resourceBridge.initialiseForModelElement(object)
				return resourceBridge
			}
		}

		// continue searching the container:
		return container.getRoot(correspondenceModel)
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
		var Iterable<? extends EObject> candidateObjects
		if (container.isResourceBridge) {
			candidateObjects = (container as IntermediateResourceBridge).emfResource?.contents
		} else {
			val reference = containmentEdge.reference
			val rawValue = container.eGet(reference)
			if (reference.isMany) {
				candidateObjects = (rawValue as Iterable<? extends EObject>)
			} else {
				if (rawValue === null) {
					return Collections.emptySet
				} else {
					candidateObjects = Collections.singleton(rawValue as EObject)
				}
			}
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
