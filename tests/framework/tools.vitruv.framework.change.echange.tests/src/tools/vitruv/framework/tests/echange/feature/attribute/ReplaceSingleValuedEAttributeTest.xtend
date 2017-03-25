package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.AllElementTypesFactory
import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EAttribute
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.tests.echange.EChangeTest

/**
 * Test class for the concrete {@link ReplaceSingleValuedEAttribute} EChange, 
 * which replaces the value of an attribute with a new one.
 */
public class ReplaceSingleValuedEAttributeTest extends EChangeTest {
	protected var Root affectedEObject = null
 	protected var EAttribute affectedFeature = null
 	protected var String oldValue = null
 	protected var String newValue = null
 
  	protected static val DEFAULT_ROOT_NAME = "Root Element"	
  	protected static val DEFAULT_SINGLE_VALUED_EATTRIBUTE_VALUE = 100
  	
 	@Before
 	override public void beforeTest() {
 		super.beforeTest()
 		affectedEObject = rootObject
 		affectedFeature = AllElementTypesPackage.Literals.IDENTIFIED__ID
 		oldValue = DEFAULT_ROOT_NAME
 		newValue = "New Root ID"
 	}
		
	/**
	 * Tests whether resolving the {@link ReplaceSingleValuedEAttribute} EChange returns 
	 * the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Set state before
		prepareStateBefore
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests applying a {@link ReplaceSingleValuedEAttribute} EChange forward
	 * by replacing a single value in a root element with a new value.
	 */
	 @Test
	 def public void replaceSingleValuedEAttributeApplyForwardTest() {
	 	// Set state before
		prepareStateBefore
		
		// Create change
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet)
			as ReplaceSingleValuedEAttribute<Root, String>
	 		
	 	Assert.assertEquals(affectedEObject.id, oldValue)
	 	
	 	// Apply forward
	 	Assert.assertTrue(resolvedChange.applyForward)
	 	
	 	Assert.assertEquals(affectedEObject.id, newValue)
	 }
	 
	 /**
	  * Tests applying a {@link ReplaceSingleValuedEAttribute} EChange backward
	  * by replacing a single value in a root element with the old value.
	  */
	 @Test
	 def public void replaceSingleValuedEAttributeApplyBackwardTest() {
	 	// Set state before
		prepareStateBefore
		
		// Create change
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet)
			as ReplaceSingleValuedEAttribute<Root, String>
			
		// Set state after
		prepareStateAfter
		
	 	Assert.assertEquals(affectedEObject.id, newValue)
	 	
	 	// Apply backward
	 	Assert.assertTrue(resolvedChange.applyBackward)
	 	
	 	Assert.assertEquals(affectedEObject.id, oldValue)
	 }
	 
	 /**
	  * Tests an affected object which has no such attribute.
	  */
	 @Test
	 def public void replaceSingleValuedEAttributeInvalidAttribute() {
	 	// NonRoot element has no int attribute.
	 	val affectedNonRootEObject = AllElementTypesFactory.eINSTANCE.createNonRoot()
	 	resource.contents.add(affectedNonRootEObject)
	 	val affectedRootFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_EATTRIBUTE
	 	val oldIntValue = DEFAULT_SINGLE_VALUED_EATTRIBUTE_VALUE
	 	val newIntValue = 500
	 	
	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = atomicFactory.<NonRoot, Integer>createReplaceSingleAttributeChange
	 	(affectedNonRootEObject, affectedRootFeature, oldIntValue, newIntValue)
	 	
	 	// NonRoot has no such feature
	 	Assert.assertEquals(affectedNonRootEObject.eClass.getFeatureID(affectedRootFeature), -1)	
	 	
	 	Assert.assertFalse(resolvedChange.applyForward)
	 	Assert.assertFalse(resolvedChange.applyBackward)
	 }
	 
	 /**
	  * Tests a {@link ReplaceSingleValuedEAttribue} EChange with the wrong value type.
	  */
	 @Test
	 def public void replaceSingleValuedEAttributeInvalidValue() {
	 	val oldIntValue = DEFAULT_SINGLE_VALUED_EATTRIBUTE_VALUE // values are Integer, attribute value type is String
	 	val newIntValue = 500
	 	
	 	// Create and resolve change
	 	val resolvedChange = atomicFactory.<Root, Integer>createReplaceSingleAttributeChange
	 		(affectedEObject, affectedFeature, oldIntValue, newIntValue).
	 		resolveBefore(resourceSet)
	 	Assert.assertTrue(resolvedChange.isResolved)
	 		
	 	// Type of attribute is String not Integer
	 	Assert.assertTrue(affectedFeature.EAttributeType.name == "EString")
	 	
	 	Assert.assertFalse(resolvedChange.applyForward)
	 	Assert.assertFalse(resolvedChange.applyBackward)
	}
	 
		 
	/**
	 * Set state before the change
	 */
	def private void prepareStateBefore() {
  		rootObject.setId(oldValue)	 	
	}
	 
	/**
	 * Set state after the change
	 */
	def private void prepareStateAfter() {
  		rootObject.setId(newValue)	 	
	}
	 
	/**
	 * Creates new unresolved change.
	 */
	def private ReplaceSingleValuedEAttribute<Root, String> createUnresolvedChange() {
		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
		return atomicFactory.<Root, String>createReplaceSingleAttributeChange
		(affectedEObject, affectedFeature, oldValue, newValue)	
	}
}