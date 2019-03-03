package tools.vitruv.framework.tests.vsum

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.domains.DefaultStateChangePropagationStrategy
import tools.vitruv.framework.domains.StateChangePropagationStrategy
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl

import static org.junit.Assert.*

class StateChangePropagationStrategyTest {
	var StateChangePropagationStrategy strategyToTest;

	@Before
	def void setup() {
		strategyToTest = new DefaultStateChangePropagationStrategy
	}

	@Test
	def void testNullResources() {
		val resolver = createNewUuidResolver
		val change = strategyToTest.getChangeSequences(null, null, resolver)
		assertNotNull(change)
		assertTrue("Composite change contains children!", change.EChanges.empty)
	}

	@Test(expected=IllegalArgumentException)
	def void testNullResolver() {
		strategyToTest.getChangeSequences(null, null, null)
		fail("No exception was thrown!")
	}

	def private UuidGeneratorAndResolver createNewUuidResolver() {
		val resourceSet = new ResourceSetImpl
		new UuidGeneratorAndResolverImpl(resourceSet, false)
	}

}
