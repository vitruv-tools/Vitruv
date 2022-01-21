package tools.vitruv.framework.vsum.views

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.framework.vsum.views.ViewTypeRepository

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import tools.vitruv.framework.vsum.views.ViewTypeFactory
import tools.vitruv.testutils.TestLogging
import tools.vitruv.testutils.RegisterMetamodelsInStandalone

@ExtendWith(TestLogging, RegisterMetamodelsInStandalone)
class ViewTypeRepositoryTest {
    static val NAME = "test view type"

    @Test
    @DisplayName("Register and retrieve test viewtype")
    def void testViewTypeRepository() {
        val repository = new ViewTypeRepository
        val viewType = ViewTypeFactory.createBasicViewType(NAME)
        assertTrue(repository.viewTypes.empty)
        repository.register(viewType)
        assertEquals(viewType, repository.findViewType(NAME))
        assertTrue(repository.viewTypes.contains(viewType))
    }
}
