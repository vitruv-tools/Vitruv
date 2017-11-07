package mir.routines.familiesToPersons;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.persons.Person;
import java.io.IOException;
import mir.routines.familiesToPersons.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeFullNameFromLastRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeFullNameFromLastRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Family family, final Person person) {
      return person;
    }
    
    public void update0Element(final Family family, final Person person) {
      String _get = person.getFullName().split(" ")[0];
      String _plus = (_get + " ");
      String _lastName = family.getLastName();
      String _plus_1 = (_plus + _lastName);
      person.setFullName(_plus_1);
    }
    
    public EObject getCorrepondenceSourcePerson(final Family family) {
      return family;
    }
  }
  
  public ChangeFullNameFromLastRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Family family) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.familiesToPersons.ChangeFullNameFromLastRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.familiesToPersons.RoutinesFacade(getExecutionState(), this);
    this.family = family;
  }
  
  private Family family;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeFullNameFromLastRoutine with input:");
    getLogger().debug("   family: " + this.family);
    
    edu.kit.ipd.sdq.metamodels.persons.Person person = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePerson(family), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.persons.Person.class,
    	(edu.kit.ipd.sdq.metamodels.persons.Person _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (person == null) {
    	return false;
    }
    registerObjectUnderModification(person);
    // val updatedElement userExecution.getElement1(family, person);
    userExecution.update0Element(family, person);
    
    postprocessElements();
    
    return true;
  }
}
