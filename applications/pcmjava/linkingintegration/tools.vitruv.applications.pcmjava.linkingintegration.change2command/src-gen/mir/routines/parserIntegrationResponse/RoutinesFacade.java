package mir.routines.parserIntegrationResponse;

import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Method;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  public void addedMethodEventParser(final ConcreteClassifier clazz, final Method method) {
    mir.routines.parserIntegrationResponse.AddedMethodEventParserRoutine effect = new mir.routines.parserIntegrationResponse.AddedMethodEventParserRoutine(this.executionState, calledBy,
    	clazz, method);
    effect.applyRoutine();
  }
}
