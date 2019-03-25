package tools.vitruv.framework.tests.domains

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.Test
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl

import static org.junit.Assert.*

class EdgeCaseStateChangeTest extends StateChangePropagationTest {

	/**
	 * Tests the comparison of two states with no changes for the uml mockup model.
	 */
	@Test
	def void testNoUmlChange() {
		val change = umlModelInstance.changeFromComparisonWithCheckpoint
		assertTrue("Composite change contains children!", change.EChanges.empty)
		assertEquals(change, umlModelInstance.recordedChanges)
	}

	/**
	 * Tests the comparison of two states with no changes for the pcm mockup model.
	 */
	@Test
	def void testNoPcmChange() {
		val change = pcmModelInstance.changeFromComparisonWithCheckpoint
		assertTrue("Composite change contains children!", change.EChanges.empty)
		assertEquals(change, pcmModelInstance.recordedChanges)
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
		assertTrue("Composite change contains children!", change.EChanges.empty)
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
		assertTrue("Composite change contains children!", change.EChanges.empty)
	}

	/**
	 * Tests invalid input: null instead of UuidGeneratorAndResolver.
	 */
	@Test(expected=IllegalArgumentException)
	def void testNullResolver() {
		strategyToTest.getChangeSequences(umlCheckpoint, umlCheckpoint, null)
	}
}
