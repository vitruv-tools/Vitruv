package tools.vitruv.framework.tests.vsum

import allElementTypes.Root
import java.nio.file.Path
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.recording.ChangeRecorder
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.propagation.ResourceAccess
import tools.vitruv.framework.propagation.impl.AbstractChangePropagationSpecification
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.testutils.TestProject
import tools.vitruv.testutils.TestProjectManager
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider

import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.getCorrespondingEObjects

@ExtendWith(TestProjectManager)
class VirtualModelTest {
	protected var Path projectFolder

	@BeforeEach
	def void initializeProjectFolder(@TestProject Path projectFolder) {
		this.projectFolder = projectFolder
	}

	protected static def createAndLoadTestVirtualModel(Path folder) {
		return new VirtualModelBuilder().withStorageFolder(folder).withDomain(
			new AllElementTypesDomainProvider().domain).withUserInteractor(
			UserInteractionFactory.instance.createUserInteractor(
				UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null))).buildAndInitialize()
	}

	private static def createAndLoadTestVirtualModelWithConsistencyPreservation(Path folder) {
		val aetDomain = new AllElementTypesDomainProvider().domain
		return new VirtualModelBuilder().withStorageFolder(folder).withDomain(aetDomain).
			withChangePropagationSpecification(new RedundancyChangePropagationSpecification(aetDomain, aetDomain)).
			withUserInteractor(
				UserInteractionFactory.instance.createUserInteractor(
					UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null))).
			buildAndInitialize()
	}

	protected def createTestModelResourceUri(String suffix) {
		URI.createFileURI(projectFolder.resolve("root" + suffix + ".allElementTypes").toString)
	}

	@Test
	@DisplayName("propagate a simple change into a virtual model")
	def void propagateIntoVirtualModel() {
		val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val monitoredResource = resourceSet.createResource(createTestModelResourceUri("")) => [
			contents += aet.Root => [
				id = 'root'
			]
		]
		val recordedChange = changeRecorder.endRecording
		virtualModel.propagateChange(recordedChange)
		val vsumModel = virtualModel.getModelInstance(createTestModelResourceUri(""))
		assertThat(vsumModel.resource, containsModelOf(monitoredResource))
	}

	@Test
	@DisplayName("propagate a simple change into a virtual model and preserve consistency")
	def void propagateIntoVirtualModelWithConsistency() {
		val virtualModel = createAndLoadTestVirtualModelWithConsistencyPreservation(projectFolder.resolve("vsum"))
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val monitoredResource = resourceSet.createResource(createTestModelResourceUri("")) => [
			contents += aet.Root => [
				id = 'root'
			]
		]
		val recordedChange = changeRecorder.endRecording
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
		val virtualModel = createAndLoadTestVirtualModelWithConsistencyPreservation(projectFolder.resolve("vsum"))
		val resourceSet = new ResourceSetImpl().withGlobalFactories
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
		val recordedChange = changeRecorder.endRecording
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
		val virtualModel = createAndLoadTestVirtualModelWithConsistencyPreservation(projectFolder.resolve("vsum"))
		val resourceSet = new ResourceSetImpl().withGlobalFactories
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
		val recordedChange = changeRecorder.endRecording
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
		val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val monitoredResource = resourceSet.createResource(createTestModelResourceUri("")) => [
			contents += aet.Root => [
				id = 'root'
			]
		]
		val recordedChange = changeRecorder.endRecording
		virtualModel.propagateChange(recordedChange)
		val reloadedResource = new ResourceSetImpl().withGlobalFactories.getResource(createTestModelResourceUri(""),
			true)
		assertThat(reloadedResource, containsModelOf(monitoredResource))
	}

	@Test
	@DisplayName("reload a virtual model to which a simple change was propagated")
	def void reloadVirtualModel() {
		val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val root = aet.Root
		val monitoredResource = resourceSet.createResource(createTestModelResourceUri("")) => [
			contents += root => [
				id = 'root'
			]
		]
		val recordedChange = changeRecorder.endRecording
		virtualModel.propagateChange(recordedChange)
		val originalModel = virtualModel.getModelInstance(createTestModelResourceUri(""))
		val reloadedVirtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
		val reloadedModel = reloadedVirtualModel.getModelInstance(createTestModelResourceUri(""))
		assertThat(reloadedModel.resource, containsModelOf(monitoredResource))
		assertNotEquals(originalModel, reloadedModel)
		// Propagate another change to reloaded virtual model to ensure that everything is loaded correctly
		changeRecorder.beginRecording
		root.singleValuedEAttribute = 1
		val secondRecordedChange = changeRecorder.endRecording
		val propagatedChange = reloadedVirtualModel.propagateChange(secondRecordedChange)
		assertEquals(1, propagatedChange.size)
	}

	@Test
	@DisplayName("reload a virtual model with consistency preservation to which a simple change was propagated")
	def void reloadVirtualModelWithConsistency() {
		val virtualModel = createAndLoadTestVirtualModelWithConsistencyPreservation(projectFolder.resolve("vsum"))
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val changeRecorder = new ChangeRecorder(resourceSet)
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val root = aet.Root
		val monitoredResource = resourceSet.createResource(createTestModelResourceUri("")) => [
			contents += root => [
				id = 'root'
			]
		]
		val recordedChange = changeRecorder.endRecording
		virtualModel.propagateChange(recordedChange)
		val originalModel = virtualModel.getModelInstance(createTestModelResourceUri(""))
		val reloadedVirtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
		val reloadedModel = reloadedVirtualModel.getModelInstance(createTestModelResourceUri(""))
		assertThat(reloadedModel.resource, containsModelOf(monitoredResource))
		assertNotEquals(originalModel, reloadedModel)
		val reloadedTargetModel = reloadedVirtualModel.getModelInstance(
			RedundancyChangePropagationSpecification.getTargetResourceUri(createTestModelResourceUri("")))
		assertThat(reloadedTargetModel.resource, containsModelOf(monitoredResource))
		assertEquals(1, reloadedVirtualModel.correspondenceModel.getCorrespondingEObjects(reloadedModel.resource.contents.get(0)).size)
	}

	@Test
	@DisplayName("move element such that corresponding element is moved from one resource to another and back")
	def void moveCorrespondingToOtherResourceAndBack() {
		val virtualModel = createAndLoadTestVirtualModelWithConsistencyPreservation(projectFolder.resolve("vsum"))
		val resourceSet = new ResourceSetImpl().withGlobalFactories
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
		virtualModel.propagateChange(changeRecorder.endRecording)
		changeRecorder.beginRecording
		val testIntermediateUri = createTestModelResourceUri("intermediate")
		resourceSet.createResource(testIntermediateUri) => [
			contents += root
		]
		virtualModel.propagateChange(changeRecorder.endRecording)
		// There must not be the old and the old corresponding model
		assertNull(virtualModel.getModelInstance(testUri))
		assertNull(virtualModel.getModelInstance(RedundancyChangePropagationSpecification.getTargetResourceUri(testUri)))
		changeRecorder.beginRecording
		monitoredResource => [
			contents += root
		]
		virtualModel.propagateChange(changeRecorder.endRecording)
		assertNull(virtualModel.getModelInstance(testIntermediateUri))
		assertNull(virtualModel.getModelInstance(RedundancyChangePropagationSpecification.getTargetResourceUri(testIntermediateUri)))
	}

	static class RedundancyChangePropagationSpecification extends AbstractChangePropagationSpecification {
		static def getTargetResourceUri(URI sourceUri) {
			URI.createFileURI(sourceUri.trimFileExtension.toFileString + "Copy." + sourceUri.fileExtension)
		}

		new(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
			super(sourceDomain, targetDomain)
		}

		override doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
			if (change instanceof InsertRootEObject) {
				return change.newValue instanceof Root
			}
			return false
		}

		override propagateChange(EChange change, CorrespondenceModel correspondenceModel,
			extension ResourceAccess resourceAccess) {
			if (!doesHandleChange(change, correspondenceModel)) {
				return
			}
			val typedChange = change as InsertRootEObject<Root>
			val insertedRoot = typedChange.newValue
			// If there is a corresponding element, reuse it, otherwise creat one
			val correspondingRoots = correspondenceModel.getCorrespondingEObjects(insertedRoot).filter(Root)
			val correspondingRoot = if (correspondingRoots.size == 1) {
				correspondingRoots.get(0)
			} else {
				val newRoot = aet.Root => [
					id = insertedRoot.id
				]
				correspondenceModel.createAndAddCorrespondence(List.of(insertedRoot), List.of(newRoot))
				newRoot
			}

			if (insertedRoot.eContainer !== null) {
				val correspondingObjects = correspondenceModel.getCorrespondingEObjects(insertedRoot.eContainer, Root)
				assertEquals(1, correspondingObjects.size)
				correspondingObjects.get(0).recursiveRoot = correspondingRoot
			}
			val resourceURI = typedChange.resource.URI
			persistAsRoot(correspondingRoot, resourceURI.targetResourceUri)
		}
	}

}
