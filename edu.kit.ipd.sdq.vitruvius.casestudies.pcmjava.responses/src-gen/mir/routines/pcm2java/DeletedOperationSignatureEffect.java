package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class DeletedOperationSignatureEffect extends AbstractEffectRealization {
  public DeletedOperationSignatureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final DeleteNonRootEObjectInList<OperationSignature> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private DeleteNonRootEObjectInList<OperationSignature> change;
  
  private EObject getElement0(final DeleteNonRootEObjectInList<OperationSignature> change, final InterfaceMethod interfaceMethod) {
    return interfaceMethod;
  }
  
  private EObject getCorrepondenceSourceInterfaceMethod(final DeleteNonRootEObjectInList<OperationSignature> change) {
    OperationSignature _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeletedOperationSignatureEffect with input:");
    getLogger().debug("   DeleteNonRootEObjectInList: " + this.change);
    
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
