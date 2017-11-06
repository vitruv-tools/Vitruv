package mir.routines.personsToFamilies;

import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.persons.Person;
import java.io.IOException;
import mir.routines.personsToFamilies.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteMemberRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteMemberRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Person person, final Member member) {
      return member;
    }
    
    public EObject getCorrepondenceSourceMember(final Person person) {
      return person;
    }
  }
  
  public DeleteMemberRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Person person) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.personsToFamilies.DeleteMemberRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.personsToFamilies.RoutinesFacade(getExecutionState(), this);
    this.person = person;
  }
  
  private Person person;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteMemberRoutine with input:");
    getLogger().debug("   person: " + this.person);
    
    edu.kit.ipd.sdq.metamodels.families.Member member = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceMember(person), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.families.Member.class,
    	(edu.kit.ipd.sdq.metamodels.families.Member _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (member == null) {
    	return false;
    }
    registerObjectUnderModification(member);
    deleteObject(userExecution.getElement1(person, member));
    
    postprocessElements();
    
    return true;
  }
}
