package tools.vitruv.framework.uuid.tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.TestProjectManager
import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import org.eclipse.emf.common.util.URI
import tools.vitruv.testutils.TestProject
import java.nio.file.Path
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotEquals
import static extension org.eclipse.emf.ecore.util.EcoreUtil.getURI
import org.junit.jupiter.api.BeforeEach
import org.eclipse.emf.ecore.util.EcoreUtil
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import static org.hamcrest.MatcherAssert.assertThat
import allElementTypes.Root
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createAndLoadUuidGeneratorAndResolver

@ExtendWith(TestProjectManager, RegisterMetamodelsInStandalone)
class UuidGeneratorAndResolverImplTest {
	var ResourceSet resourceSet
	var UuidGeneratorAndResolver uuidGeneratorAndResolver
	var Path testProjectPath

	@BeforeEach
	def void setup(@TestProject Path testProjectPath) {
		this.testProjectPath = testProjectPath
		this.resourceSet = new ResourceSetImpl().withGlobalFactories()
		this.uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet)
	}

	@Test
	@DisplayName("resolve UUID in copied into child resolver")
	def void resolveInChildResolver() {
		// Model generation
		val root = aet.Root
		val nonRoot = aet.NonRoot
		val resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString)
		resourceSet.createResource(resourceUri) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
			save(null)
		]

		// UUID generation and registration
		val generatedRootUuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		val generatedNonRootUuid = uuidGeneratorAndResolver.getAndUpdateId(nonRoot)

		val childResourceSet = new ResourceSetImpl().withGlobalFactories()
		val childUuidGeneratorAndResolver = createUuidGeneratorAndResolver(childResourceSet)
		childResourceSet.getResource(resourceUri, true)

		val childResolverRoot = resourceSet.getEObject(root.URI, true)
		val childResolverNonRoot = resourceSet.getEObject(nonRoot.URI, true)

		assertEquals(generatedRootUuid, uuidGeneratorAndResolver.getAndUpdateId(childResolverRoot))
		assertEquals(generatedNonRootUuid, uuidGeneratorAndResolver.getAndUpdateId(childResolverNonRoot))
		assertNotEquals(childUuidGeneratorAndResolver.getEObject(generatedRootUuid),
			uuidGeneratorAndResolver.getEObject(generatedRootUuid))
		assertNotEquals(childUuidGeneratorAndResolver.getEObject(generatedNonRootUuid),
			uuidGeneratorAndResolver.getEObject(generatedNonRootUuid))
	}

	@Test
	@DisplayName("try to assign different UUIDs for same object")
	def void assignDifferentUuidsToObject() {
		val object = aet.Root
		val uuid = uuidGeneratorAndResolver.getAndUpdateId(object)
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.registerEObject(uuid + "2", object)]
	}

	@Test
	@DisplayName("try to assign the same UUID multiple times for same object")
	def void assignUuidMultipleTimes() {
		val object = aet.Root
		val uuid = uuidGeneratorAndResolver.getAndUpdateId(object)
		assertThrows(IllegalStateException) [uuidGeneratorAndResolver.registerEObject(uuid, object)]
	}

	@Test
	@DisplayName("resolve UUID in child resolver for contents of multiple root elements stored in multiple resources")
	def void childResolverElementsContainedInMultipleRootElementsAndResources() {
		// Model generation
		val firstRoot = aet.Root
		val secondRoot = aet.Root
		val firstNonRoot = aet.NonRoot
		val secondNonRoot = aet.NonRoot
		val containedRoot = aet.Root
		val deeperContainedElement = aet.NonRoot
		val elements = #[firstRoot, secondRoot, firstNonRoot, secondNonRoot, containedRoot, deeperContainedElement]
		val containedRootResource = resourceSet.createResource(
			URI.createFileURI(testProjectPath.resolve("containedRoot.aet").toString)) => [
			contents += containedRoot
		]
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += firstRoot => [
				singleValuedContainmentEReference = firstNonRoot
			]
			contents += secondRoot => [
				singleValuedContainmentEReference = secondNonRoot
				recursiveRoot = containedRoot => [
					singleValuedContainmentEReference = deeperContainedElement
				]
			]
			save(null)
		]
		containedRootResource.save(null) // Save after creating second resource, because we indirectly perform changes to first resource
		// UUID generation and registration
		
		elements.forEach[uuidGeneratorAndResolver.getAndUpdateId(it)]

		val childResourceSet = new ResourceSetImpl().withGlobalFactories()
		val childUuidGeneratorAndResolver = createUuidGeneratorAndResolver(childResourceSet)

		elements.forEach[
			val elementUuid = uuidGeneratorAndResolver.getAndUpdateId(it)
			assertEquals(elementUuid, childUuidGeneratorAndResolver.getAndUpdateId(childResourceSet.getEObject(it.URI, true)))
			assertNotEquals(uuidGeneratorAndResolver.getEObject(elementUuid), childUuidGeneratorAndResolver.getEObject(elementUuid))
			assertTrue(EcoreUtil.equals(uuidGeneratorAndResolver.getEObject(elementUuid), childUuidGeneratorAndResolver.getEObject(elementUuid)))
		]
	}
	
	@Test
	@DisplayName("loads resource into child resource set and afterwards adapt UUID resolver")
	def void childResolverLoadsExistingUuids() {
		// Model generation
		val root = aet.Root
		val nonRoot = aet.NonRoot
		val uri = URI.createFileURI(testProjectPath.resolve("root.aet").toString)
		resourceSet.createResource(uri) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
			save(null)
		]
		
		val rootUuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		val nonRootUuid = uuidGeneratorAndResolver.getAndUpdateId(nonRoot)

		val childResourceSet = new ResourceSetImpl().withGlobalFactories()
		childResourceSet.getResource(uri, true)
		val childUuidGeneratorAndResolver = createUuidGeneratorAndResolver(childResourceSet)

		assertThat(root, equalsDeeply(childUuidGeneratorAndResolver.getEObject(rootUuid)))
		assertThat(nonRoot, equalsDeeply(childUuidGeneratorAndResolver.getEObject(nonRootUuid)))
	}

	@Test
	@DisplayName("generate UUID and resolve it after element deletion")
	def void elementDeletionDoesNotRemoveUiud() {
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val uuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		EcoreUtil.delete(root)
		assertEquals(uuid, uuidGeneratorAndResolver.getAndUpdateId(root))
	}

	@Test
	@DisplayName("resolve UUID for element moved to different container")
	def void elementMovementKeepsUuid() {
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val uuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root2.aet").toString)) => [
			contents += root
		]
		assertEquals(uuid, uuidGeneratorAndResolver.getAndUpdateId(root))
	}

	@Test
	@DisplayName("resolve UUID during element movement to different container")
	def void elementMovementWithResolutionInTransientStateKeepsUuid() {
		val root = aet.Root
		val resource = resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val uuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		resource.contents.clear
		assertEquals(uuid, uuidGeneratorAndResolver.getAndUpdateId(root))
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root2.aet").toString)) => [
			contents += root
		]
		assertEquals(uuid, uuidGeneratorAndResolver.getAndUpdateId(root))
	}

	@Test
	@DisplayName("cleanup resolver when saving after element removal")
	def void cleanupAfterElementRemovalRemovesUuid() {
		val root = aet.Root
		uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.getAndUpdateId(root)]
	}

	@Test
	@DisplayName("cleanup resolver when saving resource after element removal")
	def void cleanupAfterElementRemovalRemovesUuidWithResource() {
		val root = aet.Root
		val resource = resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val uuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		assertEquals(uuid, uuidGeneratorAndResolver.getAndUpdateId(root))
		resource.contents.clear
		assertEquals(uuid, uuidGeneratorAndResolver.getAndUpdateId(root))
		uuidGeneratorAndResolver.save()
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.getAndUpdateId(root)]
	}

	@Test
	@DisplayName("load UUIDs stored to a resource")
	def void existAllUuidsAfterReload() {
		val uuidUri = URI.createFileURI(testProjectPath.resolve("uuid.uuid").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		val root = aet.Root
		val nonRoot = aet.NonRoot
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		val rootUuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		val nonRootUuid = uuidGeneratorAndResolver.getAndUpdateId(nonRoot)
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		assertEquals(rootUuid, uuidGeneratorAndResolver.getAndUpdateId(root))
		assertEquals(nonRootUuid, uuidGeneratorAndResolver.getAndUpdateId(nonRoot))
	}

	@Test
	@DisplayName("load UUIDs stored to a resource for part of model")
	def void existAllUuidsAfterReloadForPartOfModel() {
		val uuidUri = URI.createFileURI(testProjectPath.resolve("uuid.uuid").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		val root = aet.Root
		val nonRoot = aet.NonRoot
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		val rootUuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		assertEquals(rootUuid, uuidGeneratorAndResolver.getAndUpdateId(root))
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.getAndUpdateId(nonRoot)]
	}

	@Test
	@DisplayName("load UUIDs stored to a resource after cleanup")
	def void existAllUuidsAfterReloadWithCleanup() {
		val uuidUri = URI.createFileURI(testProjectPath.resolve("uuid.uuid").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		val root = aet.Root
		val nonRoot = aet.NonRoot
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		val rootUuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.getAndUpdateId(nonRoot)
		root.singleValuedContainmentEReference = null
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		assertEquals(rootUuid, uuidGeneratorAndResolver.getAndUpdateId(root))
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.getAndUpdateId(nonRoot)]
	}

	@Test
	@DisplayName("load UUIDs stored to a resource and resolve elements in new resource set")
	def void existAllUuidsAfterReloadWithNewResourceSet() {
		val uuidUri = URI.createFileURI(testProjectPath.resolve("uuid.uuid").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		val root = aet.Root
		val nonRoot = aet.NonRoot
		val originalResource = resourceSet.createResource(
			URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		originalResource.save(null)
		val rootUuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		val nonRootUuid = uuidGeneratorAndResolver.getAndUpdateId(nonRoot)
		uuidGeneratorAndResolver.save()
		val newResourceSet = new ResourceSetImpl().withGlobalFactories
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(newResourceSet, uuidUri)
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		assertFalse(newResourceSet.resources.empty)
		val newResource = newResourceSet.resources.get(0)
		assertFalse(newResource.contents.empty)
		assertThat(newResource, containsModelOf(originalResource))
		val newRoot = newResource.contents.get(0) as Root
		assertEquals(rootUuid, uuidGeneratorAndResolver.getAndUpdateId(newRoot))
		assertEquals(nonRootUuid, uuidGeneratorAndResolver.getAndUpdateId(newRoot.singleValuedContainmentEReference))
	}

	@Test
	@DisplayName("try load UUIDs referencing non-existent elements")
	def void notIgnoreUuidsForNonExistentElements() {
		val uuidUri = URI.createFileURI(testProjectPath.resolve("uuid.uuid").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		val newResourceSet = new ResourceSetImpl().withGlobalFactories
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(newResourceSet, uuidUri)
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()]
	}

	@Test
	@DisplayName("try load UUIDs referencing elements in non-existent resource")
	def void notIgnoreUuidsForNonExistentResource() {
		val uuidUri = URI.createFileURI(testProjectPath.resolve("uuid.uuid").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		val newResourceSet = new ResourceSetImpl().withGlobalFactories
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(newResourceSet, uuidUri)
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()]
	}

	@Test
	@DisplayName("store and load UUIDs multiple times")
	def void storeAndReloadUuidRepositoryMultipleTimes() {
		val uuidUri = URI.createFileURI(testProjectPath.resolve("uuid.uuid").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet, uuidUri)
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val rootUuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet, uuidUri)
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		uuidGeneratorAndResolver.save()
		assertEquals(1, new ResourceSetImpl().withGlobalFactories.getResource(uuidUri, true).contents.size)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet, uuidUri)
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		assertEquals(rootUuid, uuidGeneratorAndResolver.getAndUpdateId(root))
	}

	@Test
	@DisplayName("create second UUID resource and overwrite first one")
	def void overwriteExistingUuidsWhenCreatingResolverWithoutLoading() {
		val uuidUri = URI.createFileURI(testProjectPath.resolve("uuid.uuid").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet, uuidUri)
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val rootUuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet, uuidUri)
		val newRootUuid = uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet, uuidUri)
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		assertNotEquals(rootUuid, uuidGeneratorAndResolver.getAndUpdateId(root))
		assertEquals(newRootUuid, uuidGeneratorAndResolver.getAndUpdateId(root))
	}

}
