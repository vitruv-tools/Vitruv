package mir.routines.adXre_L2R;

import edu.kit.ipd.sdq.metamodels.addresses.Address;
import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.City;
import edu.kit.ipd.sdq.metamodels.recipients.Location;
import edu.kit.ipd.sdq.metamodels.recipients.Recipient;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public boolean ensureAllMappings() {
    mir.routines.adXre_L2R.EnsureAllMappingsRoutine effect = new mir.routines.adXre_L2R.EnsureAllMappingsRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean ensureAdRootXReRootMapping() {
    mir.routines.adXre_L2R.EnsureAdRootXReRootMappingRoutine effect = new mir.routines.adXre_L2R.EnsureAdRootXReRootMappingRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean deleteAdRootXReRootMappingInstances() {
    mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstancesRoutine effect = new mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstancesRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean deleteAdRootXReRootMappingInstance(final Addresses aRoot) {
    mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstanceRoutine effect = new mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstanceRoutine(this.executionState, calledBy, aRoot);
    return effect.applyRoutine();
  }
  
  public boolean createAdRootXReRootMappingInstances() {
    mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstancesRoutine effect = new mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstancesRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean createAdRootXReRootMappingInstance(final Addresses aRoot) {
    mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstanceRoutine effect = new mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstanceRoutine(this.executionState, calledBy, aRoot);
    return effect.applyRoutine();
  }
  
  public boolean enforceRightAdRootXReRootMappingConditions(final Recipients rRoot) {
    mir.routines.adXre_L2R.EnforceRightAdRootXReRootMappingConditionsRoutine effect = new mir.routines.adXre_L2R.EnforceRightAdRootXReRootMappingConditionsRoutine(this.executionState, calledBy, rRoot);
    return effect.applyRoutine();
  }
  
  public boolean enforceAdRootXReRootMappingConditionsFromLeftToRight(final Addresses aRoot, final Recipients rRoot) {
    mir.routines.adXre_L2R.EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine effect = new mir.routines.adXre_L2R.EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine(this.executionState, calledBy, aRoot, rRoot);
    return effect.applyRoutine();
  }
  
  public boolean updateAdRootXReRootMappingInstances() {
    mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstancesRoutine effect = new mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstancesRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean updateAdRootXReRootMappingInstance(final Addresses aRoot) {
    mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstanceRoutine effect = new mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstanceRoutine(this.executionState, calledBy, aRoot);
    return effect.applyRoutine();
  }
  
  public boolean ensureAddressXRecipientLocationCityMapping() {
    mir.routines.adXre_L2R.EnsureAddressXRecipientLocationCityMappingRoutine effect = new mir.routines.adXre_L2R.EnsureAddressXRecipientLocationCityMappingRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean deleteAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstancesRoutine effect = new mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstancesRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean deleteAddressXRecipientLocationCityMappingInstance(final Addresses aRoot, final Address a) {
    mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstanceRoutine effect = new mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstanceRoutine(this.executionState, calledBy, aRoot, a);
    return effect.applyRoutine();
  }
  
  public boolean createAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstancesRoutine effect = new mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstancesRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean createAddressXRecipientLocationCityMappingInstance(final Addresses aRoot, final Address a) {
    mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstanceRoutine effect = new mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstanceRoutine(this.executionState, calledBy, aRoot, a);
    return effect.applyRoutine();
  }
  
  public boolean enforceRightAddressXRecipientLocationCityMappingConditions(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_L2R.EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine effect = new mir.routines.adXre_L2R.EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine(this.executionState, calledBy, rRoot, r, l, c);
    return effect.applyRoutine();
  }
  
  public boolean enforceAddressXRecipientLocationCityMappingConditionsFromLeftToRight(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_L2R.EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine effect = new mir.routines.adXre_L2R.EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine(this.executionState, calledBy, aRoot, rRoot, a, r, l, c);
    return effect.applyRoutine();
  }
  
  public boolean updateAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstancesRoutine effect = new mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstancesRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean updateAddressXRecipientLocationCityMappingInstance(final Addresses aRoot, final Address a) {
    mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstanceRoutine effect = new mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstanceRoutine(this.executionState, calledBy, aRoot, a);
    return effect.applyRoutine();
  }
}
