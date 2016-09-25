package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateOperationRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private CreateOperationRequiredRoleRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole orr) {
      return orr;
    }
    
    public void update0Element(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole orr) {
      orr.setRequiringEntity_RequiredRole(basicComponent);
      orr.setRequiredInterface__OperationRequiredRole(opInterface);
      String _entityName = basicComponent.getEntityName();
      String _plus = (_entityName + "_requires_");
      String _entityName_1 = opInterface.getEntityName();
      String _plus_1 = (_plus + _entityName_1);
      orr.setEntityName(_plus_1);
    }
    
    public EObject getElement2(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole orr) {
      return field;
    }
    
    public EObject getElement3(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole orr) {
      return orr;
    }
  }
  
  public CreateOperationRequiredRoleRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final BasicComponent basicComponent, final OperationInterface opInterface, final Field field) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreateOperationRequiredRoleRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.basicComponent = basicComponent;this.opInterface = opInterface;this.field = field;
  }
  
  private BasicComponent basicComponent;
  
  private OperationInterface opInterface;
  
  private Field field;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationRequiredRoleRoutine with input:");
    getLogger().debug("   BasicComponent: " + this.basicComponent);
    getLogger().debug("   OperationInterface: " + this.opInterface);
    getLogger().debug("   Field: " + this.field);
    
    OperationRequiredRole orr = RepositoryFactoryImpl.eINSTANCE.createOperationRequiredRole();
    initializeCreateElementState(orr);
    
    addCorrespondenceBetween(userExecution.getElement1(basicComponent, opInterface, field, orr), userExecution.getElement2(basicComponent, opInterface, field, orr), "");
    
    // val updatedElement userExecution.getElement3(basicComponent, opInterface, field, orr);
    userExecution.update0Element(basicComponent, opInterface, field, orr);
    
    postprocessElementStates();
  }
}
