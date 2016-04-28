package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;

@SuppressWarnings("all")
public class AddRequiredRoleToInterfaceRequiringEntityEffect extends AbstractEffectRealization {
  public AddRequiredRoleToInterfaceRequiringEntityEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateNonRootEObjectInList<RequiredRole> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateNonRootEObjectInList<RequiredRole> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine AddRequiredRoleToInterfaceRequiringEntityEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.AddRequiredRoleToInterfaceRequiringEntityEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CreateNonRootEObjectInList<RequiredRole> change) {
      RequiredRole _newValue = change.getNewValue();
      this.effectFacade.callAddRequiredRole(((OperationRequiredRole) _newValue));
    }
  }
}
