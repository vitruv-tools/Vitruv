package mir.effects.simpleChangesTests;

import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;
import java.io.IOException;
import mir.effects.simpleChangesTests.EffectsFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class HelperResponseForNonRootObjectContainerInitializationEffect extends AbstractEffectRealization {
  public HelperResponseForNonRootObjectContainerInitializationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceNewNonRootContainer(final CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change) {
    NonRootObjectContainerHelper _newValue = change.getNewValue();
    return _newValue;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect HelperResponseForNonRootObjectContainerInitializationEffect with input:");
    getLogger().debug("   CreateNonRootEObjectSingle: " + this.change);
    
    Root targetElement = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	(Root _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Root.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    NonRootObjectContainerHelper newNonRootContainer = initializeCreateElementState(
    	() -> getCorrepondenceSourceNewNonRootContainer(change), // correspondence source supplier
    	() -> AllElementTypesFactoryImpl.eINSTANCE.createNonRootObjectContainerHelper(), // element creation supplier
    	() -> null, // tag supplier
    	NonRootObjectContainerHelper.class);
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.simpleChangesTests.HelperResponseForNonRootObjectContainerInitializationEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetElement, newNonRootContainer);
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
    
    private void executeUserOperations(final CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change, final Root targetElement, final NonRootObjectContainerHelper newNonRootContainer) {
      NonRootObjectContainerHelper _newValue = change.getNewValue();
      String _id = _newValue.getId();
      newNonRootContainer.setId(_id);
      targetElement.setNonRootObjectContainerHelper(newNonRootContainer);
    }
  }
}
