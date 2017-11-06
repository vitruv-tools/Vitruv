package mir.routines.familiesToPersons;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import java.io.IOException;
import mir.routines.familiesToPersons.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeletePersonRegisterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeletePersonRegisterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePersonRegister(final FamilyRegister familyRegister) {
      return familyRegister;
    }
    
    public EObject getElement1(final FamilyRegister familyRegister, final PersonRegister personRegister) {
      return personRegister;
    }
  }
  
  public DeletePersonRegisterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final FamilyRegister familyRegister) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.familiesToPersons.DeletePersonRegisterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.familiesToPersons.RoutinesFacade(getExecutionState(), this);
    this.familyRegister = familyRegister;
  }
  
  private FamilyRegister familyRegister;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeletePersonRegisterRoutine with input:");
    getLogger().debug("   familyRegister: " + this.familyRegister);
    
    edu.kit.ipd.sdq.metamodels.persons.PersonRegister personRegister = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePersonRegister(familyRegister), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.persons.PersonRegister.class,
    	(edu.kit.ipd.sdq.metamodels.persons.PersonRegister _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (personRegister == null) {
    	return false;
    }
    registerObjectUnderModification(personRegister);
    deleteObject(userExecution.getElement1(familyRegister, personRegister));
    
    postprocessElements();
    
    return true;
  }
}
