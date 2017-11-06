package mir.routines.adXre_L2R;

import edu.kit.ipd.sdq.metamodels.addresses.Address;
import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.City;
import edu.kit.ipd.sdq.metamodels.recipients.Location;
import edu.kit.ipd.sdq.metamodels.recipients.Recipient;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.adXre_L2R.RoutinesFacade;
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
    
    public String getRetrieveTag1(final Addresses aRoot, final Address a) {
      return "AdRootXReRootMapping";
    }
    
    public String getTag1(final Addresses aRoot, final Address a, final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return "AddressXRecipientLocationCityMapping";
    }
    
    public EObject getElement6(final Addresses aRoot, final Address a, final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return c;
    }
    
    public String getTag3(final Addresses aRoot, final Address a, final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return "AddressXRecipientLocationCityMapping";
    }
    
    public String getTag2(final Addresses aRoot, final Address a, final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return "AddressXRecipientLocationCityMapping";
    }
    
    public void callRoutine1(final Addresses aRoot, final Address a, final Recipients rRoot, final Recipient r, final Location l, final City c, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.enforceAddressXRecipientLocationCityMappingConditionsFromLeftToRight(aRoot, rRoot, a, r, l, c);
    }
    
    public EObject getCorrepondenceSourceRRoot(final Addresses aRoot, final Address a) {
      return aRoot;
    }
    
    public EObject getElement1(final Addresses aRoot, final Address a, final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return a;
    }
    
    public void executeAction1(final Addresses aRoot, final Address a, final Recipients rRoot, final Recipient r, final Location l, final City c, @Extension final RoutinesFacade _routinesFacade) {
      AddressXRecipientLocationCityMapping.addressXRecipientLocationCityMapping().registerRightElementsAndPromoteCandidates(aRoot, rRoot, a, r, l, c);
    }
    
    public EObject getElement4(final Addresses aRoot, final Address a, final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return l;
    }
    
    public EObject getElement5(final Addresses aRoot, final Address a, final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return a;
    }
    
    public EObject getElement2(final Addresses aRoot, final Address a, final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return r;
    }
    
    public EObject getElement3(final Addresses aRoot, final Address a, final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return a;
    }
    
    public boolean checkMatcherPrecondition1(final Addresses aRoot, final Address a, final Recipients rRoot) {
      return AddressXRecipientLocationCityConditions.addressXRecipientLocationCityConditions().checkLeftConditions(aRoot, a);
    }
  }
  
  public CreateAddressXRecipientLocationCityMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses aRoot, final Address a) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_L2R.CreateAddressXRecipientLocationCityMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_L2R.RoutinesFacade(getExecutionState(), this);
    this.aRoot = aRoot;this.a = a;
  }
  
  private Addresses aRoot;
  
  private Address a;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateAddressXRecipientLocationCityMappingInstanceRoutine with input:");
    getLogger().debug("   aRoot: " + this.aRoot);
    getLogger().debug("   a: " + this.a);
    
    edu.kit.ipd.sdq.metamodels.recipients.Recipients rRoot = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRRoot(aRoot, a), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.recipients.Recipients.class,
    	(edu.kit.ipd.sdq.metamodels.recipients.Recipients _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(aRoot, a), 
    	false // asserted
    	);
    if (rRoot == null) {
    	return false;
    }
    registerObjectUnderModification(rRoot);
    if (!userExecution.checkMatcherPrecondition1(aRoot, a, rRoot)) {
    	return false;
    }
    edu.kit.ipd.sdq.metamodels.recipients.Recipient r = edu.kit.ipd.sdq.metamodels.recipients.impl.RecipientsFactoryImpl.eINSTANCE.createRecipient();
    notifyObjectCreated(r);
    
    edu.kit.ipd.sdq.metamodels.recipients.Location l = edu.kit.ipd.sdq.metamodels.recipients.impl.RecipientsFactoryImpl.eINSTANCE.createLocation();
    notifyObjectCreated(l);
    
    edu.kit.ipd.sdq.metamodels.recipients.City c = edu.kit.ipd.sdq.metamodels.recipients.impl.RecipientsFactoryImpl.eINSTANCE.createCity();
    notifyObjectCreated(c);
    
    addCorrespondenceBetween(userExecution.getElement1(aRoot, a, rRoot, r, l, c), userExecution.getElement2(aRoot, a, rRoot, r, l, c), userExecution.getTag1(aRoot, a, rRoot, r, l, c));
    
    addCorrespondenceBetween(userExecution.getElement3(aRoot, a, rRoot, r, l, c), userExecution.getElement4(aRoot, a, rRoot, r, l, c), userExecution.getTag2(aRoot, a, rRoot, r, l, c));
    
    addCorrespondenceBetween(userExecution.getElement5(aRoot, a, rRoot, r, l, c), userExecution.getElement6(aRoot, a, rRoot, r, l, c), userExecution.getTag3(aRoot, a, rRoot, r, l, c));
    
    userExecution.callRoutine1(aRoot, a, rRoot, r, l, c, actionsFacade);
    
    userExecution.executeAction1(aRoot, a, rRoot, r, l, c, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
