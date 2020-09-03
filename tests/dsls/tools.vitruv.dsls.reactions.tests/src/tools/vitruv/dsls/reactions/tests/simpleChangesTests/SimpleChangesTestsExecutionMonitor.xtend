package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import java.util.BitSet
import static org.junit.Assert.assertTrue;

final class SimpleChangesTestsExecutionMonitor {
	static var SimpleChangesTestsExecutionMonitor INSTANCE;
	
	static def getInstance() {
		if (INSTANCE === null) {
			INSTANCE = new SimpleChangesTestsExecutionMonitor();
		}
		return INSTANCE;
	}
	
	static def void reinitialize() {
		INSTANCE = new SimpleChangesTestsExecutionMonitor();
	}
	
	BitSet values;
	
	new() {
		this.values = new BitSet(ChangeType.Size.ordinal + 1);
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
		this.values.set(type.ordinal);
	}
	
	def boolean isSet(ChangeType type) {
		return this.values.get(type.ordinal);
	}
	
	override boolean equals(Object object) {
		if (object instanceof SimpleChangesTestsExecutionMonitor) {
			val monitor = object;
			return monitor.values.equals(this.values);
		}
		return false;
	}
	
	def assertEqualWithStatic() {
		for (var i = 0; i < ChangeType.Size.ordinal; i++) {
			if (values.get(i)) {
				assertTrue(ChangeType.values.get(i) + " was expected to occur but did not", INSTANCE.values.get(i));
			}
			if (!values.get(i)) {
				assertTrue(ChangeType.values.get(i) + " was not expected to occur but did", !INSTANCE.values.get(i));
			}
		}
	}
}
