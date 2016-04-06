package mir.effects.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.RemoveNonContainmentEReference;
import java.io.IOException;
import java.util.function.Predicate;
import mir.effects.simpleChangesTests.EffectsFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class RemoveNonContainmentEReferenceEffect extends AbstractEffectRealization {
  public RemoveNonContainmentEReferenceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private RemoveNonContainmentEReference<NonRoot> change;
  
  private boolean isChangeSet;
  
  public void setChange(final RemoveNonContainmentEReference<NonRoot> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getCorrepondenceSourceTargetRoot(final RemoveNonContainmentEReference<NonRoot> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect RemoveNonContainmentEReferenceEffect with input:");
    getLogger().debug("   RemoveNonContainmentEReference: " + this.change);
    
    Root targetRoot = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceTargetRoot(change), // correspondence source supplier
    	(Root _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Root.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.simpleChangesTests.RemoveNonContainmentEReferenceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetRoot);
    postProcessElements();
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final RemoveNonContainmentEReference<NonRoot> change, final Root targetRoot) {
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
