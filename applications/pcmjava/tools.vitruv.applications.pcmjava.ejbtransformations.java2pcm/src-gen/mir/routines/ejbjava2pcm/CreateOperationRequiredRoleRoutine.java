package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateOperationRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateOperationRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole orr) {
      return orr;
    }
    
    public EObject getElement2(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole orr) {
      return field;
    }
    
    public void updateOrrElement(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole orr) {
      orr.setRequiringEntity_RequiredRole(basicComponent);
      orr.setRequiredInterface__OperationRequiredRole(opInterface);
      String _entityName = basicComponent.getEntityName();
      String _plus = (_entityName + "_requires_");
      String _entityName_1 = opInterface.getEntityName();
      String _plus_1 = (_plus + _entityName_1);
      orr.setEntityName(_plus_1);
    }
  }
  
  public CreateOperationRequiredRoleRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final BasicComponent basicComponent, final OperationInterface opInterface, final Field field) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreateOperationRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
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
    userExecution.updateOrrElement(basicComponent, opInterface, field, orr);
    
    addCorrespondenceBetween(userExecution.getElement1(basicComponent, opInterface, field, orr), userExecution.getElement2(basicComponent, opInterface, field, orr), "");
    
    postprocessElementStates();
  }
}
