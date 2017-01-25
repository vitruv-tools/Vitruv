package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateRootRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateRootRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Root rootElement, final Root newRoot) {
      return newRoot;
    }
    
    public void updateNewRootElement(final Root rootElement, final Root newRoot) {
      String _id = rootElement.getId();
      newRoot.setId(_id);
    }
    
    public EObject getElement2(final Root rootElement, final Root newRoot) {
      return rootElement;
    }
    
    public void callRoutine1(final Root rootElement, final Root newRoot, @Extension final RoutinesFacade _routinesFacade) {
      String _id = rootElement.getId();
      String _replace = _id.replace("Source", "Target");
      String _plus = ("model/" + _replace);
      this.persistProjectRelative(rootElement, newRoot, _plus);
    }
  }
  
  public CreateRootRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Root rootElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.CreateRootRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.rootElement = rootElement;
  }
  
  private Root rootElement;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRootRoutine with input:");
    getLogger().debug("   Root: " + this.rootElement);
    
    Root newRoot = AllElementTypesFactoryImpl.eINSTANCE.createRoot();
    initializeCreateElementState(newRoot);
    userExecution.updateNewRootElement(rootElement, newRoot);
    
    addCorrespondenceBetween(userExecution.getElement1(rootElement, newRoot), userExecution.getElement2(rootElement, newRoot), "");
    
    userExecution.callRoutine1(rootElement, newRoot, actionsFacade);
    
    postprocessElementStates();
  }
}
