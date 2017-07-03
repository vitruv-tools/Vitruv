package tools.vitruv.framework.versioning.extensions.impl

import org.eclipse.xtend.lib.annotations.Accessors
import org.graphstream.graph.implementations.AbstractGraph
import org.graphstream.graph.implementations.SingleNode
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.extensions.EdgeExtension
import tools.vitruv.framework.versioning.extensions.EChangeCompareUtil

class EChangeNodeImpl extends SingleNode implements EChangeNode {
	static extension EChangeCompareUtil = EChangeCompareUtil::newManager
	static extension EdgeExtension = EdgeExtension::newManager
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	EChange eChange

	protected new(AbstractGraph graph, String id) {
		super(graph, id)
	}

	override isEChangeNodeEqual(EChangeNode node2) {
		val echangeEqual = isEChangeEqual(EChange, node2.EChange)
		val edgesEqual = edgeIterator.forall [ edge1 |
			node2.exists[edge2|isEChangeEdgeEqual(edge1, edge2)]
		]
		return echangeEqual && edgesEqual
	}

}
