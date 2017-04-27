package tools.vitruv.dsls.reactions.api.generator;

import org.eclipse.xtend2.lib.StringConcatenationClient
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.framework.domains.VitruvDomain

interface IReactionBuilder {
	def Reaction generateReaction();
	def IReactionBuilder setName(String name);
	def IReactionBuilder setTrigger(VitruvDomain sourceDomain);
	def IReactionBuilder setTargetChange(VitruvDomain targetDomain);
	def IReactionBuilder setExecutionBlock(StringConcatenationClient executionBlockCode);
}