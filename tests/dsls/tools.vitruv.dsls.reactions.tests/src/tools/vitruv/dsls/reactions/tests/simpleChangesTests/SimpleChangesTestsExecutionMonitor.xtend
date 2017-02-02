package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import java.util.BitSet
import static org.junit.Assert.assertTrue;

final class SimpleChangesTestsExecutionMonitor {
	private static var SimpleChangesTestsExecutionMonitor INSTANCE;
	
	public static def getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SimpleChangesTestsExecutionMonitor();
		}
		return INSTANCE;
	}
	
	public static def void reinitialize() {
		INSTANCE = new SimpleChangesTestsExecutionMonitor();
	}
	
	private BitSet values;
	
	new() {
		this.values = new BitSet(ChangeType.Size.ordinal + 1);
	}
	
	public enum ChangeType {
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
	
	public def void set(ChangeType type) {
		this.values.set(type.ordinal);
	}
	
	public def boolean isSet(ChangeType type) {
		return this.values.get(type.ordinal);
	}
	
	public override boolean equals(Object object) {
		if (object instanceof SimpleChangesTestsExecutionMonitor) {
			val monitor = object as SimpleChangesTestsExecutionMonitor;
			return monitor.values.equals(this.values);
		}
		return false;
	}
	
	public def assertEqualWithStatic() {
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
