package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import org.junit.Test
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.GraphManager
import tools.vitruv.framework.versioning.impl.DependencyGraphCreatorImpl
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class DependencyGraphTest extends NoConflict {
	extension GraphManager gm = GraphManager::newManager

	@Test
	def testGraph() {
		val changes = branchDiff.baseChanges.map[originalChange].toList
		val echanges = changes.map[EChanges].flatten.toList
		val DependencyGraphCreator dependencyGraphCreator = new DependencyGraphCreatorImpl
		graph = dependencyGraphCreator.createDependencyGraph(changes)
//		assertThat(graph.vertexCount, is(8))
		val requiresEdges = edgesWithType(EdgeType.REQUIRES)
		assertThat(requiresEdges.size, is(7))
		assertThat(checkIfEdgeExists(echanges.get(1), echanges.get(0)), is(true))
		assertThat(checkIfEdgeExists(echanges.get(2), echanges.get(0)), is(true))
		assertThat(checkIfEdgeExists(echanges.get(4), echanges.get(0)), is(true))
		assertThat(checkIfEdgeExists(echanges.get(6), echanges.get(0)), is(true))
		assertThat(checkIfEdgeExists(echanges.get(3), echanges.get(2)), is(true))
		assertThat(checkIfEdgeExists(echanges.get(5), echanges.get(4)), is(true))
		assertThat(checkIfEdgeExists(echanges.get(7), echanges.get(6)), is(true))

	}

}
