package tools.vitruv.framework.tests.vsum.views

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import pcm_mockup.Pcm_mockupFactory

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertIterableEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import tools.vitruv.framework.vsum.views.ViewSelector
import tools.vitruv.framework.vsum.views.selectors.BasicViewElementSelector

class ViewSelectorTest {

	@Test
	@DisplayName("Test basic selector functionality")
	def void testBasicViewSelector() {
		// Create view selector:
		val repository1 = Pcm_mockupFactory::eINSTANCE.createRepository
		val repository2 = Pcm_mockupFactory::eINSTANCE.createRepository
		var ViewSelector selector = new BasicViewElementSelector(null, null, #[repository1, repository2])

		// Check initial state:
		assertEquals(#{repository1, repository2}, selector.selectableElements)
		assertEquals(2, selector.selectableElements.size)
		assertFalse(selector.isViewObjectSelected(repository1))
		assertFalse(selector.isViewObjectSelected(repository2))
		assertTrue(selector.valid)

		// Select the repository
		selector.setSelected(repository1, true)

		// Check state after selection:
		assertTrue(selector.valid)
		assertTrue(selector.isSelected(repository1))
		assertFalse(selector.isSelected(repository2))
		assertTrue(selector.isViewObjectSelected(repository1))
		assertFalse(selector.isViewObjectSelected(repository2))
	}
}
