package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.PCM2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;

@SuppressWarnings("all")
public class ChangeTypeOfInnerDeclarationEffect extends AbstractEffectRealization {
  public ChangeTypeOfInnerDeclarationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private UpdateSingleValuedNonContainmentEReference<DataType> change;
  
  private boolean isChangeSet;
  
  public void setChange(final UpdateSingleValuedNonContainmentEReference<DataType> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getCorrepondenceSourceNewJavaDataType(final UpdateSingleValuedNonContainmentEReference<DataType> change) {
    DataType _newValue = change.getNewValue();
    return _newValue;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect ChangeTypeOfInnerDeclarationEffect with input:");
    getLogger().debug("   UpdateSingleValuedNonContainmentEReference: " + this.change);
    
    org.emftext.language.java.classifiers.Class newJavaDataType = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceNewJavaDataType(change), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,	true);
    preProcessElements();
    new mir.effects.pcm2java.ChangeTypeOfInnerDeclarationEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, newJavaDataType);
    postProcessElements();
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final UpdateSingleValuedNonContainmentEReference<DataType> change, final org.emftext.language.java.classifiers.Class newJavaDataType) {
      EObject _newAffectedEObject = change.getNewAffectedEObject();
      final InnerDeclaration innerDeclaration = ((InnerDeclaration) _newAffectedEObject);
      DataType _newValue = change.getNewValue();
      final TypeReference newDataTypeReference = PCM2JavaHelper.createTypeReference(_newValue, newJavaDataType);
      this.effectFacade.callChangeInnerDeclarationType(innerDeclaration, newDataTypeReference);
    }
  }
}
