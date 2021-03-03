package tools.vitruv.framework.tests.domains

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver

class EdgeCaseStateChangeTest extends StateChangePropagationTest {

	/**
	 * Tests the comparison of two states with no changes for the uml mockup model.
	 */
	@Test
	def void testNoUmlChange() {
		compareChanges(umlModel, umlCheckpoint)
	}

	/**
	 * Tests the comparison of two states with no changes for the pcm mockup model.
	 */
	@Test
	def void testNoPcmChange() {
		compareChanges(umlModel, umlCheckpoint)
	}
	
	/**
	 * Tests invalid input: null instead of state resources.
	 */
	@Test
	def void testNullResources() {
		val resourceSet = new ResourceSetImpl
		val resolver = createUuidGeneratorAndResolver(resourceSet)
		val Resource nullResource = null
		val change = strategyToTest.getChangeSequences(nullResource, nullResource, resolver)
		assertTrue(change.EChanges.empty, "Composite change contains children!")
	}

	/**
	 * Tests invalid input: null instead of UuidGeneratorAndResolver.
	 */
	@Test
	def void testNullResolver() {
		assertThrows(IllegalArgumentException) [
			strategyToTest.getChangeSequences(umlCheckpoint, umlCheckpoint, null)
		]
	}
}
