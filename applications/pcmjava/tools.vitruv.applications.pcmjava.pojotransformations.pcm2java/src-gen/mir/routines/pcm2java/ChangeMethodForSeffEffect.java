package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeMethodForSeffEffect extends AbstractEffectRealization {
  public ChangeMethodForSeffEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ResourceDemandingSEFF seff) {
    super(responseExecutionState, calledBy);
    				this.seff = seff;
  }
  
  private ResourceDemandingSEFF seff;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    @Extension
    private RoutinesFacade effectFacade;
    
    private void executeUserOperations(final ResourceDemandingSEFF seff, final ClassMethod oldClassMethod) {
      this.effectFacade.callCreateSEFF(seff);
    }
  }
  
  private EObject getElement0(final ResourceDemandingSEFF seff, final ClassMethod oldClassMethod) {
    return oldClassMethod;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeMethodForSeffEffect with input:");
    getLogger().debug("   ResourceDemandingSEFF: " + this.seff);
    
    ClassMethod oldClassMethod = getCorrespondingElement(
    	getCorrepondenceSourceOldClassMethod(seff), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(oldClassMethod);
    deleteObject(getElement0(seff, oldClassMethod));
    
    preprocessElementStates();
    new mir.routines.pcm2java.ChangeMethodForSeffEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	seff, oldClassMethod);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOldClassMethod(final ResourceDemandingSEFF seff) {
    return seff;
  }
}
