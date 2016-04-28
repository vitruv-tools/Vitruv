package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
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

@SuppressWarnings("all")
public class ChangeNameOfInnerDeclarationEffect extends AbstractEffectRealization {
  public ChangeNameOfInnerDeclarationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private InnerDeclaration innerDeclaration;
  
  private boolean isInnerDeclarationSet;
  
  public void setInnerDeclaration(final InnerDeclaration innerDeclaration) {
    this.innerDeclaration = innerDeclaration;
    this.isInnerDeclarationSet = true;
  }
  
  public boolean allParametersSet() {
    return isInnerDeclarationSet;
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
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine ChangeNameOfInnerDeclarationEffect with input:");
    getLogger().debug("   InnerDeclaration: " + this.innerDeclaration);
    
    Field compositeTypeField = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceCompositeTypeField(innerDeclaration), // correspondence source supplier
    	(Field _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Field.class,
    	false, true, false);
    ClassMethod compositeTypeGetterMethod = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceCompositeTypeGetterMethod(innerDeclaration), // correspondence source supplier
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	() -> getRetrieveTag0(innerDeclaration), // tag supplier
    	ClassMethod.class,
    	false, true, false);
    ClassMethod compositeTypeSetterMethod = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceCompositeTypeSetterMethod(innerDeclaration), // correspondence source supplier
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	() -> getRetrieveTag1(innerDeclaration), // tag supplier
    	ClassMethod.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.ChangeNameOfInnerDeclarationEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
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
}
