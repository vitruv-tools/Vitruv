package mir.routines.packageMappingIntegration;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
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
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class ChangedMethodModifierEventRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private ChangedMethodModifierEventRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceOperationSignature(final Method method, final AnnotationInstanceOrModifier annotationOrModifier) {
      return method;
    }
    
    public EObject getCorrepondenceSourceOperationInterface(final Method method, final AnnotationInstanceOrModifier annotationOrModifier, final OperationSignature operationSignature) {
      ConcreteClassifier _containingConcreteClassifier = method.getContainingConcreteClassifier();
      return _containingConcreteClassifier;
    }
    
    public void callRoutine1(final Method method, final AnnotationInstanceOrModifier annotationOrModifier, final OperationSignature operationSignature, final OperationInterface operationInterface, @Extension final RoutinesFacade _routinesFacade) {
      if ((annotationOrModifier instanceof Public)) {
        _routinesFacade.createOperationSignature(operationInterface, method);
        return;
      } else {
        if (((!Objects.equal(null, operationSignature)) && ((annotationOrModifier instanceof Protected) || (annotationOrModifier instanceof Private)))) {
          String _entityName = operationSignature.getEntityName();
          String _plus = ("Public method with correspondence has been made private. \r\n\t\t\t\t\tThe corresponding operaitonSignature " + _entityName);
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
  
  public ChangedMethodModifierEventRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Method method, final AnnotationInstanceOrModifier annotationOrModifier) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.ChangedMethodModifierEventRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.method = method;this.annotationOrModifier = annotationOrModifier;
  }
  
  private Method method;
  
  private AnnotationInstanceOrModifier annotationOrModifier;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangedMethodModifierEventRoutine with input:");
    getLogger().debug("   Method: " + this.method);
    getLogger().debug("   AnnotationInstanceOrModifier: " + this.annotationOrModifier);
    
    OperationSignature operationSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationSignature(method, annotationOrModifier), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(operationSignature);
    OperationInterface operationInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationInterface(method, annotationOrModifier, operationSignature), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    if (operationInterface == null) {
    	return;
    }
    initializeRetrieveElementState(operationInterface);
    userExecution.callRoutine1(method, annotationOrModifier, operationSignature, operationInterface, effectFacade);
    
    postprocessElementStates();
  }
}
