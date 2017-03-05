package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.NonRoot
import allElementTypes.Root
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import org.junit.Before
import org.eclipse.emf.ecore.EReference
import allElementTypes.AllElementTypesPackage

/**
 * Test class for the concrete {@link ReplaceSingleValuedEReference} EChange,
 * which replaces the value of a reference with a new one.
 */
public class ReplaceSingleValuedEReferenceTest extends ReferenceEChangeTest {	
	protected var NonRoot defaultOldValue = null	
	protected var EReference defaultAffectedFeature = null
	
	/**
	 * Sets the default affected feature and old value for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		defaultAffectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE
		defaultOldValue = rootObject1.singleValuedNonContainmentEReference
	}
	
	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 */
	@Test
	def public void resolveReplaceSingleValuedEReferenceTest() {
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.oldValue != defaultOldValue)
		Assert.assertTrue(unresolvedChange.newValue != defaultNewValue)
		
		val resolvedChange = unresolvedChange.resolveApply(resourceSet1) as ReplaceSingleValuedEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.oldValue == defaultOldValue)
		Assert.assertTrue(resolvedChange.newValue == defaultNewValue)
	}
	
	/**
	 * Tests whether resolving the {@link ReplaceSingleValuedEReference} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, true)	
			
		val resolvedChange = unresolvedChange.resolveApply(resourceSet1)
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertEquals(unresolvedChange.getClass, resolvedChange.getClass)
	}
	
	/**
	 * Tests replacing a single value reference in the root element.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyTest() {
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, false)	
			
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(defaultOldValue != defaultNewValue)
		Assert.assertTrue(defaultAffectedEObject.eGet(defaultAffectedFeature) == defaultOldValue)
		
		Assert.assertTrue(resolvedChange.apply)
		
		Assert.assertTrue(defaultAffectedEObject.eGet(defaultAffectedFeature) == defaultNewValue)
	}
	
	/**
	 * Reverts a {@link ReplaceSingleValuedEReference} EChange.
	 */
	@Test
	def public void replaceSingleValuedEReferenceRevertTest() {
		defaultAffectedEObject.singleValuedNonContainmentEReference = defaultNewValue
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, false)	
			
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(defaultOldValue != defaultNewValue)
		Assert.assertTrue(defaultAffectedEObject.eGet(defaultAffectedFeature) == defaultNewValue)
		
		Assert.assertTrue(resolvedChange.revert)
		
		Assert.assertTrue(defaultAffectedEObject.eGet(defaultAffectedFeature) == defaultOldValue)
		
	}
}