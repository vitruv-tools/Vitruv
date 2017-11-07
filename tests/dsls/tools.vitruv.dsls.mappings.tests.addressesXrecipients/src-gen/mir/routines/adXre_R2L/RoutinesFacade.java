package mir.routines.adXre_R2L;

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
    mir.routines.adXre_R2L.EnsureAllMappingsRoutine effect = new mir.routines.adXre_R2L.EnsureAllMappingsRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean ensureAdRootXReRootMapping() {
    mir.routines.adXre_R2L.EnsureAdRootXReRootMappingRoutine effect = new mir.routines.adXre_R2L.EnsureAdRootXReRootMappingRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean deleteAdRootXReRootMappingInstances() {
    mir.routines.adXre_R2L.DeleteAdRootXReRootMappingInstancesRoutine effect = new mir.routines.adXre_R2L.DeleteAdRootXReRootMappingInstancesRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean deleteAdRootXReRootMappingInstance(final Recipients rRoot) {
    mir.routines.adXre_R2L.DeleteAdRootXReRootMappingInstanceRoutine effect = new mir.routines.adXre_R2L.DeleteAdRootXReRootMappingInstanceRoutine(this.executionState, calledBy, rRoot);
    return effect.applyRoutine();
  }
  
  public boolean createAdRootXReRootMappingInstances() {
    mir.routines.adXre_R2L.CreateAdRootXReRootMappingInstancesRoutine effect = new mir.routines.adXre_R2L.CreateAdRootXReRootMappingInstancesRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean createAdRootXReRootMappingInstance(final Recipients rRoot) {
    mir.routines.adXre_R2L.CreateAdRootXReRootMappingInstanceRoutine effect = new mir.routines.adXre_R2L.CreateAdRootXReRootMappingInstanceRoutine(this.executionState, calledBy, rRoot);
    return effect.applyRoutine();
  }
  
  public boolean enforceLeftAdRootXReRootMappingConditions(final Addresses aRoot) {
    mir.routines.adXre_R2L.EnforceLeftAdRootXReRootMappingConditionsRoutine effect = new mir.routines.adXre_R2L.EnforceLeftAdRootXReRootMappingConditionsRoutine(this.executionState, calledBy, aRoot);
    return effect.applyRoutine();
  }
  
  public boolean enforceAdRootXReRootMappingConditionsFromRightToLeft(final Addresses aRoot, final Recipients rRoot) {
    mir.routines.adXre_R2L.EnforceAdRootXReRootMappingConditionsFromRightToLeftRoutine effect = new mir.routines.adXre_R2L.EnforceAdRootXReRootMappingConditionsFromRightToLeftRoutine(this.executionState, calledBy, aRoot, rRoot);
    return effect.applyRoutine();
  }
  
  public boolean updateAdRootXReRootMappingInstances() {
    mir.routines.adXre_R2L.UpdateAdRootXReRootMappingInstancesRoutine effect = new mir.routines.adXre_R2L.UpdateAdRootXReRootMappingInstancesRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean updateAdRootXReRootMappingInstance(final Recipients rRoot) {
    mir.routines.adXre_R2L.UpdateAdRootXReRootMappingInstanceRoutine effect = new mir.routines.adXre_R2L.UpdateAdRootXReRootMappingInstanceRoutine(this.executionState, calledBy, rRoot);
    return effect.applyRoutine();
  }
  
  public boolean ensureAddressXRecipientLocationCityMapping() {
    mir.routines.adXre_R2L.EnsureAddressXRecipientLocationCityMappingRoutine effect = new mir.routines.adXre_R2L.EnsureAddressXRecipientLocationCityMappingRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean deleteAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_R2L.DeleteAddressXRecipientLocationCityMappingInstancesRoutine effect = new mir.routines.adXre_R2L.DeleteAddressXRecipientLocationCityMappingInstancesRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean deleteAddressXRecipientLocationCityMappingInstance(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_R2L.DeleteAddressXRecipientLocationCityMappingInstanceRoutine effect = new mir.routines.adXre_R2L.DeleteAddressXRecipientLocationCityMappingInstanceRoutine(this.executionState, calledBy, rRoot, r, l, c);
    return effect.applyRoutine();
  }
  
  public boolean createAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_R2L.CreateAddressXRecipientLocationCityMappingInstancesRoutine effect = new mir.routines.adXre_R2L.CreateAddressXRecipientLocationCityMappingInstancesRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean createAddressXRecipientLocationCityMappingInstance(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_R2L.CreateAddressXRecipientLocationCityMappingInstanceRoutine effect = new mir.routines.adXre_R2L.CreateAddressXRecipientLocationCityMappingInstanceRoutine(this.executionState, calledBy, rRoot, r, l, c);
    return effect.applyRoutine();
  }
  
  public boolean enforceLeftAddressXRecipientLocationCityMappingConditions(final Addresses aRoot, final Address a) {
    mir.routines.adXre_R2L.EnforceLeftAddressXRecipientLocationCityMappingConditionsRoutine effect = new mir.routines.adXre_R2L.EnforceLeftAddressXRecipientLocationCityMappingConditionsRoutine(this.executionState, calledBy, aRoot, a);
    return effect.applyRoutine();
  }
  
  public boolean enforceAddressXRecipientLocationCityMappingConditionsFromRightToLeft(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_R2L.EnforceAddressXRecipientLocationCityMappingConditionsFromRightToLeftRoutine effect = new mir.routines.adXre_R2L.EnforceAddressXRecipientLocationCityMappingConditionsFromRightToLeftRoutine(this.executionState, calledBy, aRoot, rRoot, a, r, l, c);
    return effect.applyRoutine();
  }
  
  public boolean updateAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_R2L.UpdateAddressXRecipientLocationCityMappingInstancesRoutine effect = new mir.routines.adXre_R2L.UpdateAddressXRecipientLocationCityMappingInstancesRoutine(this.executionState, calledBy);
    return effect.applyRoutine();
  }
  
  public boolean updateAddressXRecipientLocationCityMappingInstance(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_R2L.UpdateAddressXRecipientLocationCityMappingInstanceRoutine effect = new mir.routines.adXre_R2L.UpdateAddressXRecipientLocationCityMappingInstanceRoutine(this.executionState, calledBy, rRoot, r, l, c);
    return effect.applyRoutine();
  }
}
