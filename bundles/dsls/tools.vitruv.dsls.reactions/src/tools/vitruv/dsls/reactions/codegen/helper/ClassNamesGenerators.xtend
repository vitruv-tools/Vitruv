package tools.vitruv.dsls.reactions.codegen.helper

import tools.vitruv.framework.util.datatypes.Pair
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;
import tools.vitruv.dsls.reactions.helper.XtendImportHelper
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.dsls.mirbase.mirBase.DomainReference
import tools.vitruv.framework.domains.VitruvDomainProvider

final class ClassNamesGenerators {
	private static String BASIC_PACKAGE = "mir";
	private static val FSA_SEPARATOR = "/";
	private static val XTEND_FILE_EXTENSION = ".java";
	private static val REACTIONS_PACKAGE = "reactions";
	private static String ROUTINES_PACKAGE = "routines";
	private static String ROUTINES_FACADE_CLASS_NAME = "RoutinesFacade";
	
	private new() {}
	
	static def generateClass(String packageName, XtendImportHelper importHelper, CharSequence classImplementation) '''
		package «packageName»;
		
		«importHelper.generateImportCode»
		
		«classImplementation»
		'''

	public static def String getFilePath(String qualifiedClassName) '''
		«qualifiedClassName.replace('.', FSA_SEPARATOR)»«XTEND_FILE_EXTENSION»'''
		
	public static def String getBasicMirPackageQualifiedName() '''
		«BASIC_PACKAGE»'''
		
	public static def String getBasicReactionsPackageQualifiedName() '''
		«basicMirPackageQualifiedName».«REACTIONS_PACKAGE»'''
	
	public static def String getBasicRoutinesPackageQualifiedName() '''
		«basicMirPackageQualifiedName».«ROUTINES_PACKAGE»'''	
	
//	private static def String getMetamodelIdentifier(EPackage pckg) {
//		return pckg.nsPrefix.replace(".", "_").toFirstUpper;
//	}
	
	private static def String getDomainPairName(Pair<VitruvDomainProvider<?>, VitruvDomainProvider<?>> metamodelPair) {
		return '''«metamodelPair.first.domain.name»To«metamodelPair.second.domain.name»'''	
	}
	
	private static def String getMetamodelPairName(ReactionsSegment reactionSegment) {
		return new Pair<VitruvDomainProvider<?>, VitruvDomainProvider<?>>(reactionSegment.fromDomain.domainProviderForReference, reactionSegment.toDomain.domainProviderForReference).domainPairName;
	}
	
	private static def String getPackageName(ReactionsSegment reactionSegment) '''
		reactions«reactionSegment.metamodelPairName»'''
		
	private static def String getQualifiedPackageName(ReactionsSegment reactionSegment) '''
		«basicReactionsPackageQualifiedName».«reactionSegment.packageName»'''
	
	public static def ClassNameGenerator getChangePropagationSpecificationClassNameGenerator(Pair<VitruvDomainProvider<?>, VitruvDomainProvider<?>> metamodelPair) {
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
	
	public static abstract class ClassNameGenerator {
		public def String getQualifiedName() '''
			«packageName».«simpleName»'''
			
		public def String getSimpleName();
		public def String getPackageName();
	}
	
	private static class ChangePropagationSpecificationClassNameGenerator extends ClassNameGenerator {
		private val String metamodelPairName;
		
		public new(Pair<VitruvDomainProvider<?>, VitruvDomainProvider<?>> metamodelPair) {
			this.metamodelPairName = metamodelPair.domainPairName;
		}
		
		public override getSimpleName() '''
			AbstractChangePropagationSpecification«metamodelPairName»'''
		
		public override getPackageName() '''
			«basicReactionsPackageQualifiedName»'''	
	}	
	
	private static class ExecutorClassNameGenerator extends ClassNameGenerator {
		private val ReactionsSegment reactionSegment;
		
		public new(ReactionsSegment reactionSegment) {
			this.reactionSegment = reactionSegment;
		}
		
		public override getSimpleName() '''
			Executor«reactionSegment.metamodelPairName»'''
	
		public override getPackageName() '''
			«reactionSegment.qualifiedPackageName».«reactionSegment.name.toFirstLower»'''		
	}
	
	private static class ReactionClassNameGenerator extends ClassNameGenerator {
		private val Reaction reaction;
		public new(Reaction reaction) {
			this.reaction = reaction;
		}
		
		public override String getSimpleName() '''
			«reaction.name.toFirstUpper»Reaction'''
		
		public override String getPackageName() '''
			«reaction.reactionsSegment.qualifiedPackageName».«reaction.reactionsSegment.name.toFirstLower»'''		
	}
	
	private static class RoutineClassNameGenerator extends ClassNameGenerator {
		private val Routine routine;
		public new(Routine routine) {
			this.routine = routine;
		}
		
		public override String getSimpleName() '''
			«routine.name.toFirstUpper»Routine'''
		
		public override String getPackageName() '''
			«basicRoutinesPackageQualifiedName».«routine.reactionsSegment.name.toFirstLower»'''
		
	}
	
	private static class RoutinesFacadeClassNameGenerator extends ClassNameGenerator {
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