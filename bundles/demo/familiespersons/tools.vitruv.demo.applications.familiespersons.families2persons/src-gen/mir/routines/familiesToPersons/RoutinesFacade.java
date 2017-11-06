package mir.routines.familiesToPersons;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public boolean createPersonRegister(final FamilyRegister familyRegister) {
    mir.routines.familiesToPersons.CreatePersonRegisterRoutine effect = new mir.routines.familiesToPersons.CreatePersonRegisterRoutine(this.executionState, calledBy, familyRegister);
    return effect.applyRoutine();
  }
  
  public boolean deletePersonRegister(final FamilyRegister familyRegister) {
    mir.routines.familiesToPersons.DeletePersonRegisterRoutine effect = new mir.routines.familiesToPersons.DeletePersonRegisterRoutine(this.executionState, calledBy, familyRegister);
    return effect.applyRoutine();
  }
  
  public boolean createFather(final Member member) {
    mir.routines.familiesToPersons.CreateFatherRoutine effect = new mir.routines.familiesToPersons.CreateFatherRoutine(this.executionState, calledBy, member);
    return effect.applyRoutine();
  }
  
  public boolean createSon(final Member member) {
    mir.routines.familiesToPersons.CreateSonRoutine effect = new mir.routines.familiesToPersons.CreateSonRoutine(this.executionState, calledBy, member);
    return effect.applyRoutine();
  }
  
  public boolean createMother(final Member member) {
    mir.routines.familiesToPersons.CreateMotherRoutine effect = new mir.routines.familiesToPersons.CreateMotherRoutine(this.executionState, calledBy, member);
    return effect.applyRoutine();
  }
  
  public boolean createDaughter(final Member member) {
    mir.routines.familiesToPersons.CreateDaughterRoutine effect = new mir.routines.familiesToPersons.CreateDaughterRoutine(this.executionState, calledBy, member);
    return effect.applyRoutine();
  }
  
  public boolean deletePerson(final Member member) {
    mir.routines.familiesToPersons.DeletePersonRoutine effect = new mir.routines.familiesToPersons.DeletePersonRoutine(this.executionState, calledBy, member);
    return effect.applyRoutine();
  }
  
  public boolean changeFullNameFromFirst(final Member member) {
    mir.routines.familiesToPersons.ChangeFullNameFromFirstRoutine effect = new mir.routines.familiesToPersons.ChangeFullNameFromFirstRoutine(this.executionState, calledBy, member);
    return effect.applyRoutine();
  }
  
  public boolean changeFullNameFromLast(final Family family) {
    mir.routines.familiesToPersons.ChangeFullNameFromLastRoutine effect = new mir.routines.familiesToPersons.ChangeFullNameFromLastRoutine(this.executionState, calledBy, family);
    return effect.applyRoutine();
  }
}
