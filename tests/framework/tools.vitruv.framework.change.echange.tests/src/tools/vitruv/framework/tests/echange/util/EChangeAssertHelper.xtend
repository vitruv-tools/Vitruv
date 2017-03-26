package tools.vitruv.framework.tests.echange.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Assert
import tools.vitruv.framework.change.echange.EChange

/**
 * Utility class for frequently used assert methods in the tests.
 */
class EChangeAssertHelper {
 	/**
 	 * Tests whether a unresolved change and a resolved change are the same class.
 	 */
 	def public static void assertDifferentChangeSameClass(EChange unresolvedChange, EChange resolvedChange)	 {
 		Assert.assertFalse(unresolvedChange.isResolved)
 		Assert.assertTrue(resolvedChange.isResolved)
 		Assert.assertNotSame(unresolvedChange, resolvedChange)
 		Assert.assertEquals(unresolvedChange.getClass, resolvedChange.getClass)
 	}
 	
 	/**
 	 * Tests whether two objects are the same object or copies of each other.
 	 */
 	def public static void assertEqualsOrCopy(EObject object1, EObject object2) {
		EcoreUtil.equals(object1, object2)
	}
	
	/**
	 * Tests whether a change is resolved and applies it forward.
	 */
	def public static void assertApplyForward(EChange change) {
		Assert.assertNotNull(change)
		Assert.assertTrue(change.isResolved)
		Assert.assertTrue(change.applyForward)
	}
	
	/**
	 * Tests whether a change is resolved and applies it backward.
	 */
	def public static void assertApplyBackward(EChange change) {
		Assert.assertNotNull(change)
		Assert.assertTrue(change.isResolved)
		Assert.assertTrue(change.applyBackward)
	}
}