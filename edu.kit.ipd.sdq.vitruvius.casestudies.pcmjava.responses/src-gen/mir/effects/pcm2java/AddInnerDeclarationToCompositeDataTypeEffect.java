package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.PCM2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
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
  public AddInnerDeclarationToCompositeDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CompositeDataType compositeDataType;
  
  private InnerDeclaration innerDeclaration;
  
  private TypeReference dataTypeReference;
  
  private boolean isCompositeDataTypeSet;
  
  private boolean isInnerDeclarationSet;
  
  private boolean isDataTypeReferenceSet;
  
  public void setCompositeDataType(final CompositeDataType compositeDataType) {
    this.compositeDataType = compositeDataType;
    this.isCompositeDataTypeSet = true;
  }
  
  public void setInnerDeclaration(final InnerDeclaration innerDeclaration) {
    this.innerDeclaration = innerDeclaration;
    this.isInnerDeclarationSet = true;
  }
  
  public void setDataTypeReference(final TypeReference dataTypeReference) {
    this.dataTypeReference = dataTypeReference;
    this.isDataTypeReferenceSet = true;
  }
  
  public boolean allParametersSet() {
    return isCompositeDataTypeSet&&isInnerDeclarationSet&&isDataTypeReferenceSet;
  }
  
  private EObject getCorrepondenceSourceInnerDataTypeField(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    return innerDeclaration;
  }
  
  private EObject getCorrepondenceSourceSetterMethod(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    return innerDeclaration;
  }
  
  private String getTagSetterMethod(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    return "setter";
  }
  
  private EObject getCorrepondenceSourceDataTypeClass(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    return compositeDataType;
  }
  
  private EObject getCorrepondenceSourceGetterMethod(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    return innerDeclaration;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect AddInnerDeclarationToCompositeDataTypeEffect with input:");
    getLogger().debug("   CompositeDataType: " + this.compositeDataType);
    getLogger().debug("   InnerDeclaration: " + this.innerDeclaration);
    getLogger().debug("   TypeReference: " + this.dataTypeReference);
    
    Field innerDataTypeField = initializeCreateElementState(
    	() -> getCorrepondenceSourceInnerDataTypeField(compositeDataType, innerDeclaration, dataTypeReference), // correspondence source supplier
    	() -> MembersFactoryImpl.eINSTANCE.createField(), // element creation supplier
    	() -> null, // tag supplier
    	Field.class);
    ClassMethod getterMethod = initializeCreateElementState(
    	() -> getCorrepondenceSourceGetterMethod(compositeDataType, innerDeclaration, dataTypeReference), // correspondence source supplier
    	() -> MembersFactoryImpl.eINSTANCE.createClassMethod(), // element creation supplier
    	() -> getTagGetterMethod(compositeDataType, innerDeclaration, dataTypeReference), // tag supplier
    	ClassMethod.class);
    ClassMethod setterMethod = initializeCreateElementState(
    	() -> getCorrepondenceSourceSetterMethod(compositeDataType, innerDeclaration, dataTypeReference), // correspondence source supplier
    	() -> MembersFactoryImpl.eINSTANCE.createClassMethod(), // element creation supplier
    	() -> getTagSetterMethod(compositeDataType, innerDeclaration, dataTypeReference), // tag supplier
    	ClassMethod.class);
    org.emftext.language.java.classifiers.Class dataTypeClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceDataTypeClass(compositeDataType, innerDeclaration, dataTypeReference), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,	false);
    preProcessElements();
    new mir.effects.pcm2java.AddInnerDeclarationToCompositeDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	compositeDataType, innerDeclaration, dataTypeReference, innerDataTypeField, getterMethod, setterMethod, dataTypeClass);
    postProcessElements();
  }
  
  private String getTagGetterMethod(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    return "getter";
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod, final org.emftext.language.java.classifiers.Class dataTypeClass) {
      TypeReference _copy = EcoreUtil.<TypeReference>copy(dataTypeReference);
      String _entityName = innerDeclaration.getEntityName();
      PCM2JavaHelper.createPrivateField(innerDataTypeField, _copy, _entityName);
      PCM2JavaHelper.createSetter(innerDataTypeField, setterMethod);
      PCM2JavaHelper.createGetter(innerDataTypeField, getterMethod);
      EList<Member> _members = dataTypeClass.getMembers();
      _members.add(innerDataTypeField);
      EList<Member> _members_1 = dataTypeClass.getMembers();
      _members_1.add(getterMethod);
      EList<Member> _members_2 = dataTypeClass.getMembers();
      _members_2.add(setterMethod);
      EList<Member> _members_3 = dataTypeClass.getMembers();
      PCM2JavaHelper.sortMembers(_members_3);
    }
  }
}
