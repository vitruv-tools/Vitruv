package tools.vitruvius.dsls.response.environment;

import org.eclipse.emf.ecore.EPackage
import tools.vitruvius.dsls.response.responseLanguage.Response
import tools.vitruvius.dsls.response.responseLanguage.ResponseLanguageFactory
import tools.vitruvius.dsls.response.api.generator.IResponseBuilder
import org.eclipse.xtend2.lib.StringConcatenationClient
import tools.vitruvius.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruvius.dsls.mirbase.mirBase.MetamodelReference
import tools.vitruvius.dsls.mirbase.mirBase.MetamodelImport
import tools.vitruvius.dsls.response.responseLanguage.ResponsesSegment

public class ResponseBuilder implements IResponseBuilder {
	private ResponsesSegment responsesSegment;
	private Response response;
	
	public new() {
		this.response = ResponseLanguageFactory.eINSTANCE.createResponse();
		this.response.routine = ResponseLanguageFactory.eINSTANCE.createImplicitRoutine();
		this.responsesSegment = ResponseLanguageFactory.eINSTANCE.createResponsesSegment();
		responsesSegment.responses += this.response;
	}
	
	public override setName(String name) {
		this.response.name = name;	
		return this;
	}
	
	public override setTrigger(EPackage sourceMetamodel) {
		val trigger = ResponseLanguageFactory.eINSTANCE.createArbitraryModelElementChange();
		val metamodelImport = generateMetamodelImport(sourceMetamodel);
		this.responsesSegment.fromMetamodel = generateMetamodelReference(metamodelImport);
		this.response.trigger = trigger;
		return this;
	}
	
	public override setTargetChange(EPackage targetMetamodel) {
		val metamodelImport = generateMetamodelImport(targetMetamodel);
		this.responsesSegment.toMetamodel = generateMetamodelReference(metamodelImport);
		return this;
	}
	
	public override setExecutionBlock(StringConcatenationClient executionBlockCode) {
		val executionBlock = ResponseLanguageFactory.eINSTANCE.createExecutionCodeBlock();
		executionBlock.code = new SimpleTextXBlockExpression(executionBlockCode);
		val matching = ResponseLanguageFactory.eINSTANCE.createMatching();
		val effect = ResponseLanguageFactory.eINSTANCE.createEffect();
		effect.codeBlock = executionBlock;
		this.response.routine.effect = effect;
		this.response.routine.matching = matching;
		return this;
	}
	
	private static def MetamodelImport generateMetamodelImport(EPackage pack) {
		val metamodelImport = MirBaseFactory.eINSTANCE.createMetamodelImport();
		metamodelImport.name = pack.name;
		metamodelImport.package = pack;
		return metamodelImport;
	}
	
	private static def MetamodelReference generateMetamodelReference(MetamodelImport metamodelImport) {
		val metamodelRef = MirBaseFactory.eINSTANCE.createMetamodelReference();
		metamodelRef.model = metamodelImport;
		return metamodelRef;
	}
	
	public override Response generateResponse() {
		return response;	
	}
}
