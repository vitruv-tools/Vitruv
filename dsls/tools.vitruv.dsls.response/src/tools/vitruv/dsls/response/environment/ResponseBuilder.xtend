package tools.vitruv.dsls.response.environment;

import org.eclipse.emf.ecore.EPackage
import tools.vitruv.dsls.response.responseLanguage.ResponseLanguageFactory
import tools.vitruv.dsls.response.api.generator.IResponseBuilder
import org.eclipse.xtend2.lib.StringConcatenationClient
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.mirbase.mirBase.MetamodelReference
import tools.vitruv.dsls.mirbase.mirBase.MetamodelImport
import tools.vitruv.dsls.response.responseLanguage.Routine
import tools.vitruv.dsls.response.responseLanguage.ReactionsSegment
import tools.vitruv.dsls.response.responseLanguage.Reaction

public class ResponseBuilder implements IResponseBuilder {
	private ReactionsSegment reactionsSegment;
	private Routine routine;
	private Reaction reaction;
	
	public new() {
		this.reaction = ResponseLanguageFactory.eINSTANCE.createReaction();
		this.routine = ResponseLanguageFactory.eINSTANCE.createRoutine();
		this.reactionsSegment = ResponseLanguageFactory.eINSTANCE.createReactionsSegment();
		reactionsSegment.reactions += this.reaction;
		reactionsSegment.routines += this.routine;
	}
	
	public override setName(String name) {
		this.reaction.name = name + "Response";
		this.routine.name = name + "Routine";
		this.reaction.callRoutine.code = new SimpleTextXBlockExpression('''«this.routine.name»(change); ''');
		return this;
	}
	
	public override setTrigger(EPackage sourceMetamodel) {
		val trigger = ResponseLanguageFactory.eINSTANCE.createArbitraryModelElementChange();
		val metamodelImport = generateMetamodelImport(sourceMetamodel);
		this.reactionsSegment.fromMetamodel = generateMetamodelReference(metamodelImport);
		this.reaction.trigger = trigger;
		return this;
	}
	
	public override setTargetChange(EPackage targetMetamodel) {
		val metamodelImport = generateMetamodelImport(targetMetamodel);
		this.reactionsSegment.toMetamodel = generateMetamodelReference(metamodelImport);
		return this;
	}
	
	public override setExecutionBlock(StringConcatenationClient executionBlockCode) {
		val executionBlock = ResponseLanguageFactory.eINSTANCE.createRoutineCallStatement();
		executionBlock.code = new SimpleTextXBlockExpression(executionBlockCode);
		val matcher = ResponseLanguageFactory.eINSTANCE.createMatcher();
		val action = ResponseLanguageFactory.eINSTANCE.createAction();
		action.actionStatements += executionBlock;
		this.routine.action = action;
		this.routine.matcher = matcher;
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
	
	public override Reaction generateResponse() {
		return reaction;	
	}
}
