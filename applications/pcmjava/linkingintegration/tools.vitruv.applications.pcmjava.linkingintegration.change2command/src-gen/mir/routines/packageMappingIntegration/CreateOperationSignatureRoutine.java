package mir.routines.packageMappingIntegration;

import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CreateOperationSignatureRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateOperationSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final OperationInterface opInterface, final Method newMethod) {
      return newMethod;
    }
    
    public void update0Element(final OperationInterface opInterface, final Method newMethod) {
      String _name = newMethod.getName();
      String _plus = ("Should the new method " + _name);
      String _plus_1 = (_plus + " be part of the OperationInterface ");
      String _entityName = opInterface.getEntityName();
      String _plus_2 = (_plus_1 + _entityName);
      String _plus_3 = (_plus_2 + "? ");
      final int selection = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, _plus_3, "Yes", "No");
      if ((selection == 0)) {
        OperationSignature opSig = RepositoryFactory.eINSTANCE.createOperationSignature();
        String _name_1 = newMethod.getName();
        opSig.setEntityName(_name_1);
        opSig.setInterface__OperationSignature(opInterface);
        CorrespondenceModelUtil.createAndAddCorrespondence(this.correspondenceModel, opSig, newMethod);
      }
    }
  }
  
  public CreateOperationSignatureRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationInterface opInterface, final Method newMethod) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.CreateOperationSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.opInterface = opInterface;this.newMethod = newMethod;
  }
  
  private OperationInterface opInterface;
  
  private Method newMethod;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationSignatureRoutine with input:");
    getLogger().debug("   OperationInterface: " + this.opInterface);
    getLogger().debug("   Method: " + this.newMethod);
    
    // val updatedElement userExecution.getElement1(opInterface, newMethod);
    userExecution.update0Element(opInterface, newMethod);
    
    postprocessElementStates();
  }
}
