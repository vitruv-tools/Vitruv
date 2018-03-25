package mir.routines.adXre_R2L;

import edu.kit.ipd.sdq.metamodels.addresses.Address;
import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.City;
import edu.kit.ipd.sdq.metamodels.recipients.Location;
import edu.kit.ipd.sdq.metamodels.recipients.Recipient;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import mir.routines.adXre_R2L.CreateAdRootXReRootMappingInstanceRoutine;
import mir.routines.adXre_R2L.CreateAdRootXReRootMappingInstancesRoutine;
import mir.routines.adXre_R2L.CreateAddressXRecipientLocationCityMappingInstanceRoutine;
import mir.routines.adXre_R2L.CreateAddressXRecipientLocationCityMappingInstancesRoutine;
import mir.routines.adXre_R2L.DeleteAdRootXReRootMappingInstanceRoutine;
import mir.routines.adXre_R2L.DeleteAdRootXReRootMappingInstancesRoutine;
import mir.routines.adXre_R2L.DeleteAddressXRecipientLocationCityMappingInstanceRoutine;
import mir.routines.adXre_R2L.DeleteAddressXRecipientLocationCityMappingInstancesRoutine;
import mir.routines.adXre_R2L.EnforceAdRootXReRootMappingConditionsFromRightToLeftRoutine;
import mir.routines.adXre_R2L.EnforceAddressXRecipientLocationCityMappingConditionsFromRightToLeftRoutine;
import mir.routines.adXre_R2L.EnforceLeftAdRootXReRootMappingConditionsRoutine;
import mir.routines.adXre_R2L.EnforceLeftAddressXRecipientLocationCityMappingConditionsRoutine;
import mir.routines.adXre_R2L.EnsureAdRootXReRootMappingRoutine;
import mir.routines.adXre_R2L.EnsureAddressXRecipientLocationCityMappingRoutine;
import mir.routines.adXre_R2L.EnsureAllMappingsRoutine;
import mir.routines.adXre_R2L.UpdateAdRootXReRootMappingInstanceRoutine;
import mir.routines.adXre_R2L.UpdateAdRootXReRootMappingInstancesRoutine;
import mir.routines.adXre_R2L.UpdateAddressXRecipientLocationCityMappingInstanceRoutine;
import mir.routines.adXre_R2L.UpdateAddressXRecipientLocationCityMappingInstancesRoutine;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean ensureAllMappings() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnsureAllMappingsRoutine routine = new EnsureAllMappingsRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean ensureAdRootXReRootMapping() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnsureAdRootXReRootMappingRoutine routine = new EnsureAdRootXReRootMappingRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean deleteAdRootXReRootMappingInstances() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteAdRootXReRootMappingInstancesRoutine routine = new DeleteAdRootXReRootMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean deleteAdRootXReRootMappingInstance(final Recipients rRoot) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteAdRootXReRootMappingInstanceRoutine routine = new DeleteAdRootXReRootMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot);
    return routine.applyRoutine();
  }
  
  public boolean createAdRootXReRootMappingInstances() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateAdRootXReRootMappingInstancesRoutine routine = new CreateAdRootXReRootMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean createAdRootXReRootMappingInstance(final Recipients rRoot) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateAdRootXReRootMappingInstanceRoutine routine = new CreateAdRootXReRootMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot);
    return routine.applyRoutine();
  }
  
  public boolean enforceLeftAdRootXReRootMappingConditions(final Addresses aRoot) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnforceLeftAdRootXReRootMappingConditionsRoutine routine = new EnforceLeftAdRootXReRootMappingConditionsRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot);
    return routine.applyRoutine();
  }
  
  public boolean enforceAdRootXReRootMappingConditionsFromRightToLeft(final Addresses aRoot, final Recipients rRoot) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnforceAdRootXReRootMappingConditionsFromRightToLeftRoutine routine = new EnforceAdRootXReRootMappingConditionsFromRightToLeftRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, rRoot);
    return routine.applyRoutine();
  }
  
  public boolean updateAdRootXReRootMappingInstances() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateAdRootXReRootMappingInstancesRoutine routine = new UpdateAdRootXReRootMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean updateAdRootXReRootMappingInstance(final Recipients rRoot) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateAdRootXReRootMappingInstanceRoutine routine = new UpdateAdRootXReRootMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot);
    return routine.applyRoutine();
  }
  
  public boolean ensureAddressXRecipientLocationCityMapping() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnsureAddressXRecipientLocationCityMappingRoutine routine = new EnsureAddressXRecipientLocationCityMappingRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean deleteAddressXRecipientLocationCityMappingInstances() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteAddressXRecipientLocationCityMappingInstancesRoutine routine = new DeleteAddressXRecipientLocationCityMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean deleteAddressXRecipientLocationCityMappingInstance(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteAddressXRecipientLocationCityMappingInstanceRoutine routine = new DeleteAddressXRecipientLocationCityMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot, r, l, c);
    return routine.applyRoutine();
  }
  
  public boolean createAddressXRecipientLocationCityMappingInstances() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateAddressXRecipientLocationCityMappingInstancesRoutine routine = new CreateAddressXRecipientLocationCityMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean createAddressXRecipientLocationCityMappingInstance(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateAddressXRecipientLocationCityMappingInstanceRoutine routine = new CreateAddressXRecipientLocationCityMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot, r, l, c);
    return routine.applyRoutine();
  }
  
  public boolean enforceLeftAddressXRecipientLocationCityMappingConditions(final Addresses aRoot, final Address a) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnforceLeftAddressXRecipientLocationCityMappingConditionsRoutine routine = new EnforceLeftAddressXRecipientLocationCityMappingConditionsRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, a);
    return routine.applyRoutine();
  }
  
  public boolean enforceAddressXRecipientLocationCityMappingConditionsFromRightToLeft(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnforceAddressXRecipientLocationCityMappingConditionsFromRightToLeftRoutine routine = new EnforceAddressXRecipientLocationCityMappingConditionsFromRightToLeftRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, rRoot, a, r, l, c);
    return routine.applyRoutine();
  }
  
  public boolean updateAddressXRecipientLocationCityMappingInstances() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateAddressXRecipientLocationCityMappingInstancesRoutine routine = new UpdateAddressXRecipientLocationCityMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean updateAddressXRecipientLocationCityMappingInstance(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateAddressXRecipientLocationCityMappingInstanceRoutine routine = new UpdateAddressXRecipientLocationCityMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot, r, l, c);
    return routine.applyRoutine();
  }
}
