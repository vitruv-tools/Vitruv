package tools.vitruv.framework.versioning.extensions.impl

import org.graphstream.graph.Edge
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EdgeExtension
import tools.vitruv.framework.versioning.extensions.EdgeTypeExtension
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.extensions.EChangeCompareUtil

class EdgeExtensionImpl implements EdgeExtension {
	static extension EChangeCompareUtil = EChangeCompareUtil::newManager
	static extension EdgeTypeExtension = EdgeTypeExtension::newManager

	static def EdgeExtension init() {
		new EdgeExtensionImpl
	}

	private new() {
	}

	override setType(Edge edge, EdgeType type) {
		edge.addAttribute(GraphStreamConstants::uiLabel, type.edgeLabel)
		edge.addAttribute(GraphStreamConstants::uiClass, type.toString)
	}

	override isType(Edge edge, EdgeType type) {
		edge.id.contains(type.toString)
	}

	override isEChangeEdgeEqual(Edge e1, Edge e2) {
		val sourceEqual = isEChangeEqual(e1.<EChangeNode>sourceNode.EChange, e2.<EChangeNode>sourceNode.EChange)
		val targetEqual = isEChangeEqual(e1.<EChangeNode>targetNode.EChange, e2.<EChangeNode>targetNode.EChange)
		return sourceEqual && targetEqual
	}

}
