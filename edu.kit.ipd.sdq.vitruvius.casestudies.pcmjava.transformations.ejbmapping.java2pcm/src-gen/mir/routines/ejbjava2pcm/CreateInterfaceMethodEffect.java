package mir.routines.ejbjava2pcm;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.InsertEReference;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.palladiosimulator.pcm.repository.OperationInterface;

@SuppressWarnings("all")
public class CreateInterfaceMethodEffect extends AbstractEffectRealization {
  public CreateInterfaceMethodEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<Interface, Member> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<Interface, Member> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInterfaceMethodEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    OperationInterface opInterface = getCorrespondingElement(
    	getCorrepondenceSourceOpInterface(change), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    if (opInterface == null) {
    	return;
    }
    initializeRetrieveElementState(opInterface);
    
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreateInterfaceMethodEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, opInterface);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOpInterface(final InsertEReference<Interface, Member> change) {
    Interface _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<Interface, Member> change, final OperationInterface opInterface) {
      Member _newValue = change.getNewValue();
      this.effectFacade.callCreateOperationSignature(((InterfaceMethod) _newValue), opInterface);
    }
  }
}
