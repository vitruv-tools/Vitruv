package mir.routines.ejbjava2pcm;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import java.util.function.Consumer;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;

@SuppressWarnings("all")
public class CreateOperationSignatureEffect extends AbstractEffectRealization {
  public CreateOperationSignatureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InterfaceMethod interfaceMethod, final OperationInterface opInterface) {
    super(responseExecutionState, calledBy);
    				this.interfaceMethod = interfaceMethod;this.opInterface = opInterface;
  }
  
  private InterfaceMethod interfaceMethod;
  
  private OperationInterface opInterface;
  
  private EObject getElement0(final InterfaceMethod interfaceMethod, final OperationInterface opInterface, final OperationSignature opSignature) {
    return opSignature;
  }
  
  private EObject getElement1(final InterfaceMethod interfaceMethod, final OperationInterface opInterface, final OperationSignature opSignature) {
    return interfaceMethod;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationSignatureEffect with input:");
    getLogger().debug("   InterfaceMethod: " + this.interfaceMethod);
    getLogger().debug("   OperationInterface: " + this.opInterface);
    
    OperationSignature opSignature = RepositoryFactoryImpl.eINSTANCE.createOperationSignature();
    initializeCreateElementState(opSignature);
    
    addCorrespondenceBetween(getElement0(interfaceMethod, opInterface, opSignature), getElement1(interfaceMethod, opInterface, opSignature), "");
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreateOperationSignatureEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	interfaceMethod, opInterface, opSignature);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InterfaceMethod interfaceMethod, final OperationInterface opInterface, final OperationSignature opSignature) {
      String _name = interfaceMethod.getName();
      opSignature.setEntityName(_name);
      EList<OperationSignature> _signatures__OperationInterface = opInterface.getSignatures__OperationInterface();
      _signatures__OperationInterface.add(opSignature);
      EList<Parameter> _parameters = interfaceMethod.getParameters();
      final Consumer<Parameter> _function = (Parameter it) -> {
        this.effectFacade.callCreatePCMParameter(it, opSignature);
      };
      _parameters.forEach(_function);
      TypeReference _typeReference = interfaceMethod.getTypeReference();
      this.effectFacade.callCreatePCMReturnType(_typeReference, opSignature, interfaceMethod);
    }
  }
}
