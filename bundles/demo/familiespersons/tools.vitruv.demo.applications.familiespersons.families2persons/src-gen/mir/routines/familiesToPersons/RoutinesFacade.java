package mir.routines.familiesToPersons;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createPersonRegister(final FamilyRegister familyRegister) {
    mir.routines.familiesToPersons.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.familiesToPersons.CreatePersonRegisterRoutine routine = new mir.routines.familiesToPersons.CreatePersonRegisterRoutine(_routinesFacade, _reactionExecutionState, _caller, familyRegister);
    return routine.applyRoutine();
  }
  
  public boolean deletePersonRegister(final FamilyRegister familyRegister) {
    mir.routines.familiesToPersons.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.familiesToPersons.DeletePersonRegisterRoutine routine = new mir.routines.familiesToPersons.DeletePersonRegisterRoutine(_routinesFacade, _reactionExecutionState, _caller, familyRegister);
    return routine.applyRoutine();
  }
  
  public boolean createFather(final Member member) {
    mir.routines.familiesToPersons.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.familiesToPersons.CreateFatherRoutine routine = new mir.routines.familiesToPersons.CreateFatherRoutine(_routinesFacade, _reactionExecutionState, _caller, member);
    return routine.applyRoutine();
  }
  
  public boolean createSon(final Member member) {
    mir.routines.familiesToPersons.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.familiesToPersons.CreateSonRoutine routine = new mir.routines.familiesToPersons.CreateSonRoutine(_routinesFacade, _reactionExecutionState, _caller, member);
    return routine.applyRoutine();
  }
  
  public boolean createMother(final Member member) {
    mir.routines.familiesToPersons.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.familiesToPersons.CreateMotherRoutine routine = new mir.routines.familiesToPersons.CreateMotherRoutine(_routinesFacade, _reactionExecutionState, _caller, member);
    return routine.applyRoutine();
  }
  
  public boolean createDaughter(final Member member) {
    mir.routines.familiesToPersons.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.familiesToPersons.CreateDaughterRoutine routine = new mir.routines.familiesToPersons.CreateDaughterRoutine(_routinesFacade, _reactionExecutionState, _caller, member);
    return routine.applyRoutine();
  }
  
  public boolean deletePerson(final Member member) {
    mir.routines.familiesToPersons.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.familiesToPersons.DeletePersonRoutine routine = new mir.routines.familiesToPersons.DeletePersonRoutine(_routinesFacade, _reactionExecutionState, _caller, member);
    return routine.applyRoutine();
  }
  
  public boolean changeFullNameFromFirst(final Member member) {
    mir.routines.familiesToPersons.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.familiesToPersons.ChangeFullNameFromFirstRoutine routine = new mir.routines.familiesToPersons.ChangeFullNameFromFirstRoutine(_routinesFacade, _reactionExecutionState, _caller, member);
    return routine.applyRoutine();
  }
  
  public boolean changeFullNameFromLast(final Family family) {
    mir.routines.familiesToPersons.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.familiesToPersons.ChangeFullNameFromLastRoutine routine = new mir.routines.familiesToPersons.ChangeFullNameFromLastRoutine(_routinesFacade, _reactionExecutionState, _caller, family);
    return routine.applyRoutine();
  }
}
