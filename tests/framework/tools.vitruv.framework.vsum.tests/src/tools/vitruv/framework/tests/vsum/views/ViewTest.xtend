package tools.vitruv.framework.tests.vsum.views

import allElementTypes.NonRoot
import allElementTypes.Root
import java.nio.file.Path
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.vsum.views.BasicViewType
import tools.vitruv.framework.vsum.views.View
import tools.vitruv.testutils.TestProject
import tools.vitruv.testutils.TestProjectManager

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertIterableEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.framework.tests.vsum.VirtualModelTestUtil.createAndLoadTestVirtualModel
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

import static extension com.google.common.base.Preconditions.checkNotNull
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.tests.vsum.VirtualModelTestUtil.createTestModelResourceUri
import static extension tools.vitruv.framework.tests.vsum.VirtualModelTestUtil.recordChanges

@ExtendWith(TestProjectManager)
class ViewTest {
    static val String NON_ROOT_ID = "NonRootId"
    static val String ROOT_ID = "RootId"

    var Path projectFolder

    @BeforeEach
    def void initializeProjectFolder(@TestProject Path projectFolder) {
        this.projectFolder = projectFolder
    }

    @Test
    @DisplayName("Test view creation")
    def void testViewCreation() {
        // Create test model:
        val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
        val resourceSet = new ResourceSetImpl().withGlobalFactories
        createAndPropagateRoot(resourceSet, virtualModel)

        // Create view and check initial state:
        val testView = virtualModel.createTestView
        assertNotNull(testView.rootObjects)
        assertEquals(testView.rootObjects.claimOne.checkNotNull, testView.rootObjects(Root).claimOne.checkNotNull)
        assertEquals(ROOT_ID, testView.rootObjects(Root).claimOne.checkNotNull.id)
        assertFalse(testView.hasVSUMChanged)
        assertFalse(testView.isModified)
    }

    @Test
    @DisplayName("Test view update after model change")
    def void testViewUpdate() {
        // Create test model and view:
        val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
        val resourceSet = new ResourceSetImpl().withGlobalFactories
        createAndPropagateRoot(resourceSet, virtualModel)
        val testView = virtualModel.createTestView

        // Modify model
        virtualModel.propagateChange(recordChanges(resourceSet, [
            val resource = resourceSet.resources.claimOne.checkNotNull
            val modelRoot = resource.contents.claimOne.checkNotNull as Root
            val NonRoot element = aet.NonRoot
            element.id = NON_ROOT_ID
            modelRoot.multiValuedContainmentEReference.add(element)
        ]))

        // Assert VSUM changed but view not modified:
        assertTrue(testView.hasVSUMChanged)
        assertFalse(testView.isModified)
        assertIterableEquals(#[], testView.rootObjects(Root).claimOne.checkNotNull.multiValuedContainmentEReference)

        // Update view and assert view was updated correctly
        testView.update
        assertFalse(testView.hasVSUMChanged)
        assertFalse(testView.isModified)
        val viewRoot = testView.rootObjects(Root).claimOne.checkNotNull
        assertEquals(NON_ROOT_ID, viewRoot.multiValuedContainmentEReference.claimOne.checkNotNull.id)
    }

    @Test
    @DisplayName("Test view commit after view change")
    def void testViewCommit() {
        // Create test model and view:
        val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
        val resourceSet = new ResourceSetImpl().withGlobalFactories
        createAndPropagateRoot(resourceSet, virtualModel)
        val testView = virtualModel.createTestView
        assertFalse(testView.isModified)

        // Modify view:
        val viewRoot = testView.rootObjects(Root).claimOne.checkNotNull
        val NonRoot element = aet.NonRoot
        element.id = NON_ROOT_ID
        viewRoot.multiValuedContainmentEReference.add(element)

        // Assert view modified but VSUM not changed:
        assertTrue(testView.modified)
        assertFalse(testView.hasVSUMChanged)

        // Commit changes and assert VSUM was updated correctly
        val changes = testView.commitChanges.checkNotNull
        assertFalse(changes.empty)
        assertFalse(testView.modified)
        assertFalse(testView.hasVSUMChanged)
        val modelResource = virtualModel.resourceSet.resources.claimOne.checkNotNull
        val modelRoot = modelResource.contents.claimOne.checkNotNull as Root
        assertEquals(NON_ROOT_ID, modelRoot.multiValuedContainmentEReference.claimOne.checkNotNull.id)
    }

    def private createAndPropagateRoot(ResourceSet resourceSet, VirtualModel virtualModel) {
        virtualModel.propagateChange(resourceSet.recordChanges [
            resourceSet.createResource(projectFolder.createTestModelResourceUri("")) => [
                contents += aet.Root => [
                    id = ROOT_ID
                ]
            ]
        ])
    }

    def private View createTestView(VirtualModel virtualModel) {
        val viewType = new BasicViewType("", virtualModel).checkNotNull("Cannot create view type!")
        val selector = viewType.createSelector.checkNotNull("Cannot create selector!")
        return selector.createView.checkNotNull("Cannot create view from selector!")
    }

}
