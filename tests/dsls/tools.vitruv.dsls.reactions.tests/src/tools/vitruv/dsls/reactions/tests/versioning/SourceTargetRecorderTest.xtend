package tools.vitruv.dsls.reactions.tests.versioning

import allElementTypes.Root
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.junit.Test
import tools.vitruv.dsls.reactions.tests.AbstractVersioningTest
import tools.vitruv.framework.tests.util.TestUtil
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.extensions.EChangeSerializer
import tools.vitruv.framework.versioning.extensions.VirtualModelExtension
import tools.vitruv.framework.vsum.VersioningVirtualModel

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.junit.Assert.assertThat

class SourceTargetRecorderTest extends AbstractVersioningTest {
	static extension EChangeSerializer = EChangeSerializer::instance
	static extension Logger = Logger::getLogger(SourceTargetRecorderTest)
	static protected extension VirtualModelExtension = VirtualModelExtension::instance

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
		assertThat(virtualModel.getChangeMatches(sourceVURI).length, is(2))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach [
			createAndAddNonRoot(container)
			rootElement.saveAndSynchronizeChanges
			assertModelsEqual
		]
		assertThat(virtualModel.getChangeMatches(sourceVURI).length, is(5))
		assertThat(virtualModel.getChangeMatches(sourceVURI).forall[sourceVURI == originalChange.URI], is(true))
	}

	@Test
	def void testRecordOriginalAndCorrespondentChangesSingleSaveAndSynchronize() {
		// Create container and synchronize 
		val container = createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		assertThat(virtualModel.getChangeMatches(sourceVURI).length, is(2))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat(virtualModel.getChangeMatches(sourceVURI).length, is(5))
		assertThat(virtualModel.getChangeMatches(sourceVURI).forall[sourceVURI == originalChange.URI], is(true))
	}

	@Test
	def void echangesShouldBeUnresolved() {
		// Create container and synchronize 
		val container = createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		assertThat(virtualModel.getChangeMatches(sourceVURI).length, is(2))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat(virtualModel.getChangeMatches(sourceVURI).length, is(5))
		virtualModel.getChangeMatches(sourceVURI).forEach [
			assertThat(originalChange.EChanges.forall[!resolved], is(true))
		]

	}

	@Test
	def void testSezializeChangeMatches() {
		val container = createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		val propagatedChanges = virtualModel.allUnresolvedPropagatedChanges
		assertThat(propagatedChanges, hasSize(2))
		val serializedChanges = propagatedChanges.map[serialize].toList

		val deserializedChanges = serializedChanges.map[deserialize].toList
		deserializedChanges.forEach[debug(it)]
		val newVirtualModel = TestUtil::createVirtualModel("newVMname", true, vitruvDomains,
			createChangePropagationSpecifications, userInteractor) as VersioningVirtualModel

		val originalChanges = newArrayList
		deserializedChanges.map[originalChange].forEach[originalChanges += it]
		originalChanges.forEach [
			newVirtualModel.propagateChange(it)
		]
		val modelInstance = newVirtualModel.getModelInstance(sourceVURI)
		val newRoot = modelInstance.resource.contents.get(0) as Root
		modelInstance.resource.save(#{})
		assertThat(newRoot.id, equalTo(TEST_SOURCE_MODEL_NAME))
		val newContainer = newRoot.nonRootObjectContainerHelper
		assertThat(newContainer.id, equalTo(nonRootObjectContainerName))
	}

}
