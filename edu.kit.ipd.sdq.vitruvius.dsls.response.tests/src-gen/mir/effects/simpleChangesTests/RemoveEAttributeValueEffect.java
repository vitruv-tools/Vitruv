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
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.RemoveEAttributeValue;
import java.io.IOException;
import java.util.function.Predicate;
import mir.effects.simpleChangesTests.EffectsFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class RemoveEAttributeValueEffect extends AbstractEffectRealization {
  public RemoveEAttributeValueEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private RemoveEAttributeValue<Integer> change;
  
  private boolean isChangeSet;
  
  public void setChange(final RemoveEAttributeValue<Integer> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final RemoveEAttributeValue<Integer> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect RemoveEAttributeValueEffect with input:");
    getLogger().debug("   RemoveEAttributeValue: " + this.change);
    
    Root targetElement = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	(Root _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Root.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.simpleChangesTests.RemoveEAttributeValueEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final RemoveEAttributeValue<Integer> change, final Root targetElement) {
      EObject _newAffectedEObject = change.getNewAffectedEObject();
      final EList<Integer> sourceValueList = ((Root) _newAffectedEObject).getMultiValuedEAttribute();
      EList<Integer> _multiValuedEAttribute = targetElement.getMultiValuedEAttribute();
      final Predicate<Integer> _function = (Integer it) -> {
        int _intValue = it.intValue();
        boolean _contains = sourceValueList.contains(Integer.valueOf(_intValue));
        return (!_contains);
      };
      _multiValuedEAttribute.removeIf(_function);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.RemoveEAttributeValue);
    }
  }
}
