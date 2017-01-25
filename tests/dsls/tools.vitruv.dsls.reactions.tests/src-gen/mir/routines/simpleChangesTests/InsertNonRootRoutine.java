package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
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
public class InsertNonRootRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private InsertNonRootRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root rootElement, final NonRoot insertedNonRoot) {
      return rootElement;
    }
    
    public EObject getElement1(final Root rootElement, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
      return targetElement;
    }
    
    public void update0Element(final Root rootElement, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
      EList<NonRoot> _multiValuedContainmentEReference = targetElement.getMultiValuedContainmentEReference();
      _multiValuedContainmentEReference.add(newNonRoot);
    }
    
    public EObject getElement2(final Root rootElement, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
      return newNonRoot;
    }
    
    public EObject getElement3(final Root rootElement, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
      return insertedNonRoot;
    }
    
    public void updateNewNonRootElement(final Root rootElement, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
      String _id = insertedNonRoot.getId();
      newNonRoot.setId(_id);
    }
    
    public void callRoutine1(final Root rootElement, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.CreateNonRootEObjectInList);
    }
  }
  
  public InsertNonRootRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Root rootElement, final NonRoot insertedNonRoot) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.InsertNonRootRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.rootElement = rootElement;this.insertedNonRoot = insertedNonRoot;
  }
  
  private Root rootElement;
  
  private NonRoot insertedNonRoot;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertNonRootRoutine with input:");
    getLogger().debug("   Root: " + this.rootElement);
    getLogger().debug("   NonRoot: " + this.insertedNonRoot);
    
    Root targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(rootElement, insertedNonRoot), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    NonRoot newNonRoot = AllElementTypesFactoryImpl.eINSTANCE.createNonRoot();
    initializeCreateElementState(newNonRoot);
    userExecution.updateNewNonRootElement(rootElement, insertedNonRoot, targetElement, newNonRoot);
    
    // val updatedElement userExecution.getElement1(rootElement, insertedNonRoot, targetElement, newNonRoot);
    userExecution.update0Element(rootElement, insertedNonRoot, targetElement, newNonRoot);
    
    addCorrespondenceBetween(userExecution.getElement2(rootElement, insertedNonRoot, targetElement, newNonRoot), userExecution.getElement3(rootElement, insertedNonRoot, targetElement, newNonRoot), "");
    
    userExecution.callRoutine1(rootElement, insertedNonRoot, targetElement, newNonRoot, actionsFacade);
    
    postprocessElementStates();
  }
}
