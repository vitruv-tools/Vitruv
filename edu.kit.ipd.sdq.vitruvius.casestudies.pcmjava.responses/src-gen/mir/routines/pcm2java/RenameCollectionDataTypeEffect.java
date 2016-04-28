package mir.routines.pcm2java;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class RenameCollectionDataTypeEffect extends AbstractEffectRealization {
  public RenameCollectionDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CollectionDataType collectionDataType;
  
  private boolean isCollectionDataTypeSet;
  
  public void setCollectionDataType(final CollectionDataType collectionDataType) {
    this.collectionDataType = collectionDataType;
    this.isCollectionDataTypeSet = true;
  }
  
  public boolean allParametersSet() {
    return isCollectionDataTypeSet;
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
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine RenameCollectionDataTypeEffect with input:");
    getLogger().debug("   CollectionDataType: " + this.collectionDataType);
    
    org.emftext.language.java.containers.Package datatypesPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceDatatypesPackage(collectionDataType), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionDatatypesPackage(collectionDataType, _element), // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.RenameCollectionDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	collectionDataType, datatypesPackage);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CollectionDataType collectionDataType, final org.emftext.language.java.containers.Package datatypesPackage) {
      String _entityName = collectionDataType.getEntityName();
      this.effectFacade.callRenameJavaClassifier(collectionDataType, datatypesPackage, _entityName);
    }
  }
}
