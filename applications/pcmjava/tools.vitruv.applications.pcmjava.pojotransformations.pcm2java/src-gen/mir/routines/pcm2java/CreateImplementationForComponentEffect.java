package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateImplementationForComponentEffect extends AbstractEffectRealization {
  public CreateImplementationForComponentEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component) {
    super(responseExecutionState, calledBy);
    				this.component = component;
  }
  
  private RepositoryComponent component;
  
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
    
    private void executeUserOperations(final RepositoryComponent component, final org.emftext.language.java.containers.Package componentPackage) {
      String _entityName = component.getEntityName();
      String _plus = (_entityName + "Impl");
      this.effectFacade.callCreateJavaClass(component, componentPackage, _plus);
    }
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateImplementationForComponentEffect with input:");
    getLogger().debug("   RepositoryComponent: " + this.component);
    
    org.emftext.language.java.containers.Package componentPackage = getCorrespondingElement(
    	getCorrepondenceSourceComponentPackage(component), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null);
    if (componentPackage == null) {
    	return;
    }
    initializeRetrieveElementState(componentPackage);
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreateImplementationForComponentEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	component, componentPackage);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceComponentPackage(final RepositoryComponent component) {
    return component;
  }
}
