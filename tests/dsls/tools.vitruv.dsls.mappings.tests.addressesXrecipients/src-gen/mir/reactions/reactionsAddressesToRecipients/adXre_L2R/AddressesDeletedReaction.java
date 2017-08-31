package mir.reactions.reactionsAddressesToRecipients.adXre_L2R;

import edu.kit.ipd.sdq.mdsd.addresses.Addresses;
import mir.routines.adXre_L2R.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.AdRootXReRootMapping;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

/**
 * *****************************************************
 *  ********** BEGIN DELETE REACTIONS **********
 *  *****************************************************
 */
@SuppressWarnings("all")
class AddressesDeletedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    DeleteEObject<edu.kit.ipd.sdq.mdsd.addresses.Addresses> typedChange = (DeleteEObject<edu.kit.ipd.sdq.mdsd.addresses.Addresses>)change;
    edu.kit.ipd.sdq.mdsd.addresses.Addresses affectedEObject = typedChange.getAffectedEObject();
    mir.routines.adXre_L2R.RoutinesFacade routinesFacade = new mir.routines.adXre_L2R.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressesDeletedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressesDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return DeleteEObject.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    DeleteEObject<edu.kit.ipd.sdq.mdsd.addresses.Addresses> relevantChange = (DeleteEObject<edu.kit.ipd.sdq.mdsd.addresses.Addresses>)change;
    if (!(relevantChange.getAffectedEObject() instanceof edu.kit.ipd.sdq.mdsd.addresses.Addresses)) {
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
    
    public void callRoutine1(final Addresses affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      AdRootXReRootMapping.adRootXReRootMapping().removeAddresses(affectedEObject);
    }
  }
}
