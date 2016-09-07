package mir.routines.parserIntegrationResponse;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruvius.framework.correspondence.CorrespondenceModelUtil;
import java.io.IOException;
import mir.routines.parserIntegrationResponse.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Member;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

@SuppressWarnings("all")
public class AddMethodEventParserEffect extends AbstractEffectRealization {
  public AddMethodEventParserEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<ConcreteClassifier, Member> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<ConcreteClassifier, Member> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddMethodEventParserEffect with input:");
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
    new mir.routines.parserIntegrationResponse.AddMethodEventParserEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
      this.effectFacade = new mir.routines.parserIntegrationResponse.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<ConcreteClassifier, Member> change, final OperationInterface opInterface) {
      OperationSignature opSig = RepositoryFactory.eINSTANCE.createOperationSignature();
      Member _newValue = change.getNewValue();
      String _name = _newValue.getName();
      opSig.setEntityName(_name);
      opSig.setInterface__OperationSignature(opInterface);
      Member _newValue_1 = change.getNewValue();
      CorrespondenceModelUtil.createAndAddCorrespondence(this.correspondenceModel, opSig, _newValue_1);
    }
  }
}
