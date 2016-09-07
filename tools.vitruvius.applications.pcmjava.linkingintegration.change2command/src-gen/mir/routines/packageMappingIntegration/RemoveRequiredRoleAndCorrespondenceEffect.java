package mir.routines.packageMappingIntegration;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

@SuppressWarnings("all")
public class RemoveRequiredRoleAndCorrespondenceEffect extends AbstractEffectRealization {
  public RemoveRequiredRoleAndCorrespondenceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole orr, final Field field) {
    super(responseExecutionState, calledBy);
    				this.orr = orr;this.field = field;
  }
  
  private OperationRequiredRole orr;
  
  private Field field;
  
  private EObject getElement0(final OperationRequiredRole orr, final Field field) {
    return orr;
  }
  
  private EObject getElement1(final OperationRequiredRole orr, final Field field) {
    return field;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveRequiredRoleAndCorrespondenceEffect with input:");
    getLogger().debug("   OperationRequiredRole: " + this.orr);
    getLogger().debug("   Field: " + this.field);
    
    
    removeCorrespondenceBetween(getElement0(orr, field), getElement1(orr, field));
    preprocessElementStates();
    new mir.routines.packageMappingIntegration.RemoveRequiredRoleAndCorrespondenceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	orr, field);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final OperationRequiredRole orr, final Field field) {
      EcoreUtil.delete(orr);
    }
  }
}
