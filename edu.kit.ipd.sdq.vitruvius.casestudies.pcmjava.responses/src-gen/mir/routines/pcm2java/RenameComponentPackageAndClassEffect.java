package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

@SuppressWarnings("all")
public class RenameComponentPackageAndClassEffect extends AbstractEffectRealization {
  public RenameComponentPackageAndClassEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private RepositoryComponent component;
  
  private boolean isComponentSet;
  
  public void setComponent(final RepositoryComponent component) {
    this.component = component;
    this.isComponentSet = true;
  }
  
  public boolean allParametersSet() {
    return isComponentSet;
  }
  
  private boolean getCorrespondingModelElementsPreconditionRepositoryPackage(final RepositoryComponent component, final org.emftext.language.java.containers.Package repositoryPackage) {
    String _name = repositoryPackage.getName();
    Repository _repository__RepositoryComponent = component.getRepository__RepositoryComponent();
    String _entityName = _repository__RepositoryComponent.getEntityName();
    boolean _equals = _name.equals(_entityName);
    return _equals;
  }
  
  private EObject getCorrepondenceSourceRepositoryPackage(final RepositoryComponent component) {
    Repository _repository__RepositoryComponent = component.getRepository__RepositoryComponent();
    return _repository__RepositoryComponent;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine RenameComponentPackageAndClassEffect with input:");
    getLogger().debug("   RepositoryComponent: " + this.component);
    
    org.emftext.language.java.containers.Package repositoryPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceRepositoryPackage(component), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionRepositoryPackage(component, _element), // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.RenameComponentPackageAndClassEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	component, repositoryPackage);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final RepositoryComponent component, final org.emftext.language.java.containers.Package repositoryPackage) {
      String _entityName = component.getEntityName();
      this.effectFacade.callRenameJavaPackage(component, repositoryPackage, _entityName, null);
      this.effectFacade.callRenameComponentClass(component);
    }
  }
}
