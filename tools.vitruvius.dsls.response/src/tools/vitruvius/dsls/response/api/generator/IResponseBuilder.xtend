package tools.vitruvius.dsls.response.api.generator;

import org.eclipse.emf.ecore.EPackage
import tools.vitruvius.dsls.response.responseLanguage.Response
import org.eclipse.xtend2.lib.StringConcatenationClient

interface IResponseBuilder {
	def Response generateResponse();
	def IResponseBuilder setName(String name);
	def IResponseBuilder setTrigger(EPackage sourceMetamodel);
	def IResponseBuilder setTargetChange(EPackage targetMetamodel);
	def IResponseBuilder setExecutionBlock(StringConcatenationClient executionBlockCode);
}