package mir.routines.simpleChangesTests;

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
public class CreateRootEffect extends AbstractEffectRealization {
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final Root root, final Root newRoot) {
      return newRoot;
    }
    
    public void updateNewRootElement(final Root root, final Root newRoot) {
      String _id = root.getId();
      newRoot.setId(_id);
    }
    
    public EObject getElement2(final Root root, final Root newRoot) {
      return root;
    }
  }
  
  private CreateRootEffect.EffectUserExecution userExecution;
  
  public CreateRootEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.simpleChangesTests.CreateRootEffect.EffectUserExecution(getExecutionState(), this);
    				this.root = root;
  }
  
  private Root root;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRootEffect with input:");
    getLogger().debug("   Root: " + this.root);
    
    Root newRoot = AllElementTypesFactoryImpl.eINSTANCE.createRoot();
    initializeCreateElementState(newRoot);
    userExecution.updateNewRootElement(root, newRoot);
    
    addCorrespondenceBetween(userExecution.getElement1(root, newRoot), userExecution.getElement2(root, newRoot), "");
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.CreateRootEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	root, newRoot);
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    public void executeUserOperations(final Root root, final Root newRoot) {
      String _id = root.getId();
      String _replace = _id.replace("Source", "Target");
      String _plus = ("model/" + _replace);
      this.persistProjectRelative(root, newRoot, _plus);
    }
  }
}
