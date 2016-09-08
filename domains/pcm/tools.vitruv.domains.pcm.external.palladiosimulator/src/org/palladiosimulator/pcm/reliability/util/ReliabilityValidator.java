/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.reliability.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.FailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.HardwareInducedFailureType;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.NetworkInducedFailureType;
import org.palladiosimulator.pcm.reliability.ReliabilityPackage;
import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;

/**
 * <!-- begin-user-doc --> The <b>Validator</b> for the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.reliability.ReliabilityPackage
 * @generated
 */
public class ReliabilityValidator extends EObjectValidator {

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
    public static final ReliabilityValidator INSTANCE = new ReliabilityValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of
     * diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "org.palladiosimulator.pcm.reliability";

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Ensure
     * Valid Failure Probability Range' of 'Failure Occurrence Description'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int FAILURE_OCCURRENCE_DESCRIPTION__ENSURE_VALID_FAILURE_PROBABILITY_RANGE = 1;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Hardware
     * Induced Failure Type Has Processing Resource Type' of 'Hardware Induced Failure Type'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int HARDWARE_INDUCED_FAILURE_TYPE__HARDWARE_INDUCED_FAILURE_TYPE_HAS_PROCESSING_RESOURCE_TYPE = 2;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'No Resource
     * Timeout Failure Allowed For Internal Failure Occurrence Description' of 'Internal Failure
     * Occurrence Description'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION__NO_RESOURCE_TIMEOUT_FAILURE_ALLOWED_FOR_INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = 3;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Network
     * Induced Failure Type Has Communication Link Resource Type' of 'Network Induced Failure Type'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int NETWORK_INDUCED_FAILURE_TYPE__NETWORK_INDUCED_FAILURE_TYPE_HAS_COMMUNICATION_LINK_RESOURCE_TYPE = 4;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'No Resource
     * Timeout Failure Allowed For External Failure Occurrence Description' of 'External Failure
     * Occurrence Description'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION__NO_RESOURCE_TIMEOUT_FAILURE_ALLOWED_FOR_EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = 5;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 5;

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
    public ReliabilityValidator() {
        super();
    }

    /**
     * Returns the package of this validator switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
        return ReliabilityPackage.eINSTANCE;
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
        case ReliabilityPackage.FAILURE_OCCURRENCE_DESCRIPTION:
            return this.validateFailureOccurrenceDescription((FailureOccurrenceDescription) value, diagnostics,
                    context);
        case ReliabilityPackage.HARDWARE_INDUCED_FAILURE_TYPE:
            return this.validateHardwareInducedFailureType((HardwareInducedFailureType) value, diagnostics, context);
        case ReliabilityPackage.SOFTWARE_INDUCED_FAILURE_TYPE:
            return this.validateSoftwareInducedFailureType((SoftwareInducedFailureType) value, diagnostics, context);
        case ReliabilityPackage.INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION:
            return this.validateInternalFailureOccurrenceDescription((InternalFailureOccurrenceDescription) value,
                    diagnostics, context);
        case ReliabilityPackage.NETWORK_INDUCED_FAILURE_TYPE:
            return this.validateNetworkInducedFailureType((NetworkInducedFailureType) value, diagnostics, context);
        case ReliabilityPackage.EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION:
            return this.validateExternalFailureOccurrenceDescription((ExternalFailureOccurrenceDescription) value,
                    diagnostics, context);
        case ReliabilityPackage.RESOURCE_TIMEOUT_FAILURE_TYPE:
            return this.validateResourceTimeoutFailureType((ResourceTimeoutFailureType) value, diagnostics, context);
        case ReliabilityPackage.FAILURE_TYPE:
            return this.validateFailureType((FailureType) value, diagnostics, context);
        default:
            return true;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateFailureOccurrenceDescription(final FailureOccurrenceDescription failureOccurrenceDescription,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(failureOccurrenceDescription, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(failureOccurrenceDescription, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(failureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(failureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(failureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(failureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(failureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(failureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateFailureOccurrenceDescription_EnsureValidFailureProbabilityRange(
                    failureOccurrenceDescription, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the EnsureValidFailureProbabilityRange constraint of '
     * <em>Failure Occurrence Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateFailureOccurrenceDescription_EnsureValidFailureProbabilityRange(
            final FailureOccurrenceDescription failureOccurrenceDescription, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return failureOccurrenceDescription.EnsureValidFailureProbabilityRange(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateHardwareInducedFailureType(final HardwareInducedFailureType hardwareInducedFailureType,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(hardwareInducedFailureType, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(hardwareInducedFailureType, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(hardwareInducedFailureType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(hardwareInducedFailureType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(hardwareInducedFailureType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(hardwareInducedFailureType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(hardwareInducedFailureType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(hardwareInducedFailureType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateHardwareInducedFailureType_HardwareInducedFailureTypeHasProcessingResourceType(
                    hardwareInducedFailureType, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the HardwareInducedFailureTypeHasProcessingResourceType constraint of '
     * <em>Hardware Induced Failure Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateHardwareInducedFailureType_HardwareInducedFailureTypeHasProcessingResourceType(
            final HardwareInducedFailureType hardwareInducedFailureType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return hardwareInducedFailureType.HardwareInducedFailureTypeHasProcessingResourceType(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateSoftwareInducedFailureType(final SoftwareInducedFailureType softwareInducedFailureType,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(softwareInducedFailureType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateInternalFailureOccurrenceDescription(
            final InternalFailureOccurrenceDescription internalFailureOccurrenceDescription,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(internalFailureOccurrenceDescription, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(internalFailureOccurrenceDescription, diagnostics,
                context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(internalFailureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(internalFailureOccurrenceDescription, diagnostics,
                    context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(internalFailureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(internalFailureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(internalFailureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(internalFailureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateFailureOccurrenceDescription_EnsureValidFailureProbabilityRange(
                    internalFailureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateInternalFailureOccurrenceDescription_NoResourceTimeoutFailureAllowedForInternalFailureOccurrenceDescription(
                            internalFailureOccurrenceDescription, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the NoResourceTimeoutFailureAllowedForInternalFailureOccurrenceDescription
     * constraint of '<em>Internal Failure Occurrence Description</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateInternalFailureOccurrenceDescription_NoResourceTimeoutFailureAllowedForInternalFailureOccurrenceDescription(
            final InternalFailureOccurrenceDescription internalFailureOccurrenceDescription,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return internalFailureOccurrenceDescription
                .NoResourceTimeoutFailureAllowedForInternalFailureOccurrenceDescription(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateNetworkInducedFailureType(final NetworkInducedFailureType networkInducedFailureType,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(networkInducedFailureType, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(networkInducedFailureType, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(networkInducedFailureType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(networkInducedFailureType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(networkInducedFailureType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(networkInducedFailureType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(networkInducedFailureType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(networkInducedFailureType, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateNetworkInducedFailureType_NetworkInducedFailureTypeHasCommunicationLinkResourceType(
                    networkInducedFailureType, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the NetworkInducedFailureTypeHasCommunicationLinkResourceType constraint of '
     * <em>Network Induced Failure Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateNetworkInducedFailureType_NetworkInducedFailureTypeHasCommunicationLinkResourceType(
            final NetworkInducedFailureType networkInducedFailureType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return networkInducedFailureType.NetworkInducedFailureTypeHasCommunicationLinkResourceType(diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateExternalFailureOccurrenceDescription(
            final ExternalFailureOccurrenceDescription externalFailureOccurrenceDescription,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(externalFailureOccurrenceDescription, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(externalFailureOccurrenceDescription, diagnostics,
                context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(externalFailureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(externalFailureOccurrenceDescription, diagnostics,
                    context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(externalFailureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(externalFailureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(externalFailureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(externalFailureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateFailureOccurrenceDescription_EnsureValidFailureProbabilityRange(
                    externalFailureOccurrenceDescription, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateExternalFailureOccurrenceDescription_NoResourceTimeoutFailureAllowedForExternalFailureOccurrenceDescription(
                            externalFailureOccurrenceDescription, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the NoResourceTimeoutFailureAllowedForExternalFailureOccurrenceDescription
     * constraint of '<em>External Failure Occurrence Description</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateExternalFailureOccurrenceDescription_NoResourceTimeoutFailureAllowedForExternalFailureOccurrenceDescription(
            final ExternalFailureOccurrenceDescription externalFailureOccurrenceDescription,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return externalFailureOccurrenceDescription
                .NoResourceTimeoutFailureAllowedForExternalFailureOccurrenceDescription(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateResourceTimeoutFailureType(final ResourceTimeoutFailureType resourceTimeoutFailureType,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(resourceTimeoutFailureType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateFailureType(final FailureType failureType, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(failureType, diagnostics, context);
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

} // ReliabilityValidator
