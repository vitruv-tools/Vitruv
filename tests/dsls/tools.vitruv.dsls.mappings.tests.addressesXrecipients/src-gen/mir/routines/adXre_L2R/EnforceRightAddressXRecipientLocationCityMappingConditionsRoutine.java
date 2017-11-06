package mir.routines.adXre_L2R;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.metamodels.recipients.City;
import edu.kit.ipd.sdq.metamodels.recipients.Location;
import edu.kit.ipd.sdq.metamodels.recipients.Recipient;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.adXre_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return rRoot;
    }
    
    public void update0Element(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      rRoot.getRecipients().add(r);
    }
    
    public EObject getElement4(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return c;
    }
    
    public EObject getElement2(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return r;
    }
    
    public EObject getElement3(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      return l;
    }
    
    public void update3Element(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      String _zipCode = c.getZipCode();
      boolean _tripleEquals = (_zipCode == null);
      if (_tripleEquals) {
        String _zipCode_1 = c.getZipCode();
        /* Objects.equal(_zipCode_1, ""); */
      }
    }
    
    public void update2Element(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      int _number = l.getNumber();
      boolean _lessEqualsThan = (_number <= 0);
      if (_lessEqualsThan) {
        l.setNumber(0);
      }
    }
    
    public void update1Element(final Recipients rRoot, final Recipient r, final Location l, final City c) {
      r.setBusiness(true);
      r.setLocatedAt(l);
      r.setLocatedIn(c);
    }
  }
  
  public EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients rRoot, final Recipient r, final Location l, final City c) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_L2R.EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_L2R.RoutinesFacade(getExecutionState(), this);
    this.rRoot = rRoot;this.r = r;this.l = l;this.c = c;
  }
  
  private Recipients rRoot;
  
  private Recipient r;
  
  private Location l;
  
  private City c;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine EnforceRightAddressXRecipientLocationCityMappingConditionsRoutine with input:");
    getLogger().debug("   rRoot: " + this.rRoot);
    getLogger().debug("   r: " + this.r);
    getLogger().debug("   l: " + this.l);
    getLogger().debug("   c: " + this.c);
    
    // val updatedElement userExecution.getElement1(rRoot, r, l, c);
    userExecution.update0Element(rRoot, r, l, c);
    
    // val updatedElement userExecution.getElement2(rRoot, r, l, c);
    userExecution.update1Element(rRoot, r, l, c);
    
    // val updatedElement userExecution.getElement3(rRoot, r, l, c);
    userExecution.update2Element(rRoot, r, l, c);
    
    // val updatedElement userExecution.getElement4(rRoot, r, l, c);
    userExecution.update3Element(rRoot, r, l, c);
    
    postprocessElements();
    
    return true;
  }
}
