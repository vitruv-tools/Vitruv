package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import org.junit.Test
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.impl.DependencyGraphCreatorImpl
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class DependencyGraphTest extends NoConflict {
	@Test
	def testGraph() {
		val changes = branchDiff.baseChanges.map[originalChange].toList
		val DependencyGraphCreator dependencyGraphCreator = new DependencyGraphCreatorImpl
		val graph = dependencyGraphCreator.createDependencyGraph(changes)

		assertThat(graph.vertexCount, is(8))
		assertThat(graph.edgeCount, is(8))
	}

}
