/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_reliability.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.palladiosimulator.pcm.qosannotations.qos_reliability.QosReliabilityPackage;
import org.palladiosimulator.pcm.qosannotations.qos_reliability.SpecifiedReliabilityAnnotation;

/**
 * <!-- begin-user-doc --> The <b>Validator</b> for the model. <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.qosannotations.qos_reliability.QosReliabilityPackage
 * @generated
 */
public class QosReliabilityValidator extends EObjectValidator {

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
    public static final QosReliabilityValidator INSTANCE = new QosReliabilityValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of
     * diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "org.palladiosimulator.pcm.qosannotations.qos_reliability";

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Specified
     * Reliability Annotation Must Reference Required Role Of ASystem' of 'Specified Reliability
     * Annotation'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int SPECIFIED_RELIABILITY_ANNOTATION__SPECIFIED_RELIABILITY_ANNOTATION_MUST_REFERENCE_REQUIRED_ROLE_OF_ASYSTEM = 1;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Sum Of
     * Reliability Annotation Failure Probabilities Must Not Exceed1' of 'Specified Reliability
     * Annotation'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int SPECIFIED_RELIABILITY_ANNOTATION__SUM_OF_RELIABILITY_ANNOTATION_FAILURE_PROBABILITIES_MUST_NOT_EXCEED1 = 2;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Multiple
     * External Occurrence Descriptions Per Failure Type Not Allowed' of 'Specified Reliability
     * Annotation'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int SPECIFIED_RELIABILITY_ANNOTATION__MULTIPLE_EXTERNAL_OCCURRENCE_DESCRIPTIONS_PER_FAILURE_TYPE_NOT_ALLOWED = 3;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 3;

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
    public QosReliabilityValidator() {
        super();
    }

    /**
     * Returns the package of this validator switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
        return QosReliabilityPackage.eINSTANCE;
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
        case QosReliabilityPackage.SPECIFIED_RELIABILITY_ANNOTATION:
            return this.validateSpecifiedReliabilityAnnotation((SpecifiedReliabilityAnnotation) value, diagnostics,
                    context);
        default:
            return true;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateSpecifiedReliabilityAnnotation(
            final SpecifiedReliabilityAnnotation specifiedReliabilityAnnotation, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(specifiedReliabilityAnnotation, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(specifiedReliabilityAnnotation, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(specifiedReliabilityAnnotation, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(specifiedReliabilityAnnotation, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(specifiedReliabilityAnnotation, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(specifiedReliabilityAnnotation, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(specifiedReliabilityAnnotation, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(specifiedReliabilityAnnotation, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateSpecifiedReliabilityAnnotation_SpecifiedReliabilityAnnotationMustReferenceRequiredRoleOfASystem(
                            specifiedReliabilityAnnotation, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateSpecifiedReliabilityAnnotation_SumOfReliabilityAnnotationFailureProbabilitiesMustNotExceed1(
                            specifiedReliabilityAnnotation, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateSpecifiedReliabilityAnnotation_MultipleExternalOccurrenceDescriptionsPerFailureTypeNotAllowed(
                            specifiedReliabilityAnnotation, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the SpecifiedReliabilityAnnotationMustReferenceRequiredRoleOfASystem constraint of
     * '<em>Specified Reliability Annotation</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateSpecifiedReliabilityAnnotation_SpecifiedReliabilityAnnotationMustReferenceRequiredRoleOfASystem(
            final SpecifiedReliabilityAnnotation specifiedReliabilityAnnotation, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return specifiedReliabilityAnnotation
                .SpecifiedReliabilityAnnotationMustReferenceRequiredRoleOfASystem(diagnostics, context);
    }

    /**
     * Validates the SumOfReliabilityAnnotationFailureProbabilitiesMustNotExceed1 constraint of '
     * <em>Specified Reliability Annotation</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateSpecifiedReliabilityAnnotation_SumOfReliabilityAnnotationFailureProbabilitiesMustNotExceed1(
            final SpecifiedReliabilityAnnotation specifiedReliabilityAnnotation, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return specifiedReliabilityAnnotation.SumOfReliabilityAnnotationFailureProbabilitiesMustNotExceed1(diagnostics,
                context);
    }

    /**
     * Validates the MultipleExternalOccurrenceDescriptionsPerFailureTypeNotAllowed constraint of '
     * <em>Specified Reliability Annotation</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateSpecifiedReliabilityAnnotation_MultipleExternalOccurrenceDescriptionsPerFailureTypeNotAllowed(
            final SpecifiedReliabilityAnnotation specifiedReliabilityAnnotation, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return specifiedReliabilityAnnotation
                .MultipleExternalOccurrenceDescriptionsPerFailureTypeNotAllowed(diagnostics, context);
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

} // QosReliabilityValidator
