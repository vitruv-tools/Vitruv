package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import tools.vitruv.dsls.reactions.tests.ExecutionMonitor
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor.ChangeType
import java.util.EnumSet

final class SimpleChangesTestsExecutionMonitor implements ExecutionMonitor<ChangeType> {
	public static val instance = new SimpleChangesTestsExecutionMonitor()

	val values = EnumSet.noneOf(ChangeType)

	private new() {
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

	def set(ChangeType type) {
		values += type
	}

	def reset() {
		values.clear()
	}

	override boolean equals(Object object) {
		if (object instanceof SimpleChangesTestsExecutionMonitor) {
			val monitor = object;
			return monitor.values.equals(this.values);
		}
		return false;
	}

	override getObservedExecutions() {
		values
	}
}
