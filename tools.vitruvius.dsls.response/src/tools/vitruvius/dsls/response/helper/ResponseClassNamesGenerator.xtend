package tools.vitruvius.dsls.response.helper

import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.util.datatypes.Pair
import org.eclipse.emf.common.util.URI
import tools.vitruvius.dsls.response.responseLanguage.Response
import static extension tools.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;
import tools.vitruvius.dsls.response.helper.XtendImportHelper
import tools.vitruvius.dsls.response.responseLanguage.ResponsesSegment
import tools.vitruvius.dsls.response.responseLanguage.Routine
import tools.vitruvius.dsls.response.responseLanguage.ImplicitRoutine
import tools.vitruvius.dsls.response.responseLanguage.ExplicitRoutine

final class ResponseClassNamesGenerator {
	private static String BASIC_PACKAGE = "mir";
	private static val FSA_SEPARATOR = "/";
	private static val XTEND_FILE_EXTENSION = ".java";
	private static val RESPONSES_PACKAGE = "responses";
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
	
	public static def String getBasicEffectsPackageQualifiedName() '''
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
	
	private static def String getMetamodelPairName(ResponsesSegment responsesSegment) {
		return responsesSegment.sourceTargetPair.metamodelPairName;
	}
	
	private static def String getPackageName(ResponsesSegment responsesSegment) '''
		responses«responsesSegment.metamodelPairName»'''
		
	private static def String getQualifiedPackageName(ResponsesSegment responsesSegment) '''
		«basicResponsesPackageQualifiedName».«responsesSegment.packageName»'''
	
	public static def ClassNameGenerator getChange2CommandTransformingClassNameGenerator(Pair<VURI, VURI> metamodelPair) {
		return new Change2CommandTransformingClassNameGenerator(metamodelPair);
	}
	
	public static def ClassNameGenerator getExecutorClassNameGenerator(ResponsesSegment responsesSegment) {
		return new ExecutorClassNameGenerator(responsesSegment);
	}
	
	public static def ClassNameGenerator getRoutinesFacadeClassNameGenerator(ResponsesSegment responsesSegment) {
		return new RoutinesFacadeClassNameGenerator(responsesSegment);
	}
	
	public static def ClassNameGenerator getResponseClassNameGenerator(Response response) {
		return new ResponseClassNameGenerator(response);
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
	
	private static class Change2CommandTransformingClassNameGenerator extends ClassNameGenerator {
		private val String metamodelPairName;
		
		public new(Pair<VURI, VURI> metamodelPair) {
			this.metamodelPairName = metamodelPair.metamodelPairName;
		}
		
		public override getSimpleName() '''
			AbstractChange2CommandTransforming«metamodelPairName»'''
		
		public override getPackageName() '''
			«basicResponsesPackageQualifiedName»'''	
	}	
	
	private static class ExecutorClassNameGenerator extends ClassNameGenerator {
		private val ResponsesSegment responsesSegment;
		
		public new(ResponsesSegment responsesSegment) {
			this.responsesSegment = responsesSegment;
		}
		
		public override getSimpleName() '''
			Executor«responsesSegment.metamodelPairName»'''
	
		public override getPackageName() '''
			«responsesSegment.qualifiedPackageName».«responsesSegment.name.toFirstLower»'''		
	}
	
	private static class ResponseClassNameGenerator extends ClassNameGenerator {
		private val Response response;
		public new(Response response) {
			this.response = response;
		}
		
		public override String getSimpleName() '''
			«response.name»Response'''
		
		public override String getPackageName() '''
			«response.responsesSegment.qualifiedPackageName».«response.responsesSegment.name.toFirstLower»'''		
	}
	
	private static class RoutineClassNameGenerator extends ClassNameGenerator {
		private val Routine routine;
		public new(Routine routine) {
			this.routine = routine;
		}
		
		public override String getSimpleName() '''
			«routine.routineName»Effect'''
		
		private static def dispatch String getRoutineName(ImplicitRoutine routine) {
			return routine.containingResponse.name
		}
		
		private static def dispatch String getRoutineName(ExplicitRoutine routine) {
			return routine.name
		}
		
		public override String getPackageName() '''
			«basicEffectsPackageQualifiedName».«routine.responsesSegment.name.toFirstLower»'''
		
	}
	
	private static class RoutinesFacadeClassNameGenerator extends ClassNameGenerator {
		private val ResponsesSegment responsesSegment;
		public new(ResponsesSegment responsesSegment) {
			this.responsesSegment = responsesSegment;
		}
		
		public override String getSimpleName() '''
			«ROUTINES_FACADE_CLASS_NAME»'''
		
		public override String getPackageName() '''
			«basicEffectsPackageQualifiedName».«responsesSegment.name.toFirstLower»'''		
	}
}