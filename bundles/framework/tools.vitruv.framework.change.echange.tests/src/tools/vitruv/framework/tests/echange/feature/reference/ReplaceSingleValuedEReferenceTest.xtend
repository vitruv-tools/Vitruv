package tools.vitruv.framework.tests.echange.feature.reference

import tools.vitruv.framework.tests.echange.EChangeTest
import allElementTypes.Root
import org.eclipse.emf.ecore.EReference
import org.junit.Before
import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.AllElementTypesFactory
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import org.junit.Assert
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference

/**
 * Test class for the concrete {@link ReplaceSingleValuedEReference} EChange,
 * which replaces the value of a reference with a new one.
 */
public class ReplaceSingleValuedEReferenceTest extends EChangeTest {
	protected var Root defaultAffectedEObject = null
	protected var EReference defaultAffectedFeature = null
	protected var NonRoot defaultOldValue = null
	protected var NonRoot defaultNewValue = null
	
	protected static val DEFAULT_NEW_NON_ROOT_NAME = "New Non Root Element"
		
	/**
	 * Sets the default object, feature and values for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		defaultAffectedEObject = rootObject1
		defaultAffectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE
		defaultOldValue = rootObject1.singleValuedNonContainmentEReference
		defaultNewValue = AllElementTypesFactory.eINSTANCE.createNonRoot()
		defaultNewValue.id = DEFAULT_NEW_NON_ROOT_NAME
		resource1.getContents.add(defaultNewValue)
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
		
		val resolvedChange = unresolvedChange.resolve(resourceSet1) as ReplaceSingleValuedEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.oldValue == defaultOldValue)
		Assert.assertTrue(resolvedChange.newValue == defaultNewValue)
	}
	
	/**
	 * Test resolves {@link ReplaceSingleValuedEReference} EChanges with invalid parameters.
	 */
	@Test
	def public void resolveReplaceSingleValuedEReferenceInvalidParametersTest() {
		// resolving affectedEObject, affectedFeature and with 
		// invalid parameters and resource set is tested in FeatureEChangeTest
		// TODO
	}
	
	/**
	 * Tests whether resolving the {@link ReplaceSingleValuedEReference} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, true)	
			
		val resolvedChange = unresolvedChange.resolve(resourceSet1)
		
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