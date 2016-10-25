package mir.routines.parserIntegrationReaction;

import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Method;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void addedMethodEventParser(final ConcreteClassifier clazz, final Method method) {
    mir.routines.parserIntegrationReaction.AddedMethodEventParserRoutine effect = new mir.routines.parserIntegrationReaction.AddedMethodEventParserRoutine(this.executionState, calledBy,
    	clazz, method);
    effect.applyRoutine();
  }
}
