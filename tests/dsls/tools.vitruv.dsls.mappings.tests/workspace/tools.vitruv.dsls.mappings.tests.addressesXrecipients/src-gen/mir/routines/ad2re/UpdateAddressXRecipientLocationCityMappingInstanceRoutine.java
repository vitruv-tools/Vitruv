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
public class UpdateAddressXRecipientLocationCityMappingInstanceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private UpdateAddressXRecipientLocationCityMappingInstanceRoutine.ActionUserExecution userExecution;
  
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
    
    public EObject getCorrepondenceSourceC(final Address a, final Recipient r, final Location l) {
      return a;
    }
    
    public String getRetrieveTag2(final Address a, final Recipient r) {
      return "AddressXRecipientLocationCity";
    }
    
    public String getRetrieveTag3(final Address a, final Recipient r, final Location l) {
      return "AddressXRecipientLocationCity";
    }
    
    public EObject getCorrepondenceSourceL(final Address a, final Recipient r) {
      return a;
    }
    
    public void callRoutine1(final Address a, final Recipient r, final Location l, final City c, @Extension final RoutinesFacade _routinesFacade) {
      AddressXRecipientLocationCityConditions.enforceRigthPostconditions(r, l, c);
      AddressXRecipientLocationCityConditions.enforceFromLeft2Right(a, r, l, c);
    }
  }
  
  public UpdateAddressXRecipientLocationCityMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Address a) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ad2re.UpdateAddressXRecipientLocationCityMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ad2re.RoutinesFacade(getExecutionState(), this);
    this.a = a;
  }
  
  private Address a;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateAddressXRecipientLocationCityMappingInstanceRoutine with input:");
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
    userExecution.callRoutine1(a, r, l, c, actionsFacade);
    
    postprocessElements();
  }
}
