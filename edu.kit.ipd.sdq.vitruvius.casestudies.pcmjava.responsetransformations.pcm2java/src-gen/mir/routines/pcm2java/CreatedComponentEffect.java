package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

@SuppressWarnings("all")
public class CreatedComponentEffect extends AbstractEffectRealization {
  public CreatedComponentEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<Repository, RepositoryComponent> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<Repository, RepositoryComponent> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedComponentEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    org.emftext.language.java.containers.Package repositoryPackage = getCorrespondingElement(
    	getCorrepondenceSourceRepositoryPackage(change), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	getRetrieveTag0(change));
    if (repositoryPackage == null) {
    	return;
    }
    initializeRetrieveElementState(repositoryPackage);
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreatedComponentEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, repositoryPackage);
    postprocessElementStates();
  }
  
  private String getRetrieveTag0(final InsertEReference<Repository, RepositoryComponent> change) {
    return "repository_root";
  }
  
  private EObject getCorrepondenceSourceRepositoryPackage(final InsertEReference<Repository, RepositoryComponent> change) {
    RepositoryComponent _newValue = change.getNewValue();
    Repository _repository__RepositoryComponent = _newValue.getRepository__RepositoryComponent();
    return _repository__RepositoryComponent;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<Repository, RepositoryComponent> change, final org.emftext.language.java.containers.Package repositoryPackage) {
      final RepositoryComponent component = change.getNewValue();
      String _entityName = component.getEntityName();
      this.effectFacade.callCreateJavaPackage(component, repositoryPackage, _entityName, null);
      this.effectFacade.callCreateImplementationForComponent(component);
    }
  }
}
