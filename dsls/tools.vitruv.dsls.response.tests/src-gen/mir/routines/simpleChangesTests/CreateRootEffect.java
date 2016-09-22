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
  public CreateRootEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root) {
    super(responseExecutionState, calledBy);
    				this.root = root;
  }
  
  private Root root;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    private void executeUserOperations(final Root root, final Root newRoot) {
      String _id = root.getId();
      newRoot.setId(_id);
      String _id_1 = root.getId();
      String _replace = _id_1.replace("Source", "Target");
      String _plus = ("model/" + _replace);
      this.persistProjectRelative(root, newRoot, _plus);
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
  
  private EObject getElement0(final Root root, final Root newRoot) {
    return newRoot;
  }
  
  private EObject getElement1(final Root root, final Root newRoot) {
    return root;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRootEffect with input:");
    getLogger().debug("   Root: " + this.root);
    
    Root newRoot = AllElementTypesFactoryImpl.eINSTANCE.createRoot();
    initializeCreateElementState(newRoot);
    
    addCorrespondenceBetween(getElement0(root, newRoot), getElement1(root, newRoot), "");
    preprocessElementStates();
    new mir.routines.simpleChangesTests.CreateRootEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	root, newRoot);
    postprocessElementStates();
  }
}
