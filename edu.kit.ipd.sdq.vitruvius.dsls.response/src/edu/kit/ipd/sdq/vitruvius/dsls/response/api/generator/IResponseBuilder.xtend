package edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator;

import org.eclipse.emf.ecore.EPackage
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response

interface IResponseBuilder {
	def Response generateResponse();
	def IResponseBuilder setName(String name);
	def IResponseBuilder setTrigger(EPackage sourceMetamodel);
	def IResponseBuilder setTargetChange(EPackage targetMetamodel);
	def IResponseBuilder setExecutionBlock(String executionBlockCode);
}