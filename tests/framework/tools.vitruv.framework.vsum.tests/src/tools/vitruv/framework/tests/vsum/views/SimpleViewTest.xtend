package tools.vitruv.framework.tests.vsum.views

import org.junit.jupiter.api.Test
import pcm_mockup.Pcm_mockupFactory
import pcm_mockup.Repository
import tools.vitruv.framework.tests.vsum.VsumTest
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.views.BasicModelView
import tools.vitruv.framework.vsum.views.View
import tools.vitruv.framework.vsum.views.selection.BasicViewSelector
import tools.vitruv.framework.vsum.views.selection.ViewSelector

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertIterableEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

class SimpleViewTest extends VsumTest {
    static val REPO_ID = "repository"
    static val COMP_ID = "component"

    @Test
    def void testBasicViewSelector() {
        // Create view selector:
        val repository = Pcm_mockupFactory::eINSTANCE.createRepository
        val component = Pcm_mockupFactory::eINSTANCE.createComponent
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

    @Test
    def void testBasicModelView() { // TODO TS check if resource content is valid
    // Create test model:
        var vsum = createDefaultVirtualModel
        var vuri = VURI.getInstance(alternativePcmInstanceURI)
        var model = vsum.getModelInstance(vuri)

        // Create view and check initial state:
        var View basicView = new BasicModelView(model)
        assertNotNull(basicView.resource)
        assertFalse(basicView.hasVSUMChanged)

        // Modify model
        val repository = Pcm_mockupFactory::eINSTANCE.createRepository
        repository.id = REPO_ID
        vsum.persistRootElement(vuri, repository)
        assertTrue(basicView.hasVSUMChanged)

        val component = Pcm_mockupFactory::eINSTANCE.createComponent
        component.id = COMP_ID
        repository.components.add(component)
        vsum.save
        assertTrue(basicView.hasVSUMChanged)
        assertTrue(basicView.resource.contents.empty)

        // Update view
        basicView.update
        assertFalse(basicView.hasVSUMChanged)
        assertEquals(1, basicView.resource.contents.size)
        val repo = basicView.resource.contents.get(0) as Repository
        assertEquals(REPO_ID, repo.id)
        assertEquals(COMP_ID, repo.components.get(0).id)
    }

}
