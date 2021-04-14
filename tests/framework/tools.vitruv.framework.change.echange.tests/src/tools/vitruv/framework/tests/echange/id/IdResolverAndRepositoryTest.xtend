package tools.vitruv.framework.tests.echange.id

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
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import static org.hamcrest.MatcherAssert.assertThat
import allElementTypes.Root
import static tools.vitruv.framework.change.id.IdResolverAndRepositoryFactory.createIdResolverAndRepository
import tools.vitruv.framework.change.id.IdResolverAndRepository

@ExtendWith(#[TestProjectManager, RegisterMetamodelsInStandalone])
class IdResolverAndRepositoryTest {
	var ResourceSet resourceSet
	var IdResolverAndRepository idResolver
	var Path testProjectPath

	@BeforeEach
	def void setup(@TestProject Path testProjectPath) {
		this.testProjectPath = testProjectPath
		this.resourceSet = new ResourceSetImpl().withGlobalFactories()
		this.idResolver = createIdResolverAndRepository(resourceSet)
	}
	
	@Test
	@DisplayName("resolve element with cache ID")
	def void resolveElementWithCacheId() {
		// Model generation
		val root = aet.Root
		val initialRootId = idResolver.getAndUpdateId(root)
		assertEquals(root, idResolver.getEObject(initialRootId))
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
		val rootId = idResolver.getAndUpdateId(root)
		assertEquals(root, idResolver.getEObject(rootId))
	}

	@Test
	@DisplayName("change ID after adding element to resource")
	def void idChangesWhenAddingElementToResource() {
		// Model generation
		val root = aet.Root
		val initialRootId = idResolver.getAndUpdateId(root)
		val nonRoot = aet.NonRoot
		val initialNonRootId = idResolver.getAndUpdateId(nonRoot)
		val resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString)
		resourceSet.createResource(resourceUri) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		val insertedRootId = idResolver.getAndUpdateId(root)
		val insertedNonRootId = idResolver.getAndUpdateId(root)
		assertNotEquals(initialRootId, insertedRootId)
		assertNotEquals(initialNonRootId, insertedNonRootId)
		assertEquals(root, idResolver.getEObject(insertedRootId))
		assertEquals(root, idResolver.getEObject(insertedNonRootId))
	}
	
	@Test
	@DisplayName("removed old ID after element change")
	def void changeRemovesOldId() {
		// Model generation
		val root = aet.Root
		val initialRootId = idResolver.getAndUpdateId(root)
		val nonRoot = aet.NonRoot
		val initialNonRootId = idResolver.getAndUpdateId(nonRoot)
		val resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString)
		resourceSet.createResource(resourceUri) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		idResolver.getAndUpdateId(root)
		idResolver.getAndUpdateId(nonRoot)
		assertThrows(IllegalStateException) [idResolver.getEObject(initialRootId)]
		assertThrows(IllegalStateException) [idResolver.getEObject(initialNonRootId)]
	}
	
	@Test
	@DisplayName("resolve element with old ID after change")
	def void resolveElementWithOldIdAfterChange() {
		// Model generation
		val root = aet.Root
		val initialRootId = idResolver.getAndUpdateId(root)
		val nonRoot = aet.NonRoot
		val initialNonRootId = idResolver.getAndUpdateId(nonRoot)
		val resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString)
		resourceSet.createResource(resourceUri) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		assertEquals(root, idResolver.getEObject(initialRootId))
		assertEquals(nonRoot, idResolver.getEObject(initialNonRootId))
	}
	
	@Test
	@DisplayName("update ID for unchanged object")
	def void updateIdForUnchangedObject() {
		// Model generation
		val root = aet.Root
		val initialRootId = idResolver.getAndUpdateId(root)
		val nonRoot = aet.NonRoot
		val initialNonRootId = idResolver.getAndUpdateId(nonRoot)
		val resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString)
		resourceSet.createResource(resourceUri) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		assertEquals(root, idResolver.getEObject(initialRootId))
		assertEquals(nonRoot, idResolver.getEObject(initialNonRootId))
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
		val generatedRootId = idResolver.getAndUpdateId(root)
		val generatedNonRootId = idResolver.getAndUpdateId(nonRoot)

		val childResourceSet = new ResourceSetImpl().withGlobalFactories()
		val childidResolver = createIdResolverAndRepository(childResourceSet)
		childResourceSet.getResource(resourceUri, true)

		val childResolverRoot = resourceSet.getEObject(root.URI, true)
		val childResolverNonRoot = resourceSet.getEObject(nonRoot.URI, true)

		assertEquals(generatedRootId, childidResolver.getAndUpdateId(childResolverRoot))
		assertEquals(generatedNonRootId, childidResolver.getAndUpdateId(childResolverNonRoot))
		assertEquals(childidResolver.getEObject(generatedRootId),
			idResolver.getEObject(generatedRootId))
		assertEquals(childidResolver.getEObject(generatedNonRootId),
			idResolver.getEObject(generatedNonRootId))
	}

	@Test
	@DisplayName("try to assign different IDs for same object")
	def void assignDifferentIdsToObject() {
		val object = aet.Root
		val id = idResolver.getAndUpdateId(object)
		assertThrows(IllegalStateException)[idResolver.register(id + "2", object)]
	}

	@Test
	@DisplayName("update ID without changing object")
	def void calculateIdMultipleTimesWithoutChangingObject() {
		val object = aet.Root
		val id = idResolver.getAndUpdateId(object)
		assertEquals(id, idResolver.getAndUpdateId(object))
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
		
		elements.forEach[idResolver.getAndUpdateId(it)]

		val additionalResourceSet = new ResourceSetImpl().withGlobalFactories()
		val additionalidResolver = createIdResolverAndRepository(additionalResourceSet)

		elements.forEach[
			val elementId = idResolver.getAndUpdateId(it)
			assertEquals(elementId, additionalidResolver.getAndUpdateId(additionalResourceSet.getEObject(it.URI, true)))
			assertNotEquals(idResolver.getEObject(elementId), additionalidResolver.getEObject(elementId))
			assertTrue(EcoreUtil.equals(idResolver.getEObject(elementId), additionalidResolver.getEObject(elementId)))
		]
	}

	@Test
	@DisplayName("generate ID and resolve it after element deletion")
	def void elementDeletionDoesNotRemoveUiud() {
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val id = idResolver.getAndUpdateId(root)
		EcoreUtil.delete(root)
		assertEquals(root, idResolver.getEObject(id))
	}

	@Test
	@DisplayName("resolve ID for element moved to different container")
	def void elementMovementUpdatesId() {
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val rootId = idResolver.getAndUpdateId(root)
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root2.aet").toString)) => [
			contents += root
		]
		assertEquals(root, idResolver.getEObject(rootId))
		assertNotEquals(rootId, idResolver.getAndUpdateId(root))
	}

	@Test
	@DisplayName("resolve ID during element movement to different container")
	def void elementMovementWithResolutionInTransientStateKeepsId() {
		val root = aet.Root
		val resource = resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val initialId = idResolver.getAndUpdateId(root)
		resource.contents.clear
		assertEquals(root, idResolver.getEObject(initialId))
		val cacheId = idResolver.getAndUpdateId(root)
		assertThrows(IllegalStateException) [idResolver.getEObject(initialId)]
		assertNotEquals(initialId, cacheId)
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root2.aet").toString)) => [
			contents += root
		]
		assertEquals(root, idResolver.getEObject(cacheId))
		assertNotEquals(cacheId, idResolver.getAndUpdateId(root))
		assertNotEquals(initialId, idResolver.getAndUpdateId(root))
	}

	@Test
	@DisplayName("cleanup resolver when saving after element removal")
	def void cleanupAfterElementRemovalRemovesId() {
		val root = aet.Root
		val id = idResolver.getAndUpdateId(root)
		idResolver.save()
		assertThrows(IllegalStateException)[idResolver.getEObject(id)]
	}

	@Test
	@DisplayName("cleanup resolver when saving resource after element removal")
	def void cleanupAfterElementRemovalRemovesIdWithResource() {
		val root = aet.Root
		val resource = resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val rootId = idResolver.getAndUpdateId(root)
		idResolver.save()
		assertEquals(rootId, idResolver.getAndUpdateId(root))
		resource.contents.clear
		assertEquals(root, idResolver.getEObject(rootId))
		assertNotEquals(rootId, idResolver.getAndUpdateId(root))
		idResolver.save()
		assertThrows(IllegalStateException)[idResolver.getEObject(rootId)]
	}

	@Test
	@DisplayName("load IDs stored to a resource")
	def void existAllIdsAfterReload() {
		val idUri = URI.createFileURI(testProjectPath.resolve("id.id").toString)
		idResolver = createIdResolverAndRepository(this.resourceSet, idUri)
		val root = aet.Root
		val nonRoot = aet.NonRoot
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		val rootId = idResolver.getAndUpdateId(root)
		val nonRootId = idResolver.getAndUpdateId(nonRoot)
		idResolver.save()
		idResolver = createIdResolverAndRepository(this.resourceSet, idUri)
		idResolver.loadIdsAndModelsFromSerializedIdRepository()
		assertEquals(rootId, idResolver.getAndUpdateId(root))
		assertEquals(nonRootId, idResolver.getAndUpdateId(nonRoot))
	}

	@Test
	@DisplayName("load IDs stored to a resource after cleanup")
	def void existAllIdsAfterReloadWithCleanup() {
		val idUri = URI.createFileURI(testProjectPath.resolve("id.id").toString)
		idResolver = createIdResolverAndRepository(this.resourceSet, idUri)
		val root = aet.Root
		val nonRoot = aet.NonRoot
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		val rootId = idResolver.getAndUpdateId(root)
		val nonRootId = idResolver.getAndUpdateId(nonRoot)
		root.singleValuedContainmentEReference = null
		idResolver.save()
		idResolver = createIdResolverAndRepository(this.resourceSet, idUri)
		idResolver.loadIdsAndModelsFromSerializedIdRepository()
		assertEquals(rootId, idResolver.getAndUpdateId(root))
		assertThrows(IllegalStateException)[idResolver.getEObject(nonRootId)]
	}

	@Test
	@DisplayName("load IDs stored to a resource and resolve elements in new resource set")
	def void existAllIdsAfterReloadWithNewResourceSet() {
		val idUri = URI.createFileURI(testProjectPath.resolve("id.id").toString)
		idResolver = createIdResolverAndRepository(this.resourceSet, idUri)
		val root = aet.Root
		val nonRoot = aet.NonRoot
		val originalResource = resourceSet.createResource(
			URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		originalResource.save(null)
		val rootId = idResolver.getAndUpdateId(root)
		val nonRootId = idResolver.getAndUpdateId(nonRoot)
		idResolver.save()
		val newResourceSet = new ResourceSetImpl().withGlobalFactories
		idResolver = createIdResolverAndRepository(newResourceSet, idUri)
		idResolver.loadIdsAndModelsFromSerializedIdRepository()
		assertFalse(newResourceSet.resources.empty)
		val newResource = newResourceSet.resources.get(0)
		assertFalse(newResource.contents.empty)
		assertThat(newResource, containsModelOf(originalResource))
		val newRoot = newResource.contents.get(0) as Root
		assertEquals(rootId, idResolver.getAndUpdateId(newRoot))
		assertEquals(nonRootId, idResolver.getAndUpdateId(newRoot.singleValuedContainmentEReference))
	}

	@Test
	@DisplayName("try load IDs referencing non-existent elements")
	def void notIgnoreIdsForNonExistentElements() {
		val idUri = URI.createFileURI(testProjectPath.resolve("id.id").toString)
		idResolver = createIdResolverAndRepository(this.resourceSet, idUri)
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		idResolver.getAndUpdateId(root)
		idResolver.save()
		val newResourceSet = new ResourceSetImpl().withGlobalFactories
		idResolver = createIdResolverAndRepository(newResourceSet, idUri)
		assertThrows(IllegalStateException)[idResolver.loadIdsAndModelsFromSerializedIdRepository()]
	}

	@Test
	@DisplayName("try load IDs referencing elements in non-existent resource")
	def void notIgnoreIdsForNonExistentResource() {
		val idUri = URI.createFileURI(testProjectPath.resolve("id.id").toString)
		idResolver = createIdResolverAndRepository(this.resourceSet, idUri)
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		idResolver.getAndUpdateId(root)
		idResolver.save()
		val newResourceSet = new ResourceSetImpl().withGlobalFactories
		idResolver = createIdResolverAndRepository(newResourceSet, idUri)
		assertThrows(IllegalStateException)[idResolver.loadIdsAndModelsFromSerializedIdRepository()]
	}

	@Test
	@DisplayName("store and load IDs multiple times")
	def void storeAndReloadIdRepositoryMultipleTimes() {
		val idUri = URI.createFileURI(testProjectPath.resolve("id.id").toString)
		idResolver = createIdResolverAndRepository(resourceSet, idUri)
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val rootId = idResolver.getAndUpdateId(root)
		idResolver.save()
		idResolver = createIdResolverAndRepository(resourceSet, idUri)
		idResolver.loadIdsAndModelsFromSerializedIdRepository()
		idResolver.save()
		assertEquals(1, new ResourceSetImpl().withGlobalFactories.getResource(idUri, true).contents.size)
		idResolver = createIdResolverAndRepository(resourceSet, idUri)
		idResolver.loadIdsAndModelsFromSerializedIdRepository()
		assertEquals(rootId, idResolver.getAndUpdateId(root))
	}

}
