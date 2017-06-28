package tools.vitruv.framework.versioning.extensions.impl

import org.graphstream.graph.Edge
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EdgeExtension
import tools.vitruv.framework.versioning.extensions.EdgeTypeExtension
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants

class EdgeExtensionImpl implements EdgeExtension {
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
}
