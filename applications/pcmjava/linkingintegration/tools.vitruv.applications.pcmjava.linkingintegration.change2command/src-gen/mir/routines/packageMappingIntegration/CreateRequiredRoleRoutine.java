package mir.routines.packageMappingIntegration;

import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CreateRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private CreateRequiredRoleRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole opRequiredRole) {
      return opRequiredRole;
    }
    
    public void update0Element(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole opRequiredRole) {
      opRequiredRole.setRequiredInterface__OperationRequiredRole(opInterface);
      opRequiredRole.setRequiringEntity_RequiredRole(basicComponent);
      String _entityName = basicComponent.getEntityName();
      String _plus = ("Create OperationRequiredRole between Component " + _entityName);
      String _plus_1 = (_plus + " and Interface ");
      String _entityName_1 = opInterface.getEntityName();
      String _plus_2 = (_plus_1 + _entityName_1);
      this.userInteracting.showMessage(UserInteractionType.MODAL, _plus_2);
    }
    
    public EObject getElement2(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole opRequiredRole) {
      return field;
    }
    
    public EObject getElement3(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole opRequiredRole) {
      return opRequiredRole;
    }
  }
  
  public CreateRequiredRoleRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final BasicComponent basicComponent, final OperationInterface opInterface, final Field field) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.CreateRequiredRoleRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.basicComponent = basicComponent;this.opInterface = opInterface;this.field = field;
  }
  
  private BasicComponent basicComponent;
  
  private OperationInterface opInterface;
  
  private Field field;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRequiredRoleRoutine with input:");
    getLogger().debug("   BasicComponent: " + this.basicComponent);
    getLogger().debug("   OperationInterface: " + this.opInterface);
    getLogger().debug("   Field: " + this.field);
    
    OperationRequiredRole opRequiredRole = RepositoryFactoryImpl.eINSTANCE.createOperationRequiredRole();
    initializeCreateElementState(opRequiredRole);
    
    addCorrespondenceBetween(userExecution.getElement1(basicComponent, opInterface, field, opRequiredRole), userExecution.getElement2(basicComponent, opInterface, field, opRequiredRole), "");
    
    // val updatedElement userExecution.getElement3(basicComponent, opInterface, field, opRequiredRole);
    userExecution.update0Element(basicComponent, opInterface, field, opRequiredRole);
    
    postprocessElementStates();
  }
}
