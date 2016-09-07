package mir.routines.pcm2java;

import com.google.common.base.Objects;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class RenameCollectionDataTypeEffect extends AbstractEffectRealization {
  public RenameCollectionDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType collectionDataType) {
    super(responseExecutionState, calledBy);
    				this.collectionDataType = collectionDataType;
  }
  
  private CollectionDataType collectionDataType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameCollectionDataTypeEffect with input:");
    getLogger().debug("   CollectionDataType: " + this.collectionDataType);
    
    org.emftext.language.java.containers.Package datatypesPackage = getCorrespondingElement(
    	getCorrepondenceSourceDatatypesPackage(collectionDataType), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionDatatypesPackage(collectionDataType, _element), // correspondence precondition checker
    	null);
    if (datatypesPackage == null) {
    	return;
    }
    initializeRetrieveElementState(datatypesPackage);
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenameCollectionDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	collectionDataType, datatypesPackage);
    postprocessElementStates();
  }
  
  private boolean getCorrespondingModelElementsPreconditionDatatypesPackage(final CollectionDataType collectionDataType, final org.emftext.language.java.containers.Package datatypesPackage) {
    String _name = datatypesPackage.getName();
    boolean _equals = Objects.equal(_name, "datatypes");
    return _equals;
  }
  
  private EObject getCorrepondenceSourceDatatypesPackage(final CollectionDataType collectionDataType) {
    Repository _repository__DataType = collectionDataType.getRepository__DataType();
    return _repository__DataType;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CollectionDataType collectionDataType, final org.emftext.language.java.containers.Package datatypesPackage) {
      String _entityName = collectionDataType.getEntityName();
      this.effectFacade.callRenameJavaClassifier(collectionDataType, datatypesPackage, _entityName);
    }
  }
}
