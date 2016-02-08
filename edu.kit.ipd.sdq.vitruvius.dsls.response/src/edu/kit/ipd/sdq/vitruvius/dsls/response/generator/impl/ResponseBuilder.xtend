package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl;

import org.eclipse.emf.ecore.EPackage
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguageFactory
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MetamodelReference
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.IResponseBuilder
import org.eclipse.xtend2.lib.StringConcatenationClient

public class ResponseBuilder implements IResponseBuilder {
	private Response response;
	
	public new() {
		this.response = ResponseLanguageFactory.eINSTANCE.createResponse();
		this.response.effects = ResponseLanguageFactory.eINSTANCE.createEffects();
	}
	
	public override setName(String name) {
		this.response.name = name;	
		return this;
	}
	
	public override setTrigger(EPackage sourceMetamodel) {
		val trigger = ResponseLanguageFactory.eINSTANCE.createArbitraryModelElementChange();
		trigger.changedModel = generateMetamodelReference(sourceMetamodel);
		this.response.trigger = trigger;
		return this;
	}
	
	public override setTargetChange(EPackage targetMetamodel) {
		val targetChange = ResponseLanguageFactory.eINSTANCE.createArbitraryTargetMetamodelInstanceUpdate();
		targetChange.metamodelReference = generateMetamodelReference(targetMetamodel);
		this.response.effects.targetChange = targetChange;
		return this;
	}
	
	public override setExecutionBlock(StringConcatenationClient executionBlockCode) {
		val executionBlock = ResponseLanguageFactory.eINSTANCE.createExecutionBlock();
		executionBlock.code = new SimpleTextXBlockExpression(executionBlockCode);
		this.response.effects.codeBlock = executionBlock;
		return this;
	}
	
	private static def MetamodelReference generateMetamodelReference(EPackage pack) {
		val metamodelRef = ResponseLanguageFactory.eINSTANCE.createMetamodelReference();
		val metamodelImport = ResponseLanguageFactory.eINSTANCE.createMetamodelImport();
		metamodelImport.name = pack.name;
		metamodelImport.package = pack;
		metamodelRef.model = metamodelImport;
		return metamodelRef;
	}
	
	public override Response generateResponse() {
		return response;	
	}
}
