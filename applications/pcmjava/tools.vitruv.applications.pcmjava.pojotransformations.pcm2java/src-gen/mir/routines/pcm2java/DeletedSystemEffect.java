package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.root.RemoveRootEObject;

@SuppressWarnings("all")
public class DeletedSystemEffect extends AbstractEffectRealization {
  public DeletedSystemEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RemoveRootEObject<org.palladiosimulator.pcm.system.System> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private RemoveRootEObject<org.palladiosimulator.pcm.system.System> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeletedSystemEffect with input:");
    getLogger().debug("   RemoveRootEObject: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.DeletedSystemEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final RemoveRootEObject<org.palladiosimulator.pcm.system.System> change) {
      final org.palladiosimulator.pcm.system.System system = change.getOldValue();
      String _entityName = system.getEntityName();
      this.effectFacade.callDeleteJavaPackage(system, _entityName, "root_system");
      this.effectFacade.callDeleteJavaClassifier(system);
    }
  }
}
