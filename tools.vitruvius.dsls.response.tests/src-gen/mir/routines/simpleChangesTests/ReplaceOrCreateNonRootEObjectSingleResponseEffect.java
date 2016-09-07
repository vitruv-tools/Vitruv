package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import com.google.common.base.Objects;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class ReplaceOrCreateNonRootEObjectSingleResponseEffect extends AbstractEffectRealization {
  public ReplaceOrCreateNonRootEObjectSingleResponseEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEReference<Root, NonRoot> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEReference<Root, NonRoot> change;
  
  private EObject getElement0(final ReplaceSingleValuedEReference<Root, NonRoot> change, final Root targetElement, final NonRoot newNonRoot) {
    return newNonRoot;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final ReplaceSingleValuedEReference<Root, NonRoot> change) {
    Root _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private EObject getElement1(final ReplaceSingleValuedEReference<Root, NonRoot> change, final Root targetElement, final NonRoot newNonRoot) {
    NonRoot _newValue = change.getNewValue();
    return _newValue;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceOrCreateNonRootEObjectSingleResponseEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEReference: " + this.change);
    
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
    new mir.routines.simpleChangesTests.ReplaceOrCreateNonRootEObjectSingleResponseEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final ReplaceSingleValuedEReference<Root, NonRoot> change, final Root targetElement, final NonRoot newNonRoot) {
      NonRoot _newValue = change.getNewValue();
      String _id = _newValue.getId();
      newNonRoot.setId(_id);
      targetElement.setSingleValuedContainmentEReference(newNonRoot);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.CreateNonRootEObjectSingle);
      NonRoot _oldValue = change.getOldValue();
      boolean _notEquals = (!Objects.equal(_oldValue, null));
      if (_notEquals) {
        SimpleChangesTestsExecutionMonitor _instance_1 = SimpleChangesTestsExecutionMonitor.getInstance();
        _instance_1.set(SimpleChangesTestsExecutionMonitor.ChangeType.DeleteNonRootEObjectSingle);
      }
    }
  }
}
