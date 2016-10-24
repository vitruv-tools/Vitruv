package mir.routines.packageMappingIntegration;

import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.parameters.Parameter;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class MethodParameterNameChangedEventRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private MethodParameterNameChangedEventRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final Parameter parameter, final String oldParameterName, final String newParameterName, final org.palladiosimulator.pcm.repository.Parameter pcmParam) {
      return pcmParam;
    }
    
    public void update0Element(final Parameter parameter, final String oldParameterName, final String newParameterName, final org.palladiosimulator.pcm.repository.Parameter pcmParam) {
      this.userInteracting.showMessage(UserInteractionType.MODAL, ((("Renamed method parameter " + oldParameterName) + " to ") + newParameterName));
      pcmParam.setEntityName(newParameterName);
    }
    
    public EObject getCorrepondenceSourcePcmParam(final Parameter parameter, final String oldParameterName, final String newParameterName) {
      return parameter;
    }
  }
  
  public MethodParameterNameChangedEventRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Parameter parameter, final String oldParameterName, final String newParameterName) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.MethodParameterNameChangedEventRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.parameter = parameter;this.oldParameterName = oldParameterName;this.newParameterName = newParameterName;
  }
  
  private Parameter parameter;
  
  private String oldParameterName;
  
  private String newParameterName;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine MethodParameterNameChangedEventRoutine with input:");
    getLogger().debug("   Parameter: " + this.parameter);
    getLogger().debug("   String: " + this.oldParameterName);
    getLogger().debug("   String: " + this.newParameterName);
    
    org.palladiosimulator.pcm.repository.Parameter pcmParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmParam(parameter, oldParameterName, newParameterName), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	null);
    if (pcmParam == null) {
    	return;
    }
    initializeRetrieveElementState(pcmParam);
    // val updatedElement userExecution.getElement1(parameter, oldParameterName, newParameterName, pcmParam);
    userExecution.update0Element(parameter, oldParameterName, newParameterName, pcmParam);
    
    postprocessElementStates();
  }
}
