package tools.vitruv.dsls.reactions.tests.importTests

import java.util.EnumSet
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.reactions.tests.ExecutionMonitor
import tools.vitruv.dsls.reactions.tests.importTests.ImportTestsExecutionMonitor.ExecutionType

final class ImportTestsExecutionMonitor implements ExecutionMonitor<ExecutionType> {
	enum ExecutionType {
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

	val values = EnumSet.noneOf(ExecutionType)

	@Accessors
	static val instance = new ImportTestsExecutionMonitor()

	private new() {
	}

	override getObservedExecutions() {
		values
	}

	def void set(ExecutionType type) {
		values += type
	}

	def void setAll(ExecutionType... types) {
		values += types
	}

	def void reset() {
		values.clear()
	}

	override boolean equals(Object object) {
		if (object instanceof ImportTestsExecutionMonitor) {
			return this.values.equals(object.values);
		}
		return false;
	}

	override String toString() {
		return values.toString();
	}
}
