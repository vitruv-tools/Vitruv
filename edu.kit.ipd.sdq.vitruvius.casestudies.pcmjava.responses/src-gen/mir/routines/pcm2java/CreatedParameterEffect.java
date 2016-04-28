package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
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
  
  private EObject getElement0(final CreateNonRootEObjectInList<Parameter> change, final InterfaceMethod interfaceMethod, final OrdinaryParameter javaParameter) {
    return javaParameter;
  }
  
  private EObject getElement1(final CreateNonRootEObjectInList<Parameter> change, final InterfaceMethod interfaceMethod, final OrdinaryParameter javaParameter) {
    Parameter _newValue = change.getNewValue();
    return _newValue;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceInterfaceMethod(final CreateNonRootEObjectInList<Parameter> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreatedParameterEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    InterfaceMethod interfaceMethod = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceInterfaceMethod(change), // correspondence source supplier
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	InterfaceMethod.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    OrdinaryParameter javaParameter = ParametersFactoryImpl.eINSTANCE.createOrdinaryParameter();
    initializeCreateElementState(javaParameter);
    
    addCorrespondenceBetween(getElement0(change, interfaceMethod, javaParameter), getElement1(change, interfaceMethod, javaParameter), "");
    preProcessElements();
    new mir.routines.pcm2java.CreatedParameterEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, interfaceMethod, javaParameter);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
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
