package tools.vitruv.dsls.response.helper

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.Pair
import org.eclipse.emf.common.util.URI
import static extension tools.vitruv.dsls.response.helper.ResponseLanguageHelper.*;
import tools.vitruv.dsls.response.helper.XtendImportHelper
import tools.vitruv.dsls.response.responseLanguage.ReactionsSegment
import tools.vitruv.dsls.response.responseLanguage.Routine
import tools.vitruv.dsls.response.responseLanguage.Reaction

final class ResponseClassNamesGenerator {
	private static String BASIC_PACKAGE = "mir";
	private static val FSA_SEPARATOR = "/";
	private static val XTEND_FILE_EXTENSION = ".java";
	private static val RESPONSES_PACKAGE = "reactions";
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
		
	public static def String getBasicResponsesPackageQualifiedName() '''
		«basicMirPackageQualifiedName».«RESPONSES_PACKAGE»'''
	
	public static def String getBasicRoutinesPackageQualifiedName() '''
		«basicMirPackageQualifiedName».«ROUTINES_PACKAGE»'''	
	
	private static def String getMetamodelIdentifier(URI uri) {
		if (uri.lastSegment.nullOrEmpty) {
			return uri.toString.split("\\.").last.toFirstUpper;
		} else {
			return uri.lastSegment.replace(".", "_").toFirstUpper;
		}
	}
	
	private static def String getMetamodelIdentifier(VURI uri) {
		return uri.EMFUri.metamodelIdentifier;
	}
	
	private static def String getMetamodelPairName(Pair<VURI, VURI> metamodelPair) {
		return '''«metamodelPair.first.metamodelIdentifier»To«metamodelPair.second.metamodelIdentifier»'''	
	}
	
	private static def String getMetamodelPairName(ReactionsSegment reactionSegment) {
		return reactionSegment.sourceTargetPair.metamodelPairName;
	}
	
	private static def String getPackageName(ReactionsSegment reactionSegment) '''
		reactions«reactionSegment.metamodelPairName»'''
		
	private static def String getQualifiedPackageName(ReactionsSegment reactionSegment) '''
		«basicResponsesPackageQualifiedName».«reactionSegment.packageName»'''
	
	public static def ClassNameGenerator getChangePropagationSpecificationClassNameGenerator(Pair<VURI, VURI> metamodelPair) {
		return new ChangePropagationSpecificationClassNameGenerator(metamodelPair);
	}
	
	public static def ClassNameGenerator getExecutorClassNameGenerator(ReactionsSegment reactionSegment) {
		return new ExecutorClassNameGenerator(reactionSegment);
	}
	
	public static def ClassNameGenerator getRoutinesFacadeClassNameGenerator(ReactionsSegment reactionSegment) {
		return new RoutinesFacadeClassNameGenerator(reactionSegment);
	}
	
	public static def ClassNameGenerator getReactionClassNameGenerator(Reaction reaction) {
		return new ResponseClassNameGenerator(reaction);
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
		
		public new(Pair<VURI, VURI> metamodelPair) {
			this.metamodelPairName = metamodelPair.metamodelPairName;
		}
		
		public override getSimpleName() '''
			AbstractChangePropagationSpecification«metamodelPairName»'''
		
		public override getPackageName() '''
			«basicResponsesPackageQualifiedName»'''	
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
	
	private static class ResponseClassNameGenerator extends ClassNameGenerator {
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