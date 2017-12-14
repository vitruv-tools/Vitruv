package tools.vitruv.framework.tests.echange.feature

import allElementTypes.AllElementTypesFactory
import allElementTypes.AllElementTypesPackage
import allElementTypes.Root
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.tests.echange.EChangeTest
import org.junit.After
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.change.uuid.UuidResolver
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolverImpl

/**
 * Test class for {@link FeatureEChange} which is used by every {@link EChange} which modifies {@link EStructuralFeature}s 
 * (single- or multi-valued attributes or references) of a {@link EObject}.
 */
 public class FeatureEChangeTest extends EChangeTest {
 	protected var Root affectedEObject = null
 	protected var EAttribute affectedFeature = null
 	
 	// Second model instance
 	protected var Root rootObject2 = null
 	protected var Resource resource2 = null
 	protected var ResourceSet resourceSet2 = null
 	protected var UuidResolver uuidResolver2;
 	
 	@Before
 	override public void beforeTest() {
 		super.beforeTest()
 		affectedEObject = rootObject
 		affectedFeature = AllElementTypesPackage.Literals.IDENTIFIED__ID
 		
 		// Load model in second resource
 		resourceSet2 = new ResourceSetImpl()
 		resourceSet2.getResourceFactoryRegistry().getExtensionToFactoryMap().put(METAMODEL, new XMIResourceFactoryImpl())
 		resource2 = resourceSet2.getResource(fileUri, true)
 		rootObject2 = resource2.getEObject(EcoreUtil.getURI(rootObject).fragment()) as Root
 		this.uuidResolver2 = new UuidGeneratorAndResolverImpl(resourceSet2, true);
 	}
 	
 	@After
 	override public void afterTest() {
 		super.afterTest()
 	}
	 
 	/**
 	 * Tests if a feature change, which affected object and feature references 
 	 * to the changed model instance, resolved correctly to the same model instance, 
 	 * after unresolving the object. 
 	 */
 	@Test
 	def public void resolveBeforeTest() {
		// Create change 		
 		val unresolvedChange = createUnresolvedChange()
 		unresolvedChange.assertIsNotResolved(affectedEObject, affectedFeature)
 					
 		// Resolve
 		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
 			as FeatureEChange<Root, EAttribute>
 		resolvedChange.assertIsResolved(affectedEObject, affectedFeature)
 	}
 	
	/**
	 * Tests a failed resolve.
	 */
	@Test
	def public void resolveEFeatureChangeFails() {
		// Change first resource by insert second root element
		affectedEObject = prepareSecondRoot
		
		// second resource has no such root element
		Assert.assertNull(resource2.getEObject(EcoreUtil.getURI(affectedEObject).fragment))
		
		// Create change 		
 		val unresolvedChange = createUnresolvedChange()
 		unresolvedChange.assertIsNotResolved(affectedEObject, affectedFeature)
			
  		// Resolve
 		val resolvedChange = unresolvedChange.resolveBefore(uuidResolver2)
 			as FeatureEChange<Root, EAttribute>
		Assert.assertNull(resolvedChange)
	}

	/**
	 * Tests whether resolving an already resolved EFeatureChange throws an exception.
	 */
	@Test(expected=IllegalArgumentException)
	def public void resolveResolvedEFeatureChange() {
		// Create change and resolve	
 		val resolvedChange = createUnresolvedChange().resolveBefore(uuidGeneratorAndResolver)
 			as FeatureEChange<Root, EAttribute>
 		resolvedChange.assertIsResolved(affectedEObject, affectedFeature)
		
		// Resolve again
		resolvedChange.resolveBefore(uuidGeneratorAndResolver)

	}
 	
 	/**
 	 * Test whether resolving the EFeatureChange fails by giving a null EObject
 	 */
 	@Test(expected=IllegalStateException)
 	def public void resolveEFeatureAffectedObjectNull() {
 		affectedEObject = null
 		
		// Create change	
 		createUnresolvedChange()
// 		Assert.assertFalse(unresolvedChange.isResolved)
// 		Assert.assertNull(unresolvedChange.affectedEObject)
// 		
// 		// Resolve		
// 		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver) 
// 			as FeatureEChange<Root, EAttribute>
//		Assert.assertNull(resolvedChange)			
 	}
 	
 	/**
 	 * Tests whether resolving the EFeatureChange fails by giving a null EFeature
 	 */
 	 @Test
 	 def public void resolveEFeatureAffectedFeatureNull() {
 	 	affectedFeature = null
 	 	
		// Create change	
 		val unresolvedChange = createUnresolvedChange()
 		unresolvedChange.assertIsNotResolved(affectedEObject, null)	
 		
 		// Resolve
 		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver) 
 			as FeatureEChange<Root, EAttribute>
 	  	Assert.assertNull(resolvedChange)
 	 }
 	 
 	 /**
 	  * Tests whether resolving the EFeatureChange fails by giving a null uuidGeneratorAndResolver
 	  */
 	  @Test
 	  def public void resolveEFeatureuuidGeneratorAndResolverNull() {
		// Create change	
 		val unresolvedChange = createUnresolvedChange()	
 		unresolvedChange.assertIsNotResolved(affectedEObject, affectedFeature)	
 	  	
 	  	// Resolve
 	  	val resolvedChange = unresolvedChange.resolveBefore(null) 
 	  		as FeatureEChange<Root, EAttribute>
 	  	Assert.assertNull(resolvedChange)
 	}
 	  
 	
	/**
	 * Creates and inserts a new root element in the resource 1.
	 */
	def private Root prepareSecondRoot() {
		val root = AllElementTypesFactory.eINSTANCE.createRoot()
		resource.contents.add(root)
		return root		
	}
	
	/**
	 * Change is not resolved.
	 */ 	
	def private static void assertIsNotResolved(FeatureEChange<Root, EAttribute> change, 
		Root affectedEObject, EAttribute affectedFeature) {
		Assert.assertFalse(change.isResolved)
		Assert.assertNotSame(change.affectedEObject, affectedEObject)
		Assert.assertSame(change.affectedFeature, affectedFeature)				
	} 
	
	/**
	 * Change is resolved.
	 */
 	def private static void assertIsResolved(FeatureEChange<Root, EAttribute> change, 
		Root affectedEObject, EAttribute affectedFeature) {
		Assert.assertTrue(change.isResolved)
		Assert.assertSame(change.affectedEObject, affectedEObject)
		Assert.assertSame(change.affectedFeature, affectedFeature)			
	} 

	/**
	 * Creates new unresolved change.
	 */
	def private FeatureEChange<Root, EAttribute> createUnresolvedChange() {
		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
		return atomicFactory.createReplaceSingleAttributeChange(affectedEObject, affectedFeature, null, null)	
	}	 
 }