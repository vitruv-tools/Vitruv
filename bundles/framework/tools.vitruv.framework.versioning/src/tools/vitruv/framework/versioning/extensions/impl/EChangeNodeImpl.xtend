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

class EChangeNodeImpl extends SingleNode implements EChangeNode {
	static extension EChangeCompareUtil = EChangeCompareUtil::instance
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	EChange eChange
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	boolean triggered

	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	VURI vuri

	protected new(AbstractGraph graph, String id) {
		super(graph, id)
	}

	override isEChangeNodeEqual(EChangeNode node2) {
		val echangeEqual = isEChangeEqual(EChange, node2.EChange)
		val edgesEqual = <EChangeEdge>edgeIterator.forall [ edge1 |
			node2.exists[edge2|edge1.isEChangeEdgeEqual(edge2)]
		]
		return echangeEqual && edgesEqual
	}

	override setType(NodeType type) {
		val x = getAttribute(GraphStreamConstants::uiClass)
		val String s = '''«x»«type.toString»'''
		setAttribute(GraphStreamConstants::uiClass, s)
		val newX = getAttribute(GraphStreamConstants::uiClass)
		if (x == newX)
			throw new IllegalArgumentException
	}

	override isConflicting() {
		return <EChangeEdge>edgeSet.exists[isType(EdgeType::CONFLICTS)]
	}

}
