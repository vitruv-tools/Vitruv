package mir.routines.adXre_R2L;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import java.io.IOException;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class EnforceLeftAdRootXReRootMappingConditionsRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private EnforceLeftAdRootXReRootMappingConditionsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Addresses aRoot) {
      return aRoot;
    }
    
    public void update0Element(final Addresses aRoot) {
    }
  }
  
  public EnforceLeftAdRootXReRootMappingConditionsRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses aRoot) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_R2L.EnforceLeftAdRootXReRootMappingConditionsRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_R2L.RoutinesFacade(getExecutionState(), this);
    this.aRoot = aRoot;
  }
  
  private Addresses aRoot;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine EnforceLeftAdRootXReRootMappingConditionsRoutine with input:");
    getLogger().debug("   aRoot: " + this.aRoot);
    
    // val updatedElement userExecution.getElement1(aRoot);
    userExecution.update0Element(aRoot);
    
    postprocessElements();
    
    return true;
  }
}
