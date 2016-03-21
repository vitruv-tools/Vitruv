package edu.kit.ipd.sdq.vitruvius.dsls.response.generator

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effect
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExplicitEffect
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ImplicitEffect
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;

class EffectsGeneratorUtils extends GeneratorUtils {
	private static String EFFECTS_PACKAGE = "effects";
	private static String EFFECTS_FACADE_CLASS_NAME = "EffectsFacade";
	
	public static def String getQualifiedClassName(Effect effect) '''
		«getPackageQualifiedName(effect.eResource.getPackageNameForResource)».«effect.simpleClassName»'''
		
	public static def String getQualifiedEffectsFacadeClassName(Effect effect) '''
		«getPackageQualifiedName(effect.eResource.getPackageNameForResource)».«effect.simpleEffectsFacadeClassName»'''
		
	public static def getSimpleEffectsFacadeClassName(Effect effect) '''
		«EFFECTS_FACADE_CLASS_NAME»'''
	
	public static def dispatch getSimpleClassName(ExplicitEffect effect) '''
		«effect.name»Effect'''
	
	public static def dispatch getSimpleClassName(ImplicitEffect effect) '''
		«effect.containingResponse.name»Effect'''

	private static def String getPackageQualifiedName(String sourceFileName) '''
		«getBasicEffectsPackageQualifiedName».«sourceFileName»'''

	static def String getBasicEffectsPackageQualifiedName() '''
		«basicPackageQualifiedName».«EFFECTS_PACKAGE»'''		
}
