package mir.routines.simpleChangesTests;

import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class TestJavaTypesRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private TestJavaTypesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final SimpleChangesTestsExecutionMonitor monitor, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public TestJavaTypesRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final SimpleChangesTestsExecutionMonitor monitor) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.TestJavaTypesRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.monitor = monitor;
  }
  
  private SimpleChangesTestsExecutionMonitor monitor;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine TestJavaTypesRoutine with input:");
    getLogger().debug("   SimpleChangesTestsExecutionMonitor: " + this.monitor);
    
    userExecution.callRoutine1(monitor, actionsFacade);
    
    postprocessElements();
  }
}
