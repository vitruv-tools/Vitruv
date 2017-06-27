package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import java.util.BitSet
import static org.junit.Assert.assertTrue

final class SimpleChangesTestsExecutionMonitor {
	static SimpleChangesTestsExecutionMonitor INSTANCE
	val BitSet values

	static def getInstance() {
		if (INSTANCE === null)
			INSTANCE = new SimpleChangesTestsExecutionMonitor
		return INSTANCE
	}

	static def void reinitialize() {
		INSTANCE = new SimpleChangesTestsExecutionMonitor
	}

	new() {
		values = new BitSet(ChangeType.Size.ordinal + 1)
	}

	public enum ChangeType {
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

	override equals(Object object) {
		if (object instanceof SimpleChangesTestsExecutionMonitor)
			return object.values.equals(values)
		return false
	}

	def assertEqualWithStatic() {
		for (i : 0 ..< ChangeType::Size.ordinal) {
			if (values.get(i))
				assertTrue(ChangeType.values.get(i) + " was expected to occur but did not", INSTANCE.values.get(i))
			else
				assertTrue(ChangeType.values.get(i) + " was not expected to occur but did", !INSTANCE.values.get(i))
		}
	}
}
