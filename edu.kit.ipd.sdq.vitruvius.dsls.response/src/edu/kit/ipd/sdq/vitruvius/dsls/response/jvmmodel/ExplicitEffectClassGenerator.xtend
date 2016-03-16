package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExplicitEffect
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.ModelElement
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.NamedJavaElement

class ExplicitEffectClassGenerator extends EffectClassGenerator {
	private List<ModelElement> modelInputElements;
	private List<NamedJavaElement> javaInputElements;
	
	new(ExplicitEffect effect, JvmTypesBuilderWithoutAssociations jvmTypesBuilder, JvmTypeReferenceBuilder typeReferenceBuilder) {
		super(effect, jvmTypesBuilder, typeReferenceBuilder);
		modelInputElements = effect.modelInputElements;
		javaInputElements = effect.javaInputElements;
	}
		
	protected override Iterable<JvmFormalParameter> generateInputParameters(EObject contextObject) {
		return generateMethodInputParameters(contextObject, modelInputElements, javaInputElements);
	}
	
}