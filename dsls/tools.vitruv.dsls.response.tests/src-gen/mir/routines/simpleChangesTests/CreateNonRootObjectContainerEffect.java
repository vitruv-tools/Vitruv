package mir.routines.simpleChangesTests;

import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateNonRootObjectContainerEffect extends AbstractEffectRealization {
  public CreateNonRootObjectContainerEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root, final NonRootObjectContainerHelper nonRootObjectContainer) {
    super(responseExecutionState, calledBy);
    				this.root = root;this.nonRootObjectContainer = nonRootObjectContainer;
  }
  
  private Root root;
  
  private NonRootObjectContainerHelper nonRootObjectContainer;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    private void executeUserOperations(final Root root, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      String _id = nonRootObjectContainer.getId();
      newNonRootContainer.setId(_id);
      targetElement.setNonRootObjectContainerHelper(newNonRootContainer);
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
  
  private EObject getElement0(final Root root, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
    return newNonRootContainer;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final Root root, final NonRootObjectContainerHelper nonRootObjectContainer) {
    return root;
  }
  
  private EObject getElement1(final Root root, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
    return nonRootObjectContainer;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateNonRootObjectContainerEffect with input:");
    getLogger().debug("   Root: " + this.root);
    getLogger().debug("   NonRootObjectContainerHelper: " + this.nonRootObjectContainer);
    
    Root targetElement = getCorrespondingElement(
    	getCorrepondenceSourceTargetElement(root, nonRootObjectContainer), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    NonRootObjectContainerHelper newNonRootContainer = AllElementTypesFactoryImpl.eINSTANCE.createNonRootObjectContainerHelper();
    initializeCreateElementState(newNonRootContainer);
    
    addCorrespondenceBetween(getElement0(root, nonRootObjectContainer, targetElement, newNonRootContainer), getElement1(root, nonRootObjectContainer, targetElement, newNonRootContainer), "");
    preprocessElementStates();
    new mir.routines.simpleChangesTests.CreateNonRootObjectContainerEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	root, nonRootObjectContainer, targetElement, newNonRootContainer);
    postprocessElementStates();
  }
}
