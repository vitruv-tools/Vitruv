package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.Root
import org.eclipse.emf.ecore.EAttribute
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import org.junit.Assert

/**
 * Test class for the concrete {@link RemoveEAttributeValue} EChange,
 * which removes a value in a multivalued attribute.
 */
public class RemoveEAttributeValueTest extends InsertRemoveEAttributeTest {
	/**
	 * Inserts values in the default feature 
	 * so they can be removed by the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()


	}
	
	/**
	 * Tests whether resolving the {@link RemoveEAttributeValue} EChange
	 * returns the same class. 
	 */
	@Test
	def public void resolveToCorrectType() {
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
 			<Root, Integer>createInsertAttributeChange(defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE, true)
 			
 		val resolvedChange = unresolvedChange.resolve(resourceSet1)
 		
 		Assert.assertTrue(resolvedChange.isResolved)
 		Assert.assertTrue(unresolvedChange != resolvedChange)
 		Assert.assertEquals(unresolvedChange.getClass(), resolvedChange.getClass())
	}
}