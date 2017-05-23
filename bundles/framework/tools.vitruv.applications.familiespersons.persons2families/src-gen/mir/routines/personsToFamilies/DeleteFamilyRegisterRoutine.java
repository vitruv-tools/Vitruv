package mir.routines.personsToFamilies;

import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import java.io.IOException;
import mir.routines.personsToFamilies.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteFamilyRegisterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteFamilyRegisterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final PersonRegister personsRegister, final PersonRegister familyRegister) {
      return familyRegister;
    }
    
    public EObject getCorrepondenceSourceFamilyRegister(final PersonRegister personsRegister) {
      return personsRegister;
    }
  }
  
  public DeleteFamilyRegisterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PersonRegister personsRegister) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.personsToFamilies.DeleteFamilyRegisterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.personsToFamilies.RoutinesFacade(getExecutionState(), this);
    this.personsRegister = personsRegister;
  }
  
  private PersonRegister personsRegister;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteFamilyRegisterRoutine with input:");
    getLogger().debug("   PersonRegister: " + this.personsRegister);
    
    PersonRegister familyRegister = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceFamilyRegister(personsRegister), // correspondence source supplier
    	PersonRegister.class,
    	(PersonRegister _element) -> true, // correspondence precondition checker
    	null);
    if (familyRegister == null) {
    	return;
    }
    registerObjectUnderModification(familyRegister);
    deleteObject(userExecution.getElement1(personsRegister, familyRegister));
    
    postprocessElements();
  }
}
