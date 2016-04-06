package mir.effects.simpleChangesTests;

import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.InsertEAttributeValue;
import java.io.IOException;
import mir.effects.simpleChangesTests.EffectsFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class InsertEAttributeValueEffect extends AbstractEffectRealization {
  public InsertEAttributeValueEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private InsertEAttributeValue<Integer> change;
  
  private boolean isChangeSet;
  
  public void setChange(final InsertEAttributeValue<Integer> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final InsertEAttributeValue<Integer> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect InsertEAttributeValueEffect with input:");
    getLogger().debug("   InsertEAttributeValue: " + this.change);
    
    Root targetElement = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	(Root _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Root.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.simpleChangesTests.InsertEAttributeValueEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetElement);
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
    
    private void executeUserOperations(final InsertEAttributeValue<Integer> change, final Root targetElement) {
      EList<Integer> _multiValuedEAttribute = targetElement.getMultiValuedEAttribute();
      Integer _newValue = change.getNewValue();
      _multiValuedEAttribute.add(_newValue);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.InsertEAttributeValue);
    }
  }
}
