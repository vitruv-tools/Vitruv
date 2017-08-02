package mir.routines.ad2re;

import edu.kit.ipd.sdq.mdsd.addresses.Address;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void ensureAllMappings() {
    mir.routines.ad2re.EnsureAllMappingsRoutine effect = new mir.routines.ad2re.EnsureAllMappingsRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void ensureAddressXRecipientLocationCityMapping() {
    mir.routines.ad2re.EnsureAddressXRecipientLocationCityMappingRoutine effect = new mir.routines.ad2re.EnsureAddressXRecipientLocationCityMappingRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void deleteAddressXRecipientLocationCityMappingInstances() {
    mir.routines.ad2re.DeleteAddressXRecipientLocationCityMappingInstancesRoutine effect = new mir.routines.ad2re.DeleteAddressXRecipientLocationCityMappingInstancesRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void deleteAddressXRecipientLocationCityMappingInstance(final Address a) {
    mir.routines.ad2re.DeleteAddressXRecipientLocationCityMappingInstanceRoutine effect = new mir.routines.ad2re.DeleteAddressXRecipientLocationCityMappingInstanceRoutine(this.executionState, calledBy, a);
    effect.applyRoutine();
  }
  
  public void createAddressXRecipientLocationCityMappingInstances() {
    mir.routines.ad2re.CreateAddressXRecipientLocationCityMappingInstancesRoutine effect = new mir.routines.ad2re.CreateAddressXRecipientLocationCityMappingInstancesRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void createAddressXRecipientLocationCityMappingInstance(final Address a) {
    mir.routines.ad2re.CreateAddressXRecipientLocationCityMappingInstanceRoutine effect = new mir.routines.ad2re.CreateAddressXRecipientLocationCityMappingInstanceRoutine(this.executionState, calledBy, a);
    effect.applyRoutine();
  }
  
  public void updateAddressXRecipientLocationCityMappingInstances() {
    mir.routines.ad2re.UpdateAddressXRecipientLocationCityMappingInstancesRoutine effect = new mir.routines.ad2re.UpdateAddressXRecipientLocationCityMappingInstancesRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void updateAddressXRecipientLocationCityMappingInstance(final Address a) {
    mir.routines.ad2re.UpdateAddressXRecipientLocationCityMappingInstanceRoutine effect = new mir.routines.ad2re.UpdateAddressXRecipientLocationCityMappingInstanceRoutine(this.executionState, calledBy, a);
    effect.applyRoutine();
  }
}
