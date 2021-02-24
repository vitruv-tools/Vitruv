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
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.testutils.TestProject
import java.nio.file.Path
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotEquals
import static extension org.eclipse.emf.ecore.util.EcoreUtil.getURI
import org.junit.jupiter.api.BeforeEach

@ExtendWith(TestProjectManager, RegisterMetamodelsInStandalone)
class UuidGeneratorAndResolverImplTest {
	val resourceSet = new ResourceSetImpl().withGlobalFactories()
	val uuidGeneratorAndResolver = new UuidGeneratorAndResolverImpl(resourceSet)
	var Path testProjectPath

	@BeforeEach
	def void setup(@TestProject Path testProjectPath) {
		this.testProjectPath = testProjectPath
	}

	@Test
	@DisplayName("resolve UUID in parent resolver for object with root container not being its resource root")
	def void parentResolveRootContainerNotResourceRoot() {
		val childResourceSet = new ResourceSetImpl().withGlobalFactories()
		val childUuidGeneratorAndResolver = new UuidGeneratorAndResolverImpl(uuidGeneratorAndResolver, childResourceSet)

		// Model generation
		val nonRoot = aet.NonRoot
		childResourceSet.createResource(URI.createFileURI(testProjectPath.resolve("root.aet").toString)) => [
			contents += aet.Root=> [
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
		val childUuidGeneratorAndResolver = new UuidGeneratorAndResolverImpl(uuidGeneratorAndResolver, childResourceSet)

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
		val childUuidGeneratorAndResolver = new UuidGeneratorAndResolverImpl(uuidGeneratorAndResolver, childResourceSet)

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

}
