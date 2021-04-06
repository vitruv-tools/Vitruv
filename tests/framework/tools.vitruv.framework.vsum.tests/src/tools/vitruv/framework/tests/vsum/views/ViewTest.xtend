package tools.vitruv.framework.tests.vsum.views

import org.junit.jupiter.api.Test
import pcm_mockup.Pcm_mockupFactory
import pcm_mockup.Repository
import tools.vitruv.framework.vsum.views.BasicModelView
import tools.vitruv.framework.vsum.views.View

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import tools.vitruv.framework.tests.vsum.VirtualModelTest

class ViewTest extends VirtualModelTest {
    static val REPO_ID = "repository"
    static val COMP_ID = "component"

    @Test
    def void testBasicModelView() { // FIXME TS: Broken test case after sync with master.
//        // Create test model:
//        var vsum = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
//        var vuri = VURI.getInstance(alternativePcmInstanceURI)
//        var model = vsum.getModelInstance(vuri)
//
//        // Create view and check initial state:
//        var View basicView = new BasicModelView(model)
//        assertNotNull(basicView.resource)
//        assertFalse(basicView.hasVSUMChanged)
//
//        // Modify model
//        val repository = Pcm_mockupFactory::eINSTANCE.createRepository
//        repository.id = REPO_ID
//        vsum.persistRootElement(vuri, repository)
//        assertTrue(basicView.hasVSUMChanged)
//
//        val component = Pcm_mockupFactory::eINSTANCE.createComponent
//        component.id = COMP_ID
//        repository.components.add(component)
//        vsum.save
//        assertTrue(basicView.hasVSUMChanged)
//        assertTrue(basicView.resource.contents.empty)
//
//        // Update view
//        basicView.update
//        assertFalse(basicView.hasVSUMChanged)
//        assertEquals(1, basicView.resource.contents.size)
//        val repo = basicView.resource.contents.get(0) as Repository
//        assertEquals(REPO_ID, repo.id)
//        assertEquals(COMP_ID, repo.components.get(0).id)
    }

}
