package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectsFacade;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

@SuppressWarnings("all")
public class EffectsFacade extends AbstractEffectsFacade {
  public EffectsFacade(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  public void callCreateRepositorySubPackages(final Repository repository) {
    mir.effects.pcm2java.CreateRepositorySubPackagesEffect effect = new mir.effects.pcm2java.CreateRepositorySubPackagesEffect(this.executionState, calledBy);
    effect.setRepository(repository);
    effect.applyEffect();
  }
  
  public void callCreateImplementationForSystem(final org.palladiosimulator.pcm.system.System system) {
    mir.effects.pcm2java.CreateImplementationForSystemEffect effect = new mir.effects.pcm2java.CreateImplementationForSystemEffect(this.executionState, calledBy);
    effect.setSystem(system);
    effect.applyEffect();
  }
  
  public void callAddAssemblyContextToComposedStructure(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext) {
    mir.effects.pcm2java.AddAssemblyContextToComposedStructureEffect effect = new mir.effects.pcm2java.AddAssemblyContextToComposedStructureEffect(this.executionState, calledBy);
    effect.setCompositeComponentClass(compositeComponentClass);
    effect.setAssemblyContext(assemblyContext);
    effect.applyEffect();
  }
  
  public void callCreateImplementationForComponent(final RepositoryComponent component) {
    mir.effects.pcm2java.CreateImplementationForComponentEffect effect = new mir.effects.pcm2java.CreateImplementationForComponentEffect(this.executionState, calledBy);
    effect.setComponent(component);
    effect.applyEffect();
  }
  
  public void callRenameComponentPackageAndClass(final RepositoryComponent component) {
    mir.effects.pcm2java.RenameComponentPackageAndClassEffect effect = new mir.effects.pcm2java.RenameComponentPackageAndClassEffect(this.executionState, calledBy);
    effect.setComponent(component);
    effect.applyEffect();
  }
  
  public void callRenameComponentClass(final RepositoryComponent component) {
    mir.effects.pcm2java.RenameComponentClassEffect effect = new mir.effects.pcm2java.RenameComponentClassEffect(this.executionState, calledBy);
    effect.setComponent(component);
    effect.applyEffect();
  }
  
  public void callRenameInterface(final OperationInterface interf) {
    mir.effects.pcm2java.RenameInterfaceEffect effect = new mir.effects.pcm2java.RenameInterfaceEffect(this.executionState, calledBy);
    effect.setInterf(interf);
    effect.applyEffect();
  }
  
  public void callRenameCompositeDataType(final CompositeDataType compositeDataType) {
    mir.effects.pcm2java.RenameCompositeDataTypeEffect effect = new mir.effects.pcm2java.RenameCompositeDataTypeEffect(this.executionState, calledBy);
    effect.setCompositeDataType(compositeDataType);
    effect.applyEffect();
  }
  
  public void callAddInnerDeclarationToCompositeDataType(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    mir.effects.pcm2java.AddInnerDeclarationToCompositeDataTypeEffect effect = new mir.effects.pcm2java.AddInnerDeclarationToCompositeDataTypeEffect(this.executionState, calledBy);
    effect.setCompositeDataType(compositeDataType);
    effect.setInnerDeclaration(innerDeclaration);
    effect.setDataTypeReference(dataTypeReference);
    effect.applyEffect();
  }
  
  public void callChangeNameOfInnerDeclaration(final InnerDeclaration innerDeclaration) {
    mir.effects.pcm2java.ChangeNameOfInnerDeclarationEffect effect = new mir.effects.pcm2java.ChangeNameOfInnerDeclarationEffect(this.executionState, calledBy);
    effect.setInnerDeclaration(innerDeclaration);
    effect.applyEffect();
  }
  
  public void callChangeInnerDeclarationType(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    mir.effects.pcm2java.ChangeInnerDeclarationTypeEffect effect = new mir.effects.pcm2java.ChangeInnerDeclarationTypeEffect(this.executionState, calledBy);
    effect.setInnerDeclaration(innerDeclaration);
    effect.setNewTypeReference(newTypeReference);
    effect.applyEffect();
  }
  
  public void callCreateJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.effects.pcm2java.CreateJavaPackageEffect effect = new mir.effects.pcm2java.CreateJavaPackageEffect(this.executionState, calledBy);
    effect.setSourceElementMappedToPackage(sourceElementMappedToPackage);
    effect.setParentPackage(parentPackage);
    effect.setPackageName(packageName);
    effect.setNewTag(newTag);
    effect.applyEffect();
  }
  
  public void callRenameJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    mir.effects.pcm2java.RenameJavaPackageEffect effect = new mir.effects.pcm2java.RenameJavaPackageEffect(this.executionState, calledBy);
    effect.setSourceElementMappedToPackage(sourceElementMappedToPackage);
    effect.setParentPackage(parentPackage);
    effect.setPackageName(packageName);
    effect.setExpectedTag(expectedTag);
    effect.applyEffect();
  }
  
  public void callDeleteJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    mir.effects.pcm2java.DeleteJavaPackageEffect effect = new mir.effects.pcm2java.DeleteJavaPackageEffect(this.executionState, calledBy);
    effect.setSourceElementMappedToPackage(sourceElementMappedToPackage);
    effect.setPackageName(packageName);
    effect.setExpectedTag(expectedTag);
    effect.applyEffect();
  }
  
  public void callCreateJavaClass(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.effects.pcm2java.CreateJavaClassEffect effect = new mir.effects.pcm2java.CreateJavaClassEffect(this.executionState, calledBy);
    effect.setSourceElementMappedToClass(sourceElementMappedToClass);
    effect.setContainingPackage(containingPackage);
    effect.setClassName(className);
    effect.applyEffect();
  }
  
  public void callCreateJavaInterface(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.effects.pcm2java.CreateJavaInterfaceEffect effect = new mir.effects.pcm2java.CreateJavaInterfaceEffect(this.executionState, calledBy);
    effect.setSourceElementMappedToClass(sourceElementMappedToClass);
    effect.setContainingPackage(containingPackage);
    effect.setClassName(className);
    effect.applyEffect();
  }
  
  public void callCreateCompilationUnit(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    mir.effects.pcm2java.CreateCompilationUnitEffect effect = new mir.effects.pcm2java.CreateCompilationUnitEffect(this.executionState, calledBy);
    effect.setSourceElementMappedToClass(sourceElementMappedToClass);
    effect.setClassifier(classifier);
    effect.setContainingPackage(containingPackage);
    effect.applyEffect();
  }
  
  public void callRenameJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.effects.pcm2java.RenameJavaClassifierEffect effect = new mir.effects.pcm2java.RenameJavaClassifierEffect(this.executionState, calledBy);
    effect.setClassSourceElement(classSourceElement);
    effect.setContainingPackage(containingPackage);
    effect.setClassName(className);
    effect.applyEffect();
  }
  
  public void callDeleteJavaClassifier(final NamedElement sourceElement) {
    mir.effects.pcm2java.DeleteJavaClassifierEffect effect = new mir.effects.pcm2java.DeleteJavaClassifierEffect(this.executionState, calledBy);
    effect.setSourceElement(sourceElement);
    effect.applyEffect();
  }
  
  public void callAddProvidedRole(final OperationProvidedRole providedRole) {
    mir.effects.pcm2java.AddProvidedRoleEffect effect = new mir.effects.pcm2java.AddProvidedRoleEffect(this.executionState, calledBy);
    effect.setProvidedRole(providedRole);
    effect.applyEffect();
  }
  
  public void callRemoveProvidedRole(final ProvidedRole providedRole) {
    mir.effects.pcm2java.RemoveProvidedRoleEffect effect = new mir.effects.pcm2java.RemoveProvidedRoleEffect(this.executionState, calledBy);
    effect.setProvidedRole(providedRole);
    effect.applyEffect();
  }
  
  public void callAddRequiredRole(final OperationRequiredRole requiredRole) {
    mir.effects.pcm2java.AddRequiredRoleEffect effect = new mir.effects.pcm2java.AddRequiredRoleEffect(this.executionState, calledBy);
    effect.setRequiredRole(requiredRole);
    effect.applyEffect();
  }
  
  public void callAddParameterAndAssignmentToConstructor(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName) {
    mir.effects.pcm2java.AddParameterAndAssignmentToConstructorEffect effect = new mir.effects.pcm2java.AddParameterAndAssignmentToConstructorEffect(this.executionState, calledBy);
    effect.setParameterCorrespondenceSource(parameterCorrespondenceSource);
    effect.setConstructor(constructor);
    effect.setTypeReference(typeReference);
    effect.setFieldToBeAssigned(fieldToBeAssigned);
    effect.setParameterName(parameterName);
    effect.applyEffect();
  }
  
  public void callRemoveRequiredRole(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    mir.effects.pcm2java.RemoveRequiredRoleEffect effect = new mir.effects.pcm2java.RemoveRequiredRoleEffect(this.executionState, calledBy);
    effect.setRequiredRole(requiredRole);
    effect.setRequiringEntity(requiringEntity);
    effect.applyEffect();
  }
  
  public void callRemoveCorrespondingParameterFromConstructor(final Constructor ctor, final NamedElement correspondenceSource) {
    mir.effects.pcm2java.RemoveCorrespondingParameterFromConstructorEffect effect = new mir.effects.pcm2java.RemoveCorrespondingParameterFromConstructorEffect(this.executionState, calledBy);
    effect.setCtor(ctor);
    effect.setCorrespondenceSource(correspondenceSource);
    effect.applyEffect();
  }
  
  public void callReinitializeOperationRequiredRole(final OperationRequiredRole requiredRole) {
    mir.effects.pcm2java.ReinitializeOperationRequiredRoleEffect effect = new mir.effects.pcm2java.ReinitializeOperationRequiredRoleEffect(this.executionState, calledBy);
    effect.setRequiredRole(requiredRole);
    effect.applyEffect();
  }
  
  public void callChangeInterfaceMethodReturnType(final InterfaceMethod interfaceMethod, final DataType returnType) {
    mir.effects.pcm2java.ChangeInterfaceMethodReturnTypeEffect effect = new mir.effects.pcm2java.ChangeInterfaceMethodReturnTypeEffect(this.executionState, calledBy);
    effect.setInterfaceMethod(interfaceMethod);
    effect.setReturnType(returnType);
    effect.applyEffect();
  }
  
  public void callChangeParameterType(final OrdinaryParameter javaParameter, final DataType parameterType) {
    mir.effects.pcm2java.ChangeParameterTypeEffect effect = new mir.effects.pcm2java.ChangeParameterTypeEffect(this.executionState, calledBy);
    effect.setJavaParameter(javaParameter);
    effect.setParameterType(parameterType);
    effect.applyEffect();
  }
  
  public void callCreateSEFF(final ServiceEffectSpecification seff) {
    mir.effects.pcm2java.CreateSEFFEffect effect = new mir.effects.pcm2java.CreateSEFFEffect(this.executionState, calledBy);
    effect.setSeff(seff);
    effect.applyEffect();
  }
  
  public void callUpdateSEFFImplementingMethodName(final ServiceEffectSpecification seff) {
    mir.effects.pcm2java.UpdateSEFFImplementingMethodNameEffect effect = new mir.effects.pcm2java.UpdateSEFFImplementingMethodNameEffect(this.executionState, calledBy);
    effect.setSeff(seff);
    effect.applyEffect();
  }
  
  public void callAddSuperTypeToDataType(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    mir.effects.pcm2java.AddSuperTypeToDataTypeEffect effect = new mir.effects.pcm2java.AddSuperTypeToDataTypeEffect(this.executionState, calledBy);
    effect.setDataType(dataType);
    effect.setInnerTypeReference(innerTypeReference);
    effect.setSuperTypeQualifiedName(superTypeQualifiedName);
    effect.applyEffect();
  }
}
