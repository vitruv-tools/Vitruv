package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateInnerDeclarationImplementationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private CreateInnerDeclarationImplementationRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceNonPrimitiveInnerDataTypeClass(final InnerDeclaration innerDeclaration) {
      DataType _datatype_InnerDeclaration = innerDeclaration.getDatatype_InnerDeclaration();
      return _datatype_InnerDeclaration;
    }
    
    public void callRoutine1(final InnerDeclaration innerDeclaration, final org.emftext.language.java.classifiers.Class nonPrimitiveInnerDataTypeClass, @Extension final RoutinesFacade _routinesFacade) {
      DataType _datatype_InnerDeclaration = innerDeclaration.getDatatype_InnerDeclaration();
      final TypeReference innerDataTypeReference = Pcm2JavaHelper.createTypeReference(_datatype_InnerDeclaration, nonPrimitiveInnerDataTypeClass);
      CompositeDataType _compositeDataType_InnerDeclaration = innerDeclaration.getCompositeDataType_InnerDeclaration();
      _routinesFacade.addInnerDeclarationToCompositeDataType(_compositeDataType_InnerDeclaration, innerDeclaration, innerDataTypeReference);
    }
  }
  
  public CreateInnerDeclarationImplementationRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration innerDeclaration) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.CreateInnerDeclarationImplementationRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.innerDeclaration = innerDeclaration;
  }
  
  private InnerDeclaration innerDeclaration;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInnerDeclarationImplementationRoutine with input:");
    getLogger().debug("   InnerDeclaration: " + this.innerDeclaration);
    
    org.emftext.language.java.classifiers.Class nonPrimitiveInnerDataTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceNonPrimitiveInnerDataTypeClass(innerDeclaration), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(nonPrimitiveInnerDataTypeClass);
    userExecution.callRoutine1(innerDeclaration, nonPrimitiveInnerDataTypeClass, effectFacade);
    
    postprocessElementStates();
  }
}
