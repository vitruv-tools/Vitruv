package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateComponentImplementationEffect extends AbstractEffectRealization {
  public CreateComponentImplementationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component) {
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
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final RepositoryComponent component, final org.emftext.language.java.containers.Package repositoryPackage) {
      String _entityName = component.getEntityName();
      this.effectFacade.callCreateJavaPackage(component, repositoryPackage, _entityName, null);
      this.effectFacade.callCreateImplementationForComponent(component);
    }
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateComponentImplementationEffect with input:");
    getLogger().debug("   RepositoryComponent: " + this.component);
    
    org.emftext.language.java.containers.Package repositoryPackage = getCorrespondingElement(
    	getCorrepondenceSourceRepositoryPackage(component), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	getRetrieveTag0(component));
    if (repositoryPackage == null) {
    	return;
    }
    initializeRetrieveElementState(repositoryPackage);
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreateComponentImplementationEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	component, repositoryPackage);
    postprocessElementStates();
  }
  
  private String getRetrieveTag0(final RepositoryComponent component) {
    return "repository_root";
  }
  
  private EObject getCorrepondenceSourceRepositoryPackage(final RepositoryComponent component) {
    Repository _repository__RepositoryComponent = component.getRepository__RepositoryComponent();
    return _repository__RepositoryComponent;
  }
}
