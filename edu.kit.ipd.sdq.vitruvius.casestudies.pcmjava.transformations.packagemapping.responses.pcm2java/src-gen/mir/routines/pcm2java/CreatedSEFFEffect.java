package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

@SuppressWarnings("all")
public class CreatedSEFFEffect extends AbstractEffectRealization {
  public CreatedSEFFEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<BasicComponent, ServiceEffectSpecification> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<BasicComponent, ServiceEffectSpecification> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedSEFFEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreatedSEFFEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<BasicComponent, ServiceEffectSpecification> change) {
      ServiceEffectSpecification _newValue = change.getNewValue();
      this.effectFacade.callCreateSEFF(_newValue);
    }
  }
}
