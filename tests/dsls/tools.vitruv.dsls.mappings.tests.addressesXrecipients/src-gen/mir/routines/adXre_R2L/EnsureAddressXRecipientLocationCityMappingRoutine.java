package mir.routines.adXre_R2L;

import java.io.IOException;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * *****************************************************
 * ********** BEGIN AddressXRecipientLocationCity MAPPING **********
 * ******************************************************
 */
@SuppressWarnings("all")
public class EnsureAddressXRecipientLocationCityMappingRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private EnsureAddressXRecipientLocationCityMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(@Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteAddressXRecipientLocationCityMappingInstances();
      _routinesFacade.createAddressXRecipientLocationCityMappingInstances();
      _routinesFacade.updateAddressXRecipientLocationCityMappingInstances();
    }
  }
  
  public EnsureAddressXRecipientLocationCityMappingRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_R2L.EnsureAddressXRecipientLocationCityMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_R2L.RoutinesFacade(getExecutionState(), this);
  }
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine EnsureAddressXRecipientLocationCityMappingRoutine with input:");
    
    userExecution.callRoutine1(actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
