package tools.vitruv.framework.tests.echange.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.EChange
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*
import java.util.List
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNotSame
import static org.junit.jupiter.api.Assertions.fail

/**
 * Utility class for frequently used assert methods in the tests.
 */
class EChangeAssertHelper {
	/**
	 * Tests whether a unresolved change and a resolved change are the same class.
	 */
	def static void assertDifferentChangeSameClass(EChange unresolvedChange, EChange resolvedChange) {
		assertFalse(unresolvedChange.isResolved)
		assertTrue(resolvedChange.isResolved)
		assertNotSame(unresolvedChange, resolvedChange)
		assertEquals(unresolvedChange.getClass, resolvedChange.getClass)
	}

	/**
	 * Tests whether a unresolved changes and a resolved changes are the same classes.
	 */
	def static void assertDifferentChangeSameClass(List<? extends EChange> unresolvedChange,
		List<? extends EChange> resolvedChange) {
		assertEquals(unresolvedChange.size, resolvedChange.size)
		for (var i = 0; i < unresolvedChange.size; i++) {
			assertDifferentChangeSameClass(unresolvedChange.get(i), resolvedChange.get(i))
		}
	}

	/**
	 * Tests whether two objects are the same object or copies of each other.
	 */
	def static void assertEqualsOrCopy(Object object1, Object object2) {
		if (object1 === null && object2 === null) {
			return;
		}
		val typedObject1 = assertType(object1, EObject);
		val typedObject2 = assertType(object2, EObject);
		EcoreUtil.equals(typedObject1, typedObject2)
	}

	/**
	 * Tests whether a change is resolved and applies it forward.
	 */
	def static void assertApplyForward(EChange change) {
		assertNotNull(change)
		assertTrue(change.isResolved)
		assertTrue(change.applyForward)
	}

	/**
	 * Tests whether a change sequence is resolved and applies it forward.
	 */
	def static void assertApplyForward(List<EChange> change) {
		change.forEach[assertApplyForward]
	}

	/**
	 * Tests whether a change is resolved and applies it backward.
	 */
	def static void assertApplyBackward(EChange change) {
		assertNotNull(change)
		assertTrue(change.isResolved)
		assertTrue(change.applyBackward)
	}

	/**
	 * Tests whether a change sequence is resolved and applies it backward.
	 */
	def static void assertApplyBackward(List<EChange> change) {
		change.reverseView.forEach[assertApplyBackward]
	}

	/**
	 * Tests whether an non applicable change could be applied forward.
	 */
	def static void assertCannotBeAppliedForward(EChange change) {
		var boolean exceptionThrown = false
		try {
			change.applyForward
		} catch (RuntimeException e) {
			exceptionThrown = true
		}
		if (!exceptionThrown) {
			fail("No RuntimeException thrown.")
		}
	}

	/**
	 * Tests whether an non applicable change could be applied backward.
	 */
	def static void assertCannotBeAppliedBackward(EChange change) {
		var boolean exceptionThrown = false
		try {
			change.applyForward
		} catch (RuntimeException e) {
			exceptionThrown = true
		}
		if (!exceptionThrown) {
			fail("No RuntimeException thrown.")
		}
	}

	static def <T> T assertType(Object original, Class<T> type) {
		if (type.isAssignableFrom(original.class)) {
			return original as T
		}
		fail("Object " + original + " is not expected type " + type);
		return null;
	}
}
