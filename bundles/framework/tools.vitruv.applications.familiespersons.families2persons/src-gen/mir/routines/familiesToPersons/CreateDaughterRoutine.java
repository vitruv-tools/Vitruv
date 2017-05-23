package mir.routines.familiesToPersons;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.persons.Female;
import edu.kit.ipd.sdq.metamodels.persons.impl.PersonsFactoryImpl;
import java.io.IOException;
import mir.routines.familiesToPersons.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateDaughterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateDaughterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Member member, final Female person) {
      return member;
    }
    
    public EObject getElement4(final Member member, final Female person) {
      return person;
    }
    
    public EObject getElement2(final Member member, final Female person) {
      return person;
    }
    
    public EObject getElement3(final Member member, final Female person) {
      Family _familyDaughter = member.getFamilyDaughter();
      return _familyDaughter;
    }
    
    public void updatePersonElement(final Member member, final Female person) {
      String _firstName = member.getFirstName();
      String _plus = (_firstName + " ");
      Family _familyFather = member.getFamilyFather();
      String _lastName = _familyFather.getLastName();
      String _plus_1 = (_plus + _lastName);
      person.setFullName(_plus_1);
    }
  }
  
  public CreateDaughterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Member member) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.familiesToPersons.CreateDaughterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.familiesToPersons.RoutinesFacade(getExecutionState(), this);
    this.member = member;
  }
  
  private Member member;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateDaughterRoutine with input:");
    getLogger().debug("   Member: " + this.member);
    
    Female person = PersonsFactoryImpl.eINSTANCE.createFemale();
    userExecution.updatePersonElement(member, person);
    
    addCorrespondenceBetween(userExecution.getElement1(member, person), userExecution.getElement2(member, person), "");
    
    addCorrespondenceBetween(userExecution.getElement3(member, person), userExecution.getElement4(member, person), "");
    
    postprocessElements();
  }
}
