package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import java.util.List
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractEffectsFacade
import org.eclipse.xtext.common.types.JvmOperation
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExplicitEffect
import org.eclipse.xtext.common.types.JvmVisibility
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.EffectsGeneratorUtils.*;

class EffectsFacadeClassGenerator extends ClassGenerator {
	private val List<ExplicitEffect> effects;
	
	new(List<ExplicitEffect> effects, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.effects = effects;
	}
	
	public override generateClass() {
		if (effects.empty) {
			return null;
		}
		generateUnassociatedClass(effects.get(0).qualifiedEffectsFacadeClassName) [
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