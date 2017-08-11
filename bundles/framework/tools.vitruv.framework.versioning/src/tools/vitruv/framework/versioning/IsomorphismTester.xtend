package tools.vitruv.framework.versioning

import org.graphstream.graph.Graph

interface IsomorphismTester {
	def boolean areIsomorphic(Graph g1, Graph g2)
}
