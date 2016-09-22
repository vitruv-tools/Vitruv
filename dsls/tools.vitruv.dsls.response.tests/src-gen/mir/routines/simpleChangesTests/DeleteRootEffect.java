package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteRootEffect extends AbstractEffectRealization {
  public DeleteRootEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root) {
    super(responseExecutionState, calledBy);
    				this.root = root;
  }
  
  private Root root;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
  
  private EObject getElement0(final Root root, final Root oldModel) {
    return oldModel;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteRootEffect with input:");
    getLogger().debug("   Root: " + this.root);
    
    Root oldModel = getCorrespondingElement(
    	getCorrepondenceSourceOldModel(root), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (oldModel == null) {
    	return;
    }
    initializeRetrieveElementState(oldModel);
    deleteObject(getElement0(root, oldModel));
    
    preprocessElementStates();
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOldModel(final Root root) {
    return root;
  }
}
