package mir.routines.packageMappingIntegration;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import java.io.IOException;
import java.util.HashSet;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.modifiers.Private;
import org.emftext.language.java.modifiers.Protected;
import org.emftext.language.java.modifiers.Public;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class ChangeMethodModifierEventEffect extends AbstractEffectRealization {
  public ChangeMethodModifierEventEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<Method, AnnotationInstanceOrModifier> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<Method, AnnotationInstanceOrModifier> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeMethodModifierEventEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    OperationSignature operationSignature = getCorrespondingElement(
    	getCorrepondenceSourceOperationSignature(change), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(operationSignature);
    OperationInterface operationInterface = getCorrespondingElement(
    	getCorrepondenceSourceOperationInterface(change), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    if (operationInterface == null) {
    	return;
    }
    initializeRetrieveElementState(operationInterface);
    
    preprocessElementStates();
    new mir.routines.packageMappingIntegration.ChangeMethodModifierEventEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, operationSignature, operationInterface);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOperationSignature(final InsertEReference<Method, AnnotationInstanceOrModifier> change) {
    Method _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private EObject getCorrepondenceSourceOperationInterface(final InsertEReference<Method, AnnotationInstanceOrModifier> change) {
    Method _affectedEObject = change.getAffectedEObject();
    ConcreteClassifier _containingConcreteClassifier = _affectedEObject.getContainingConcreteClassifier();
    return _containingConcreteClassifier;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<Method, AnnotationInstanceOrModifier> change, final OperationSignature operationSignature, final OperationInterface operationInterface) {
      AnnotationInstanceOrModifier _newValue = change.getNewValue();
      if ((_newValue instanceof Public)) {
        Method _affectedEObject = change.getAffectedEObject();
        this.effectFacade.callCreateOperationSignature(operationInterface, _affectedEObject);
        return;
      } else {
        if (((!Objects.equal(null, operationSignature)) && ((change.getNewValue() instanceof Protected) || (change.getNewValue() instanceof Private)))) {
          String _entityName = operationSignature.getEntityName();
          String _plus = ("Public method with correspondence has been made private. \r\n\t\t\t\tThe corresponding operaitonSignature " + _entityName);
          String _plus_1 = (_plus + " will be deleted as well.");
          this.userInteracting.showMessage(UserInteractionType.MODAL, _plus_1);
          HashSet<EObject> _newHashSet = Sets.<EObject>newHashSet(operationSignature);
          this.correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(_newHashSet);
          EcoreUtil.remove(operationSignature);
          return;
        }
      }
    }
  }
}
