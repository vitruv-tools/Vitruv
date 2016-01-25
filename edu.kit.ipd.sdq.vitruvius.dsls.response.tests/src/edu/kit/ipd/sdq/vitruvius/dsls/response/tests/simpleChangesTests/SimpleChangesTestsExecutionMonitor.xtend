package edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests

import java.util.BitSet

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
		this.values = new BitSet(ChangeType.Size.ordinal);
	}
	
	public enum ChangeType {
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
}
