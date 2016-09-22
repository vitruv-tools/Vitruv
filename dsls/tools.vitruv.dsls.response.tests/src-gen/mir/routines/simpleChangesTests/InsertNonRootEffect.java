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
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertNonRootEffect extends AbstractEffectRealization {
  public InsertNonRootEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root, final NonRoot insertedNonRoot) {
    super(responseExecutionState, calledBy);
    				this.root = root;this.insertedNonRoot = insertedNonRoot;
  }
  
  private Root root;
  
  private NonRoot insertedNonRoot;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    private void executeUserOperations(final Root root, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
      String _id = insertedNonRoot.getId();
      newNonRoot.setId(_id);
      EList<NonRoot> _multiValuedContainmentEReference = targetElement.getMultiValuedContainmentEReference();
      _multiValuedContainmentEReference.add(newNonRoot);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.CreateNonRootEObjectInList);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
  
  private EObject getElement0(final Root root, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
    return newNonRoot;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final Root root, final NonRoot insertedNonRoot) {
    return root;
  }
  
  private EObject getElement1(final Root root, final NonRoot insertedNonRoot, final Root targetElement, final NonRoot newNonRoot) {
    return insertedNonRoot;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertNonRootEffect with input:");
    getLogger().debug("   Root: " + this.root);
    getLogger().debug("   NonRoot: " + this.insertedNonRoot);
    
    Root targetElement = getCorrespondingElement(
    	getCorrepondenceSourceTargetElement(root, insertedNonRoot), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    NonRoot newNonRoot = AllElementTypesFactoryImpl.eINSTANCE.createNonRoot();
    initializeCreateElementState(newNonRoot);
    
    addCorrespondenceBetween(getElement0(root, insertedNonRoot, targetElement, newNonRoot), getElement1(root, insertedNonRoot, targetElement, newNonRoot), "");
    preprocessElementStates();
    new mir.routines.simpleChangesTests.InsertNonRootEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	root, insertedNonRoot, targetElement, newNonRoot);
    postprocessElementStates();
  }
}
