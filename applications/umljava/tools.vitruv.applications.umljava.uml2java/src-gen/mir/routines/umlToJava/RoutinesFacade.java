package mir.routines.umlToJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createJavaClass(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJava.CreateJavaClassRoutine effect = new mir.routines.umlToJava.CreateJavaClassRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
}
