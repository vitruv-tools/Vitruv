package tools.vitruv.framework.versioning.extensions.impl

import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EdgeTypeExtension

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
