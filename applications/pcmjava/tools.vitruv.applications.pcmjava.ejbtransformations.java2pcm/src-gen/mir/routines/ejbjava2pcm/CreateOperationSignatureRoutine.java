package mir.routines.ejbjava2pcm;

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
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateOperationSignatureRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private CreateOperationSignatureRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final InterfaceMethod interfaceMethod, final OperationInterface opInterface, final OperationSignature opSignature) {
      return opSignature;
    }
    
    public EObject getElement2(final InterfaceMethod interfaceMethod, final OperationInterface opInterface, final OperationSignature opSignature) {
      return interfaceMethod;
    }
    
    public void callRoutine1(final InterfaceMethod interfaceMethod, final OperationInterface opInterface, final OperationSignature opSignature, @Extension final RoutinesFacade _routinesFacade) {
      String _name = interfaceMethod.getName();
      opSignature.setEntityName(_name);
      EList<OperationSignature> _signatures__OperationInterface = opInterface.getSignatures__OperationInterface();
      _signatures__OperationInterface.add(opSignature);
      EList<Parameter> _parameters = interfaceMethod.getParameters();
      final Consumer<Parameter> _function = (Parameter it) -> {
        _routinesFacade.createPCMParameter(it, opSignature);
      };
      _parameters.forEach(_function);
      TypeReference _typeReference = interfaceMethod.getTypeReference();
      _routinesFacade.createPCMReturnType(_typeReference, opSignature, interfaceMethod);
    }
  }
  
  public CreateOperationSignatureRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InterfaceMethod interfaceMethod, final OperationInterface opInterface) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreateOperationSignatureRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.interfaceMethod = interfaceMethod;this.opInterface = opInterface;
  }
  
  private InterfaceMethod interfaceMethod;
  
  private OperationInterface opInterface;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationSignatureRoutine with input:");
    getLogger().debug("   InterfaceMethod: " + this.interfaceMethod);
    getLogger().debug("   OperationInterface: " + this.opInterface);
    
    OperationSignature opSignature = RepositoryFactoryImpl.eINSTANCE.createOperationSignature();
    initializeCreateElementState(opSignature);
    
    addCorrespondenceBetween(userExecution.getElement1(interfaceMethod, opInterface, opSignature), userExecution.getElement2(interfaceMethod, opInterface, opSignature), "");
    
    userExecution.callRoutine1(interfaceMethod, opInterface, opSignature, effectFacade);
    
    postprocessElementStates();
  }
}
