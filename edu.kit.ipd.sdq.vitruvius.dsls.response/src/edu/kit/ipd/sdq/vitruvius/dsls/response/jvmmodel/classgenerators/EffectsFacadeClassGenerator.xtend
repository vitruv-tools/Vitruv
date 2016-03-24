package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import java.util.List
import org.eclipse.xtext.common.types.JvmOperation
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExplicitEffect
import org.eclipse.xtext.common.types.JvmVisibility
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.effects.AbstractEffectsFacade
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effect
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MetamodelPairResponses
import static edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageConstants.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.CallHierarchyHaving
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.EffectsFacadeClassNameGenerator
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.EffectClassNameGenerator

class EffectsFacadeClassGenerator extends ClassGenerator {
	private val List<ExplicitEffect> effects;
	private val Effect facadeNameIdentifyingEffect;
	private val EffectsFacadeClassNameGenerator effectFacadeNameGenerator;
	
	new(MetamodelPairResponses responsesContainer, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.effects = responsesContainer.effects;
		this.facadeNameIdentifyingEffect = (responsesContainer.effects + responsesContainer.responses.map[effect]).get(0);
		this.effectFacadeNameGenerator = new EffectsFacadeClassNameGenerator(facadeNameIdentifyingEffect);
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
		val effectNameGenerator = new EffectClassNameGenerator(effect);
		return effect.toMethod("call" + effect.name, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PUBLIC;
			parameters += generateMethodInputParameters(effect.modelInputElements, effect.javaInputElements);
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