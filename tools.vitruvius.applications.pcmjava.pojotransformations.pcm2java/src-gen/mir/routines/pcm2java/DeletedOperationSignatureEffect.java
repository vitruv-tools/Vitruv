package mir.routines.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.RemoveEReference;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class DeletedOperationSignatureEffect extends AbstractEffectRealization {
  public DeletedOperationSignatureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RemoveEReference<OperationInterface, OperationSignature> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private RemoveEReference<OperationInterface, OperationSignature> change;
  
  private EObject getElement0(final RemoveEReference<OperationInterface, OperationSignature> change, final InterfaceMethod interfaceMethod) {
    return interfaceMethod;
  }
  
  private EObject getCorrepondenceSourceInterfaceMethod(final RemoveEReference<OperationInterface, OperationSignature> change) {
    OperationSignature _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeletedOperationSignatureEffect with input:");
    getLogger().debug("   RemoveEReference: " + this.change);
    
    InterfaceMethod interfaceMethod = getCorrespondingElement(
    	getCorrepondenceSourceInterfaceMethod(change), // correspondence source supplier
    	InterfaceMethod.class,
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	null);
    if (interfaceMethod == null) {
    	return;
    }
    initializeRetrieveElementState(interfaceMethod);
    deleteObject(getElement0(change, interfaceMethod));
    
    preprocessElementStates();
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
