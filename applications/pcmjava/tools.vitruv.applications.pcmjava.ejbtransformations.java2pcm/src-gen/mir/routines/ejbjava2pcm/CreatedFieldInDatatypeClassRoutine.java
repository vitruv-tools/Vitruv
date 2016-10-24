package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatedFieldInDatatypeClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatedFieldInDatatypeClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class clazz, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
      return innerDec;
    }
    
    public void update0Element(final org.emftext.language.java.classifiers.Class clazz, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
      EList<InnerDeclaration> _innerDeclaration_CompositeDataType = compositeDataType.getInnerDeclaration_CompositeDataType();
      _innerDeclaration_CompositeDataType.add(innerDec);
    }
    
    public EObject getCorrepondenceSourceCompositeDataType(final org.emftext.language.java.classifiers.Class clazz, final Field field) {
      return clazz;
    }
    
    public EObject getElement2(final org.emftext.language.java.classifiers.Class clazz, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
      return field;
    }
    
    public EObject getElement3(final org.emftext.language.java.classifiers.Class clazz, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
      return compositeDataType;
    }
    
    public void updateInnerDecElement(final org.emftext.language.java.classifiers.Class clazz, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
      TypeReference _typeReference = field.getTypeReference();
      Repository _repository__DataType = compositeDataType.getRepository__DataType();
      long _arrayDimension = field.getArrayDimension();
      DataType _correspondingPCMDataTypeForTypeReference = TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(_typeReference, this.correspondenceModel, 
        this.userInteracting, _repository__DataType, _arrayDimension);
      innerDec.setDatatype_InnerDeclaration(_correspondingPCMDataTypeForTypeReference);
      String _name = field.getName();
      innerDec.setEntityName(_name);
    }
  }
  
  public CreatedFieldInDatatypeClassRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class clazz, final Field field) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatedFieldInDatatypeClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.clazz = clazz;this.field = field;
  }
  
  private org.emftext.language.java.classifiers.Class clazz;
  
  private Field field;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedFieldInDatatypeClassRoutine with input:");
    getLogger().debug("   Class: " + this.clazz);
    getLogger().debug("   Field: " + this.field);
    
    CompositeDataType compositeDataType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeDataType(clazz, field), // correspondence source supplier
    	CompositeDataType.class,
    	(CompositeDataType _element) -> true, // correspondence precondition checker
    	null);
    if (compositeDataType == null) {
    	return;
    }
    initializeRetrieveElementState(compositeDataType);
    InnerDeclaration innerDec = RepositoryFactoryImpl.eINSTANCE.createInnerDeclaration();
    initializeCreateElementState(innerDec);
    userExecution.updateInnerDecElement(clazz, field, compositeDataType, innerDec);
    
    addCorrespondenceBetween(userExecution.getElement1(clazz, field, compositeDataType, innerDec), userExecution.getElement2(clazz, field, compositeDataType, innerDec), "");
    
    // val updatedElement userExecution.getElement3(clazz, field, compositeDataType, innerDec);
    userExecution.update0Element(clazz, field, compositeDataType, innerDec);
    
    postprocessElementStates();
  }
}
