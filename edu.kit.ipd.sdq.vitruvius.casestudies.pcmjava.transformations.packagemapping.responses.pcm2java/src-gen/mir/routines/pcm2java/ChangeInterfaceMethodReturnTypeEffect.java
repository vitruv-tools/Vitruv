package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;

@SuppressWarnings("all")
public class ChangeInterfaceMethodReturnTypeEffect extends AbstractEffectRealization {
  public ChangeInterfaceMethodReturnTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InterfaceMethod interfaceMethod, final DataType returnType) {
    super(responseExecutionState, calledBy);
    				this.interfaceMethod = interfaceMethod;this.returnType = returnType;
  }
  
  private InterfaceMethod interfaceMethod;
  
  private DataType returnType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInterfaceMethodReturnTypeEffect with input:");
    getLogger().debug("   InterfaceMethod: " + this.interfaceMethod);
    getLogger().debug("   DataType: " + this.returnType);
    
    org.emftext.language.java.classifiers.Class returnTypeClass = getCorrespondingElement(
    	getCorrepondenceSourceReturnTypeClass(interfaceMethod, returnType), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(returnTypeClass);
    
    preprocessElementStates();
    new mir.routines.pcm2java.ChangeInterfaceMethodReturnTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	interfaceMethod, returnType, returnTypeClass);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceReturnTypeClass(final InterfaceMethod interfaceMethod, final DataType returnType) {
    return returnType;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InterfaceMethod interfaceMethod, final DataType returnType, final org.emftext.language.java.classifiers.Class returnTypeClass) {
      final TypeReference returnTypeReference = Pcm2JavaHelper.createTypeReference(returnType, returnTypeClass);
      interfaceMethod.setTypeReference(returnTypeReference);
    }
  }
}
