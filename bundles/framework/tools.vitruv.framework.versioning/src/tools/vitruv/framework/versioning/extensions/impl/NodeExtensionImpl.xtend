package tools.vitruv.framework.versioning.extensions.impl

import org.graphstream.graph.Node
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EdgeExtension
import tools.vitruv.framework.versioning.extensions.NodeExtension

class NodeExtensionImpl implements NodeExtension {
	static val uiLabel = "ui.label"
	static extension EdgeExtension = EdgeExtension::newManager

	static def NodeExtension init() {
		new NodeExtensionImpl
	}

	private new() {
	}

	override isLeave(Node node) {
		val x = node.enteringEdgeSet.forall[!isType(EdgeType::PROVIDES)]
		return x
	}

	override setLabel(Node node, String label) {
		node.addAttribute(uiLabel, label)
	}

}
