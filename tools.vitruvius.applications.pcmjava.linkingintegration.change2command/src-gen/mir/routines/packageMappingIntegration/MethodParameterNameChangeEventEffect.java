package mir.routines.packageMappingIntegration;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.parameters.Parameter;

@SuppressWarnings("all")
public class MethodParameterNameChangeEventEffect extends AbstractEffectRealization {
  public MethodParameterNameChangeEventEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEAttribute<Parameter, String> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEAttribute<Parameter, String> change;
  
  private EObject getCorrepondenceSourcePcmParam(final ReplaceSingleValuedEAttribute<Parameter, String> change) {
    Parameter _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine MethodParameterNameChangeEventEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEAttribute: " + this.change);
    
    org.palladiosimulator.pcm.repository.Parameter pcmParam = getCorrespondingElement(
    	getCorrepondenceSourcePcmParam(change), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	null);
    if (pcmParam == null) {
    	return;
    }
    initializeRetrieveElementState(pcmParam);
    
    preprocessElementStates();
    new mir.routines.packageMappingIntegration.MethodParameterNameChangeEventEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, pcmParam);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEAttribute<Parameter, String> change, final org.palladiosimulator.pcm.repository.Parameter pcmParam) {
      String _oldValue = change.getOldValue();
      String _plus = ("Renamed method parameter " + _oldValue);
      String _plus_1 = (_plus + " to ");
      String _newValue = change.getNewValue();
      String _plus_2 = (_plus_1 + _newValue);
      this.userInteracting.showMessage(UserInteractionType.MODAL, _plus_2);
      String _newValue_1 = change.getNewValue();
      pcmParam.setEntityName(_newValue_1);
    }
  }
}
