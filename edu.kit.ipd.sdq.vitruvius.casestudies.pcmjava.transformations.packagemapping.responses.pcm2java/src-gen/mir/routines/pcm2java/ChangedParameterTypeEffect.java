package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReplaceSingleValuedEReference;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Parameter;

@SuppressWarnings("all")
public class ChangedParameterTypeEffect extends AbstractEffectRealization {
  public ChangedParameterTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEReference<Parameter, DataType> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEReference<Parameter, DataType> change;
  
  private EObject getCorrepondenceSourceInterfaceMethod(final ReplaceSingleValuedEReference<Parameter, DataType> change) {
    Parameter _affectedEObject = change.getAffectedEObject();
    EObject _eContainer = _affectedEObject.eContainer();
    return _eContainer;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangedParameterTypeEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEReference: " + this.change);
    
    InterfaceMethod interfaceMethod = getCorrespondingElement(
    	getCorrepondenceSourceInterfaceMethod(change), // correspondence source supplier
    	InterfaceMethod.class,
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	null);
    if (interfaceMethod == null) {
    	return;
    }
    initializeRetrieveElementState(interfaceMethod);
    OrdinaryParameter javaParameter = getCorrespondingElement(
    	getCorrepondenceSourceJavaParameter(change), // correspondence source supplier
    	OrdinaryParameter.class,
    	(OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null);
    if (javaParameter == null) {
    	return;
    }
    initializeRetrieveElementState(javaParameter);
    
    preprocessElementStates();
    new mir.routines.pcm2java.ChangedParameterTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, interfaceMethod, javaParameter);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceJavaParameter(final ReplaceSingleValuedEReference<Parameter, DataType> change) {
    Parameter _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEReference<Parameter, DataType> change, final InterfaceMethod interfaceMethod, final OrdinaryParameter javaParameter) {
      DataType _newValue = change.getNewValue();
      this.effectFacade.callChangeParameterType(javaParameter, _newValue);
    }
  }
}
