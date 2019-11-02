package mir.routines.adXre_L2R;

import edu.kit.ipd.sdq.metamodels.addresses.Address;
import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.City;
import edu.kit.ipd.sdq.metamodels.recipients.Location;
import edu.kit.ipd.sdq.metamodels.recipients.Recipient;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstanceRoutine;
import mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstancesRoutine;
import mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstanceRoutine;
import mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstancesRoutine;
import mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstanceRoutine;
import mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstancesRoutine;
import mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstanceRoutine;
import mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstancesRoutine;
import mir.routines.adXre_L2R.EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine;
import mir.routines.adXre_L2R.EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine;
import mir.routines.adXre_L2R.EnforceRightAdRootXReRootMappingConditionsRoutine;
import mir.routines.adXre_L2R.EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine;
import mir.routines.adXre_L2R.EnsureAdRootXReRootMappingRoutine;
import mir.routines.adXre_L2R.EnsureAddressXRecipientLocationCityMappingRoutine;
import mir.routines.adXre_L2R.EnsureAllMappingsRoutine;
import mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstanceRoutine;
import mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstancesRoutine;
import mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstanceRoutine;
import mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstancesRoutine;
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
  
  public boolean deleteAdRootXReRootMappingInstance(final Addresses aRoot) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteAdRootXReRootMappingInstanceRoutine routine = new DeleteAdRootXReRootMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot);
    return routine.applyRoutine();
  }
  
  public boolean createAdRootXReRootMappingInstances() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateAdRootXReRootMappingInstancesRoutine routine = new CreateAdRootXReRootMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean createAdRootXReRootMappingInstance(final Addresses aRoot) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateAdRootXReRootMappingInstanceRoutine routine = new CreateAdRootXReRootMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot);
    return routine.applyRoutine();
  }
  
  public boolean enforceRightAdRootXReRootMappingConditions(final Recipients rRoot) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnforceRightAdRootXReRootMappingConditionsRoutine routine = new EnforceRightAdRootXReRootMappingConditionsRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot);
    return routine.applyRoutine();
  }
  
  public boolean enforceAdRootXReRootMappingConditionsFromLeftToRight(final Addresses aRoot, final Recipients rRoot) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine routine = new EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, rRoot);
    return routine.applyRoutine();
  }
  
  public boolean updateAdRootXReRootMappingInstances() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateAdRootXReRootMappingInstancesRoutine routine = new UpdateAdRootXReRootMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean updateAdRootXReRootMappingInstance(final Addresses aRoot) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateAdRootXReRootMappingInstanceRoutine routine = new UpdateAdRootXReRootMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot);
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
  
  public boolean deleteAddressXRecipientLocationCityMappingInstance(final Addresses aRoot, final Address a) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteAddressXRecipientLocationCityMappingInstanceRoutine routine = new DeleteAddressXRecipientLocationCityMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, a);
    return routine.applyRoutine();
  }
  
  public boolean createAddressXRecipientLocationCityMappingInstances() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateAddressXRecipientLocationCityMappingInstancesRoutine routine = new CreateAddressXRecipientLocationCityMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean createAddressXRecipientLocationCityMappingInstance(final Addresses aRoot, final Address a) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateAddressXRecipientLocationCityMappingInstanceRoutine routine = new CreateAddressXRecipientLocationCityMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, a);
    return routine.applyRoutine();
  }
  
  public boolean enforceRightAddressXRecipientLocationCityMappingConditions(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine routine = new EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot, r, l, c);
    return routine.applyRoutine();
  }
  
  public boolean enforceAddressXRecipientLocationCityMappingConditionsFromLeftToRight(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine routine = new EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, rRoot, a, r, l, c);
    return routine.applyRoutine();
  }
  
  public boolean updateAddressXRecipientLocationCityMappingInstances() {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateAddressXRecipientLocationCityMappingInstancesRoutine routine = new UpdateAddressXRecipientLocationCityMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean updateAddressXRecipientLocationCityMappingInstance(final Addresses aRoot, final Address a) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateAddressXRecipientLocationCityMappingInstanceRoutine routine = new UpdateAddressXRecipientLocationCityMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, a);
    return routine.applyRoutine();
  }
}
