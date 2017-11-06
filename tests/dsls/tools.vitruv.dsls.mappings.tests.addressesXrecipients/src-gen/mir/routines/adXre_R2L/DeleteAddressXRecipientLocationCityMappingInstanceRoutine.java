package mir.routines.adXre_R2L;

import edu.kit.ipd.sdq.metamodels.addresses.Address;
import edu.kit.ipd.sdq.metamodels.recipients.City;
import edu.kit.ipd.sdq.metamodels.recipients.Location;
import edu.kit.ipd.sdq.metamodels.recipients.Recipient;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.conditions.AddressXRecipientLocationCityConditions;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteAddressXRecipientLocationCityMappingInstanceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteAddressXRecipientLocationCityMappingInstanceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return "AddressXRecipientLocationCityMapping";
    }
    
    public String getTag1(final Recipients rRoot, final Recipient r, final Location l, final City c, final Address a) {
      return "AddressXRecipientLocationCity";
    }
    
    public EObject getElement6(final Recipients rRoot, final Recipient r, final Location l, final City c, final Address a) {
      return c;
    }
    
    public String getTag3(final Recipients rRoot, final Recipient r, final Location l, final City c, final Address a) {
      return "AddressXRecipientLocationCity";
    }
    
    public String getTag2(final Recipients rRoot, final Recipient r, final Location l, final City c, final Address a) {
      return "AddressXRecipientLocationCity";
    }
    
    public EObject getElement7(final Recipients rRoot, final Recipient r, final Location l, final City c, final Address a) {
      return a;
    }
    
    public EObject getElement1(final Recipients rRoot, final Recipient r, final Location l, final City c, final Address a) {
      return a;
    }
    
    public EObject getCorrepondenceSourceA(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return r;
    }
    
    public EObject getElement4(final Recipients rRoot, final Recipient r, final Location l, final City c, final Address a) {
      return l;
    }
    
    public EObject getElement5(final Recipients rRoot, final Recipient r, final Location l, final City c, final Address a) {
      return a;
    }
    
    public EObject getElement2(final Recipients rRoot, final Recipient r, final Location l, final City c, final Address a) {
      return r;
    }
    
    public EObject getElement3(final Recipients rRoot, final Recipient r, final Location l, final City c, final Address a) {
      return a;
    }
    
    public boolean checkMatcherPrecondition1(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      boolean _checkRightConditions = AddressXRecipientLocationCityConditions.addressXRecipientLocationCityConditions().checkRightConditions(rRoot, r, l, c);
      return (!_checkRightConditions);
    }
  }
  
  public DeleteAddressXRecipientLocationCityMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients rRoot, final Recipient r, final Location l, final City c) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_R2L.DeleteAddressXRecipientLocationCityMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_R2L.RoutinesFacade(getExecutionState(), this);
    this.rRoot = rRoot;this.r = r;this.l = l;this.c = c;
  }
  
  private Recipients rRoot;
  
  private Recipient r;
  
  private Location l;
  
  private City c;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteAddressXRecipientLocationCityMappingInstanceRoutine with input:");
    getLogger().debug("   rRoot: " + this.rRoot);
    getLogger().debug("   r: " + this.r);
    getLogger().debug("   l: " + this.l);
    getLogger().debug("   c: " + this.c);
    
    if (!userExecution.checkMatcherPrecondition1(rRoot, r, l, c)) {
    	return false;
    }
    edu.kit.ipd.sdq.metamodels.addresses.Address a = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceA(rRoot, r, l, c), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.addresses.Address.class,
    	(edu.kit.ipd.sdq.metamodels.addresses.Address _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(rRoot, r, l, c), 
    	false // asserted
    	);
    if (a == null) {
    	return false;
    }
    registerObjectUnderModification(a);
    removeCorrespondenceBetween(userExecution.getElement1(rRoot, r, l, c, a), userExecution.getElement2(rRoot, r, l, c, a), userExecution.getTag1(rRoot, r, l, c, a));
    
    removeCorrespondenceBetween(userExecution.getElement3(rRoot, r, l, c, a), userExecution.getElement4(rRoot, r, l, c, a), userExecution.getTag2(rRoot, r, l, c, a));
    
    removeCorrespondenceBetween(userExecution.getElement5(rRoot, r, l, c, a), userExecution.getElement6(rRoot, r, l, c, a), userExecution.getTag3(rRoot, r, l, c, a));
    
    deleteObject(userExecution.getElement7(rRoot, r, l, c, a));
    
    postprocessElements();
    
    return true;
  }
}
