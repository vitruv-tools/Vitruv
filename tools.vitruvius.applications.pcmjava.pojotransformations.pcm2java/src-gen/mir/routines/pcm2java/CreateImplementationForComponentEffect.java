package mir.routines.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

@SuppressWarnings("all")
public class CreateImplementationForComponentEffect extends AbstractEffectRealization {
  public CreateImplementationForComponentEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component) {
    super(responseExecutionState, calledBy);
    				this.component = component;
  }
  
  private RepositoryComponent component;
  
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
    new mir.routines.pcm2java.CreateImplementationForComponentEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	component, componentPackage);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceComponentPackage(final RepositoryComponent component) {
    return component;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final RepositoryComponent component, final org.emftext.language.java.containers.Package componentPackage) {
      String _entityName = component.getEntityName();
      String _plus = (_entityName + "Impl");
      this.effectFacade.callCreateJavaClass(component, componentPackage, _plus);
    }
  }
}
