package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruvius.framework.change.echange.feature.attribute.RemoveEAttributeValue;

import java.io.IOException;
import java.util.function.Predicate;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class RemoveEAttributeValueEffect extends AbstractEffectRealization {
  public RemoveEAttributeValueEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RemoveEAttributeValue<Root, Integer> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private RemoveEAttributeValue<Root, Integer> change;
  
  private EObject getCorrepondenceSourceTargetElement(final RemoveEAttributeValue<Root, Integer> change) {
    Root _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveEAttributeValueEffect with input:");
    getLogger().debug("   RemoveEAttributeValue: " + this.change);
    
    Root targetElement = getCorrespondingElement(
    	getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.RemoveEAttributeValueEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetElement);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final RemoveEAttributeValue<Root, Integer> change, final Root targetElement) {
      Root _affectedEObject = change.getAffectedEObject();
      final EList<Integer> sourceValueList = _affectedEObject.getMultiValuedEAttribute();
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
