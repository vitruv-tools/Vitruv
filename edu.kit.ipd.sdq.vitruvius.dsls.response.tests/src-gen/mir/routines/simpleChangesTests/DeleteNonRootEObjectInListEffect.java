package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class DeleteNonRootEObjectInListEffect extends AbstractEffectRealization {
  public DeleteNonRootEObjectInListEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private DeleteNonRootEObjectInList<NonRoot> change;
  
  private boolean isChangeSet;
  
  public void setChange(final DeleteNonRootEObjectInList<NonRoot> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getElement0(final DeleteNonRootEObjectInList<NonRoot> change, final NonRoot targetElement) {
    return targetElement;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final DeleteNonRootEObjectInList<NonRoot> change) {
    NonRoot _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine DeleteNonRootEObjectInListEffect with input:");
    getLogger().debug("   DeleteNonRootEObjectInList: " + this.change);
    
    NonRoot targetElement = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	(NonRoot _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	NonRoot.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    markObjectDelete(getElement0(change, targetElement));
    
    preProcessElements();
    new mir.routines.simpleChangesTests.DeleteNonRootEObjectInListEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetElement);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final DeleteNonRootEObjectInList<NonRoot> change, final NonRoot targetElement) {
      EcoreUtil.remove(targetElement);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.DeleteNonRootEObjectInList);
    }
  }
}
