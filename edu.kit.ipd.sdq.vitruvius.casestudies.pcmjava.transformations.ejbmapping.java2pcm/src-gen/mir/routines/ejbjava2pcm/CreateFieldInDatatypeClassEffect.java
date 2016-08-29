package mir.routines.ejbjava2pcm;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.reference.InsertEReference;
import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;

@SuppressWarnings("all")
public class CreateFieldInDatatypeClassEffect extends AbstractEffectRealization {
  public CreateFieldInDatatypeClassEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<org.emftext.language.java.classifiers.Class, Member> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<org.emftext.language.java.classifiers.Class, Member> change;
  
  private EObject getElement0(final InsertEReference<org.emftext.language.java.classifiers.Class, Member> change, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
    return innerDec;
  }
  
  private EObject getElement1(final InsertEReference<org.emftext.language.java.classifiers.Class, Member> change, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
    Member _newValue = change.getNewValue();
    return _newValue;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateFieldInDatatypeClassEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    CompositeDataType compositeDataType = getCorrespondingElement(
    	getCorrepondenceSourceCompositeDataType(change), // correspondence source supplier
    	CompositeDataType.class,
    	(CompositeDataType _element) -> true, // correspondence precondition checker
    	null);
    if (compositeDataType == null) {
    	return;
    }
    initializeRetrieveElementState(compositeDataType);
    InnerDeclaration innerDec = RepositoryFactoryImpl.eINSTANCE.createInnerDeclaration();
    initializeCreateElementState(innerDec);
    
    addCorrespondenceBetween(getElement0(change, compositeDataType, innerDec), getElement1(change, compositeDataType, innerDec), "");
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreateFieldInDatatypeClassEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, compositeDataType, innerDec);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceCompositeDataType(final InsertEReference<org.emftext.language.java.classifiers.Class, Member> change) {
    org.emftext.language.java.classifiers.Class _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<org.emftext.language.java.classifiers.Class, Member> change, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
      Member _newValue = change.getNewValue();
      final Field field = ((Field) _newValue);
      TypeReference _typeReference = field.getTypeReference();
      Repository _repository__DataType = compositeDataType.getRepository__DataType();
      long _arrayDimension = field.getArrayDimension();
      DataType _correspondingPCMDataTypeForTypeReference = TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(_typeReference, this.correspondenceModel, 
        this.userInteracting, _repository__DataType, _arrayDimension);
      innerDec.setDatatype_InnerDeclaration(_correspondingPCMDataTypeForTypeReference);
      Member _newValue_1 = change.getNewValue();
      String _name = _newValue_1.getName();
      innerDec.setEntityName(_name);
      EList<InnerDeclaration> _innerDeclaration_CompositeDataType = compositeDataType.getInnerDeclaration_CompositeDataType();
      _innerDeclaration_CompositeDataType.add(innerDec);
    }
  }
}
