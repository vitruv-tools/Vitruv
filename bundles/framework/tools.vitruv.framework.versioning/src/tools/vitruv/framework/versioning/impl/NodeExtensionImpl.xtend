package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.NodeExtension
import org.graphstream.graph.Node
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.EdgeExtension

class NodeExtensionImpl implements NodeExtension {
	static val uiLabel = "ui.label"
	static extension EdgeExtension ee = EdgeExtension::newManager

	static def NodeExtension init() {
		new NodeExtensionImpl
	}

	private new() {
	}

	override isLeave(Node node) {
		val x = node.leavingEdgeSet.forall[!isType(EdgeType.REQUIRES)]
		return x
	}

	override setLabel(Node node, String label) {
		node.addAttribute(uiLabel, label)
	}

}
