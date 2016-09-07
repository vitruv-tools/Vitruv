package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruvius.framework.change.echange.feature.attribute.InsertEAttributeValue;

import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class InsertEAttributeValueEffect extends AbstractEffectRealization {
  public InsertEAttributeValueEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEAttributeValue<Root, Integer> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEAttributeValue<Root, Integer> change;
  
  private EObject getCorrepondenceSourceTargetElement(final InsertEAttributeValue<Root, Integer> change) {
    Root _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertEAttributeValueEffect with input:");
    getLogger().debug("   InsertEAttributeValue: " + this.change);
    
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
    new mir.routines.simpleChangesTests.InsertEAttributeValueEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final InsertEAttributeValue<Root, Integer> change, final Root targetElement) {
      EList<Integer> _multiValuedEAttribute = targetElement.getMultiValuedEAttribute();
      Integer _newValue = change.getNewValue();
      _multiValuedEAttribute.add(_newValue);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.InsertEAttributeValue);
    }
  }
}
