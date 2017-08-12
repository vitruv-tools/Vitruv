package tools.vitruv.dsls.reactions.codegen.helper

import edu.kit.ipd.sdq.commons.util.java.Pair
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.framework.domains.VitruvDomain
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.common.helper.ClassNameGenerator

@Utility class ClassNamesGenerators {
	static val String BASIC_PACKAGE = "mir"
	private static val REACTIONS_PACKAGE = "reactions"
	static val String ROUTINES_PACKAGE = "routines"
	static val String ROUTINES_FACADE_CLASS_NAME = "RoutinesFacade"
	
	public static def String getBasicMirPackageQualifiedName() '''
		«BASIC_PACKAGE»'''
		
	public static def String getBasicReactionsPackageQualifiedName() '''
		«basicMirPackageQualifiedName».«REACTIONS_PACKAGE»'''
	
	public static def String getBasicRoutinesPackageQualifiedName() '''
		«basicMirPackageQualifiedName».«ROUTINES_PACKAGE»'''	
	
//	private static def String getMetamodelIdentifier(EPackage pckg) {
//		return pckg.nsPrefix.replace(".", "_").toFirstUpper;
//	}
	
	private static def String correctAcronymCapitalization(String potentialAcronym) {
		if (potentialAcronym.toUpperCase == potentialAcronym) {
			return potentialAcronym.toLowerCase.toFirstUpper;
		}
		return potentialAcronym;
	}
	
	private static def String getDomainPairName(Pair<VitruvDomain, VitruvDomain> metamodelPair) {
		return '''«metamodelPair.first.name.correctAcronymCapitalization»To«metamodelPair.second.name.correctAcronymCapitalization»'''	
	}
	
	private static def String getMetamodelPairName(ReactionsSegment reactionSegment) {
		return new Pair<VitruvDomain, VitruvDomain>(reactionSegment.fromDomain.domainForReference, reactionSegment.toDomain.domainForReference).domainPairName;
	}
	
	private static def String getPackageName(ReactionsSegment reactionSegment) '''
		reactions«reactionSegment.metamodelPairName»'''
		
	private static def String getQualifiedPackageName(ReactionsSegment reactionSegment) '''
		«basicReactionsPackageQualifiedName».«reactionSegment.packageName»'''
	
	public static def ClassNameGenerator getChangePropagationSpecificationClassNameGenerator(Pair<VitruvDomain, VitruvDomain> metamodelPair) {
		return new ChangePropagationSpecificationClassNameGenerator(metamodelPair);
	}
	
	public static def ClassNameGenerator getExecutorClassNameGenerator(ReactionsSegment reactionSegment) {
		return new ExecutorClassNameGenerator(reactionSegment);
	}
	
	public static def ClassNameGenerator getRoutinesFacadeClassNameGenerator(ReactionsSegment reactionSegment) {
		return new RoutinesFacadeClassNameGenerator(reactionSegment);
	}
	
	public static def ClassNameGenerator getReactionClassNameGenerator(Reaction reaction) {
		return new ReactionClassNameGenerator(reaction);
	}
	
	public static def ClassNameGenerator getRoutineClassNameGenerator(Routine routine) {
		return new RoutineClassNameGenerator(routine);
	}
	
	private static class ChangePropagationSpecificationClassNameGenerator implements ClassNameGenerator {
		private val String metamodelPairName;
		
		public new(Pair<VitruvDomain, VitruvDomain> metamodelPair) {
			this.metamodelPairName = metamodelPair.domainPairName;
		}
		
		public override getSimpleName() '''
			«metamodelPairName»ChangePropagationSpecification'''
		
		public override getPackageName(){ basicReactionsPackageQualifiedName}	
	}	
	
	private static class ExecutorClassNameGenerator implements ClassNameGenerator {
		private val ReactionsSegment reactionSegment;
		
		public new(ReactionsSegment reactionSegment) {
			this.reactionSegment = reactionSegment;
		}
		
		public override getSimpleName() '''
			Executor«reactionSegment.metamodelPairName»'''
	
		public override getPackageName() '''
			«reactionSegment.qualifiedPackageName».«reactionSegment.name.toFirstLower»'''		
	}
	
	private static class ReactionClassNameGenerator implements ClassNameGenerator {
		private val Reaction reaction;
		public new(Reaction reaction) {
			this.reaction = reaction;
		}
		
		public override String getSimpleName() '''
			«reaction.name.toFirstUpper»Reaction'''
		
		public override String getPackageName() '''
			«reaction.reactionsSegment.qualifiedPackageName».«reaction.reactionsSegment.name.toFirstLower»'''		
	}
	
	private static class RoutineClassNameGenerator implements ClassNameGenerator {
		private val Routine routine;
		public new(Routine routine) {
			this.routine = routine;
		}
		
		public override String getSimpleName() '''
			«routine.name.toFirstUpper»Routine'''
		
		public override String getPackageName() '''
			«basicRoutinesPackageQualifiedName».«routine.reactionsSegment.name.toFirstLower»'''
		
	}
	
	private static class RoutinesFacadeClassNameGenerator implements ClassNameGenerator {
		private val ReactionsSegment reactionSegment;
		public new(ReactionsSegment reactionSegment) {
			this.reactionSegment = reactionSegment;
		}
		
		public override String getSimpleName() '''
			«ROUTINES_FACADE_CLASS_NAME»'''
		
		public override String getPackageName() '''
			«basicRoutinesPackageQualifiedName».«reactionSegment.name.toFirstLower»'''		
	}
}