package mir.effects.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import java.io.IOException;
import mir.effects.simpleChangesTests.EffectsFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class UpdateSingleValuedNonContainmentEReferenceEffect extends AbstractEffectRealization {
  public UpdateSingleValuedNonContainmentEReferenceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private UpdateSingleValuedNonContainmentEReference<NonRoot> change;
  
  private boolean isChangeSet;
  
  public void setChange(final UpdateSingleValuedNonContainmentEReference<NonRoot> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final UpdateSingleValuedNonContainmentEReference<NonRoot> change) {
    NonRoot _newValue = change.getNewValue();
    return _newValue;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceTargetContainer(final UpdateSingleValuedNonContainmentEReference<NonRoot> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect UpdateSingleValuedNonContainmentEReferenceEffect with input:");
    getLogger().debug("   UpdateSingleValuedNonContainmentEReference: " + this.change);
    
    Root targetContainer = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceTargetContainer(change), // correspondence source supplier
    	(Root _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Root.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    NonRoot targetElement = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	(NonRoot _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	NonRoot.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.simpleChangesTests.UpdateSingleValuedNonContainmentEReferenceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetContainer, targetElement);
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
    
    private void executeUserOperations(final UpdateSingleValuedNonContainmentEReference<NonRoot> change, final Root targetContainer, final NonRoot targetElement) {
      targetContainer.setSingleValuedNonContainmentEReference(targetElement);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.UpdateSingleValuedNonContainmentEReference);
    }
  }
}
