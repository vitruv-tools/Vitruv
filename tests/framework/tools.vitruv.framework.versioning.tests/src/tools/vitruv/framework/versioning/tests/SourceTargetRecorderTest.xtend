package tools.vitruv.framework.versioning.tests

import org.apache.log4j.Level
import org.apache.log4j.Logger

import org.junit.Test

import allElementTypes.Root

import tools.vitruv.framework.tests.util.TestUtil
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.common.EChangeSerializer
import tools.vitruv.framework.vsum.InternalTestVersioningVirtualModel
import tools.vitruv.framework.vsum.VersioningVirtualModel

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.notNullValue

import static org.hamcrest.collection.IsCollectionWithSize.hasSize

import static org.junit.Assert.assertThat

class SourceTargetRecorderTest extends AbstractVersioningTest {
	// Static extensions.
	static extension EChangeSerializer = EChangeSerializer::instance
	static extension Logger = Logger::getLogger(SourceTargetRecorderTest)

	static protected val nonRootObjectContainerName = "NonRootObjectContainer"
	protected VURI sourceVURI

	override setup() {
		super.setup
		level = Level::DEBUG
		sourceVURI = VURI::getInstance(rootElement.eResource)
	}

	override cleanup() {
		super.cleanup
	}

	override unresolveChanges() { true }

	@Test
	def void testRecordOriginalAndCorrespondentChanges() {

		// Create container and synchronize
		val container = createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges
		assertThat((virtualModel as InternalTestVersioningVirtualModel).getUnresolvedPropagatedChanges(sourceVURI),
			hasSize(2))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach [
			createAndAddNonRoot(container)
			rootElement.saveAndSynchronizeChanges
			assertModelsEqual
		]
		assertThat((virtualModel as InternalTestVersioningVirtualModel).getUnresolvedPropagatedChanges(sourceVURI),
			hasSize(5))
		assertThat((virtualModel as InternalTestVersioningVirtualModel).getUnresolvedPropagatedChanges(sourceVURI).
			forall[sourceVURI == originalChange.URI], is(true))
	}

	@Test
	def void testRecordOriginalAndCorrespondentChangesSingleSaveAndSynchronize() {
		// Create container and synchronize
		val container = createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		assertThat((virtualModel as InternalTestVersioningVirtualModel).getUnresolvedPropagatedChanges(sourceVURI),
			hasSize(2))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat((virtualModel as InternalTestVersioningVirtualModel).getUnresolvedPropagatedChanges(sourceVURI),
			hasSize(5))
		assertThat((virtualModel as InternalTestVersioningVirtualModel).getUnresolvedPropagatedChanges(sourceVURI).
			forall[sourceVURI == originalChange.URI], is(true))
	}

	@Test
	def void echangesShouldBeUnresolved() {
		// Create container and synchronize
		val container = createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		assertThat((virtualModel as InternalTestVersioningVirtualModel).getUnresolvedPropagatedChanges(sourceVURI),
			hasSize(2))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat((virtualModel as InternalTestVersioningVirtualModel).getUnresolvedPropagatedChanges(sourceVURI),
			hasSize(5))
		(virtualModel as InternalTestVersioningVirtualModel).getUnresolvedPropagatedChanges(sourceVURI).forEach [
			assertThat(originalChange.EChanges.forall[!resolved], is(true))
		]

	}

	@Test
	def void testSerializeChangeMatches() {
		val container = createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges
		NON_CONTAINMENT_NON_ROOT_IDS.forEach [
			createAndAddNonRoot(container)
			rootElement.saveAndSynchronizeChanges
		]

		val propagatedChanges = virtualModel.allUnresolvedPropagatedChanges
		assertThat(propagatedChanges, hasSize(5))
		val serializedChanges = propagatedChanges.map[serialize]

		val deserializedChanges = serializedChanges.map[deserialize]
		deserializedChanges.forEach[debug(it)]
		val newVirtualModel = TestUtil::createVirtualModel("newVMname", true, vitruvDomains,
			createChangePropagationSpecifications, userInteractor) as VersioningVirtualModel

		deserializedChanges.map[originalChange].forEach[newVirtualModel.propagateChange(it)]
		val modelInstance = newVirtualModel.getModelInstance(sourceVURI)
		val newRoot = modelInstance.resource.contents.get(0) as Root
		modelInstance.resource.save(#{})
		assertThat(newRoot.id, equalTo(TEST_SOURCE_MODEL_NAME))
		val newContainer = newRoot.nonRootObjectContainerHelper
		assertThat(newContainer.id, equalTo(nonRootObjectContainerName))
		assertThat(newContainer.nonRootObjectsContainment, hasSize(NON_CONTAINMENT_NON_ROOT_IDS.size))

		NON_CONTAINMENT_NON_ROOT_IDS.forEach [ currentId |
			val nonRoot = newContainer.nonRootObjectsContainment.findFirst[id == currentId]
			assertThat(nonRoot, notNullValue)
		]

	}

	@Test
	def void testSerializeChangeMatchesOneString() {
		val container = createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges
		NON_CONTAINMENT_NON_ROOT_IDS.forEach [
			createAndAddNonRoot(container)
			rootElement.saveAndSynchronizeChanges
		]

		val propagatedChanges = virtualModel.allUnresolvedPropagatedChanges
		assertThat(propagatedChanges, hasSize(5))
		val serializedChanges = propagatedChanges.serializeAll
		debug(serializedChanges)
		val deserializedChanges = serializedChanges.deserializeAll
		val newVirtualModel = TestUtil::createVirtualModel("newVMname", true, vitruvDomains,
			createChangePropagationSpecifications, userInteractor) as VersioningVirtualModel

		deserializedChanges.map[originalChange].forEach[newVirtualModel.propagateChange(it)]
		val modelInstance = newVirtualModel.getModelInstance(sourceVURI)
		val newRoot = modelInstance.resource.contents.get(0) as Root
		modelInstance.resource.save(#{})
		assertThat(newRoot.id, equalTo(TEST_SOURCE_MODEL_NAME))
		val newContainer = newRoot.nonRootObjectContainerHelper
		assertThat(newContainer.id, equalTo(nonRootObjectContainerName))
		assertThat(newContainer.nonRootObjectsContainment, hasSize(NON_CONTAINMENT_NON_ROOT_IDS.size))

		NON_CONTAINMENT_NON_ROOT_IDS.forEach [ currentId |
			val nonRoot = newContainer.nonRootObjectsContainment.findFirst[id == currentId]
			assertThat(nonRoot, notNullValue)
		]
	}

}
