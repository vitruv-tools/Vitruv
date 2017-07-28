package tools.vitruv.framework.versioning.extensions.impl

import org.graphstream.graph.implementations.SingleGraph
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants
import tools.vitruv.framework.versioning.NodeType
import tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertRootImpl

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
		node.«NodeType::UNPAIRED.toString» {
			shadow-color: red; 
			shadow-width: 60px; 
			shadow-mode: plain;
		}
		node.«CreateAndInsertRootImpl.simpleName» {
			size: 50px;
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
