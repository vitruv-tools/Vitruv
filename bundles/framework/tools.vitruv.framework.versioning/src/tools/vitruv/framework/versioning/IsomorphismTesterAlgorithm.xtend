package tools.vitruv.framework.versioning

import edu.ucla.sspace.graph.graphstream.isomorphism.IsomorphismTester
import org.graphstream.graph.Graph
import tools.vitruv.framework.versioning.extensions.EChangeNode
import java.util.Map

interface IsomorphismTesterAlgorithm extends IsomorphismTester {
	def void init(Graph g1, Graph g2)

	def void compute()

	def boolean isIsomorphic()

	def Graph getCombinedGraph()

	def Map<EChangeNode, EChangeNode> getIsomorphism()
}
