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
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public String getRetrieveTag1(final RepositoryComponent component) {
      return "repository_root";
    }
    
    public EObject getCorrepondenceSourceRepositoryPackage(final RepositoryComponent component) {
      Repository _repository__RepositoryComponent = component.getRepository__RepositoryComponent();
      return _repository__RepositoryComponent;
    }
    
    public void callRoutine1(final RepositoryComponent component, final org.emftext.language.java.containers.Package repositoryPackage, @Extension final RoutinesFacade _routinesFacade) {
      String _entityName = component.getEntityName();
      _routinesFacade.createJavaPackage(component, repositoryPackage, _entityName, null);
      _routinesFacade.createImplementationForComponent(component);
    }
  }
  
  private CreateComponentImplementationEffect.EffectUserExecution userExecution;
  
  public CreateComponentImplementationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.CreateComponentImplementationEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.component = component;
  }
  
  private RepositoryComponent component;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateComponentImplementationEffect with input:");
    getLogger().debug("   RepositoryComponent: " + this.component);
    
    org.emftext.language.java.containers.Package repositoryPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepositoryPackage(component), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(component));
    if (repositoryPackage == null) {
    	return;
    }
    initializeRetrieveElementState(repositoryPackage);
    preprocessElementStates();
    userExecution.callRoutine1(
    	component, repositoryPackage, effectFacade);
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
