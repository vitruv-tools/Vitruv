package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmFormalParameter

import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ImplicitEffect
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.EChangeHelper.*;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response

class ImplicitEffectClassGenerator extends EffectClassGenerator {
	protected final Class<? extends EChange> change;
	protected final Response containingResponse;
	
	public new(ImplicitEffect effect, TypesBuilderExtensionProvider typesBuilderExtensionProvider, boolean hasEffectsFacade) {
		super(effect, typesBuilderExtensionProvider, hasEffectsFacade);
		this.containingResponse = effect.containingResponse;
		this.change = containingResponse.trigger.generateEChangeInstanceClass();
	}
		
	protected override Iterable<JvmFormalParameter> generateInputParameters(EObject contextObject) {
		return #[contextObject.generateChangeParameter(containingResponse.trigger)];
	}
	
}