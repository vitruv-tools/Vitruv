package mir.routines.personsToFamilies;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.persons.Female;
import edu.kit.ipd.sdq.metamodels.persons.Male;
import edu.kit.ipd.sdq.metamodels.persons.Person;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import mir.routines.personsToFamilies.AddCorrRoutine;
import mir.routines.personsToFamilies.ChangeNamesRoutine;
import mir.routines.personsToFamilies.CreateFamilyRegisterRoutine;
import mir.routines.personsToFamilies.CreateFemaleMemberOfFamilyRoutine;
import mir.routines.personsToFamilies.CreateMaleMemberOfFamilyRoutine;
import mir.routines.personsToFamilies.DeleteFamilyRegisterRoutine;
import mir.routines.personsToFamilies.DeleteMemberRoutine;
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
  
  public boolean createFamilyRegister(final PersonRegister personRegister) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateFamilyRegisterRoutine routine = new CreateFamilyRegisterRoutine(_routinesFacade, _reactionExecutionState, _caller, personRegister);
    return routine.applyRoutine();
  }
  
  public boolean deleteFamilyRegister(final PersonRegister personsRegister) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteFamilyRegisterRoutine routine = new DeleteFamilyRegisterRoutine(_routinesFacade, _reactionExecutionState, _caller, personsRegister);
    return routine.applyRoutine();
  }
  
  public boolean createMaleMemberOfFamily(final Male person) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateMaleMemberOfFamilyRoutine routine = new CreateMaleMemberOfFamilyRoutine(_routinesFacade, _reactionExecutionState, _caller, person);
    return routine.applyRoutine();
  }
  
  public boolean addCorr(final Person person, final Family family) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrRoutine routine = new AddCorrRoutine(_routinesFacade, _reactionExecutionState, _caller, person, family);
    return routine.applyRoutine();
  }
  
  public boolean createFemaleMemberOfFamily(final Female person) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateFemaleMemberOfFamilyRoutine routine = new CreateFemaleMemberOfFamilyRoutine(_routinesFacade, _reactionExecutionState, _caller, person);
    return routine.applyRoutine();
  }
  
  public boolean changeNames(final Person person) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNamesRoutine routine = new ChangeNamesRoutine(_routinesFacade, _reactionExecutionState, _caller, person);
    return routine.applyRoutine();
  }
  
  public boolean deleteMember(final Person person) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteMemberRoutine routine = new DeleteMemberRoutine(_routinesFacade, _reactionExecutionState, _caller, person);
    return routine.applyRoutine();
  }
}
