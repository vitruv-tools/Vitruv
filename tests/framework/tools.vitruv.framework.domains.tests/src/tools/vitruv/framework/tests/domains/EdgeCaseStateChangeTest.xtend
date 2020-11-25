package tools.vitruv.framework.tests.domains

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue
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
	 * Tests invalid input: null instead of state EObjects.
	 */
	@Test
	def void testNullEObject() {
		val resourceSet = new ResourceSetImpl
		val resolver = new UuidGeneratorAndResolverImpl(resourceSet, false)
		val EObject nullEObject = null
		val change = strategyToTest.getChangeSequences(nullEObject, nullEObject, resolver)
		assertTrue(change.EChanges.empty, "Composite change contains children!")
	}

	/**
	 * Tests invalid input: null instead of state resources.
	 */
	@Test
	def void testNullResources() {
		val resourceSet = new ResourceSetImpl
		val resolver = new UuidGeneratorAndResolverImpl(resourceSet, false)
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
