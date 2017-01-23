package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertEAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private InsertEAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root rootElement, final Integer attributeValue) {
      return rootElement;
    }
    
    public EObject getElement1(final Root rootElement, final Integer attributeValue, final Root targetElement) {
      return targetElement;
    }
    
    public void update0Element(final Root rootElement, final Integer attributeValue, final Root targetElement) {
      EList<Integer> _multiValuedEAttribute = targetElement.getMultiValuedEAttribute();
      _multiValuedEAttribute.add(attributeValue);
    }
    
    public void callRoutine1(final Root rootElement, final Integer attributeValue, final Root targetElement, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.InsertEAttributeValue);
    }
  }
  
  public InsertEAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Root rootElement, final Integer attributeValue) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.InsertEAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.rootElement = rootElement;this.attributeValue = attributeValue;
  }
  
  private Root rootElement;
  
  private Integer attributeValue;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertEAttributeRoutine with input:");
    getLogger().debug("   Root: " + this.rootElement);
    getLogger().debug("   Integer: " + this.attributeValue);
    
    Root targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(rootElement, attributeValue), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    // val updatedElement userExecution.getElement1(rootElement, attributeValue, targetElement);
    userExecution.update0Element(rootElement, attributeValue, targetElement);
    
    userExecution.callRoutine1(rootElement, attributeValue, targetElement, actionsFacade);
    
    postprocessElementStates();
  }
}
