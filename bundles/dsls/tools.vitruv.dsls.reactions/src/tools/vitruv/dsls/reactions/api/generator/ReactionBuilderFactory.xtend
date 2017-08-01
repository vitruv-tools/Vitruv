package tools.vitruv.dsls.reactions.api.generator;

import tools.vitruv.dsls.reactions.generator.ReactionBuilder

class ReactionBuilderFactory {
	public def IReactionBuilder createReactionBuilder() {
		return new ReactionBuilder();
	}
}