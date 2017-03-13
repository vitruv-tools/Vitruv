package tools.vitruv.dsls.reactions.codegen.classgenerators

import java.util.List
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

class RoutineFacadeClassGenerator extends ClassGenerator {
	private val List<Routine> routines;
	private val ClassNameGenerator routinesFacadeNameGenerator;
	
	new(ReactionsSegment reactionsSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.routines = reactionsSegment.routines;
		this.routinesFacadeNameGenerator = reactionsSegment.routinesFacadeClassNameGenerator;
	}
	
	public override generateClass() {
		generateUnassociatedClass(routinesFacadeNameGenerator.qualifiedName) [
			superTypes += typeRef(AbstractRepairRoutinesFacade);
			members += toConstructor() [
				val reactionExecutionStateParameter = generateReactionExecutionStateParameter();
				val calledByParameter = generateParameter(EFFECT_FACADE_CALLED_BY_FIELD_NAME, typeRef(CallHierarchyHaving));
				parameters += reactionExecutionStateParameter;
				parameters += calledByParameter;
				body = '''super(«reactionExecutionStateParameter.name», «calledByParameter.name»);'''
			]
			members += routines.map[generateCallMethod];
		]
	}
	
	private def JvmOperation generateCallMethod(Routine routine) {
		val routineNameGenerator = routine.routineClassNameGenerator;
		return routine.toMethod(routine.name, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PUBLIC;
			parameters += generateMethodInputParameters(routine.input.modelInputElements, routine.input.javaInputElements);
			body = '''
				«routineNameGenerator.qualifiedName» effect = new «routineNameGenerator.qualifiedName»(this.executionState, «EFFECT_FACADE_CALLED_BY_FIELD_NAME»,
					«FOR parameter : parameters SEPARATOR ', '»«parameter.name»«ENDFOR»);
				effect.applyRoutine();
				'''			
		]
	}
	
}