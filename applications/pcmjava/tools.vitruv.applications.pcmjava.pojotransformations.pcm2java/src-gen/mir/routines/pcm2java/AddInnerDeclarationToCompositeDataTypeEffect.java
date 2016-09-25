package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddInnerDeclarationToCompositeDataTypeEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public void update0Element(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      EList<Member> _members = dataTypeClass.getMembers();
      _members.add(innerDataTypeField);
      EList<Member> _members_1 = dataTypeClass.getMembers();
      _members_1.add(getterMethod);
      EList<Member> _members_2 = dataTypeClass.getMembers();
      _members_2.add(setterMethod);
      EList<Member> _members_3 = dataTypeClass.getMembers();
      Pcm2JavaHelper.sortMembers(_members_3);
    }
    
    public EObject getCorrepondenceSourceDataTypeClass(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
      return dataType;
    }
    
    public String getTag1(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod) {
      return "getter";
    }
    
    public void updateSetterMethodElement(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      Pcm2JavaHelper.createSetter(innerDataTypeField, setterMethod);
    }
    
    public EObject getElement6(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      return innerDeclaration;
    }
    
    public String getTag2(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      return "setter";
    }
    
    public EObject getElement7(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      return dataTypeClass;
    }
    
    public EObject getElement1(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField) {
      return innerDataTypeField;
    }
    
    public EObject getElement4(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod) {
      return innerDeclaration;
    }
    
    public EObject getElement5(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      return setterMethod;
    }
    
    public EObject getElement2(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField) {
      return innerDeclaration;
    }
    
    public EObject getElement3(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod) {
      return getterMethod;
    }
    
    public void updateInnerDataTypeFieldElement(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField) {
      TypeReference _copy = EcoreUtil.<TypeReference>copy(dataTypeReference);
      String _entityName = innerDeclaration.getEntityName();
      Pcm2JavaHelper.createPrivateField(innerDataTypeField, _copy, _entityName);
    }
    
    public void updateGetterMethodElement(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod) {
      Pcm2JavaHelper.createGetter(innerDataTypeField, getterMethod);
    }
  }
  
  private AddInnerDeclarationToCompositeDataTypeEffect.EffectUserExecution userExecution;
  
  public AddInnerDeclarationToCompositeDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.AddInnerDeclarationToCompositeDataTypeEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.dataType = dataType;this.innerDeclaration = innerDeclaration;this.dataTypeReference = dataTypeReference;
  }
  
  private CompositeDataType dataType;
  
  private InnerDeclaration innerDeclaration;
  
  private TypeReference dataTypeReference;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddInnerDeclarationToCompositeDataTypeEffect with input:");
    getLogger().debug("   CompositeDataType: " + this.dataType);
    getLogger().debug("   InnerDeclaration: " + this.innerDeclaration);
    getLogger().debug("   TypeReference: " + this.dataTypeReference);
    
    org.emftext.language.java.classifiers.Class dataTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDataTypeClass(dataType, innerDeclaration, dataTypeReference), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (dataTypeClass == null) {
    	return;
    }
    initializeRetrieveElementState(dataTypeClass);
    Field innerDataTypeField = MembersFactoryImpl.eINSTANCE.createField();
    initializeCreateElementState(innerDataTypeField);
    userExecution.updateInnerDataTypeFieldElement(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField);
    
    addCorrespondenceBetween(userExecution.getElement1(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField), userExecution.getElement2(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField), "");
    
    ClassMethod getterMethod = MembersFactoryImpl.eINSTANCE.createClassMethod();
    initializeCreateElementState(getterMethod);
    userExecution.updateGetterMethodElement(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod);
    
    addCorrespondenceBetween(userExecution.getElement3(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod), userExecution.getElement4(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod), userExecution.getTag1(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod));
    
    ClassMethod setterMethod = MembersFactoryImpl.eINSTANCE.createClassMethod();
    initializeCreateElementState(setterMethod);
    userExecution.updateSetterMethodElement(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod);
    
    addCorrespondenceBetween(userExecution.getElement5(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod), userExecution.getElement6(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod), userExecution.getTag2(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod));
    
    // val updatedElement userExecution.getElement7(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod);
    userExecution.update0Element(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod);
    
    preprocessElementStates();
    postprocessElementStates();
  }
}
