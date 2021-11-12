package tools.vitruv.framework.tests.vsum.views

import allElementTypes.Root
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tools.vitruv.framework.tests.vsum.VirtualModelTest
import tools.vitruv.framework.vsum.views.BasicViewType

import static com.google.common.base.Preconditions.checkNotNull
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import org.junit.jupiter.api.Disabled

class ViewTypeTest extends VirtualModelTest { // TODO TS: This currently re-runs tests from superclass
    val String ROOT_ID = "RootId1"
    val String ROOT_ID_2 = "RootId2"

    @Disabled // TODO TS: adapt test case for changed viewtype interface
    @Test
    @DisplayName("Test basic view type and selector functionality")
    def void testBasicViewType() {
        // Create test model with two resources and a root element each:
        val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
        val resourceSet = new ResourceSetImpl().withGlobalFactories
        resourceSet.createResource(createTestModelResourceUri("")) => [
            contents += aet.Root => [
                id = ROOT_ID
            ]
        ]
        resourceSet.createResource(createTestModelResourceUri("")) => [
            contents += aet.Root => [
                id = ROOT_ID_2
            ]
        ]

        // Create view type, select first element, and create view:
        val viewType = checkNotNull(new BasicViewType("test view type", virtualModel), "Cannot create view type!")
        val selector = checkNotNull(viewType.createSelector, "Cannot create selector!")
        selector.setSelected(0, true)
        val view = checkNotNull(selector.createView, "Cannot create view from selector!")

        // Check view content:
        assertEquals(1, view.rootObjects.size)
        val root = view.rootObjects.get(0) as Root
        assertEquals(ROOT_ID, root.id)
    }
}
