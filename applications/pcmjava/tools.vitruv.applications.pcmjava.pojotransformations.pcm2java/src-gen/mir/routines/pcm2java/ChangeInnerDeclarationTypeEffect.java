package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeInnerDeclarationTypeEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private ChangeInnerDeclarationTypeEffect.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      return compositeTypeField;
    }
    
    public void update0Element(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      TypeReference _copy = EcoreUtil.<TypeReference>copy(newTypeReference);
      compositeTypeField.setTypeReference(_copy);
    }
    
    public String getRetrieveTag1(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField) {
      return "getter";
    }
    
    public String getRetrieveTag2(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod) {
      return "setter";
    }
    
    public EObject getElement2(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      return compositeTypeGetterMethod;
    }
    
    public EObject getElement3(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      return compositeTypeSetterMethod;
    }
    
    public EObject getCorrepondenceSourceCompositeTypeField(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
      return innerDeclaration;
    }
    
    public EObject getCorrepondenceSourceCompositeTypeGetterMethod(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField) {
      return innerDeclaration;
    }
    
    public EObject getCorrepondenceSourceCompositeTypeSetterMethod(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod) {
      return innerDeclaration;
    }
    
    public void update2Element(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      EList<Parameter> _parameters = compositeTypeSetterMethod.getParameters();
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(_parameters);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        EList<Parameter> _parameters_1 = compositeTypeSetterMethod.getParameters();
        final Parameter parameter = _parameters_1.get(0);
        TypeReference _copy = EcoreUtil.<TypeReference>copy(newTypeReference);
        parameter.setTypeReference(_copy);
      }
    }
    
    public void update1Element(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      TypeReference _copy = EcoreUtil.<TypeReference>copy(newTypeReference);
      compositeTypeGetterMethod.setTypeReference(_copy);
    }
  }
  
  public ChangeInnerDeclarationTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.ChangeInnerDeclarationTypeEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.innerDeclaration = innerDeclaration;this.newTypeReference = newTypeReference;
  }
  
  private InnerDeclaration innerDeclaration;
  
  private TypeReference newTypeReference;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInnerDeclarationTypeEffect with input:");
    getLogger().debug("   InnerDeclaration: " + this.innerDeclaration);
    getLogger().debug("   TypeReference: " + this.newTypeReference);
    
    Field compositeTypeField = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeTypeField(innerDeclaration, newTypeReference), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    if (compositeTypeField == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeField);
    Method compositeTypeGetterMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeTypeGetterMethod(innerDeclaration, newTypeReference, compositeTypeField), // correspondence source supplier
    	Method.class,
    	(Method _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(innerDeclaration, newTypeReference, compositeTypeField));
    if (compositeTypeGetterMethod == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeGetterMethod);
    Method compositeTypeSetterMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeTypeSetterMethod(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod), // correspondence source supplier
    	Method.class,
    	(Method _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod));
    if (compositeTypeSetterMethod == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeSetterMethod);
    // val updatedElement userExecution.getElement1(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    userExecution.update0Element(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    
    // val updatedElement userExecution.getElement2(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    userExecution.update1Element(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    
    // val updatedElement userExecution.getElement3(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    userExecution.update2Element(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    
    postprocessElementStates();
  }
}
