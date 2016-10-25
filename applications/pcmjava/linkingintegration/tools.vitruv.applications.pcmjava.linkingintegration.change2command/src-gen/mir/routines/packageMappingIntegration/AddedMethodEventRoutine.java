package mir.routines.packageMappingIntegration;

import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddedMethodEventRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddedMethodEventRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceOpInterface(final ConcreteClassifier clazz, final Method method) {
      return clazz;
    }
    
    public void callRoutine1(final ConcreteClassifier clazz, final Method method, final OperationInterface opInterface, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createOperationSignature(opInterface, method);
    }
  }
  
  public AddedMethodEventRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier clazz, final Method method) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.AddedMethodEventRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.clazz = clazz;this.method = method;
  }
  
  private ConcreteClassifier clazz;
  
  private Method method;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddedMethodEventRoutine with input:");
    getLogger().debug("   ConcreteClassifier: " + this.clazz);
    getLogger().debug("   Method: " + this.method);
    
    OperationInterface opInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpInterface(clazz, method), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    if (opInterface == null) {
    	return;
    }
    initializeRetrieveElementState(opInterface);
    userExecution.callRoutine1(clazz, method, opInterface, actionsFacade);
    
    postprocessElementStates();
  }
}
