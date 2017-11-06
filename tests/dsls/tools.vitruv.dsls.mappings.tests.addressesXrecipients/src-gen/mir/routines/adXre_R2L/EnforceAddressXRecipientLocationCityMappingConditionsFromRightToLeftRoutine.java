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
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class EnforceAddressXRecipientLocationCityMappingConditionsFromRightToLeftRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private EnforceAddressXRecipientLocationCityMappingConditionsFromRightToLeftRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
      return a;
    }
    
    public void update0Element(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
      a.setNumber(l.getNumber());
      a.setStreet(l.getStreet());
      a.setZipCode(c.getZipCode());
    }
    
    public void callRoutine1(final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.enforceLeftAddressXRecipientLocationCityMappingConditions(aRoot, a);
    }
  }
  
  public EnforceAddressXRecipientLocationCityMappingConditionsFromRightToLeftRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses aRoot, final Recipients rRoot, final Address a, final Recipient r, final Location l, final City c) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_R2L.EnforceAddressXRecipientLocationCityMappingConditionsFromRightToLeftRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_R2L.RoutinesFacade(getExecutionState(), this);
    this.aRoot = aRoot;this.rRoot = rRoot;this.a = a;this.r = r;this.l = l;this.c = c;
  }
  
  private Addresses aRoot;
  
  private Recipients rRoot;
  
  private Address a;
  
  private Recipient r;
  
  private Location l;
  
  private City c;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine EnforceAddressXRecipientLocationCityMappingConditionsFromRightToLeftRoutine with input:");
    getLogger().debug("   aRoot: " + this.aRoot);
    getLogger().debug("   rRoot: " + this.rRoot);
    getLogger().debug("   a: " + this.a);
    getLogger().debug("   r: " + this.r);
    getLogger().debug("   l: " + this.l);
    getLogger().debug("   c: " + this.c);
    
    userExecution.callRoutine1(aRoot, rRoot, a, r, l, c, actionsFacade);
    
    // val updatedElement userExecution.getElement1(aRoot, rRoot, a, r, l, c);
    userExecution.update0Element(aRoot, rRoot, a, r, l, c);
    
    postprocessElements();
    
    return true;
  }
}
