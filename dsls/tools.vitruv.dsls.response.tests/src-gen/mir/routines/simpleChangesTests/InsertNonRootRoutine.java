package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertNonRootRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private InsertNonRootRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root root, final NonRoot insertedNonRoot) {
      return root;
    }
    
    public EObject getElement1(final Root root, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
      return targetElement;
    }
    
    public void update0Element(final Root root, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
      EList<NonRoot> _multiValuedContainmentEReference = targetElement.getMultiValuedContainmentEReference();
      _multiValuedContainmentEReference.add(newNonRoot);
    }
    
    public EObject getElement2(final Root root, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
      return newNonRoot;
    }
    
    public EObject getElement3(final Root root, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
      return insertedNonRoot;
    }
    
    public void updateNewNonRootElement(final Root root, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
      String _id = insertedNonRoot.getId();
      newNonRoot.setId(_id);
    }
    
    public void callRoutine1(final Root root, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.CreateNonRootEObjectInList);
    }
  }
  
  public InsertNonRootRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root, final NonRoot insertedNonRoot) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.InsertNonRootRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.root = root;this.insertedNonRoot = insertedNonRoot;
  }
  
  private Root root;
  
  private NonRoot insertedNonRoot;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertNonRootRoutine with input:");
    getLogger().debug("   Root: " + this.root);
    getLogger().debug("   NonRoot: " + this.insertedNonRoot);
    
    Root targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(root, insertedNonRoot), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    NonRoot newNonRoot = AllElementTypesFactoryImpl.eINSTANCE.createNonRoot();
    initializeCreateElementState(newNonRoot);
    userExecution.updateNewNonRootElement(root, insertedNonRoot, targetElement, newNonRoot);
    
    // val updatedElement userExecution.getElement1(root, insertedNonRoot, targetElement, newNonRoot);
    userExecution.update0Element(root, insertedNonRoot, targetElement, newNonRoot);
    
    addCorrespondenceBetween(userExecution.getElement2(root, insertedNonRoot, targetElement, newNonRoot), userExecution.getElement3(root, insertedNonRoot, targetElement, newNonRoot), "");
    
    userExecution.callRoutine1(root, insertedNonRoot, targetElement, newNonRoot, effectFacade);
    
    postprocessElementStates();
  }
}
