/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.palladiosimulator.pcm.core.composition.util.CompositionValidator;
import org.palladiosimulator.pcm.core.entity.util.EntityValidator;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.ComponentType;
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
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
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

/**
 * <!-- begin-user-doc --> The <b>Validator</b> for the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage
 * @generated
 */
public class RepositoryValidator extends EObjectValidator {

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
    public static final RepositoryValidator INSTANCE = new RepositoryValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of
     * diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "org.palladiosimulator.pcm.repository";

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'No Seff
     * Type Used Twice' of 'Basic Component'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BASIC_COMPONENT__NO_SEFF_TYPE_USED_TWICE = 1;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Required
     * Interfaces Have To Conform To Complete Type' of 'Implementation Component Type'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int IMPLEMENTATION_COMPONENT_TYPE__REQUIRED_INTERFACES_HAVE_TO_CONFORM_TO_COMPLETE_TYPE = 2;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Provided
     * Interfaces Have To Conform To Complete Type' of 'Implementation Component Type'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int IMPLEMENTATION_COMPONENT_TYPE__PROVIDED_INTERFACES_HAVE_TO_CONFORM_TO_COMPLETE_TYPE = 3;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Provided
     * Interface Have To Conform To Component Type' of 'Implementation Component Type'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int IMPLEMENTATION_COMPONENT_TYPE__PROVIDED_INTERFACE_HAVE_TO_CONFORM_TO_COMPONENT_TYPE = 4;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Provide
     * Same Or More Interfaces As Complete Component Type' of 'Implementation Component Type'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int IMPLEMENTATION_COMPONENT_TYPE__PROVIDE_SAME_OR_MORE_INTERFACES_AS_COMPLETE_COMPONENT_TYPE = 5;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Require
     * Same Or Fewer Interfaces As Complete Component Type' of 'Implementation Component Type'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int IMPLEMENTATION_COMPONENT_TYPE__REQUIRE_SAME_OR_FEWER_INTERFACES_AS_COMPLETE_COMPONENT_TYPE = 6;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'No Protocol
     * Type ID Used Twice' of 'Interface'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INTERFACE__NO_PROTOCOL_TYPE_ID_USED_TWICE = 7;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Parameter
     * Names Have To Be Unique For ASignature' of 'Operation Signature'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OPERATION_SIGNATURE__PARAMETER_NAMES_HAVE_TO_BE_UNIQUE_FOR_ASIGNATURE = 8;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Signatures
     * Have To Be Unique For An Interface' of 'Operation Interface'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public static final int OPERATION_INTERFACE__SIGNATURES_HAVE_TO_BE_UNIQUE_FOR_AN_INTERFACE = 9;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'At Least
     * One Interface Has To Be Provided Or Required By AUsefull Complete Component Type' of
     * 'Complete Component Type'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int COMPLETE_COMPONENT_TYPE__AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_OR_REQUIRED_BY_AUSEFULL_COMPLETE_COMPONENT_TYPE = 10;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Provided
     * Interfaces Have To Conform To Provided Type2' of 'Complete Component Type'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int COMPLETE_COMPONENT_TYPE__PROVIDED_INTERFACES_HAVE_TO_CONFORM_TO_PROVIDED_TYPE2 = 11;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'At Least
     * One Interface Has To Be Provided By AUsefull Provides Component Type' of 'Provides Component
     * Type'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROVIDES_COMPONENT_TYPE__AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_BY_AUSEFULL_PROVIDES_COMPONENT_TYPE = 12;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 12;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants in a derived class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

    /**
     * The cached base package validator. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected CompositionValidator compositionValidator;

    /**
     * The cached base package validator. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected EntityValidator entityValidator;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public RepositoryValidator() {
        super();
        this.compositionValidator = CompositionValidator.INSTANCE;
        this.entityValidator = EntityValidator.INSTANCE;
    }

    /**
     * Returns the package of this validator switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
        return RepositoryPackage.eINSTANCE;
    }

    /**
     * Calls <code>validateXXX</code> for the corresponding classifier of the model. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected boolean validate(final int classifierID, final Object value, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        switch (classifierID) {
        case RepositoryPackage.PASSIVE_RESOURCE:
            return this.validatePassiveResource((PassiveResource) value, diagnostics, context);
        case RepositoryPackage.BASIC_COMPONENT:
            return this.validateBasicComponent((BasicComponent) value, diagnostics, context);
        case RepositoryPackage.IMPLEMENTATION_COMPONENT_TYPE:
            return this.validateImplementationComponentType((ImplementationComponentType) value, diagnostics, context);
        case RepositoryPackage.REPOSITORY_COMPONENT:
            return this.validateRepositoryComponent((RepositoryComponent) value, diagnostics, context);
        case RepositoryPackage.PROVIDED_ROLE:
            return this.validateProvidedRole((ProvidedRole) value, diagnostics, context);
        case RepositoryPackage.PARAMETER:
            return this.validateParameter((Parameter) value, diagnostics, context);
        case RepositoryPackage.DATA_TYPE:
            return this.validateDataType((DataType) value, diagnostics, context);
        case RepositoryPackage.REPOSITORY:
            return this.validateRepository((Repository) value, diagnostics, context);
        case RepositoryPackage.INTERFACE:
            return this.validateInterface((Interface) value, diagnostics, context);
        case RepositoryPackage.REQUIRED_CHARACTERISATION:
            return this.validateRequiredCharacterisation((RequiredCharacterisation) value, diagnostics, context);
        case RepositoryPackage.EVENT_GROUP:
            return this.validateEventGroup((EventGroup) value, diagnostics, context);
        case RepositoryPackage.EVENT_TYPE:
            return this.validateEventType((EventType) value, diagnostics, context);
        case RepositoryPackage.SIGNATURE:
            return this.validateSignature((Signature) value, diagnostics, context);
        case RepositoryPackage.EXCEPTION_TYPE:
            return this.validateExceptionType((ExceptionType) value, diagnostics, context);
        case RepositoryPackage.INFRASTRUCTURE_SIGNATURE:
            return this.validateInfrastructureSignature((InfrastructureSignature) value, diagnostics, context);
        case RepositoryPackage.INFRASTRUCTURE_INTERFACE:
            return this.validateInfrastructureInterface((InfrastructureInterface) value, diagnostics, context);
        case RepositoryPackage.INFRASTRUCTURE_REQUIRED_ROLE:
            return this.validateInfrastructureRequiredRole((InfrastructureRequiredRole) value, diagnostics, context);
        case RepositoryPackage.REQUIRED_ROLE:
            return this.validateRequiredRole((RequiredRole) value, diagnostics, context);
        case RepositoryPackage.OPERATION_SIGNATURE:
            return this.validateOperationSignature((OperationSignature) value, diagnostics, context);
        case RepositoryPackage.OPERATION_INTERFACE:
            return this.validateOperationInterface((OperationInterface) value, diagnostics, context);
        case RepositoryPackage.OPERATION_REQUIRED_ROLE:
            return this.validateOperationRequiredRole((OperationRequiredRole) value, diagnostics, context);
        case RepositoryPackage.SOURCE_ROLE:
            return this.validateSourceRole((SourceRole) value, diagnostics, context);
        case RepositoryPackage.SINK_ROLE:
            return this.validateSinkRole((SinkRole) value, diagnostics, context);
        case RepositoryPackage.OPERATION_PROVIDED_ROLE:
            return this.validateOperationProvidedRole((OperationProvidedRole) value, diagnostics, context);
        case RepositoryPackage.INFRASTRUCTURE_PROVIDED_ROLE:
            return this.validateInfrastructureProvidedRole((InfrastructureProvidedRole) value, diagnostics, context);
        case RepositoryPackage.COMPLETE_COMPONENT_TYPE:
            return this.validateCompleteComponentType((CompleteComponentType) value, diagnostics, context);
        case RepositoryPackage.PROVIDES_COMPONENT_TYPE:
            return this.validateProvidesComponentType((ProvidesComponentType) value, diagnostics, context);
        case RepositoryPackage.COMPOSITE_COMPONENT:
            return this.validateCompositeComponent((CompositeComponent) value, diagnostics, context);
        case RepositoryPackage.PRIMITIVE_DATA_TYPE:
            return this.validatePrimitiveDataType((PrimitiveDataType) value, diagnostics, context);
        case RepositoryPackage.COLLECTION_DATA_TYPE:
            return this.validateCollectionDataType((CollectionDataType) value, diagnostics, context);
        case RepositoryPackage.COMPOSITE_DATA_TYPE:
            return this.validateCompositeDataType((CompositeDataType) value, diagnostics, context);
        case RepositoryPackage.INNER_DECLARATION:
            return this.validateInnerDeclaration((InnerDeclaration) value, diagnostics, context);
        case RepositoryPackage.ROLE:
            return this.validateRole((Role) value, diagnostics, context);
        case RepositoryPackage.PARAMETER_MODIFIER:
            return this.validateParameterModifier((ParameterModifier) value, diagnostics, context);
        case RepositoryPackage.COMPONENT_TYPE:
            return this.validateComponentType((ComponentType) value, diagnostics, context);
        case RepositoryPackage.PRIMITIVE_TYPE_ENUM:
            return this.validatePrimitiveTypeEnum((PrimitiveTypeEnum) value, diagnostics, context);
        default:
            return true;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validatePassiveResource(final PassiveResource passiveResource, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(passiveResource, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateBasicComponent(final BasicComponent basicComponent, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(basicComponent, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(basicComponent, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(basicComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(basicComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(basicComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(basicComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(basicComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(basicComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_RequiredInterfacesHaveToConformToCompleteType(
                    basicComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_providedInterfacesHaveToConformToCompleteType(
                    basicComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_ProvidedInterfaceHaveToConformToComponentType(
                    basicComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_ProvideSameOrMoreInterfacesAsCompleteComponentType(
                    basicComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_RequireSameOrFewerInterfacesAsCompleteComponentType(
                    basicComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateBasicComponent_NoSeffTypeUsedTwice(basicComponent, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the NoSeffTypeUsedTwice constraint of '<em>Basic Component</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateBasicComponent_NoSeffTypeUsedTwice(final BasicComponent basicComponent,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return basicComponent.NoSeffTypeUsedTwice(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateImplementationComponentType(final ImplementationComponentType implementationComponentType,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(implementationComponentType, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(implementationComponentType, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(implementationComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(implementationComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(implementationComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(implementationComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(implementationComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(implementationComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_RequiredInterfacesHaveToConformToCompleteType(
                    implementationComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_providedInterfacesHaveToConformToCompleteType(
                    implementationComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_ProvidedInterfaceHaveToConformToComponentType(
                    implementationComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_ProvideSameOrMoreInterfacesAsCompleteComponentType(
                    implementationComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_RequireSameOrFewerInterfacesAsCompleteComponentType(
                    implementationComponentType, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the RequiredInterfacesHaveToConformToCompleteType constraint of '
     * <em>Implementation Component Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateImplementationComponentType_RequiredInterfacesHaveToConformToCompleteType(
            final ImplementationComponentType implementationComponentType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return implementationComponentType.RequiredInterfacesHaveToConformToCompleteType(diagnostics, context);
    }

    /**
     * Validates the providedInterfacesHaveToConformToCompleteType constraint of '
     * <em>Implementation Component Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateImplementationComponentType_providedInterfacesHaveToConformToCompleteType(
            final ImplementationComponentType implementationComponentType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return implementationComponentType.providedInterfacesHaveToConformToCompleteType(diagnostics, context);
    }

    /**
     * Validates the ProvidedInterfaceHaveToConformToComponentType constraint of '
     * <em>Implementation Component Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateImplementationComponentType_ProvidedInterfaceHaveToConformToComponentType(
            final ImplementationComponentType implementationComponentType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return implementationComponentType.ProvidedInterfaceHaveToConformToComponentType(diagnostics, context);
    }

    /**
     * Validates the ProvideSameOrMoreInterfacesAsCompleteComponentType constraint of '
     * <em>Implementation Component Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateImplementationComponentType_ProvideSameOrMoreInterfacesAsCompleteComponentType(
            final ImplementationComponentType implementationComponentType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return implementationComponentType.ProvideSameOrMoreInterfacesAsCompleteComponentType(diagnostics, context);
    }

    /**
     * Validates the RequireSameOrFewerInterfacesAsCompleteComponentType constraint of '
     * <em>Implementation Component Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateImplementationComponentType_RequireSameOrFewerInterfacesAsCompleteComponentType(
            final ImplementationComponentType implementationComponentType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return implementationComponentType.RequireSameOrFewerInterfacesAsCompleteComponentType(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateRepositoryComponent(final RepositoryComponent repositoryComponent,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(repositoryComponent, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateProvidedRole(final ProvidedRole providedRole, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(providedRole, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateParameter(final Parameter parameter, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(parameter, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateDataType(final DataType dataType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(dataType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateRepository(final Repository repository, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(repository, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateInterface(final Interface interface_, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(interface_, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(interface_, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(interface_, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(interface_, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(interface_, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(interface_, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(interface_, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(interface_, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateInterface_NoProtocolTypeIDUsedTwice(interface_, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the NoProtocolTypeIDUsedTwice constraint of '<em>Interface</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateInterface_NoProtocolTypeIDUsedTwice(final Interface interface_,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return interface_.NoProtocolTypeIDUsedTwice(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateRequiredCharacterisation(final RequiredCharacterisation requiredCharacterisation,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(requiredCharacterisation, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateEventGroup(final EventGroup eventGroup, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(eventGroup, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(eventGroup, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(eventGroup, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(eventGroup, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(eventGroup, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(eventGroup, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(eventGroup, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(eventGroup, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateInterface_NoProtocolTypeIDUsedTwice(eventGroup, diagnostics, context);
        }
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateEventType(final EventType eventType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(eventType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateSignature(final Signature signature, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(signature, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateExceptionType(final ExceptionType exceptionType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(exceptionType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateInfrastructureSignature(final InfrastructureSignature infrastructureSignature,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(infrastructureSignature, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateInfrastructureInterface(final InfrastructureInterface infrastructureInterface,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(infrastructureInterface, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(infrastructureInterface, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(infrastructureInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(infrastructureInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(infrastructureInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(infrastructureInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(infrastructureInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(infrastructureInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateInterface_NoProtocolTypeIDUsedTwice(infrastructureInterface, diagnostics, context);
        }
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateInfrastructureRequiredRole(final InfrastructureRequiredRole infrastructureRequiredRole,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(infrastructureRequiredRole, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateRequiredRole(final RequiredRole requiredRole, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(requiredRole, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateOperationSignature(final OperationSignature operationSignature,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(operationSignature, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(operationSignature, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(operationSignature, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(operationSignature, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(operationSignature, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(operationSignature, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(operationSignature, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(operationSignature, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateOperationSignature_ParameterNamesHaveToBeUniqueForASignature(operationSignature,
                    diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the ParameterNamesHaveToBeUniqueForASignature constraint of '
     * <em>Operation Signature</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateOperationSignature_ParameterNamesHaveToBeUniqueForASignature(
            final OperationSignature operationSignature, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return operationSignature.ParameterNamesHaveToBeUniqueForASignature(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateOperationInterface(final OperationInterface operationInterface,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(operationInterface, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(operationInterface, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(operationInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(operationInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(operationInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(operationInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(operationInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(operationInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateInterface_NoProtocolTypeIDUsedTwice(operationInterface, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateOperationInterface_SignaturesHaveToBeUniqueForAnInterface(operationInterface,
                    diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the SignaturesHaveToBeUniqueForAnInterface constraint of '
     * <em>Operation Interface</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateOperationInterface_SignaturesHaveToBeUniqueForAnInterface(
            final OperationInterface operationInterface, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return operationInterface.SignaturesHaveToBeUniqueForAnInterface(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateOperationRequiredRole(final OperationRequiredRole operationRequiredRole,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(operationRequiredRole, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateSourceRole(final SourceRole sourceRole, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(sourceRole, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateSinkRole(final SinkRole sinkRole, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(sinkRole, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateOperationProvidedRole(final OperationProvidedRole operationProvidedRole,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(operationProvidedRole, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateInfrastructureProvidedRole(final InfrastructureProvidedRole infrastructureProvidedRole,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(infrastructureProvidedRole, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateCompleteComponentType(final CompleteComponentType completeComponentType,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(completeComponentType, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(completeComponentType, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(completeComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(completeComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(completeComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(completeComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(completeComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(completeComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateCompleteComponentType_AtLeastOneInterfaceHasToBeProvidedOrRequiredByAUsefullCompleteComponentType(
                            completeComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateCompleteComponentType_providedInterfacesHaveToConformToProvidedType2(
                    completeComponentType, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the AtLeastOneInterfaceHasToBeProvidedOrRequiredByAUsefullCompleteComponentType
     * constraint of '<em>Complete Component Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    public boolean validateCompleteComponentType_AtLeastOneInterfaceHasToBeProvidedOrRequiredByAUsefullCompleteComponentType(
            final CompleteComponentType completeComponentType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return completeComponentType
                .AtLeastOneInterfaceHasToBeProvidedOrRequiredByAUsefullCompleteComponentType(diagnostics, context);
    }

    /**
     * Validates the providedInterfacesHaveToConformToProvidedType2 constraint of '
     * <em>Complete Component Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateCompleteComponentType_providedInterfacesHaveToConformToProvidedType2(
            final CompleteComponentType completeComponentType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return completeComponentType.providedInterfacesHaveToConformToProvidedType2(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateProvidesComponentType(final ProvidesComponentType providesComponentType,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(providesComponentType, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(providesComponentType, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(providesComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(providesComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(providesComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(providesComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(providesComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(providesComponentType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateProvidesComponentType_AtLeastOneInterfaceHasToBeProvidedByAUsefullProvidesComponentType(
                            providesComponentType, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the AtLeastOneInterfaceHasToBeProvidedByAUsefullProvidesComponentType constraint of
     * '<em>Provides Component Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateProvidesComponentType_AtLeastOneInterfaceHasToBeProvidedByAUsefullProvidesComponentType(
            final ProvidesComponentType providesComponentType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return providesComponentType.AtLeastOneInterfaceHasToBeProvidedByAUsefullProvidesComponentType(diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateCompositeComponent(final CompositeComponent compositeComponent,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(compositeComponent, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(compositeComponent, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(compositeComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(compositeComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(compositeComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(compositeComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(compositeComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(compositeComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.compositionValidator
                    .validateComposedStructure_MultipleConnectorsConstraint(compositeComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.compositionValidator
                    .validateComposedStructure_MultipleConnectorsConstraintForAssemblyConnectors(compositeComponent,
                            diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.entityValidator.validateComposedProvidingRequiringEntity_ProvidedRolesMustBeBound(
                    compositeComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_RequiredInterfacesHaveToConformToCompleteType(
                    compositeComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_providedInterfacesHaveToConformToCompleteType(
                    compositeComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_ProvidedInterfaceHaveToConformToComponentType(
                    compositeComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_ProvideSameOrMoreInterfacesAsCompleteComponentType(
                    compositeComponent, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateImplementationComponentType_RequireSameOrFewerInterfacesAsCompleteComponentType(
                    compositeComponent, diagnostics, context);
        }
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validatePrimitiveDataType(final PrimitiveDataType primitiveDataType,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(primitiveDataType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateCollectionDataType(final CollectionDataType collectionDataType,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(collectionDataType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateCompositeDataType(final CompositeDataType compositeDataType,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(compositeDataType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateInnerDeclaration(final InnerDeclaration innerDeclaration, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(innerDeclaration, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateRole(final Role role, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(role, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateParameterModifier(final ParameterModifier parameterModifier,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateComponentType(final ComponentType componentType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validatePrimitiveTypeEnum(final PrimitiveTypeEnum primitiveTypeEnum,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return true;
    }

    /**
     * Returns the resource locator that will be used to fetch messages for this validator's
     * diagnostics. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        // TODO
        // Specialize this to return a resource locator for messages specific to this validator.
        // Ensure that you remove @generated or mark it @generated NOT
        return super.getResourceLocator();
    }

} // RepositoryValidator
