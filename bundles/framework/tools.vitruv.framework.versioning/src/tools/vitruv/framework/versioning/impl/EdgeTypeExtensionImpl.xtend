package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.EdgeTypeExtension
import tools.vitruv.framework.versioning.EdgeType

class EdgeTypeExtensionImpl implements EdgeTypeExtension {
	static def EdgeTypeExtension init() {
		new EdgeTypeExtensionImpl
	}

	private static def String getEdgeLabelImpl(EdgeType type) { type.toString }

	private new() {
	}

	override getEdgeLabel(EdgeType type) {
		type.edgeLabelImpl
	}

}
