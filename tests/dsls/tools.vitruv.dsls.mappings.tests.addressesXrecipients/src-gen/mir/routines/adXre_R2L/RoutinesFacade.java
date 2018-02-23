package mir.routines.adXre_R2L;

import edu.kit.ipd.sdq.metamodels.addresses.Address;
import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.City;
import edu.kit.ipd.sdq.metamodels.recipients.Location;
import edu.kit.ipd.sdq.metamodels.recipients.Recipient;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean ensureAllMappings() {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.EnsureAllMappingsRoutine routine = new mir.routines.adXre_R2L.EnsureAllMappingsRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean ensureAdRootXReRootMapping() {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.EnsureAdRootXReRootMappingRoutine routine = new mir.routines.adXre_R2L.EnsureAdRootXReRootMappingRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean deleteAdRootXReRootMappingInstances() {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.DeleteAdRootXReRootMappingInstancesRoutine routine = new mir.routines.adXre_R2L.DeleteAdRootXReRootMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean deleteAdRootXReRootMappingInstance(final Recipients rRoot) {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.DeleteAdRootXReRootMappingInstanceRoutine routine = new mir.routines.adXre_R2L.DeleteAdRootXReRootMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot);
    return routine.applyRoutine();
  }
  
  public boolean createAdRootXReRootMappingInstances() {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.CreateAdRootXReRootMappingInstancesRoutine routine = new mir.routines.adXre_R2L.CreateAdRootXReRootMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean createAdRootXReRootMappingInstance(final Recipients rRoot) {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.CreateAdRootXReRootMappingInstanceRoutine routine = new mir.routines.adXre_R2L.CreateAdRootXReRootMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot);
    return routine.applyRoutine();
  }
  
  public boolean enforceLeftAdRootXReRootMappingConditions(final Addresses aRoot) {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.EnforceLeftAdRootXReRootMappingConditionsRoutine routine = new mir.routines.adXre_R2L.EnforceLeftAdRootXReRootMappingConditionsRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot);
    return routine.applyRoutine();
  }
  
  public boolean enforceAdRootXReRootMappingConditionsFromRightToLeft(final Addresses aRoot, final Recipients rRoot) {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.EnforceAdRootXReRootMappingConditionsFromRightToLeftRoutine routine = new mir.routines.adXre_R2L.EnforceAdRootXReRootMappingConditionsFromRightToLeftRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, rRoot);
    return routine.applyRoutine();
  }
  
  public boolean updateAdRootXReRootMappingInstances() {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.UpdateAdRootXReRootMappingInstancesRoutine routine = new mir.routines.adXre_R2L.UpdateAdRootXReRootMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean updateAdRootXReRootMappingInstance(final Recipients rRoot) {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.UpdateAdRootXReRootMappingInstanceRoutine routine = new mir.routines.adXre_R2L.UpdateAdRootXReRootMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot);
    return routine.applyRoutine();
  }
  
  public boolean ensureAddressXRecipientLocationCityMapping() {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.EnsureAddressXRecipientLocationCityMappingRoutine routine = new mir.routines.adXre_R2L.EnsureAddressXRecipientLocationCityMappingRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean deleteAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.DeleteAddressXRecipientLocationCityMappingInstancesRoutine routine = new mir.routines.adXre_R2L.DeleteAddressXRecipientLocationCityMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean deleteAddressXRecipientLocationCityMappingInstance(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.DeleteAddressXRecipientLocationCityMappingInstanceRoutine routine = new mir.routines.adXre_R2L.DeleteAddressXRecipientLocationCityMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot, r, l, c);
    return routine.applyRoutine();
  }
  
  public boolean createAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.CreateAddressXRecipientLocationCityMappingInstancesRoutine routine = new mir.routines.adXre_R2L.CreateAddressXRecipientLocationCityMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean createAddressXRecipientLocationCityMappingInstance(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.CreateAddressXRecipientLocationCityMappingInstanceRoutine routine = new mir.routines.adXre_R2L.CreateAddressXRecipientLocationCityMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot, r, l, c);
    return routine.applyRoutine();
  }
  
  public boolean enforceLeftAddressXRecipientLocationCityMappingConditions(final Addresses aRoot, final Address a) {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.EnforceLeftAddressXRecipientLocationCityMappingConditionsRoutine routine = new mir.routines.adXre_R2L.EnforceLeftAddressXRecipientLocationCityMappingConditionsRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, a);
    return routine.applyRoutine();
  }
  
  public boolean enforceAddressXRecipientLocationCityMappingConditionsFromRightToLeft(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.EnforceAddressXRecipientLocationCityMappingConditionsFromRightToLeftRoutine routine = new mir.routines.adXre_R2L.EnforceAddressXRecipientLocationCityMappingConditionsFromRightToLeftRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, rRoot, a, r, l, c);
    return routine.applyRoutine();
  }
  
  public boolean updateAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.UpdateAddressXRecipientLocationCityMappingInstancesRoutine routine = new mir.routines.adXre_R2L.UpdateAddressXRecipientLocationCityMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean updateAddressXRecipientLocationCityMappingInstance(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_R2L.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_R2L.UpdateAddressXRecipientLocationCityMappingInstanceRoutine routine = new mir.routines.adXre_R2L.UpdateAddressXRecipientLocationCityMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot, r, l, c);
    return routine.applyRoutine();
  }
}
