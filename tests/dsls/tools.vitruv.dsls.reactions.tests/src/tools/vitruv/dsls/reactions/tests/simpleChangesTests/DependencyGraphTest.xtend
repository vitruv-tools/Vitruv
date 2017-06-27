package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import org.junit.Test
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.GraphManager
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class DependencyGraphTest extends NoConflict {
	extension GraphManager gm = GraphManager::newManager

	@Test
	def testRequireEdges() {
		val changes = branchDiff.baseChanges.map[originalChange].toList
		val echanges = changes.map[EChanges].flatten.toList
		val dependencyGraphCreator = DependencyGraphCreator::createDependencyGraphCreator
		graph = dependencyGraphCreator.createDependencyGraph(changes)
//		assertThat(graph.vertexCount, is(8))
		val requiresEdges = edgesWithType(EdgeType.REQUIRES)
		assertThat(requiresEdges.size, is(7))
		assertThat(checkIfEdgeExists(echanges.get(1), echanges.get(0), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(2), echanges.get(0), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(4), echanges.get(0), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(6), echanges.get(0), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(3), echanges.get(2), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(5), echanges.get(4), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(echanges.get(7), echanges.get(6), EdgeType.REQUIRES), is(true))
		assertThat(leaves.length, is(1))
		assertThat(leaves.toList.get(0), is(echanges.get(0).node))
	}

	@Test
	def testRequireEdgesChangeMatches() {
		val originalEChanges = branchDiff.baseChanges.map[originalChange].map[EChanges].flatten.toList
		val targetEChanges = branchDiff.baseChanges.map[it.targetToCorrespondentChanges.values].flatten.flatten.map [
			EChanges
		].flatten.toList
		assertThat(originalEChanges.length, is(targetEChanges.length))
		val dependencyGraphCreator = DependencyGraphCreator::createDependencyGraphCreator
		graph = dependencyGraphCreator.createDependencyGraphFromChangeMatches(branchDiff.baseChanges)
//		assertThat(graph.vertexCount, is(8))
		val requiresEdges = edgesWithType(EdgeType.REQUIRES)
		assertThat(requiresEdges.size, is(14))
		assertThat(checkIfEdgeExists(originalEChanges.get(1), originalEChanges.get(0), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(originalEChanges.get(2), originalEChanges.get(0), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(originalEChanges.get(4), originalEChanges.get(0), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(originalEChanges.get(6), originalEChanges.get(0), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(originalEChanges.get(3), originalEChanges.get(2), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(originalEChanges.get(5), originalEChanges.get(4), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(originalEChanges.get(7), originalEChanges.get(6), EdgeType.REQUIRES), is(true))

		assertThat(checkIfEdgeExists(targetEChanges.get(1), targetEChanges.get(0), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(targetEChanges.get(2), targetEChanges.get(0), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(targetEChanges.get(4), targetEChanges.get(0), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(targetEChanges.get(6), targetEChanges.get(0), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(targetEChanges.get(3), targetEChanges.get(2), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(targetEChanges.get(5), targetEChanges.get(4), EdgeType.REQUIRES), is(true))
		assertThat(checkIfEdgeExists(targetEChanges.get(7), targetEChanges.get(6), EdgeType.REQUIRES), is(true))

		originalEChanges.forEach [ c, i |
			val otherEdge = targetEChanges.get(i)
			assertThat('''No edge between «c» and «otherEdge»''', checkIfEdgeExists(c, otherEdge, EdgeType.TRIGGERS),
				is(true))
		]
		assertThat(leaves.length, is(2))
		assertThat(leaves.toList.get(0), is(originalEChanges.get(0).node))
	}

}
