package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateNonRootEObjectSingleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateNonRootEObjectSingleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root sourceRoot, final NonRoot containedObject) {
      return sourceRoot;
    }
    
    public EObject getElement1(final Root sourceRoot, final NonRoot containedObject, final Root targetElement, final NonRoot newNonRoot) {
      return targetElement;
    }
    
    public void update0Element(final Root sourceRoot, final NonRoot containedObject, final Root targetElement, final NonRoot newNonRoot) {
      targetElement.setSingleValuedContainmentEReference(newNonRoot);
    }
    
    public EObject getElement2(final Root sourceRoot, final NonRoot containedObject, final Root targetElement, final NonRoot newNonRoot) {
      return newNonRoot;
    }
    
    public EObject getElement3(final Root sourceRoot, final NonRoot containedObject, final Root targetElement, final NonRoot newNonRoot) {
      return containedObject;
    }
    
    public void updateNewNonRootElement(final Root sourceRoot, final NonRoot containedObject, final Root targetElement, final NonRoot newNonRoot) {
      String _id = containedObject.getId();
      newNonRoot.setId(_id);
    }
    
    public void callRoutine1(final Root sourceRoot, final NonRoot containedObject, final Root targetElement, final NonRoot newNonRoot, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.CreateNonRootEObjectSingle);
    }
  }
  
  public CreateNonRootEObjectSingleRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Root sourceRoot, final NonRoot containedObject) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.CreateNonRootEObjectSingleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.sourceRoot = sourceRoot;this.containedObject = containedObject;
  }
  
  private Root sourceRoot;
  
  private NonRoot containedObject;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateNonRootEObjectSingleRoutine with input:");
    getLogger().debug("   Root: " + this.sourceRoot);
    getLogger().debug("   NonRoot: " + this.containedObject);
    
    Root targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(sourceRoot, containedObject), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    NonRoot newNonRoot = AllElementTypesFactoryImpl.eINSTANCE.createNonRoot();
    initializeCreateElementState(newNonRoot);
    userExecution.updateNewNonRootElement(sourceRoot, containedObject, targetElement, newNonRoot);
    
    // val updatedElement userExecution.getElement1(sourceRoot, containedObject, targetElement, newNonRoot);
    userExecution.update0Element(sourceRoot, containedObject, targetElement, newNonRoot);
    
    addCorrespondenceBetween(userExecution.getElement2(sourceRoot, containedObject, targetElement, newNonRoot), userExecution.getElement3(sourceRoot, containedObject, targetElement, newNonRoot), "");
    
    userExecution.callRoutine1(sourceRoot, containedObject, targetElement, newNonRoot, actionsFacade);
    
    postprocessElementStates();
  }
}
