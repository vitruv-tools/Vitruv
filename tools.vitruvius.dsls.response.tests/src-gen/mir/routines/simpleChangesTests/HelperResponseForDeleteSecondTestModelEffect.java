package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.root.RemoveRootEObject;

import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class HelperResponseForDeleteSecondTestModelEffect extends AbstractEffectRealization {
  public HelperResponseForDeleteSecondTestModelEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RemoveRootEObject<Root> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private RemoveRootEObject<Root> change;
  
  private EObject getElement0(final RemoveRootEObject<Root> change, final Root oldModel) {
    return oldModel;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine HelperResponseForDeleteSecondTestModelEffect with input:");
    getLogger().debug("   RemoveRootEObject: " + this.change);
    
    Root oldModel = getCorrespondingElement(
    	getCorrepondenceSourceOldModel(change), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (oldModel == null) {
    	return;
    }
    initializeRetrieveElementState(oldModel);
    deleteObject(getElement0(change, oldModel));
    
    preprocessElementStates();
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOldModel(final RemoveRootEObject<Root> change) {
    Root _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
