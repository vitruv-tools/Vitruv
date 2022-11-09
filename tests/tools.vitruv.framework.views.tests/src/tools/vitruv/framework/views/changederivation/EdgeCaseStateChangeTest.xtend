package tools.vitruv.framework.views.changederivation

import org.eclipse.emf.ecore.resource.Resource
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import static org.junit.jupiter.api.Assertions.assertThrows
import tools.vitruv.change.atomic.uuid.UuidResolver

class EdgeCaseStateChangeTest extends StateChangePropagationTest {

	/**
	 * Tests the comparison of two states with no changes for the uml mockup model.
	 */
	@ParameterizedTest()
	@MethodSource("strategiesToTest")
	def void testNoUmlChange(StateBasedChangeResolutionStrategy strategyToTest) {
		compareChanges(umlModel, umlCheckpoint, strategyToTest)
	}

	/**
	 * Tests the comparison of two states with no changes for the pcm mockup model.
	 */
	@ParameterizedTest()
	@MethodSource("strategiesToTest")
	def void testNoPcmChange(StateBasedChangeResolutionStrategy strategyToTest) {
		compareChanges(umlModel, umlCheckpoint, strategyToTest)
	}

	/**
	 * Tests invalid input: null instead of state resources.
	 */
	@ParameterizedTest()
	@MethodSource("strategiesToTest")
	def void testNullResources(StateBasedChangeResolutionStrategy strategyToTest) {
		val Resource nullResource = null
		assertThrows(IllegalArgumentException)[strategyToTest.getChangeSequenceForCreated(nullResource)]
		assertThrows(IllegalArgumentException)[strategyToTest.getChangeSequenceBetween(nullResource, nullResource, uuidResolver)]
		assertThrows(IllegalArgumentException)[strategyToTest.getChangeSequenceForDeleted(nullResource, uuidResolver)]
	}
	
	/**
	 * Tests invalid input: null instead of uuid resolver.
	 */
	@ParameterizedTest()
	@MethodSource("strategiesToTest")
	def void testNullUuidResolver(StateBasedChangeResolutionStrategy strategyToTest) {
		val UuidResolver nullUuidResolver = null
		assertThrows(IllegalArgumentException)[strategyToTest.getChangeSequenceBetween(umlModel, umlCheckpoint, nullUuidResolver)]
		assertThrows(IllegalArgumentException)[strategyToTest.getChangeSequenceForDeleted(umlModel, nullUuidResolver)]
	}

}
