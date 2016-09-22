package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateRepositorySubPackagesEffect extends AbstractEffectRealization {
  public CreateRepositorySubPackagesEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Repository repository) {
    super(responseExecutionState, calledBy);
    				this.repository = repository;
  }
  
  private Repository repository;
  
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
    
    private void executeUserOperations(final Repository repository, final org.emftext.language.java.containers.Package repositoryPackage) {
      this.effectFacade.callCreateJavaPackage(repository, repositoryPackage, "datatypes", "datatypes");
      this.effectFacade.callCreateJavaPackage(repository, repositoryPackage, "contracts", "contracts");
    }
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRepositorySubPackagesEffect with input:");
    getLogger().debug("   Repository: " + this.repository);
    
    org.emftext.language.java.containers.Package repositoryPackage = getCorrespondingElement(
    	getCorrepondenceSourceRepositoryPackage(repository), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null);
    if (repositoryPackage == null) {
    	return;
    }
    initializeRetrieveElementState(repositoryPackage);
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreateRepositorySubPackagesEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	repository, repositoryPackage);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceRepositoryPackage(final Repository repository) {
    return repository;
  }
}
