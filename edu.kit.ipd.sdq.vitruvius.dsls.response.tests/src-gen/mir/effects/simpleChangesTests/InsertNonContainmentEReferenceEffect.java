package mir.effects.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;
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
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.InsertNonContainmentEReference;
import java.io.IOException;
import mir.effects.simpleChangesTests.EffectsFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class InsertNonContainmentEReferenceEffect extends AbstractEffectRealization {
  public InsertNonContainmentEReferenceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private InsertNonContainmentEReference<NonRoot> change;
  
  private boolean isChangeSet;
  
  public void setChange(final InsertNonContainmentEReference<NonRoot> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final InsertNonContainmentEReference<NonRoot> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect InsertNonContainmentEReferenceEffect with input:");
    getLogger().debug("   InsertNonContainmentEReference: " + this.change);
    
    Root targetElement = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	(Root _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Root.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.simpleChangesTests.InsertNonContainmentEReferenceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetElement);
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
    
    private void executeUserOperations(final InsertNonContainmentEReference<NonRoot> change, final Root targetElement) {
      NonRootObjectContainerHelper _nonRootObjectContainerHelper = targetElement.getNonRootObjectContainerHelper();
      EList<NonRoot> _nonRootObjectsContainment = _nonRootObjectContainerHelper.getNonRootObjectsContainment();
      final Function1<NonRoot, Boolean> _function = (NonRoot it) -> {
        String _id = it.getId();
        NonRoot _newValue = change.getNewValue();
        String _id_1 = _newValue.getId();
        return Boolean.valueOf(Objects.equal(_id, _id_1));
      };
      final NonRoot addedNonRoot = IterableExtensions.<NonRoot>findFirst(_nonRootObjectsContainment, _function);
      EList<NonRoot> _multiValuedNonContainmentEReference = targetElement.getMultiValuedNonContainmentEReference();
      _multiValuedNonContainmentEReference.add(addedNonRoot);
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.InsertNonContainmentEReference);
    }
  }
}
