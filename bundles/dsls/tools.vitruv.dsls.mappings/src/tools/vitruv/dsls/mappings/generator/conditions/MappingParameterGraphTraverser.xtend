package tools.vitruv.dsls.mappings.generator.conditions

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.conditions.impl.InValueConditionGenerator

class MappingParameterGraphTraverser {

	private Map<String, Node> nodes = new HashMap

	new(List<FeatureConditionGenerator> conditions, List<String> parameters) {
		constructGraph(conditions, parameters)
	}

	private def constructGraph(List<FeatureConditionGenerator> conditions, List<String> parameters) {
		parameters.forEach [ parameter |
			parameter.constructNode
		]
		conditions.filter[it instanceof InValueConditionGenerator].forEach [
			createInRelation(it as InValueConditionGenerator)
		]
		nodes.values.forEach [
			println('''Node «it.parameter»  «IF !parents.empty»Parents: «FOR parent: it.parents »«parent.parameter» «ENDFOR»«ENDIF» «IF !it.children.empty» Children: «FOR child: it.children» «child.node.parameter» «ENDFOR»«ENDIF»''')
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
		val childNode = nodes.get(childParameter.value.name)
		val parentNode = nodes.get(parentParameter.value.name)
		parentNode.addChild(childNode, inFeature)
		childNode.addParent(parentNode)
	}

	private def validateGraph() {
		// check for cycles (cycles are allowed)
		nodes.values.forEach[cycleCheck(new ArrayList)]
		// check for unconnected nodes
		val shouldReachNodesCount = nodes.size - 1
		if (nodes.values.map[reachableNodesCount(new ArrayList)].min < shouldReachNodesCount) {
			// at least one of the nodes can not reach all of the other nodes
			throw new IllegalStateException(
				'A MappingParameter cannot be reached by the other nodes, graph is invalid!')
		}
	}

	public def findPath(String from, String to) {
		val fromNode = nodes.get(from)
		val toNode = nodes.get(to)
		fromNode.findPath(toNode, new NodePath(fromNode))
	}

	public def findPath(List<String> startPoints, String to) {
		startPoints.map[it.findPath(to)].minBy[it.steps.size]
	}

	public def findStepToNextNode(List<String> startPoints) {
		val remainingNodes = nodes.keySet.filter[!startPoints.contains(it)]
		val path = remainingNodes.map[target|findPath(startPoints, target)].minBy[steps.size]
		if (path.steps.size > 1) {
			// this should never happen
			throw new IllegalStateException('Could not find step to next node, something must be terribly wrong!')
		}
		path
	}

	public def getNode(String parameter) {
		return nodes.get(parameter)
	}

	public static class Node {
		private List<Node> parents = new ArrayList
		@Accessors(PUBLIC_GETTER)
		private List<ChildNode> children = new ArrayList
		@Accessors(PUBLIC_GETTER)
		private String parameter

		new(String parameter) {
			this.parameter = parameter
		}

		public def addParent(Node parent) {
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

		public def getChild(String childParameter) {
			children.findFirst[node.parameter == childParameter]
		}

		public def addChild(Node child, EReference inFeature) {
			this.children += new ChildNode(child, inFeature)
		}

		public def void cycleCheck(List<Node> visitedNodes) throws IllegalStateException{
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

		public def int reachableNodesCount(List<Node> discoveredNodes) {
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
			].reduce[p1, p2|new Integer(p1.intValue + p2.intValue)]
		}

		public def NodePath findPath(Node node, NodePath path) {
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

	public static class ChildNode {
		@Accessors(PUBLIC_GETTER)
		private Node node
		@Accessors(PUBLIC_GETTER)
		private EReference inFeature

		new(Node node, EReference inFeature) {
			this.node = node
			this.inFeature = inFeature
		}
	}

	public static class NodePath {
		private Node startNode
		@Accessors(PUBLIC_GETTER)
		private List<TraverseStep> steps = new ArrayList

		new(Node startNode) {
			this.startNode = startNode
		}

		public def getStartNode() {
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

	public abstract static class TraverseStep {
		@Accessors(PUBLIC_GETTER)
		protected EReference feature
		protected Node to

		new(Node to, EReference feature) {
			this.to = to
			this.feature = feature
		}

		public def getParameter() {
			return to.parameter
		}
	}

	public static class TraverseStepDown extends TraverseStep {

		new(ChildNode to) {
			super(to.node, to.inFeature)
		}

		// only used by tests
		new(String to, EReference reference) {
			super(new Node(to), reference)
		}
	}

	public static class TraverseStepUp extends TraverseStep {
		new(Node to, EReference feature) {
			super(to, feature)
		}

		// only used by tests
		new(String to, EReference reference) {
			super(new Node(to), reference)
		}
	}

}
