package tools.vitruv.framework.tests.vsum.views

import org.junit.jupiter.api.Test
import tools.vitruv.framework.vsum.views.BasicViewType
import tools.vitruv.framework.vsum.views.ViewTypeRepository

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import tools.vitruv.framework.tests.vsum.VirtualModelTest
import org.junit.jupiter.api.DisplayName

class ViewTypeRepositoryTest extends VirtualModelTest { // TODO TS: This currently re-runs tests from superclass

    @Test
    @DisplayName("Register and retrieve test viewtype")
    def void testViewTypeRepository() {
        val name = "test view type"
        val repository = new ViewTypeRepository
        val viewType = new BasicViewType(name, null)
        assertTrue(repository.viewTypes.empty)
        repository.register(viewType)
        assertEquals(viewType, repository.findViewType(name))
        assertTrue(repository.viewTypes.contains(viewType))
    }
}
