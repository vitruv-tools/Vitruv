package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class UpdateSingleValuedNonContainmentEReferenceEffect extends AbstractEffectRealization {
  public UpdateSingleValuedNonContainmentEReferenceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEReference<Root, NonRoot> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEReference<Root, NonRoot> change;
  
  private EObject getCorrepondenceSourceTargetElement(final ReplaceSingleValuedEReference<Root, NonRoot> change) {
    NonRoot _newValue = change.getNewValue();
    return _newValue;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateSingleValuedNonContainmentEReferenceEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEReference: " + this.change);
    
    Root targetContainer = getCorrespondingElement(
    	getCorrepondenceSourceTargetContainer(change), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetContainer == null) {
    	return;
    }
    initializeRetrieveElementState(targetContainer);
    NonRoot targetElement = getCorrespondingElement(
    	getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	NonRoot.class,
    	(NonRoot _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.UpdateSingleValuedNonContainmentEReferenceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetContainer, targetElement);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceTargetContainer(final ReplaceSingleValuedEReference<Root, NonRoot> change) {
    Root _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEReference<Root, NonRoot> change, final Root targetContainer, final NonRoot targetElement) {
      targetContainer.setSingleValuedNonContainmentEReference(targetElement);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.UpdateSingleValuedNonContainmentEReference);
    }
  }
}
