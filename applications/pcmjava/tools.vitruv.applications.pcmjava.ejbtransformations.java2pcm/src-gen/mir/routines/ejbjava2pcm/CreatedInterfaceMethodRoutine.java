package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.InterfaceMethod;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatedInterfaceMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private CreatedInterfaceMethodRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceOpInterface(final Interface interf, final InterfaceMethod method) {
      return interf;
    }
    
    public void callRoutine1(final Interface interf, final InterfaceMethod method, final OperationInterface opInterface, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createOperationSignature(method, opInterface);
    }
  }
  
  public CreatedInterfaceMethodRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Interface interf, final InterfaceMethod method) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatedInterfaceMethodRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.interf = interf;this.method = method;
  }
  
  private Interface interf;
  
  private InterfaceMethod method;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedInterfaceMethodRoutine with input:");
    getLogger().debug("   Interface: " + this.interf);
    getLogger().debug("   InterfaceMethod: " + this.method);
    
    OperationInterface opInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpInterface(interf, method), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    if (opInterface == null) {
    	return;
    }
    initializeRetrieveElementState(opInterface);
    userExecution.callRoutine1(interf, method, opInterface, effectFacade);
    
    postprocessElementStates();
  }
}
