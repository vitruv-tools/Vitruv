package mir.routines.personsToFamilies;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.persons.Female;
import edu.kit.ipd.sdq.metamodels.persons.Male;
import edu.kit.ipd.sdq.metamodels.persons.Person;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createFamilyRegister(final PersonRegister personRegister) {
    mir.routines.personsToFamilies.CreateFamilyRegisterRoutine effect = new mir.routines.personsToFamilies.CreateFamilyRegisterRoutine(this.executionState, calledBy,
    	personRegister);
    effect.applyRoutine();
  }
  
  public void deleteFamilyRegister(final PersonRegister personsRegister) {
    mir.routines.personsToFamilies.DeleteFamilyRegisterRoutine effect = new mir.routines.personsToFamilies.DeleteFamilyRegisterRoutine(this.executionState, calledBy,
    	personsRegister);
    effect.applyRoutine();
  }
  
  public void createMaleMemberOfFamily(final Male person) {
    mir.routines.personsToFamilies.CreateMaleMemberOfFamilyRoutine effect = new mir.routines.personsToFamilies.CreateMaleMemberOfFamilyRoutine(this.executionState, calledBy,
    	person);
    effect.applyRoutine();
  }
  
  public void addCorr(final Person person, final Family family) {
    mir.routines.personsToFamilies.AddCorrRoutine effect = new mir.routines.personsToFamilies.AddCorrRoutine(this.executionState, calledBy,
    	person, family);
    effect.applyRoutine();
  }
  
  public void createFemaleMemberOfFamily(final Female person) {
    mir.routines.personsToFamilies.CreateFemaleMemberOfFamilyRoutine effect = new mir.routines.personsToFamilies.CreateFemaleMemberOfFamilyRoutine(this.executionState, calledBy,
    	person);
    effect.applyRoutine();
  }
  
  public void changeNames(final Person person) {
    mir.routines.personsToFamilies.ChangeNamesRoutine effect = new mir.routines.personsToFamilies.ChangeNamesRoutine(this.executionState, calledBy,
    	person);
    effect.applyRoutine();
  }
  
  public void deleteMember(final Person person) {
    mir.routines.personsToFamilies.DeleteMemberRoutine effect = new mir.routines.personsToFamilies.DeleteMemberRoutine(this.executionState, calledBy,
    	person);
    effect.applyRoutine();
  }
}
