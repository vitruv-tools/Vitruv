package mir.routines.ad2re;

import edu.kit.ipd.sdq.mdsd.addresses.Address;
import edu.kit.ipd.sdq.mdsd.recipients.City;
import edu.kit.ipd.sdq.mdsd.recipients.Location;
import edu.kit.ipd.sdq.mdsd.recipients.Recipient;
import edu.kit.ipd.sdq.mdsd.recipients.impl.RecipientsFactoryImpl;
import java.io.IOException;
import mir.routines.ad2re.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateAddressXRecipientLocationCityMappingInstanceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateAddressXRecipientLocationCityMappingInstanceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Address a, final Recipient r, final Location l, final City c) {
      return a;
    }
    
    public EObject getElement4(final Address a, final Recipient r, final Location l, final City c) {
      return l;
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
    
    public EObject getElement6(final Address a, final Recipient r, final Location l, final City c) {
      return c;
    }
    
    public void callRoutine1(final Address a, final Recipient r, final Location l, final City c, @Extension final RoutinesFacade _routinesFacade) {
      AddressXRecipientLocationCityMapping.registerLeftInstance(a);
    }
  }
  
  public CreateAddressXRecipientLocationCityMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Address a) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ad2re.CreateAddressXRecipientLocationCityMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ad2re.RoutinesFacade(getExecutionState(), this);
    this.a = a;
  }
  
  private Address a;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateAddressXRecipientLocationCityMappingInstanceRoutine with input:");
    getLogger().debug("   Address: " + this.a);
    
    Recipient r = RecipientsFactoryImpl.eINSTANCE.createRecipient();
    notifyObjectCreated(r);
    
    Location l = RecipientsFactoryImpl.eINSTANCE.createLocation();
    notifyObjectCreated(l);
    
    City c = RecipientsFactoryImpl.eINSTANCE.createCity();
    notifyObjectCreated(c);
    
    addCorrespondenceBetween(userExecution.getElement1(a, r, l, c), userExecution.getElement2(a, r, l, c), "");
    
    addCorrespondenceBetween(userExecution.getElement3(a, r, l, c), userExecution.getElement4(a, r, l, c), "");
    
    addCorrespondenceBetween(userExecution.getElement5(a, r, l, c), userExecution.getElement6(a, r, l, c), "");
    
    userExecution.callRoutine1(a, r, l, c, actionsFacade);
    
    postprocessElements();
  }
}
