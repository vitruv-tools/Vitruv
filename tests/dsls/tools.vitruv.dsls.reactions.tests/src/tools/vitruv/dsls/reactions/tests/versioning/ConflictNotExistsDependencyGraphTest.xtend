package tools.vitruv.dsls.reactions.tests.versioning

import java.util.Collection
import org.junit.Test
import tools.vitruv.dsls.reactions.tests.AbstractConflictNotExistsTest
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeNode

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class ConflictNotExistsDependencyGraphTest extends AbstractConflictNotExistsTest {

	@Test
	def void testEChangeNode() {
		graph = dependencyGraphCreator.createDependencyGraph(changes)
		val Collection<EChangeNode> nodeSet = graph.nodeSet
		echanges.forEach[echange|nodeSet.exists[echange === EChange]]
	}

	@Test
	def testRequireEdges() {
		graph = dependencyGraphCreator.createDependencyGraph(changes)
		val requiresEdges = graph.edgesWithType(EdgeType::PROVIDES)
		assertThat(requiresEdges.size, is(7))
		assertThat(graph.checkIfEdgeExists(echanges.get(0), echanges.get(1), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(0), echanges.get(2), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(0), echanges.get(4), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(0), echanges.get(6), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(2), echanges.get(3), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(4), echanges.get(5), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(6), echanges.get(7), EdgeType.PROVIDES), is(true))
		assertThat(graph.leaves.length, is(1))
		assertThat(graph.leaves.toList.get(0), is(graph.getNode(echanges.get(0))))
		assertThat(graph.calculateComponentNumber, is(1))
	}

	@Test
	def testSeveralComponents() {
		graph = dependencyGraphCreator.createDependencyGraph(changes.drop(1).toList)
		val requiresEdges = graph.edgesWithType(EdgeType.PROVIDES)
		assertThat(requiresEdges.size, is(3))
		assertThat(graph.checkIfEdgeExists(echanges.get(2), echanges.get(3), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(4), echanges.get(5), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(echanges.get(6), echanges.get(7), EdgeType.PROVIDES), is(true))
		assertThat(graph.leaves.length, is(3))
		assertThat(graph.calculateComponentNumber, is(3))
		val currentSubgraphs = graph.subgraphs
		assertThat(currentSubgraphs.length, is(3))
		currentSubgraphs.forEach [ g |
			assertThat(g.nodeSet.size, is(2))
			assertThat(g.edgeSet.size, is(1))
		]

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
		val requiresEdges = graph.edgesWithType(EdgeType.PROVIDES)
		assertThat(requiresEdges.size, is(14))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(0), originalEChanges.get(1), EdgeType.PROVIDES),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(0), originalEChanges.get(2), EdgeType.PROVIDES),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(0), originalEChanges.get(4), EdgeType.PROVIDES),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(0), originalEChanges.get(6), EdgeType.PROVIDES),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(2), originalEChanges.get(3), EdgeType.PROVIDES),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(4), originalEChanges.get(5), EdgeType.PROVIDES),
			is(true))
		assertThat(graph.checkIfEdgeExists(originalEChanges.get(6), originalEChanges.get(7), EdgeType.PROVIDES),
			is(true))

		assertThat(graph.checkIfEdgeExists(targetEChanges.get(0), targetEChanges.get(1), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(0), targetEChanges.get(2), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(0), targetEChanges.get(4), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(0), targetEChanges.get(6), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(2), targetEChanges.get(3), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(4), targetEChanges.get(5), EdgeType.PROVIDES), is(true))
		assertThat(graph.checkIfEdgeExists(targetEChanges.get(6), targetEChanges.get(7), EdgeType.PROVIDES), is(true))

		originalEChanges.forEach [ c, i |
			val otherEdge = targetEChanges.get(i)
			assertThat('''No edge between «c» and «otherEdge»''',
				graph.checkIfEdgeExists(c, otherEdge, EdgeType::TRIGGERS), is(true))
		]
		assertThat(graph.leaves.length, is(2))
		assertThat(graph.leaves.toList.get(0), is(graph.getNode(originalEChanges.get(0))))
		assertThat(graph.calculateComponentNumber, is(2))
	}
}
