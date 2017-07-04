package tools.vitruv.framework.versioning.extensions.impl

import org.graphstream.graph.Node
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EdgeExtension
import tools.vitruv.framework.versioning.extensions.NodeExtension
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants

class NodeExtensionImpl implements NodeExtension {
	static extension EdgeExtension = EdgeExtension::instance

	static def NodeExtension init() {
		new NodeExtensionImpl
	}

	private new() {
	}

	override isLeave(Node node) {
		node.enteringEdgeSet.forall[!isType(EdgeType::PROVIDES)]
	}

	override setLabel(Node node, String label) {
		node.addAttribute(GraphStreamConstants::uiLabel, label)
	}
}
