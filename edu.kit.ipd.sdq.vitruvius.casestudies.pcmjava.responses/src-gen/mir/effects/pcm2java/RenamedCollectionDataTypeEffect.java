package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;

@SuppressWarnings("all")
public class RenamedCollectionDataTypeEffect extends AbstractEffectRealization {
  public RenamedCollectionDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
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
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect RenamedCollectionDataTypeEffect with input:");
    getLogger().debug("   UpdateSingleValuedEAttribute: " + this.change);
    
    preProcessElements();
    new mir.effects.pcm2java.RenamedCollectionDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change);
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
    
    private void executeUserOperations(final UpdateSingleValuedEAttribute<String> change) {
      EObject _newAffectedEObject = change.getNewAffectedEObject();
      this.effectFacade.callRenameCollectionDataType(((CollectionDataType) _newAffectedEObject));
    }
  }
}
