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
public class DeletedComponentEffect extends AbstractEffectRealization {
  public DeletedComponentEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final DeleteNonRootEObjectInList<RepositoryComponent> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private DeleteNonRootEObjectInList<RepositoryComponent> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeletedComponentEffect with input:");
    getLogger().debug("   DeleteNonRootEObjectInList: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.DeletedComponentEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final DeleteNonRootEObjectInList<RepositoryComponent> change) {
      RepositoryComponent _oldValue = change.getOldValue();
      RepositoryComponent _oldValue_1 = change.getOldValue();
      String _entityName = _oldValue_1.getEntityName();
      this.effectFacade.callDeleteJavaPackage(_oldValue, _entityName, "");
      RepositoryComponent _oldValue_2 = change.getOldValue();
      this.effectFacade.callDeleteJavaClassifier(_oldValue_2);
    }
  }
}
