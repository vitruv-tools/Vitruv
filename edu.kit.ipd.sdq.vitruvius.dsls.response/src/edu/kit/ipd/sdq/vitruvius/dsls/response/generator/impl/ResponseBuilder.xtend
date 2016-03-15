package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl;

import org.eclipse.emf.ecore.EPackage
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguageFactory
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.IResponseBuilder
import org.eclipse.xtend2.lib.StringConcatenationClient
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.MirBaseFactory
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.MetamodelReference

public class ResponseBuilder implements IResponseBuilder {
	private Response response;
	
	public new() {
		this.response = ResponseLanguageFactory.eINSTANCE.createResponse();
		this.response.effect = ResponseLanguageFactory.eINSTANCE.createImplicitEffect();
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
		this.response.effect.targetChange = targetChange;
		return this;
	}
	
	public override setExecutionBlock(StringConcatenationClient executionBlockCode) {
		val executionBlock = ResponseLanguageFactory.eINSTANCE.createExecutionCodeBlock();
		executionBlock.code = new SimpleTextXBlockExpression(executionBlockCode);
		this.response.effect.codeBlock = executionBlock;
		return this;
	}
	
	private static def MetamodelReference generateMetamodelReference(EPackage pack) {
		val metamodelRef = MirBaseFactory.eINSTANCE.createMetamodelReference();
		val metamodelImport = MirBaseFactory.eINSTANCE.createMetamodelImport();
		metamodelImport.name = pack.name;
		metamodelImport.package = pack;
		metamodelRef.model = metamodelImport;
		return metamodelRef;
	}
	
	public override Response generateResponse() {
		return response;	
	}
}
