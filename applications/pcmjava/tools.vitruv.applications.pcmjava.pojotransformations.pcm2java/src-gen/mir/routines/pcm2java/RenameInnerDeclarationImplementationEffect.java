package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
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
  public RenameInnerDeclarationImplementationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration innerDeclaration) {
    super(responseExecutionState, calledBy);
    				this.innerDeclaration = innerDeclaration;
  }
  
  private InnerDeclaration innerDeclaration;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    private void executeUserOperations(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      final String newName = innerDeclaration.getEntityName();
      compositeTypeField.setName(newName);
      String _firstUpper = StringExtensions.toFirstUpper(newName);
      String _plus = ("get" + _firstUpper);
      compositeTypeGetterMethod.setName(_plus);
      EList<Parameter> _parameters = compositeTypeSetterMethod.getParameters();
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(_parameters);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        EList<Parameter> _parameters_1 = compositeTypeSetterMethod.getParameters();
        final Parameter parameter = _parameters_1.get(0);
        String _firstUpper_1 = StringExtensions.toFirstUpper(newName);
        String _plus_1 = ("set" + _firstUpper_1);
        parameter.setName(_plus_1);
      }
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    @Extension
    private RoutinesFacade effectFacade;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameInnerDeclarationImplementationEffect with input:");
    getLogger().debug("   InnerDeclaration: " + this.innerDeclaration);
    
    Field compositeTypeField = getCorrespondingElement(
    	getCorrepondenceSourceCompositeTypeField(innerDeclaration), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    if (compositeTypeField == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeField);
    ClassMethod compositeTypeGetterMethod = getCorrespondingElement(
    	getCorrepondenceSourceCompositeTypeGetterMethod(innerDeclaration), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	getRetrieveTag0(innerDeclaration));
    if (compositeTypeGetterMethod == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeGetterMethod);
    ClassMethod compositeTypeSetterMethod = getCorrespondingElement(
    	getCorrepondenceSourceCompositeTypeSetterMethod(innerDeclaration), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	getRetrieveTag1(innerDeclaration));
    if (compositeTypeSetterMethod == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeSetterMethod);
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenameInnerDeclarationImplementationEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    postprocessElementStates();
  }
  
  private String getRetrieveTag0(final InnerDeclaration innerDeclaration) {
    return "getter";
  }
  
  private String getRetrieveTag1(final InnerDeclaration innerDeclaration) {
    return "setter";
  }
  
  private EObject getCorrepondenceSourceCompositeTypeField(final InnerDeclaration innerDeclaration) {
    return innerDeclaration;
  }
  
  private EObject getCorrepondenceSourceCompositeTypeGetterMethod(final InnerDeclaration innerDeclaration) {
    return innerDeclaration;
  }
  
  private EObject getCorrepondenceSourceCompositeTypeSetterMethod(final InnerDeclaration innerDeclaration) {
    return innerDeclaration;
  }
}
