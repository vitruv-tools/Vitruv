package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameMethodForResourceDemandingBehaviorRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameMethodForResourceDemandingBehaviorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ResourceDemandingInternalBehaviour behavior, final ClassMethod javaMethod) {
      return javaMethod;
    }
    
    public void update0Element(final ResourceDemandingInternalBehaviour behavior, final ClassMethod javaMethod) {
      String _entityName = behavior.getEntityName();
      javaMethod.setName(_entityName);
    }
    
    public EObject getCorrepondenceSourceJavaMethod(final ResourceDemandingInternalBehaviour behavior) {
      return behavior;
    }
  }
  
  public RenameMethodForResourceDemandingBehaviorRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ResourceDemandingInternalBehaviour behavior) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.RenameMethodForResourceDemandingBehaviorRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.behavior = behavior;
  }
  
  private ResourceDemandingInternalBehaviour behavior;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameMethodForResourceDemandingBehaviorRoutine with input:");
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
    // val updatedElement userExecution.getElement1(behavior, javaMethod);
    userExecution.update0Element(behavior, javaMethod);
    
    postprocessElementStates();
  }
}
