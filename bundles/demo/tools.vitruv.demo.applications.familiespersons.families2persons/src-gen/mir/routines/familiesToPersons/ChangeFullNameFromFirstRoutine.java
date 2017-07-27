package mir.routines.familiesToPersons;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.persons.Person;
import java.io.IOException;
import mir.routines.familiesToPersons.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeFullNameFromFirstRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeFullNameFromFirstRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Member member, final Person person) {
      return person;
    }
    
    public void update0Element(final Member member, final Person person) {
      String _firstName = member.getFirstName();
      String _plus = (_firstName + " ");
      Family _familyFather = member.getFamilyFather();
      String _lastName = _familyFather.getLastName();
      String _plus_1 = (_plus + _lastName);
      person.setFullName(_plus_1);
    }
    
    public EObject getCorrepondenceSourcePerson(final Member member) {
      return member;
    }
  }
  
  public ChangeFullNameFromFirstRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Member member) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.familiesToPersons.ChangeFullNameFromFirstRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.familiesToPersons.RoutinesFacade(getExecutionState(), this);
    this.member = member;
  }
  
  private Member member;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeFullNameFromFirstRoutine with input:");
    getLogger().debug("   Member: " + this.member);
    
    Person person = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePerson(member), // correspondence source supplier
    	Person.class,
    	(Person _element) -> true, // correspondence precondition checker
    	null);
    if (person == null) {
    	return;
    }
    registerObjectUnderModification(person);
    // val updatedElement userExecution.getElement1(member, person);
    userExecution.update0Element(member, person);
    
    postprocessElements();
  }
}
