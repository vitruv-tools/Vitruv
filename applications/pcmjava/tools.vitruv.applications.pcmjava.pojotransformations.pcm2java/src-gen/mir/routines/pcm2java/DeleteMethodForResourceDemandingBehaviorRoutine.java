package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteMethodForResourceDemandingBehaviorRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private DeleteMethodForResourceDemandingBehaviorRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final ResourceDemandingInternalBehaviour behavior, final ClassMethod javaMethod) {
      return javaMethod;
    }
    
    public EObject getCorrepondenceSourceJavaMethod(final ResourceDemandingInternalBehaviour behavior) {
      return behavior;
    }
  }
  
  public DeleteMethodForResourceDemandingBehaviorRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ResourceDemandingInternalBehaviour behavior) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.DeleteMethodForResourceDemandingBehaviorRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.behavior = behavior;
  }
  
  private ResourceDemandingInternalBehaviour behavior;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteMethodForResourceDemandingBehaviorRoutine with input:");
    getLogger().debug("   ResourceDemandingInternalBehaviour: " + this.behavior);
    
    ClassMethod javaMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaMethod(behavior), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    if (javaMethod == null) {
    	return;
    }
    initializeRetrieveElementState(javaMethod);
    deleteObject(userExecution.getElement1(behavior, javaMethod));
    
    postprocessElementStates();
  }
}
