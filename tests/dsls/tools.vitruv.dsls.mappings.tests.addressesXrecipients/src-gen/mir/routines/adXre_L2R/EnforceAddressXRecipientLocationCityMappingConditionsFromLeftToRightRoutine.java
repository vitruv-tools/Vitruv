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
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
      return l;
    }
    
    public void update0Element(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
      l.setNumber(a.getNumber());
      l.setStreet(a.getStreet());
    }
    
    public EObject getElement2(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
      return c;
    }
    
    public void update1Element(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
      c.setZipCode(a.getZipCode());
    }
    
    public void callRoutine1(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.enforceRightAddressXRecipientLocationCityMappingConditions(rRoot, r, l, c);
    }
  }
  
  public EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_L2R.EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_L2R.RoutinesFacade(getExecutionState(), this);
    this.aRoot = aRoot;this.rRoot = rRoot;this.a = a;this.r = r;this.l = l;this.c = c;
  }
  
  private Addresses aRoot;
  
  private Recipients rRoot;
  
  private Address a;
  
  private Recipient r;
  
  private Location l;
  
  private City c;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine EnforceAddressXRecipientLocationCityMappingConditionsFromLeftToRightRoutine with input:");
    getLogger().debug("   aRoot: " + this.aRoot);
    getLogger().debug("   rRoot: " + this.rRoot);
    getLogger().debug("   a: " + this.a);
    getLogger().debug("   r: " + this.r);
    getLogger().debug("   l: " + this.l);
    getLogger().debug("   c: " + this.c);
    
    userExecution.callRoutine1(aRoot, rRoot, a, r, l, c, actionsFacade);
    
    // val updatedElement userExecution.getElement1(aRoot, rRoot, a, r, l, c);
    userExecution.update0Element(aRoot, rRoot, a, r, l, c);
    
    // val updatedElement userExecution.getElement2(aRoot, rRoot, a, r, l, c);
    userExecution.update1Element(aRoot, rRoot, a, r, l, c);
    
    postprocessElements();
    
    return true;
  }
}
