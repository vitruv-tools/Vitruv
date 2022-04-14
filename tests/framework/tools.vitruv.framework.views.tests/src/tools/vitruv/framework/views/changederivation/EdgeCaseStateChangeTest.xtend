package tools.vitruv.framework.views.changederivation

import org.eclipse.emf.ecore.resource.Resource
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import static org.junit.jupiter.api.Assertions.assertThrows

class EdgeCaseStateChangeTest extends StateChangePropagationTest {

	/**
	 * Tests the comparison of two states with no changes for the uml mockup model.
	 */
	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testNoUmlChange(StateBasedChangeResolutionStrategy strategyToTest, String name) {
		compareChanges(umlModel, umlCheckpoint, strategyToTest)
	}

	/**
	 * Tests the comparison of two states with no changes for the pcm mockup model.
	 */
	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testNoPcmChange(StateBasedChangeResolutionStrategy strategyToTest, String name) {
		compareChanges(umlModel, umlCheckpoint, strategyToTest)
	}

	/**
	 * Tests invalid input: null instead of state resources.
	 */
	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testNullResources(StateBasedChangeResolutionStrategy strategyToTest, String name) {
		val Resource nullResource = null
		assertThrows(IllegalArgumentException)[strategyToTest.getChangeSequenceBetween(nullResource, nullResource)]
	}

}
