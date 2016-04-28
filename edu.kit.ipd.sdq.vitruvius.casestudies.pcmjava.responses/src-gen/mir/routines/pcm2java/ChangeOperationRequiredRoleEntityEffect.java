package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

@SuppressWarnings("all")
public class ChangeOperationRequiredRoleEntityEffect extends AbstractEffectRealization {
  public ChangeOperationRequiredRoleEntityEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private UpdateSingleValuedNonContainmentEReference<InterfaceRequiringEntity> change;
  
  private boolean isChangeSet;
  
  public void setChange(final UpdateSingleValuedNonContainmentEReference<InterfaceRequiringEntity> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine ChangeOperationRequiredRoleEntityEffect with input:");
    getLogger().debug("   UpdateSingleValuedNonContainmentEReference: " + this.change);
    
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.ChangeOperationRequiredRoleEntityEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final UpdateSingleValuedNonContainmentEReference<InterfaceRequiringEntity> change) {
      EObject _newAffectedEObject = change.getNewAffectedEObject();
      final OperationRequiredRole requiredRole = ((OperationRequiredRole) _newAffectedEObject);
      InterfaceRequiringEntity _oldValue = change.getOldValue();
      this.effectFacade.callRemoveRequiredRole(requiredRole, _oldValue);
      this.effectFacade.callAddRequiredRole(requiredRole);
    }
  }
}
