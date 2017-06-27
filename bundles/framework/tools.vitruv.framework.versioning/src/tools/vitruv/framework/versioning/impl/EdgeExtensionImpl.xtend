package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.EdgeExtension
import org.graphstream.graph.Edge
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.EdgeTypeExtension

class EdgeExtensionImpl implements EdgeExtension {
	static extension EdgeTypeExtension = EdgeTypeExtension::newManager
	static val uiLabel = "ui.label"

	static def EdgeExtension init() {
		new EdgeExtensionImpl
	}

	private new() {
	}

	override setType(Edge edge, EdgeType type) {
		edge.addAttribute(uiLabel, type.edgeLabel)
	}

	override isType(Edge edge, EdgeType type) {
		edge.id.contains(type.toString)
	}
}
