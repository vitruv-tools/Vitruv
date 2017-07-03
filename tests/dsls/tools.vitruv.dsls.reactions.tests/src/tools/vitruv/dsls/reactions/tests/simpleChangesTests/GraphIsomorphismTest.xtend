package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import org.junit.Test
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.AbstractGraphTest
import tools.vitruv.framework.versioning.IsomorphismTesterAlgorithm
import tools.vitruv.framework.versioning.impl.PrimitiveIsomorphismTesterImpl
import tools.vitruv.framework.versioning.extensions.GraphExtension
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.EdgeType

class GraphIsomorphismTest extends AbstractGraphTest {
	static extension GraphExtension = GraphExtension::newManager

	@Test
	def void testEqual() {
		graph = dependencyGraphCreator.createDependencyGraph(changes)
		val otherChanges = branchDiff.compareChanges.map[originalChange]
		val otherGraph = dependencyGraphCreator.createDependencyGraph(otherChanges)
		val IsomorphismTesterAlgorithm tester = new PrimitiveIsomorphismTesterImpl
		tester.init(graph, otherGraph)
		tester.compute
		assertThat(tester.isIsomorphic, is(true))
		val combinedGraph = tester.combinedGraph
		assertThat(combinedGraph.nodeSet.size, is(graph.nodeSet.size + otherGraph.nodeSet.size))
		assertThat(combinedGraph.edgeSet.size >= graph.edgeSet.size + otherGraph.edgeSet.size, is(true))
		val iso = tester.isomorphism
		graph.<EChangeNode>nodeSet.forEach [ node1 |
			val node2 = iso.get(node1)
			assertThat(otherGraph.<EChangeNode>nodeSet.exists[node2 == it], is(true))
			assertThat(combinedGraph.checkIfEdgeExists(node1.EChange, node2.EChange, EdgeType::ISOMORPHIC), is(true))
			assertThat(node1.enteringEdgeSet.size, is(node2.enteringEdgeSet.size))
			assertThat(node1.leavingEdgeSet.size, is(node2.leavingEdgeSet.size))
		]
	}

}
