package tools.vitruv.framework.tests.vsum.views

import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tools.vitruv.framework.tests.vsum.VirtualModelTest
import tools.vitruv.framework.vsum.views.BasicModelView
import tools.vitruv.framework.vsum.views.View

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertIterableEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

import static extension com.google.common.base.Preconditions.checkNotNull
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.tests.vsum.VirtualModelTestUtil.recordChanges

class ViewTest extends VirtualModelTest { // TODO TS: This currently re-runs tests from superclass
    val String NON_ROOT_ID = "NonRootId"
    val String ROOT_ID = "RootId"

    @Test
    @DisplayName("Test basic view functionality")
    def void testBasicModelView() { // TODO TS split into multiple test, cover commit mechanism
    // Create test model:
        val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
        val resourceSet = new ResourceSetImpl().withGlobalFactories
        virtualModel.propagateChange(resourceSet.recordChanges [
            resourceSet.createResource(createTestModelResourceUri("")) => [
                contents += aet.Root => [
                    id = ROOT_ID
                ]
            ]
        ])
        val modelResource = resourceSet.resources.claimOne.checkNotNull

        // Create view and check initial state:
        var View basicView = checkNotNull(new BasicModelView(virtualModel.resourceSet.resources, virtualModel),
            "Cannot create view!");
        assertNotNull(basicView.rootObjects)
        assertEquals(basicView.rootObjects.claimOne.checkNotNull, basicView.rootObjects(Root).claimOne.checkNotNull)
        assertEquals(ROOT_ID, basicView.rootObjects(Root).claimOne.checkNotNull.id)
        assertFalse(basicView.hasVSUMChanged)

        // Modify model
        virtualModel.propagateChange(recordChanges(resourceSet, [
            val modelRoot = modelResource.contents.claimOne.checkNotNull as Root
            val NonRoot element = aet.NonRoot
            element.id = NON_ROOT_ID
            modelRoot.multiValuedContainmentEReference.add(element)
        ]))

        // Assert VSUM changed but view not modified:
        assertTrue(basicView.hasVSUMChanged)
        assertIterableEquals(#[], basicView.rootObjects(Root).claimOne.checkNotNull.multiValuedContainmentEReference)

        // Update view and assert view was updated correctly
        basicView.update
        assertFalse(basicView.hasVSUMChanged)
        val viewRoot = basicView.rootObjects(Root).claimOne.checkNotNull
        assertEquals(NON_ROOT_ID, viewRoot.multiValuedContainmentEReference.claimOne.checkNotNull.id)
    }

}
