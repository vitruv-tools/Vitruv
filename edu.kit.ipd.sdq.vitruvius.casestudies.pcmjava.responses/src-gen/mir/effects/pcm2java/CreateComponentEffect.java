package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
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
  
  private boolean getCorrespondingModelElementsPreconditionRepositoryPackage(final CreateNonRootEObjectInList<RepositoryComponent> change, final org.emftext.language.java.containers.Package repositoryPackage) {
    RepositoryComponent _newValue = change.getNewValue();
    Repository _repository__RepositoryComponent = _newValue.getRepository__RepositoryComponent();
    String _entityName = _repository__RepositoryComponent.getEntityName();
    String _name = repositoryPackage.getName();
    boolean _equals = _entityName.equals(_name);
    return _equals;
  }
  
  private EObject getCorrepondenceSourceRepositoryPackage(final CreateNonRootEObjectInList<RepositoryComponent> change) {
    RepositoryComponent _newValue = change.getNewValue();
    Repository _repository__RepositoryComponent = _newValue.getRepository__RepositoryComponent();
    return _repository__RepositoryComponent;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect CreateComponentEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    org.emftext.language.java.containers.Package repositoryPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceRepositoryPackage(change), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionRepositoryPackage(change, _element), // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.pcm2java.CreateComponentEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, repositoryPackage);
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
    
    private void executeUserOperations(final CreateNonRootEObjectInList<RepositoryComponent> change, final org.emftext.language.java.containers.Package repositoryPackage) {
      final RepositoryComponent component = change.getNewValue();
      String _entityName = component.getEntityName();
      this.effectFacade.callCreateJavaPackage(component, repositoryPackage, _entityName, null);
      this.effectFacade.callCreateImplementationForComponent(component);
    }
  }
}
