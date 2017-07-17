package tools.vitruv.framework.versioning.extensions.impl

import tools.vitruv.framework.versioning.extensions.EChangeEdge
import org.eclipse.xtend.lib.annotations.Accessors
import org.graphstream.graph.Edge
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.AbstractEdge
import org.graphstream.graph.implementations.AbstractNode
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeCompareUtil
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.extensions.EdgeTypeExtension
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants

class EChangeEdgeImpl extends AbstractEdge implements EChangeEdge {
	static extension EChangeCompareUtil = EChangeCompareUtil::instance
	static extension EdgeTypeExtension = EdgeTypeExtension::instance
	@Accessors(PUBLIC_GETTER)
	EdgeType type

	protected new(String id, Node source, Node target, boolean directed) {
		super(id, source as AbstractNode, target as AbstractNode, directed)
	}

	override setType(EdgeType type) {
		this.type = type
		addAttribute(GraphStreamConstants::uiLabel, type.edgeLabel)
		addAttribute(GraphStreamConstants::uiClass, type.toString)
	}

	override isType(EdgeType edgeType) {
		type === edgeType
	}

	override isEChangeEdgeEqual(Edge e2) {
		val sourceEqual = isEChangeEqual(<EChangeNode>sourceNode.EChange, e2.<EChangeNode>sourceNode.EChange)
		val targetEqual = isEChangeEqual(<EChangeNode>targetNode.EChange, e2.<EChangeNode>targetNode.EChange)
		return sourceEqual && targetEqual
	}
}
