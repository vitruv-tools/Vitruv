package tools.vitruv.framework.tests.echange.integration

import org.junit.Test

import static allElementTypes.AllElementTypesPackage.Literals.*
import org.junit.Assert

/**
 * Test class for integration tests of the EChange and Change package.
 * All EChanges which changes EAttributes will be tested.
 */
class EAttributeChangeIntegrationTest extends IntegrationTest {
	
	/**
	 * Tests all EAttribute changes.
	 */
	@Test
	def public TestEAttributeChanges() {
		// State before
		assertIsStateBefore
		
		// Create changes
		addReplaceEAttributeChange(ROOT__SINGLE_VALUED_EATTRIBUTE, rootObject.singleValuedEAttribute, 5)
		addInsertEAttributeValueChange(ROOT__MULTI_VALUED_EATTRIBUTE, 0, 4)
		addInsertEAttributeValueChange(ROOT__MULTI_VALUED_EATTRIBUTE, 1, 16)
		addInsertEAttributeValueChange(ROOT__MULTI_VALUED_EATTRIBUTE, 2, 64)
		addReplaceEAttributeChange(ROOT__SINGLE_VALUED_EATTRIBUTE, 5, 10)
		addInsertEAttributeValueChange(ROOT__MULTI_VALUED_EATTRIBUTE, 3, 256)
		addRemoveEAttributeValueChange(ROOT__MULTI_VALUED_EATTRIBUTE, 2, 64)
		addRemoveEAttributeValueChange(ROOT__MULTI_VALUED_EATTRIBUTE, 2, 256)
		addReplaceEAttributeChange(ROOT__SINGLE_VALUED_EATTRIBUTE, 10, 15)
		addInsertEAttributeValueChange(ROOT__MULTI_VALUED_EATTRIBUTE, 1, 854)
				
		// Record
		startRecording
		resolveAndApplyChanges
		endRecording
		
		resolveAndRevertChanges
		assertAppliedAreRecordedChanges
		
		// State before
		assertIsStateBefore
	}
	
	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		Assert.assertEquals(rootObject.multiValuedEAttribute.size, 0)
		Assert.assertEquals(rootObject.singleValuedEAttribute, 0)
	}
}