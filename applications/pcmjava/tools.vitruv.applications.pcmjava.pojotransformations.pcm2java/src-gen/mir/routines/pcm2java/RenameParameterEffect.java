package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameParameterEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final Parameter parameter, final OrdinaryParameter javaParameter) {
      return javaParameter;
    }
    
    public void update0Element(final Parameter parameter, final OrdinaryParameter javaParameter) {
      String _parameterName = parameter.getParameterName();
      javaParameter.setName(_parameterName);
    }
    
    public EObject getCorrepondenceSourceJavaParameter(final Parameter parameter) {
      return parameter;
    }
  }
  
  private RenameParameterEffect.EffectUserExecution userExecution;
  
  public RenameParameterEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Parameter parameter) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.RenameParameterEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.parameter = parameter;
  }
  
  private Parameter parameter;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameParameterEffect with input:");
    getLogger().debug("   Parameter: " + this.parameter);
    
    OrdinaryParameter javaParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaParameter(parameter), // correspondence source supplier
    	OrdinaryParameter.class,
    	(OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null);
    if (javaParameter == null) {
    	return;
    }
    initializeRetrieveElementState(javaParameter);
    // val updatedElement userExecution.getElement1(parameter, javaParameter);
    userExecution.update0Element(parameter, javaParameter);
    
    preprocessElementStates();
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
