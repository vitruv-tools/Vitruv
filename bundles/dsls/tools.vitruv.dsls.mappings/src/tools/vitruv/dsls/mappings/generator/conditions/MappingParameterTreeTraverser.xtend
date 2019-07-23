package tools.vitruv.dsls.mappings.generator.conditions

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.conditions.impl.InValueConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import java.util.Stack
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterTreeTraverser.TraverseStepDown
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterTreeTraverser.TraverseStepUp
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterTreeTraverser.NodePath
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping

class MappingParameterTreeTraverser {

	private Map<String, Node> nodes = new HashMap
	private Node topLevelNode

	new(List<FeatureConditionGenerator> conditions, List<String> parameters) {
		constructTree(conditions, parameters)
	}

	private def constructTree(List<FeatureConditionGenerator> conditions, List<String> parameters) {
		parameters.forEach [ parameter |
			parameter.constructNode
		]
		conditions.filter[it instanceof InValueConditionGenerator].forEach [
			createInRelation(it as InValueConditionGenerator)
		]
		nodes.values.forEach [
			println('''Node «it.parameter»  «IF it.parent!==null »Parent: «it.parent.parameter» «ENDIF» «IF !it.children.empty» Children: «FOR child: it.children» «child.node.parameter» «ENDFOR»«ENDIF»''')
		]
		topLevelNode = findTopLevelNode
	}

	private def constructNode(String parameter) {
		nodes.put(parameter, new Node(parameter))
	}

	private def createInRelation(InValueConditionGenerator condition) {
		val childParameter = condition.childParameter
		val inFeature = condition.inFeature
		val parentParameter = condition.parentParameter
		val childNode = nodes.get(childParameter.value.name)
		val parentNode = nodes.get(parentParameter.value.name)
		childNode.setParent(parentNode)
		parentNode.addChild(childNode, inFeature)
	}

	/**
	 * check the depth of all nodes and take the lowest one
	 */
	private def findTopLevelNode() {
		val topLevelNodes = nodes.values.filter[getDepth(new ArrayList) == 0]
		if (topLevelNodes.size > 1) {
			// its not a tree, we dont allow this
			throw new IllegalStateException('The MappingParameters in-relations do not resolve to a tree structure!')
		}
		topLevelNodes.get(0)
	}

	public def findPath(String from, String to) {
		val fromNode = nodes.get(from)
		val toNode = nodes.get(to)
		fromNode.findPath(toNode, new NodePath(fromNode))
	}

	public def findPath(List<String> startPoints, String to) {
		startPoints.map[it.findPath(to)].minBy[it.steps.size]
	}

	public def findPathToNextNode(List<String> startPoints) {
		val remainingNodes = nodes.keySet.filter[!startPoints.contains(it)]
		remainingNodes.map[target|findPath(startPoints, target)].minBy[steps.size]
	}

	public def getNode(String parameter) {
		return nodes.get(parameter)
	}

	private static class Node {
		private Node parent
		private List<ChildNode> children = new ArrayList
		private String parameter

		new(String parameter) {
			this.parameter = parameter
		}

		public def setParent(Node parent) {
			this.parent = parent
		}

		public def getChild(String childParameter) {
			children.findFirst[node.parameter == childParameter]
		}

		public def addChild(Node child, EStructuralFeature inFeature) {
			this.children += new ChildNode(child, inFeature)
		}

		public def int getDepth(List<Node> visitedNodes) {
			if (parent === null) {
				return 0
			}
			if (visitedNodes.contains(this)) {
				// we have a cycle!
				throw new IllegalStateException('The MappingParameters in-relations contains cycles!')
			}
			visitedNodes += this
			return parent.getDepth(visitedNodes) + 1
		}

		public def NodePath findPath(Node node, NodePath path) {
			if (this == node) {
				// found node
				return path
			}
			// traverse parent and child nodes, if they were not traversed already
			if (parent !== null) {
				if (!path.hasTraversedAlready(parent)) {
					val fork = path.copy
					// find the feature from the parent
					val parentChildNode = parent.children.findFirst[it == Node.this]
					//?. only because of the tests
					val feature = parentChildNode?.inFeature
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

	private static class ChildNode {
		private Node node
		private EStructuralFeature inFeature

		new(Node node, EStructuralFeature inFeature) {
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
		protected EStructuralFeature feature
		protected Node to

		new(Node to, EStructuralFeature feature) {
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
		new(String to) {
			super(new Node(to), null)
		}
	}

	public static class TraverseStepUp extends TraverseStep {
		new(Node to, EStructuralFeature feature) {
			super(to, feature)
		}

		// only used by tests
		new(String to) {
			super(new Node(to), null)
		}
	}

}
