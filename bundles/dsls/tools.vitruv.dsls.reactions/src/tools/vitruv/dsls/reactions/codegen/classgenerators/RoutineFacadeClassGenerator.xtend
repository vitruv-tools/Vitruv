package tools.vitruv.dsls.reactions.codegen.classgenerators

import java.util.Map
import org.eclipse.xtext.common.types.JvmConstructor
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsImportsHelper.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import org.eclipse.xtext.common.types.JvmGenericType
import tools.vitruv.dsls.common.helper.ClassNameGenerator
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState

class RoutineFacadeClassGenerator extends ClassGenerator {
	val ReactionsSegment reactionsSegment
	val ClassNameGenerator routinesFacadeNameGenerator;
	var Map<ReactionsSegment, ReactionsImportPath> includedRoutinesFacades;
	var JvmGenericType generatedClass

	new(ReactionsSegment reactionsSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		if (!reactionsSegment.isComplete) {
			throw new IllegalArgumentException("incomplete");
		}
		this.reactionsSegment = reactionsSegment;
		this.routinesFacadeNameGenerator = reactionsSegment.routinesFacadeClassNameGenerator;
	}

	public override generateEmptyClass() {
		generatedClass = reactionsSegment.toClass(routinesFacadeNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
		]
	}

	override generateBody() {
		this.includedRoutinesFacades =  reactionsSegment.includedRoutinesFacades;
		generatedClass => [
			superTypes += typeRef(AbstractRepairRoutinesFacade);
			members += generateConstructor();
			// fields for all routines facades of reactions segments imported with qualified names,
			// including transitively included routines facades for imports without qualified names:
			members += includedRoutinesFacades.entrySet.map [
				val includedReactionsSegment = it.key;
				val includedRoutinesFacadeFieldName = includedReactionsSegment.name;
				val includedRoutinesFacadeClassName = includedReactionsSegment.routinesFacadeClassNameGenerator.qualifiedName;
				reactionsSegment.toField(includedRoutinesFacadeFieldName, typeRef(includedRoutinesFacadeClassName)) [
					visibility = JvmVisibility.PUBLIC;
				]
			]
			// included routines: own routines and routines imported without qualified names, including transitively included routines,
			// with overridden routines being replaced
			members += reactionsSegment.includedRoutines.entrySet.map[generateCallMethod(it.key, it.value)];
		]
	}

	protected def JvmConstructor generateConstructor() {
		return reactionsSegment.toConstructor() [
			val routinesFacadesProviderParameter = generateParameter("routinesFacadesProvider", typeRef(RoutinesFacadesProvider));
			val reactionsImportPathParameter = generateParameter("reactionsImportPath", typeRef(ReactionsImportPath));
			val executionState = generateParameter("executionState", typeRef(RoutinesFacadeExecutionState));
			parameters += routinesFacadesProviderParameter;
			parameters += reactionsImportPathParameter;
			parameters += executionState;
			body = '''
			super(«routinesFacadesProviderParameter.name», «reactionsImportPathParameter.name», «executionState.name»);
			«this.getExtendedConstructorBody()»'''
		]
	}

	protected def String getExtendedConstructorBody() '''
		«FOR includedRoutinesFacadeEntry : includedRoutinesFacades.entrySet»
			«val includedReactionsSegment = includedRoutinesFacadeEntry.key»
			«val includedSegmentImportPath = includedRoutinesFacadeEntry.value»
			«val includedRoutinesFacadeFieldName = includedReactionsSegment.name»
			this.«includedRoutinesFacadeFieldName» = «includedSegmentImportPath.generateGetRoutinesFacadeCall»;
		«ENDFOR»
	'''

	// the reactions import path used here is absolute (starting with the root of the import hierarchy):
	protected def JvmOperation generateCallMethod(Routine routine, ReactionsImportPath absoluteImportPath) {
		val routineNameGenerator = routine.routineClassNameGenerator;
		val routinesFacadeNameGenerator = routine.reactionsSegment.routinesFacadeClassNameGenerator;
		routine.associatePrimary(routine.toMethod(routine.name, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PUBLIC;
			parameters +=
				generateMethodInputParameters(routine.input.modelInputElements, routine.input.javaInputElements);
			body = '''
				«routinesFacadeNameGenerator.qualifiedName» _routinesFacade = «absoluteImportPath.generateGetRoutinesFacadeCall»;
				«typeRef(ReactionExecutionState).qualifiedName» _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
				«typeRef(CallHierarchyHaving).qualifiedName» _caller = this._getExecutionState().getCaller();
				«routineNameGenerator.qualifiedName» routine = new «routineNameGenerator.qualifiedName»(_routinesFacade, _reactionExecutionState, _caller«
					»«FOR parameter : parameters BEFORE ', ' SEPARATOR ', '»«parameter.name»«ENDFOR»);
				return routine.applyRoutine();
			'''
		])
	}

	// absolute reactions import path, gets prepended by facade's parent import path:
	protected def String generateGetRoutinesFacadeCall(ReactionsImportPath reactionsImportPath) '''
		this._getRoutinesFacadesProvider().getRoutinesFacade(«typeRef(ReactionsImportPath).qualifiedName».fromPathString("«reactionsImportPath.pathString»").prepend(this._getParentImportPath()))'''
}
