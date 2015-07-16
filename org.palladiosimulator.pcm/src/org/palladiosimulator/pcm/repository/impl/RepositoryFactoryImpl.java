/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.ComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.RequiredCharacterisation;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class RepositoryFactoryImpl extends EFactoryImpl implements RepositoryFactory {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static RepositoryFactory init() {
        try {
            final RepositoryFactory theRepositoryFactory = (RepositoryFactory) EPackage.Registry.INSTANCE
                    .getEFactory(RepositoryPackage.eNS_URI);
            if (theRepositoryFactory != null) {
                return theRepositoryFactory;
            }
        } catch (final Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new RepositoryFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public RepositoryFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(final EClass eClass) {
        switch (eClass.getClassifierID()) {
        case RepositoryPackage.PASSIVE_RESOURCE:
            return this.createPassiveResource();
        case RepositoryPackage.BASIC_COMPONENT:
            return this.createBasicComponent();
        case RepositoryPackage.PARAMETER:
            return this.createParameter();
        case RepositoryPackage.REPOSITORY:
            return this.createRepository();
        case RepositoryPackage.REQUIRED_CHARACTERISATION:
            return this.createRequiredCharacterisation();
        case RepositoryPackage.EVENT_GROUP:
            return this.createEventGroup();
        case RepositoryPackage.EVENT_TYPE:
            return this.createEventType();
        case RepositoryPackage.EXCEPTION_TYPE:
            return this.createExceptionType();
        case RepositoryPackage.INFRASTRUCTURE_SIGNATURE:
            return this.createInfrastructureSignature();
        case RepositoryPackage.INFRASTRUCTURE_INTERFACE:
            return this.createInfrastructureInterface();
        case RepositoryPackage.INFRASTRUCTURE_REQUIRED_ROLE:
            return this.createInfrastructureRequiredRole();
        case RepositoryPackage.OPERATION_SIGNATURE:
            return this.createOperationSignature();
        case RepositoryPackage.OPERATION_INTERFACE:
            return this.createOperationInterface();
        case RepositoryPackage.OPERATION_REQUIRED_ROLE:
            return this.createOperationRequiredRole();
        case RepositoryPackage.SOURCE_ROLE:
            return this.createSourceRole();
        case RepositoryPackage.SINK_ROLE:
            return this.createSinkRole();
        case RepositoryPackage.OPERATION_PROVIDED_ROLE:
            return this.createOperationProvidedRole();
        case RepositoryPackage.INFRASTRUCTURE_PROVIDED_ROLE:
            return this.createInfrastructureProvidedRole();
        case RepositoryPackage.COMPLETE_COMPONENT_TYPE:
            return this.createCompleteComponentType();
        case RepositoryPackage.PROVIDES_COMPONENT_TYPE:
            return this.createProvidesComponentType();
        case RepositoryPackage.COMPOSITE_COMPONENT:
            return this.createCompositeComponent();
        case RepositoryPackage.PRIMITIVE_DATA_TYPE:
            return this.createPrimitiveDataType();
        case RepositoryPackage.COLLECTION_DATA_TYPE:
            return this.createCollectionDataType();
        case RepositoryPackage.COMPOSITE_DATA_TYPE:
            return this.createCompositeDataType();
        case RepositoryPackage.INNER_DECLARATION:
            return this.createInnerDeclaration();
        default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object createFromString(final EDataType eDataType, final String initialValue) {
        switch (eDataType.getClassifierID()) {
        case RepositoryPackage.PARAMETER_MODIFIER:
            return this.createParameterModifierFromString(eDataType, initialValue);
        case RepositoryPackage.COMPONENT_TYPE:
            return this.createComponentTypeFromString(eDataType, initialValue);
        case RepositoryPackage.PRIMITIVE_TYPE_ENUM:
            return this.createPrimitiveTypeEnumFromString(eDataType, initialValue);
        default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String convertToString(final EDataType eDataType, final Object instanceValue) {
        switch (eDataType.getClassifierID()) {
        case RepositoryPackage.PARAMETER_MODIFIER:
            return this.convertParameterModifierToString(eDataType, instanceValue);
        case RepositoryPackage.COMPONENT_TYPE:
            return this.convertComponentTypeToString(eDataType, instanceValue);
        case RepositoryPackage.PRIMITIVE_TYPE_ENUM:
            return this.convertPrimitiveTypeEnumToString(eDataType, instanceValue);
        default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PassiveResource createPassiveResource() {
        final PassiveResourceImpl passiveResource = new PassiveResourceImpl();
        return passiveResource;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public BasicComponent createBasicComponent() {
        final BasicComponentImpl basicComponent = new BasicComponentImpl();
        return basicComponent;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Parameter createParameter() {
        final ParameterImpl parameter = new ParameterImpl();
        return parameter;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Repository createRepository() {
        final RepositoryImpl repository = new RepositoryImpl();
        return repository;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RequiredCharacterisation createRequiredCharacterisation() {
        final RequiredCharacterisationImpl requiredCharacterisation = new RequiredCharacterisationImpl();
        return requiredCharacterisation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EventGroup createEventGroup() {
        final EventGroupImpl eventGroup = new EventGroupImpl();
        return eventGroup;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EventType createEventType() {
        final EventTypeImpl eventType = new EventTypeImpl();
        return eventType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ExceptionType createExceptionType() {
        final ExceptionTypeImpl exceptionType = new ExceptionTypeImpl();
        return exceptionType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public InfrastructureSignature createInfrastructureSignature() {
        final InfrastructureSignatureImpl infrastructureSignature = new InfrastructureSignatureImpl();
        return infrastructureSignature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public InfrastructureInterface createInfrastructureInterface() {
        final InfrastructureInterfaceImpl infrastructureInterface = new InfrastructureInterfaceImpl();
        return infrastructureInterface;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public InfrastructureRequiredRole createInfrastructureRequiredRole() {
        final InfrastructureRequiredRoleImpl infrastructureRequiredRole = new InfrastructureRequiredRoleImpl();
        return infrastructureRequiredRole;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public OperationSignature createOperationSignature() {
        final OperationSignatureImpl operationSignature = new OperationSignatureImpl();
        return operationSignature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public OperationInterface createOperationInterface() {
        final OperationInterfaceImpl operationInterface = new OperationInterfaceImpl();
        return operationInterface;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public OperationRequiredRole createOperationRequiredRole() {
        final OperationRequiredRoleImpl operationRequiredRole = new OperationRequiredRoleImpl();
        return operationRequiredRole;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SourceRole createSourceRole() {
        final SourceRoleImpl sourceRole = new SourceRoleImpl();
        return sourceRole;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SinkRole createSinkRole() {
        final SinkRoleImpl sinkRole = new SinkRoleImpl();
        return sinkRole;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public OperationProvidedRole createOperationProvidedRole() {
        final OperationProvidedRoleImpl operationProvidedRole = new OperationProvidedRoleImpl();
        return operationProvidedRole;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public InfrastructureProvidedRole createInfrastructureProvidedRole() {
        final InfrastructureProvidedRoleImpl infrastructureProvidedRole = new InfrastructureProvidedRoleImpl();
        return infrastructureProvidedRole;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CompleteComponentType createCompleteComponentType() {
        final CompleteComponentTypeImpl completeComponentType = new CompleteComponentTypeImpl();
        return completeComponentType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ProvidesComponentType createProvidesComponentType() {
        final ProvidesComponentTypeImpl providesComponentType = new ProvidesComponentTypeImpl();
        return providesComponentType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CompositeComponent createCompositeComponent() {
        final CompositeComponentImpl compositeComponent = new CompositeComponentImpl();
        return compositeComponent;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PrimitiveDataType createPrimitiveDataType() {
        final PrimitiveDataTypeImpl primitiveDataType = new PrimitiveDataTypeImpl();
        return primitiveDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CollectionDataType createCollectionDataType() {
        final CollectionDataTypeImpl collectionDataType = new CollectionDataTypeImpl();
        return collectionDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CompositeDataType createCompositeDataType() {
        final CompositeDataTypeImpl compositeDataType = new CompositeDataTypeImpl();
        return compositeDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public InnerDeclaration createInnerDeclaration() {
        final InnerDeclarationImpl innerDeclaration = new InnerDeclarationImpl();
        return innerDeclaration;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ParameterModifier createParameterModifierFromString(final EDataType eDataType, final String initialValue) {
        final ParameterModifier result = ParameterModifier.get(initialValue);
        if (result == null) {
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        }
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertParameterModifierToString(final EDataType eDataType, final Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ComponentType createComponentTypeFromString(final EDataType eDataType, final String initialValue) {
        final ComponentType result = ComponentType.get(initialValue);
        if (result == null) {
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        }
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertComponentTypeToString(final EDataType eDataType, final Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public PrimitiveTypeEnum createPrimitiveTypeEnumFromString(final EDataType eDataType, final String initialValue) {
        final PrimitiveTypeEnum result = PrimitiveTypeEnum.get(initialValue);
        if (result == null) {
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        }
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertPrimitiveTypeEnumToString(final EDataType eDataType, final Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RepositoryPackage getRepositoryPackage() {
        return (RepositoryPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static RepositoryPackage getPackage() {
        return RepositoryPackage.eINSTANCE;
    }

} // RepositoryFactoryImpl
