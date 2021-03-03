package tools.vitruv.framework.uuid.tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.TestProjectManager
import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
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
	@DisplayName("resolve UUID in parent resolver for object with root container not being its resource root")
	def void parentResolveRootContainerNotResourceRoot() {
		val childResourceSet = new ResourceSetImpl().withGlobalFactories()
		val childUuidGeneratorAndResolver = createUuidGeneratorAndResolver(uuidGeneratorAndResolver, childResourceSet)

		// Model generation
		val nonRoot = aet.NonRoot
		childResourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += aet.Root => [
				singleValuedContainmentEReference = nonRoot
			]
			save(null)
		]
		childResourceSet.createResource(URI.createFileURI(testProjectPath.resolve("nonRoot.aet").toString)) => [
			contents += nonRoot
			save(null)
		]

		// UUID generation and registration
		val generatedUuid = childUuidGeneratorAndResolver.generateUuid(nonRoot)
		val parentResolverNonRoot = resourceSet.getEObject(nonRoot.URI, true)
		uuidGeneratorAndResolver.registerEObject(generatedUuid, parentResolverNonRoot)

		// This requires to resolve the element in the parent resolver's resource set with its URI
		assertEquals(generatedUuid, uuidGeneratorAndResolver.getUuid(nonRoot))
		assertNotEquals(childUuidGeneratorAndResolver.getEObject(generatedUuid),
			uuidGeneratorAndResolver.getEObject(generatedUuid))
	}

	@Test
	@DisplayName("resolve UUID in parent resolver for multiple root elements")
	def void parentResolveOneOfMultipleRootElements() {
		val childResourceSet = new ResourceSetImpl().withGlobalFactories()
		val childUuidGeneratorAndResolver = createUuidGeneratorAndResolver(uuidGeneratorAndResolver, childResourceSet)

		// Model generation
		val firstRoot = aet.Root
		val secondRoot = aet.Root
		childResourceSet.createResource(URI.createFileURI(testProjectPath.resolve("test.aet").toString)) => [
			contents += firstRoot
			contents += secondRoot
			save(null)
		]

		// UUID generation and registration
		val firstRootUuid = childUuidGeneratorAndResolver.generateUuid(firstRoot)
		val secondRootUuid = childUuidGeneratorAndResolver.generateUuid(secondRoot)
		val parentResolverFirstRoot = resourceSet.getEObject(firstRoot.URI, true)
		val parentResolverSecondRoot = resourceSet.getEObject(secondRoot.URI, true)
		uuidGeneratorAndResolver.registerEObject(firstRootUuid, parentResolverFirstRoot)
		uuidGeneratorAndResolver.registerEObject(secondRootUuid, parentResolverSecondRoot)

		// This requires to resolve the elements in the parent resolver's resource set with its URI
		assertEquals(firstRootUuid, uuidGeneratorAndResolver.getUuid(firstRoot))
		assertEquals(secondRootUuid, uuidGeneratorAndResolver.getUuid(secondRoot))
		assertNotEquals(childUuidGeneratorAndResolver.getEObject(firstRootUuid),
			uuidGeneratorAndResolver.getEObject(firstRootUuid))
	}

	@Test
	@DisplayName("resolve UUID in parent resolver for contents of multiple root elements")
	def void parentResolveElementsContainedInOneOfMultipleRootElements() {
		val childResourceSet = new ResourceSetImpl().withGlobalFactories()
		val childUuidGeneratorAndResolver = createUuidGeneratorAndResolver(uuidGeneratorAndResolver, childResourceSet)

		// Model generation
		val firstNonRoot = aet.NonRoot
		val secondNonRoot = aet.NonRoot
		val deeperContainedElement = aet.NonRoot
		childResourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += aet.Root => [
				singleValuedContainmentEReference = firstNonRoot
			]
			contents += aet.Root => [
				singleValuedContainmentEReference = secondNonRoot
				recursiveRoot = aet.Root => [
					singleValuedContainmentEReference = deeperContainedElement
				]
			]
			save(null)
		]

		// UUID generation and registration
		val firstNonRootUuid = childUuidGeneratorAndResolver.generateUuid(firstNonRoot)
		val secondNonRootUuid = childUuidGeneratorAndResolver.generateUuid(secondNonRoot)
		val deeperContainedElementUuid = childUuidGeneratorAndResolver.generateUuid(deeperContainedElement)
		val parentResolverFirstNonRoot = resourceSet.getEObject(firstNonRoot.URI, true)
		val parentResolverSecondNonRoot = resourceSet.getEObject(secondNonRoot.URI, true)
		val parentResolverdeeperContainedElement = resourceSet.getEObject(deeperContainedElement.URI, true)
		uuidGeneratorAndResolver.registerEObject(firstNonRootUuid, parentResolverFirstNonRoot)
		uuidGeneratorAndResolver.registerEObject(secondNonRootUuid, parentResolverSecondNonRoot)
		uuidGeneratorAndResolver.registerEObject(deeperContainedElementUuid, parentResolverdeeperContainedElement)

		// This requires to resolve the elements in the parent resolver's resource set with its URI
		assertEquals(firstNonRootUuid, uuidGeneratorAndResolver.getUuid(firstNonRoot))
		assertEquals(secondNonRootUuid, uuidGeneratorAndResolver.getUuid(secondNonRoot))
		assertEquals(deeperContainedElementUuid, uuidGeneratorAndResolver.getUuid(deeperContainedElement))
		assertNotEquals(childUuidGeneratorAndResolver.getEObject(firstNonRootUuid),
			uuidGeneratorAndResolver.getEObject(firstNonRootUuid))
		assertNotEquals(childUuidGeneratorAndResolver.getEObject(secondNonRootUuid),
			uuidGeneratorAndResolver.getEObject(secondNonRootUuid))
		assertNotEquals(childUuidGeneratorAndResolver.getEObject(deeperContainedElementUuid),
			uuidGeneratorAndResolver.getEObject(deeperContainedElementUuid))
	}

	@Test
	@DisplayName("generate UUID and resolve it after element deletion")
	def void elementDeletionDoesNotRemoveUiud() {
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val uuid = uuidGeneratorAndResolver.generateUuid(root)
		EcoreUtil.delete(root)
		assertEquals(uuid, uuidGeneratorAndResolver.getUuid(root))
	}

	@Test
	@DisplayName("resolve UUID for element moved to different container")
	def void elementMovementKeepsUuid() {
		val root = aet.Root
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val uuid = uuidGeneratorAndResolver.generateUuid(root)
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root2.aet").toString)) => [
			contents += root
		]
		assertEquals(uuid, uuidGeneratorAndResolver.getUuid(root))
	}

	@Test
	@DisplayName("resolve UUID during element movement to different container")
	def void elementMovementWithResolutionInTransientStateKeepsUuid() {
		val root = aet.Root
		val resource = resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val uuid = uuidGeneratorAndResolver.generateUuid(root)
		resource.contents.clear
		assertEquals(uuid, uuidGeneratorAndResolver.getUuid(root))
		resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root2.aet").toString)) => [
			contents += root
		]
		assertEquals(uuid, uuidGeneratorAndResolver.getUuid(root))
	}

	@Test
	@DisplayName("cleanup resolver when saving after element removal")
	def void cleanupAfterElementRemovalRemovesUuid() {
		val root = aet.Root
		uuidGeneratorAndResolver.generateUuid(root)
		uuidGeneratorAndResolver.save()
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.getUuid(root)]
	}
	
	@Test
	@DisplayName("cleanup resolver when saving resource after element removal")
	def void cleanupAfterElementRemovalRemovesUuidWithResource() {
		val root = aet.Root
		val resource = resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root
		]
		val uuid = uuidGeneratorAndResolver.generateUuid(root)
		uuidGeneratorAndResolver.save()
		assertEquals(uuid, uuidGeneratorAndResolver.getUuid(root))
		resource.contents.clear
		assertEquals(uuid, uuidGeneratorAndResolver.getUuid(root))
		uuidGeneratorAndResolver.save()
		assertThrows(IllegalStateException)[uuidGeneratorAndResolver.getUuid(root)]
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
		val rootUuid = uuidGeneratorAndResolver.generateUuid(root)
		val nonRootUuid = uuidGeneratorAndResolver.generateUuid(nonRoot)
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createAndLoadUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		assertEquals(rootUuid, uuidGeneratorAndResolver.getUuid(root))
		assertEquals(nonRootUuid, uuidGeneratorAndResolver.getUuid(nonRoot))
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
		val rootUuid = uuidGeneratorAndResolver.generateUuid(root)
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createAndLoadUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		assertEquals(rootUuid, uuidGeneratorAndResolver.getUuid(root))
		assertThrows(IllegalStateException) [uuidGeneratorAndResolver.getUuid(nonRoot)]
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
		val rootUuid = uuidGeneratorAndResolver.generateUuid(root)
		uuidGeneratorAndResolver.generateUuid(nonRoot)
		root.singleValuedContainmentEReference = null
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createAndLoadUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		assertEquals(rootUuid, uuidGeneratorAndResolver.getUuid(root))
		assertThrows(IllegalStateException) [uuidGeneratorAndResolver.getUuid(nonRoot)]
	}
	
	@Test
	@DisplayName("load UUIDs stored to a resource and resolve elements in new resource set")
	def void existAllUuidsAfterReloadWithNewResourceSet() {
		val uuidUri = URI.createFileURI(testProjectPath.resolve("uuid.uuid").toString)
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(this.resourceSet, uuidUri)
		val root = aet.Root
		val nonRoot = aet.NonRoot
		val originalResource = resourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		originalResource.save(null)
		val rootUuid = uuidGeneratorAndResolver.generateUuid(root)
		val nonRootUuid = uuidGeneratorAndResolver.generateUuid(nonRoot)
		uuidGeneratorAndResolver.save()
		val newResourceSet = new ResourceSetImpl().withGlobalFactories
		uuidGeneratorAndResolver = createAndLoadUuidGeneratorAndResolver(newResourceSet , uuidUri)
		assertFalse(newResourceSet.resources.empty)
		val newResource = newResourceSet.resources.get(0) 
		assertFalse(newResource.contents.empty)
		assertThat(newResource, containsModelOf(originalResource)) 
		val newRoot = newResource.contents.get(0) as Root
		assertEquals(rootUuid, uuidGeneratorAndResolver.getUuid(root))
		assertEquals(rootUuid, uuidGeneratorAndResolver.getUuid(newRoot))
		assertEquals(nonRootUuid, uuidGeneratorAndResolver.getUuid(root.singleValuedContainmentEReference))
		assertEquals(nonRootUuid, uuidGeneratorAndResolver.getUuid(newRoot.singleValuedContainmentEReference))
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
		uuidGeneratorAndResolver.generateUuid(root)
		uuidGeneratorAndResolver.save()
		val newResourceSet = new ResourceSetImpl().withGlobalFactories
		assertThrows(IllegalStateException) [createAndLoadUuidGeneratorAndResolver(newResourceSet, uuidUri)]
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
		uuidGeneratorAndResolver.generateUuid(root)
		uuidGeneratorAndResolver.save()
		val newResourceSet = new ResourceSetImpl().withGlobalFactories
		assertThrows(IllegalStateException) [createAndLoadUuidGeneratorAndResolver(newResourceSet, uuidUri)]
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
		val rootUuid = uuidGeneratorAndResolver.generateUuid(root)
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createAndLoadUuidGeneratorAndResolver(resourceSet, uuidUri)
		uuidGeneratorAndResolver.save()
		assertEquals(1, new ResourceSetImpl().withGlobalFactories.getResource(uuidUri, true).contents.size)
		uuidGeneratorAndResolver = createAndLoadUuidGeneratorAndResolver(resourceSet, uuidUri)
		assertEquals(rootUuid, uuidGeneratorAndResolver.getUuid(root))
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
		val rootUuid = uuidGeneratorAndResolver.generateUuid(root)
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createUuidGeneratorAndResolver(resourceSet, uuidUri)
		val newRootUuid = uuidGeneratorAndResolver.generateUuid(root)
		uuidGeneratorAndResolver.save()
		uuidGeneratorAndResolver = createAndLoadUuidGeneratorAndResolver(resourceSet, uuidUri)
		assertNotEquals(rootUuid, uuidGeneratorAndResolver.getUuid(root))
		assertEquals(newRootUuid, uuidGeneratorAndResolver.getUuid(root))
	}

}
