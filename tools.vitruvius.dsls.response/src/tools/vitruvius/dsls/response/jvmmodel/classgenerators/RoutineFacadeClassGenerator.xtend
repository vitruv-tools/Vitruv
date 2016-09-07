package tools.vitruvius.dsls.response.jvmmodel.classgenerators

import java.util.List
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.JvmVisibility
import static tools.vitruvius.dsls.response.helper.ResponseLanguageConstants.*;
import static extension tools.vitruvius.dsls.response.helper.ResponseClassNamesGenerator.*;
import tools.vitruvius.dsls.response.responseLanguage.ResponsesSegment
import tools.vitruvius.dsls.response.helper.ResponseClassNamesGenerator.ClassNameGenerator
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectsFacade
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving
import tools.vitruvius.dsls.response.responseLanguage.ExplicitRoutine

class RoutineFacadeClassGenerator extends ClassGenerator {
	private val List<ExplicitRoutine> routines;
	private val ClassNameGenerator routinesFacadeNameGenerator;
	
	new(ResponsesSegment responsesSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.routines = responsesSegment.routines;
		this.routinesFacadeNameGenerator = responsesSegment.routinesFacadeClassNameGenerator;
	}
	
	public override generateClass() {
		generateUnassociatedClass(routinesFacadeNameGenerator.qualifiedName) [
			superTypes += typeRef(AbstractEffectsFacade);
			members += toConstructor() [
				val responseExecutionStateParameter = generateResponseExecutionStateParameter();
				val calledByParameter = generateParameter(EFFECT_FACADE_CALLED_BY_FIELD_NAME, typeRef(CallHierarchyHaving));
				parameters += responseExecutionStateParameter;
				parameters += calledByParameter;
				body = '''super(«responseExecutionStateParameter.name», «calledByParameter.name»);'''
			]
			members += routines.map[generateCallMethod];
		]
	}
	
	private def JvmOperation generateCallMethod(ExplicitRoutine routine) {
		val routineNameGenerator = routine.routineClassNameGenerator;
		return routine.toMethod("call" + routine.name, typeRef(Void.TYPE)) [
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