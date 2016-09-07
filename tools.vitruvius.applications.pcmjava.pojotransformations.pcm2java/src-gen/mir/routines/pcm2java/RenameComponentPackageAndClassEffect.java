package mir.routines.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

@SuppressWarnings("all")
public class RenameComponentPackageAndClassEffect extends AbstractEffectRealization {
  public RenameComponentPackageAndClassEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component) {
    super(responseExecutionState, calledBy);
    				this.component = component;
  }
  
  private RepositoryComponent component;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameComponentPackageAndClassEffect with input:");
    getLogger().debug("   RepositoryComponent: " + this.component);
    
    org.emftext.language.java.containers.Package repositoryPackage = getCorrespondingElement(
    	getCorrepondenceSourceRepositoryPackage(component), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionRepositoryPackage(component, _element), // correspondence precondition checker
    	null);
    if (repositoryPackage == null) {
    	return;
    }
    initializeRetrieveElementState(repositoryPackage);
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenameComponentPackageAndClassEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	component, repositoryPackage);
    postprocessElementStates();
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
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final RepositoryComponent component, final org.emftext.language.java.containers.Package repositoryPackage) {
      String _entityName = component.getEntityName();
      this.effectFacade.callRenameJavaPackage(component, repositoryPackage, _entityName, null);
      this.effectFacade.callRenameComponentClass(component);
    }
  }
}
