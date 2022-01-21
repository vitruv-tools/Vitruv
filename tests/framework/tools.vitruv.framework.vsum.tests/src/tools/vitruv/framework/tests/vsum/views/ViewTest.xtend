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
import tools.vitruv.framework.vsum.views.View
import tools.vitruv.testutils.TestProject
import tools.vitruv.testutils.TestProjectManager

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertIterableEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.framework.tests.vsum.VirtualModelTestUtil.createAndLoadTestVirtualModel
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

import static extension com.google.common.base.Preconditions.checkNotNull
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.tests.vsum.VirtualModelTestUtil.createTestModelResourceUri
import static extension tools.vitruv.framework.tests.vsum.VirtualModelTestUtil.recordChanges
import tools.vitruv.framework.vsum.views.ViewTypeFactory

@ExtendWith(TestProjectManager)
class ViewTest {
	static val String NON_ROOT_ID = "NonRootId"
	static val String ROOT_ID = "RootId"

	var Path projectFolder
	var VirtualModel virtualModel
	var ResourceSet resourceSet
	var View testView

	@BeforeEach
	def void setup(@TestProject Path projectFolder) {
		this.projectFolder = projectFolder
		virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
		resourceSet = new ResourceSetImpl().withGlobalFactories
		createAndPropagateRoot(resourceSet, virtualModel)
		testView = virtualModel.createTestView
	}

	@Test
	@DisplayName("Test basic view creation")
	def void testViewCreation() {
		// Check initial state:
		assertNotNull(testView.getRootObjects)
		assertEquals(testView.getRootObjects.claimOne.checkNotNull, testView.getRootObjects(Root).claimOne.checkNotNull)
		assertEquals(ROOT_ID, testView.getRootObjects(Root).claimOne.checkNotNull.id)
		assertFalse(testView.haveViewSourcesChanged)
		assertFalse(testView.isModified)
	}

	@Test
	@DisplayName("Test view update after model change")
	def void testViewUpdate() {
		// Modify model
		virtualModel.propagateChange(recordChanges(resourceSet, [
			val resource = resourceSet.resources.claimOne.checkNotNull
			val modelRoot = resource.contents.claimOne.checkNotNull as Root
			val NonRoot element = aet.NonRoot
			element.id = NON_ROOT_ID
			modelRoot.multiValuedContainmentEReference.add(element)
		]))

		// Assert VSUM changed but view not modified:
		assertTrue(testView.haveViewSourcesChanged)
		assertFalse(testView.isModified)
		assertIterableEquals(#[], testView.getRootObjects(Root).claimOne.checkNotNull.multiValuedContainmentEReference)

		// Update view and assert view was updated correctly
		testView.update
		assertFalse(testView.haveViewSourcesChanged)
		assertFalse(testView.isModified)
		val viewRoot = testView.getRootObjects(Root).claimOne.checkNotNull
		assertEquals(NON_ROOT_ID, viewRoot.multiValuedContainmentEReference.claimOne.checkNotNull.id)
	}

	@Test
	@DisplayName("Test view commit after view change")
	def void testViewCommit() {
		// Modify view:
		assertFalse(testView.isModified)
		val viewRoot = testView.getRootObjects(Root).claimOne.checkNotNull
		val NonRoot element = aet.NonRoot
		element.id = NON_ROOT_ID
		viewRoot.multiValuedContainmentEReference.add(element)

		// Assert view modified but VSUM not changed:
		assertTrue(testView.modified)
		assertFalse(testView.haveViewSourcesChanged)

		// Commit changes and assert VSUM was updated correctly
		val changes = testView.commitChanges.checkNotNull
		assertFalse(changes.empty)
		assertFalse(testView.modified)
		assertFalse(testView.haveViewSourcesChanged)

		val reopenedViewRoot = virtualModel.createTestView.getRootObjects(Root).claimOne.checkNotNull
		assertEquals(NON_ROOT_ID, reopenedViewRoot.multiValuedContainmentEReference.claimOne.checkNotNull.id)
	}

	@Test
	@DisplayName("Test illegal update after view change")
	def void testDirtyViewUpdate() {
		// Modify view:
		assertFalse(testView.isModified)
		val viewRoot = testView.getRootObjects(Root).claimOne.checkNotNull
		val NonRoot element = aet.NonRoot
		element.id = NON_ROOT_ID
		viewRoot.multiValuedContainmentEReference.add(element)

		// Assert view modified but VSUM not changed:
		assertTrue(testView.modified)
		assertFalse(testView.haveViewSourcesChanged)

		// Assert that update not possible
		assertThrows(UnsupportedOperationException, [testView.update])
	}

	@Test
	@DisplayName("Test illegal call after view closed")
	def void testClosedViewMethodCall() {
		// Close view and check if view prevents illegal calls
		testView.close
		assertTrue(testView.isClosed)
		assertThrows(IllegalStateException, [testView.update])
		assertThrows(IllegalStateException, [testView.commitChanges])
		assertThrows(IllegalStateException, [testView.rootObjects])
		assertThrows(IllegalStateException, [testView.getRootObjects(Root)])
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

	def private static View createTestView(VirtualModel virtualModel) {
		val viewType = ViewTypeFactory.createBasicViewType("").checkNotNull("cannot create view type")
		val selector = virtualModel.createSelector(viewType).checkNotNull("cannot create selector")
		selector.selectableElements.forEach[selector.setSelected(it, true)]
		return selector.createView.checkNotNull("Cannot create view from selector!")
	}

}
