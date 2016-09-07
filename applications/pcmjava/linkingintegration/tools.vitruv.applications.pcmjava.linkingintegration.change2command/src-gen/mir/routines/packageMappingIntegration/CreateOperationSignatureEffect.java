package mir.routines.packageMappingIntegration;

import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import tools.vitruv.framework.userinteraction.UserInteractionType;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

@SuppressWarnings("all")
public class CreateOperationSignatureEffect extends AbstractEffectRealization {
  public CreateOperationSignatureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationInterface opInterface, final Method newMethod) {
    super(responseExecutionState, calledBy);
    				this.opInterface = opInterface;this.newMethod = newMethod;
  }
  
  private OperationInterface opInterface;
  
  private Method newMethod;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationSignatureEffect with input:");
    getLogger().debug("   OperationInterface: " + this.opInterface);
    getLogger().debug("   Method: " + this.newMethod);
    
    
    preprocessElementStates();
    new mir.routines.packageMappingIntegration.CreateOperationSignatureEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	opInterface, newMethod);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final OperationInterface opInterface, final Method newMethod) {
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
}
