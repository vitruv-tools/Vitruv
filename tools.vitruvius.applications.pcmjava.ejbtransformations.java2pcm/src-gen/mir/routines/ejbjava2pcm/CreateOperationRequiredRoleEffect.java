package mir.routines.ejbjava2pcm;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;

@SuppressWarnings("all")
public class CreateOperationRequiredRoleEffect extends AbstractEffectRealization {
  public CreateOperationRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final BasicComponent basicComponent, final OperationInterface opInterface, final Field field) {
    super(responseExecutionState, calledBy);
    				this.basicComponent = basicComponent;this.opInterface = opInterface;this.field = field;
  }
  
  private BasicComponent basicComponent;
  
  private OperationInterface opInterface;
  
  private Field field;
  
  private EObject getElement0(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole orr) {
    return orr;
  }
  
  private EObject getElement1(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole orr) {
    return field;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationRequiredRoleEffect with input:");
    getLogger().debug("   BasicComponent: " + this.basicComponent);
    getLogger().debug("   OperationInterface: " + this.opInterface);
    getLogger().debug("   Field: " + this.field);
    
    OperationRequiredRole orr = RepositoryFactoryImpl.eINSTANCE.createOperationRequiredRole();
    initializeCreateElementState(orr);
    
    addCorrespondenceBetween(getElement0(basicComponent, opInterface, field, orr), getElement1(basicComponent, opInterface, field, orr), "");
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreateOperationRequiredRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	basicComponent, opInterface, field, orr);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field, final OperationRequiredRole orr) {
      orr.setRequiringEntity_RequiredRole(basicComponent);
      orr.setRequiredInterface__OperationRequiredRole(opInterface);
      String _entityName = basicComponent.getEntityName();
      String _plus = (_entityName + "_requires_");
      String _entityName_1 = opInterface.getEntityName();
      String _plus_1 = (_plus + _entityName_1);
      orr.setEntityName(_plus_1);
    }
  }
}
