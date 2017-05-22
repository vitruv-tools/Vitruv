package mir.routines.personsToFamilies;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.families.impl.FamiliesFactoryImpl;
import edu.kit.ipd.sdq.metamodels.persons.Male;
import java.io.IOException;
import mir.routines.personsToFamilies.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateMaleMemberOfFamilyRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateMaleMemberOfFamilyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Male person, final Member member) {
      return person;
    }
    
    public EObject getElement4(final Male person, final Member member, final Family family) {
      return family;
    }
    
    public void updateMemberElement(final Male person, final Member member) {
      String _fullName = person.getFullName();
      String[] _split = _fullName.split(" ");
      String _get = _split[0];
      member.setFirstName(_get);
    }
    
    public EObject getElement2(final Male person, final Member member) {
      return member;
    }
    
    public void updateFamilyElement(final Male person, final Member member, final Family family) {
      String _fullName = person.getFullName();
      String[] _split = _fullName.split(" ");
      String _get = _split[1];
      family.setLastName(_get);
      family.setFather(member);
    }
    
    public EObject getElement3(final Male person, final Member member, final Family family) {
      return person;
    }
  }
  
  public CreateMaleMemberOfFamilyRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Male person) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.personsToFamilies.CreateMaleMemberOfFamilyRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.personsToFamilies.RoutinesFacade(getExecutionState(), this);
    this.person = person;
  }
  
  private Male person;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateMaleMemberOfFamilyRoutine with input:");
    getLogger().debug("   Male: " + this.person);
    
    Member member = FamiliesFactoryImpl.eINSTANCE.createMember();
    userExecution.updateMemberElement(person, member);
    
    addCorrespondenceBetween(userExecution.getElement1(person, member), userExecution.getElement2(person, member), "");
    
    Family family = FamiliesFactoryImpl.eINSTANCE.createFamily();
    userExecution.updateFamilyElement(person, member, family);
    
    addCorrespondenceBetween(userExecution.getElement3(person, member, family), userExecution.getElement4(person, member, family), "");
    
    postprocessElements();
  }
}
