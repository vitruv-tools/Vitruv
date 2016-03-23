package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import java.util.List
import org.eclipse.xtext.common.types.JvmOperation
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExplicitEffect
import org.eclipse.xtext.common.types.JvmVisibility
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.EffectsGeneratorUtils.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.effects.AbstractEffectsFacade
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effect
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MetamodelPairResponses

class EffectsFacadeClassGenerator extends ClassGenerator {
	private val List<ExplicitEffect> effects;
	private val Effect facadeNameIdentifyingEffect;
	
	new(MetamodelPairResponses responsesContainer, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.effects = responsesContainer.effects;
		this.facadeNameIdentifyingEffect = (responsesContainer.effects + responsesContainer.responses.map[effect]).get(0);
	}
	
	public override generateClass() {
		generateUnassociatedClass(facadeNameIdentifyingEffect.qualifiedEffectsFacadeClassName) [
			superTypes += typeRef(AbstractEffectsFacade);
			members += toConstructor() [
				val responseExecutionStateParameter = generateResponseExecutionStateParameter(); 
				parameters += responseExecutionStateParameter;
				body = '''super(«responseExecutionStateParameter.name»);'''
			]
			members += effects.map[generateCallMethod];
		]
	}
	
	private def JvmOperation generateCallMethod(ExplicitEffect effect) {
		return effect.toMethod("call" + effect.name, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PUBLIC;
			parameters += generateMethodInputParameters(effect.modelInputElements, effect.javaInputElements);
			body = '''
				«effect.qualifiedClassName» effect = new «effect.qualifiedClassName»(this.executionState);
				«FOR parameter : parameters»
					effect.set«parameter.name.toFirstUpper»(«parameter.name»);
				«ENDFOR»
				effect.applyEffect();
				'''			
		]
	}
	
}