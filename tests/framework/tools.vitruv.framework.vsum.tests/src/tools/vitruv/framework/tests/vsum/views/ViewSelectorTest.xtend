package tools.vitruv.framework.tests.vsum.views

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import pcm_mockup.Pcm_mockupFactory
import tools.vitruv.framework.vsum.views.selection.BasicViewSelector
import tools.vitruv.framework.vsum.views.selection.ViewSelector

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertIterableEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class ViewSelectorTest {

    @Test
    @DisplayName("Test basic selector functionality")
    def void testBasicViewSelector() {
        // Create view selector:
        val repository1 = Pcm_mockupFactory::eINSTANCE.createRepository
        val repository2 = Pcm_mockupFactory::eINSTANCE.createRepository
        var ViewSelector selector = new BasicViewSelector(null, #[repository1, repository2])

        // Check initial state:
        assertEquals(#{repository1, repository2}, selector.elements)
        assertEquals(2, selector.elements.size)
        assertEquals(0, selector.selectedElements.size)
        assertFalse(selector.valid)

        // Select the repository
        selector.setSelected(repository1, true)

        // Check state after selection:
        assertTrue(selector.valid)
        assertTrue(selector.isSelected(repository1))
        assertFalse(selector.isSelected(repository2))
        assertEquals(1, selector.selectedElements.size)
        assertTrue(selector.selectedElements.contains(repository1))
    }
}