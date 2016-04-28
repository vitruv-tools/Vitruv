package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

@SuppressWarnings("all")
public class DeletePackageForBasicComponentEffect extends AbstractEffectRealization {
  public DeletePackageForBasicComponentEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private DeleteNonRootEObjectInList<RepositoryComponent> change;
  
  private boolean isChangeSet;
  
  public void setChange(final DeleteNonRootEObjectInList<RepositoryComponent> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine DeletePackageForBasicComponentEffect with input:");
    getLogger().debug("   DeleteNonRootEObjectInList: " + this.change);
    
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.DeletePackageForBasicComponentEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final DeleteNonRootEObjectInList<RepositoryComponent> change) {
      RepositoryComponent _oldValue = change.getOldValue();
      RepositoryComponent _oldValue_1 = change.getOldValue();
      String _entityName = _oldValue_1.getEntityName();
      this.effectFacade.callDeleteJavaPackage(_oldValue, _entityName, "");
    }
  }
}
