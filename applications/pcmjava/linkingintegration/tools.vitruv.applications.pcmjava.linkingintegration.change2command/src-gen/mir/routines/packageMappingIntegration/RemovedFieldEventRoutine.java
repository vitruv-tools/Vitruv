package mir.routines.packageMappingIntegration;

import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class RemovedFieldEventRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemovedFieldEventRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final Field field, final NamedElement namedElement) {
      return field;
    }
    
    public void update0Element(final Field field, final NamedElement namedElement) {
      this.userInteracting.showMessage(UserInteractionType.MODAL, (((("Removed " + namedElement) + " because the corresponding field ") + field) + " has been removed"));
      EcoreUtil.remove(field);
    }
    
    public EObject getCorrepondenceSourceNamedElement(final Field field) {
      return field;
    }
    
    public EObject getElement2(final Field field, final NamedElement namedElement) {
      return namedElement;
    }
    
    public EObject getElement3(final Field field, final NamedElement namedElement) {
      return field;
    }
  }
  
  public RemovedFieldEventRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Field field) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.RemovedFieldEventRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.field = field;
  }
  
  private Field field;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemovedFieldEventRoutine with input:");
    getLogger().debug("   Field: " + this.field);
    
    NamedElement namedElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceNamedElement(field), // correspondence source supplier
    	NamedElement.class,
    	(NamedElement _element) -> true, // correspondence precondition checker
    	null);
    if (namedElement == null) {
    	return;
    }
    initializeRetrieveElementState(namedElement);
    removeCorrespondenceBetween(userExecution.getElement1(field, namedElement), userExecution.getElement2(field, namedElement));
    
    // val updatedElement userExecution.getElement3(field, namedElement);
    userExecution.update0Element(field, namedElement);
    
    postprocessElementStates();
  }
}
