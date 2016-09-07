package mir.routines.pcm2java;

import tools.vitruvius.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;

@SuppressWarnings("all")
public class AddInnerDeclarationToCompositeDataTypeEffect extends AbstractEffectRealization {
  public AddInnerDeclarationToCompositeDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    super(responseExecutionState, calledBy);
    				this.compositeDataType = compositeDataType;this.innerDeclaration = innerDeclaration;this.dataTypeReference = dataTypeReference;
  }
  
  private CompositeDataType compositeDataType;
  
  private InnerDeclaration innerDeclaration;
  
  private TypeReference dataTypeReference;
  
  private EObject getElement0(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
    return innerDataTypeField;
  }
  
  private EObject getElement1(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
    return getterMethod;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddInnerDeclarationToCompositeDataTypeEffect with input:");
    getLogger().debug("   CompositeDataType: " + this.compositeDataType);
    getLogger().debug("   InnerDeclaration: " + this.innerDeclaration);
    getLogger().debug("   TypeReference: " + this.dataTypeReference);
    
    org.emftext.language.java.classifiers.Class dataTypeClass = getCorrespondingElement(
    	getCorrepondenceSourceDataTypeClass(compositeDataType, innerDeclaration, dataTypeReference), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (dataTypeClass == null) {
    	return;
    }
    initializeRetrieveElementState(dataTypeClass);
    Field innerDataTypeField = MembersFactoryImpl.eINSTANCE.createField();
    initializeCreateElementState(innerDataTypeField);
    ClassMethod getterMethod = MembersFactoryImpl.eINSTANCE.createClassMethod();
    initializeCreateElementState(getterMethod);
    ClassMethod setterMethod = MembersFactoryImpl.eINSTANCE.createClassMethod();
    initializeCreateElementState(setterMethod);
    
    addCorrespondenceBetween(getElement0(compositeDataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod), getElement3(compositeDataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod), "");
    addCorrespondenceBetween(getElement1(compositeDataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod), getElement4(compositeDataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod), getTag0(compositeDataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod));
    addCorrespondenceBetween(getElement2(compositeDataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod), getElement5(compositeDataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod), getTag1(compositeDataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod));
    preprocessElementStates();
    new mir.routines.pcm2java.AddInnerDeclarationToCompositeDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	compositeDataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod);
    postprocessElementStates();
  }
  
  private EObject getElement4(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
    return innerDeclaration;
  }
  
  private EObject getElement5(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
    return innerDeclaration;
  }
  
  private EObject getElement2(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
    return setterMethod;
  }
  
  private EObject getCorrepondenceSourceDataTypeClass(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    return compositeDataType;
  }
  
  private EObject getElement3(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
    return innerDeclaration;
  }
  
  private String getTag1(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
    return "setter";
  }
  
  private String getTag0(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
    return "getter";
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      TypeReference _copy = EcoreUtil.<TypeReference>copy(dataTypeReference);
      String _entityName = innerDeclaration.getEntityName();
      Pcm2JavaHelper.createPrivateField(innerDataTypeField, _copy, _entityName);
      Pcm2JavaHelper.createSetter(innerDataTypeField, setterMethod);
      Pcm2JavaHelper.createGetter(innerDataTypeField, getterMethod);
      EList<Member> _members = dataTypeClass.getMembers();
      _members.add(innerDataTypeField);
      EList<Member> _members_1 = dataTypeClass.getMembers();
      _members_1.add(getterMethod);
      EList<Member> _members_2 = dataTypeClass.getMembers();
      _members_2.add(setterMethod);
      EList<Member> _members_3 = dataTypeClass.getMembers();
      Pcm2JavaHelper.sortMembers(_members_3);
    }
  }
}
