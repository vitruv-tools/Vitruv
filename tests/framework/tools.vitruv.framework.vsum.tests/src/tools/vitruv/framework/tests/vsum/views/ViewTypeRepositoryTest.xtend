package tools.vitruv.framework.tests.vsum.views

import java.nio.file.Path
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.framework.vsum.views.ViewTypeRepository
import tools.vitruv.testutils.TestProject
import tools.vitruv.testutils.TestProjectManager

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.framework.tests.vsum.VirtualModelTestUtil.*
import tools.vitruv.framework.vsum.views.ViewTypeFactory

@ExtendWith(TestProjectManager)
class ViewTypeRepositoryTest {
    static val NAME = "test view type"
    var Path projectFolder

    @BeforeEach
    def void initializeProjectFolder(@TestProject Path projectFolder) {
        this.projectFolder = projectFolder
    }

    @Test
    @DisplayName("Register and retrieve test viewtype")
    def void testViewTypeRepository() {
        val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
        val repository = new ViewTypeRepository
        val viewType = ViewTypeFactory.createBasicViewType(NAME, virtualModel)
        assertTrue(repository.viewTypes.empty)
        repository.register(viewType)
        assertEquals(viewType, repository.findViewType(NAME))
        assertTrue(repository.viewTypes.contains(viewType))
    }
}
