package tools.vitruv.dsls.reactions.codegen.classgenerators

import java.util.LinkedHashMap
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.JvmVisibility
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static extension  tools.vitruv.dsls.reactions.codegen.helper.RoutineCallMethodNames.*
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
		generatedClass = reactionsSegment.toClass(routinesFacadeNameGenerator.qualifiedName)[]
	}

	override generateBody() {
		val routinesByMethodNames = new LinkedHashMap<String, Routine>();
		// routines:
		for (routine : reactionsSegment.routines) {
			routinesByMethodNames.put(routine.callMethodName, routine);
		}
		// imported routines:
		for (routinesImport : reactionsSegment.reactionsFile.routinesImports) {
			for (routine : routinesImport.reactionsSegment.routines) {
				routinesByMethodNames.putIfAbsent(routinesImport.getImportedCallMethodName(routine), routine)
			}
		}

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
			members += routinesByMethodNames.entrySet.map[generateCallMethod(value, key)];
		]
	}

	private def JvmOperation generateCallMethod(Routine routine, String methodName) {
		val routineNameGenerator = routine.routineClassNameGenerator;
		routine.associatePrimary(routine.toMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PUBLIC;
			parameters +=
				generateMethodInputParameters(routine.input.modelInputElements, routine.input.javaInputElements);
			body = '''
				«routineNameGenerator.qualifiedName» effect = new «routineNameGenerator.qualifiedName»(this.executionState, «EFFECT_FACADE_CALLED_BY_FIELD_NAME»«
					»«FOR parameter : parameters BEFORE ', ' SEPARATOR ', '»«parameter.name»«ENDFOR»);
				return effect.applyRoutine();
			'''
		])
	}

}
