package tools.vitruv.dsls.reactions.tests

import com.google.inject.Singleton

@Singleton
class AllElementTypesRedundancyReactionsCompiler extends TestReactionsCompiler {

	static val INPUT_REACTION_FILES = #["AllElementTypesRedundancy.reactions"]
	static val CHANGE_PROPAGATION_SEGMENTS = #["simpleChangesTests"]

	new() {
		super(INPUT_REACTION_FILES, CHANGE_PROPAGATION_SEGMENTS)
	}
}
