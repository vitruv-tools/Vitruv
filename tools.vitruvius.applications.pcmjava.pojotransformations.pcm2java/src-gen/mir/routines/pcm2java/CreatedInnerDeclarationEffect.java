package mir.routines.pcm2java;

import tools.vitruvius.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;

@SuppressWarnings("all")
public class CreatedInnerDeclarationEffect extends AbstractEffectRealization {
  public CreatedInnerDeclarationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<CompositeDataType, InnerDeclaration> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<CompositeDataType, InnerDeclaration> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedInnerDeclarationEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    org.emftext.language.java.classifiers.Class nonPrimitiveInnerDataTypeClass = getCorrespondingElement(
    	getCorrepondenceSourceNonPrimitiveInnerDataTypeClass(change), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(nonPrimitiveInnerDataTypeClass);
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreatedInnerDeclarationEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, nonPrimitiveInnerDataTypeClass);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceNonPrimitiveInnerDataTypeClass(final InsertEReference<CompositeDataType, InnerDeclaration> change) {
    InnerDeclaration _newValue = change.getNewValue();
    DataType _datatype_InnerDeclaration = _newValue.getDatatype_InnerDeclaration();
    return _datatype_InnerDeclaration;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<CompositeDataType, InnerDeclaration> change, final org.emftext.language.java.classifiers.Class nonPrimitiveInnerDataTypeClass) {
      InnerDeclaration _newValue = change.getNewValue();
      DataType _datatype_InnerDeclaration = _newValue.getDatatype_InnerDeclaration();
      final TypeReference innerDataTypeReference = Pcm2JavaHelper.createTypeReference(_datatype_InnerDeclaration, nonPrimitiveInnerDataTypeClass);
      final CompositeDataType compositeDataType = change.getAffectedEObject();
      final InnerDeclaration innerDeclaration = change.getNewValue();
      this.effectFacade.callAddInnerDeclarationToCompositeDataType(compositeDataType, innerDeclaration, innerDataTypeReference);
    }
  }
}
