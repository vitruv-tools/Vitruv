package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class DeleteRootTestEffect extends AbstractEffectRealization {
  public DeleteRootTestEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private DeleteRootEObject<Root> change;
  
  private boolean isChangeSet;
  
  public void setChange(final DeleteRootEObject<Root> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getElement0(final DeleteRootEObject<Root> change, final Root oldModel) {
    return oldModel;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceOldModel(final DeleteRootEObject<Root> change) {
    Root _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine DeleteRootTestEffect with input:");
    getLogger().debug("   DeleteRootEObject: " + this.change);
    
    Root oldModel = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceOldModel(change), // correspondence source supplier
    	(Root _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Root.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    markObjectDelete(getElement0(change, oldModel));
    
    preProcessElements();
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
