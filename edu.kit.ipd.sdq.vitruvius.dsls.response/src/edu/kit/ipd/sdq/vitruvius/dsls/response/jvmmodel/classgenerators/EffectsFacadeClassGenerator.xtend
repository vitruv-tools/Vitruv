package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import java.util.List
import org.eclipse.xtext.common.types.JvmOperation
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExplicitEffect
import org.eclipse.xtext.common.types.JvmVisibility
import static edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageConstants.*;
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponsesSegment
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.ClassNameGenerator
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectsFacade
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving

class EffectsFacadeClassGenerator extends ClassGenerator {
	private val List<ExplicitEffect> effects;
	private val ClassNameGenerator effectFacadeNameGenerator;
	
	new(ResponsesSegment responsesSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.effects = responsesSegment.effects;
		this.effectFacadeNameGenerator = responsesSegment.effectsFacadeClassNameGenerator;
	}
	
	public override generateClass() {
		generateUnassociatedClass(effectFacadeNameGenerator.qualifiedName) [
			superTypes += typeRef(AbstractEffectsFacade);
			members += toConstructor() [
				val responseExecutionStateParameter = generateResponseExecutionStateParameter();
				val calledByParameter = generateParameter(EFFECT_FACADE_CALLED_BY_FIELD_NAME, typeRef(CallHierarchyHaving));
				parameters += responseExecutionStateParameter;
				parameters += calledByParameter;
				body = '''super(«responseExecutionStateParameter.name», «calledByParameter.name»);'''
			]
			members += effects.map[generateCallMethod];
		]
	}
	
	private def JvmOperation generateCallMethod(ExplicitEffect effect) {
		val effectNameGenerator = effect.effectClassNameGenerator;
		return effect.toMethod("call" + effect.name, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PUBLIC;
			parameters += generateMethodInputParameters(effect.input.modelInputElements, effect.input.javaInputElements);
			body = '''
				«effectNameGenerator.qualifiedName» effect = new «effectNameGenerator.qualifiedName»(this.executionState, «EFFECT_FACADE_CALLED_BY_FIELD_NAME»);
				«FOR parameter : parameters»
					effect.set«parameter.name.toFirstUpper»(«parameter.name»);
				«ENDFOR»
				effect.applyEffect();
				'''			
		]
	}
	
}