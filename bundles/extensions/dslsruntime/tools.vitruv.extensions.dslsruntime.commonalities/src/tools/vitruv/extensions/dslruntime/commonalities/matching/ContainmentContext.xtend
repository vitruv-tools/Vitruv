package tools.vitruv.extensions.dslruntime.commonalities.matching

import com.google.common.base.Preconditions
import java.util.Collections
import java.util.HashSet
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtend.lib.annotations.ToString
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.IReferenceMappingOperator

import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.ReferenceMappingOperatorHelper.*

/**
 * Represents a participation's containment context.
 * <p>
 * Participation objects are represented as nodes with names and types
 * according to their participation classes. Containment relations are
 * represented as directed edges between nodes. Each edge provides either the
 * EReference that realizes the containment relationship, or an operator that
 * can be queried for the contained objects.
 * <p>
 * The containment context of a participation can be thought of as a forest
 * (i.e. a collection of disjoint containment trees). In most cases the
 * containment context will only consist of a single containment tree. However,
 * in the context of commonality references it is possible to define multiple
 * reference mappings for the same participation. If these mappings specify
 * different reference containers, the containment context will contain
 * multiple root nodes.
 * <p>
 * A containment relation can also be defined implicitly according to the
 * attributes of the involved objects (eg. in Java sub-packages exist
 * independently from each other and their 'containment' relationship is
 * expressed implicitly by their namespaces and name attributes). We support
 * the containment context to define one additional (external) root node which
 * is connected by such attribute references to nodes of the participation's
 * main containment tree.
 * <p>
 * Assumptions:
 * <ul>
 * <li>Each object is contained by at most one other object (with the exception
 * of objects 'contained' via attribute references).
 * <li>No cyclic containments.
 * <li>No self containment (no loops).
 * <li>There is at most one root container (i.e. Resource) per participation.
 * TODO Support multiple root containers?
 * </ul>
 */
@ToString
class ContainmentContext {

	@Data
	static class Node {

		val String name
		val EClass type
		val String correspondenceTag

		def String toSimpleString() {
			return name
		}
	}

	interface Edge {

		def Node getContainer()
		def Node getContained()
		def String toSimpleString()
	}

	@Data
	static class ReferenceEdge implements Edge {

		val Node container
		val Node contained
		val EReference reference

		override toSimpleString() {
			return '''«container.toSimpleString»[«reference.name»] -> «contained.toSimpleString»'''
		}
	}

	@Data
	static class OperatorEdge implements Edge {

		val Node container
		val Node contained
		val IReferenceMappingOperator operator

		override toSimpleString() {
			return '''«container.toSimpleString»[operator: «operator.class.simpleName»] -> «
				contained.toSimpleString»'''
		}
	}

	val Map<String, Node> nodesByName = new LinkedHashMap
	val Set<Edge> edges = new LinkedHashSet
	val Set<Node> roots = new LinkedHashSet // lazily calculated

	// The specific Intermediate type that the root nodes have to already
	// correspond to (can be null). This applies to the attribute reference
	// root node instead, if one is specified.
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	var EClass rootIntermediateType = null
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	var Node attributeReferenceRootNode = null
	var Set<OperatorEdge> attributeReferenceEdges = new HashSet
	val transient attributeReferenceEdgesView = Collections.unmodifiableSet(attributeReferenceEdges)

	new() {
	}

	private def commonPreValidateNode(String name, EClass type) {
		Preconditions.checkArgument(!name.nullOrEmpty, "name is null or empty")
		Preconditions.checkNotNull(type, "type is null")
		Preconditions.checkArgument(!nodesByName.containsKey(name) && name != attributeReferenceRootNode?.name,
			"There already exists a node with this name: " + name)
	}

	def addNode(String name, EClass type, String correspondenceTag) {
		commonPreValidateNode(name, type)
		val newNode = new Node(name, type, correspondenceTag)
		nodesByName.put(name, newNode)
		roots.clear() // Reset lazy calculated roots
		return newNode
	}

	private def validateNodeExists(String nodeName) {
		Preconditions.checkArgument(nodesByName.containsKey(nodeName), "There is no node with this name: " + nodeName)
	}

	private def commonPreValidateEdge(String containerName, String containedName) {
		validateNodeExists(containerName)
		validateNodeExists(containedName)
		Preconditions.checkArgument(containerName != containedName, "Container cannot contain itself (no loops)")
	}

	private def addEdge(Edge newEdge) {
		Preconditions.checkArgument(!edges.contains(newEdge), "The edge already exists")
		edges.add(newEdge)
		roots.clear() // Reset lazy calculated roots
	}

	def addReferenceEdge(String containerName, String containedName, EReference reference) {
		commonPreValidateEdge(containerName, containedName)
		val container = nodesByName.get(containerName)
		val contained = nodesByName.get(containedName)
		assertTrue(container !== null && contained !== null)
		Preconditions.checkNotNull(reference, "Containment reference is null")
		Preconditions.checkArgument(reference.isContainment, "The given reference is not a containment")
		Preconditions.checkArgument(container.type.EAllReferences.contains(reference),
			"Containment reference belongs to a different container class")

		val newEdge = new ReferenceEdge(container, contained, reference)
		addEdge(newEdge)
		return newEdge
	}

	def addOperatorEdge(String containerName, String containedName, IReferenceMappingOperator operator) {
		commonPreValidateEdge(containerName, containedName)
		val container = nodesByName.get(containerName)
		val contained = nodesByName.get(containedName)
		assertTrue(container !== null && contained !== null)
		Preconditions.checkNotNull(operator, "Operator is null")
		Preconditions.checkArgument(!operator.isAttributeReference,
			"Operator edge cannot use an attribute reference operator!")

		val newEdge = new OperatorEdge(container, contained, operator)
		addEdge(newEdge)
		return newEdge
	}

	def setRootIntermediateType(EClass type) {
		Preconditions.checkState(rootIntermediateType === null, "The root intermediate type has already been set!")
		this.rootIntermediateType = type
	}

	def setAttributeReferenceRootNode(String name, EClass type, String correspondenceTag) {
		Preconditions.checkState(attributeReferenceRootNode === null,
			"The attribute reference root node has already been set!")
		commonPreValidateNode(name, type)

		attributeReferenceRootNode = new Node(name, type, correspondenceTag)
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

		val contained = nodesByName.get(containedName)
		val container = attributeReferenceRootNode
		val newEdge = new OperatorEdge(container, contained, operator)
		Preconditions.checkArgument(!attributeReferenceEdges.contains(newEdge), "The edge already exists")
		attributeReferenceEdges.add(newEdge)
		return newEdge
	}

	/**
	 * Gets the root nodes.
	 * 
	 * @return the root nodes, or empty if there are no nodes at all
	 */
	def getRoots() {
		if (roots.empty) {
			// Note: roots is a Set (removes duplicates).
			roots += nodesByName.values.map[findRoot]
		}
		return roots
	}

	/**
	 * Finds the root node starting at the given node.
	 * 
	 * @param start
	 * 		the start node, not <code>null</code>
	 * @return the root node, not <code>null</code>
	 */
	private def Node findRoot(Node start) {
		var current = start
		var container = current.container
		while (container !== null) {
			current = container
			container = current.container
		}
		return current
	}

	/**
	 * Gets the container node of the given node.
	 * 
	 * @return The container node, or <code>null</code> if the given node is
	 * 		<code>null</code>, does not belong to this containment context, or
	 * 		has no container.
	 */
	def Node getContainer(Node node) {
		if (node == attributeReferenceRootNode) return null
		return edges.findFirst[contained == node]?.container
	}

	def getNode(String name) {
		if (name == attributeReferenceRootNode?.name) return attributeReferenceRootNode
		return nodesByName.get(name)
	}

	/**
	 * Gets the containment edges of the given node.
	 */
	def Iterable<? extends Edge> getContainments(Node node) {
		if (node == attributeReferenceRootNode) return attributeReferenceEdges
		return edges.filter[container == node]
	}

	/**
	 * Gets a view on the attribute reference edges.
	 */
	def getAttributeReferenceEdges() {
		return attributeReferenceEdgesView
	}
}
