package mir.routines.adXre_L2R;

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
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.EnsureAllMappingsRoutine routine = new mir.routines.adXre_L2R.EnsureAllMappingsRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean ensureAdRootXReRootMapping() {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.EnsureAdRootXReRootMappingRoutine routine = new mir.routines.adXre_L2R.EnsureAdRootXReRootMappingRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean deleteAdRootXReRootMappingInstances() {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstancesRoutine routine = new mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean deleteAdRootXReRootMappingInstance(final Addresses aRoot) {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstanceRoutine routine = new mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot);
    return routine.applyRoutine();
  }
  
  public boolean createAdRootXReRootMappingInstances() {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstancesRoutine routine = new mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean createAdRootXReRootMappingInstance(final Addresses aRoot) {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstanceRoutine routine = new mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot);
    return routine.applyRoutine();
  }
  
  public boolean enforceRightAdRootXReRootMappingConditions(final Recipients rRoot) {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.EnforceRightAdRootXReRootMappingConditionsRoutine routine = new mir.routines.adXre_L2R.EnforceRightAdRootXReRootMappingConditionsRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot);
    return routine.applyRoutine();
  }
  
  public boolean enforceAdRootXReRootMappingConditionsFromLeftToRight(final Addresses aRoot, final Recipients rRoot) {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine routine = new mir.routines.adXre_L2R.EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, rRoot);
    return routine.applyRoutine();
  }
  
  public boolean updateAdRootXReRootMappingInstances() {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstancesRoutine routine = new mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean updateAdRootXReRootMappingInstance(final Addresses aRoot) {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstanceRoutine routine = new mir.routines.adXre_L2R.UpdateAdRootXReRootMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot);
    return routine.applyRoutine();
  }
  
  public boolean ensureAddressXRecipientLocationCityMapping() {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.EnsureAddressXRecipientLocationCityMappingRoutine routine = new mir.routines.adXre_L2R.EnsureAddressXRecipientLocationCityMappingRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean deleteAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstancesRoutine routine = new mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean deleteAddressXRecipientLocationCityMappingInstance(final Addresses aRoot, final Address a) {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstanceRoutine routine = new mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, a);
    return routine.applyRoutine();
  }
  
  public boolean createAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstancesRoutine routine = new mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean createAddressXRecipientLocationCityMappingInstance(final Addresses aRoot, final Address a) {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstanceRoutine routine = new mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, a);
    return routine.applyRoutine();
  }
  
  public boolean enforceRightAddressXRecipientLocationCityMappingConditions(final Recipients rRoot, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine routine = new mir.routines.adXre_L2R.EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot, r, l, c);
    return routine.applyRoutine();
  }
  
  public boolean enforceAddressXRecipientLocationCityMappingConditionsFromLeftToRight(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine routine = new mir.routines.adXre_L2R.EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, rRoot, a, r, l, c);
    return routine.applyRoutine();
  }
  
  public boolean updateAddressXRecipientLocationCityMappingInstances() {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstancesRoutine routine = new mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstancesRoutine(_routinesFacade, _reactionExecutionState, _caller);
    return routine.applyRoutine();
  }
  
  public boolean updateAddressXRecipientLocationCityMappingInstance(final Addresses aRoot, final Address a) {
    mir.routines.adXre_L2R.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstanceRoutine routine = new mir.routines.adXre_L2R.UpdateAddressXRecipientLocationCityMappingInstanceRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot, a);
    return routine.applyRoutine();
  }
}
