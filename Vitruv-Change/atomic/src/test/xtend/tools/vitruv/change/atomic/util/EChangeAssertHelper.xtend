package tools.vitruv.change.atomic.util

import java.util.List
import org.eclipse.emf.ecore.EObject
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.uuid.Uuid

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.is
import static org.hamcrest.core.IsInstanceOf.instanceOf
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotSame

/**
 * Utility class for frequently used assert methods in the tests.
 */
class EChangeAssertHelper {
	/**
	 * Tests whether a unresolved change and a resolved change are the same class.
	 */
	def void assertDifferentChangeSameClass(EChange<Uuid> unresolvedChange, EChange<? extends EObject> resolvedChange) {
		assertNotSame(unresolvedChange, resolvedChange)
		assertEquals(unresolvedChange.getClass, resolvedChange.getClass)
	}

	/**
	 * Tests whether a unresolved changes and a resolved changes are the same classes.
	 */
	def void assertDifferentChangeSameClass(List<? extends EChange<Uuid>> unresolvedChange,
		List<? extends EChange<? extends EObject>> resolvedChange) {
		assertEquals(unresolvedChange.size, resolvedChange.size)
		for (var i = 0; i < unresolvedChange.size; i++) {
			assertDifferentChangeSameClass(unresolvedChange.get(i), resolvedChange.get(i))
		}
	}
	
	static def <T> T assertType(Object original, Class<T> type) {
		assertThat(original, is(instanceOf(type)))
		return original as T
	}
	
}
