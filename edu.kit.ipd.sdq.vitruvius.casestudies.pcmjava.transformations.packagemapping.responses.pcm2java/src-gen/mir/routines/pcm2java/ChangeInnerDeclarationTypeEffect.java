package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.InnerDeclaration;

@SuppressWarnings("all")
public class ChangeInnerDeclarationTypeEffect extends AbstractEffectRealization {
  public ChangeInnerDeclarationTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    super(responseExecutionState, calledBy);
    				this.innerDeclaration = innerDeclaration;this.newTypeReference = newTypeReference;
  }
  
  private InnerDeclaration innerDeclaration;
  
  private TypeReference newTypeReference;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInnerDeclarationTypeEffect with input:");
    getLogger().debug("   InnerDeclaration: " + this.innerDeclaration);
    getLogger().debug("   TypeReference: " + this.newTypeReference);
    
    Field compositeTypeField = getCorrespondingElement(
    	getCorrepondenceSourceCompositeTypeField(innerDeclaration, newTypeReference), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    if (compositeTypeField == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeField);
    Method compositeTypeGetterMethod = getCorrespondingElement(
    	getCorrepondenceSourceCompositeTypeGetterMethod(innerDeclaration, newTypeReference), // correspondence source supplier
    	Method.class,
    	(Method _element) -> true, // correspondence precondition checker
    	getRetrieveTag0(innerDeclaration, newTypeReference));
    if (compositeTypeGetterMethod == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeGetterMethod);
    Method compositeTypeSetterMethod = getCorrespondingElement(
    	getCorrepondenceSourceCompositeTypeSetterMethod(innerDeclaration, newTypeReference), // correspondence source supplier
    	Method.class,
    	(Method _element) -> true, // correspondence precondition checker
    	getRetrieveTag1(innerDeclaration, newTypeReference));
    if (compositeTypeSetterMethod == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeSetterMethod);
    
    preprocessElementStates();
    new mir.routines.pcm2java.ChangeInnerDeclarationTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    postprocessElementStates();
  }
  
  private String getRetrieveTag0(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    return "getter";
  }
  
  private String getRetrieveTag1(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    return "setter";
  }
  
  private EObject getCorrepondenceSourceCompositeTypeField(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    return innerDeclaration;
  }
  
  private EObject getCorrepondenceSourceCompositeTypeGetterMethod(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    return innerDeclaration;
  }
  
  private EObject getCorrepondenceSourceCompositeTypeSetterMethod(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    return innerDeclaration;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      TypeReference _copy = EcoreUtil.<TypeReference>copy(newTypeReference);
      compositeTypeField.setTypeReference(_copy);
      TypeReference _copy_1 = EcoreUtil.<TypeReference>copy(newTypeReference);
      compositeTypeGetterMethod.setTypeReference(_copy_1);
      EList<Parameter> _parameters = compositeTypeSetterMethod.getParameters();
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(_parameters);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        EList<Parameter> _parameters_1 = compositeTypeSetterMethod.getParameters();
        final Parameter parameter = _parameters_1.get(0);
        TypeReference _copy_2 = EcoreUtil.<TypeReference>copy(newTypeReference);
        parameter.setTypeReference(_copy_2);
      }
    }
  }
}
