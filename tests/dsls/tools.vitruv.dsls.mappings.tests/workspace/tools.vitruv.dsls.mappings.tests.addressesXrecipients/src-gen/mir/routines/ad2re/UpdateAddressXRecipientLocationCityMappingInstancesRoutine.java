package mir.routines.ad2re;

import edu.kit.ipd.sdq.mdsd.addresses.Address;
import java.io.IOException;
import mir.routines.ad2re.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateAddressXRecipientLocationCityMappingInstancesRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private UpdateAddressXRecipientLocationCityMappingInstancesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(@Extension final RoutinesFacade _routinesFacade) {
      final Iterable<Address> instances = AddressXRecipientLocationCityMapping.getLeftInstances();
      for (final Address a : instances) {
        _routinesFacade.updateAddressXRecipientLocationCityMappingInstance(a);
      }
    }
  }
  
  public UpdateAddressXRecipientLocationCityMappingInstancesRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ad2re.UpdateAddressXRecipientLocationCityMappingInstancesRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ad2re.RoutinesFacade(getExecutionState(), this);
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateAddressXRecipientLocationCityMappingInstancesRoutine with input:");
    
    userExecution.callRoutine1(, actionsFacade);
    
    postprocessElements();
  }
}
