package mir.routines.personsToFamilies;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.persons.Person;
import java.io.IOException;
import mir.routines.personsToFamilies.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCorrRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddCorrRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Person person, final Family family) {
      return person;
    }
    
    public EObject getElement2(final Person person, final Family family) {
      return family;
    }
  }
  
  public AddCorrRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Person person, final Family family) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.personsToFamilies.AddCorrRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.personsToFamilies.RoutinesFacade(getExecutionState(), this);
    this.person = person;this.family = family;
  }
  
  private Person person;
  
  private Family family;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrRoutine with input:");
    getLogger().debug("   person: " + this.person);
    getLogger().debug("   family: " + this.family);
    
    addCorrespondenceBetween(userExecution.getElement1(person, family), userExecution.getElement2(person, family), "");
    
    postprocessElements();
    
    return true;
  }
}
