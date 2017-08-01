package mir.routines.ad2re;

import edu.kit.ipd.sdq.mdsd.addresses.Address;
import edu.kit.ipd.sdq.mdsd.recipients.City;
import edu.kit.ipd.sdq.mdsd.recipients.Location;
import edu.kit.ipd.sdq.mdsd.recipients.Recipient;
import java.io.IOException;
import mir.routines.ad2re.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public EObject getCorrepondenceSourceR(final Address a) {
      return a;
    }
    
    public String getRetrieveTag1(final Address a) {
      return "AddressXRecipientLocationCity";
    }
    
    public String getRetrieveTag2(final Address a, final Recipient r) {
      return "AddressXRecipientLocationCity";
    }
    
    public String getRetrieveTag3(final Address a, final Recipient r, final Location l) {
      return "AddressXRecipientLocationCity";
    }
    
    public EObject getElement8(final Address a, final Recipient r, final Location l, final City c) {
      return l;
    }
    
    public EObject getElement9(final Address a, final Recipient r, final Location l, final City c) {
      return c;
    }
    
    public EObject getElement6(final Address a, final Recipient r, final Location l, final City c) {
      return c;
    }
    
    public EObject getElement7(final Address a, final Recipient r, final Location l, final City c) {
      return r;
    }
    
    public void callRoutine1(final Address a, final Recipient r, final Location l, final City c, @Extension final RoutinesFacade _routinesFacade) {
      AddressXRecipientLocationCityMapping.deregisterLeftInstance(a);
    }
    
    public EObject getElement1(final Address a, final Recipient r, final Location l, final City c) {
      return a;
    }
    
    public EObject getElement4(final Address a, final Recipient r, final Location l, final City c) {
      return l;
    }
    
    public EObject getCorrepondenceSourceC(final Address a, final Recipient r, final Location l) {
      return a;
    }
    
    public EObject getElement5(final Address a, final Recipient r, final Location l, final City c) {
      return a;
    }
    
    public EObject getElement2(final Address a, final Recipient r, final Location l, final City c) {
      return r;
    }
    
    public EObject getElement3(final Address a, final Recipient r, final Location l, final City c) {
      return a;
    }
    
    public EObject getCorrepondenceSourceL(final Address a, final Recipient r) {
      return a;
    }
  }
  
  public DeleteAddressXRecipientLocationCityMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Address a) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ad2re.DeleteAddressXRecipientLocationCityMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ad2re.RoutinesFacade(getExecutionState(), this);
    this.a = a;
  }
  
  private Address a;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteAddressXRecipientLocationCityMappingInstanceRoutine with input:");
    getLogger().debug("   Address: " + this.a);
    
    Recipient r = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceR(a), // correspondence source supplier
    	Recipient.class,
    	(Recipient _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(a));
    if (r == null) {
    	return;
    }
    registerObjectUnderModification(r);
    Location l = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceL(a, r), // correspondence source supplier
    	Location.class,
    	(Location _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(a, r));
    if (l == null) {
    	return;
    }
    registerObjectUnderModification(l);
    City c = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceC(a, r, l), // correspondence source supplier
    	City.class,
    	(City _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(a, r, l));
    if (c == null) {
    	return;
    }
    registerObjectUnderModification(c);
    removeCorrespondenceBetween(userExecution.getElement1(a, r, l, c), userExecution.getElement2(a, r, l, c));
    
    removeCorrespondenceBetween(userExecution.getElement3(a, r, l, c), userExecution.getElement4(a, r, l, c));
    
    removeCorrespondenceBetween(userExecution.getElement5(a, r, l, c), userExecution.getElement6(a, r, l, c));
    
    deleteObject(userExecution.getElement7(a, r, l, c));
    
    deleteObject(userExecution.getElement8(a, r, l, c));
    
    deleteObject(userExecution.getElement9(a, r, l, c));
    
    userExecution.callRoutine1(a, r, l, c, actionsFacade);
    
    postprocessElements();
  }
}
