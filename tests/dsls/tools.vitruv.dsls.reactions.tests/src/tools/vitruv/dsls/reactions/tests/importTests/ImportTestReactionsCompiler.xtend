package tools.vitruv.dsls.reactions.tests.importTests

import com.google.inject.Singleton
import tools.vitruv.dsls.reactions.tests.TestReactionsCompiler

@Singleton
class ImportTestReactionsCompiler extends TestReactionsCompiler {

	static val INPUT_REACTION_FILES = #["Root.reactions", "DirectSN.reactions", "Direct2SN.reactions", "DirectRoutinesQN.reactions",
		"TransitiveSN.reactions", "TransitiveRoutinesSN.reactions", "TransitiveRoutinesQN.reactions", "CommonRoutines.reactions"]
	static val CHANGE_PROPAGATION_SEGMENTS = #["importTestsRoot"]

	new() {
		super(INPUT_REACTION_FILES, CHANGE_PROPAGATION_SEGMENTS)
	}
}
