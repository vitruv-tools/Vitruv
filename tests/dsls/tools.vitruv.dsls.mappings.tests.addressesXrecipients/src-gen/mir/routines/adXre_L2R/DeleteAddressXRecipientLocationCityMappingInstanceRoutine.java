package mir.routines.adXre_L2R;

import edu.kit.ipd.sdq.metamodels.addresses.Address;
import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.City;
import edu.kit.ipd.sdq.metamodels.recipients.Location;
import edu.kit.ipd.sdq.metamodels.recipients.Recipient;
import java.io.IOException;
import mir.routines.adXre_L2R.RoutinesFacade;
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
    
    public EObject getCorrepondenceSourceR(final Addresses aRoot, final Address a) {
      return a;
    }
    
    public String getRetrieveTag1(final Addresses aRoot, final Address a) {
      return "AddressXRecipientLocationCityMapping";
    }
    
    public String getRetrieveTag2(final Addresses aRoot, final Address a, final Recipient r) {
      return "AddressXRecipientLocationCityMapping";
    }
    
    public String getRetrieveTag3(final Addresses aRoot, final Address a, final Recipient r, final Location l) {
      return "AddressXRecipientLocationCityMapping";
    }
    
    public String getTag1(final Addresses aRoot, final Address a, final Recipient r, final Location l, final City c) {
      return "AddressXRecipientLocationCity";
    }
    
    public EObject getElement8(final Addresses aRoot, final Address a, final Recipient r, final Location l, final City c) {
      return l;
    }
    
    public EObject getElement9(final Addresses aRoot, final Address a, final Recipient r, final Location l, final City c) {
      return c;
    }
    
    public EObject getElement6(final Addresses aRoot, final Address a, final Recipient r, final Location l, final City c) {
      return c;
    }
    
    public String getTag3(final Addresses aRoot, final Address a, final Recipient r, final Location l, final City c) {
      return "AddressXRecipientLocationCity";
    }
    
    public String getTag2(final Addresses aRoot, final Address a, final Recipient r, final Location l, final City c) {
      return "AddressXRecipientLocationCity";
    }
    
    public EObject getElement7(final Addresses aRoot, final Address a, final Recipient r, final Location l, final City c) {
      return r;
    }
    
    public EObject getElement1(final Addresses aRoot, final Address a, final Recipient r, final Location l, final City c) {
      return a;
    }
    
    public EObject getElement4(final Addresses aRoot, final Address a, final Recipient r, final Location l, final City c) {
      return l;
    }
    
    public EObject getCorrepondenceSourceC(final Addresses aRoot, final Address a, final Recipient r, final Location l) {
      return a;
    }
    
    public EObject getElement5(final Addresses aRoot, final Address a, final Recipient r, final Location l, final City c) {
      return a;
    }
    
    public EObject getElement2(final Addresses aRoot, final Address a, final Recipient r, final Location l, final City c) {
      return r;
    }
    
    public EObject getElement3(final Addresses aRoot, final Address a, final Recipient r, final Location l, final City c) {
      return a;
    }
    
    public boolean checkMatcherPrecondition1(final Addresses aRoot, final Address a) {
      boolean _checkLeftConditions = AddressXRecipientLocationCityConditions.addressXRecipientLocationCityConditions().checkLeftConditions(aRoot, a);
      return (!_checkLeftConditions);
    }
    
    public EObject getCorrepondenceSourceL(final Addresses aRoot, final Address a, final Recipient r) {
      return a;
    }
  }
  
  public DeleteAddressXRecipientLocationCityMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses aRoot, final Address a) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_L2R.DeleteAddressXRecipientLocationCityMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_L2R.RoutinesFacade(getExecutionState(), this);
    this.aRoot = aRoot;this.a = a;
  }
  
  private Addresses aRoot;
  
  private Address a;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteAddressXRecipientLocationCityMappingInstanceRoutine with input:");
    getLogger().debug("   aRoot: " + this.aRoot);
    getLogger().debug("   a: " + this.a);
    
    if (!userExecution.checkMatcherPrecondition1(aRoot, a)) {
    	return false;
    }
    edu.kit.ipd.sdq.metamodels.recipients.Recipient r = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceR(aRoot, a), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.recipients.Recipient.class,
    	(edu.kit.ipd.sdq.metamodels.recipients.Recipient _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(aRoot, a), 
    	false // asserted
    	);
    if (r == null) {
    	return false;
    }
    registerObjectUnderModification(r);
    edu.kit.ipd.sdq.metamodels.recipients.Location l = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceL(aRoot, a, r), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.recipients.Location.class,
    	(edu.kit.ipd.sdq.metamodels.recipients.Location _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(aRoot, a, r), 
    	false // asserted
    	);
    if (l == null) {
    	return false;
    }
    registerObjectUnderModification(l);
    edu.kit.ipd.sdq.metamodels.recipients.City c = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceC(aRoot, a, r, l), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.recipients.City.class,
    	(edu.kit.ipd.sdq.metamodels.recipients.City _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(aRoot, a, r, l), 
    	false // asserted
    	);
    if (c == null) {
    	return false;
    }
    registerObjectUnderModification(c);
    removeCorrespondenceBetween(userExecution.getElement1(aRoot, a, r, l, c), userExecution.getElement2(aRoot, a, r, l, c), userExecution.getTag1(aRoot, a, r, l, c));
    
    removeCorrespondenceBetween(userExecution.getElement3(aRoot, a, r, l, c), userExecution.getElement4(aRoot, a, r, l, c), userExecution.getTag2(aRoot, a, r, l, c));
    
    removeCorrespondenceBetween(userExecution.getElement5(aRoot, a, r, l, c), userExecution.getElement6(aRoot, a, r, l, c), userExecution.getTag3(aRoot, a, r, l, c));
    
    deleteObject(userExecution.getElement7(aRoot, a, r, l, c));
    
    deleteObject(userExecution.getElement8(aRoot, a, r, l, c));
    
    deleteObject(userExecution.getElement9(aRoot, a, r, l, c));
    
    postprocessElements();
    
    return true;
  }
}
