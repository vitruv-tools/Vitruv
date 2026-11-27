package tools.vitruv.change.atomic.util

import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.change.atomic.feature.attribute.InsertEAttributeValue
import tools.vitruv.change.atomic.feature.attribute.RemoveEAttributeValue
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertSame

/**
 * Helper class to compare different instances of the same change.
 */
class EChangeAssertEquals {
	def dispatch static void assertEquals(EChange<?> change, EChange<?> change2) {
		// Is needed so xtend creates the assertEquals(EChange, EChange) method.
		assertTrue(false)
	}

	/**
	 * Compares two {@link ReplaceSingleValuedEAttribute} EChanges.
	 */
	def dispatch static void assertEquals(ReplaceSingleValuedEAttribute<?, ?> change, EChange<?> change2) {
		var replaceChange = change2.assertIsInstanceOf(ReplaceSingleValuedEAttribute)
		assertSame(change.affectedElement, replaceChange.affectedElement)
		assertSame(change.affectedFeature, replaceChange.affectedFeature)
		assertEquals(change.oldValue, replaceChange.oldValue)
		assertEquals(change.newValue, replaceChange.newValue)
	}

	/**
	 * Compares two {@link InsertEAttributeValue} EChanges.
	 */
	def dispatch static void assertEquals(InsertEAttributeValue<?, ?> change, EChange<?> change2) {
		var insertChange = change2.assertIsInstanceOf(InsertEAttributeValue)
		assertSame(change.affectedElement, insertChange.affectedElement)
		assertSame(change.affectedFeature, insertChange.affectedFeature)
		assertEquals(change.newValue, insertChange.newValue)
	}

	/**
	 * Compares two {@link RemoveEAttributeValue} EChanges.
	 */
	def dispatch static void assertEquals(RemoveEAttributeValue<?, ?> change, EChange<?> change2) {
		var removeChange = change2.assertIsInstanceOf(RemoveEAttributeValue)
		assertSame(change.affectedElement, removeChange.affectedElement)
		assertSame(change.affectedFeature, removeChange.affectedFeature)
		assertEquals(change.oldValue, removeChange.oldValue)
	}

	def private static <E, T extends EChange<? extends E>> T assertIsInstanceOf(EChange<E> change, Class<T> type) {
		assertTrue(type.isInstance(change))
		return type.cast(change)
	}
}
