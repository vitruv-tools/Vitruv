package mir.routines.pcm2java;

import tools.vitruvius.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;

@SuppressWarnings("all")
public class ChangeParameterTypeEffect extends AbstractEffectRealization {
  public ChangeParameterTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OrdinaryParameter javaParameter, final DataType parameterType) {
    super(responseExecutionState, calledBy);
    				this.javaParameter = javaParameter;this.parameterType = parameterType;
  }
  
  private OrdinaryParameter javaParameter;
  
  private DataType parameterType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeParameterTypeEffect with input:");
    getLogger().debug("   OrdinaryParameter: " + this.javaParameter);
    getLogger().debug("   DataType: " + this.parameterType);
    
    org.emftext.language.java.classifiers.Class javaParameterTypeClass = getCorrespondingElement(
    	getCorrepondenceSourceJavaParameterTypeClass(javaParameter, parameterType), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(javaParameterTypeClass);
    
    preprocessElementStates();
    new mir.routines.pcm2java.ChangeParameterTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	javaParameter, parameterType, javaParameterTypeClass);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceJavaParameterTypeClass(final OrdinaryParameter javaParameter, final DataType parameterType) {
    return parameterType;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final OrdinaryParameter javaParameter, final DataType parameterType, final org.emftext.language.java.classifiers.Class javaParameterTypeClass) {
      final TypeReference parameterTypeReference = Pcm2JavaHelper.createTypeReference(parameterType, javaParameterTypeClass);
      javaParameter.setTypeReference(parameterTypeReference);
    }
  }
}
