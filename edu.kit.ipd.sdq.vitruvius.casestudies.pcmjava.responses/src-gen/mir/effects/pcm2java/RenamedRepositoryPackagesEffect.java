package mir.effects.pcm2java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

@SuppressWarnings("all")
public class RenamedRepositoryPackagesEffect extends AbstractEffectRealization {
  public RenamedRepositoryPackagesEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private UpdateSingleValuedEAttribute<String> change;
  
  private boolean isChangeSet;
  
  public void setChange(final UpdateSingleValuedEAttribute<String> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private String getTagRootPackage(final UpdateSingleValuedEAttribute<String> change) {
    return "repository_root";
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceRootPackage(final UpdateSingleValuedEAttribute<String> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  private String getModelPath(final UpdateSingleValuedEAttribute<String> change, final org.emftext.language.java.containers.Package rootPackage) {
    String _buildJavaFilePath = Pcm2JavaHelper.buildJavaFilePath(rootPackage);
    return _buildJavaFilePath;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect RenamedRepositoryPackagesEffect with input:");
    getLogger().debug("   UpdateSingleValuedEAttribute: " + this.change);
    
    org.emftext.language.java.containers.Package rootPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceRootPackage(change), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	() -> getTagRootPackage(change), // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    setPersistenceInformation(rootPackage, () -> getModelPath(change, rootPackage), false);
    preProcessElements();
    new mir.effects.pcm2java.RenamedRepositoryPackagesEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, rootPackage);
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
    
    private void executeUserOperations(final UpdateSingleValuedEAttribute<String> change, final org.emftext.language.java.containers.Package rootPackage) {
      EObject _newAffectedEObject = change.getNewAffectedEObject();
      final Repository repository = ((Repository) _newAffectedEObject);
      String _newValue = change.getNewValue();
      rootPackage.setName(_newValue);
      this.effectFacade.callRenameJavaPackage(repository, rootPackage, "contracts", "contracts");
      this.effectFacade.callRenameJavaPackage(repository, rootPackage, "datatypes", "datatypes");
      EList<RepositoryComponent> _components__Repository = repository.getComponents__Repository();
      Iterable<BasicComponent> _filter = Iterables.<BasicComponent>filter(_components__Repository, BasicComponent.class);
      for (final BasicComponent component : _filter) {
        this.effectFacade.callRenameComponentPackageAndClass(component);
      }
      EList<Interface> _interfaces__Repository = repository.getInterfaces__Repository();
      Iterable<OperationInterface> _filter_1 = Iterables.<OperationInterface>filter(_interfaces__Repository, OperationInterface.class);
      for (final OperationInterface interface_ : _filter_1) {
        this.effectFacade.callRenameInterface(interface_);
      }
      EList<DataType> _dataTypes__Repository = repository.getDataTypes__Repository();
      Iterable<CompositeDataType> _filter_2 = Iterables.<CompositeDataType>filter(_dataTypes__Repository, CompositeDataType.class);
      for (final CompositeDataType dataType : _filter_2) {
        this.effectFacade.callRenameCompositeDataType(dataType);
      }
    }
  }
}
