package mir.effects.simpleChangesTests;

import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import java.io.IOException;
import mir.effects.simpleChangesTests.EffectsFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class HelperResponseForCreateSecondTestModelEffect extends AbstractEffectRealization {
  public HelperResponseForCreateSecondTestModelEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateRootEObject<Root> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateRootEObject<Root> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private String getModelPath(final CreateRootEObject<Root> change, final Root newRoot) {
    return "model/EachTestModelTarget";
  }
  
  private EObject getCorrepondenceSourceNewRoot(final CreateRootEObject<Root> change) {
    Root _newValue = change.getNewValue();
    return _newValue;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect HelperResponseForCreateSecondTestModelEffect with input:");
    getLogger().debug("   CreateRootEObject: " + this.change);
    
    Root newRoot = initializeCreateElementState(
    	() -> getCorrepondenceSourceNewRoot(change), // correspondence source supplier
    	() -> AllElementTypesFactoryImpl.eINSTANCE.createRoot(), // element creation supplier
    	() -> null, // tag supplier
    	Root.class);
    setPersistenceInformation(newRoot, () -> getModelPath(change, newRoot), false);
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.simpleChangesTests.HelperResponseForCreateSecondTestModelEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, newRoot);
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
    
    private void executeUserOperations(final CreateRootEObject<Root> change, final Root newRoot) {
      Root _newValue = change.getNewValue();
      String _id = _newValue.getId();
      newRoot.setId(_id);
    }
  }
}
