package tools.vitruvius.dsls.response.jvmmodel.classgenerators

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmFormalParameter
import tools.vitruvius.dsls.mirbase.mirBase.NamedJavaElement
import tools.vitruvius.dsls.mirbase.mirBase.ModelElement
import tools.vitruvius.dsls.response.responseLanguage.ExplicitRoutine

class ExplicitRoutineClassGenerator extends RoutineClassGenerator {
	private final List<ModelElement> modelInputElements;
	private final List<NamedJavaElement> javaInputElements;
	
	public new(ExplicitRoutine routine, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(routine, typesBuilderExtensionProvider);
		modelInputElements = routine.input.modelInputElements;
		javaInputElements = routine.input.javaInputElements;
	}
		
	protected override Iterable<JvmFormalParameter> generateInputParameters(EObject contextObject) {
		return generateMethodInputParameters(contextObject, modelInputElements, javaInputElements);
	}
	
	override protected getInputParameterNames() {
		return modelInputElements.map[name]+ javaInputElements.map[name];
	}
	
}