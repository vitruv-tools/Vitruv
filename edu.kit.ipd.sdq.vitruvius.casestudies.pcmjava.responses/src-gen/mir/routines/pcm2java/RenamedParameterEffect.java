package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.parameters.OrdinaryParameter;

@SuppressWarnings("all")
public class RenamedParameterEffect extends AbstractEffectRealization {
  public RenamedParameterEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final UpdateSingleValuedEAttribute<String> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private UpdateSingleValuedEAttribute<String> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenamedParameterEffect with input:");
    getLogger().debug("   UpdateSingleValuedEAttribute: " + this.change);
    
    OrdinaryParameter javaParameter = getCorrespondingElement(
    	getCorrepondenceSourceJavaParameter(change), // correspondence source supplier
    	OrdinaryParameter.class,
    	(OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null);
    if (javaParameter == null) {
    	return;
    }
    initializeRetrieveElementState(javaParameter);
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenamedParameterEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, javaParameter);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceJavaParameter(final UpdateSingleValuedEAttribute<String> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final UpdateSingleValuedEAttribute<String> change, final OrdinaryParameter javaParameter) {
      String _newValue = change.getNewValue();
      javaParameter.setName(_newValue);
    }
  }
}
