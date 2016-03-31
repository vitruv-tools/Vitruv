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
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;

@SuppressWarnings("all")
public class ChangeInterfaceMethodReturnTypeEffect extends AbstractEffectRealization {
  public ChangeInterfaceMethodReturnTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private InterfaceMethod interfaceMethod;
  
  private DataType returnType;
  
  private boolean isInterfaceMethodSet;
  
  private boolean isReturnTypeSet;
  
  public void setInterfaceMethod(final InterfaceMethod interfaceMethod) {
    this.interfaceMethod = interfaceMethod;
    this.isInterfaceMethodSet = true;
  }
  
  public void setReturnType(final DataType returnType) {
    this.returnType = returnType;
    this.isReturnTypeSet = true;
  }
  
  public boolean allParametersSet() {
    return isInterfaceMethodSet&&isReturnTypeSet;
  }
  
  private EObject getCorrepondenceSourceReturnTypeClass(final InterfaceMethod interfaceMethod, final DataType returnType) {
    return returnType;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect ChangeInterfaceMethodReturnTypeEffect with input:");
    getLogger().debug("   InterfaceMethod: " + this.interfaceMethod);
    getLogger().debug("   DataType: " + this.returnType);
    
    org.emftext.language.java.classifiers.Class returnTypeClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceReturnTypeClass(interfaceMethod, returnType), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,
    	CorrespondenceFailHandlerFactory.createDoNothingHandler(false));
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.pcm2java.ChangeInterfaceMethodReturnTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	interfaceMethod, returnType, returnTypeClass);
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
    
    private void executeUserOperations(final InterfaceMethod interfaceMethod, final DataType returnType, final org.emftext.language.java.classifiers.Class returnTypeClass) {
      final TypeReference returnTypeReference = Pcm2JavaHelper.createTypeReference(returnType, returnTypeClass);
      interfaceMethod.setTypeReference(returnTypeReference);
    }
  }
}
