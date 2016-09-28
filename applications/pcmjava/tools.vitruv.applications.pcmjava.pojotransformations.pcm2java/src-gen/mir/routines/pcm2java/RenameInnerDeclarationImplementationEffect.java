package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.parameters.Parameter;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameInnerDeclarationImplementationEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private RenameInnerDeclarationImplementationEffect.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      return compositeTypeField;
    }
    
    public void update0Element(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      String _entityName = innerDeclaration.getEntityName();
      compositeTypeField.setName(_entityName);
    }
    
    public String getRetrieveTag1(final InnerDeclaration innerDeclaration, final Field compositeTypeField) {
      return "getter";
    }
    
    public String getRetrieveTag2(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod) {
      return "setter";
    }
    
    public EObject getElement2(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      return compositeTypeGetterMethod;
    }
    
    public EObject getElement3(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      return compositeTypeSetterMethod;
    }
    
    public EObject getCorrepondenceSourceCompositeTypeField(final InnerDeclaration innerDeclaration) {
      return innerDeclaration;
    }
    
    public EObject getCorrepondenceSourceCompositeTypeGetterMethod(final InnerDeclaration innerDeclaration, final Field compositeTypeField) {
      return innerDeclaration;
    }
    
    public EObject getCorrepondenceSourceCompositeTypeSetterMethod(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod) {
      return innerDeclaration;
    }
    
    public void update2Element(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      EList<Parameter> _parameters = compositeTypeSetterMethod.getParameters();
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(_parameters);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        EList<Parameter> _parameters_1 = compositeTypeSetterMethod.getParameters();
        final Parameter parameter = _parameters_1.get(0);
        String _entityName = innerDeclaration.getEntityName();
        String _firstUpper = StringExtensions.toFirstUpper(_entityName);
        String _plus = ("set" + _firstUpper);
        parameter.setName(_plus);
      }
    }
    
    public void update1Element(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      String _entityName = innerDeclaration.getEntityName();
      String _firstUpper = StringExtensions.toFirstUpper(_entityName);
      String _plus = ("get" + _firstUpper);
      compositeTypeGetterMethod.setName(_plus);
    }
  }
  
  public RenameInnerDeclarationImplementationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration innerDeclaration) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.RenameInnerDeclarationImplementationEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.innerDeclaration = innerDeclaration;
  }
  
  private InnerDeclaration innerDeclaration;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameInnerDeclarationImplementationEffect with input:");
    getLogger().debug("   InnerDeclaration: " + this.innerDeclaration);
    
    Field compositeTypeField = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeTypeField(innerDeclaration), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    if (compositeTypeField == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeField);
    ClassMethod compositeTypeGetterMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeTypeGetterMethod(innerDeclaration, compositeTypeField), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(innerDeclaration, compositeTypeField));
    if (compositeTypeGetterMethod == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeGetterMethod);
    ClassMethod compositeTypeSetterMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeTypeSetterMethod(innerDeclaration, compositeTypeField, compositeTypeGetterMethod), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(innerDeclaration, compositeTypeField, compositeTypeGetterMethod));
    if (compositeTypeSetterMethod == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeSetterMethod);
    // val updatedElement userExecution.getElement1(innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    userExecution.update0Element(innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    
    // val updatedElement userExecution.getElement2(innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    userExecution.update1Element(innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    
    // val updatedElement userExecution.getElement3(innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    userExecution.update2Element(innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    
    postprocessElementStates();
  }
}
