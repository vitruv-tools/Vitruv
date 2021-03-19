package tools.vitruv.framework.tests.vsum.views

import org.junit.jupiter.api.Test
import tools.vitruv.framework.tests.vsum.VsumTest
import tools.vitruv.framework.vsum.views.BasicViewType
import tools.vitruv.framework.vsum.views.ViewTypeRepository

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class ViewTypeRepositoryTest extends VsumTest {

    @Test
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
