package mir.routines.adXre_R2L;

import edu.kit.ipd.sdq.metamodels.addresses.Address;
import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.City;
import edu.kit.ipd.sdq.metamodels.recipients.Location;
import edu.kit.ipd.sdq.metamodels.recipients.Recipient;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.AddressXRecipientLocationCityMapping;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.conditions.AddressXRecipientLocationCityConditions;
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
    
    public String getRetrieveTag1(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return "AdRootXReRootMapping";
    }
    
    public String getTag1(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a) {
      return "AddressXRecipientLocationCityMapping";
    }
    
    public EObject getElement6(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a) {
      return c;
    }
    
    public String getTag3(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a) {
      return "AddressXRecipientLocationCityMapping";
    }
    
    public String getTag2(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a) {
      return "AddressXRecipientLocationCityMapping";
    }
    
    public void callRoutine1(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.enforceAddressXRecipientLocationCityMappingConditionsFromRightToLeft(aRoot, rRoot, a, r, l, c);
    }
    
    public EObject getElement1(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a) {
      return a;
    }
    
    public EObject getCorrepondenceSourceARoot(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return rRoot;
    }
    
    public void executeAction1(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a, @Extension final RoutinesFacade _routinesFacade) {
      AddressXRecipientLocationCityMapping.addressXRecipientLocationCityMapping().registerLeftElementsAndPromoteCandidates(aRoot, rRoot, a, r, l, c);
    }
    
    public EObject getElement4(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a) {
      return l;
    }
    
    public EObject getElement5(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a) {
      return a;
    }
    
    public EObject getElement2(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a) {
      return r;
    }
    
    public EObject getElement3(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot, final Address a) {
      return a;
    }
    
    public boolean checkMatcherPrecondition1(final Recipients rRoot, final Recipient r, final Location l, final City c, final Addresses aRoot) {
      return AddressXRecipientLocationCityConditions.addressXRecipientLocationCityConditions().checkRightConditions(rRoot, r, l, c);
    }
  }
  
  public CreateAddressXRecipientLocationCityMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients rRoot, final Recipient r, final Location l, final City c) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_R2L.CreateAddressXRecipientLocationCityMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_R2L.RoutinesFacade(getExecutionState(), this);
    this.rRoot = rRoot;this.r = r;this.l = l;this.c = c;
  }
  
  private Recipients rRoot;
  
  private Recipient r;
  
  private Location l;
  
  private City c;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateAddressXRecipientLocationCityMappingInstanceRoutine with input:");
    getLogger().debug("   rRoot: " + this.rRoot);
    getLogger().debug("   r: " + this.r);
    getLogger().debug("   l: " + this.l);
    getLogger().debug("   c: " + this.c);
    
    edu.kit.ipd.sdq.metamodels.addresses.Addresses aRoot = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceARoot(rRoot, r, l, c), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.addresses.Addresses.class,
    	(edu.kit.ipd.sdq.metamodels.addresses.Addresses _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(rRoot, r, l, c), 
    	false // asserted
    	);
    if (aRoot == null) {
    	return false;
    }
    registerObjectUnderModification(aRoot);
    if (!userExecution.checkMatcherPrecondition1(rRoot, r, l, c, aRoot)) {
    	return false;
    }
    edu.kit.ipd.sdq.metamodels.addresses.Address a = edu.kit.ipd.sdq.metamodels.addresses.impl.AddressesFactoryImpl.eINSTANCE.createAddress();
    notifyObjectCreated(a);
    
    addCorrespondenceBetween(userExecution.getElement1(rRoot, r, l, c, aRoot, a), userExecution.getElement2(rRoot, r, l, c, aRoot, a), userExecution.getTag1(rRoot, r, l, c, aRoot, a));
    
    addCorrespondenceBetween(userExecution.getElement3(rRoot, r, l, c, aRoot, a), userExecution.getElement4(rRoot, r, l, c, aRoot, a), userExecution.getTag2(rRoot, r, l, c, aRoot, a));
    
    addCorrespondenceBetween(userExecution.getElement5(rRoot, r, l, c, aRoot, a), userExecution.getElement6(rRoot, r, l, c, aRoot, a), userExecution.getTag3(rRoot, r, l, c, aRoot, a));
    
    userExecution.callRoutine1(rRoot, r, l, c, aRoot, a, actionsFacade);
    
    userExecution.executeAction1(rRoot, r, l, c, aRoot, a, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
