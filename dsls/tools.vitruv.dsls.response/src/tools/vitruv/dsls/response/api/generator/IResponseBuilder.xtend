package tools.vitruv.dsls.response.api.generator;

import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtend2.lib.StringConcatenationClient
import tools.vitruv.dsls.response.responseLanguage.Reaction

interface IResponseBuilder {
	def Reaction generateResponse();
	def IResponseBuilder setName(String name);
	def IResponseBuilder setTrigger(EPackage sourceMetamodel);
	def IResponseBuilder setTargetChange(EPackage targetMetamodel);
	def IResponseBuilder setExecutionBlock(StringConcatenationClient executionBlockCode);
}