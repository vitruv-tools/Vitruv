package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateImplementationForComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateImplementationForComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceComponentPackage(final RepositoryComponent component) {
      return component;
    }
    
    public void callRoutine1(final RepositoryComponent component, final org.emftext.language.java.containers.Package componentPackage, @Extension final RoutinesFacade _routinesFacade) {
      String _entityName = component.getEntityName();
      String _plus = (_entityName + "Impl");
      _routinesFacade.createJavaClass(component, componentPackage, _plus);
    }
  }
  
  public CreateImplementationForComponentRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.CreateImplementationForComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.component = component;
  }
  
  private RepositoryComponent component;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateImplementationForComponentRoutine with input:");
    getLogger().debug("   RepositoryComponent: " + this.component);
    
    org.emftext.language.java.containers.Package componentPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceComponentPackage(component), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null);
    if (componentPackage == null) {
    	return;
    }
    initializeRetrieveElementState(componentPackage);
    userExecution.callRoutine1(component, componentPackage, actionsFacade);
    
    postprocessElementStates();
  }
}
