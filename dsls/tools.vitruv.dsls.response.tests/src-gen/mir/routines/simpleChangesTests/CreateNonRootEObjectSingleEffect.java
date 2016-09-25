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
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
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
  }
  
  private CreateNonRootEObjectSingleEffect.EffectUserExecution userExecution;
  
  public CreateNonRootEObjectSingleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root sourceRoot, final NonRoot containedObject) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.simpleChangesTests.CreateNonRootEObjectSingleEffect.EffectUserExecution(getExecutionState(), this);
    				this.sourceRoot = sourceRoot;this.containedObject = containedObject;
  }
  
  private Root sourceRoot;
  
  private NonRoot containedObject;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateNonRootEObjectSingleEffect with input:");
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
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.CreateNonRootEObjectSingleEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	sourceRoot, containedObject, targetElement, newNonRoot);
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    public void executeUserOperations(final Root sourceRoot, final NonRoot containedObject, final Root targetElement, final NonRoot newNonRoot) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.CreateNonRootEObjectSingle);
    }
  }
}
