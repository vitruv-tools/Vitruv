package mir.routines.packageMappingIntegration;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Member;
import org.palladiosimulator.pcm.core.entity.NamedElement;

@SuppressWarnings("all")
public class RemoveFieldEventEffect extends AbstractEffectRealization {
  public RemoveFieldEventEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RemoveEReference<ConcreteClassifier, Member> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private RemoveEReference<ConcreteClassifier, Member> change;
  
  private EObject getElement0(final RemoveEReference<ConcreteClassifier, Member> change, final NamedElement nameElement) {
    Member _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  private EObject getElement1(final RemoveEReference<ConcreteClassifier, Member> change, final NamedElement nameElement) {
    return nameElement;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveFieldEventEffect with input:");
    getLogger().debug("   RemoveEReference: " + this.change);
    
    NamedElement nameElement = getCorrespondingElement(
    	getCorrepondenceSourceNameElement(change), // correspondence source supplier
    	NamedElement.class,
    	(NamedElement _element) -> true, // correspondence precondition checker
    	null);
    if (nameElement == null) {
    	return;
    }
    initializeRetrieveElementState(nameElement);
    
    removeCorrespondenceBetween(getElement0(change, nameElement), getElement1(change, nameElement));
    preprocessElementStates();
    new mir.routines.packageMappingIntegration.RemoveFieldEventEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, nameElement);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceNameElement(final RemoveEReference<ConcreteClassifier, Member> change) {
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
    
    private void executeUserOperations(final RemoveEReference<ConcreteClassifier, Member> change, final NamedElement nameElement) {
      Member _oldValue = change.getOldValue();
      String _plus = ((("Removed " + nameElement) + " because the corresponding field ") + _oldValue);
      String _plus_1 = (_plus + " has been removed");
      this.userInteracting.showMessage(UserInteractionType.MODAL, _plus_1);
      Member _oldValue_1 = change.getOldValue();
      EcoreUtil.remove(_oldValue_1);
    }
  }
}
