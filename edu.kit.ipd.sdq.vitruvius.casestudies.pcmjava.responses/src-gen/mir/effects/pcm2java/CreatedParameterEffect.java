package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.impl.ParametersFactoryImpl;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Parameter;

@SuppressWarnings("all")
public class CreatedParameterEffect extends AbstractEffectRealization {
  public CreatedParameterEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateNonRootEObjectInList<Parameter> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateNonRootEObjectInList<Parameter> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceInterfaceMethod(final CreateNonRootEObjectInList<Parameter> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  private EObject getCorrepondenceSourceJavaParameter(final CreateNonRootEObjectInList<Parameter> change) {
    Parameter _newValue = change.getNewValue();
    return _newValue;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect CreatedParameterEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    InterfaceMethod interfaceMethod = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceInterfaceMethod(change), // correspondence source supplier
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	InterfaceMethod.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    OrdinaryParameter javaParameter = initializeCreateElementState(
    	() -> getCorrepondenceSourceJavaParameter(change), // correspondence source supplier
    	() -> ParametersFactoryImpl.eINSTANCE.createOrdinaryParameter(), // element creation supplier
    	() -> null, // tag supplier
    	OrdinaryParameter.class);
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.pcm2java.CreatedParameterEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, interfaceMethod, javaParameter);
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
    
    private void executeUserOperations(final CreateNonRootEObjectInList<Parameter> change, final InterfaceMethod interfaceMethod, final OrdinaryParameter javaParameter) {
      Parameter _newValue = change.getNewValue();
      String _parameterName = _newValue.getParameterName();
      javaParameter.setName(_parameterName);
      Parameter _newValue_1 = change.getNewValue();
      DataType _dataType__Parameter = _newValue_1.getDataType__Parameter();
      this.effectFacade.callChangeParameterType(javaParameter, _dataType__Parameter);
      EList<org.emftext.language.java.parameters.Parameter> _parameters = interfaceMethod.getParameters();
      _parameters.add(javaParameter);
    }
  }
}
