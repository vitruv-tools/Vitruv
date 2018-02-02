package mir.routines.personsToFamilies;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.persons.Female;
import edu.kit.ipd.sdq.metamodels.persons.Male;
import edu.kit.ipd.sdq.metamodels.persons.Person;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createFamilyRegister(final PersonRegister personRegister) {
    mir.routines.personsToFamilies.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.personsToFamilies.CreateFamilyRegisterRoutine routine = new mir.routines.personsToFamilies.CreateFamilyRegisterRoutine(_routinesFacade, _reactionExecutionState, _caller, personRegister);
    return routine.applyRoutine();
  }
  
  public boolean deleteFamilyRegister(final PersonRegister personsRegister) {
    mir.routines.personsToFamilies.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.personsToFamilies.DeleteFamilyRegisterRoutine routine = new mir.routines.personsToFamilies.DeleteFamilyRegisterRoutine(_routinesFacade, _reactionExecutionState, _caller, personsRegister);
    return routine.applyRoutine();
  }
  
  public boolean createMaleMemberOfFamily(final Male person) {
    mir.routines.personsToFamilies.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.personsToFamilies.CreateMaleMemberOfFamilyRoutine routine = new mir.routines.personsToFamilies.CreateMaleMemberOfFamilyRoutine(_routinesFacade, _reactionExecutionState, _caller, person);
    return routine.applyRoutine();
  }
  
  public boolean addCorr(final Person person, final Family family) {
    mir.routines.personsToFamilies.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.personsToFamilies.AddCorrRoutine routine = new mir.routines.personsToFamilies.AddCorrRoutine(_routinesFacade, _reactionExecutionState, _caller, person, family);
    return routine.applyRoutine();
  }
  
  public boolean createFemaleMemberOfFamily(final Female person) {
    mir.routines.personsToFamilies.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.personsToFamilies.CreateFemaleMemberOfFamilyRoutine routine = new mir.routines.personsToFamilies.CreateFemaleMemberOfFamilyRoutine(_routinesFacade, _reactionExecutionState, _caller, person);
    return routine.applyRoutine();
  }
  
  public boolean changeNames(final Person person) {
    mir.routines.personsToFamilies.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.personsToFamilies.ChangeNamesRoutine routine = new mir.routines.personsToFamilies.ChangeNamesRoutine(_routinesFacade, _reactionExecutionState, _caller, person);
    return routine.applyRoutine();
  }
  
  public boolean deleteMember(final Person person) {
    mir.routines.personsToFamilies.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.personsToFamilies.DeleteMemberRoutine routine = new mir.routines.personsToFamilies.DeleteMemberRoutine(_routinesFacade, _reactionExecutionState, _caller, person);
    return routine.applyRoutine();
  }
}
