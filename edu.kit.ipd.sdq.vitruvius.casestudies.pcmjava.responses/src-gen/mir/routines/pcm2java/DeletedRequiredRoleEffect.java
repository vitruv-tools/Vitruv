package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;

@SuppressWarnings("all")
public class DeletedRequiredRoleEffect extends AbstractEffectRealization {
  public DeletedRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final DeleteNonRootEObjectInList<RequiredRole> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private DeleteNonRootEObjectInList<RequiredRole> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeletedRequiredRoleEffect with input:");
    getLogger().debug("   DeleteNonRootEObjectInList: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.DeletedRequiredRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final DeleteNonRootEObjectInList<RequiredRole> change) {
      RequiredRole _oldValue = change.getOldValue();
      EObject _oldAffectedEObject = change.getOldAffectedEObject();
      this.effectFacade.callRemoveRequiredRole(_oldValue, ((RepositoryComponent) _oldAffectedEObject));
    }
  }
}
