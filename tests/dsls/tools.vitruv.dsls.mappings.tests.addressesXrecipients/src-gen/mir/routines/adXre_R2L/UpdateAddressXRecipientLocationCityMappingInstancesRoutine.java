package mir.routines.adXre_R2L;

import java.io.IOException;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.AddressXRecipientLocationCityMapping;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.RightAddressXRecipientLocationCityInstanceHalf;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * ********* BEGIN AddressXRecipientLocationCity UPDATE ROUTINES *********
 */
@SuppressWarnings("all")
public class UpdateAddressXRecipientLocationCityMappingInstancesRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private UpdateAddressXRecipientLocationCityMappingInstancesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(@Extension final RoutinesFacade _routinesFacade) {
      final Iterable<RightAddressXRecipientLocationCityInstanceHalf> instances = AddressXRecipientLocationCityMapping.addressXRecipientLocationCityMapping().getRightInstanceHalvesClone();
      for (final RightAddressXRecipientLocationCityInstanceHalf instance : instances) {
        _routinesFacade.updateAddressXRecipientLocationCityMappingInstance(instance.getRootXroot().getRRoot(), instance.getR(), instance.getL(), instance.getC());
      }
    }
  }
  
  public UpdateAddressXRecipientLocationCityMappingInstancesRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_R2L.UpdateAddressXRecipientLocationCityMappingInstancesRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_R2L.RoutinesFacade(getExecutionState(), this);
  }
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateAddressXRecipientLocationCityMappingInstancesRoutine with input:");
    
    userExecution.callRoutine1(actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
