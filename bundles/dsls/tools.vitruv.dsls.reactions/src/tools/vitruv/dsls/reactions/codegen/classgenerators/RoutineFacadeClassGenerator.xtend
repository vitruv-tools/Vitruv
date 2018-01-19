package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmConstructor
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.JvmVisibility
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*
import static extension tools.vitruv.dsls.reactions.util.ReactionsLanguageUtil.*
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import org.eclipse.xtext.common.types.JvmGenericType
import tools.vitruv.dsls.common.helper.ClassNameGenerator

class RoutineFacadeClassGenerator extends ClassGenerator {
	val ReactionsSegment reactionsSegment
	val ClassNameGenerator routinesFacadeNameGenerator;
	var JvmGenericType generatedClass
	
	new(ReactionsSegment reactionsSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.reactionsSegment = reactionsSegment
		this.routinesFacadeNameGenerator = reactionsSegment.routinesFacadeClassNameGenerator
	}

	public override generateEmptyClass() {
		generatedClass = reactionsSegment.toClass(routinesFacadeNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
		]
	}

	override generateBody() {
		generatedClass => [
			superTypes += typeRef(AbstractRepairRoutinesFacade);
			members += generateBaseConstructor().setupConstructor();
			// fields for all directly imported routines facades:
			for (reactionsImport : reactionsSegment.reactionsImports) {
				val importedReactionsSegment = reactionsImport.importedReactionsSegment;
				val importedRoutinesFacadeFieldName = importedReactionsSegment.importedRoutinesFacadeFieldName;
				val importedRoutinesFacadeClassName = reactionsSegment.getImportedRoutinesFacadeClassNameGenerator(importedReactionsSegment.name).qualifiedName;
				members += reactionsSegment.toField(importedRoutinesFacadeFieldName, typeRef(importedRoutinesFacadeClassName))[
					visibility = JvmVisibility.PUBLIC;
				]
			}
			// routines:
			members += reactionsSegment.regularRoutines.map[generateCallMethod];
		]
	}

	protected final def JvmConstructor generateBaseConstructor() {
		return reactionsSegment.toConstructor() [
			val executorParameter = generateExecutorParameter();
			val reactionExecutionStateParameter = generateReactionExecutionStateParameter();
			val calledByParameter = generateParameter(EFFECT_FACADE_CALLED_BY_FIELD_NAME, typeRef(CallHierarchyHaving));
			parameters += executorParameter;
			parameters += reactionExecutionStateParameter;
			parameters += calledByParameter;
			body = '''
			super(«executorParameter.name», «reactionExecutionStateParameter.name», «calledByParameter.name»);'''
		]
	}

	private def JvmConstructor setupConstructor(JvmConstructor constructor) {
		constructor.body = '''
		super(«EXECUTOR_PARAMETER_NAME», «REACTION_EXECUTION_STATE_PARAMETER_NAME», «EFFECT_FACADE_CALLED_BY_FIELD_NAME»);
		«FOR reactionsImport : reactionsSegment.reactionsImports»
		«val importedReactionsSegment = reactionsImport.importedReactionsSegment»
		«val importedRoutinesFacadeFieldName = importedReactionsSegment.importedRoutinesFacadeFieldName»
		this.«importedRoutinesFacadeFieldName» = «EXECUTOR_PARAMETER_NAME».«EXECUTOR_ROUTINES_FACADE_FACTORY_METHOD_NAME»("«importedReactionsSegment.name»", «REACTION_EXECUTION_STATE_PARAMETER_NAME», «EFFECT_FACADE_CALLED_BY_FIELD_NAME»);
		«ENDFOR»''';
		return constructor;
	}

	protected def JvmOperation generateCallMethod(Routine routine) {
		val routineNameGenerator = routine.routineClassNameGenerator;
		routine.associatePrimary(routine.toMethod(routine.callMethodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PUBLIC;
			annotations += annotationRef(Override);
			parameters +=
				generateMethodInputParameters(routine.input.modelInputElements, routine.input.javaInputElements);
			body = '''
				«routineNameGenerator.qualifiedName» effect = new «routineNameGenerator.qualifiedName»(this.«EXECUTOR_FIELD_NAME», this.executionState, this.«EFFECT_FACADE_CALLED_BY_FIELD_NAME»«
					»«FOR parameter : parameters BEFORE ', ' SEPARATOR ', '»«parameter.name»«ENDFOR»);
				return effect.applyRoutine();
			'''
		])
	}
}
