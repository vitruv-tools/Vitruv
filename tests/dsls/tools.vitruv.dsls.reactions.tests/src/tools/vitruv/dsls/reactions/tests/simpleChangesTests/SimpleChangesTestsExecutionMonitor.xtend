package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import java.util.BitSet
import static org.junit.Assert.assertTrue

final class SimpleChangesTestsExecutionMonitor {
	static var SimpleChangesTestsExecutionMonitor INSTANCE

	static def getInstance() {
		if (INSTANCE === null) {
			INSTANCE = new SimpleChangesTestsExecutionMonitor
		}
		INSTANCE
	}

	static def void reinitialize() {
		INSTANCE = new SimpleChangesTestsExecutionMonitor
	}

	BitSet values

	new() {
		values = new BitSet(ChangeType::Size.ordinal + 1)
	}

	enum ChangeType {
		CreateEObject,
		DeleteEObject,
		UpdateSingleValuedPrimitveTypeEAttribute,
		UpdateSingleValuedEAttribute,
		UnsetEAttribute,
		CreateNonRootEObjectSingle,
		DeleteNonRootEObjectSingle,
		CreateNonRootEObjectInList,
		DeleteNonRootEObjectInList,
		InsertEAttributeValue,
		RemoveEAttributeValue,
		ReplaceEAttributeValue,
		InsertNonContainmentEReference,
		RemoveNonContainmentEReference,
		PermuteNonContainmentEReference,
		ReplaceNonContainmentEReference,
		UpdateSingleValuedNonContainmentEReference,
		UnsetNonContainmentEReference,
		Size
	}

	def void set(ChangeType type) {
		values.set(type.ordinal)
	}

	def boolean isSet(ChangeType type) {
		values.get(type.ordinal)
	}

	override boolean equals(Object object) {
		if (object instanceof SimpleChangesTestsExecutionMonitor)
			object.values.equals(values)
		else
			false
	}

	def assertEqualWithStatic() {
		for (var i = 0; i < ChangeType::Size.ordinal; i++) {
			if (values.get(i))
				assertTrue('''«ChangeType::values.get(i)» was expected to occur but did not''', INSTANCE.values.get(i))
			else
				assertTrue('''«ChangeType::values.get(i)»  was not expected to occur but did''',
					!INSTANCE.values.get(i))
		}
	}
}
