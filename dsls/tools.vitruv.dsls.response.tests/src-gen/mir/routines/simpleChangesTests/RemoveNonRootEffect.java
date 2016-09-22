package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveNonRootEffect extends AbstractEffectRealization {
  public RemoveNonRootEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NonRoot removedNonRoot) {
    super(responseExecutionState, calledBy);
    				this.removedNonRoot = removedNonRoot;
  }
  
  private NonRoot removedNonRoot;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    private void executeUserOperations(final NonRoot removedNonRoot, final NonRoot targetElement) {
      EcoreUtil.remove(targetElement);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.DeleteNonRootEObjectInList);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    @Extension
    private RoutinesFacade effectFacade;
  }
  
  private EObject getElement0(final NonRoot removedNonRoot, final NonRoot targetElement) {
    return targetElement;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final NonRoot removedNonRoot) {
    return removedNonRoot;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveNonRootEffect with input:");
    getLogger().debug("   NonRoot: " + this.removedNonRoot);
    
    NonRoot targetElement = getCorrespondingElement(
    	getCorrepondenceSourceTargetElement(removedNonRoot), // correspondence source supplier
    	NonRoot.class,
    	(NonRoot _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    deleteObject(getElement0(removedNonRoot, targetElement));
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.RemoveNonRootEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	removedNonRoot, targetElement);
    postprocessElementStates();
  }
}
