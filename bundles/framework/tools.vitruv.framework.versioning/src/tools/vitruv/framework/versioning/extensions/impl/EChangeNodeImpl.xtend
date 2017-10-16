package tools.vitruv.framework.versioning.extensions.impl

import org.eclipse.xtend.lib.annotations.Accessors
import org.graphstream.graph.implementations.AbstractGraph
import org.graphstream.graph.implementations.SingleNode
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.NodeType
import tools.vitruv.framework.versioning.extensions.EChangeCompareUtil
import tools.vitruv.framework.versioning.extensions.EChangeEdge
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants
import org.eclipse.xtext.xbase.lib.Functions.Function1

class EChangeNodeImpl extends SingleNode implements EChangeNode {
	static extension EChangeCompareUtil = EChangeCompareUtil::instance
	@Accessors(PUBLIC_GETTER)
	EChange eChange
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	boolean triggered

	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	VURI vuri

	protected new(AbstractGraph graph, String id) {
		super(graph, id)
	}

	override setEChange(EChange e) {
		eChange = e
		val x = getAttribute(GraphStreamConstants::uiClass)
		val prefix = if(null === x || "" === x) "" else x + ", "
		val String s = '''«prefix»«e.class.simpleName»'''
		setAttribute(GraphStreamConstants::uiClass, s)
	}

	override isEChangeNodeEqual(EChangeNode node2) {
		val echangeEqual = isEChangeEqual(EChange, node2.EChange)
		val edgesEqual = <EChangeEdge>edgeIterator.forall [ edge1 |
			node2.exists[edge2|edge1.isEChangeEdgeEqual(edge2)]
		]
		val otherEdgesEqual = node2.<EChangeEdge>edgeIterator.forall [ edge1 |
			exists[edge2|edge1.isEChangeEdgeEqual(edge2)]
		]
		return echangeEqual && (edgesEqual || otherEdgesEqual)
	}

	override setType(NodeType type) {
		val x = getAttribute(GraphStreamConstants::uiClass)
		val String s = '''«x», «type.toString»'''
		setAttribute(GraphStreamConstants::uiClass, s)
	}

	override isConflicting() {
		return <EChangeEdge>edgeSet.exists[isType(EdgeType::CONFLICTS)]
	}

	override isProvideLeave() {
		determineIsLeave([EChangeEdge e|!e.isType(EdgeType::REQUIRED)])
	}

	override setLabel(String label) {
		addAttribute(GraphStreamConstants::uiLabel, label)
	}

	override isLeave() {
		determineIsLeave([EChangeEdge e|!e.isType(EdgeType::REQUIRED) && !e.isType(EdgeType::TRIGGERS)])
	}

	private def determineIsLeave(Function1<EChangeEdge, Boolean> predicate) {
		<EChangeEdge>enteringEdgeSet.forall[predicate.apply(it)]
	}
}
