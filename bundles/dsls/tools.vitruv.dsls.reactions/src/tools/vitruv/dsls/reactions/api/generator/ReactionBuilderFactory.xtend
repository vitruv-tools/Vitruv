package tools.vitruv.dsls.reactions.api.generator;

import tools.vitruv.dsls.reactions.environment.ReactionBuilder

class ReactionBuilderFactory {
	public def IReactionBuilder createReactionBuilder() {
		return new ReactionBuilder();
	}
}