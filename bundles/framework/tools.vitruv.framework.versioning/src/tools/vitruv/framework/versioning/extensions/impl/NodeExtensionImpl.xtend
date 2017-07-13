package tools.vitruv.framework.versioning.extensions.impl

import org.graphstream.graph.Node
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EdgeExtension
import tools.vitruv.framework.versioning.extensions.NodeExtension
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.graphstream.graph.Edge

class NodeExtensionImpl implements NodeExtension {
	static extension EdgeExtension = EdgeExtension::instance

	static def NodeExtension init() {
		new NodeExtensionImpl
	}

	private new() {
	}

	override isProvideLeave(Node node) {
		node.determineIsLeave([Edge e|!e.isType(EdgeType::REQUIRED)])
	}

	override setLabel(Node node, String label) {
		node.addAttribute(GraphStreamConstants::uiLabel, label)
	}

	override isLeave(Node node) {
		node.determineIsLeave([Edge e|!e.isType(EdgeType::REQUIRED) && !e.isType(EdgeType::TRIGGERS)])
	}

	private static def determineIsLeave(Node node, Function1<Edge, Boolean> predicate) {
		node.enteringEdgeSet.forall[predicate.apply(it)]
	}

}
