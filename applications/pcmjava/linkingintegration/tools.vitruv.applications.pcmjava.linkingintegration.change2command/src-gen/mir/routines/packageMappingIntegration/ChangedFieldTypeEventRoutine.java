package mir.routines.packageMappingIntegration;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class ChangedFieldTypeEventRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangedFieldTypeEventRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceBasicComponent(final Field field, final TypeReference oldType, final TypeReference newType, final OperationInterface oldCorrespondingOpInterface, final OperationInterface opInterface, final OperationRequiredRole opRequiredRole) {
      ConcreteClassifier _containingConcreteClassifier = field.getContainingConcreteClassifier();
      return _containingConcreteClassifier;
    }
    
    public EObject getCorrepondenceSourceOldCorrespondingOpInterface(final Field field, final TypeReference oldType, final TypeReference newType) {
      return oldType;
    }
    
    public EObject getCorrepondenceSourceOpRequiredRole(final Field field, final TypeReference oldType, final TypeReference newType, final OperationInterface oldCorrespondingOpInterface, final OperationInterface opInterface) {
      return field;
    }
    
    public EObject getCorrepondenceSourceOpInterface(final Field field, final TypeReference oldType, final TypeReference newType, final OperationInterface oldCorrespondingOpInterface) {
      return newType;
    }
    
    public void callRoutine1(final Field field, final TypeReference oldType, final TypeReference newType, final OperationInterface oldCorrespondingOpInterface, final OperationInterface opInterface, final OperationRequiredRole opRequiredRole, final BasicComponent basicComponent, @Extension final RoutinesFacade _routinesFacade) {
      if ((((!Objects.equal(null, oldCorrespondingOpInterface)) && (!Objects.equal(null, opInterface))) && (!Objects.equal(null, opRequiredRole)))) {
        this.userInteracting.showMessage(UserInteractionType.MODAL, "the operation required role has been changed");
        opRequiredRole.setRequiredInterface__OperationRequiredRole(opInterface);
        return;
      }
      if ((Objects.equal(null, oldCorrespondingOpInterface) && (!Objects.equal(null, opInterface)))) {
        String _entityName = basicComponent.getEntityName();
        String _plus = ("Create OperationRequiredRole between Component " + _entityName);
        String _plus_1 = (_plus + " and Interface ");
        String _entityName_1 = opInterface.getEntityName();
        String _plus_2 = (_plus_1 + _entityName_1);
        this.userInteracting.showMessage(UserInteractionType.MODAL, _plus_2);
        _routinesFacade.createRequiredRole(basicComponent, opInterface, field);
        return;
      }
      if ((((!Objects.equal(null, oldCorrespondingOpInterface)) && Objects.equal(null, opInterface)) && (!Objects.equal(null, opRequiredRole)))) {
        String _entityName_2 = basicComponent.getEntityName();
        String _plus_3 = ("Remove OperationRequiredRole between Component " + _entityName_2);
        String _plus_4 = (_plus_3 + " and Interface ");
        String _entityName_3 = oldCorrespondingOpInterface.getEntityName();
        String _plus_5 = (_plus_4 + _entityName_3);
        this.userInteracting.showMessage(UserInteractionType.MODAL, _plus_5);
        _routinesFacade.removeRequiredRoleAndCorrespondence(opRequiredRole, field);
      }
    }
  }
  
  public ChangedFieldTypeEventRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Field field, final TypeReference oldType, final TypeReference newType) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.ChangedFieldTypeEventRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.field = field;this.oldType = oldType;this.newType = newType;
  }
  
  private Field field;
  
  private TypeReference oldType;
  
  private TypeReference newType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangedFieldTypeEventRoutine with input:");
    getLogger().debug("   Field: " + this.field);
    getLogger().debug("   TypeReference: " + this.oldType);
    getLogger().debug("   TypeReference: " + this.newType);
    
    OperationInterface oldCorrespondingOpInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOldCorrespondingOpInterface(field, oldType, newType), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(oldCorrespondingOpInterface);
    OperationInterface opInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpInterface(field, oldType, newType, oldCorrespondingOpInterface), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(opInterface);
    OperationRequiredRole opRequiredRole = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpRequiredRole(field, oldType, newType, oldCorrespondingOpInterface, opInterface), // correspondence source supplier
    	OperationRequiredRole.class,
    	(OperationRequiredRole _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(opRequiredRole);
    BasicComponent basicComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceBasicComponent(field, oldType, newType, oldCorrespondingOpInterface, opInterface, opRequiredRole), // correspondence source supplier
    	BasicComponent.class,
    	(BasicComponent _element) -> true, // correspondence precondition checker
    	null);
    if (basicComponent == null) {
    	return;
    }
    initializeRetrieveElementState(basicComponent);
    userExecution.callRoutine1(field, oldType, newType, oldCorrespondingOpInterface, opInterface, opRequiredRole, basicComponent, actionsFacade);
    
    postprocessElementStates();
  }
}
