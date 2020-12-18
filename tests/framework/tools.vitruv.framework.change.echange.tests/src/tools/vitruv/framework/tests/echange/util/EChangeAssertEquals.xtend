package tools.vitruv.framework.tests.echange.util

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertSame

/**
 * Helper class to compare different instances of the same change.
 */
class EChangeAssertEquals {
	def dispatch static void assertEquals(EChange change, EChange change2) {
		// Is needed so xtend creates the assertEquals(EChange, EChange) method.
		assertTrue(false)
	}

	/**
	 * Compares two {@link ReplaceSingleValuedEAttribute} EChanges.
	 */
	def dispatch static void assertEquals(ReplaceSingleValuedEAttribute<?, ?> change, EChange change2) {
		var replaceChange = change2.assertIsInstanceOf(ReplaceSingleValuedEAttribute)
		assertSame(change.affectedEObject, replaceChange.affectedEObject)
		assertSame(change.affectedFeature, replaceChange.affectedFeature)
		assertEquals(change.oldValue, replaceChange.oldValue)
		assertEquals(change.newValue, replaceChange.newValue)
	}

	/**
	 * Compares two {@link InsertEAttributeValue} EChanges.
	 */
	def dispatch static void assertEquals(InsertEAttributeValue<?, ?> change, EChange change2) {
		var insertChange = change2.assertIsInstanceOf(InsertEAttributeValue)
		assertSame(change.affectedEObject, insertChange.affectedEObject)
		assertSame(change.affectedFeature, insertChange.affectedFeature)
		assertEquals(change.newValue, insertChange.newValue)
	}

	/**
	 * Compares two {@link RemoveEAttributeValue} EChanges.
	 */
	def dispatch static void assertEquals(RemoveEAttributeValue<?, ?> change, EChange change2) {
		var removeChange = change2.assertIsInstanceOf(RemoveEAttributeValue)
		assertSame(change.affectedEObject, removeChange.affectedEObject)
		assertSame(change.affectedFeature, removeChange.affectedFeature)
		assertEquals(change.oldValue, removeChange.oldValue)
	}

	def private static <T> T assertIsInstanceOf(EChange change, Class<T> type) {
		assertTrue(type.isInstance(change))
		return type.cast(change)
	}
}
