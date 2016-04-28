package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.palladiosimulator.pcm.repository.Parameter;

@SuppressWarnings("all")
public class RemovedParameterEffect extends AbstractEffectRealization {
  public RemovedParameterEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private DeleteNonRootEObjectInList<Parameter> change;
  
  private boolean isChangeSet;
  
  public void setChange(final DeleteNonRootEObjectInList<Parameter> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getElement0(final DeleteNonRootEObjectInList<Parameter> change, final InterfaceMethod interfaceMethod, final OrdinaryParameter javaParameter) {
    return javaParameter;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceInterfaceMethod(final DeleteNonRootEObjectInList<Parameter> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  private EObject getCorrepondenceSourceJavaParameter(final DeleteNonRootEObjectInList<Parameter> change) {
    Parameter _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine RemovedParameterEffect with input:");
    getLogger().debug("   DeleteNonRootEObjectInList: " + this.change);
    
    InterfaceMethod interfaceMethod = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceInterfaceMethod(change), // correspondence source supplier
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	InterfaceMethod.class,
    	false, true, false);
    OrdinaryParameter javaParameter = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaParameter(change), // correspondence source supplier
    	(OrdinaryParameter _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	OrdinaryParameter.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    markObjectDelete(getElement0(change, interfaceMethod, javaParameter));
    
    preProcessElements();
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
