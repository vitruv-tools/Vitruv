package mir.routines.simpleChangesTests;

import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class HelperResponseForNonRootObjectContainerInitializationEffect extends AbstractEffectRealization {
  public HelperResponseForNonRootObjectContainerInitializationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEReference<Root, NonRootObjectContainerHelper> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEReference<Root, NonRootObjectContainerHelper> change;
  
  private EObject getElement0(final ReplaceSingleValuedEReference<Root, NonRootObjectContainerHelper> change, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
    return newNonRootContainer;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final ReplaceSingleValuedEReference<Root, NonRootObjectContainerHelper> change) {
    Root _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private EObject getElement1(final ReplaceSingleValuedEReference<Root, NonRootObjectContainerHelper> change, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
    NonRootObjectContainerHelper _newValue = change.getNewValue();
    return _newValue;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine HelperResponseForNonRootObjectContainerInitializationEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEReference: " + this.change);
    
    Root targetElement = getCorrespondingElement(
    	getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    NonRootObjectContainerHelper newNonRootContainer = AllElementTypesFactoryImpl.eINSTANCE.createNonRootObjectContainerHelper();
    initializeCreateElementState(newNonRootContainer);
    
    addCorrespondenceBetween(getElement0(change, targetElement, newNonRootContainer), getElement1(change, targetElement, newNonRootContainer), "");
    preprocessElementStates();
    new mir.routines.simpleChangesTests.HelperResponseForNonRootObjectContainerInitializationEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetElement, newNonRootContainer);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEReference<Root, NonRootObjectContainerHelper> change, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      NonRootObjectContainerHelper _newValue = change.getNewValue();
      String _id = _newValue.getId();
      newNonRootContainer.setId(_id);
      targetElement.setNonRootObjectContainerHelper(newNonRootContainer);
    }
  }
}
