package tools.vitruv.framework.tests.vsum.views

import org.junit.jupiter.api.Test
import pcm_mockup.Pcm_mockupFactory
import tools.vitruv.framework.tests.vsum.VsumTest
import tools.vitruv.framework.vsum.views.selection.BasicViewSelector
import tools.vitruv.framework.vsum.views.selection.ViewSelector

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertIterableEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class SimpleViewTest extends VsumTest { // TODO TS right now this does not need to be a VsumTest
    @Test
    def void testBasicViewSelector() {
        // Create view selector:
        val repository = Pcm_mockupFactory::eINSTANCE.createRepository()
        val component = Pcm_mockupFactory::eINSTANCE.createComponent()
        var ViewSelector selector = new BasicViewSelector(null, #[repository, component])

        // Check initial state:
        assertIterableEquals(#[repository, component], selector.elements)
        assertEquals(2, selector.size)
        assertEquals(0, selector.selectedElements.size)
        assertFalse(selector.valid)

        // Select the repository
        val index = selector.getIndexOf(repository)
        selector.setSelected(index, true)

        // Check state after selection:
        assertTrue(selector.valid)
        assertTrue(selector.isSelected(0))
        assertFalse(selector.isSelected(1))
        assertEquals(1, selector.selectedElements.size)
        assertTrue(selector.selectedElements.contains(repository))
    }
}
