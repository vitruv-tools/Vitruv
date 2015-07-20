/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyEventConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.DelegationConnector;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector;
import org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.SinkDelegationConnector;
import org.palladiosimulator.pcm.core.composition.SourceDelegationConnector;

/**
 * <!-- begin-user-doc --> The <b>Validator</b> for the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage
 * @generated
 */
public class CompositionValidator extends EObjectValidator {

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
    public static final CompositionValidator INSTANCE = new CompositionValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of
     * diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "org.palladiosimulator.pcm.core.composition";

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Multiple
     * Connectors Constraint' of 'Composed Structure'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int COMPOSED_STRUCTURE__MULTIPLE_CONNECTORS_CONSTRAINT = 1;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Multiple
     * Connectors Constraint For Assembly Connectors' of 'Composed Structure'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int COMPOSED_STRUCTURE__MULTIPLE_CONNECTORS_CONSTRAINT_FOR_ASSEMBLY_CONNECTORS = 2;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Provided
     * Delegation Connectorandtheconnected Componentmustbepartofthesamecompositestructure' of
     * 'Provided Delegation Connector'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROVIDED_DELEGATION_CONNECTOR__PROVIDED_DELEGATION_CONNECTORANDTHECONNECTED_COMPONENTMUSTBEPARTOFTHESAMECOMPOSITESTRUCTURE = 3;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Component
     * Of Assembly Context And Inner Role Providing Component Need To Be The Same' of 'Provided
     * Delegation Connector'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int PROVIDED_DELEGATION_CONNECTOR__COMPONENT_OF_ASSEMBLY_CONTEXT_AND_INNER_ROLE_PROVIDING_COMPONENT_NEED_TO_BE_THE_SAME = 4;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Required
     * Delegation Connectorandtheconnected Componentmustbepartofthesamecompositestructure' of
     * 'Required Delegation Connector'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int REQUIRED_DELEGATION_CONNECTOR__REQUIRED_DELEGATION_CONNECTORANDTHECONNECTED_COMPONENTMUSTBEPARTOFTHESAMECOMPOSITESTRUCTURE = 5;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Component
     * Of Assembly Context And Inner Role Requiring Component Need To Be The Same' of 'Required
     * Delegation Connector'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int REQUIRED_DELEGATION_CONNECTOR__COMPONENT_OF_ASSEMBLY_CONTEXT_AND_INNER_ROLE_REQUIRING_COMPONENT_NEED_TO_BE_THE_SAME = 6;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Requiring
     * Entity Of Outer Required Role Must Be The Same As The Parent Of The Required Delegation
     * Connector' of 'Required Delegation Connector'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int REQUIRED_DELEGATION_CONNECTOR__REQUIRING_ENTITY_OF_OUTER_REQUIRED_ROLE_MUST_BE_THE_SAME_AS_THE_PARENT_OF_THE_REQUIRED_DELEGATION_CONNECTOR = 7;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Assembly
     * Connectors Referenced Provided Roles And Child Context Must Match' of 'Assembly Connector'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ASSEMBLY_CONNECTOR__ASSEMBLY_CONNECTORS_REFERENCED_PROVIDED_ROLES_AND_CHILD_CONTEXT_MUST_MATCH = 8;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Assembly
     * Connectors Referenced Required Role And Child Context Must Match' of 'Assembly Connector'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ASSEMBLY_CONNECTOR__ASSEMBLY_CONNECTORS_REFERENCED_REQUIRED_ROLE_AND_CHILD_CONTEXT_MUST_MATCH = 9;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Assembly
     * Connectors Referenced Interfaces Must Match' of 'Assembly Connector'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int ASSEMBLY_CONNECTOR__ASSEMBLY_CONNECTORS_REFERENCED_INTERFACES_MUST_MATCH = 10;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 10;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants in a derived class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public CompositionValidator() {
        super();
    }

    /**
     * Returns the package of this validator switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
        return CompositionPackage.eINSTANCE;
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
        case CompositionPackage.DELEGATION_CONNECTOR:
            return this.validateDelegationConnector((DelegationConnector) value, diagnostics, context);
        case CompositionPackage.CONNECTOR:
            return this.validateConnector((Connector) value, diagnostics, context);
        case CompositionPackage.COMPOSED_STRUCTURE:
            return this.validateComposedStructure((ComposedStructure) value, diagnostics, context);
        case CompositionPackage.RESOURCE_REQUIRED_DELEGATION_CONNECTOR:
            return this.validateResourceRequiredDelegationConnector((ResourceRequiredDelegationConnector) value,
                    diagnostics, context);
        case CompositionPackage.EVENT_CHANNEL:
            return this.validateEventChannel((EventChannel) value, diagnostics, context);
        case CompositionPackage.EVENT_CHANNEL_SOURCE_CONNECTOR:
            return this.validateEventChannelSourceConnector((EventChannelSourceConnector) value, diagnostics, context);
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR:
            return this.validateEventChannelSinkConnector((EventChannelSinkConnector) value, diagnostics, context);
        case CompositionPackage.PROVIDED_DELEGATION_CONNECTOR:
            return this.validateProvidedDelegationConnector((ProvidedDelegationConnector) value, diagnostics, context);
        case CompositionPackage.REQUIRED_DELEGATION_CONNECTOR:
            return this.validateRequiredDelegationConnector((RequiredDelegationConnector) value, diagnostics, context);
        case CompositionPackage.ASSEMBLY_CONNECTOR:
            return this.validateAssemblyConnector((AssemblyConnector) value, diagnostics, context);
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR:
            return this.validateAssemblyEventConnector((AssemblyEventConnector) value, diagnostics, context);
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR:
            return this.validateSourceDelegationConnector((SourceDelegationConnector) value, diagnostics, context);
        case CompositionPackage.SINK_DELEGATION_CONNECTOR:
            return this.validateSinkDelegationConnector((SinkDelegationConnector) value, diagnostics, context);
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            return this.validateAssemblyInfrastructureConnector((AssemblyInfrastructureConnector) value, diagnostics,
                    context);
        case CompositionPackage.PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            return this.validateProvidedInfrastructureDelegationConnector(
                    (ProvidedInfrastructureDelegationConnector) value, diagnostics, context);
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            return this.validateRequiredInfrastructureDelegationConnector(
                    (RequiredInfrastructureDelegationConnector) value, diagnostics, context);
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            return this.validateRequiredResourceDelegationConnector((RequiredResourceDelegationConnector) value,
                    diagnostics, context);
        case CompositionPackage.ASSEMBLY_CONTEXT:
            return this.validateAssemblyContext((AssemblyContext) value, diagnostics, context);
        default:
            return true;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateDelegationConnector(final DelegationConnector delegationConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(delegationConnector, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateConnector(final Connector connector, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(connector, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateComposedStructure(final ComposedStructure composedStructure,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(composedStructure, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(composedStructure, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(composedStructure, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(composedStructure, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(composedStructure, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(composedStructure, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(composedStructure, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(composedStructure, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateComposedStructure_MultipleConnectorsConstraint(composedStructure, diagnostics,
                    context);
        }
        if (result || diagnostics != null) {
            result &= this.validateComposedStructure_MultipleConnectorsConstraintForAssemblyConnectors(
                    composedStructure, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the MultipleConnectorsConstraint constraint of '<em>Composed Structure</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateComposedStructure_MultipleConnectorsConstraint(final ComposedStructure composedStructure,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return composedStructure.MultipleConnectorsConstraint(diagnostics, context);
    }

    /**
     * Validates the MultipleConnectorsConstraintForAssemblyConnectors constraint of '
     * <em>Composed Structure</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateComposedStructure_MultipleConnectorsConstraintForAssemblyConnectors(
            final ComposedStructure composedStructure, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return composedStructure.MultipleConnectorsConstraintForAssemblyConnectors(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateResourceRequiredDelegationConnector(
            final ResourceRequiredDelegationConnector resourceRequiredDelegationConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(resourceRequiredDelegationConnector, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateEventChannel(final EventChannel eventChannel, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(eventChannel, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateEventChannelSourceConnector(final EventChannelSourceConnector eventChannelSourceConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(eventChannelSourceConnector, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateEventChannelSinkConnector(final EventChannelSinkConnector eventChannelSinkConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(eventChannelSinkConnector, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateProvidedDelegationConnector(final ProvidedDelegationConnector providedDelegationConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(providedDelegationConnector, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(providedDelegationConnector, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(providedDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(providedDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(providedDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(providedDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(providedDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(providedDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateProvidedDelegationConnector_ProvidedDelegationConnectorandtheconnectedComponentmustbepartofthesamecompositestructure(
                            providedDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateProvidedDelegationConnector_ComponentOfAssemblyContextAndInnerRoleProvidingComponentNeedToBeTheSame(
                            providedDelegationConnector, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the
     * ProvidedDelegationConnectorandtheconnectedComponentmustbepartofthesamecompositestructure
     * constraint of '<em>Provided Delegation Connector</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public boolean validateProvidedDelegationConnector_ProvidedDelegationConnectorandtheconnectedComponentmustbepartofthesamecompositestructure(
            final ProvidedDelegationConnector providedDelegationConnector, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return providedDelegationConnector
                .ProvidedDelegationConnectorandtheconnectedComponentmustbepartofthesamecompositestructure(diagnostics,
                        context);
    }

    /**
     * Validates the ComponentOfAssemblyContextAndInnerRoleProvidingComponentNeedToBeTheSame
     * constraint of '<em>Provided Delegation Connector</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public boolean validateProvidedDelegationConnector_ComponentOfAssemblyContextAndInnerRoleProvidingComponentNeedToBeTheSame(
            final ProvidedDelegationConnector providedDelegationConnector, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return providedDelegationConnector
                .ComponentOfAssemblyContextAndInnerRoleProvidingComponentNeedToBeTheSame(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateRequiredDelegationConnector(final RequiredDelegationConnector requiredDelegationConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(requiredDelegationConnector, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(requiredDelegationConnector, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(requiredDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(requiredDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(requiredDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(requiredDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(requiredDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(requiredDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateRequiredDelegationConnector_RequiredDelegationConnectorandtheconnectedComponentmustbepartofthesamecompositestructure(
                            requiredDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateRequiredDelegationConnector_ComponentOfAssemblyContextAndInnerRoleRequiringComponentNeedToBeTheSame(
                            requiredDelegationConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateRequiredDelegationConnector_RequiringEntityOfOuterRequiredRoleMustBeTheSameAsTheParentOfTheRequiredDelegationConnector(
                            requiredDelegationConnector, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the
     * RequiredDelegationConnectorandtheconnectedComponentmustbepartofthesamecompositestructure
     * constraint of '<em>Required Delegation Connector</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public boolean validateRequiredDelegationConnector_RequiredDelegationConnectorandtheconnectedComponentmustbepartofthesamecompositestructure(
            final RequiredDelegationConnector requiredDelegationConnector, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return requiredDelegationConnector
                .RequiredDelegationConnectorandtheconnectedComponentmustbepartofthesamecompositestructure(diagnostics,
                        context);
    }

    /**
     * Validates the ComponentOfAssemblyContextAndInnerRoleRequiringComponentNeedToBeTheSame
     * constraint of '<em>Required Delegation Connector</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public boolean validateRequiredDelegationConnector_ComponentOfAssemblyContextAndInnerRoleRequiringComponentNeedToBeTheSame(
            final RequiredDelegationConnector requiredDelegationConnector, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return requiredDelegationConnector
                .ComponentOfAssemblyContextAndInnerRoleRequiringComponentNeedToBeTheSame(diagnostics, context);
    }

    /**
     * Validates the
     * RequiringEntityOfOuterRequiredRoleMustBeTheSameAsTheParentOfTheRequiredDelegationConnector
     * constraint of '<em>Required Delegation Connector</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public boolean validateRequiredDelegationConnector_RequiringEntityOfOuterRequiredRoleMustBeTheSameAsTheParentOfTheRequiredDelegationConnector(
            final RequiredDelegationConnector requiredDelegationConnector, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return requiredDelegationConnector
                .RequiringEntityOfOuterRequiredRoleMustBeTheSameAsTheParentOfTheRequiredDelegationConnector(diagnostics,
                        context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateAssemblyConnector(final AssemblyConnector assemblyConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(assemblyConnector, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(assemblyConnector, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(assemblyConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(assemblyConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(assemblyConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(assemblyConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(assemblyConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(assemblyConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateAssemblyConnector_AssemblyConnectorsReferencedProvidedRolesAndChildContextMustMatch(
                    assemblyConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateAssemblyConnector_AssemblyConnectorsReferencedRequiredRoleAndChildContextMustMatch(
                    assemblyConnector, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateAssemblyConnector_AssemblyConnectorsReferencedInterfacesMustMatch(assemblyConnector,
                    diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the AssemblyConnectorsReferencedProvidedRolesAndChildContextMustMatch constraint of
     * '<em>Assembly Connector</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateAssemblyConnector_AssemblyConnectorsReferencedProvidedRolesAndChildContextMustMatch(
            final AssemblyConnector assemblyConnector, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return assemblyConnector.AssemblyConnectorsReferencedProvidedRolesAndChildContextMustMatch(diagnostics,
                context);
    }

    /**
     * Validates the AssemblyConnectorsReferencedRequiredRoleAndChildContextMustMatch constraint of
     * '<em>Assembly Connector</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateAssemblyConnector_AssemblyConnectorsReferencedRequiredRoleAndChildContextMustMatch(
            final AssemblyConnector assemblyConnector, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return assemblyConnector.AssemblyConnectorsReferencedRequiredRoleAndChildContextMustMatch(diagnostics, context);
    }

    /**
     * Validates the AssemblyConnectorsReferencedInterfacesMustMatch constraint of '
     * <em>Assembly Connector</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateAssemblyConnector_AssemblyConnectorsReferencedInterfacesMustMatch(
            final AssemblyConnector assemblyConnector, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return assemblyConnector.AssemblyConnectorsReferencedInterfacesMustMatch(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateAssemblyEventConnector(final AssemblyEventConnector assemblyEventConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(assemblyEventConnector, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateSourceDelegationConnector(final SourceDelegationConnector sourceDelegationConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(sourceDelegationConnector, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateSinkDelegationConnector(final SinkDelegationConnector sinkDelegationConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(sinkDelegationConnector, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateAssemblyInfrastructureConnector(
            final AssemblyInfrastructureConnector assemblyInfrastructureConnector, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(assemblyInfrastructureConnector, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateProvidedInfrastructureDelegationConnector(
            final ProvidedInfrastructureDelegationConnector providedInfrastructureDelegationConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(providedInfrastructureDelegationConnector, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateRequiredInfrastructureDelegationConnector(
            final RequiredInfrastructureDelegationConnector requiredInfrastructureDelegationConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(requiredInfrastructureDelegationConnector, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateRequiredResourceDelegationConnector(
            final RequiredResourceDelegationConnector requiredResourceDelegationConnector,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(requiredResourceDelegationConnector, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateAssemblyContext(final AssemblyContext assemblyContext, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(assemblyContext, diagnostics, context);
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

} // CompositionValidator
