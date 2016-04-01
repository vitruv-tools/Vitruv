package mir.effects.pcm2java;

import com.google.common.base.Objects;
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
    getLogger().debug("Called effect RenameCollectionDataTypeEffect with input:");
    getLogger().debug("   CollectionDataType: " + this.collectionDataType);
    
    org.emftext.language.java.containers.Package datatypesPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceDatatypesPackage(collectionDataType), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionDatatypesPackage(collectionDataType, _element), // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.pcm2java.RenameCollectionDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	collectionDataType, datatypesPackage);
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
    
    private void executeUserOperations(final CollectionDataType collectionDataType, final org.emftext.language.java.containers.Package datatypesPackage) {
      String _entityName = collectionDataType.getEntityName();
      this.effectFacade.callRenameJavaClassifier(collectionDataType, datatypesPackage, _entityName);
    }
  }
}
