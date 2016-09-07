package tools.vitruvius.dsls.response.jvmmodel.classgenerators

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmFormalParameter

import static extension tools.vitruvius.dsls.response.helper.ResponseLanguageHelper.*
import static extension tools.vitruvius.dsls.response.helper.EChangeHelper.*;
import tools.vitruvius.dsls.response.responseLanguage.Response
import tools.vitruvius.dsls.response.responseLanguage.ImplicitRoutine
import tools.vitruvius.framework.change.echange.EChange

class ImplicitRoutineClassGenerator extends RoutineClassGenerator {
	protected final Class<? extends EChange> change;
	protected final Response containingResponse;
	
	public new(ImplicitRoutine routine, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(routine, typesBuilderExtensionProvider);
		this.containingResponse = routine.containingResponse;
		this.change = containingResponse.trigger.generateEChangeInstanceClass();
	}
		
	protected override Iterable<JvmFormalParameter> generateInputParameters(EObject contextObject) {
		return #[contextObject.generateChangeParameter(containingResponse.trigger)];
	}
	
	override protected getInputParameterNames() {
		return #[ResponseLanguageParameterGenerator.CHANGE_PARAMETER_NAME];
	}
	
}