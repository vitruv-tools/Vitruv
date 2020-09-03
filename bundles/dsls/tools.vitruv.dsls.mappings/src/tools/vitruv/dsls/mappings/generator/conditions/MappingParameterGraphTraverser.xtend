package tools.vitruv.dsls.mappings.generator.conditions

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.function.Function
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.conditions.impl.InValueConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter

class MappingParameterGraphTraverser {

	val Map<String, Node> nodes = new HashMap
	var Function<MappingParameter, String> nameMapping

	new(List<FeatureConditionGenerator> conditions, List<String> parameters,
		Function<MappingParameter, String> nameMapping) {
		this.nameMapping = nameMapping
		constructGraph(conditions, parameters)
	}

	private def constructGraph(List<FeatureConditionGenerator> conditions, List<String> parameters) {
		parameters.forEach [ parameter |
			parameter.constructNode
		]
		conditions.filter[it instanceof InValueConditionGenerator].forEach [
			createInRelation(it as InValueConditionGenerator)
		]
		validateGraph
	}

	private def constructNode(String parameter) {
		nodes.put(parameter, new Node(parameter))
	}

	private def createInRelation(InValueConditionGenerator condition) {
		val childParameter = condition.childParameter
		val inFeature = condition.inFeature as EReference
		val parentParameter = condition.parentParameter
		val childNode = nodes.get(nameMapping.apply(childParameter))
		val parentNode = nodes.get(nameMapping.apply(parentParameter))
		if(childNode == parentNode){
			//not allowed
			throw new IllegalStateException('Invalid relation for parameter '+childNode.parameter)
		}
		parentNode.addChild(childNode, inFeature)
		childNode.addParent(parentNode)
	}

	private def validateGraph() {
		// check for cycles not needed (cycles are allowed)
		//nodes.values.forEach[cycleCheck(new ArrayList)]
		// check for unconnected nodes
		val shouldReachNodesCount = nodes.size - 1
		if (nodes.values.map[reachableNodesCount(new ArrayList)].min < shouldReachNodesCount) {
			// at least one of the nodes can not reach all of the other nodes
			throw new IllegalStateException(
				'A MappingParameter cannot be reached by the other nodes, graph is invalid!')
		}
	}

	/**
	 * Returns a path between two nodes in the graph
	 */
	def findPath(String from, String to) {
		val fromNode = nodes.get(from)
		val toNode = nodes.get(to)
		fromNode.findPath(toNode, new NodePath(fromNode))
	}

	/**
	 * Finds the shortest path between a list of start nodes to a target node.
	 * The returned path starts from one of the start nodes and ends at the target node.
	 */
	def findPath(List<String> startPoints, String to) {
		startPoints.map[it.findPath(to)].minBy[it.steps.size]
	}

	/**
	 * Returns a path with one step from a list of start nodes. The target node is of the remaining
	 * nodes in the graph without the starting nodes. 
	 */
	def findStepToNextNode(List<String> startPoints) {
		val remainingNodes = nodes.keySet.filter[!startPoints.contains(it)]
		if(remainingNodes.empty){
			throw new IllegalStateException('Can not find step to next node, because all nodes are visited already!')
		}
		val path = remainingNodes.map[target|findPath(startPoints, target)].minBy[steps.size]
		if (path.steps.size > 1) {
			// this should never happen
			throw new IllegalStateException('Could not find step to next node, something must be terribly wrong!')
		}
		path
	}

	/**
	 * Returns a node by its key
	 */
	def getNode(String parameter) {
		return nodes.get(parameter)
	}

	static class Node {
		List<Node> parents = new ArrayList
		@Accessors(PUBLIC_GETTER)
		List<ChildNode> children = new ArrayList
		@Accessors(PUBLIC_GETTER)
		String parameter

		new(String parameter) {
			this.parameter = parameter
		}
		
		/**
		 * Adds a parent node
		 */
		def addParent(Node parent) {
			if (!parents.contains(parent)) {
				parents += parent
				containmentCheck
			}
		}

		/**
		 * the node can only be in a containment-feature once, all other parents must be referenced features
		 */
		private def containmentCheck() {
			if (parents.filter [ parent |
				val childNodeInParent = parent.children.findFirst[it.node == this]
				childNodeInParent.inFeature.containment
			].size > 1) {
				throw new IllegalStateException(
					'A MappingParameter can only be contained by one containment-reference!')
			}
		}
		
		/**
		 * Returns a child node by its parameter
		 */
		def getChild(String childParameter) {
			children.findFirst[node.parameter == childParameter]
		}
		
		/**
		 * Adds a child node
		 */
		def addChild(Node child, EReference inFeature) {
			this.children += new ChildNode(child, inFeature)
		}
		
		/**
		 * Checks for cycles in the graph. If a cycle is found, an exception will be thrown
		 */
		def void cycleCheck(List<Node> visitedNodes) throws IllegalStateException{
			if (parents.empty) {
				// top level node
				return
			}
			if (visitedNodes.contains(this)) {
				// we have a cycle!
				throw new IllegalStateException('The MappingParameters in-relations contains cycles!')
			}
			visitedNodes += this
			// go up all parent nodes
			parents.forEach [
				cycleCheck(visitedNodes)
			]
		}
		
		/**
		 * Returns the count of other nodes in the graph that can be reached from this node,
		 * provided a list of already reached nodes.
		 */
		def int reachableNodesCount(List<Node> discoveredNodes) {
			if (!discoveredNodes.contains(this)) {
				discoveredNodes += this
			}
			val parentsReachableNodesCount = discoverNodes(parents, discoveredNodes)
			val childsReachableNodesCount = discoverNodes(children.map[it.node], discoveredNodes)
			parentsReachableNodesCount + childsReachableNodesCount
		}

		private def int discoverNodes(List<Node> nodes, List<Node> discoveredNodes) {
			val undiscoveredNodes = nodes.filter[!discoveredNodes.contains(it)]
			if (undiscoveredNodes.empty) {
				return discoveredNodes.size - 1
			}
			undiscoveredNodes.map [
				val copiedDiscover = new ArrayList
				discoveredNodes.forEach[copiedDiscover += it]
				reachableNodesCount(copiedDiscover)
			].reduce[p1, p2| Integer.valueOf(p1.intValue + p2.intValue)]
		}
		
		/**
		 * Finds a path from this node to a target node
		 */
		def NodePath findPath(Node node, NodePath path) {
			if (this == node) {
				// found node
				return path
			}
			// traverse parent and child nodes, if they were not traversed already
			for (parent : parents) {
				if (!path.hasTraversedAlready(parent)) {
					val fork = path.copy
					// find the feature from the parent
					val parentChildNode = parent.children.findFirst[it.node == Node.this]
					val feature = parentChildNode.inFeature
					fork.steps += new TraverseStepUp(parent, feature)
					val forkedPath = parent.findPath(node, fork)
					if (forkedPath !== null) {
						return forkedPath
					}
				}
			}
			for (child : children) {
				val childNode = child.node
				if (!path.hasTraversedAlready(childNode)) {
					val fork = path.copy
					fork.steps += new TraverseStepDown(child)
					val forkedPath = childNode.findPath(node, fork)
					if (forkedPath !== null) {
						return forkedPath
					}
				}
			}
			// should not happen, we could not find a path
			return null
		}

	}

	static class ChildNode {
		@Accessors(PUBLIC_GETTER)
		Node node
		@Accessors(PUBLIC_GETTER)
		EReference inFeature

		new(Node node, EReference inFeature) {
			this.node = node
			this.inFeature = inFeature
		}
	}

	static class NodePath {
		Node startNode
		@Accessors(PUBLIC_GETTER)
		List<TraverseStep> steps = new ArrayList

		new(Node startNode) {
			this.startNode = startNode
		}
	
		/**
		 * Returns the start node name
		 */
		def getStartNode() {
			startNode.parameter
		}

		private def hasTraversedAlready(Node node) {
			node == startNode || steps.exists[it.to == node]
		}

		private def copy() {
			val fork = new NodePath(startNode)
			steps.forEach[fork.steps += it]
			fork
		}
	}

	abstract static class TraverseStep {
		@Accessors(PUBLIC_GETTER)
		protected EReference feature
		protected Node to

		new(Node to, EReference feature) {
			this.to = to
			this.feature = feature
		}
		
		/**
		 * Returns the feature name of the edge traversed in this step
		 */
		def getParameter() {
			return to.parameter
		}
	}

	static class TraverseStepDown extends TraverseStep {

		new(ChildNode to) {
			super(to.node, to.inFeature)
		}

		// only used by tests
		new(String to, EReference reference) {
			super(new Node(to), reference)
		}
	}

	static class TraverseStepUp extends TraverseStep {
		new(Node to, EReference feature) {
			super(to, feature)
		}

		// only used by tests
		new(String to, EReference reference) {
			super(new Node(to), reference)
		}
	}

}
