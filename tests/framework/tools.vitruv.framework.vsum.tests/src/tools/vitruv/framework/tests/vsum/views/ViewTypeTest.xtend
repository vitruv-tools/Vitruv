package tools.vitruv.framework.tests.vsum.views

import allElementTypes.Root
import java.nio.file.Path
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.TestProject
import tools.vitruv.testutils.TestProjectManager

import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.framework.tests.vsum.VirtualModelTestUtil.*
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

import static extension com.google.common.base.Preconditions.checkNotNull
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.tests.vsum.VirtualModelTestUtil.createTestModelResourceUri
import static extension tools.vitruv.framework.tests.vsum.VirtualModelTestUtil.recordChanges
import org.junit.jupiter.api.Disabled
import tools.vitruv.framework.vsum.views.ViewTypeFactory

@ExtendWith(TestProjectManager)
class ViewTypeTest {
    static val String ROOT_ID = "RootId1"
    static val String ROOT_ID_2 = "RootId2"
    var Path projectFolder

    @BeforeEach
    def void initializeProjectFolder(@TestProject Path projectFolder) {
        this.projectFolder = projectFolder
    }

    @Disabled // TODO TS: Due to changed update functionality this is currently no longer supported.
    @Test
    @DisplayName("Test basic view type and selector functionality")
    def void testBasicViewType() {
        // Create test model with two resources and a root element each:
        val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
        val resourceSet = new ResourceSetImpl().withGlobalFactories
        virtualModel.propagateChange(resourceSet.recordChanges [
            resourceSet.createResource(projectFolder.createTestModelResourceUri("1")) => [
                contents += aet.Root => [
                    id = ROOT_ID
                ]
            ]
            resourceSet.createResource(projectFolder.createTestModelResourceUri("2")) => [
                contents += aet.Root => [
                    id = ROOT_ID_2
                ]
            ]
        ])
        assertEquals(resourceSet.resources.size, virtualModel.resourceSet.resources.size)

        // Create view type, select first element, and create view:
        val viewType = checkNotNull(ViewTypeFactory.createBasicViewType("test view type", virtualModel), "Cannot create view type!")
        val selector = checkNotNull(viewType.createSelector(virtualModel), "Cannot create selector!")
        selector.setSelected(selector.elements.get(0), true)
        val view = checkNotNull(selector.createView, "Cannot create view from selector!")

        // Check view content, only first element should be provided:
        assertEquals(1, view.rootObjects.size)
        val root = view.rootObjects.claimOne.checkNotNull as Root
        assertEquals(ROOT_ID, root.id)
    }
}
