package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateNonRootInContainerEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private CreateNonRootInContainerEffect.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot, final NonRootObjectContainerHelper nonRootContainer, final NonRoot newNonRoot) {
      return nonRootContainer;
    }
    
    public void update0Element(final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot, final NonRootObjectContainerHelper nonRootContainer, final NonRoot newNonRoot) {
      EList<NonRoot> _nonRootObjectsContainment = nonRootContainer.getNonRootObjectsContainment();
      _nonRootObjectsContainment.add(newNonRoot);
    }
    
    public EObject getElement2(final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot, final NonRootObjectContainerHelper nonRootContainer, final NonRoot newNonRoot) {
      return newNonRoot;
    }
    
    public EObject getElement3(final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot, final NonRootObjectContainerHelper nonRootContainer, final NonRoot newNonRoot) {
      return insertedNonRoot;
    }
    
    public EObject getCorrepondenceSourceNonRootContainer(final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot) {
      return container;
    }
    
    public void updateNewNonRootElement(final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot, final NonRootObjectContainerHelper nonRootContainer, final NonRoot newNonRoot) {
      String _id = insertedNonRoot.getId();
      newNonRoot.setId(_id);
    }
  }
  
  public CreateNonRootInContainerEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.CreateNonRootInContainerEffect.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.container = container;this.insertedNonRoot = insertedNonRoot;
  }
  
  private NonRootObjectContainerHelper container;
  
  private NonRoot insertedNonRoot;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateNonRootInContainerEffect with input:");
    getLogger().debug("   NonRootObjectContainerHelper: " + this.container);
    getLogger().debug("   NonRoot: " + this.insertedNonRoot);
    
    NonRootObjectContainerHelper nonRootContainer = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceNonRootContainer(container, insertedNonRoot), // correspondence source supplier
    	NonRootObjectContainerHelper.class,
    	(NonRootObjectContainerHelper _element) -> true, // correspondence precondition checker
    	null);
    if (nonRootContainer == null) {
    	return;
    }
    initializeRetrieveElementState(nonRootContainer);
    NonRoot newNonRoot = AllElementTypesFactoryImpl.eINSTANCE.createNonRoot();
    initializeCreateElementState(newNonRoot);
    userExecution.updateNewNonRootElement(container, insertedNonRoot, nonRootContainer, newNonRoot);
    
    // val updatedElement userExecution.getElement1(container, insertedNonRoot, nonRootContainer, newNonRoot);
    userExecution.update0Element(container, insertedNonRoot, nonRootContainer, newNonRoot);
    
    addCorrespondenceBetween(userExecution.getElement2(container, insertedNonRoot, nonRootContainer, newNonRoot), userExecution.getElement3(container, insertedNonRoot, nonRootContainer, newNonRoot), "");
    
    postprocessElementStates();
  }
}
