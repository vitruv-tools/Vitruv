package mir.routines.adXre_R2L;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.metamodels.addresses.Address;
import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import java.io.IOException;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class EnforceLeftAddressXRecipientLocationCityMappingConditionsRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private EnforceLeftAddressXRecipientLocationCityMappingConditionsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Addresses aRoot, final Address a) {
      return aRoot;
    }
    
    public void update0Element(final Addresses aRoot, final Address a) {
      aRoot.getAddresses().add(a);
    }
    
    public EObject getElement2(final Addresses aRoot, final Address a) {
      return a;
    }
    
    public void update1Element(final Addresses aRoot, final Address a) {
      int _number = a.getNumber();
      boolean _lessEqualsThan = (_number <= 0);
      if (_lessEqualsThan) {
        a.setNumber(0);
      }
      String _zipCode = a.getZipCode();
      boolean _tripleEquals = (_zipCode == null);
      if (_tripleEquals) {
        String _zipCode_1 = a.getZipCode();
        /* Objects.equal(_zipCode_1, ""); */
      }
    }
  }
  
  public EnforceLeftAddressXRecipientLocationCityMappingConditionsRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses aRoot, final Address a) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_R2L.EnforceLeftAddressXRecipientLocationCityMappingConditionsRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_R2L.RoutinesFacade(getExecutionState(), this);
    this.aRoot = aRoot;this.a = a;
  }
  
  private Addresses aRoot;
  
  private Address a;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine EnforceLeftAddressXRecipientLocationCityMappingConditionsRoutine with input:");
    getLogger().debug("   aRoot: " + this.aRoot);
    getLogger().debug("   a: " + this.a);
    
    // val updatedElement userExecution.getElement1(aRoot, a);
    userExecution.update0Element(aRoot, a);
    
    // val updatedElement userExecution.getElement2(aRoot, a);
    userExecution.update1Element(aRoot, a);
    
    postprocessElements();
    
    return true;
  }
}
