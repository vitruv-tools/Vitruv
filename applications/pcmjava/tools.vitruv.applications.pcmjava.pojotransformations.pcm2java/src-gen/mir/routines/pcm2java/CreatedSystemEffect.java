package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;

@SuppressWarnings("all")
public class CreatedSystemEffect extends AbstractEffectRealization {
  public CreatedSystemEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertRootEObject<org.palladiosimulator.pcm.system.System> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertRootEObject<org.palladiosimulator.pcm.system.System> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedSystemEffect with input:");
    getLogger().debug("   InsertRootEObject: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreatedSystemEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final InsertRootEObject<org.palladiosimulator.pcm.system.System> change) {
      final org.palladiosimulator.pcm.system.System system = change.getNewValue();
      String _entityName = system.getEntityName();
      this.effectFacade.callCreateJavaPackage(system, null, _entityName, "root_system");
      this.effectFacade.callCreateImplementationForSystem(system);
    }
  }
}
