package tools.vitruv.framework.versioning.impl

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.graphstream.graph.Edge
import org.graphstream.graph.Graph
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.GraphManager

class GraphManagerImpl implements GraphManager {
	static val uiLabel = "ui.label"
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Graph graph

	static def GraphManager init() {
		new GraphManagerImpl
	}

	private static def String getEdgeLabel(EdgeType type) {
		'''«type.toString»'''
	}

	private static def String createEdgeName(EChange e1, EChange e2, EdgeType type) {
		'''«e1» «type.toString» «e2»'''
	}

	private static dispatch def String getShortString(EObject e) {
		e.toString
	}

	private static dispatch def String getShortString(EAttribute e) {
		'''«e.name»'''
	}

	private static dispatch def String getNodeLabel(EChange e) {
		'''EChange «Integer.toHexString(e.hashCode)»'''
	}

	private static dispatch def String getNodeLabel(TransactionalChange e) {
		'''TC'''
	}

	private static dispatch def String getNodeLabel(ReplaceSingleValuedEReference<?, ?> e) {
		'''ReplaceSingleValuedEReference@«Integer.toHexString(e.hashCode)»'''
	}

	private static dispatch def String getNodeLabel(ReplaceSingleValuedEAttribute<?, ?> e) {
		'''replace «e.affectedFeature.shortString»  with "«e.newValue»"'''
	}

	private static dispatch def String getNodeLabel(CreateAndInsertNonRoot<?, ?> e) {
		'''CreateAndInsertNonRoot@«Integer.toHexString(e.hashCode)»'''
	}

	private static dispatch def String getNodeLabel(CreateAndReplaceNonRoot<?, ?> e) {
		'''CreateAndReplaceNonRoot@«Integer.toHexString(e.hashCode)»'''
	}

	private static dispatch def String getNodeId(EChange e) {
		e.toString
	}

	private static dispatch def String getNodeId(TransactionalChange e) {
		e.toString
	}

	private new() {
		graph = new SingleGraph("Test")
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")
	}

	override addNode(EChange e) {
		addNodeImpl(e)
	}

	private dispatch def addNodeImpl(VitruviusChange e) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	private dispatch def void addNodeImpl(TransactionalChange e) {
		val node = graph.addNode(e.nodeId)
		node.addAttribute(uiLabel, e.nodeLabel)
		e.EChanges.forEach [
			addNodeImpl
//			addTransactionalEdge(e, it)
		]
	}

	private dispatch def void addNodeImpl(EChange e) {
		val node = graph.addNode(e.nodeId)
		node.addAttribute(uiLabel, e.nodeLabel)
	}

	override getLeaves() {
		graph.nodeSet.filter[leave]
	}

	private def boolean isLeave(Node node) {
		val x = node.leavingEdgeSet.forall[!isType(EdgeType.REQUIRES)]
		return x
	}

	override checkIfEdgeExists(EChange e1, EChange e2) {
		val node1 = e1.node
		val node2 = e2.node
		val x = node1.hasEdgeBetween(node2)
		return x
	}

	private def boolean isType(Edge edge, EdgeType type) {
		edge.id.contains(type.toString)
	}

	override checkIfEdgeExists(EChange e1, EChange e2, EdgeType type) {
		val edgeExists = checkIfEdgeExists(e1, e2)
		if (!edgeExists)
			return false
		val edge = e1.node.getEdgeBetween(e2.node)
		return edge.isType(type)
	}

	private def dispatch Edge addChangeEdge(String n, TransactionalChange e1, EChange e2, boolean directed) {
		graph.addEdge(n, e1.nodeId, e2.nodeId, directed)
	}

	private def dispatch Edge addChangeEdge(String n, EChange e1, EChange e2, boolean directed) {
		graph.addEdge(n, e1.nodeId, e2.nodeId, directed)
	}

	override getNode(EChange e) {
		graph.getNode(e.nodeId)
	}

	override edgesWithType(EdgeType t) {
		graph.edgeSet.filter[id.contains(t.toString)]
	}

	override addEdge(EChange e1, EChange e2, EdgeType type) {
		switch (type) {
			case REQUIRES: {
				addDirectedEdge(e1, e2, type)
			}
			case TRIGGERS: {
				addDirectedEdge(e1, e2, type)
			}
			default: {
				throw new UnsupportedOperationException
			}
		}
	}

	private def void setType(Edge edge, EdgeType type) {
		edge.addAttribute(uiLabel, type.edgeLabel)
	}

	private def addDirectedEdge(EChange e1, EChange e2, EdgeType type) {
		val edge = addChangeEdge(createEdgeName(e1, e2, type), e1, e2, true)
		edge.type = type
	}

}
