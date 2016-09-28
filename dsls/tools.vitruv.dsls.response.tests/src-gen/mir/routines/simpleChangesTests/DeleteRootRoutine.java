package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteRootRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private DeleteRootRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final Root root, final Root oldModel) {
      return oldModel;
    }
    
    public EObject getCorrepondenceSourceOldModel(final Root root) {
      return root;
    }
  }
  
  public DeleteRootRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.DeleteRootRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.root = root;
  }
  
  private Root root;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteRootRoutine with input:");
    getLogger().debug("   Root: " + this.root);
    
    Root oldModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOldModel(root), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (oldModel == null) {
    	return;
    }
    initializeRetrieveElementState(oldModel);
    deleteObject(userExecution.getElement1(root, oldModel));
    
    postprocessElementStates();
  }
}
