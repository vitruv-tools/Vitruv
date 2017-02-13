package tools.vitruv.framework.tests.echange.feature

import allElementTypes.AllElementTypesPackage
import allElementTypes.Root
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.tests.echange.EChangeTest
import org.junit.Before
import allElementTypes.AllElementTypesFactory
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * Test class for {@link FeatureEChange} which is used by every {@link EChange} which modifies {@link EStructuralFeature}s 
 * (single- or multi-valued attributes or references) of a {@link EObject}.
 */
 public class FeatureEChangeTest extends EChangeTest {
 	protected var Root defaultAffectedEObject = null
 	protected var EAttribute defaultAffectedFeature = null
 	
 	@Before
 	override public void beforeTest() {
 		super.beforeTest()
 		defaultAffectedEObject = rootObject1
 		defaultAffectedFeature = AllElementTypesPackage.Literals.IDENTIFIED__ID
 	}
 	
 	/**
 	 * Tests if a feature change, which affected object and feature references 
 	 * to the changed model instance, resolved correctly to the same model instance, 
 	 * after unresolving the object. 
 	 */
 	@Test
 	def public void resolveEFeatureChangeTest() {
 		Assert.assertTrue(rootObject1 == defaultAffectedEObject)
 		
 		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
 		val unresolvedChange = TypeInferringAtomicEChangeFactory.
 			<Root, String>createReplaceSingleAttributeChange(defaultAffectedEObject, defaultAffectedFeature, null, null, true)
 			
 		Assert.assertFalse(unresolvedChange.isResolved())
 		Assert.assertTrue(rootObject1 != unresolvedChange.affectedEObject)
 		
 		val resolvedChange = unresolvedChange.resolve(resourceSet1) as FeatureEChange<Root, EAttribute>
 		
 		Assert.assertFalse(unresolvedChange.isResolved())
 		Assert.assertTrue(rootObject1 != unresolvedChange.affectedEObject)
 		
  		Assert.assertNotNull(resolvedChange)		
 		Assert.assertTrue(resolvedChange.isResolved())
 		Assert.assertTrue(rootObject1 == resolvedChange.affectedEObject)
 		Assert.assertTrue(unresolvedChange.affectedEObject != resolvedChange.affectedEObject)
 	}
 	
 	/**
 	 * Tests if a feature change, which affected object and feature references 
 	 * to the changed model instance, resolved correctly to another model instance,
 	 * after unresolving the object.
 	 */
 	@Test
 	def public void resolveEFeatureChangeTest2() {
 		Assert.assertTrue(rootObject1 == defaultAffectedEObject)
 		Assert.assertTrue(rootObject1 != rootObject2)
 		
 		val unresolvedChange = TypeInferringAtomicEChangeFactory.
 			<Root, String>createReplaceSingleAttributeChange(defaultAffectedEObject, defaultAffectedFeature, null, null, true)
 			
 		Assert.assertTrue(rootObject1 != unresolvedChange.affectedEObject)
 		Assert.assertTrue(rootObject2 != unresolvedChange.affectedEObject)
 		
 		val resolvedChange = unresolvedChange.resolve(resourceSet2) as FeatureEChange<Root, EAttribute>
 		
 		Assert.assertTrue(resolvedChange.isResolved)
 		Assert.assertTrue(rootObject1 != resolvedChange.affectedEObject)
 		Assert.assertTrue(rootObject2 == resolvedChange.affectedEObject)
 		
 		
 	}

	/**
	 * Tests a failed resolve.
	 */
	@Test
	def public void resolveEFeatureChangeFails() {
		// create second Root element and place it in the first resource
		val root = AllElementTypesFactory.eINSTANCE.createRoot()
		root.id = DEFAULT_ROOT_NAME
		resource1.contents.add(root)
		
		// second resource has no such root element
		Assert.assertNull(resource2.getEObject(EcoreUtil.getURI(root).fragment))
		
		val affectedEObject = root
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, String>createReplaceSingleAttributeChange(affectedEObject, defaultAffectedFeature, null, null, true) 
			
		Assert.assertFalse(unresolvedChange.isResolved)
		
		val resolvedChange = unresolvedChange.resolve(resourceSet2)
		
		Assert.assertFalse(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange == resolvedChange)
	}

	/**
	 * Tests whether resolving an already resolved EFeatureChange returns itself
	 */
	@Test
	def public void resolveResolvedEFeatureChange() {
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, String>createReplaceSingleAttributeChange(defaultAffectedEObject, defaultAffectedFeature, null, null, false)
			
		Assert.assertTrue(resolvedChange.isResolved)
		
		val resolvedChange2 = resolvedChange.resolve(resourceSet1)
		
		Assert.assertTrue(resolvedChange2.isResolved)
		Assert.assertTrue(resolvedChange == resolvedChange2)
	}
 	
 	/**
 	 * Test whether resolving the EFeatureChange fails by giving a null EObject
 	 */
 	@Test
 	def public void resolveEFeatureAffectedObjectNull() {
 		val affectedEObject = null
 		
 		val unresolvedChange = TypeInferringAtomicEChangeFactory.
 			<Root, String>createReplaceSingleAttributeChange(affectedEObject, defaultAffectedFeature, null, null, true)
 			
  		Assert.assertNotNull(unresolvedChange)
 		Assert.assertNull(unresolvedChange.getAffectedEObject)
 		Assert.assertFalse(unresolvedChange.isResolved)
 					
 		val resolvedChange = unresolvedChange.resolve(resourceSet1) as FeatureEChange<Root, EAttribute>

		Assert.assertNull(resolvedChange)			
 	}
 	
 	/**
 	 * Tests whether resolving the EFeatureChange fails by giving a null EFeature
 	 */
 	 @Test
 	 def public void resolveEFeatureAffectedFeatureNull() {
 	 	val EAttribute affectedFeature = null
 	 	
 		val unresolvedChange = TypeInferringAtomicEChangeFactory.
 			<Root, String>createReplaceSingleAttributeChange(defaultAffectedEObject, affectedFeature, null, null, true)
 			
 		Assert.assertNotNull(unresolvedChange)
 		Assert.assertNull(unresolvedChange.getAffectedFeature)
 		Assert.assertFalse(unresolvedChange.isResolved)
 		
 		val resolvedChange = unresolvedChange.resolve(resourceSet1) as FeatureEChange<Root, EAttribute>
 		
 		Assert.assertNull(resolvedChange)
 	 }
 	 
 	 /**
 	  * Tests whether resolving the EFeatureChange fails by giving a null ResourceSet
 	  */
 	  @Test
 	  def public void resolveEFeatureResourceSetNull() {
 	  	
 	  	val unresolvedChange = TypeInferringAtomicEChangeFactory.
 	  		<Root, String>createReplaceSingleAttributeChange(defaultAffectedEObject, defaultAffectedFeature, null, null, true)
 	  		
 	  	Assert.assertNotNull(unresolvedChange)
 	  	Assert.assertFalse(unresolvedChange.isResolved)
 	  	
 	  	val resolvedChange = unresolvedChange.resolve(null) as FeatureEChange<Root, EAttribute>
 	  	
 	  	Assert.assertNull(resolvedChange)
 	  }
 }