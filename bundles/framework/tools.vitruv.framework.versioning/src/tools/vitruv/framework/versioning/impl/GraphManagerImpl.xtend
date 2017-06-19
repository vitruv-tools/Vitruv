package tools.vitruv.framework.versioning.impl

import org.eclipse.xtend.lib.annotations.Accessors
import org.graphstream.graph.Graph
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.GraphManager
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import org.graphstream.graph.Edge
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EAttribute

//import org.apache.log4j.Logger
class GraphManagerImpl implements GraphManager {
//	static val logger = Logger::getLogger(ConflictDetectorImpl)
	static val transactionalIdentifier = "transactional"
	static val affectedIdentifier = "affected"
	static val uiLabel = "ui.label"
	@Accessors(PUBLIC_GETTER)
	val Graph graph

	static def GraphManager init() {
		new GraphManagerImpl
	}

	private static def String getTransactionalEdgeId(TransactionalChange t, EChange e) {
		'''«transactionalIdentifier»: «t» to «e»'''
	}

	private static def String getTransactionalEdgeLabel(TransactionalChange t, EChange e) {
		'''contains'''
	}

	private static def String getAffectedEdgeLabel(EObject ob) {
		'''«ob.toString.substring(0)»'''
	}

	private static dispatch def String getShortString(EObject e) {
		e.toString
	}

	private static dispatch def String getShortString(EAttribute e) {
		'''«e.name»'''
	}

	private static def String createAffectedEdgeName(EChange e1, EChange e2) {
		'''«affectedIdentifier»: «e1» to «e2»'''
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
		'''replace «e.affectedFeature.shortString»  with "«e.newValue»" at «e.affectedEObject.class.name»'''
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

	override addNode(VitruviusChange e) {
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
			addTransactionalEdge(e, it)

		]
	}

	private dispatch def void addNodeImpl(EChange e) {
		val node = graph.addNode(e.nodeId)
		node.addAttribute(uiLabel, e.nodeLabel)
	}

	override checkIfEdgeExists(EChange e1, EChange e2) {
		val node1 = e1.node
		val node2 = e2.node
		val x = node1.hasEdgeBetween(node2)
		return x
	}

	override addAffectedEdge(EChange e1, EChange e2, EObject affectedObject) {
		val edge = addChangeEdge(createAffectedEdgeName(e1, e2), e1, e2, false)
		edge.addAttribute(uiLabel, affectedObject.affectedEdgeLabel)
	}

	private def dispatch Edge addChangeEdge(String n, TransactionalChange e1, EChange e2, boolean directed) {
		graph.addEdge(n, e1.nodeId, e2.nodeId, directed)
	}

	private def dispatch Edge addChangeEdge(String n, EChange e1, EChange e2, boolean directed) {
		graph.addEdge(n, e1.nodeId, e2.nodeId, directed)
	}

	private dispatch def Node getNode(EChange e) {
		graph.getNode(e.nodeId)
	}

	private dispatch def Node getNode(TransactionalChange t) {
		graph.getNode(t.nodeId)
	}

	private def addTransactionalEdge(TransactionalChange t, EChange e) {
		val edge = addChangeEdge(getTransactionalEdgeId(t, e), t, e, true)
		edge.addAttribute(uiLabel, getTransactionalEdgeLabel(t, e))
	}

}
