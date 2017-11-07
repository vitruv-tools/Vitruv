package mir.routines.personsToFamilies;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import java.io.IOException;
import mir.routines.personsToFamilies.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateFamilyRegisterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateFamilyRegisterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final PersonRegister personRegister, final FamilyRegister familyRegister) {
      return familyRegister;
    }
    
    public EObject getElement2(final PersonRegister personRegister, final FamilyRegister familyRegister) {
      return personRegister;
    }
    
    public void updateFamilyRegisterElement(final PersonRegister personRegister, final FamilyRegister familyRegister) {
    }
  }
  
  public CreateFamilyRegisterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PersonRegister personRegister) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.personsToFamilies.CreateFamilyRegisterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.personsToFamilies.RoutinesFacade(getExecutionState(), this);
    this.personRegister = personRegister;
  }
  
  private PersonRegister personRegister;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateFamilyRegisterRoutine with input:");
    getLogger().debug("   personRegister: " + this.personRegister);
    
    edu.kit.ipd.sdq.metamodels.families.FamilyRegister familyRegister = edu.kit.ipd.sdq.metamodels.families.impl.FamiliesFactoryImpl.eINSTANCE.createFamilyRegister();
    notifyObjectCreated(familyRegister);
    userExecution.updateFamilyRegisterElement(personRegister, familyRegister);
    
    addCorrespondenceBetween(userExecution.getElement1(personRegister, familyRegister), userExecution.getElement2(personRegister, familyRegister), "");
    
    postprocessElements();
    
    return true;
  }
}
