package tools.vitruv.framework.versioning.extensions.impl

import org.graphstream.graph.EdgeFactory
import tools.vitruv.framework.versioning.extensions.EChangeEdge
import org.graphstream.graph.Node

class EChangeEdgeFactoryImpl implements EdgeFactory<EChangeEdge> {

	override newInstance(String id, Node src, Node dst, boolean directed) {
		new EChangeEdgeImpl(id, src, dst, directed)
	}

}
