package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateNonRootInContainerEffect extends AbstractEffectRealization {
  public CreateNonRootInContainerEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot) {
    super(responseExecutionState, calledBy);
    				this.container = container;this.insertedNonRoot = insertedNonRoot;
  }
  
  private NonRootObjectContainerHelper container;
  
  private NonRoot insertedNonRoot;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    private void executeUserOperations(final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot, final NonRootObjectContainerHelper nonRootContainer, final NonRoot newNonRoot) {
      String _id = insertedNonRoot.getId();
      newNonRoot.setId(_id);
      EList<NonRoot> _nonRootObjectsContainment = nonRootContainer.getNonRootObjectsContainment();
      _nonRootObjectsContainment.add(newNonRoot);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    @Extension
    private RoutinesFacade effectFacade;
  }
  
  private EObject getElement0(final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot, final NonRootObjectContainerHelper nonRootContainer, final NonRoot newNonRoot) {
    return newNonRoot;
  }
  
  private EObject getElement1(final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot, final NonRootObjectContainerHelper nonRootContainer, final NonRoot newNonRoot) {
    return insertedNonRoot;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateNonRootInContainerEffect with input:");
    getLogger().debug("   NonRootObjectContainerHelper: " + this.container);
    getLogger().debug("   NonRoot: " + this.insertedNonRoot);
    
    NonRootObjectContainerHelper nonRootContainer = getCorrespondingElement(
    	getCorrepondenceSourceNonRootContainer(container, insertedNonRoot), // correspondence source supplier
    	NonRootObjectContainerHelper.class,
    	(NonRootObjectContainerHelper _element) -> true, // correspondence precondition checker
    	null);
    if (nonRootContainer == null) {
    	return;
    }
    initializeRetrieveElementState(nonRootContainer);
    NonRoot newNonRoot = AllElementTypesFactoryImpl.eINSTANCE.createNonRoot();
    initializeCreateElementState(newNonRoot);
    
    addCorrespondenceBetween(getElement0(container, insertedNonRoot, nonRootContainer, newNonRoot), getElement1(container, insertedNonRoot, nonRootContainer, newNonRoot), "");
    preprocessElementStates();
    new mir.routines.simpleChangesTests.CreateNonRootInContainerEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	container, insertedNonRoot, nonRootContainer, newNonRoot);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceNonRootContainer(final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot) {
    return container;
  }
}
