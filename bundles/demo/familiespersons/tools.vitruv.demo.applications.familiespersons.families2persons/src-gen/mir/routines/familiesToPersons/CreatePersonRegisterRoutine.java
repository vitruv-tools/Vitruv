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
public class CreatePersonRegisterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePersonRegisterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final FamilyRegister familyRegister, final PersonRegister personRegister) {
      return personRegister;
    }
    
    public EObject getElement2(final FamilyRegister familyRegister, final PersonRegister personRegister) {
      return familyRegister;
    }
    
    public void updatePersonRegisterElement(final FamilyRegister familyRegister, final PersonRegister personRegister) {
      this.persistProjectRelative(familyRegister, personRegister, "model/persons.persons");
    }
  }
  
  public CreatePersonRegisterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final FamilyRegister familyRegister) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.familiesToPersons.CreatePersonRegisterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.familiesToPersons.RoutinesFacade(getExecutionState(), this);
    this.familyRegister = familyRegister;
  }
  
  private FamilyRegister familyRegister;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePersonRegisterRoutine with input:");
    getLogger().debug("   familyRegister: " + this.familyRegister);
    
    edu.kit.ipd.sdq.metamodels.persons.PersonRegister personRegister = edu.kit.ipd.sdq.metamodels.persons.impl.PersonsFactoryImpl.eINSTANCE.createPersonRegister();
    notifyObjectCreated(personRegister);
    userExecution.updatePersonRegisterElement(familyRegister, personRegister);
    
    addCorrespondenceBetween(userExecution.getElement1(familyRegister, personRegister), userExecution.getElement2(familyRegister, personRegister), "");
    
    postprocessElements();
    
    return true;
  }
}
