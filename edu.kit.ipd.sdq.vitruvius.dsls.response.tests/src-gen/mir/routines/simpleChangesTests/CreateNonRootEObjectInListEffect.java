package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class CreateNonRootEObjectInListEffect extends AbstractEffectRealization {
  public CreateNonRootEObjectInListEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateNonRootEObjectInList<NonRoot> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateNonRootEObjectInList<NonRoot> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getElement0(final CreateNonRootEObjectInList<NonRoot> change, final Root targetElement, final NonRoot newNonRoot) {
    return newNonRoot;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final CreateNonRootEObjectInList<NonRoot> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  private EObject getElement1(final CreateNonRootEObjectInList<NonRoot> change, final Root targetElement, final NonRoot newNonRoot) {
    NonRoot _newValue = change.getNewValue();
    return _newValue;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreateNonRootEObjectInListEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    Root targetElement = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	(Root _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Root.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    NonRoot newNonRoot = AllElementTypesFactoryImpl.eINSTANCE.createNonRoot();
    initializeCreateElementState(newNonRoot);
    
    addCorrespondenceBetween(getElement0(change, targetElement, newNonRoot), getElement1(change, targetElement, newNonRoot), "");
    preProcessElements();
    new mir.routines.simpleChangesTests.CreateNonRootEObjectInListEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetElement, newNonRoot);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CreateNonRootEObjectInList<NonRoot> change, final Root targetElement, final NonRoot newNonRoot) {
      NonRoot _newValue = change.getNewValue();
      String _id = _newValue.getId();
      newNonRoot.setId(_id);
      EList<NonRoot> _multiValuedContainmentEReference = targetElement.getMultiValuedContainmentEReference();
      _multiValuedContainmentEReference.add(newNonRoot);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.CreateNonRootEObjectInList);
    }
  }
}
