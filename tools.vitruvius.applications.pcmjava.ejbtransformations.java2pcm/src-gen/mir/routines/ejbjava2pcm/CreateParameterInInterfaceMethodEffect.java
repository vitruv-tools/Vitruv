package mir.routines.ejbjava2pcm;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.Parameter;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class CreateParameterInInterfaceMethodEffect extends AbstractEffectRealization {
  public CreateParameterInInterfaceMethodEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<InterfaceMethod, Parameter> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<InterfaceMethod, Parameter> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateParameterInInterfaceMethodEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    OperationSignature opSignature = getCorrespondingElement(
    	getCorrepondenceSourceOpSignature(change), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (opSignature == null) {
    	return;
    }
    initializeRetrieveElementState(opSignature);
    
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreateParameterInInterfaceMethodEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, opSignature);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOpSignature(final InsertEReference<InterfaceMethod, Parameter> change) {
    InterfaceMethod _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<InterfaceMethod, Parameter> change, final OperationSignature opSignature) {
      Parameter _newValue = change.getNewValue();
      this.effectFacade.callCreatePCMParameter(_newValue, opSignature);
    }
  }
}
