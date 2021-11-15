package tools.vitruv.framework.tests.vsum.views

import allElementTypes.Root
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tools.vitruv.framework.tests.vsum.VirtualModelTest
import tools.vitruv.framework.vsum.views.BasicViewType

import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

import static extension com.google.common.base.Preconditions.checkNotNull
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.tests.vsum.VirtualModelTestUtil.recordChanges

class ViewTypeTest extends VirtualModelTest { // TODO TS: This currently re-runs tests from superclass
    val String ROOT_ID = "RootId1"
    val String ROOT_ID_2 = "RootId2"

    @Test
    @DisplayName("Test basic view type and selector functionality")
    def void testBasicViewType() {
        // Create test model with two resources and a root element each:
        val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
        val resourceSet = new ResourceSetImpl().withGlobalFactories
        virtualModel.propagateChange(resourceSet.recordChanges [
            resourceSet.createResource(createTestModelResourceUri("1")) => [
                contents += aet.Root => [
                    id = ROOT_ID
                ]
            ]
            resourceSet.createResource(createTestModelResourceUri("2")) => [
                contents += aet.Root => [
                    id = ROOT_ID_2
                ]
            ]
        ])
        assertEquals(resourceSet.resources.size, virtualModel.resourceSet.resources.size)

        // Create view type, select first element, and create view:
        val viewType = checkNotNull(new BasicViewType("test view type", virtualModel), "Cannot create view type!")
        val selector = checkNotNull(viewType.createSelector, "Cannot create selector!")
        selector.setSelected(0, true)
        val view = checkNotNull(selector.createView, "Cannot create view from selector!")

        // Check view content, only first element should be provided:
        assertEquals(1, view.rootObjects.size)
        val root = view.rootObjects.claimOne.checkNotNull as Root
        assertEquals(ROOT_ID, root.id)
    }
}
