package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class CreateRepositorySubPackagesEffect extends AbstractEffectRealization {
  public CreateRepositorySubPackagesEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private Repository repository;
  
  private boolean isRepositorySet;
  
  public void setRepository(final Repository repository) {
    this.repository = repository;
    this.isRepositorySet = true;
  }
  
  public boolean allParametersSet() {
    return isRepositorySet;
  }
  
  private EObject getCorrepondenceSourceRepositoryPackage(final Repository repository) {
    return repository;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreateRepositorySubPackagesEffect with input:");
    getLogger().debug("   Repository: " + this.repository);
    
    org.emftext.language.java.containers.Package repositoryPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceRepositoryPackage(repository), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.CreateRepositorySubPackagesEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	repository, repositoryPackage);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final Repository repository, final org.emftext.language.java.containers.Package repositoryPackage) {
      this.effectFacade.callCreateJavaPackage(repository, repositoryPackage, "datatypes", "datatypes");
      this.effectFacade.callCreateJavaPackage(repository, repositoryPackage, "contracts", "contracts");
    }
  }
}
