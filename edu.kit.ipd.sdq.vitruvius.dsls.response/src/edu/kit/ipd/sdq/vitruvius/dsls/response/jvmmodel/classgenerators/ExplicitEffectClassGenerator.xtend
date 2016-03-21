package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExplicitEffect
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmFormalParameter
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.ModelElement
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.NamedJavaElement

class ExplicitEffectClassGenerator extends EffectClassGenerator {
	private final List<ModelElement> modelInputElements;
	private final List<NamedJavaElement> javaInputElements;
	
	public new(ExplicitEffect effect, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(effect, typesBuilderExtensionProvider);
		modelInputElements = effect.modelInputElements;
		javaInputElements = effect.javaInputElements;
	}
	
	
	protected override Iterable<JvmFormalParameter> generateInputParameters(EObject contextObject) {
		return generateMethodInputParameters(contextObject, modelInputElements, javaInputElements);
	}
	
}