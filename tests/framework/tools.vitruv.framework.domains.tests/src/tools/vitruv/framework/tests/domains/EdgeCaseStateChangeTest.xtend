package tools.vitruv.framework.tests.domains

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.Test
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl

import static org.junit.Assert.*

class EdgeCaseStateChangeTest extends StateChangePropagationTest {

	/**
	 * Tests the comparison of two states with no changes for botch mock-up models.
	 */
	@Test
	def void testNoChange() {
		val change = changeFromComparisonWithCheckpoint
		assertTrue("Composite change contains children!", change.EChanges.empty)
		assertEquals(change, umlModelInstance.recordedChanges)
		assertEquals(change, pcmModelInstance.recordedChanges)
	}

	/**
	 * Tests invalid input: null instead of states.
	 */
	@Test
	def void testNullResources() {
		val resourceSet = new ResourceSetImpl
		val resolver = new UuidGeneratorAndResolverImpl(resourceSet, false)
		val change = strategyToTest.getChangeSequences(null, null, resolver)
		assertTrue("Composite change contains children!", change.EChanges.empty)
	}

	/**
	 * Tests invalid input: null instead of UuidGeneratorAndResolver.
	 */
	@Test(expected=IllegalArgumentException)
	def void testNullResolver() {
		strategyToTest.getChangeSequences(null, null, null)
	}
}
