package mir.routines.pcm2java;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameCollectionDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameCollectionDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public boolean getCorrespondingModelElementsPreconditionDatatypesPackage(final CollectionDataType collectionDataType, final org.emftext.language.java.containers.Package datatypesPackage) {
      String _name = datatypesPackage.getName();
      boolean _equals = Objects.equal(_name, "datatypes");
      return _equals;
    }
    
    public EObject getCorrepondenceSourceDatatypesPackage(final CollectionDataType collectionDataType) {
      Repository _repository__DataType = collectionDataType.getRepository__DataType();
      return _repository__DataType;
    }
    
    public void callRoutine1(final CollectionDataType collectionDataType, final org.emftext.language.java.containers.Package datatypesPackage, @Extension final RoutinesFacade _routinesFacade) {
      String _entityName = collectionDataType.getEntityName();
      _routinesFacade.renameJavaClassifier(collectionDataType, datatypesPackage, _entityName);
    }
  }
  
  public RenameCollectionDataTypeRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType collectionDataType) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.RenameCollectionDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.collectionDataType = collectionDataType;
  }
  
  private CollectionDataType collectionDataType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameCollectionDataTypeRoutine with input:");
    getLogger().debug("   CollectionDataType: " + this.collectionDataType);
    
    org.emftext.language.java.containers.Package datatypesPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDatatypesPackage(collectionDataType), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> userExecution.getCorrespondingModelElementsPreconditionDatatypesPackage(collectionDataType, _element), // correspondence precondition checker
    	null);
    if (datatypesPackage == null) {
    	return;
    }
    initializeRetrieveElementState(datatypesPackage);
    userExecution.callRoutine1(collectionDataType, datatypesPackage, actionsFacade);
    
    postprocessElementStates();
  }
}
