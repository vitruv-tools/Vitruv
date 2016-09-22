package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateNonRootEObjectSingleEffect extends AbstractEffectRealization {
  public CreateNonRootEObjectSingleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root sourceRoot, final NonRoot containedObject) {
    super(responseExecutionState, calledBy);
    				this.sourceRoot = sourceRoot;this.containedObject = containedObject;
  }
  
  private Root sourceRoot;
  
  private NonRoot containedObject;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    private void executeUserOperations(final Root sourceRoot, final NonRoot containedObject, final Root targetElement, final NonRoot newNonRoot) {
      String _id = containedObject.getId();
      newNonRoot.setId(_id);
      targetElement.setSingleValuedContainmentEReference(newNonRoot);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.CreateNonRootEObjectSingle);
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
  
  private EObject getElement0(final Root sourceRoot, final NonRoot containedObject, final Root targetElement, final NonRoot newNonRoot) {
    return newNonRoot;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final Root sourceRoot, final NonRoot containedObject) {
    return sourceRoot;
  }
  
  private EObject getElement1(final Root sourceRoot, final NonRoot containedObject, final Root targetElement, final NonRoot newNonRoot) {
    return containedObject;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateNonRootEObjectSingleEffect with input:");
    getLogger().debug("   Root: " + this.sourceRoot);
    getLogger().debug("   NonRoot: " + this.containedObject);
    
    Root targetElement = getCorrespondingElement(
    	getCorrepondenceSourceTargetElement(sourceRoot, containedObject), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    NonRoot newNonRoot = AllElementTypesFactoryImpl.eINSTANCE.createNonRoot();
    initializeCreateElementState(newNonRoot);
    
    addCorrespondenceBetween(getElement0(sourceRoot, containedObject, targetElement, newNonRoot), getElement1(sourceRoot, containedObject, targetElement, newNonRoot), "");
    preprocessElementStates();
    new mir.routines.simpleChangesTests.CreateNonRootEObjectSingleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	sourceRoot, containedObject, targetElement, newNonRoot);
    postprocessElementStates();
  }
}
