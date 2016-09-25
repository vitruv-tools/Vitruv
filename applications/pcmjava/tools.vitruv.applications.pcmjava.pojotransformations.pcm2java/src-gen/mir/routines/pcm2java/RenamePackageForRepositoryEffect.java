package mir.routines.pcm2java;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenamePackageForRepositoryEffect extends AbstractEffectRealization {
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final Repository repository, final org.emftext.language.java.containers.Package rootPackage) {
      return rootPackage;
    }
    
    public void update0Element(final Repository repository, final org.emftext.language.java.containers.Package rootPackage) {
      String _entityName = repository.getEntityName();
      rootPackage.setName(_entityName);
    }
    
    public String getRetrieveTag1(final Repository repository) {
      return "repository_root";
    }
    
    public EObject getCorrepondenceSourceRootPackage(final Repository repository) {
      return repository;
    }
  }
  
  private RenamePackageForRepositoryEffect.EffectUserExecution userExecution;
  
  public RenamePackageForRepositoryEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Repository repository) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.RenamePackageForRepositoryEffect.EffectUserExecution(getExecutionState(), this);
    				this.repository = repository;
  }
  
  private Repository repository;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenamePackageForRepositoryEffect with input:");
    getLogger().debug("   Repository: " + this.repository);
    
    org.emftext.language.java.containers.Package rootPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRootPackage(repository), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(repository));
    if (rootPackage == null) {
    	return;
    }
    initializeRetrieveElementState(rootPackage);
    // val updatedElement userExecution.getElement1(repository, rootPackage);
    userExecution.update0Element(repository, rootPackage);
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenamePackageForRepositoryEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	repository, rootPackage);
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    public void executeUserOperations(final Repository repository, final org.emftext.language.java.containers.Package rootPackage) {
      this.effectFacade.renameJavaPackage(repository, rootPackage, "contracts", "contracts");
      this.effectFacade.renameJavaPackage(repository, rootPackage, "datatypes", "datatypes");
      EList<RepositoryComponent> _components__Repository = repository.getComponents__Repository();
      Iterable<BasicComponent> _filter = Iterables.<BasicComponent>filter(_components__Repository, BasicComponent.class);
      for (final BasicComponent component : _filter) {
        this.effectFacade.renameComponentPackageAndClass(component);
      }
      EList<Interface> _interfaces__Repository = repository.getInterfaces__Repository();
      Iterable<OperationInterface> _filter_1 = Iterables.<OperationInterface>filter(_interfaces__Repository, OperationInterface.class);
      for (final OperationInterface interface_ : _filter_1) {
        this.effectFacade.renameInterface(interface_);
      }
      EList<DataType> _dataTypes__Repository = repository.getDataTypes__Repository();
      Iterable<CompositeDataType> _filter_2 = Iterables.<CompositeDataType>filter(_dataTypes__Repository, CompositeDataType.class);
      for (final CompositeDataType dataType : _filter_2) {
        this.effectFacade.renameCompositeDataType(dataType);
      }
      EList<DataType> _dataTypes__Repository_1 = repository.getDataTypes__Repository();
      Iterable<CollectionDataType> _filter_3 = Iterables.<CollectionDataType>filter(_dataTypes__Repository_1, CollectionDataType.class);
      for (final CollectionDataType dataType_1 : _filter_3) {
        this.effectFacade.renameCollectionDataType(dataType_1);
      }
      String _buildJavaFilePath = Pcm2JavaHelper.buildJavaFilePath(rootPackage);
      this.persistProjectRelative(repository, rootPackage, _buildJavaFilePath);
    }
  }
}
