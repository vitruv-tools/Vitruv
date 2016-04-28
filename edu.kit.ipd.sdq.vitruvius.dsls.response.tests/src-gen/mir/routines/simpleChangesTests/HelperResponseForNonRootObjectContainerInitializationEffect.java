package mir.routines.simpleChangesTests;

import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class HelperResponseForNonRootObjectContainerInitializationEffect extends AbstractEffectRealization {
  public HelperResponseForNonRootObjectContainerInitializationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getElement0(final CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
    return newNonRootContainer;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  private EObject getElement1(final CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
    NonRootObjectContainerHelper _newValue = change.getNewValue();
    return _newValue;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine HelperResponseForNonRootObjectContainerInitializationEffect with input:");
    getLogger().debug("   CreateNonRootEObjectSingle: " + this.change);
    
    Root targetElement = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	(Root _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Root.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    NonRootObjectContainerHelper newNonRootContainer = AllElementTypesFactoryImpl.eINSTANCE.createNonRootObjectContainerHelper();
    initializeCreateElementState(newNonRootContainer);
    
    addCorrespondenceBetween(getElement0(change, targetElement, newNonRootContainer), getElement1(change, targetElement, newNonRootContainer), "");
    preProcessElements();
    new mir.routines.simpleChangesTests.HelperResponseForNonRootObjectContainerInitializationEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetElement, newNonRootContainer);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      NonRootObjectContainerHelper _newValue = change.getNewValue();
      String _id = _newValue.getId();
      newNonRootContainer.setId(_id);
      targetElement.setNonRootObjectContainerHelper(newNonRootContainer);
    }
  }
}
