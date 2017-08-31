package mir.reactions.reactionsRecipientsToAddresses.adXre_R2L;

import edu.kit.ipd.sdq.mdsd.recipients.Recipient;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.AddressXRecipientLocationCityMapping;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

@SuppressWarnings("all")
class RecipientDeletedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    DeleteEObject<edu.kit.ipd.sdq.mdsd.recipients.Recipient> typedChange = (DeleteEObject<edu.kit.ipd.sdq.mdsd.recipients.Recipient>)change;
    edu.kit.ipd.sdq.mdsd.recipients.Recipient affectedEObject = typedChange.getAffectedEObject();
    mir.routines.adXre_R2L.RoutinesFacade routinesFacade = new mir.routines.adXre_R2L.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientDeletedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return DeleteEObject.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    DeleteEObject<edu.kit.ipd.sdq.mdsd.recipients.Recipient> relevantChange = (DeleteEObject<edu.kit.ipd.sdq.mdsd.recipients.Recipient>)change;
    if (!(relevantChange.getAffectedEObject() instanceof edu.kit.ipd.sdq.mdsd.recipients.Recipient)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof DeleteEObject)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Recipient affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      AddressXRecipientLocationCityMapping.addressXRecipientLocationCityMapping().removeRecipient(affectedEObject);
    }
  }
}
