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
public class RemoveRequiredRoleFromInterfaceRequiringEntityEffect extends AbstractEffectRealization {
  public RemoveRequiredRoleFromInterfaceRequiringEntityEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private DeleteNonRootEObjectInList<RequiredRole> change;
  
  private boolean isChangeSet;
  
  public void setChange(final DeleteNonRootEObjectInList<RequiredRole> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine RemoveRequiredRoleFromInterfaceRequiringEntityEffect with input:");
    getLogger().debug("   DeleteNonRootEObjectInList: " + this.change);
    
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.RemoveRequiredRoleFromInterfaceRequiringEntityEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final DeleteNonRootEObjectInList<RequiredRole> change) {
      RequiredRole _oldValue = change.getOldValue();
      EObject _oldAffectedEObject = change.getOldAffectedEObject();
      this.effectFacade.callRemoveRequiredRole(_oldValue, ((RepositoryComponent) _oldAffectedEObject));
    }
  }
}
