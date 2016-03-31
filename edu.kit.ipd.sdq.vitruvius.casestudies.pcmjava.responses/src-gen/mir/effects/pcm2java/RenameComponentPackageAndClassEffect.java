package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
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
    getLogger().debug("Called effect RenameComponentPackageAndClassEffect with input:");
    getLogger().debug("   RepositoryComponent: " + this.component);
    
    org.emftext.language.java.containers.Package repositoryPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceRepositoryPackage(component), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionRepositoryPackage(component, _element), // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    preProcessElements();
    new mir.effects.pcm2java.RenameComponentPackageAndClassEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	component, repositoryPackage);
    postProcessElements();
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final RepositoryComponent component, final org.emftext.language.java.containers.Package repositoryPackage) {
      String _entityName = component.getEntityName();
      this.effectFacade.callRenameJavaPackage(component, repositoryPackage, _entityName, null);
      this.effectFacade.callRenameComponentClass(component);
    }
  }
}
