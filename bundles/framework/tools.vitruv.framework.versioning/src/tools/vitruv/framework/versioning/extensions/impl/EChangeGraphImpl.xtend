package tools.vitruv.framework.versioning.extensions.impl

import org.graphstream.graph.implementations.SingleGraph
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants
import tools.vitruv.framework.versioning.NodeType

class EChangeGraphImpl extends SingleGraph {
	private static val String styleSheet = '''
		node {
			fill-color: blue;
		}
		node.graph0 {
			fill-color: green;
		}
		node.graph1 {
			fill-color: yellow;
		}
		node.graph0«NodeType::UNPAIRED.toString» {
			fill-color: red;
		}
		node.graph1«NodeType::UNPAIRED.toString» {
			fill-color: orange;
		}
		edge.«EdgeType::REQUIRED.toString» {
			visibility-mode: hidden;
		}
		edge.«EdgeType::REQUIRES.toString» {
			fill-color: red;
		}
		edge.«EdgeType::TRIGGERS.toString» {
			fill-color: black;
		}
		edge.«EdgeType::CONFLICTS.toString» {	
			fill-color: blue;
		}
		edge.«EdgeType::ISOMORPHIC.toString» {
			fill-color: brown;
		}
	'''

	new(String id) {
		super(id)
		nodeFactory = new EChangeNodeFactoryImpl
		edgeFactory = new EChangeEdgeFactoryImpl
		addAttribute(GraphStreamConstants::stylesheet, styleSheet)
	}
}
