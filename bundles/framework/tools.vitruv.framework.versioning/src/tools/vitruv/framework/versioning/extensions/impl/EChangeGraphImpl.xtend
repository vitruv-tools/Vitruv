package tools.vitruv.framework.versioning.extensions.impl

import org.graphstream.graph.implementations.SingleGraph
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants

class EChangeGraphImpl extends SingleGraph {
	private static val String styleSheet = '''
		node {
			fill-color: blue;
		}
		edge.«EdgeType.PROVIDES.toString» {
			fill-color: red;
		}
		edge.«EdgeType.TRIGGERS.toString» {
			fill-color: black;
		}
	'''

	new(String id) {
		super(id)
		nodeFactory = new EChangeNodeFactoryImpl
		addAttribute(GraphStreamConstants::stylesheet, styleSheet)
	}
}
