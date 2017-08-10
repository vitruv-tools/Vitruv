package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.JvmVisibility
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*;
import tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.ClassNameGenerator
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider
import org.eclipse.xtext.common.types.JvmGenericType

class RoutineFacadeClassGenerator extends ClassGenerator {
	val ReactionsSegment reactionsSegment
	val ClassNameGenerator routinesFacadeNameGenerator;

	new(ReactionsSegment reactionsSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.reactionsSegment = reactionsSegment
		this.routinesFacadeNameGenerator = reactionsSegment.routinesFacadeClassNameGenerator
	}

	public override generateEmptyClass() {
		reactionsSegment.toClass(routinesFacadeNameGenerator.qualifiedName)[]
	}

	override generateBody(JvmGenericType generatedClass) {
		generatedClass => [
			superTypes += typeRef(AbstractRepairRoutinesFacade);
			members += reactionsSegment.toConstructor() [
				val reactionExecutionStateParameter = generateReactionExecutionStateParameter();
				val calledByParameter = generateParameter(EFFECT_FACADE_CALLED_BY_FIELD_NAME,
					typeRef(CallHierarchyHaving));
				parameters += reactionExecutionStateParameter;
				parameters += calledByParameter;
				body = '''super(«reactionExecutionStateParameter.name», «calledByParameter.name»);'''
			]
			members += reactionsSegment.routines.map[generateCallMethod];
		]
	}

	private def JvmOperation generateCallMethod(Routine routine) {
		val routineNameGenerator = routine.routineClassNameGenerator;
		routine.associatePrimary(routine.toMethod(routine.name, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PUBLIC;
			parameters +=
				generateMethodInputParameters(routine.input.modelInputElements, routine.input.javaInputElements);
			body = '''
				«routineNameGenerator.qualifiedName» effect = new «routineNameGenerator.qualifiedName»(this.executionState, «EFFECT_FACADE_CALLED_BY_FIELD_NAME»«
					»«FOR parameter : parameters BEFORE ', ' SEPARATOR ', '»«parameter.name»«ENDFOR»);
				effect.applyRoutine();
			'''
		])
	}

}
