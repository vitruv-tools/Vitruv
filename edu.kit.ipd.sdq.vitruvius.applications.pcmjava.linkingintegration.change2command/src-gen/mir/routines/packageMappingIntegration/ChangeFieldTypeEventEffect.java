package mir.routines.packageMappingIntegration;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteractionType;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

@SuppressWarnings("all")
public class ChangeFieldTypeEventEffect extends AbstractEffectRealization {
  public ChangeFieldTypeEventEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEReference<Field, TypeReference> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEReference<Field, TypeReference> change;
  
  private EObject getCorrepondenceSourceBasicComponent(final ReplaceSingleValuedEReference<Field, TypeReference> change) {
    Field _affectedEObject = change.getAffectedEObject();
    ConcreteClassifier _containingConcreteClassifier = _affectedEObject.getContainingConcreteClassifier();
    return _containingConcreteClassifier;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeFieldTypeEventEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEReference: " + this.change);
    
    OperationInterface oldCorrespondingOpInterface = getCorrespondingElement(
    	getCorrepondenceSourceOldCorrespondingOpInterface(change), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(oldCorrespondingOpInterface);
    OperationInterface opInterface = getCorrespondingElement(
    	getCorrepondenceSourceOpInterface(change), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(opInterface);
    OperationRequiredRole opRequiredRole = getCorrespondingElement(
    	getCorrepondenceSourceOpRequiredRole(change), // correspondence source supplier
    	OperationRequiredRole.class,
    	(OperationRequiredRole _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(opRequiredRole);
    BasicComponent basicComponent = getCorrespondingElement(
    	getCorrepondenceSourceBasicComponent(change), // correspondence source supplier
    	BasicComponent.class,
    	(BasicComponent _element) -> true, // correspondence precondition checker
    	null);
    if (basicComponent == null) {
    	return;
    }
    initializeRetrieveElementState(basicComponent);
    
    preprocessElementStates();
    new mir.routines.packageMappingIntegration.ChangeFieldTypeEventEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, oldCorrespondingOpInterface, opInterface, opRequiredRole, basicComponent);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOldCorrespondingOpInterface(final ReplaceSingleValuedEReference<Field, TypeReference> change) {
    TypeReference _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  private EObject getCorrepondenceSourceOpRequiredRole(final ReplaceSingleValuedEReference<Field, TypeReference> change) {
    Field _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private EObject getCorrepondenceSourceOpInterface(final ReplaceSingleValuedEReference<Field, TypeReference> change) {
    TypeReference _newValue = change.getNewValue();
    return _newValue;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEReference<Field, TypeReference> change, final OperationInterface oldCorrespondingOpInterface, final OperationInterface opInterface, final OperationRequiredRole opRequiredRole, final BasicComponent basicComponent) {
      if ((((!Objects.equal(null, oldCorrespondingOpInterface)) && (!Objects.equal(null, opInterface))) && (!Objects.equal(null, opRequiredRole)))) {
        this.userInteracting.showMessage(UserInteractionType.MODAL, "the operation required role has been changed");
        opRequiredRole.setRequiredInterface__OperationRequiredRole(opInterface);
        return;
      }
      if ((Objects.equal(null, oldCorrespondingOpInterface) && (!Objects.equal(null, opInterface)))) {
        String _entityName = basicComponent.getEntityName();
        String _plus = ("Create OperationRequiredRole between Component " + _entityName);
        String _plus_1 = (_plus + " and Interface ");
        String _entityName_1 = opInterface.getEntityName();
        String _plus_2 = (_plus_1 + _entityName_1);
        this.userInteracting.showMessage(UserInteractionType.MODAL, _plus_2);
        Field _affectedEObject = change.getAffectedEObject();
        this.effectFacade.callCreateRequiredRole(basicComponent, opInterface, _affectedEObject);
        return;
      }
      if ((((!Objects.equal(null, oldCorrespondingOpInterface)) && Objects.equal(null, opInterface)) && (!Objects.equal(null, opRequiredRole)))) {
        String _entityName_2 = basicComponent.getEntityName();
        String _plus_3 = ("Remove OperationRequiredRole between Component " + _entityName_2);
        String _plus_4 = (_plus_3 + " and Interface ");
        String _entityName_3 = oldCorrespondingOpInterface.getEntityName();
        String _plus_5 = (_plus_4 + _entityName_3);
        this.userInteracting.showMessage(UserInteractionType.MODAL, _plus_5);
        Field _affectedEObject_1 = change.getAffectedEObject();
        this.effectFacade.callRemoveRequiredRoleAndCorrespondence(opRequiredRole, _affectedEObject_1);
      }
    }
  }
}
