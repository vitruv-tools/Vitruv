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
	@DisplayName("resolve element with cache ID")
	def void resolveElementWithCacheId() {
		// Model generation
		val root = aet.Root
		val initialRootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		assertEquals(root, uuidGeneratorAndResolver.getEObject(initialRootId))
	}
	
	@Test
	@DisplayName("resolve element with URI ID")
	def void resolveElementWithUriId() {
		// Model generation
		val root = aet.Root
		val resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString)
		resourceSet.createResource(resourceUri) => [
			contents += root
		]
		val rootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		assertEquals(root, uuidGeneratorAndResolver.getEObject(rootId))
	}

	@Test
	@DisplayName("change ID after adding element to resource")
	def void idChangesWhenAddingElementToResource() {
		// Model generation
		val root = aet.Root
		val initialRootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		val nonRoot = aet.NonRoot
		val initialNonRootId = uuidGeneratorAndResolver.getAndUpdateId(nonRoot)
		val resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString)
		resourceSet.createResource(resourceUri) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		val insertedRootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		val insertedNonRootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		assertNotEquals(initialRootId, insertedRootId)
		assertNotEquals(initialNonRootId, insertedNonRootId)
		assertEquals(root, uuidGeneratorAndResolver.getEObject(insertedRootId))
		assertEquals(root, uuidGeneratorAndResolver.getEObject(insertedNonRootId))
	}
	
	@Test
	@DisplayName("removed old ID after element change")
	def void changeRemovesOldId() {
		// Model generation
		val root = aet.Root
		val initialRootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		val nonRoot = aet.NonRoot
		val initialNonRootId = uuidGeneratorAndResolver.getAndUpdateId(nonRoot)
		val resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString)
		resourceSet.createResource(resourceUri) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.getAndUpdateId(nonRoot)
		assertThrows(IllegalStateException) [uuidGeneratorAndResolver.getEObject(initialRootId)]
		assertThrows(IllegalStateException) [uuidGeneratorAndResolver.getEObject(initialNonRootId)]
	}
	
	@Test
	@DisplayName("resolve element with old ID after change")
	def void resolveElementWithOldIdAfterChange() {
		// Model generation
		val root = aet.Root
		val initialRootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		val nonRoot = aet.NonRoot
		val initialNonRootId = uuidGeneratorAndResolver.getAndUpdateId(nonRoot)
		val resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString)
		resourceSet.createResource(resourceUri) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		assertEquals(root, uuidGeneratorAndResolver.getEObject(initialRootId))
		assertEquals(nonRoot, uuidGeneratorAndResolver.getEObject(initialNonRootId))
	}
	
	@Test
	@DisplayName("update ID for unchanged object")
	def void updateIdForUnchangedObject() {
		// Model generation
		val root = aet.Root
		val initialRootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		val nonRoot = aet.NonRoot
		val initialNonRootId = uuidGeneratorAndResolver.getAndUpdateId(nonRoot)
		val resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString)
		resourceSet.createResource(resourceUri) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		assertEquals(root, uuidGeneratorAndResolver.getEObject(initialRootId))
		assertEquals(nonRoot, uuidGeneratorAndResolver.getEObject(initialNonRootId))
	}
	

	@Test
	@DisplayName("resolve ID in other resource set")
	def void resolveInOtherResourceSet() {
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

		// ID generation and registration
		val generatedRootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		val generatedNonRootId = uuidGeneratorAndResolver.getAndUpdateId(nonRoot)

		val childResourceSet = new ResourceSetImpl().withGlobalFactories()
		val childUuidGeneratorAndResolver = createUuidGeneratorAndResolver(childResourceSet)
		childResourceSet.getResource(resourceUri, true)

		val childResolverRoot = resourceSet.getEObject(root.URI, true)
		val childResolverNonRoot = resourceSet.getEObject(nonRoot.URI, true)

		assertEquals(generatedRootId, childUuidGeneratorAndResolver.getAndUpdateId(childResolverRoot))
		assertEquals(generatedNonRootId, childUuidGeneratorAndResolver.getAndUpdateId(childResolverNonRoot))
		assertEquals(childUuidGeneratorAndResolver.getEObject(generatedRootId),
			uuidGeneratorAndResolver.getEObject(generatedRootId))
		assertEquals(childUuidGeneratorAndResolver.getEObject(generatedNonRootId),
			uuidGeneratorAndResolver.getEObject(generatedNonRootId))
	}

	@Test
	@DisplayName("try to assign different IDs for same object")
	def void assignDifferentIdsToObject() {
		val object = aet.Root
		val id = uuidGeneratorAndResolver.getAndUpdateId(object)
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.registerEObject(id + "2", object)]
	}

	@Test
	@DisplayName("update ID without changing object")
	def void calculateIdMultipleTimesWithoutChangingObject() {
		val object = aet.Root
		val id = uuidGeneratorAndResolver.getAndUpdateId(object)
		assertEquals(id, uuidGeneratorAndResolver.getAndUpdateId(object))
	}

	@Test
	@DisplayName("resolve ID in child resolver for contents of multiple root elements stored in multiple resources")
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
		// ID generation and registration
		
		elements.forEach[uuidGeneratorAndResolver.getAndUpdateId(it)]

		val additionalResourceSet = new ResourceSetImpl().withGlobalFactories()
		val additionalUuidGeneratorAndResolver = createUuidGeneratorAndResolver(additionalResourceSet)

		elements.forEach[
			val elementId = uuidGeneratorAndResolver.getAndUpdateId(it)
			assertEquals(elementId, additionalUuidGeneratorAndResolver.getAndUpdateId(additionalResourceSet.getEObject(it.URI, true)))
			assertNotEquals(uuidGeneratorAndResolver.getEObject(elementId), additionalUuidGeneratorAndResolver.getEObject(elementId))
			assertTrue(EcoreUtil.equals(uuidGeneratorAndResolver.getEObject(elementId), additionalUuidGeneratorAndResolver.getEObject(elementId)))
		]
	}

	@Test
	@DisplayName("generate ID and resolve it after element deletion")
	def void elementDeletionDoesNotRemoveUiud() {
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val id = uuidGeneratorAndResolver.getAndUpdateId(root)
		EcoreUtil.delete(root)
		assertEquals(root, uuidGeneratorAndResolver.getEObject(id))
	}

	@Test
	@DisplayName("resolve ID for element moved to different container")
	def void elementMovementUpdatesId() {
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val rootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root2.aet").toString)) => [
			contents += root
		]
		assertEquals(root, uuidGeneratorAndResolver.getEObject(rootId))
		assertNotEquals(rootId, uuidGeneratorAndResolver.getAndUpdateId(root))
	}

	@Test
	@DisplayName("resolve ID during element movement to different container")
	def void elementMovementWithResolutionInTransientStateKeepsId() {
		val root = aet.Root
		val resource = resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val initialId = uuidGeneratorAndResolver.getAndUpdateId(root)
		resource.contents.clear
		assertEquals(root, uuidGeneratorAndResolver.getEObject(initialId))
		val cacheId = uuidGeneratorAndResolver.getAndUpdateId(root)
		assertThrows(IllegalStateException) [uuidGeneratorAndResolver.getEObject(initialId)]
		assertNotEquals(initialId, cacheId)
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root2.aet").toString)) => [
			contents += root
		]
		assertEquals(root, uuidGeneratorAndResolver.getEObject(cacheId))
		assertNotEquals(cacheId, uuidGeneratorAndResolver.getAndUpdateId(root))
		assertNotEquals(initialId, uuidGeneratorAndResolver.getAndUpdateId(root))
	}

	@Test
	@DisplayName("cleanup resolver when saving after element removal")
	def void cleanupAfterElementRemovalRemovesId() {
		val root = aet.Root
		val id = uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.getEObject(id)]
	}

	@Test
	@DisplayName("cleanup resolver when saving resource after element removal")
	def void cleanupAfterElementRemovalRemovesIdWithResource() {
		val root = aet.Root
		val resource = resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val rootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		assertEquals(rootId, uuidGeneratorAndResolver.getAndUpdateId(root))
		resource.contents.clear
		assertEquals(root, uuidGeneratorAndResolver.getEObject(rootId))
		assertNotEquals(rootId, uuidGeneratorAndResolver.getAndUpdateId(root))
		uuidGeneratorAndResolver.save()
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.getEObject(rootId)]
	}

	@Test
	@DisplayName("load IDs stored to a resource")
	def void existAllIdsAfterReload() {
		val idUri = URI.createFileURI(testProjectPath.resolve("id.id").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, idUri)
		val root = aet.Root
		val nonRoot = aet.NonRoot
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		val rootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		val nonRootId = uuidGeneratorAndResolver.getAndUpdateId(nonRoot)
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, idUri)
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		assertEquals(rootId, uuidGeneratorAndResolver.getAndUpdateId(root))
		assertEquals(nonRootId, uuidGeneratorAndResolver.getAndUpdateId(nonRoot))
	}

	@Test
	@DisplayName("load IDs stored to a resource after cleanup")
	def void existAllIdsAfterReloadWithCleanup() {
		val idUri = URI.createFileURI(testProjectPath.resolve("id.id").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, idUri)
		val root = aet.Root
		val nonRoot = aet.NonRoot
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		val rootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		val nonRootId = uuidGeneratorAndResolver.getAndUpdateId(nonRoot)
		root.singleValuedContainmentEReference = null
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, idUri)
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		assertEquals(rootId, uuidGeneratorAndResolver.getAndUpdateId(root))
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.getEObject(nonRootId)]
	}

	@Test
	@DisplayName("load IDs stored to a resource and resolve elements in new resource set")
	def void existAllIdsAfterReloadWithNewResourceSet() {
		val idUri = URI.createFileURI(testProjectPath.resolve("id.id").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, idUri)
		val root = aet.Root
		val nonRoot = aet.NonRoot
		val originalResource = resourceSet.createResource(
			URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		originalResource.save(null)
		val rootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		val nonRootId = uuidGeneratorAndResolver.getAndUpdateId(nonRoot)
		uuidGeneratorAndResolver.save()
		val newResourceSet = new ResourceSetImpl().withGlobalFactories
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(newResourceSet, idUri)
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		assertFalse(newResourceSet.resources.empty)
		val newResource = newResourceSet.resources.get(0)
		assertFalse(newResource.contents.empty)
		assertThat(newResource, containsModelOf(originalResource))
		val newRoot = newResource.contents.get(0) as Root
		assertEquals(rootId, uuidGeneratorAndResolver.getAndUpdateId(newRoot))
		assertEquals(nonRootId, uuidGeneratorAndResolver.getAndUpdateId(newRoot.singleValuedContainmentEReference))
	}

	@Test
	@DisplayName("try load IDs referencing non-existent elements")
	def void notIgnoreIdsForNonExistentElements() {
		val idUri = URI.createFileURI(testProjectPath.resolve("id.id").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, idUri)
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		val newResourceSet = new ResourceSetImpl().withGlobalFactories
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(newResourceSet, idUri)
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()]
	}

	@Test
	@DisplayName("try load IDs referencing elements in non-existent resource")
	def void notIgnoreIdsForNonExistentResource() {
		val idUri = URI.createFileURI(testProjectPath.resolve("id.id").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, idUri)
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		val newResourceSet = new ResourceSetImpl().withGlobalFactories
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(newResourceSet, idUri)
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()]
	}

	@Test
	@DisplayName("store and load IDs multiple times")
	def void storeAndReloadUuidRepositoryMultipleTimes() {
		val idUri = URI.createFileURI(testProjectPath.resolve("id.id").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet, idUri)
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val rootId = uuidGeneratorAndResolver.getAndUpdateId(root)
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet, idUri)
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		uuidGeneratorAndResolver.save()
		assertEquals(1, new ResourceSetImpl().withGlobalFactories.getResource(idUri, true).contents.size)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet, idUri)
		uuidGeneratorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		assertEquals(rootId, uuidGeneratorAndResolver.getAndUpdateId(root))
	}

}
