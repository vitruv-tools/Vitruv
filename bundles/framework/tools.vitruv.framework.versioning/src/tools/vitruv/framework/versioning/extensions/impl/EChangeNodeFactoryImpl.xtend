package tools.vitruv.framework.versioning.extensions.impl

import org.graphstream.graph.NodeFactory
import org.graphstream.graph.Graph
import tools.vitruv.framework.versioning.extensions.EChangeNode
import org.graphstream.graph.implementations.AbstractGraph

class EChangeNodeFactoryImpl implements NodeFactory<EChangeNode> {

	override newInstance(String id, Graph graph) {
		new EChangeNodeImpl(graph as AbstractGraph, id)
	}

}
