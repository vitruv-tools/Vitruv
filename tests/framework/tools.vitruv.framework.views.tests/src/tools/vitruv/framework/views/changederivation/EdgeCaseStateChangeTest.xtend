package tools.vitruv.framework.views.changederivation

import org.eclipse.emf.ecore.resource.Resource
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

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
		val Resource nullResource = null
		assertThrows(IllegalArgumentException)[strategyToTest.getChangeSequenceBetween(nullResource, nullResource)]
	}

}
