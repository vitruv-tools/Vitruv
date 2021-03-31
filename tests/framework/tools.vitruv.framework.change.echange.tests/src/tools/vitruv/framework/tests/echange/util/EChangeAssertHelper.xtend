package tools.vitruv.framework.tests.echange.util

import tools.vitruv.framework.change.echange.EChange
import java.util.List
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNotSame
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.IsInstanceOf.instanceOf
import static org.hamcrest.core.Is.is
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolver.*

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
	 * Tests whether a change is resolved and applies it forward.
	 */
	def static void assertApplyForward(EChange change) {
		assertNotNull(change)
		assertTrue(change.isResolved)
		change.applyForward
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
		change.applyBackward
	}

	/**
	 * Tests whether a change sequence is resolved and applies it backward.
	 */
	def static void assertApplyBackward(List<EChange> change) {
		change.reverseView.forEach[assertApplyBackward]
	}
	
	static def <T> T assertType(Object original, Class<T> type) {
		assertThat(original, is(instanceOf(type)))
		return original as T
	}

}
