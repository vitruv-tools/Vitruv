package tools.vitruv.framework.tests.vsum

import org.junit.jupiter.api.Test
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.testutils.TestProject
import java.nio.file.Path
import tools.vitruv.framework.change.recording.ChangeRecorder
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.util.datatypes.VURI
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import static org.hamcrest.MatcherAssert.assertThat
import tools.vitruv.testutils.TestProjectManager
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNotEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName

@ExtendWith(TestProjectManager)
class VirtualModelTest {
	var Path projectFolder

	@BeforeEach
	def void initializeProjectFolder(@TestProject Path projectFolder) {
		this.projectFolder = projectFolder
	}

	private static def createAndLoadTestVirtualModel(Path folder) {
		return new VirtualModelBuilder().withStorageFolder(folder).withDomain(
			new AllElementTypesDomainProvider().domain).withUserInteractor(
			UserInteractionFactory.instance.createUserInteractor(
				UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null))).buildAndInitialize()
	}

	@Test
	@DisplayName("propagate a simple change into a virtual model")
	def void propagateIntoVirtualModel() {
		val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val changeRecorder = new ChangeRecorder(createUuidGeneratorAndResolver(virtualModel.uuidResolver, resourceSet))
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val resourceUri = URI.createFileURI(projectFolder.resolve("root.allElementTypes").toString)
		val root = aet.Root
		resourceSet.createResource(resourceUri) => [
			contents += root
		]
		virtualModel.propagateChange(
			VitruviusChangeFactory.instance.createCompositeTransactionalChange(changeRecorder.endRecording))
		val vsumModel = virtualModel.getModelInstance(VURI.getInstance(resourceUri))
		assertNotNull(vsumModel.resource)
		assertFalse(vsumModel.resource.contents.empty)
		assertThat(vsumModel.resource.contents.get(0), equalsDeeply(root))
	}

	@Test
	@DisplayName("load resource that should have been saved after propagating a change into a virtual model")
	def void savedVirtualModel() {
		val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val changeRecorder = new ChangeRecorder(createUuidGeneratorAndResolver(virtualModel.uuidResolver, resourceSet))
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val resourceUri = URI.createFileURI(projectFolder.resolve("root.allElementTypes").toString)
		val root = aet.Root
		resourceSet.createResource(resourceUri) => [
			contents += root
		]
		virtualModel.propagateChange(
			VitruviusChangeFactory.instance.createCompositeTransactionalChange(changeRecorder.endRecording))
		val reloadedResource = new ResourceSetImpl().withGlobalFactories.getResource(resourceUri, true)
		assertNotNull(reloadedResource)
		assertFalse(reloadedResource.contents.empty)
		assertThat(reloadedResource.contents.get(0), equalsDeeply(root))
	}

	@Test
	@DisplayName("reload a virtual model to which a simple change was propagated")
	def void reloadVirtualModel() {
		val virtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
		val resourceSet = new ResourceSetImpl().withGlobalFactories
		val changeRecorder = new ChangeRecorder(createUuidGeneratorAndResolver(virtualModel.uuidResolver, resourceSet))
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording
		val resourceUri = URI.createFileURI(projectFolder.resolve("root.allElementTypes").toString)
		val root = aet.Root
		resourceSet.createResource(resourceUri) => [
			contents += root
		]
		virtualModel.propagateChange(
			VitruviusChangeFactory.instance.createCompositeTransactionalChange(changeRecorder.endRecording))
		val originalModel = virtualModel.getModelInstance(VURI.getInstance(resourceUri))
		val reloadedVirtualModel = createAndLoadTestVirtualModel(projectFolder.resolve("vsum"))
		val reloadedModel = reloadedVirtualModel.getModelInstance(VURI.getInstance(resourceUri))
		assertNotNull(reloadedModel.resource)
		assertFalse(reloadedModel.resource.contents.empty)
		assertThat(reloadedModel.resource.contents.get(0), equalsDeeply(root))
		assertNotEquals(originalModel, reloadedModel)
	}
}
