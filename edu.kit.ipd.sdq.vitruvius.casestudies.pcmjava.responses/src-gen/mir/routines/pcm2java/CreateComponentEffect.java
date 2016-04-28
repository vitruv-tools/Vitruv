package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

@SuppressWarnings("all")
public class CreateComponentEffect extends AbstractEffectRealization {
  public CreateComponentEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateNonRootEObjectInList<RepositoryComponent> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateNonRootEObjectInList<RepositoryComponent> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private String getRetrieveTag0(final CreateNonRootEObjectInList<RepositoryComponent> change) {
    return "repository_root";
  }
  
  private EObject getCorrepondenceSourceRepositoryPackage(final CreateNonRootEObjectInList<RepositoryComponent> change) {
    RepositoryComponent _newValue = change.getNewValue();
    Repository _repository__RepositoryComponent = _newValue.getRepository__RepositoryComponent();
    return _repository__RepositoryComponent;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreateComponentEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    org.emftext.language.java.containers.Package repositoryPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceRepositoryPackage(change), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	() -> getRetrieveTag0(change), // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.CreateComponentEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, repositoryPackage);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CreateNonRootEObjectInList<RepositoryComponent> change, final org.emftext.language.java.containers.Package repositoryPackage) {
      final RepositoryComponent component = change.getNewValue();
      String _entityName = component.getEntityName();
      this.effectFacade.callCreateJavaPackage(component, repositoryPackage, _entityName, null);
      this.effectFacade.callCreateImplementationForComponent(component);
    }
  }
}
