package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder

import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ImplicitEffect
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.EChangeHelper.*;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response

class ImplicitEffectClassGenerator extends EffectClassGenerator {
	protected final Class<? extends EChange> change;
	protected final Response containingResponse;
	protected final extension ResponseParameterGenerator responseParameterGenerator;
	 
	new(ImplicitEffect effect, JvmTypesBuilderWithoutAssociations jvmTypesBuilder, JvmTypeReferenceBuilder typeReferenceBuilder) {
		super(effect, jvmTypesBuilder, typeReferenceBuilder);
		this.containingResponse = effect.containingResponse;
		this.change = containingResponse.trigger.generateEChangeInstanceClass();
		this.responseParameterGenerator = new ResponseParameterGenerator(containingResponse, typeReferenceBuilder, jvmTypesBuilder);
	}
		
	protected override Iterable<JvmFormalParameter> generateInputParameters(EObject contextObject) {
		return #[generateChangeParameter(contextObject)];
	}
	
}