package mir.routines.adXre_R2L;

import edu.kit.ipd.sdq.mdsd.addresses.Address;
import edu.kit.ipd.sdq.mdsd.addresses.Addresses;
import edu.kit.ipd.sdq.mdsd.recipients.City;
import edu.kit.ipd.sdq.mdsd.recipients.Location;
import edu.kit.ipd.sdq.mdsd.recipients.Recipient;
import edu.kit.ipd.sdq.mdsd.recipients.Recipients;
import java.io.IOException;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.conditions.AddressXRecipientLocationCityConditions;
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
    
    public EObject getCorrepondenceSourceARoot(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return rRoot;
    }
    
    public EObject getCorrepondenceSourceA(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot) {
      return r;
    }
    
    public String getRetrieveTag1(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return "AdRootXReRootMapping";
    }
    
    public String getRetrieveTag2(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot) {
      return "AddressXRecipientLocationCityMapping";
    }
    
    public boolean checkMatcherPrecondition1(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a) {
      return AddressXRecipientLocationCityConditions.addressXRecipientLocationCityConditions().checkRightConditions(rRoot, r, l, c);
    }
    
    public void callRoutine1(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.enforceAddressXRecipientLocationCityMappingConditionsFromRightToLeft(aRoot, rRoot, a, r, l, c);
    }
  }
  
  public UpdateAddressXRecipientLocationCityMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients rRoot, final Recipient r, final Location l, final City c) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_R2L.UpdateAddressXRecipientLocationCityMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_R2L.RoutinesFacade(getExecutionState(), this);
    this.rRoot = rRoot;this.r = r;this.l = l;this.c = c;
  }
  
  private Recipients rRoot;
  
  private Recipient r;
  
  private Location l;
  
  private City c;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateAddressXRecipientLocationCityMappingInstanceRoutine with input:");
    getLogger().debug("   rRoot: " + this.rRoot);
    getLogger().debug("   r: " + this.r);
    getLogger().debug("   l: " + this.l);
    getLogger().debug("   c: " + this.c);
    
    edu.kit.ipd.sdq.mdsd.addresses.Addresses aRoot = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceARoot(rRoot, r, l, c), // correspondence source supplier
    	edu.kit.ipd.sdq.mdsd.addresses.Addresses.class,
    	(edu.kit.ipd.sdq.mdsd.addresses.Addresses _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(rRoot, r, l, c));
    if (aRoot == null) {
    	return;
    }
    registerObjectUnderModification(aRoot);
    edu.kit.ipd.sdq.mdsd.addresses.Address a = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceA(rRoot, r, l, c, aRoot), // correspondence source supplier
    	edu.kit.ipd.sdq.mdsd.addresses.Address.class,
    	(edu.kit.ipd.sdq.mdsd.addresses.Address _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(rRoot, r, l, c, aRoot));
    if (a == null) {
    	return;
    }
    registerObjectUnderModification(a);
    if (!userExecution.checkMatcherPrecondition1(rRoot, r, l, c, aRoot, a)) {
    	return;
    }
    userExecution.callRoutine1(rRoot, r, l, c, aRoot, a, actionsFacade);
    
    postprocessElements();
  }
}
