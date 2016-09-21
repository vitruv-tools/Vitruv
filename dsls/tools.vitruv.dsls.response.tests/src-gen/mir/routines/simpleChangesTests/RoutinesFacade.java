package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectsFacade;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractEffectsFacade {
  public RoutinesFacade(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  public void callDeleteNonRootEObjectSingle(final NonRoot containedObject) {
    mir.routines.simpleChangesTests.DeleteNonRootEObjectSingleEffect effect = new mir.routines.simpleChangesTests.DeleteNonRootEObjectSingleEffect(this.executionState, calledBy,
    	containedObject);
    effect.applyRoutine();
  }
  
  public void callCreateNonRootEObjectSingle(final Root sourceRoot, final NonRoot containedObject) {
    mir.routines.simpleChangesTests.CreateNonRootEObjectSingleEffect effect = new mir.routines.simpleChangesTests.CreateNonRootEObjectSingleEffect(this.executionState, calledBy,
    	sourceRoot, containedObject);
    effect.applyRoutine();
  }
}
