package mir.routines.packageMappingIntegration;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.OperationInterface;

@SuppressWarnings("all")
public class AddMethodEventEffect extends AbstractEffectRealization {
  public AddMethodEventEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<ConcreteClassifier, Member> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<ConcreteClassifier, Member> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddMethodEventEffect with input:");
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
    new mir.routines.packageMappingIntegration.AddMethodEventEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, opInterface);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOpInterface(final InsertEReference<ConcreteClassifier, Member> change) {
    ConcreteClassifier _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<ConcreteClassifier, Member> change, final OperationInterface opInterface) {
      Member _newValue = change.getNewValue();
      this.effectFacade.callCreateOperationSignature(opInterface, ((Method) _newValue));
    }
  }
}
