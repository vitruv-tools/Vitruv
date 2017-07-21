package tools.vitruv.framework.versioning.extensions.impl

import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.graphstream.graph.Node
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeEdge
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants
import tools.vitruv.framework.versioning.extensions.NodeExtension

class NodeExtensionImpl implements NodeExtension {

	static def NodeExtension init() {
		new NodeExtensionImpl
	}

	private new() {
	}

	override isProvideLeave(Node node) {
		node.determineIsLeave([EChangeEdge e|!e.isType(EdgeType::REQUIRED)])
	}

	override setLabel(Node node, String label) {
		node.addAttribute(GraphStreamConstants::uiLabel, label)
	}

	override isLeave(Node node) {
		node.determineIsLeave([EChangeEdge e|!e.isType(EdgeType::REQUIRED) && !e.isType(EdgeType::TRIGGERS)])
	}

	private static def determineIsLeave(Node node, Function1<EChangeEdge, Boolean> predicate) {
		node.<EChangeEdge>enteringEdgeSet.forall[predicate.apply(it)]
	}

}
