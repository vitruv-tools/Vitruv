package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import com.google.common.base.Objects;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruvius.framework.change.echange.feature.reference.RemoveEReference;

import java.io.IOException;
import java.util.function.Predicate;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class RemoveNonContainmentEReferenceEffect extends AbstractEffectRealization {
  public RemoveNonContainmentEReferenceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RemoveEReference<Root, NonRoot> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private RemoveEReference<Root, NonRoot> change;
  
  private EObject getCorrepondenceSourceTargetRoot(final RemoveEReference<Root, NonRoot> change) {
    Root _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveNonContainmentEReferenceEffect with input:");
    getLogger().debug("   RemoveEReference: " + this.change);
    
    Root targetRoot = getCorrespondingElement(
    	getCorrepondenceSourceTargetRoot(change), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetRoot == null) {
    	return;
    }
    initializeRetrieveElementState(targetRoot);
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.RemoveNonContainmentEReferenceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetRoot);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final RemoveEReference<Root, NonRoot> change, final Root targetRoot) {
      EList<NonRoot> _multiValuedNonContainmentEReference = targetRoot.getMultiValuedNonContainmentEReference();
      final Predicate<NonRoot> _function = (NonRoot it) -> {
        String _id = it.getId();
        NonRoot _oldValue = change.getOldValue();
        String _id_1 = _oldValue.getId();
        return Objects.equal(_id, _id_1);
      };
      _multiValuedNonContainmentEReference.removeIf(_function);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.RemoveNonContainmentEReference);
    }
  }
}
