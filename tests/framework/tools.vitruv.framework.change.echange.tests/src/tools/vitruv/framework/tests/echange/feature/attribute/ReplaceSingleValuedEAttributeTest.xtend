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

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*

/**
 * Test class for the concrete {@link ReplaceSingleValuedEAttribute} EChange, 
 * which replaces the value of an attribute with a new one.
 */
class ReplaceSingleValuedEAttributeTest extends EChangeTest {
	protected var Root affectedEObject = null
 	protected var EAttribute affectedFeature = null
 	protected var String oldValue = null
 	protected var String newValue = null
 
  	protected static val DEFAULT_ROOT_NAME = "Root Element"
  	protected static val DEFAULT_SINGLE_VALUED_EATTRIBUTE_VALUE = 123
  	
 	@Before
 	override void beforeTest() {
 		super.beforeTest()
 		affectedEObject = rootObject
 		affectedFeature = AllElementTypesPackage.Literals.IDENTIFIED__ID
 		oldValue = DEFAULT_ROOT_NAME
 		newValue = "New Root ID"
 		prepareStateBefore
 	}
		
	/**
	 * Tests whether resolving the {@link ReplaceSingleValuedEAttribute} EChange returns 
	 * the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange()
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests applying a {@link ReplaceSingleValuedEAttribute} EChange forward
	 * by replacing a single value in a root element with a new value.
	 */
	 @Test
	 def void applyForwardTest() {
		// Create change
		val resolvedChange = createUnresolvedChange().resolveBefore(uuidGeneratorAndResolver)
			as ReplaceSingleValuedEAttribute<Root, String>
	 	
	 	// Apply forward
	 	resolvedChange.assertApplyForward
	 	
	 	// State after
	 	assertIsStateAfter
	 }
	 
	 /**
	  * Tests applying a {@link ReplaceSingleValuedEAttribute} EChange backward
	  * by replacing a single value in a root element with the old value.
	  */
	 @Test
	 def void applyBackwardTest() {
		// Create change
		val resolvedChange = createUnresolvedChange().resolveBefore(uuidGeneratorAndResolver)
			as ReplaceSingleValuedEAttribute<Root, String>
			
		// Set state after
		prepareStateAfter
	 	
	 	// Apply backward
	 	resolvedChange.assertApplyBackward
	 	
	 	// State before
	 	assertIsStateBefore
	 }
	 
	 /**
	  * Tests an affected object which has no such attribute.
	  */
	 @Test
	 def void invalidAttributeTest() {
	 	// NonRoot element has no int attribute.
	 	val affectedNonRootEObject = AllElementTypesFactory.eINSTANCE.createNonRoot()
	 	resource.contents.add(affectedNonRootEObject)
	 	val affectedRootFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_EATTRIBUTE
	 	val oldIntValue = DEFAULT_SINGLE_VALUED_EATTRIBUTE_VALUE
	 	val newIntValue = 500
	 	
	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = atomicFactory.<NonRoot, Integer>createReplaceSingleAttributeChange
	 	(affectedNonRootEObject, affectedRootFeature, oldIntValue, newIntValue).resolveBefore(uuidGeneratorAndResolver)
	 	
	 	// NonRoot has no such feature
	 	Assert.assertEquals(affectedNonRootEObject.eClass.getFeatureID(affectedRootFeature), -1)	
	 	
	 	// Apply
	 	resolvedChange.assertCannotBeAppliedForward	 	
		resolvedChange.assertCannotBeAppliedBackward
	 }
	 
	 /**
	  * Tests a {@link ReplaceSingleValuedEAttribue} EChange with the wrong value type.
	  */
	 @Test
	 def void invalidValueTest() {
	 	val oldIntValue = DEFAULT_SINGLE_VALUED_EATTRIBUTE_VALUE // values are Integer, attribute value type is String
	 	val newIntValue = 500
	 	
	 	// Create and resolve change
	 	val resolvedChange = atomicFactory.<Root, Integer>createReplaceSingleAttributeChange
	 		(affectedEObject, affectedFeature, oldIntValue, newIntValue).
	 		resolveBefore(uuidGeneratorAndResolver)
	 	Assert.assertTrue(resolvedChange.isResolved)
	 		
	 	// Type of attribute is String not Integer
	 	Assert.assertEquals(affectedFeature.EAttributeType.name, "EString")
	 	
	 	// Apply
	 	resolvedChange.assertCannotBeAppliedForward	 	
		resolvedChange.assertCannotBeAppliedBackward
	}
	 
		 
	/**
	 * Set state before the change
	 */
	def private void prepareStateBefore() {
  		rootObject.eSet(affectedFeature, oldValue)
  		assertIsStateBefore 	
	}
	 
	/**
	 * Set state after the change
	 */
	def private void prepareStateAfter() {
  		rootObject.setId(newValue)
  		assertIsStateAfter 	
	}
	
	/** 
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		Assert.assertEquals(affectedEObject.eGet(affectedFeature), oldValue)
	}
	
	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		Assert.assertEquals(affectedEObject.eGet(affectedFeature), newValue)
	}
	 
	/**
	 * Creates new unresolved change.
	 */
	def private ReplaceSingleValuedEAttribute<Root, String> createUnresolvedChange() {
		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
		return atomicFactory.createReplaceSingleAttributeChange(affectedEObject, affectedFeature, oldValue, newValue)	
	}
}