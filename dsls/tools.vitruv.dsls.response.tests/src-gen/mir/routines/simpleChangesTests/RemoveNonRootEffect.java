package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveNonRootEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private RemoveNonRootEffect.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final NonRoot removedNonRoot) {
      return removedNonRoot;
    }
    
    public EObject getElement1(final NonRoot removedNonRoot, final NonRoot targetElement) {
      return targetElement;
    }
    
    public void callRoutine1(final NonRoot removedNonRoot, final NonRoot targetElement, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.DeleteNonRootEObjectInList);
    }
  }
  
  public RemoveNonRootEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NonRoot removedNonRoot) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.RemoveNonRootEffect.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.removedNonRoot = removedNonRoot;
  }
  
  private NonRoot removedNonRoot;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveNonRootEffect with input:");
    getLogger().debug("   NonRoot: " + this.removedNonRoot);
    
    NonRoot targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(removedNonRoot), // correspondence source supplier
    	NonRoot.class,
    	(NonRoot _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    userExecution.callRoutine1(removedNonRoot, targetElement, effectFacade);
    
    deleteObject(userExecution.getElement1(removedNonRoot, targetElement));
    
    postprocessElementStates();
  }
}
