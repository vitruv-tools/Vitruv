package tools.vitruv.dsls.reactions.tests.versioning

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import org.junit.Test
import tools.vitruv.framework.change.copy.ChangeCopyFactory
import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
import tools.vitruv.framework.vsum.InternalModelRepository
import tools.vitruv.framework.vsum.InternalTestVirtualModel
import tools.vitruv.framework.util.datatypes.VURI

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class ReapplyTest extends SourceTargetRecorderTest {
	static val newTestSourceModelName = "EachTestModelSource2"
	static val newTestTargetModelName = "EachTestModelTarget2"

	@Test
	def void testReapply() {
		// Paths and VURIs
		val targetVURI = TEST_TARGET_MODEL_NAME.calculateVURI
		val sourceVURI = TEST_SOURCE_MODEL_NAME.calculateVURI

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		assertThat(rootElement.eContents.length, is(0))
		assertThat(rootElement.nonRootObjectContainerHelper, equalTo(null))
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		assertThat(rootElement.eContents.length, is(1))
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(1))

		val newSourceVURI = newTestSourceModelName.calculateVURI
		val newRoot = AllElementTypesFactory::eINSTANCE.createRoot
		newRoot.id = newTestSourceModelName
		newTestSourceModelName.projectModelPath.createAndSynchronizeModel(newRoot)

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(4))

		val changeMatches = stRecorder.getChangeMatches(sourceVURI)
		assertThat(changeMatches.length, is(4))
		val originalChanges = changeMatches.map[originalChange]
		assertThat(originalChanges.length, is(4))
		val pair = new Pair(sourceVURI.EMFUri.toString, newSourceVURI.EMFUri.toString)
		val eChangeCopier = ChangeCopyFactory::instance.createEChangeCopier(#[pair])
		val copiedChanges = originalChanges.filter[it instanceof EMFModelChangeImpl].map [
			it as EMFModelChangeImpl
		].map[eChangeCopier.copyEMFModelChangeToSingleChange(it, newSourceVURI)].toList
		assertThat(copiedChanges.length, is(4))

		virtualModel.propagateChange(copiedChanges.get(0))
		assertThatNonRootObjectContainerHasRightId

		for (i : 0 ..< 3) {
			virtualModel.propagateChange(copiedChanges.get(i + 1))
			assertThatNonRootObjectHasBeenInsertedInContainerAndRightId(i, true)
		}
		newSourceVURI.saveSecondSourceModel
	}

	@Test
	def void testReapplyAsList() {
		// Paths and VURIs
		val targetVURI = TEST_TARGET_MODEL_NAME.calculateVURI
		val sourceVURI = TEST_SOURCE_MODEL_NAME.calculateVURI

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		assertThat(rootElement.eContents.length, is(0))
		assertThat(rootElement.nonRootObjectContainerHelper, equalTo(null))
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		assertThat(rootElement.eContents.length, is(1))
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(1))

		val newSourceVURI = newTestSourceModelName.calculateVURI
		val newRoot = AllElementTypesFactory::eINSTANCE.createRoot
		newRoot.id = newTestSourceModelName
		newTestSourceModelName.projectModelPath.createAndSynchronizeModel(newRoot)

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(4))

		val changeMatches = stRecorder.getChangeMatches(sourceVURI)
		assertThat(changeMatches.length, is(4))
		val originalChanges = changeMatches.map[originalChange]
		assertThat(originalChanges.length, is(4))
		val pair = new Pair(sourceVURI.EMFUri.toString, newSourceVURI.EMFUri.toString)
		val eChangeCopier = ChangeCopyFactory::instance.createEChangeCopier(#[pair])
		val copiedChanges = originalChanges.filter[it instanceof EMFModelChangeImpl].map [
			it as EMFModelChangeImpl
		].map[eChangeCopier.copyEMFModelChangeToList(it, newSourceVURI)].flatten.toList
		assertThat(copiedChanges.length, is(8))

		virtualModel.propagateChange(copiedChanges.get(0))
		assertThatNonRootObjectContainerIsCreated
		virtualModel.propagateChange(copiedChanges.get(1))
		assertThatNonRootObjectContainerHasRightId

		for (i : 0 ..< 3) {
			virtualModel.propagateChange(copiedChanges.get(2 + i * 2))
			assertThatNonRootObjectHasBeenInsertedInContainer(i)
			virtualModel.propagateChange(copiedChanges.get(3 + i * 2))
			assertThatNonRootObjectHasBeenInsertedInContainerAndRightId(i)
		}
		newSourceVURI.saveSecondSourceModel
	}

	private def saveSecondSourceModel(VURI vuri) {
		val modelInstance = virtualModel.getModelInstance(vuri)
		val resource = modelInstance.resource
		resource.modified = true
		virtualModel.save
	}

	private def assertThatNonRootObjectContainerIsCreated() {
		assertThat("Container has not been created", sourceRootIterator.exists [
			null !== nonRootObjectContainerHelper && nonRootObjectContainerName != nonRootObjectContainerHelper.id
		], is(true))
	}

	private def assertThatNonRootObjectContainerHasRightId() {
		assertThat(sourceRootIterator.exists [
			nonRootObjectContainerName == nonRootObjectContainerHelper.id &&
				nonRootObjectContainerHelper.eContents.size === 0
		], is(true))
	}

	private def assertThatNonRootObjectHasBeenInsertedInContainer(int numberOfInsertedElement) {
		assertThat(sourceRootIterator.filter [
			nonRootObjectContainerName == nonRootObjectContainerHelper.id
		].map[nonRootObjectContainerHelper].filter[nonRootObjectsContainment.size === numberOfInsertedElement + 1].map [
			nonRootObjectsContainment.get(numberOfInsertedElement)
		].exists [
			id != NON_CONTAINMENT_NON_ROOT_IDS.get(numberOfInsertedElement)
		], is(true))
	}

	private def assertThatNonRootObjectHasBeenInsertedInContainerAndRightId(int numberOfInsertedElement) {
		assertThatNonRootObjectHasBeenInsertedInContainerAndRightId(numberOfInsertedElement, false)
	}

	private def assertThatNonRootObjectHasBeenInsertedInContainerAndRightId(int numberOfInsertedElement,
		boolean testTarget) {
		val x = [ Iterable<Root> iter |
			iter.filter [
				nonRootObjectContainerName == nonRootObjectContainerHelper.id
			].map[nonRootObjectContainerHelper].filter[nonRootObjectsContainment.size === numberOfInsertedElement + 1].
				map [
					nonRootObjectsContainment.get(numberOfInsertedElement)
				].exists [
					id == NON_CONTAINMENT_NON_ROOT_IDS.get(numberOfInsertedElement)
				]
		]
		assertThat(x.apply(sourceRootIterator), is(true))
		if (testTarget)
			assertThat(x.apply(targetRootIterator), is(true))
	}

	private def getResourceSet() {
		val internalModel = virtualModel as InternalTestVirtualModel
		val internalModelRepository = internalModel.modelRepository as InternalModelRepository
		val resourceSet = internalModelRepository.resourceSet
		return resourceSet
	}

	private def getSourceRootIterator() {
		newTestSourceModelName.rootIterator
	}

	private def getTargetRootIterator() {
		newTestTargetModelName.rootIterator
	}

	private def getRootIterator(String name) {
		resourceSet.resources.filter[URI.toString.contains(name)].map[contents].flatten.filter [
			it instanceof Root
		].map[it as Root].filter [
			id == newTestSourceModelName
		]
	}
}
