package tools.vitruv.dsls.reactions.tests.importTests

import com.google.inject.Singleton
import tools.vitruv.dsls.reactions.tests.TestReactionsCompiler

@Singleton
class ImportTestReactionsCompiler extends TestReactionsCompiler {

	// ordered: segments with imports after their imported segments
	static val INPUT_REACTION_FILES = #["CommonRoutines.reactions", "TransitiveRoutinesQN.reactions", "TransitiveRoutinesSN.reactions",
		"Transitive2SN.reactions", "Transitive3SN.reactions", "TransitiveSN.reactions", "DirectRoutinesQN.reactions", "Direct2SN.reactions",
		"DirectSN.reactions", "Root.reactions"]
	static val CHANGE_PROPAGATION_SEGMENTS = #["importTestsRoot"]

	new() {
		super(INPUT_REACTION_FILES, CHANGE_PROPAGATION_SEGMENTS)
	}
}
