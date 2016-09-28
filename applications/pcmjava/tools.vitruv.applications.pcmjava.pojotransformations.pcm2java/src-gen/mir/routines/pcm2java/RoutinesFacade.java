package mir.routines.pcm2java;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  public void createRepositorySubPackages(final Repository repository) {
    mir.routines.pcm2java.CreateRepositorySubPackagesRoutine effect = new mir.routines.pcm2java.CreateRepositorySubPackagesRoutine(this.executionState, calledBy,
    	repository);
    effect.applyRoutine();
  }
  
  public void renamePackageForRepository(final Repository repository) {
    mir.routines.pcm2java.RenamePackageForRepositoryRoutine effect = new mir.routines.pcm2java.RenamePackageForRepositoryRoutine(this.executionState, calledBy,
    	repository);
    effect.applyRoutine();
  }
  
  public void createImplementationForSystem(final org.palladiosimulator.pcm.system.System system) {
    mir.routines.pcm2java.CreateImplementationForSystemRoutine effect = new mir.routines.pcm2java.CreateImplementationForSystemRoutine(this.executionState, calledBy,
    	system);
    effect.applyRoutine();
  }
  
  public void changeSystemImplementationName(final org.palladiosimulator.pcm.system.System system) {
    mir.routines.pcm2java.ChangeSystemImplementationNameRoutine effect = new mir.routines.pcm2java.ChangeSystemImplementationNameRoutine(this.executionState, calledBy,
    	system);
    effect.applyRoutine();
  }
  
  public void addAssemblyContextToComposedStructure(final ComposedStructure composedStructure, final AssemblyContext assemblyContext) {
    mir.routines.pcm2java.AddAssemblyContextToComposedStructureRoutine effect = new mir.routines.pcm2java.AddAssemblyContextToComposedStructureRoutine(this.executionState, calledBy,
    	composedStructure, assemblyContext);
    effect.applyRoutine();
  }
  
  public void createComponentImplementation(final RepositoryComponent component) {
    mir.routines.pcm2java.CreateComponentImplementationRoutine effect = new mir.routines.pcm2java.CreateComponentImplementationRoutine(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void createImplementationForComponent(final RepositoryComponent component) {
    mir.routines.pcm2java.CreateImplementationForComponentRoutine effect = new mir.routines.pcm2java.CreateImplementationForComponentRoutine(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void renameComponentPackageAndClass(final RepositoryComponent component) {
    mir.routines.pcm2java.RenameComponentPackageAndClassRoutine effect = new mir.routines.pcm2java.RenameComponentPackageAndClassRoutine(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void renameComponentClass(final RepositoryComponent component) {
    mir.routines.pcm2java.RenameComponentClassRoutine effect = new mir.routines.pcm2java.RenameComponentClassRoutine(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void createInterfaceImplementation(final Interface interf) {
    mir.routines.pcm2java.CreateInterfaceImplementationRoutine effect = new mir.routines.pcm2java.CreateInterfaceImplementationRoutine(this.executionState, calledBy,
    	interf);
    effect.applyRoutine();
  }
  
  public void renameInterface(final OperationInterface interf) {
    mir.routines.pcm2java.RenameInterfaceRoutine effect = new mir.routines.pcm2java.RenameInterfaceRoutine(this.executionState, calledBy,
    	interf);
    effect.applyRoutine();
  }
  
  public void createCompositeDataTypeImplementation(final CompositeDataType dataType) {
    mir.routines.pcm2java.CreateCompositeDataTypeImplementationRoutine effect = new mir.routines.pcm2java.CreateCompositeDataTypeImplementationRoutine(this.executionState, calledBy,
    	dataType);
    effect.applyRoutine();
  }
  
  public void renameCompositeDataType(final CompositeDataType compositeDataType) {
    mir.routines.pcm2java.RenameCompositeDataTypeRoutine effect = new mir.routines.pcm2java.RenameCompositeDataTypeRoutine(this.executionState, calledBy,
    	compositeDataType);
    effect.applyRoutine();
  }
  
  public void createCollectionDataTypeImplementation(final CollectionDataType dataType) {
    mir.routines.pcm2java.CreateCollectionDataTypeImplementationRoutine effect = new mir.routines.pcm2java.CreateCollectionDataTypeImplementationRoutine(this.executionState, calledBy,
    	dataType);
    effect.applyRoutine();
  }
  
  public void addSuperTypeToDataType(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    mir.routines.pcm2java.AddSuperTypeToDataTypeRoutine effect = new mir.routines.pcm2java.AddSuperTypeToDataTypeRoutine(this.executionState, calledBy,
    	dataType, innerTypeReference, superTypeQualifiedName);
    effect.applyRoutine();
  }
  
  public void renameCollectionDataType(final CollectionDataType collectionDataType) {
    mir.routines.pcm2java.RenameCollectionDataTypeRoutine effect = new mir.routines.pcm2java.RenameCollectionDataTypeRoutine(this.executionState, calledBy,
    	collectionDataType);
    effect.applyRoutine();
  }
  
  public void createInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2java.CreateInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2java.CreateInnerDeclarationImplementationRoutine(this.executionState, calledBy,
    	innerDeclaration);
    effect.applyRoutine();
  }
  
  public void addInnerDeclarationToCompositeDataType(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    mir.routines.pcm2java.AddInnerDeclarationToCompositeDataTypeRoutine effect = new mir.routines.pcm2java.AddInnerDeclarationToCompositeDataTypeRoutine(this.executionState, calledBy,
    	dataType, innerDeclaration, dataTypeReference);
    effect.applyRoutine();
  }
  
  public void renameInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2java.RenameInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2java.RenameInnerDeclarationImplementationRoutine(this.executionState, calledBy,
    	innerDeclaration);
    effect.applyRoutine();
  }
  
  public void changeTypeOfInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2java.ChangeTypeOfInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2java.ChangeTypeOfInnerDeclarationImplementationRoutine(this.executionState, calledBy,
    	innerDeclaration);
    effect.applyRoutine();
  }
  
  public void changeInnerDeclarationType(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    mir.routines.pcm2java.ChangeInnerDeclarationTypeRoutine effect = new mir.routines.pcm2java.ChangeInnerDeclarationTypeRoutine(this.executionState, calledBy,
    	innerDeclaration, newTypeReference);
    effect.applyRoutine();
  }
  
  public void createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.pcm2java.CreateJavaPackageRoutine effect = new mir.routines.pcm2java.CreateJavaPackageRoutine(this.executionState, calledBy,
    	sourceElementMappedToPackage, parentPackage, packageName, newTag);
    effect.applyRoutine();
  }
  
  public void renameJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2java.RenameJavaPackageRoutine effect = new mir.routines.pcm2java.RenameJavaPackageRoutine(this.executionState, calledBy,
    	sourceElementMappedToPackage, parentPackage, packageName, expectedTag);
    effect.applyRoutine();
  }
  
  public void deleteJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2java.DeleteJavaPackageRoutine effect = new mir.routines.pcm2java.DeleteJavaPackageRoutine(this.executionState, calledBy,
    	sourceElementMappedToPackage, packageName, expectedTag);
    effect.applyRoutine();
  }
  
  public void createJavaClass(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2java.CreateJavaClassRoutine effect = new mir.routines.pcm2java.CreateJavaClassRoutine(this.executionState, calledBy,
    	sourceElementMappedToClass, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void createJavaInterface(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2java.CreateJavaInterfaceRoutine effect = new mir.routines.pcm2java.CreateJavaInterfaceRoutine(this.executionState, calledBy,
    	sourceElementMappedToClass, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void createCompilationUnit(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    mir.routines.pcm2java.CreateCompilationUnitRoutine effect = new mir.routines.pcm2java.CreateCompilationUnitRoutine(this.executionState, calledBy,
    	sourceElementMappedToClass, classifier, containingPackage);
    effect.applyRoutine();
  }
  
  public void renameJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2java.RenameJavaClassifierRoutine effect = new mir.routines.pcm2java.RenameJavaClassifierRoutine(this.executionState, calledBy,
    	classSourceElement, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void deleteJavaClassifier(final NamedElement sourceElement) {
    mir.routines.pcm2java.DeleteJavaClassifierRoutine effect = new mir.routines.pcm2java.DeleteJavaClassifierRoutine(this.executionState, calledBy,
    	sourceElement);
    effect.applyRoutine();
  }
  
  public void addProvidedRole(final OperationProvidedRole providedRole) {
    mir.routines.pcm2java.AddProvidedRoleRoutine effect = new mir.routines.pcm2java.AddProvidedRoleRoutine(this.executionState, calledBy,
    	providedRole);
    effect.applyRoutine();
  }
  
  public void removeProvidedRole(final ProvidedRole providedRole) {
    mir.routines.pcm2java.RemoveProvidedRoleRoutine effect = new mir.routines.pcm2java.RemoveProvidedRoleRoutine(this.executionState, calledBy,
    	providedRole);
    effect.applyRoutine();
  }
  
  public void addRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2java.AddRequiredRoleRoutine effect = new mir.routines.pcm2java.AddRequiredRoleRoutine(this.executionState, calledBy,
    	requiredRole);
    effect.applyRoutine();
  }
  
  public void addParameterAndAssignmentToConstructor(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName) {
    mir.routines.pcm2java.AddParameterAndAssignmentToConstructorRoutine effect = new mir.routines.pcm2java.AddParameterAndAssignmentToConstructorRoutine(this.executionState, calledBy,
    	parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName);
    effect.applyRoutine();
  }
  
  public void removeRequiredRole(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    mir.routines.pcm2java.RemoveRequiredRoleRoutine effect = new mir.routines.pcm2java.RemoveRequiredRoleRoutine(this.executionState, calledBy,
    	requiredRole, requiringEntity);
    effect.applyRoutine();
  }
  
  public void removeParameterToFieldAssignmentFromConstructor(final Constructor ctor, final String fieldName) {
    mir.routines.pcm2java.RemoveParameterToFieldAssignmentFromConstructorRoutine effect = new mir.routines.pcm2java.RemoveParameterToFieldAssignmentFromConstructorRoutine(this.executionState, calledBy,
    	ctor, fieldName);
    effect.applyRoutine();
  }
  
  public void removeCorrespondingParameterFromConstructor(final Constructor ctor, final NamedElement correspondenceSource) {
    mir.routines.pcm2java.RemoveCorrespondingParameterFromConstructorRoutine effect = new mir.routines.pcm2java.RemoveCorrespondingParameterFromConstructorRoutine(this.executionState, calledBy,
    	ctor, correspondenceSource);
    effect.applyRoutine();
  }
  
  public void reinitializeOperationRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2java.ReinitializeOperationRequiredRoleRoutine effect = new mir.routines.pcm2java.ReinitializeOperationRequiredRoleRoutine(this.executionState, calledBy,
    	requiredRole);
    effect.applyRoutine();
  }
  
  public void createMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2java.CreateMethodForOperationSignatureRoutine effect = new mir.routines.pcm2java.CreateMethodForOperationSignatureRoutine(this.executionState, calledBy,
    	operationSignature);
    effect.applyRoutine();
  }
  
  public void renameMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2java.RenameMethodForOperationSignatureRoutine effect = new mir.routines.pcm2java.RenameMethodForOperationSignatureRoutine(this.executionState, calledBy,
    	operationSignature);
    effect.applyRoutine();
  }
  
  public void changeReturnTypeOfMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2java.ChangeReturnTypeOfMethodForOperationSignatureRoutine effect = new mir.routines.pcm2java.ChangeReturnTypeOfMethodForOperationSignatureRoutine(this.executionState, calledBy,
    	operationSignature);
    effect.applyRoutine();
  }
  
  public void changeInterfaceMethodReturnType(final InterfaceMethod interfaceMethod, final DataType returnType) {
    mir.routines.pcm2java.ChangeInterfaceMethodReturnTypeRoutine effect = new mir.routines.pcm2java.ChangeInterfaceMethodReturnTypeRoutine(this.executionState, calledBy,
    	interfaceMethod, returnType);
    effect.applyRoutine();
  }
  
  public void deleteMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2java.DeleteMethodForOperationSignatureRoutine effect = new mir.routines.pcm2java.DeleteMethodForOperationSignatureRoutine(this.executionState, calledBy,
    	operationSignature);
    effect.applyRoutine();
  }
  
  public void createParameter(final Parameter parameter) {
    mir.routines.pcm2java.CreateParameterRoutine effect = new mir.routines.pcm2java.CreateParameterRoutine(this.executionState, calledBy,
    	parameter);
    effect.applyRoutine();
  }
  
  public void renameParameter(final Parameter parameter) {
    mir.routines.pcm2java.RenameParameterRoutine effect = new mir.routines.pcm2java.RenameParameterRoutine(this.executionState, calledBy,
    	parameter);
    effect.applyRoutine();
  }
  
  public void changeParameterType(final Parameter parameter) {
    mir.routines.pcm2java.ChangeParameterTypeRoutine effect = new mir.routines.pcm2java.ChangeParameterTypeRoutine(this.executionState, calledBy,
    	parameter);
    effect.applyRoutine();
  }
  
  public void deleteParameter(final OperationSignature signature, final Parameter parameter) {
    mir.routines.pcm2java.DeleteParameterRoutine effect = new mir.routines.pcm2java.DeleteParameterRoutine(this.executionState, calledBy,
    	signature, parameter);
    effect.applyRoutine();
  }
  
  public void createMethodForResourceDemandingBehavior(final ResourceDemandingInternalBehaviour behavior) {
    mir.routines.pcm2java.CreateMethodForResourceDemandingBehaviorRoutine effect = new mir.routines.pcm2java.CreateMethodForResourceDemandingBehaviorRoutine(this.executionState, calledBy,
    	behavior);
    effect.applyRoutine();
  }
  
  public void renameMethodForResourceDemandingBehavior(final ResourceDemandingInternalBehaviour behavior) {
    mir.routines.pcm2java.RenameMethodForResourceDemandingBehaviorRoutine effect = new mir.routines.pcm2java.RenameMethodForResourceDemandingBehaviorRoutine(this.executionState, calledBy,
    	behavior);
    effect.applyRoutine();
  }
  
  public void deleteMethodForResourceDemandingBehavior(final ResourceDemandingInternalBehaviour behavior) {
    mir.routines.pcm2java.DeleteMethodForResourceDemandingBehaviorRoutine effect = new mir.routines.pcm2java.DeleteMethodForResourceDemandingBehaviorRoutine(this.executionState, calledBy,
    	behavior);
    effect.applyRoutine();
  }
  
  public void createSEFF(final ServiceEffectSpecification seff) {
    mir.routines.pcm2java.CreateSEFFRoutine effect = new mir.routines.pcm2java.CreateSEFFRoutine(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
  
  public void changeMethodForSeff(final ResourceDemandingSEFF seff) {
    mir.routines.pcm2java.ChangeMethodForSeffRoutine effect = new mir.routines.pcm2java.ChangeMethodForSeffRoutine(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
  
  public void updateSEFFImplementingMethodName(final ServiceEffectSpecification seff) {
    mir.routines.pcm2java.UpdateSEFFImplementingMethodNameRoutine effect = new mir.routines.pcm2java.UpdateSEFFImplementingMethodNameRoutine(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
  
  public void deleteMethodForSeff(final ServiceEffectSpecification seff) {
    mir.routines.pcm2java.DeleteMethodForSeffRoutine effect = new mir.routines.pcm2java.DeleteMethodForSeffRoutine(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
}
