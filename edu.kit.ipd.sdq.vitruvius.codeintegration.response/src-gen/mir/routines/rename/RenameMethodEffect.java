package mir.routines.rename;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import java.io.IOException;
import mir.routines.rename.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class RenameMethodEffect extends AbstractEffectRealization {
  public RenameMethodEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final UpdateSingleValuedEAttribute<String> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private UpdateSingleValuedEAttribute<String> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameMethodEffect with input:");
    getLogger().debug("   UpdateSingleValuedEAttribute: " + this.change);
    
    OperationSignature operationSignature = getCorrespondingElement(
    	getCorrepondenceSourceOperationSignature(change), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (operationSignature == null) {
    	return;
    }
    initializeRetrieveElementState(operationSignature);
    
    preprocessElementStates();
    new mir.routines.rename.RenameMethodEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, operationSignature);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOperationSignature(final UpdateSingleValuedEAttribute<String> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.rename.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final UpdateSingleValuedEAttribute<String> change, final OperationSignature operationSignature) {
      String _newValue = change.getNewValue();
      operationSignature.setEntityName(_newValue);
    }
  }
}
