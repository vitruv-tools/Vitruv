package tools.vitruv.dsls.reactions.api.generator;

import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtend2.lib.StringConcatenationClient
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction

interface IReactionBuilder {
	def Reaction generateReaction();
	def IReactionBuilder setName(String name);
	def IReactionBuilder setTrigger(EPackage sourceMetamodel);
	def IReactionBuilder setTargetChange(EPackage targetMetamodel);
	def IReactionBuilder setExecutionBlock(StringConcatenationClient executionBlockCode);
}