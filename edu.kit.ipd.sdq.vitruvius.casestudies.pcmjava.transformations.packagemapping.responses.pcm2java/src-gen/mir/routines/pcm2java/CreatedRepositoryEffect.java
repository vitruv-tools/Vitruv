package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.root.InsertRootEObject;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class CreatedRepositoryEffect extends AbstractEffectRealization {
  public CreatedRepositoryEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertRootEObject<Repository> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertRootEObject<Repository> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedRepositoryEffect with input:");
    getLogger().debug("   InsertRootEObject: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreatedRepositoryEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final InsertRootEObject<Repository> change) {
      final Repository repository = change.getNewValue();
      String _entityName = repository.getEntityName();
      this.effectFacade.callCreateJavaPackage(repository, null, _entityName, "repository_root");
      this.effectFacade.callCreateRepositorySubPackages(repository);
    }
  }
}
