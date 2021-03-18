package tools.vitruv.framework.tests.vsum.views

import org.junit.jupiter.api.Test
import tools.vitruv.framework.tests.vsum.VsumTest
import tools.vitruv.framework.vsum.views.ViewType
import tools.vitruv.framework.vsum.views.ViewTypeRepository
import tools.vitruv.framework.vsum.views.selection.ViewSelector

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

class ViewTypeRepositoryTest extends VsumTest {

    @Test
    def void testViewTypeRepository() {
        val repository = new ViewTypeRepository
        val viewType = new DummyViewType
        assertTrue(repository.viewTypes.empty)
        val id = repository.register(viewType)
        assertNotNull(id)
        assertEquals(viewType, repository.findViewType(id))
        assertTrue(repository.viewTypes.contains(viewType))

    }

    static class DummyViewType implements ViewType {

        override createSelector() {
            null
        }

        override createView(ViewSelector selector) {
            null
        }

    }
}
