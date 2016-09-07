package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;

import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class CreateNonRootEObjectInListEffect extends AbstractEffectRealization {
  public CreateNonRootEObjectInListEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<Root, NonRoot> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<Root, NonRoot> change;
  
  private EObject getElement0(final InsertEReference<Root, NonRoot> change, final Root targetElement, final NonRoot newNonRoot) {
    return newNonRoot;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final InsertEReference<Root, NonRoot> change) {
    Root _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private EObject getElement1(final InsertEReference<Root, NonRoot> change, final Root targetElement, final NonRoot newNonRoot) {
    NonRoot _newValue = change.getNewValue();
    return _newValue;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateNonRootEObjectInListEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    Root targetElement = getCorrespondingElement(
    	getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    NonRoot newNonRoot = AllElementTypesFactoryImpl.eINSTANCE.createNonRoot();
    initializeCreateElementState(newNonRoot);
    
    addCorrespondenceBetween(getElement0(change, targetElement, newNonRoot), getElement1(change, targetElement, newNonRoot), "");
    preprocessElementStates();
    new mir.routines.simpleChangesTests.CreateNonRootEObjectInListEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetElement, newNonRoot);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<Root, NonRoot> change, final Root targetElement, final NonRoot newNonRoot) {
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
