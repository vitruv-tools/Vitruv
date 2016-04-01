package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
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
    getLogger().debug("Called effect AddRequiredRoleToInterfaceRequiringEntityEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    preProcessElements();
    new mir.effects.pcm2java.AddRequiredRoleToInterfaceRequiringEntityEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change);
    postProcessElements();
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CreateNonRootEObjectInList<RequiredRole> change) {
      RequiredRole _newValue = change.getNewValue();
      this.effectFacade.callAddRequiredRole(((OperationRequiredRole) _newValue));
    }
  }
}
