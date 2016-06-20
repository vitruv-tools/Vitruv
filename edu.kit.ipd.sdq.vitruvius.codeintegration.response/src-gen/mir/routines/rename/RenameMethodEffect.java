package mir.routines.rename;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import java.io.IOException;
import mir.routines.rename.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class RenameMethodEffect extends AbstractEffectRealization {
  public RenameMethodEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private UpdateSingleValuedEAttribute<String> change;
  
  private boolean isChangeSet;
  
  public void setChange(final UpdateSingleValuedEAttribute<String> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceOperationSignature(final UpdateSingleValuedEAttribute<String> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine RenameMethodEffect with input:");
    getLogger().debug("   UpdateSingleValuedEAttribute: " + this.change);
    
    OperationSignature operationSignature = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceOperationSignature(change), // correspondence source supplier
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	OperationSignature.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.rename.RenameMethodEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, operationSignature);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private mir.routines.rename.RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final UpdateSingleValuedEAttribute<String> change, final OperationSignature operationSignature) {
      final OperationInterface operationInterface = operationSignature.getInterface__OperationSignature();
      String _newValue = change.getNewValue();
      operationInterface.setEntityName(_newValue);
    }
  }
}
