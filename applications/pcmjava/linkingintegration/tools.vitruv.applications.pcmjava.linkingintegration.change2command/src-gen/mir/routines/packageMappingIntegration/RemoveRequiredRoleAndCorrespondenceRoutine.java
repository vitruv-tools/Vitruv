package mir.routines.packageMappingIntegration;

import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveRequiredRoleAndCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private RemoveRequiredRoleAndCorrespondenceRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final OperationRequiredRole orr, final Field field) {
      return orr;
    }
    
    public void update0Element(final OperationRequiredRole orr, final Field field) {
      EcoreUtil.delete(orr);
    }
    
    public EObject getElement2(final OperationRequiredRole orr, final Field field) {
      return field;
    }
    
    public EObject getElement3(final OperationRequiredRole orr, final Field field) {
      return orr;
    }
  }
  
  public RemoveRequiredRoleAndCorrespondenceRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole orr, final Field field) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.RemoveRequiredRoleAndCorrespondenceRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.orr = orr;this.field = field;
  }
  
  private OperationRequiredRole orr;
  
  private Field field;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveRequiredRoleAndCorrespondenceRoutine with input:");
    getLogger().debug("   OperationRequiredRole: " + this.orr);
    getLogger().debug("   Field: " + this.field);
    
    removeCorrespondenceBetween(userExecution.getElement1(orr, field), userExecution.getElement2(orr, field));
    
    // val updatedElement userExecution.getElement3(orr, field);
    userExecution.update0Element(orr, field);
    
    postprocessElementStates();
  }
}
