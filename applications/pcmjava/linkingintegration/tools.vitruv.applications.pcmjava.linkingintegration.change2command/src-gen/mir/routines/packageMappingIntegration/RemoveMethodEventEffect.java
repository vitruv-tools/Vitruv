package mir.routines.packageMappingIntegration;

import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Member;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class RemoveMethodEventEffect extends AbstractEffectRealization {
  public RemoveMethodEventEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RemoveEReference<ConcreteClassifier, Member> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private RemoveEReference<ConcreteClassifier, Member> change;
  
  private EObject getElement0(final RemoveEReference<ConcreteClassifier, Member> change, final OperationSignature opSig) {
    return opSig;
  }
  
  private EObject getElement1(final RemoveEReference<ConcreteClassifier, Member> change, final OperationSignature opSig) {
    Member _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveMethodEventEffect with input:");
    getLogger().debug("   RemoveEReference: " + this.change);
    
    OperationSignature opSig = getCorrespondingElement(
    	getCorrepondenceSourceOpSig(change), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (opSig == null) {
    	return;
    }
    initializeRetrieveElementState(opSig);
    
    removeCorrespondenceBetween(getElement0(change, opSig), getElement1(change, opSig));
    preprocessElementStates();
    new mir.routines.packageMappingIntegration.RemoveMethodEventEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, opSig);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOpSig(final RemoveEReference<ConcreteClassifier, Member> change) {
    Member _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final RemoveEReference<ConcreteClassifier, Member> change, final OperationSignature opSig) {
      EcoreUtil.remove(opSig);
    }
  }
}
