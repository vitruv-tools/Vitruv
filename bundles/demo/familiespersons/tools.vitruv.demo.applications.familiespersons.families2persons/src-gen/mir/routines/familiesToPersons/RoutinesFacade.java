package mir.routines.familiesToPersons;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import mir.routines.familiesToPersons.ChangeFullNameFromFirstRoutine;
import mir.routines.familiesToPersons.ChangeFullNameFromLastRoutine;
import mir.routines.familiesToPersons.CreateDaughterRoutine;
import mir.routines.familiesToPersons.CreateFatherRoutine;
import mir.routines.familiesToPersons.CreateMotherRoutine;
import mir.routines.familiesToPersons.CreatePersonRegisterRoutine;
import mir.routines.familiesToPersons.CreateSonRoutine;
import mir.routines.familiesToPersons.DeletePersonRegisterRoutine;
import mir.routines.familiesToPersons.DeletePersonRoutine;
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
  
  public boolean createPersonRegister(final FamilyRegister familyRegister) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatePersonRegisterRoutine routine = new CreatePersonRegisterRoutine(_routinesFacade, _reactionExecutionState, _caller, familyRegister);
    return routine.applyRoutine();
  }
  
  public boolean deletePersonRegister(final FamilyRegister familyRegister) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeletePersonRegisterRoutine routine = new DeletePersonRegisterRoutine(_routinesFacade, _reactionExecutionState, _caller, familyRegister);
    return routine.applyRoutine();
  }
  
  public boolean createFather(final Member member) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateFatherRoutine routine = new CreateFatherRoutine(_routinesFacade, _reactionExecutionState, _caller, member);
    return routine.applyRoutine();
  }
  
  public boolean createSon(final Member member) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateSonRoutine routine = new CreateSonRoutine(_routinesFacade, _reactionExecutionState, _caller, member);
    return routine.applyRoutine();
  }
  
  public boolean createMother(final Member member) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateMotherRoutine routine = new CreateMotherRoutine(_routinesFacade, _reactionExecutionState, _caller, member);
    return routine.applyRoutine();
  }
  
  public boolean createDaughter(final Member member) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateDaughterRoutine routine = new CreateDaughterRoutine(_routinesFacade, _reactionExecutionState, _caller, member);
    return routine.applyRoutine();
  }
  
  public boolean deletePerson(final Member member) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeletePersonRoutine routine = new DeletePersonRoutine(_routinesFacade, _reactionExecutionState, _caller, member);
    return routine.applyRoutine();
  }
  
  public boolean changeFullNameFromFirst(final Member member) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeFullNameFromFirstRoutine routine = new ChangeFullNameFromFirstRoutine(_routinesFacade, _reactionExecutionState, _caller, member);
    return routine.applyRoutine();
  }
  
  public boolean changeFullNameFromLast(final Family family) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeFullNameFromLastRoutine routine = new ChangeFullNameFromLastRoutine(_routinesFacade, _reactionExecutionState, _caller, family);
    return routine.applyRoutine();
  }
}
