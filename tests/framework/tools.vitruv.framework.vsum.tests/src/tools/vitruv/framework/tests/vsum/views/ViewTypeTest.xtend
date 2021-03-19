package tools.vitruv.framework.tests.vsum.views

import org.junit.jupiter.api.Test
import pcm_mockup.Pcm_mockupFactory
import tools.vitruv.framework.tests.vsum.VsumTest
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.views.BasicViewType

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import pcm_mockup.Repository

class ViewTypeTest extends VsumTest {

    @Test
    def void testBasicViewType() {
        // Create test model:
        var vsum = createDefaultVirtualModel
        var vuri = VURI.getInstance(alternativePcmInstanceURI)
        var model = vsum.getModelInstance(vuri)
        val repository1 = Pcm_mockupFactory::eINSTANCE.createRepository
        vsum.persistRootElement(vuri, repository1)
        val repository2 = Pcm_mockupFactory::eINSTANCE.createRepository
        vsum.persistRootElement(vuri, repository2)
        vsum.save

        // Create view and check initial state:
        val viewType = new BasicViewType("test view type", model)
        val selector = viewType.createSelector
        selector.setSelected(0, true)
        val view = selector.createView

        // Check view content:
        assertEquals(1, view.resource.contents.size)
        val viewRepo = view.resource.contents.get(0) as Repository
        assertEquals(repository1.id, viewRepo.id)
    }

}
