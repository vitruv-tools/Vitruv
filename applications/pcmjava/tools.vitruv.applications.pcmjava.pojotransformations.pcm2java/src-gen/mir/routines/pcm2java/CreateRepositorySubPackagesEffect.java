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
  private RoutinesFacade effectFacade;
  
  private CreateRepositorySubPackagesEffect.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceRepositoryPackage(final Repository repository) {
      return repository;
    }
    
    public void callRoutine1(final Repository repository, final org.emftext.language.java.containers.Package repositoryPackage, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaPackage(repository, repositoryPackage, "datatypes", "datatypes");
      _routinesFacade.createJavaPackage(repository, repositoryPackage, "contracts", "contracts");
    }
  }
  
  public CreateRepositorySubPackagesEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Repository repository) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.CreateRepositorySubPackagesEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.repository = repository;
  }
  
  private Repository repository;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRepositorySubPackagesEffect with input:");
    getLogger().debug("   Repository: " + this.repository);
    
    org.emftext.language.java.containers.Package repositoryPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepositoryPackage(repository), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null);
    if (repositoryPackage == null) {
    	return;
    }
    initializeRetrieveElementState(repositoryPackage);
    userExecution.callRoutine1(repository, repositoryPackage, effectFacade);
    
    postprocessElementStates();
  }
}
