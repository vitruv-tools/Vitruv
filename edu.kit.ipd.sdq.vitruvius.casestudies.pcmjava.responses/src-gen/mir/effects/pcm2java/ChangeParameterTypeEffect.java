package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;

@SuppressWarnings("all")
public class ChangeParameterTypeEffect extends AbstractEffectRealization {
  public ChangeParameterTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private OrdinaryParameter javaParameter;
  
  private DataType parameterType;
  
  private boolean isJavaParameterSet;
  
  private boolean isParameterTypeSet;
  
  public void setJavaParameter(final OrdinaryParameter javaParameter) {
    this.javaParameter = javaParameter;
    this.isJavaParameterSet = true;
  }
  
  public void setParameterType(final DataType parameterType) {
    this.parameterType = parameterType;
    this.isParameterTypeSet = true;
  }
  
  public boolean allParametersSet() {
    return isJavaParameterSet&&isParameterTypeSet;
  }
  
  private EObject getCorrepondenceSourceJavaParameterTypeClass(final OrdinaryParameter javaParameter, final DataType parameterType) {
    return parameterType;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect ChangeParameterTypeEffect with input:");
    getLogger().debug("   OrdinaryParameter: " + this.javaParameter);
    getLogger().debug("   DataType: " + this.parameterType);
    
    org.emftext.language.java.classifiers.Class javaParameterTypeClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaParameterTypeClass(javaParameter, parameterType), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,
    	CorrespondenceFailHandlerFactory.createDoNothingHandler(false));
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.pcm2java.ChangeParameterTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	javaParameter, parameterType, javaParameterTypeClass);
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
    
    private void executeUserOperations(final OrdinaryParameter javaParameter, final DataType parameterType, final org.emftext.language.java.classifiers.Class javaParameterTypeClass) {
      final TypeReference parameterTypeReference = Pcm2JavaHelper.createTypeReference(parameterType, javaParameterTypeClass);
      javaParameter.setTypeReference(parameterTypeReference);
    }
  }
}
