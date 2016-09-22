package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeTypeOfInnerDeclarationImplementationEffect extends AbstractEffectRealization {
  public ChangeTypeOfInnerDeclarationImplementationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration innerDeclaration) {
    super(responseExecutionState, calledBy);
    				this.innerDeclaration = innerDeclaration;
  }
  
  private InnerDeclaration innerDeclaration;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InnerDeclaration innerDeclaration, final org.emftext.language.java.classifiers.Class newJavaDataType) {
      DataType _datatype_InnerDeclaration = innerDeclaration.getDatatype_InnerDeclaration();
      final TypeReference newDataTypeReference = Pcm2JavaHelper.createTypeReference(_datatype_InnerDeclaration, newJavaDataType);
      this.effectFacade.changeInnerDeclarationType(innerDeclaration, newDataTypeReference);
    }
  }
  
  private EObject getCorrepondenceSourceNewJavaDataType(final InnerDeclaration innerDeclaration) {
    DataType _datatype_InnerDeclaration = innerDeclaration.getDatatype_InnerDeclaration();
    return _datatype_InnerDeclaration;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfInnerDeclarationImplementationEffect with input:");
    getLogger().debug("   InnerDeclaration: " + this.innerDeclaration);
    
    org.emftext.language.java.classifiers.Class newJavaDataType = getCorrespondingElement(
    	getCorrepondenceSourceNewJavaDataType(innerDeclaration), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(newJavaDataType);
    
    preprocessElementStates();
    new mir.routines.pcm2java.ChangeTypeOfInnerDeclarationImplementationEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	innerDeclaration, newJavaDataType);
    postprocessElementStates();
  }
}
