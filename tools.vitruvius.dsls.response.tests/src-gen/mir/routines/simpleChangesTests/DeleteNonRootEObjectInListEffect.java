package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruvius.framework.change.echange.feature.reference.RemoveEReference;

import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class DeleteNonRootEObjectInListEffect extends AbstractEffectRealization {
  public DeleteNonRootEObjectInListEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RemoveEReference<Root, NonRoot> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private RemoveEReference<Root, NonRoot> change;
  
  private EObject getElement0(final RemoveEReference<Root, NonRoot> change, final NonRoot targetElement) {
    return targetElement;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final RemoveEReference<Root, NonRoot> change) {
    NonRoot _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteNonRootEObjectInListEffect with input:");
    getLogger().debug("   RemoveEReference: " + this.change);
    
    NonRoot targetElement = getCorrespondingElement(
    	getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	NonRoot.class,
    	(NonRoot _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    deleteObject(getElement0(change, targetElement));
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.DeleteNonRootEObjectInListEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetElement);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final RemoveEReference<Root, NonRoot> change, final NonRoot targetElement) {
      EcoreUtil.remove(targetElement);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.DeleteNonRootEObjectInList);
    }
  }
}
