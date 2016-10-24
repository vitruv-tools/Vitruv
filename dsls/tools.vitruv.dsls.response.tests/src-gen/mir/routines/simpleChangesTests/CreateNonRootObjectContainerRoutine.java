package mir.routines.simpleChangesTests;

import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateNonRootObjectContainerRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateNonRootObjectContainerRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root root, final NonRootObjectContainerHelper nonRootObjectContainer) {
      return root;
    }
    
    public EObject getElement1(final Root root, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      return targetElement;
    }
    
    public void update0Element(final Root root, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      targetElement.setNonRootObjectContainerHelper(newNonRootContainer);
    }
    
    public EObject getElement2(final Root root, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      return newNonRootContainer;
    }
    
    public EObject getElement3(final Root root, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      return nonRootObjectContainer;
    }
    
    public void updateNewNonRootContainerElement(final Root root, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      String _id = nonRootObjectContainer.getId();
      newNonRootContainer.setId(_id);
    }
  }
  
  public CreateNonRootObjectContainerRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root, final NonRootObjectContainerHelper nonRootObjectContainer) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.CreateNonRootObjectContainerRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.root = root;this.nonRootObjectContainer = nonRootObjectContainer;
  }
  
  private Root root;
  
  private NonRootObjectContainerHelper nonRootObjectContainer;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateNonRootObjectContainerRoutine with input:");
    getLogger().debug("   Root: " + this.root);
    getLogger().debug("   NonRootObjectContainerHelper: " + this.nonRootObjectContainer);
    
    Root targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(root, nonRootObjectContainer), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    NonRootObjectContainerHelper newNonRootContainer = AllElementTypesFactoryImpl.eINSTANCE.createNonRootObjectContainerHelper();
    initializeCreateElementState(newNonRootContainer);
    userExecution.updateNewNonRootContainerElement(root, nonRootObjectContainer, targetElement, newNonRootContainer);
    
    // val updatedElement userExecution.getElement1(root, nonRootObjectContainer, targetElement, newNonRootContainer);
    userExecution.update0Element(root, nonRootObjectContainer, targetElement, newNonRootContainer);
    
    addCorrespondenceBetween(userExecution.getElement2(root, nonRootObjectContainer, targetElement, newNonRootContainer), userExecution.getElement3(root, nonRootObjectContainer, targetElement, newNonRootContainer), "");
    
    postprocessElementStates();
  }
}
