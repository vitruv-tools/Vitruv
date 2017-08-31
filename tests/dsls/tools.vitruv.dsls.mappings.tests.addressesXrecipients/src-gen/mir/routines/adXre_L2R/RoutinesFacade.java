package mir.routines.adXre_L2R;

import edu.kit.ipd.sdq.mdsd.addresses.Address;
import edu.kit.ipd.sdq.mdsd.addresses.Addresses;
import edu.kit.ipd.sdq.mdsd.recipients.City;
import edu.kit.ipd.sdq.mdsd.recipients.Location;
import edu.kit.ipd.sdq.mdsd.recipients.Recipient;
import edu.kit.ipd.sdq.mdsd.recipients.Recipients;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void ensureAllMappings() {
    mir.routines.adXre_L2R.EnsureAllMappingsRoutine effect = new mir.routines.adXre_L2R.EnsureAllMappingsRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void ensureAdRootXReRootMapping() {
    mir.routines.adXre_L2R.EnsureAdRootXReRootMappingRoutine effect = new mir.routines.adXre_L2R.EnsureAdRootXReRootMappingRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void deleteAdRootXReRootMappingInstances() {
    mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstancesRoutine effect = new mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstancesRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void deleteAdRootXReRootMappingInstance(final Addresses aRoot) {
    mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstanceRoutine effect = new mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstanceRoutine(this.executionState, calledBy, aRoot);
    effect.applyRoutine();
  }
  
  public void createAdRootXReRootMappingInstances() {
    mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstancesRoutine effect = new mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstancesRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void createAdRootXReRootMappingInstance(final Addresses aRoot) {
    mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstanceRoutine effect = new mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstanceRoutine(this.executionState, calledBy, aRoot);
    effect.applyRoutine();
  }
  
  public void enforceRightAdRootXReRootMappingConditions(final Recipients rRoot) {
    mir.routines.adXre_L2R.EnforceRightAdRootXReRootMappingConditionsRoutine effect = new mir.routines.adXre_L2R.EnforceRightAdRootXReRootMappingConditionsRoutine(this.executionState, calledBy, rRoot);
    effect.applyRoutine();
  }
  
  public void enforceAdRootXReRootMappingConditionsFromLeftToRight(final Addresses aRoot, final Recipients rRoot) {
    mir.routines.adXre_L2R.EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine effect = new mir.routines.adXre_L2R.EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine(this.executionState, calledBy, aRoot, rRoot);
    effect.applyRoutine();
  }
  
  public void updateAdRootXReRootMappingInstances() {
    mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstancesRoutine effect = new mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstancesRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void updateAdRootXReRootMappingInstance(final Addresses aRoot) {
    mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstanceRoutine effect = new mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstanceRoutine(this.executionState, calledBy, aRoot);
    effect.applyRoutine();
  }
  
  public void ensureAddressXRecipientLocationCityMapping() {
    mir.routines.adXre_L2R.EnsureAddressXRecipientLocationCityMappingRoutine effect = new mir.routines.adXre_L2R.EnsureAddressXRecipientLocationCityMappingRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void deleteAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstancesRoutine effect = new mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstancesRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void deleteAddressXRecipientLocationCityMappingInstance(final Addresses aRoot, final Address a) {
    mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstanceRoutine effect = new mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstanceRoutine(this.executionState, calledBy, aRoot, a);
    effect.applyRoutine();
  }
  
  public void createAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstancesRoutine effect = new mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstancesRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void createAddressXRecipientLocationCityMappingInstance(final Addresses aRoot, final Address a) {
    mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstanceRoutine effect = new mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstanceRoutine(this.executionState, calledBy, aRoot, a);
    effect.applyRoutine();
  }
  
  public void enforceRightAddressXRecipientLocationCityMappingConditions(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_L2R.EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine effect = new mir.routines.adXre_L2R.EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine(this.executionState, calledBy, rRoot, r, l, c);
    effect.applyRoutine();
  }
  
  public void enforceAddressXRecipientLocationCityMappingConditionsFromLeftToRight(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_L2R.EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine effect = new mir.routines.adXre_L2R.EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine(this.executionState, calledBy, aRoot, rRoot, a, r, l, c);
    effect.applyRoutine();
  }
  
  public void updateAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstancesRoutine effect = new mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstancesRoutine(this.executionState, calledBy);
    effect.applyRoutine();
  }
  
  public void updateAddressXRecipientLocationCityMappingInstance(final Addresses aRoot, final Address a) {
    mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstanceRoutine effect = new mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstanceRoutine(this.executionState, calledBy, aRoot, a);
    effect.applyRoutine();
  }
}
