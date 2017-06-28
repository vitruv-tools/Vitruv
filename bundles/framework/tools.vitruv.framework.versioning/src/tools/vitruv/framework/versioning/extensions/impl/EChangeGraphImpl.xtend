package tools.vitruv.framework.versioning.extensions.impl

import org.graphstream.graph.implementations.SingleGraph

class EChangeGraphImpl extends SingleGraph {

	new(String id) {
		super(id)
		nodeFactory = new EChangeNodeFactoryImpl
	}
}
