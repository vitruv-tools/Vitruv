package tools.vitruv.framework.vsum

import allElementTypes.Root
import java.nio.file.Path
import java.util.HashSet
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.change.atomic.eobject.CreateEObject
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.change.atomic.root.InsertRootEObject
import tools.vitruv.change.atomic.uuid.UuidResolver
import tools.vitruv.change.composite.description.VitruviusChangeResolver
import tools.vitruv.change.composite.recording.ChangeRecorder
import tools.vitruv.framework.views.View
import tools.vitruv.framework.views.ViewTypeFactory
import tools.vitruv.framework.vsum.VirtualModelTestUtil.RedundancyChangePropagationSpecification
import tools.vitruv.testutils.TestProject
import tools.vitruv.testutils.TestProjectManager

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

import static extension com.google.common.base.Preconditions.checkNotNull
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getFirstRootEObject
import static extension tools.vitruv.framework.vsum.VirtualModelTestUtil.*

@ExtendWith(TestProjectManager)
class VirtualModelTest {
	static val String NON_ROOT_ID = "NonRootId"
	static val String ROOT_ID = "RootId"

	var Path projectFolder

	@BeforeEach
	def void initializeProjectFolder(@TestProject Path projectFolder) {
		this.projectFolder = projectFolder
	}

	@Test
	@DisplayName("propagate a simple change into a virtual model")
	def void propagateIntoVirtualModel() {
		val virtualModel = createAndLoadTestVirtualModel(pathToVirtualModelProjectFolder)
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val uuidResolver = UuidResolver.create(resourceSet)
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val monitoredResource = resourceSet.createResource(createTestModelResourceUri("")) => [
			contents += aet.Root => [
				id = 'root'
			]
		]
		val recordedChange = changeRecorder.endRecording(uuidResolver)
		virtualModel.propagateChange(recordedChange)
		val vsumModel = virtualModel.getModelInstance(createTestModelResourceUri(""))
		assertThat(vsumModel.resource, containsModelOf(monitoredResource))
	}

	@Test
	@DisplayName("propagate a simple change into a virtual model and preserve consistency")
	def void propagateIntoVirtualModelWithConsistency() {
		val virtualModel = createAndLoadTestVirtualModelWithConsistencyPreservation(pathToVirtualModelProjectFolder)
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val uuidResolver = UuidResolver.create(resourceSet)
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val monitoredResource = resourceSet.createResource(createTestModelResourceUri("")) => [
			contents += aet.Root => [
				id = 'root'
			]
		]
		val recordedChange = changeRecorder.endRecording(uuidResolver)
		virtualModel.propagateChange(recordedChange)
		val sorceModel = virtualModel.getModelInstance(createTestModelResourceUri(""))
		val targetModel = virtualModel.getModelInstance(
			RedundancyChangePropagationSpecification.getTargetResourceUri(createTestModelResourceUri("")))
		assertThat(targetModel.resource, containsModelOf(monitoredResource))
		assertEquals(1,
			virtualModel.correspondenceModel.getCorrespondingEObjects(sorceModel.resource.contents.get(0)).size)
	}

	@Test
	@DisplayName("persist element as resource root also contained in other persisted element")
	def void singleChangeForRootElementInMultipleResource() {
		val virtualModel = createAndLoadTestVirtualModelWithConsistencyPreservation(pathToVirtualModelProjectFolder)
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val uuidResolver = UuidResolver.create(resourceSet)
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val containedRoot = aet.Root
		resourceSet.createResource(createTestModelResourceUri("")) => [
			contents += aet.Root => [
				id = 'root'
				recursiveRoot = containedRoot => [
					id = 'containedRoot'
				]
			]
		]
		resourceSet.createResource(createTestModelResourceUri("Contained")) => [
			contents += containedRoot
		]
		val recordedChange = changeRecorder.endRecording(uuidResolver)
		val propagatedChanges = virtualModel.propagateChange(recordedChange)
		val consequentialChanges = propagatedChanges.map[consequentialChanges.EChanges].flatten
		assertEquals(2, consequentialChanges.filter(CreateEObject).size)
		assertEquals(2, consequentialChanges.filter(InsertRootEObject).size)
		assertEquals(1, consequentialChanges.filter(ReplaceSingleValuedEReference).size)
		assertEquals(2, consequentialChanges.filter(ReplaceSingleValuedEAttribute).size)
		assertEquals(7, consequentialChanges.size)
	}

	@Test
	@DisplayName("add element to containment of element persisted in two resources")
	def void singleChangeForElementContainedInRootElementInMultipleResource() {
		val virtualModel = createAndLoadTestVirtualModelWithConsistencyPreservation(pathToVirtualModelProjectFolder)
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val uuidResolver = UuidResolver.create(resourceSet)
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val containedRoot = aet.Root
		val containedInContainedRoot = aet.Root
		resourceSet.createResource(createTestModelResourceUri("")) => [
			contents += aet.Root => [
				id = 'root'
				recursiveRoot = containedRoot => [
					id = 'containedRoot'
					recursiveRoot = containedInContainedRoot => [
						id = 'containedInContained'
					]
				]
			]
		]
		resourceSet.createResource(createTestModelResourceUri("Contained")) => [
			contents += containedRoot
		]
		resourceSet.createResource(createTestModelResourceUri("ContainedInContained")) => [
			contents += containedInContainedRoot
		]
		val recordedChange = changeRecorder.endRecording(uuidResolver)
		val propagatedChanges = virtualModel.propagateChange(recordedChange)
		val consequentialChanges = propagatedChanges.map[consequentialChanges.EChanges].flatten
		assertEquals(3, consequentialChanges.filter(CreateEObject).size)
		assertEquals(3, consequentialChanges.filter(InsertRootEObject).size)
		assertEquals(2, consequentialChanges.filter(ReplaceSingleValuedEReference).size)
		assertEquals(3, consequentialChanges.filter(ReplaceSingleValuedEAttribute).size)
		assertEquals(11, consequentialChanges.size)
	}

	@Test
	@DisplayName("load resource that should have been saved after propagating a change into a virtual model")
	def void savedVirtualModel() {
		val virtualModel = createAndLoadTestVirtualModel(pathToVirtualModelProjectFolder)
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val uuidResolver = UuidResolver.create(resourceSet)
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val monitoredResource = resourceSet.createResource(createTestModelResourceUri("")) => [
			contents += aet.Root => [
				id = 'root'
			]
		]
		val recordedChange = changeRecorder.endRecording(uuidResolver)
		virtualModel.propagateChange(recordedChange)
		val reloadedResource = new ResourceSetImpl().withGlobalFactories.getResource(createTestModelResourceUri(""),
			true)
		assertThat(reloadedResource, containsModelOf(monitoredResource))
	}

	@Test
	@DisplayName("reload a virtual model to which a simple change was propagated")
	def void reloadVirtualModel() {
		val virtualModel = createAndLoadTestVirtualModel(pathToVirtualModelProjectFolder)
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val uuidResolver = UuidResolver.create(resourceSet)
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val root = aet.Root
		val monitoredResource = resourceSet.createResource(createTestModelResourceUri("")) => [
			contents += root => [
				id = 'root'
			]
		]
		val recordedChange = changeRecorder.endRecording(uuidResolver)
		virtualModel.propagateChange(recordedChange)
		val originalModel = virtualModel.getModelInstance(createTestModelResourceUri(""))
		val reloadedVirtualModel = createAndLoadTestVirtualModel(pathToVirtualModelProjectFolder)
		val reloadedModel = reloadedVirtualModel.getModelInstance(createTestModelResourceUri(""))
		assertThat(reloadedModel.resource, containsModelOf(monitoredResource))
		assertNotEquals(originalModel, reloadedModel)
		// Propagate another change to reloaded virtual model to ensure that everything is loaded correctly
		changeRecorder.beginRecording
		root.singleValuedEAttribute = 1
		val secondRecordedChange = changeRecorder.endRecording(uuidResolver)
		val propagatedChange = reloadedVirtualModel.propagateChange(secondRecordedChange)
		assertEquals(1, propagatedChange.size)
	}

	@Test
	@DisplayName("reload a virtual model with consistency preservation to which a simple change was propagated")
	def void reloadVirtualModelWithConsistency() {
		val virtualModel = createAndLoadTestVirtualModelWithConsistencyPreservation(pathToVirtualModelProjectFolder)
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val uuidResolver = UuidResolver.create(resourceSet)
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val root = aet.Root
		val monitoredResource = resourceSet.createResource(createTestModelResourceUri("")) => [
			contents += root => [
				id = 'root'
			]
		]
		val recordedChange = changeRecorder.endRecording(uuidResolver)
		virtualModel.propagateChange(recordedChange)
		val originalModel = virtualModel.getModelInstance(createTestModelResourceUri(""))
		val reloadedVirtualModel = createAndLoadTestVirtualModel(pathToVirtualModelProjectFolder)
		val reloadedModel = reloadedVirtualModel.getModelInstance(createTestModelResourceUri(""))
		assertThat(reloadedModel.resource, containsModelOf(monitoredResource))
		assertNotEquals(originalModel, reloadedModel)
		val reloadedTargetModel = reloadedVirtualModel.getModelInstance(
			RedundancyChangePropagationSpecification.getTargetResourceUri(createTestModelResourceUri("")))
		assertThat(reloadedTargetModel.resource, containsModelOf(monitoredResource))
		assertEquals(1,
			reloadedVirtualModel.correspondenceModel.getCorrespondingEObjects(reloadedModel.resource.contents.get(0)).
				size)
	}

	@Test
	@DisplayName("move element such that corresponding element is moved from one resource to another and back")
	def void moveCorrespondingToOtherResourceAndBack() {
		val virtualModel = createAndLoadTestVirtualModelWithConsistencyPreservation(pathToVirtualModelProjectFolder)
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val uuidResolver = UuidResolver.create(resourceSet)
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val root = aet.Root
		val testUri = createTestModelResourceUri("")
		val monitoredResource = resourceSet.createResource(testUri) => [
			contents += root => [
				id = 'root'
			]
		]
		virtualModel.propagateChange(changeRecorder.endRecording(uuidResolver))
		changeRecorder.beginRecording
		val testIntermediateUri = createTestModelResourceUri("intermediate")
		resourceSet.createResource(testIntermediateUri) => [
			contents += root
		]
		virtualModel.propagateChange(changeRecorder.endRecording(uuidResolver))
		// There must not be the old and the old corresponding model
		assertNull(virtualModel.getModelInstance(testUri))
		assertNull(
			virtualModel.getModelInstance(RedundancyChangePropagationSpecification.getTargetResourceUri(testUri)))
		changeRecorder.beginRecording
		monitoredResource => [
			contents += root
		]
		virtualModel.propagateChange(changeRecorder.endRecording(uuidResolver))
		assertNull(virtualModel.getModelInstance(testIntermediateUri))
		assertNull(
			virtualModel.getModelInstance(
				RedundancyChangePropagationSpecification.getTargetResourceUri(testIntermediateUri)))
	}

	@Test
	@DisplayName("create a view for a virtual model")
	def void createView() {
		val virtualModel = createAndLoadTestVirtualModel(pathToVirtualModelProjectFolder)
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val uuidResolver = UuidResolver.create(resourceSet)
		virtualModel.createAndPropagateRoot(resourceSet, uuidResolver, ROOT_ID)
		val testView = virtualModel.createTestView
		// Check initial state:
		assertThat(new HashSet(testView.rootObjects), not(is(emptySet())))
		assertEquals(testView.rootObjects.claimOne, testView.getRootObjects(Root).claimOne)
		assertThat(testView.getRootObjects(Root).claimOne.id, is(ROOT_ID))
		assertThat("view source must not have been changed", !testView.outdated)
		assertThat("view must not have been modified", !testView.isModified)
	}

	@Test
	@DisplayName("update view after a change in the virtual model")
	def void updateView() {
		val virtualModel = createAndLoadTestVirtualModel(pathToVirtualModelProjectFolder)
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val uuidResolver = UuidResolver.create(resourceSet)
		virtualModel.createAndPropagateRoot(resourceSet, uuidResolver, ROOT_ID)
		val testView = virtualModel.createTestView

		// Modify model
		virtualModel.propagateChange(resourceSet.recordChanges(uuidResolver) [
			val resource = resourceSet.resources.claimOne
			resource.firstRootEObject as Root => [
				multiValuedContainmentEReference += aet.NonRoot => [
					id = NON_ROOT_ID
				]
			]
		])

		// Assert VSUM changed but view not modified:
		assertThat("view source must have been changed", testView.outdated)
		assertThat("view must not have been modified", !testView.isModified)
		assertThat(testView.getRootObjects(Root).claimOne.multiValuedContainmentEReference, is(emptyList()))

		// Update view and assert view was updated correctly
		testView.update()
		assertThat("view source must not have been changed", !testView.outdated)
		assertThat("view must not have been modified", !testView.isModified)
		val viewRoot = testView.getRootObjects(Root).claimOne
		assertThat(viewRoot.multiValuedContainmentEReference.claimOne.id, is(NON_ROOT_ID))
	}

	@Test
	@DisplayName("change view and commit changes")
	def void commitView() {
		val virtualModel = createAndLoadTestVirtualModel(pathToVirtualModelProjectFolder)
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val uuidResolver = UuidResolver.create(resourceSet)
		virtualModel.createAndPropagateRoot(resourceSet, uuidResolver, ROOT_ID)
		val testView = virtualModel.createTestView.withChangeRecordingTrait

		// Modify view:
		assertThat("view must not have been modified", !testView.isModified)
		testView.getRootObjects(Root).claimOne => [
			multiValuedContainmentEReference += aet.NonRoot => [
				id = NON_ROOT_ID
			]
		]

		// Assert view modified but VSUM not changed:
		assertThat("view source must not have been changed", !testView.outdated)
		assertThat("view must have been modified", testView.isModified)

		// Commit changes and assert VSUM was updated correctly
		testView.commitChanges()
		assertThat("view source must have been changed", testView.outdated)
		assertThat("view must not have been modified", !testView.isModified)

		testView.update();
		assertThat("view source must not have been changed", !testView.outdated)
		assertThat("view must not have been modified", !testView.isModified)
		
		val reopenedViewRoot = virtualModel.createTestView.getRootObjects(Root).claimOne
		assertThat(reopenedViewRoot.multiValuedContainmentEReference.claimOne.id, is(NON_ROOT_ID))
	}

	private def createTestModelResourceUri(String suffix) {
		projectFolder.createTestModelResourceUri(suffix)
	}

	private def getPathToVirtualModelProjectFolder() {
		projectFolder.resolve("vsum")
	}
	
	private def endRecording(ChangeRecorder changeRecorder, UuidResolver uuidResolver) {
		val change = changeRecorder.endRecording
		val changeResolver = VitruviusChangeResolver.forUuids(uuidResolver)
		return changeResolver.assignIds(change)
	}

	def private createAndPropagateRoot(VirtualModel virtualModel, ResourceSet resourceSet, UuidResolver uuidResolver, String rootId) {
		virtualModel.propagateChange(resourceSet.recordChanges(uuidResolver) [
			resourceSet.createResource(projectFolder.createTestModelResourceUri("")) => [
				contents += aet.Root => [
					id = rootId
				]
			]
		])
	}

	def private static View createTestView(VirtualModel virtualModel) {
		val viewType = ViewTypeFactory.createIdentityMappingViewType("").checkNotNull("cannot create view type")
		val selector = virtualModel.createSelector(viewType).checkNotNull("cannot create selector")
		selector.selectableElements.forEach[selector.setSelected(it, true)]
		return selector.createView.checkNotNull("Cannot create view from selector!")
	}

}
