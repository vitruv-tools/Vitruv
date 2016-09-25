package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertEAttributeEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root root, final Integer attributeValue) {
      return root;
    }
    
    public EObject getElement1(final Root root, final Integer attributeValue, final Root targetElement) {
      return targetElement;
    }
    
    public void update0Element(final Root root, final Integer attributeValue, final Root targetElement) {
      EList<Integer> _multiValuedEAttribute = targetElement.getMultiValuedEAttribute();
      _multiValuedEAttribute.add(attributeValue);
    }
    
    public void callRoutine1(final Root root, final Integer attributeValue, final Root targetElement, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.InsertEAttributeValue);
    }
  }
  
  private InsertEAttributeEffect.EffectUserExecution userExecution;
  
  public InsertEAttributeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root, final Integer attributeValue) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.simpleChangesTests.InsertEAttributeEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    				this.root = root;this.attributeValue = attributeValue;
  }
  
  private Root root;
  
  private Integer attributeValue;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertEAttributeEffect with input:");
    getLogger().debug("   Root: " + this.root);
    getLogger().debug("   Integer: " + this.attributeValue);
    
    Root targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(root, attributeValue), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    // val updatedElement userExecution.getElement1(root, attributeValue, targetElement);
    userExecution.update0Element(root, attributeValue, targetElement);
    
    preprocessElementStates();
    userExecution.callRoutine1(
    	root, attributeValue, targetElement, effectFacade);
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
