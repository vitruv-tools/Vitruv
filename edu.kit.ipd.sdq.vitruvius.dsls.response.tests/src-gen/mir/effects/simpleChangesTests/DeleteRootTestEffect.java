package mir.effects.simpleChangesTests;

import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject;
import java.io.IOException;
import mir.effects.simpleChangesTests.EffectsFacade;
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
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceOldModel(final DeleteRootEObject<Root> change) {
    Root _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect DeleteRootTestEffect with input:");
    getLogger().debug("   DeleteRootEObject: " + this.change);
    
    Root oldModel = initializeDeleteElementState(
    	() -> getCorrepondenceSourceOldModel(change), // correspondence source supplier
    	(Root _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Root.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    preProcessElements();
    postProcessElements();
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
    }
  }
}
