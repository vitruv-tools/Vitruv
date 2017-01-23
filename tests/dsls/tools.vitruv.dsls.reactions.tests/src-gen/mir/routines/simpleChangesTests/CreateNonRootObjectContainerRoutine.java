package mir.routines.simpleChangesTests;

import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateNonRootObjectContainerRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateNonRootObjectContainerRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root rootElement, final NonRootObjectContainerHelper nonRootObjectContainer) {
      return rootElement;
    }
    
    public EObject getElement1(final Root rootElement, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      return targetElement;
    }
    
    public void update0Element(final Root rootElement, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      targetElement.setNonRootObjectContainerHelper(newNonRootContainer);
    }
    
    public EObject getElement2(final Root rootElement, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      return newNonRootContainer;
    }
    
    public EObject getElement3(final Root rootElement, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      return nonRootObjectContainer;
    }
    
    public void updateNewNonRootContainerElement(final Root rootElement, final NonRootObjectContainerHelper nonRootObjectContainer, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      String _id = nonRootObjectContainer.getId();
      newNonRootContainer.setId(_id);
    }
  }
  
  public CreateNonRootObjectContainerRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Root rootElement, final NonRootObjectContainerHelper nonRootObjectContainer) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.CreateNonRootObjectContainerRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.rootElement = rootElement;this.nonRootObjectContainer = nonRootObjectContainer;
  }
  
  private Root rootElement;
  
  private NonRootObjectContainerHelper nonRootObjectContainer;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateNonRootObjectContainerRoutine with input:");
    getLogger().debug("   Root: " + this.rootElement);
    getLogger().debug("   NonRootObjectContainerHelper: " + this.nonRootObjectContainer);
    
    Root targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(rootElement, nonRootObjectContainer), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    NonRootObjectContainerHelper newNonRootContainer = AllElementTypesFactoryImpl.eINSTANCE.createNonRootObjectContainerHelper();
    initializeCreateElementState(newNonRootContainer);
    userExecution.updateNewNonRootContainerElement(rootElement, nonRootObjectContainer, targetElement, newNonRootContainer);
    
    // val updatedElement userExecution.getElement1(rootElement, nonRootObjectContainer, targetElement, newNonRootContainer);
    userExecution.update0Element(rootElement, nonRootObjectContainer, targetElement, newNonRootContainer);
    
    addCorrespondenceBetween(userExecution.getElement2(rootElement, nonRootObjectContainer, targetElement, newNonRootContainer), userExecution.getElement3(rootElement, nonRootObjectContainer, targetElement, newNonRootContainer), "");
    
    postprocessElementStates();
  }
}
