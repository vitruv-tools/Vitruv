package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class CreatedSystemEffect extends AbstractEffectRealization {
  public CreatedSystemEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateRootEObject<org.palladiosimulator.pcm.system.System> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateRootEObject<org.palladiosimulator.pcm.system.System> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreatedSystemEffect with input:");
    getLogger().debug("   CreateRootEObject: " + this.change);
    
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.CreatedSystemEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final CreateRootEObject<org.palladiosimulator.pcm.system.System> change) {
      final org.palladiosimulator.pcm.system.System system = change.getNewValue();
      String _entityName = system.getEntityName();
      this.effectFacade.callCreateJavaPackage(system, null, _entityName, "root_system");
      this.effectFacade.callCreateImplementationForSystem(system);
    }
  }
}
