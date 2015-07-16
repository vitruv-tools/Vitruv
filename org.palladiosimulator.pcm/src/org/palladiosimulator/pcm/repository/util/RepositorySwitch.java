/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.ImplementationComponentType;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.RequiredCharacterisation;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

import de.uka.ipd.sdq.identifier.Identifier;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the
 * call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for
 * each class of the model, starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the result of the switch.
 * <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage
 * @generated
 */
public class RepositorySwitch<T> {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static RepositoryPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public RepositorySwitch() {
        if (modelPackage == null) {
            modelPackage = RepositoryPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result;
     * it yields that result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(final EObject theEObject) {
        return this.doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result;
     * it yields that result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(final EClass theEClass, final EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return this.doSwitch(theEClass.getClassifierID(), theEObject);
        } else {
            final List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return eSuperTypes.isEmpty() ? this.defaultCase(theEObject) : this.doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result;
     * it yields that result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(final int classifierID, final EObject theEObject) {
        switch (classifierID) {
        case RepositoryPackage.PASSIVE_RESOURCE: {
            final PassiveResource passiveResource = (PassiveResource) theEObject;
            T result = this.casePassiveResource(passiveResource);
            if (result == null) {
                result = this.caseEntity(passiveResource);
            }
            if (result == null) {
                result = this.caseIdentifier(passiveResource);
            }
            if (result == null) {
                result = this.caseNamedElement(passiveResource);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.BASIC_COMPONENT: {
            final BasicComponent basicComponent = (BasicComponent) theEObject;
            T result = this.caseBasicComponent(basicComponent);
            if (result == null) {
                result = this.caseImplementationComponentType(basicComponent);
            }
            if (result == null) {
                result = this.caseRepositoryComponent(basicComponent);
            }
            if (result == null) {
                result = this.caseInterfaceProvidingRequiringEntity(basicComponent);
            }
            if (result == null) {
                result = this.caseInterfaceProvidingEntity(basicComponent);
            }
            if (result == null) {
                result = this.caseInterfaceRequiringEntity(basicComponent);
            }
            if (result == null) {
                result = this.caseResourceInterfaceRequiringEntity(basicComponent);
            }
            if (result == null) {
                result = this.caseEntity(basicComponent);
            }
            if (result == null) {
                result = this.caseIdentifier(basicComponent);
            }
            if (result == null) {
                result = this.caseNamedElement(basicComponent);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.IMPLEMENTATION_COMPONENT_TYPE: {
            final ImplementationComponentType implementationComponentType = (ImplementationComponentType) theEObject;
            T result = this.caseImplementationComponentType(implementationComponentType);
            if (result == null) {
                result = this.caseRepositoryComponent(implementationComponentType);
            }
            if (result == null) {
                result = this.caseInterfaceProvidingRequiringEntity(implementationComponentType);
            }
            if (result == null) {
                result = this.caseInterfaceProvidingEntity(implementationComponentType);
            }
            if (result == null) {
                result = this.caseInterfaceRequiringEntity(implementationComponentType);
            }
            if (result == null) {
                result = this.caseResourceInterfaceRequiringEntity(implementationComponentType);
            }
            if (result == null) {
                result = this.caseEntity(implementationComponentType);
            }
            if (result == null) {
                result = this.caseIdentifier(implementationComponentType);
            }
            if (result == null) {
                result = this.caseNamedElement(implementationComponentType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.REPOSITORY_COMPONENT: {
            final RepositoryComponent repositoryComponent = (RepositoryComponent) theEObject;
            T result = this.caseRepositoryComponent(repositoryComponent);
            if (result == null) {
                result = this.caseInterfaceProvidingRequiringEntity(repositoryComponent);
            }
            if (result == null) {
                result = this.caseInterfaceProvidingEntity(repositoryComponent);
            }
            if (result == null) {
                result = this.caseInterfaceRequiringEntity(repositoryComponent);
            }
            if (result == null) {
                result = this.caseResourceInterfaceRequiringEntity(repositoryComponent);
            }
            if (result == null) {
                result = this.caseEntity(repositoryComponent);
            }
            if (result == null) {
                result = this.caseIdentifier(repositoryComponent);
            }
            if (result == null) {
                result = this.caseNamedElement(repositoryComponent);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.PROVIDED_ROLE: {
            final ProvidedRole providedRole = (ProvidedRole) theEObject;
            T result = this.caseProvidedRole(providedRole);
            if (result == null) {
                result = this.caseRole(providedRole);
            }
            if (result == null) {
                result = this.caseEntity(providedRole);
            }
            if (result == null) {
                result = this.caseIdentifier(providedRole);
            }
            if (result == null) {
                result = this.caseNamedElement(providedRole);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.PARAMETER: {
            final Parameter parameter = (Parameter) theEObject;
            T result = this.caseParameter(parameter);
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.DATA_TYPE: {
            final DataType dataType = (DataType) theEObject;
            T result = this.caseDataType(dataType);
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.REPOSITORY: {
            final Repository repository = (Repository) theEObject;
            T result = this.caseRepository(repository);
            if (result == null) {
                result = this.caseEntity(repository);
            }
            if (result == null) {
                result = this.caseIdentifier(repository);
            }
            if (result == null) {
                result = this.caseNamedElement(repository);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.INTERFACE: {
            final Interface interface_ = (Interface) theEObject;
            T result = this.caseInterface(interface_);
            if (result == null) {
                result = this.caseEntity(interface_);
            }
            if (result == null) {
                result = this.caseIdentifier(interface_);
            }
            if (result == null) {
                result = this.caseNamedElement(interface_);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.REQUIRED_CHARACTERISATION: {
            final RequiredCharacterisation requiredCharacterisation = (RequiredCharacterisation) theEObject;
            T result = this.caseRequiredCharacterisation(requiredCharacterisation);
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.EVENT_GROUP: {
            final EventGroup eventGroup = (EventGroup) theEObject;
            T result = this.caseEventGroup(eventGroup);
            if (result == null) {
                result = this.caseInterface(eventGroup);
            }
            if (result == null) {
                result = this.caseEntity(eventGroup);
            }
            if (result == null) {
                result = this.caseIdentifier(eventGroup);
            }
            if (result == null) {
                result = this.caseNamedElement(eventGroup);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.EVENT_TYPE: {
            final EventType eventType = (EventType) theEObject;
            T result = this.caseEventType(eventType);
            if (result == null) {
                result = this.caseSignature(eventType);
            }
            if (result == null) {
                result = this.caseEntity(eventType);
            }
            if (result == null) {
                result = this.caseIdentifier(eventType);
            }
            if (result == null) {
                result = this.caseNamedElement(eventType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.SIGNATURE: {
            final Signature signature = (Signature) theEObject;
            T result = this.caseSignature(signature);
            if (result == null) {
                result = this.caseEntity(signature);
            }
            if (result == null) {
                result = this.caseIdentifier(signature);
            }
            if (result == null) {
                result = this.caseNamedElement(signature);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.EXCEPTION_TYPE: {
            final ExceptionType exceptionType = (ExceptionType) theEObject;
            T result = this.caseExceptionType(exceptionType);
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.INFRASTRUCTURE_SIGNATURE: {
            final InfrastructureSignature infrastructureSignature = (InfrastructureSignature) theEObject;
            T result = this.caseInfrastructureSignature(infrastructureSignature);
            if (result == null) {
                result = this.caseSignature(infrastructureSignature);
            }
            if (result == null) {
                result = this.caseEntity(infrastructureSignature);
            }
            if (result == null) {
                result = this.caseIdentifier(infrastructureSignature);
            }
            if (result == null) {
                result = this.caseNamedElement(infrastructureSignature);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.INFRASTRUCTURE_INTERFACE: {
            final InfrastructureInterface infrastructureInterface = (InfrastructureInterface) theEObject;
            T result = this.caseInfrastructureInterface(infrastructureInterface);
            if (result == null) {
                result = this.caseInterface(infrastructureInterface);
            }
            if (result == null) {
                result = this.caseEntity(infrastructureInterface);
            }
            if (result == null) {
                result = this.caseIdentifier(infrastructureInterface);
            }
            if (result == null) {
                result = this.caseNamedElement(infrastructureInterface);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.INFRASTRUCTURE_REQUIRED_ROLE: {
            final InfrastructureRequiredRole infrastructureRequiredRole = (InfrastructureRequiredRole) theEObject;
            T result = this.caseInfrastructureRequiredRole(infrastructureRequiredRole);
            if (result == null) {
                result = this.caseRequiredRole(infrastructureRequiredRole);
            }
            if (result == null) {
                result = this.caseRole(infrastructureRequiredRole);
            }
            if (result == null) {
                result = this.caseEntity(infrastructureRequiredRole);
            }
            if (result == null) {
                result = this.caseIdentifier(infrastructureRequiredRole);
            }
            if (result == null) {
                result = this.caseNamedElement(infrastructureRequiredRole);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.REQUIRED_ROLE: {
            final RequiredRole requiredRole = (RequiredRole) theEObject;
            T result = this.caseRequiredRole(requiredRole);
            if (result == null) {
                result = this.caseRole(requiredRole);
            }
            if (result == null) {
                result = this.caseEntity(requiredRole);
            }
            if (result == null) {
                result = this.caseIdentifier(requiredRole);
            }
            if (result == null) {
                result = this.caseNamedElement(requiredRole);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.OPERATION_SIGNATURE: {
            final OperationSignature operationSignature = (OperationSignature) theEObject;
            T result = this.caseOperationSignature(operationSignature);
            if (result == null) {
                result = this.caseSignature(operationSignature);
            }
            if (result == null) {
                result = this.caseEntity(operationSignature);
            }
            if (result == null) {
                result = this.caseIdentifier(operationSignature);
            }
            if (result == null) {
                result = this.caseNamedElement(operationSignature);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.OPERATION_INTERFACE: {
            final OperationInterface operationInterface = (OperationInterface) theEObject;
            T result = this.caseOperationInterface(operationInterface);
            if (result == null) {
                result = this.caseInterface(operationInterface);
            }
            if (result == null) {
                result = this.caseEntity(operationInterface);
            }
            if (result == null) {
                result = this.caseIdentifier(operationInterface);
            }
            if (result == null) {
                result = this.caseNamedElement(operationInterface);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.OPERATION_REQUIRED_ROLE: {
            final OperationRequiredRole operationRequiredRole = (OperationRequiredRole) theEObject;
            T result = this.caseOperationRequiredRole(operationRequiredRole);
            if (result == null) {
                result = this.caseRequiredRole(operationRequiredRole);
            }
            if (result == null) {
                result = this.caseRole(operationRequiredRole);
            }
            if (result == null) {
                result = this.caseEntity(operationRequiredRole);
            }
            if (result == null) {
                result = this.caseIdentifier(operationRequiredRole);
            }
            if (result == null) {
                result = this.caseNamedElement(operationRequiredRole);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.SOURCE_ROLE: {
            final SourceRole sourceRole = (SourceRole) theEObject;
            T result = this.caseSourceRole(sourceRole);
            if (result == null) {
                result = this.caseRequiredRole(sourceRole);
            }
            if (result == null) {
                result = this.caseRole(sourceRole);
            }
            if (result == null) {
                result = this.caseEntity(sourceRole);
            }
            if (result == null) {
                result = this.caseIdentifier(sourceRole);
            }
            if (result == null) {
                result = this.caseNamedElement(sourceRole);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.SINK_ROLE: {
            final SinkRole sinkRole = (SinkRole) theEObject;
            T result = this.caseSinkRole(sinkRole);
            if (result == null) {
                result = this.caseProvidedRole(sinkRole);
            }
            if (result == null) {
                result = this.caseRole(sinkRole);
            }
            if (result == null) {
                result = this.caseEntity(sinkRole);
            }
            if (result == null) {
                result = this.caseIdentifier(sinkRole);
            }
            if (result == null) {
                result = this.caseNamedElement(sinkRole);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.OPERATION_PROVIDED_ROLE: {
            final OperationProvidedRole operationProvidedRole = (OperationProvidedRole) theEObject;
            T result = this.caseOperationProvidedRole(operationProvidedRole);
            if (result == null) {
                result = this.caseProvidedRole(operationProvidedRole);
            }
            if (result == null) {
                result = this.caseRole(operationProvidedRole);
            }
            if (result == null) {
                result = this.caseEntity(operationProvidedRole);
            }
            if (result == null) {
                result = this.caseIdentifier(operationProvidedRole);
            }
            if (result == null) {
                result = this.caseNamedElement(operationProvidedRole);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.INFRASTRUCTURE_PROVIDED_ROLE: {
            final InfrastructureProvidedRole infrastructureProvidedRole = (InfrastructureProvidedRole) theEObject;
            T result = this.caseInfrastructureProvidedRole(infrastructureProvidedRole);
            if (result == null) {
                result = this.caseProvidedRole(infrastructureProvidedRole);
            }
            if (result == null) {
                result = this.caseRole(infrastructureProvidedRole);
            }
            if (result == null) {
                result = this.caseEntity(infrastructureProvidedRole);
            }
            if (result == null) {
                result = this.caseIdentifier(infrastructureProvidedRole);
            }
            if (result == null) {
                result = this.caseNamedElement(infrastructureProvidedRole);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.COMPLETE_COMPONENT_TYPE: {
            final CompleteComponentType completeComponentType = (CompleteComponentType) theEObject;
            T result = this.caseCompleteComponentType(completeComponentType);
            if (result == null) {
                result = this.caseRepositoryComponent(completeComponentType);
            }
            if (result == null) {
                result = this.caseInterfaceProvidingRequiringEntity(completeComponentType);
            }
            if (result == null) {
                result = this.caseInterfaceProvidingEntity(completeComponentType);
            }
            if (result == null) {
                result = this.caseInterfaceRequiringEntity(completeComponentType);
            }
            if (result == null) {
                result = this.caseResourceInterfaceRequiringEntity(completeComponentType);
            }
            if (result == null) {
                result = this.caseEntity(completeComponentType);
            }
            if (result == null) {
                result = this.caseIdentifier(completeComponentType);
            }
            if (result == null) {
                result = this.caseNamedElement(completeComponentType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.PROVIDES_COMPONENT_TYPE: {
            final ProvidesComponentType providesComponentType = (ProvidesComponentType) theEObject;
            T result = this.caseProvidesComponentType(providesComponentType);
            if (result == null) {
                result = this.caseRepositoryComponent(providesComponentType);
            }
            if (result == null) {
                result = this.caseInterfaceProvidingRequiringEntity(providesComponentType);
            }
            if (result == null) {
                result = this.caseInterfaceProvidingEntity(providesComponentType);
            }
            if (result == null) {
                result = this.caseInterfaceRequiringEntity(providesComponentType);
            }
            if (result == null) {
                result = this.caseResourceInterfaceRequiringEntity(providesComponentType);
            }
            if (result == null) {
                result = this.caseEntity(providesComponentType);
            }
            if (result == null) {
                result = this.caseIdentifier(providesComponentType);
            }
            if (result == null) {
                result = this.caseNamedElement(providesComponentType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.COMPOSITE_COMPONENT: {
            final CompositeComponent compositeComponent = (CompositeComponent) theEObject;
            T result = this.caseCompositeComponent(compositeComponent);
            if (result == null) {
                result = this.caseComposedProvidingRequiringEntity(compositeComponent);
            }
            if (result == null) {
                result = this.caseImplementationComponentType(compositeComponent);
            }
            if (result == null) {
                result = this.caseComposedStructure(compositeComponent);
            }
            if (result == null) {
                result = this.caseRepositoryComponent(compositeComponent);
            }
            if (result == null) {
                result = this.caseInterfaceProvidingRequiringEntity(compositeComponent);
            }
            if (result == null) {
                result = this.caseInterfaceProvidingEntity(compositeComponent);
            }
            if (result == null) {
                result = this.caseInterfaceRequiringEntity(compositeComponent);
            }
            if (result == null) {
                result = this.caseIdentifier(compositeComponent);
            }
            if (result == null) {
                result = this.caseNamedElement(compositeComponent);
            }
            if (result == null) {
                result = this.caseResourceInterfaceRequiringEntity(compositeComponent);
            }
            if (result == null) {
                result = this.caseEntity(compositeComponent);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.PRIMITIVE_DATA_TYPE: {
            final PrimitiveDataType primitiveDataType = (PrimitiveDataType) theEObject;
            T result = this.casePrimitiveDataType(primitiveDataType);
            if (result == null) {
                result = this.caseDataType(primitiveDataType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.COLLECTION_DATA_TYPE: {
            final CollectionDataType collectionDataType = (CollectionDataType) theEObject;
            T result = this.caseCollectionDataType(collectionDataType);
            if (result == null) {
                result = this.caseEntity(collectionDataType);
            }
            if (result == null) {
                result = this.caseDataType(collectionDataType);
            }
            if (result == null) {
                result = this.caseIdentifier(collectionDataType);
            }
            if (result == null) {
                result = this.caseNamedElement(collectionDataType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.COMPOSITE_DATA_TYPE: {
            final CompositeDataType compositeDataType = (CompositeDataType) theEObject;
            T result = this.caseCompositeDataType(compositeDataType);
            if (result == null) {
                result = this.caseEntity(compositeDataType);
            }
            if (result == null) {
                result = this.caseDataType(compositeDataType);
            }
            if (result == null) {
                result = this.caseIdentifier(compositeDataType);
            }
            if (result == null) {
                result = this.caseNamedElement(compositeDataType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.INNER_DECLARATION: {
            final InnerDeclaration innerDeclaration = (InnerDeclaration) theEObject;
            T result = this.caseInnerDeclaration(innerDeclaration);
            if (result == null) {
                result = this.caseNamedElement(innerDeclaration);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case RepositoryPackage.ROLE: {
            final Role role = (Role) theEObject;
            T result = this.caseRole(role);
            if (result == null) {
                result = this.caseEntity(role);
            }
            if (result == null) {
                result = this.caseIdentifier(role);
            }
            if (result == null) {
                result = this.caseNamedElement(role);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        default:
            return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Passive Resource</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Passive Resource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePassiveResource(final PassiveResource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Basic Component</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Basic Component</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBasicComponent(final BasicComponent object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Implementation Component Type</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Implementation Component Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseImplementationComponentType(final ImplementationComponentType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Component</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Component</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRepositoryComponent(final RepositoryComponent object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Provided Role</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Provided Role</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProvidedRole(final ProvidedRole object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParameter(final Parameter object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Data Type</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Data Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDataType(final DataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Repository</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Repository</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRepository(final Repository object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Interface</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Interface</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInterface(final Interface object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Required Characterisation</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Required Characterisation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRequiredCharacterisation(final RequiredCharacterisation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Event Group</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Event Group</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEventGroup(final EventGroup object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Event Type</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Event Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEventType(final EventType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Signature</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Signature</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSignature(final Signature object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Exception Type</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Exception Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExceptionType(final ExceptionType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Infrastructure Signature</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Infrastructure Signature</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInfrastructureSignature(final InfrastructureSignature object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Infrastructure Interface</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Infrastructure Interface</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInfrastructureInterface(final InfrastructureInterface object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Infrastructure Required Role</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Infrastructure Required Role</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInfrastructureRequiredRole(final InfrastructureRequiredRole object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Required Role</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Required Role</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRequiredRole(final RequiredRole object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Operation Signature</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Operation Signature</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperationSignature(final OperationSignature object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Operation Interface</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Operation Interface</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperationInterface(final OperationInterface object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Operation Required Role</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Operation Required Role</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperationRequiredRole(final OperationRequiredRole object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Source Role</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Source Role</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSourceRole(final SourceRole object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Sink Role</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Sink Role</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSinkRole(final SinkRole object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Operation Provided Role</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Operation Provided Role</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperationProvidedRole(final OperationProvidedRole object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Infrastructure Provided Role</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Infrastructure Provided Role</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInfrastructureProvidedRole(final InfrastructureProvidedRole object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Complete Component Type</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Complete Component Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCompleteComponentType(final CompleteComponentType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Provides Component Type</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Provides Component Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProvidesComponentType(final ProvidesComponentType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Composite Component</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Composite Component</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCompositeComponent(final CompositeComponent object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Primitive Data Type</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Primitive Data Type</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrimitiveDataType(final PrimitiveDataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Collection Data Type</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Collection Data Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCollectionDataType(final CollectionDataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Composite Data Type</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Composite Data Type</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCompositeDataType(final CompositeDataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Inner Declaration</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Inner Declaration</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInnerDeclaration(final InnerDeclaration object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Role</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Role</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRole(final Role object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Identifier</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Identifier</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseIdentifier(final Identifier object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedElement(final NamedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Entity</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEntity(final Entity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Interface Providing Entity</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Interface Providing Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInterfaceProvidingEntity(final InterfaceProvidingEntity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Resource Interface Requiring Entity</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Resource Interface Requiring Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceInterfaceRequiringEntity(final ResourceInterfaceRequiringEntity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Interface Requiring Entity</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Interface Requiring Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInterfaceRequiringEntity(final InterfaceRequiringEntity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Interface Providing Requiring Entity</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Interface Providing Requiring Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInterfaceProvidingRequiringEntity(final InterfaceProvidingRequiringEntity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Composed Structure</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Composed Structure</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseComposedStructure(final ComposedStructure object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Composed Providing Requiring Entity</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Composed Providing Requiring Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseComposedProvidingRequiringEntity(final ComposedProvidingRequiringEntity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(final EObject object) {
        return null;
    }

} // RepositorySwitch
