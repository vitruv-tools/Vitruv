package tools.vitruv.framework.tests.echange.feature.attribute

import tools.vitruv.framework.tests.echange.EChangeTest
import org.junit.Test
import allElementTypes.AllElementTypesPackage
import allElementTypes.Root
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import org.junit.Assert
import org.eclipse.emf.ecore.EAttribute
import org.junit.Before
import allElementTypes.NonRoot

/**
 * Test class for the concrete {@link ReplaceSingleValuedEAttribute} EChange, 
 * which replaces the value of an attribute with a new one.
 */
public class ReplaceSingleValuedEAttributeTest extends EChangeTest {
	protected var Root defaultAffectedEObject = null
 	protected var EAttribute defaultAffectedFeature = null
 	protected var String defaultOldValue = null
 	protected var String defaultNewValue = null
 	
 	/**
 	 * Sets the default object, feature and values for the tests.
 	 */
 	@Before
 	override public void beforeTest() {
 		super.beforeTest()
 		defaultAffectedEObject = rootObject1
 		defaultAffectedFeature = AllElementTypesPackage.Literals.IDENTIFIED__ID
 		defaultOldValue = DEFAULT_ROOT_NAME
 		defaultNewValue = "New Root ID"
 	}
 	
	/**
	 * Tests whether resolving the {@link ReplaceSingleValuedEAttribute} EChange returns 
	 * the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
 			<Root, String>createReplaceSingleAttributeChange(defaultAffectedEObject, defaultAffectedFeature, null, null, true)
 		
 		val resolvedChange = unresolvedChange.resolve(resourceSet1)
 		
 		Assert.assertTrue(resolvedChange.isResolved)
 		Assert.assertTrue(unresolvedChange != resolvedChange)
 		Assert.assertEquals(unresolvedChange.getClass,resolvedChange.getClass)
	}
	
	/**
	 * Tests replacing a single value in the root element.
	 */
	 @Test
	 def public void replaceSingleValuedEAttributeApplyTest() {
	 	
	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = TypeInferringAtomicEChangeFactory.
	 		<Root, String>createReplaceSingleAttributeChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, false)
	 		
	 	Assert.assertTrue(resolvedChange.isResolved)
	 	Assert.assertEquals(defaultAffectedEObject.id, defaultOldValue)
	 	Assert.assertNotEquals(defaultAffectedEObject.id, defaultNewValue)
	 	
	 	Assert.assertTrue(resolvedChange.apply)
	 	
	 	Assert.assertNotEquals(defaultAffectedEObject.id, defaultOldValue)
	 	Assert.assertEquals(defaultAffectedEObject.id, defaultNewValue)
	 }
	 
	 /**
	  * Reverts a {@link ReplaceSingleValuedEAttribute} EChange.
	  */
	 @Test
	 def public void replaceSingleValuedEAttributeRevertTest() {
	 	defaultAffectedEObject.eSet(defaultAffectedFeature, defaultNewValue)
	 	
	 		 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = TypeInferringAtomicEChangeFactory.
	 		<Root, String>createReplaceSingleAttributeChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, false)
	 		
	 	Assert.assertTrue(resolvedChange.isResolved)
	 	Assert.assertNotEquals(defaultAffectedEObject.id, defaultOldValue)
	 	Assert.assertEquals(defaultAffectedEObject.id, defaultNewValue)
	 	
	 	Assert.assertTrue(resolvedChange.revert)
	 	
	 	Assert.assertEquals(defaultAffectedEObject.id, defaultOldValue)
	 	Assert.assertNotEquals(defaultAffectedEObject.id, defaultNewValue)
	 }
	 
	 /**
	  * Tests an affected object which has no such attribute.
	  */
	 @Test
	 def public void replaceSingleValuedEAttributeInvalidAttribute() {
	 	val affectedEObject = rootObject1.singleValuedNonContainmentEReference // NonRoot element
	 	val affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_EATTRIBUTE // Attribute of Root element
	 	val oldValue = DEFAULT_SINGLE_VALUED_EATTRIBUTE_VALUE
	 	val newValue = 500
	 	
	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = TypeInferringAtomicEChangeFactory.
	 		<NonRoot, Integer>createReplaceSingleAttributeChange(affectedEObject, affectedFeature, oldValue, newValue, false)
	 	
	 	// NonRoot has no such feature
	 	Assert.assertTrue(affectedEObject.eClass.getFeatureID(affectedFeature) == -1)	
	 	
	 	Assert.assertFalse(resolvedChange.apply)
	 	Assert.assertFalse(resolvedChange.revert)
	 }
	 
	 /**
	  * Tests a {@link ReplaceSingleValuedEAttribue} EChange with the wrong value type.
	  */
	 @Test
	 def public void replaceSingleValuedEAttributeInvalidValue() {
	 	val oldValue = DEFAULT_SINGLE_VALUED_EATTRIBUTE_VALUE // values are Integer, attribute value type is String
	 	val newValue = 500
	 	
	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = TypeInferringAtomicEChangeFactory.
	 		<Root, Integer>createReplaceSingleAttributeChange(defaultAffectedEObject, defaultAffectedFeature, oldValue, newValue, false)
	 		
	 	// Type of attribute is String not Integer
	 	Assert.assertTrue(defaultAffectedFeature.EAttributeType.name == "EString")
	 	
	 	Assert.assertFalse(resolvedChange.apply)
	 	Assert.assertFalse(resolvedChange.revert)
	 }
}