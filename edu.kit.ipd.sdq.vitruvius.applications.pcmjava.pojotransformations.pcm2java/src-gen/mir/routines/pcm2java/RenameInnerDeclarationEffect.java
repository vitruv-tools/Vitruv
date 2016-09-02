package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

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
public class RenameInnerDeclarationEffect extends AbstractEffectRealization {
  public RenameInnerDeclarationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEAttribute<InnerDeclaration, String> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEAttribute<InnerDeclaration, String> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameInnerDeclarationEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEAttribute: " + this.change);
    
    Field compositeTypeField = getCorrespondingElement(
    	getCorrepondenceSourceCompositeTypeField(change), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    if (compositeTypeField == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeField);
    ClassMethod compositeTypeGetterMethod = getCorrespondingElement(
    	getCorrepondenceSourceCompositeTypeGetterMethod(change), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	getRetrieveTag0(change));
    if (compositeTypeGetterMethod == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeGetterMethod);
    ClassMethod compositeTypeSetterMethod = getCorrespondingElement(
    	getCorrepondenceSourceCompositeTypeSetterMethod(change), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	getRetrieveTag1(change));
    if (compositeTypeSetterMethod == null) {
    	return;
    }
    initializeRetrieveElementState(compositeTypeSetterMethod);
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenameInnerDeclarationEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    postprocessElementStates();
  }
  
  private String getRetrieveTag0(final ReplaceSingleValuedEAttribute<InnerDeclaration, String> change) {
    return "getter";
  }
  
  private String getRetrieveTag1(final ReplaceSingleValuedEAttribute<InnerDeclaration, String> change) {
    return "setter";
  }
  
  private EObject getCorrepondenceSourceCompositeTypeField(final ReplaceSingleValuedEAttribute<InnerDeclaration, String> change) {
    InnerDeclaration _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private EObject getCorrepondenceSourceCompositeTypeGetterMethod(final ReplaceSingleValuedEAttribute<InnerDeclaration, String> change) {
    InnerDeclaration _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private EObject getCorrepondenceSourceCompositeTypeSetterMethod(final ReplaceSingleValuedEAttribute<InnerDeclaration, String> change) {
    InnerDeclaration _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEAttribute<InnerDeclaration, String> change, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      final String newName = change.getNewValue();
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
