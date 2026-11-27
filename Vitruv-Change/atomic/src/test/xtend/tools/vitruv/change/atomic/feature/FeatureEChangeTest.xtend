package tools.vitruv.change.atomic.feature

import allElementTypes.AllElementTypesPackage
import allElementTypes.Root
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.EChangeTest
import tools.vitruv.change.atomic.uuid.Uuid
import tools.vitruv.change.atomic.uuid.UuidResolver

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertThrows
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

/**
 * Test class for {@link FeatureEChange} which is used by every {@link EChange} which modifies {@link EStructuralFeature}s 
 * (single- or multi-valued attributes or references) of a {@link EObject}.
 */
class FeatureEChangeTest extends EChangeTest {
	var Root affectedEObject
	var EAttribute affectedFeature

	// Second model instance
	var UuidResolver uuidResolver2
	var Resource resource2

	@BeforeEach
	def final void beforeTest() {
		affectedEObject = rootObject
		affectedFeature = AllElementTypesPackage.Literals.IDENTIFIED__ID

		// Load model in second resource
		val resourceSet2 = new ResourceSetImpl().withGlobalFactories
		resource2 = resourceSet2.getResource(resource.URI, true)
		uuidResolver2 = UuidResolver.create(resourceSet2)
		resource2.allContents.forEach[uuidResolver2.registerEObject(it)]
	}

	/**
	 * Tests a failed resolve.
	 */
	@Test
	def void resolveEFeatureChangeFails() {
		// Change first resource by insert second root element
		affectedEObject = prepareSecondRoot

		// second resource has no such root element
		assertNull(resource2.getEObject(EcoreUtil.getURI(affectedEObject).fragment))

		// Create change
		val unresolvedChange = createUnresolvedChange()

		// Resolve
		assertThrows(IllegalStateException)[unresolvedChange.applyForwardAndResolve]
	}

	/**
	 * Test whether resolving the EFeatureChange fails by giving a null EObject
	 */
	@Test
	def void resolveEFeatureAffectedObjectNull() {
		affectedEObject = null

		// Create change	
		assertThrows(IllegalArgumentException)[createUnresolvedChange()]
	}

	/**
	 * Tests whether creating the EFeatureChange fails by giving a null EFeature
	 */
	@Test
	def void createEFeatureAffectedFeatureNull() {
		affectedFeature = null	
		assertThrows(IllegalArgumentException)[createUnresolvedChange()]
	}

	/**
	 * Creates and inserts a new root element in the resource 1.
	 */
	def private Root prepareSecondRoot() {
		val root = aet.Root
		resource.contents.add(root)
		return root
	}

	/**
	 * Creates new unresolved change.
	 */
	def private EChange<Uuid> createUnresolvedChange() {
		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
		return atomicFactory.createReplaceSingleAttributeChange(affectedEObject, affectedFeature, null, null).unresolve
	}
}
