package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel

import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.ModelElement
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExplicitEffect
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder

import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*

class ExplicitEffectClassGenerator extends EffectClassGenerator {
	private List<ModelElement> inputElements;
	
	new(ExplicitEffect effect, JvmTypesBuilderWithoutAssociations jvmTypesBuilder, JvmTypeReferenceBuilder typeReferenceBuilder) {
		super(effect, jvmTypesBuilder, typeReferenceBuilder);
		inputElements = effect.inputElements;
	}
		
	protected override Iterable<JvmFormalParameter> generateInputParameters(EObject contextObject) {
		return inputElements.map[generateParameter(contextObject, it.name, it.element.javaClass)];
	}
	
}