package tools.vitruv.dsls.reactions.environment;

import org.eclipse.emf.ecore.EPackage
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory
import org.eclipse.xtend2.lib.StringConcatenationClient
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.mirbase.mirBase.MetamodelReference
import tools.vitruv.dsls.mirbase.mirBase.MetamodelImport
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.api.generator.IReactionBuilder

public class ReactionBuilder implements IReactionBuilder {
	private ReactionsSegment reactionsSegment;
	private Routine routine;
	private Reaction reaction;
	
	public new() {
		this.reaction = ReactionsLanguageFactory.eINSTANCE.createReaction();
		this.routine = ReactionsLanguageFactory.eINSTANCE.createRoutine();
		this.reactionsSegment = ReactionsLanguageFactory.eINSTANCE.createReactionsSegment();
		reactionsSegment.reactions += this.reaction;
		reactionsSegment.routines += this.routine;
	}
	
	public override setName(String name) {
		this.reaction.name = name + "Reaction";
		this.routine.name = name + "Routine";
		this.reaction.callRoutine.code = new SimpleTextXBlockExpression('''«this.routine.name»(change); ''');
		return this;
	}
	
	public override setTrigger(EPackage sourceMetamodel) {
		val trigger = ReactionsLanguageFactory.eINSTANCE.createArbitraryModelChange();
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
		val executionBlock = ReactionsLanguageFactory.eINSTANCE.createRoutineCallStatement();
		executionBlock.code = new SimpleTextXBlockExpression(executionBlockCode);
		val matcher = ReactionsLanguageFactory.eINSTANCE.createMatcher();
		val action = ReactionsLanguageFactory.eINSTANCE.createAction();
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
	
	public override Reaction generateReaction() {
		return reaction;	
	}
}
