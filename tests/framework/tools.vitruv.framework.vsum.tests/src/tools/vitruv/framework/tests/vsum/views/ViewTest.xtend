package tools.vitruv.framework.tests.vsum.views

import allElementTypes.NonRoot
import allElementTypes.Root
import java.util.Collection
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tools.vitruv.framework.tests.vsum.VirtualModelTest
import tools.vitruv.framework.vsum.views.BasicModelView
import tools.vitruv.framework.vsum.views.View

import static com.google.common.base.Preconditions.checkNotNull
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertIterableEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

class ViewTest extends VirtualModelTest { // TODO TS: This currently re-runs tests from superclass
    val String NON_ROOT_ID = "NonRootId"
    val String ROOT_ID = "RootId"

    @Test
    @DisplayName("Test basic view functionality")
    def void testBasicModelView() {
        // Create test model:
        val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
        val resourceSet = new ResourceSetImpl().withGlobalFactories
        val modelResource = resourceSet.createResource(createTestModelResourceUri("")) => [
            contents += aet.Root => [
                id = ROOT_ID
            ]
        ]

        // Create view and check initial state:
        var View basicView = checkNotNull(new BasicModelView(#[modelResource], virtualModel), "Cannot create view!");
        assertNotNull(basicView.rootObjects)
        assertEquals(basicView.rootObjects.oneAndOnly, basicView.rootObjects(Root).oneAndOnly)
        assertEquals(ROOT_ID, basicView.rootObjects(Root).oneAndOnly.id)
        assertFalse(basicView.hasVSUMChanged)

        // Modify model
        val modelRoot = modelResource.contents.oneAndOnly as Root
        val NonRoot element = aet.NonRoot
        element.id = NON_ROOT_ID
        modelRoot.multiValuedContainmentEReference.add(element)

        // Assert VSUM changed but view not modified:
        assertTrue(basicView.hasVSUMChanged)
        assertIterableEquals(#[], basicView.rootObjects(Root).oneAndOnly.multiValuedContainmentEReference)

        // Update view and assert view was updated correctly
        basicView.update
        assertFalse(basicView.hasVSUMChanged)
        val viewRoot = basicView.rootObjects(Root).oneAndOnly
        assertEquals(NON_ROOT_ID, viewRoot.multiValuedContainmentEReference.oneAndOnly.id)
    }

    def private <T> T getOneAndOnly(Collection<T> collection) {
        assertEquals(1, collection.size)
        return collection.get(0)
    }
}
