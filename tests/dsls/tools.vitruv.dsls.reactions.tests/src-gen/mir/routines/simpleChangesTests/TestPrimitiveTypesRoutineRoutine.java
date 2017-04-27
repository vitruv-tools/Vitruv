package mir.routines.simpleChangesTests;

import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class TestPrimitiveTypesRoutineRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private TestPrimitiveTypesRoutineRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Integer intVal, final Long longVal, final Short shortVal, final Byte byteVal, final Character charVal, final Double doubleVal, final Float floatVal, final Boolean boolVal, final String stringVal, @Extension final RoutinesFacade _routinesFacade) {
      intVal.intValue();
      longVal.longValue();
      shortVal.shortValue();
      byteVal.byteValue();
      charVal.charValue();
      doubleVal.doubleValue();
      floatVal.floatValue();
      boolVal.booleanValue();
      stringVal.charAt(0);
      _routinesFacade.testPrimitiveTypesRoutine(intVal, longVal, shortVal, byteVal, charVal, doubleVal, floatVal, boolVal, stringVal);
    }
  }
  
  public TestPrimitiveTypesRoutineRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Integer intVal, final Long longVal, final Short shortVal, final Byte byteVal, final Character charVal, final Double doubleVal, final Float floatVal, final Boolean boolVal, final String stringVal) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.TestPrimitiveTypesRoutineRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.intVal = intVal;this.longVal = longVal;this.shortVal = shortVal;this.byteVal = byteVal;this.charVal = charVal;this.doubleVal = doubleVal;this.floatVal = floatVal;this.boolVal = boolVal;this.stringVal = stringVal;
  }
  
  private Integer intVal;
  
  private Long longVal;
  
  private Short shortVal;
  
  private Byte byteVal;
  
  private Character charVal;
  
  private Double doubleVal;
  
  private Float floatVal;
  
  private Boolean boolVal;
  
  private String stringVal;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine TestPrimitiveTypesRoutineRoutine with input:");
    getLogger().debug("   Integer: " + this.intVal);
    getLogger().debug("   Long: " + this.longVal);
    getLogger().debug("   Short: " + this.shortVal);
    getLogger().debug("   Byte: " + this.byteVal);
    getLogger().debug("   Character: " + this.charVal);
    getLogger().debug("   Double: " + this.doubleVal);
    getLogger().debug("   Float: " + this.floatVal);
    getLogger().debug("   Boolean: " + this.boolVal);
    getLogger().debug("   String: " + this.stringVal);
    
    userExecution.callRoutine1(intVal, longVal, shortVal, byteVal, charVal, doubleVal, floatVal, boolVal, stringVal, actionsFacade);
    
    postprocessElements();
  }
}
