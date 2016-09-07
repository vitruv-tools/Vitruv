package mir.routines.packageMappingIntegration;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;

@SuppressWarnings("all")
public class CreateRequiredRoleEffect extends AbstractEffectRealization {
  public CreateRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final BasicComponent basicComponent, final OperationInterface opInterface, final Field field) {
    super(responseExecutionState, calledBy);
    				this.basicComponent = basicComponent;this.opInterface = opInterface;this.field = field;
  }
  
  private BasicComponent basicComponent;
  
  private OperationInterface opInterface;
  
  private Field field;
  
  private EObject getElement0(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole opRequiredRole) {
    return opRequiredRole;
  }
  
  private EObject getElement1(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole opRequiredRole) {
    return field;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRequiredRoleEffect with input:");
    getLogger().debug("   BasicComponent: " + this.basicComponent);
    getLogger().debug("   OperationInterface: " + this.opInterface);
    getLogger().debug("   Field: " + this.field);
    
    OperationRequiredRole opRequiredRole = RepositoryFactoryImpl.eINSTANCE.createOperationRequiredRole();
    initializeCreateElementState(opRequiredRole);
    
    addCorrespondenceBetween(getElement0(basicComponent, opInterface, field, opRequiredRole), getElement1(basicComponent, opInterface, field, opRequiredRole), "");
    preprocessElementStates();
    new mir.routines.packageMappingIntegration.CreateRequiredRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	basicComponent, opInterface, field, opRequiredRole);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole opRequiredRole) {
      opRequiredRole.setRequiredInterface__OperationRequiredRole(opInterface);
      opRequiredRole.setRequiringEntity_RequiredRole(basicComponent);
      String _entityName = basicComponent.getEntityName();
      String _plus = ("Create OperationRequiredRole between Component " + _entityName);
      String _plus_1 = (_plus + " and Interface ");
      String _entityName_1 = opInterface.getEntityName();
      String _plus_2 = (_plus_1 + _entityName_1);
      this.userInteracting.showMessage(UserInteractionType.MODAL, _plus_2);
    }
  }
}
