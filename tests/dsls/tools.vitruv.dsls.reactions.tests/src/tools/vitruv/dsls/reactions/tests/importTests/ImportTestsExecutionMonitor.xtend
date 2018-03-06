package tools.vitruv.dsls.reactions.tests.importTests

import java.util.EnumSet
import java.util.List

import static org.junit.Assert.assertTrue

final class ImportTestsExecutionMonitor {

	public enum ExecutionType {
		RootReaction,
		RootRoutine,
		RootDirectSNOverriddenReaction,
		RootTransitiveSNOverriddenReaction,
		RootTransitiveSNOverriddenReaction2,
		RootDirectSNOverriddenRoutine,
		RootTransitiveSNOverriddenRoutine,
		RootTransitive3SNOverriddenRoutine,
		RootTransitiveSNOverriddenRoutine2,
		RootCommonRoutinesRoutine1,
		RootCommonRoutinesRoutine2,
		RootCommonRoutinesRoutine3,
		RootCommonRoutines2Routine3,

		DirectSNReaction,
		DirectSNRoutine,
		DirectSNInnerRoutine,
		DirectSNOverriddenReaction,
		DirectSNTransitiveSNOverriddenReaction2,
		DirectSNTransitiveSNOverriddenReaction3,
		DirectSNOverriddenRoutine,
		DirectSNTransitiveSNOverriddenRoutine2,
		DirectSNTransitiveSNOverriddenRoutine3,

		Direct2SNReaction,
		Direct2SNRoutine,

		TransitiveSNReaction,
		TransitiveSNRoutine,
		TransitiveSNInnerRoutine,
		TransitiveSNOverriddenReaction,
		TransitiveSNOverriddenReaction2,
		TransitiveSNOverriddenReaction3,
		TransitiveSNOverriddenRoutine,
		TransitiveSNOverriddenRoutine2,
		TransitiveSNOverriddenRoutine3,

		Transitive2SNReaction,
		Transitive2SNRoutine,

		Transitive3SNReaction,
		Transitive3SNRoutine,
		Transitive3SNOverriddenRoutine,

		DirectRoutinesQNReaction,
		DirectRoutinesQNRoutine,

		TransitiveRoutinesSNReaction,
		TransitiveRoutinesSNRoutine,

		TransitiveRoutinesQNReaction,
		TransitiveRoutinesQNRoutine,

		CommonRoutinesRoutine1,
		CommonRoutinesRoutine2,
		CommonRoutinesRoutine3
	}

	private static val ImportTestsExecutionMonitor INSTANCE = new ImportTestsExecutionMonitor();

	public static def getInstance() {
		return INSTANCE;
	}

	private val values = EnumSet.noneOf(ExecutionType);

	new() {
	}

	public def void set(ExecutionType type) {
		values.add(type);
	}

	public def void setAll(ExecutionType... types) {
		if (types !== null) {
			for (type : types) {
				this.set(type);
			}
		}
	}

	public def boolean isSet(ExecutionType type) {
		return values.contains(type);
	}

	public def void reset() {
		values.clear();
	}

	public override boolean equals(Object object) {
		if (object instanceof ImportTestsExecutionMonitor) {
			return this.values.equals(object.values);
		}
		return false;
	}

	public override String toString() {
		return values.toString();
	}

	public def assertIsSet(ExecutionType type) {
		assertTrue(type + " was expected to occur, but did not", this.isSet(type));
	}

	public def assertIsNotSet(ExecutionType type) {
		assertTrue(type + " was not expected to occur but did", !this.isSet(type));
	}

	public def assertIsSetOnly(ExecutionType... types) {
		val typesList = (types as List<ExecutionType> ?: #[]);
		assertTrue("Expected " + typesList + " to occur, but got " + values,
			values.size == typesList.size && values.containsAll(typesList));
	}

	public def assertEqualWithStatic() {
		for (executionType : ExecutionType.values) {
			if (this.isSet(executionType)) {
				assertTrue(executionType + " was expected to occur but did not", instance.isSet(executionType));
			} else {
				assertTrue(executionType + " was not expected to occur but did", !instance.isSet(executionType));
			}
		}
	}
}
