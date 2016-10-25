package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteNonRootEObjectSingleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteNonRootEObjectSingleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final NonRoot containedObject) {
      return containedObject;
    }
    
    public EObject getElement1(final NonRoot containedObject, final NonRoot targetElement) {
      return targetElement;
    }
    
    public void callRoutine1(final NonRoot containedObject, final NonRoot targetElement, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.DeleteNonRootEObjectSingle);
    }
  }
  
  public DeleteNonRootEObjectSingleRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NonRoot containedObject) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.DeleteNonRootEObjectSingleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.containedObject = containedObject;
  }
  
  private NonRoot containedObject;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteNonRootEObjectSingleRoutine with input:");
    getLogger().debug("   NonRoot: " + this.containedObject);
    
    NonRoot targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(containedObject), // correspondence source supplier
    	NonRoot.class,
    	(NonRoot _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    deleteObject(userExecution.getElement1(containedObject, targetElement));
    
    userExecution.callRoutine1(containedObject, targetElement, actionsFacade);
    
    postprocessElementStates();
  }
}
