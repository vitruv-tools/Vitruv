/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.reliability;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.palladiosimulator.pcm.qosannotations.qos_reliability.SpecifiedReliabilityAnnotation;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>External Failure Occurrence Description</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Special&nbsp;case&nbsp;of&nbsp;a&nbsp;FailureOccurenceDescription&nbsp;specifying&nbsp;the&nbsp;
 * failure&nbsp;potential&nbsp;of&nbsp;system-external&nbsp;calls.&nbsp;Can&nbsp;relate&nbsp;to any
 * FailureType. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription#getSpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription
 * <em>Specified Reliability Annotation External Failure Occurrence Description</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription#getFailureType__ExternalFailureOccurrenceDescription
 * <em>Failure Type External Failure Occurrence Description</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.reliability.ReliabilityPackage#getExternalFailureOccurrenceDescription()
 * @model
 * @generated
 */
public interface ExternalFailureOccurrenceDescription extends FailureOccurrenceDescription {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '
     * <em><b>Specified Reliability Annotation External Failure Occurrence Description</b></em>'
     * container reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.qosannotations.qos_reliability.SpecifiedReliabilityAnnotation#getExternalFailureOccurrenceDescriptions__SpecifiedReliabilityAnnotation
     * <em>External Failure Occurrence Descriptions Specified Reliability Annotation</em>}'. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '
     * <em>Specified Reliability Annotation External Failure Occurrence Description</em>' container
     * reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '
     *         <em>Specified Reliability Annotation External Failure Occurrence Description</em>'
     *         container reference.
     * @see #setSpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription(SpecifiedReliabilityAnnotation)
     * @see org.palladiosimulator.pcm.reliability.ReliabilityPackage#getExternalFailureOccurrenceDescription_SpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription()
     * @see org.palladiosimulator.qosannotations.qos_reliability.SpecifiedReliabilityAnnotation#getExternalFailureOccurrenceDescriptions__SpecifiedReliabilityAnnotation
     * @model opposite="externalFailureOccurrenceDescriptions__SpecifiedReliabilityAnnotation"
     *        required="true" transient="false"
     * @generated
     */
    SpecifiedReliabilityAnnotation getSpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription#getSpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription
     * <em>Specified Reliability Annotation External Failure Occurrence Description</em>}' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Specified Reliability Annotation External Failure Occurrence Description</em>'
     *            container reference.
     * @see #getSpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription()
     * @generated
     */
    void setSpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription(SpecifiedReliabilityAnnotation value);

    /**
     * Returns the value of the '
     * <em><b>Failure Type External Failure Occurrence Description</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Failure Type External Failure Occurrence Description</em>'
     * reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Failure Type External Failure Occurrence Description</em>'
     *         reference.
     * @see #setFailureType__ExternalFailureOccurrenceDescription(FailureType)
     * @see org.palladiosimulator.pcm.reliability.ReliabilityPackage#getExternalFailureOccurrenceDescription_FailureType__ExternalFailureOccurrenceDescription()
     * @model required="true"
     * @generated
     */
    FailureType getFailureType__ExternalFailureOccurrenceDescription();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription#getFailureType__ExternalFailureOccurrenceDescription
     * <em>Failure Type External Failure Occurrence Description</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Failure Type External Failure Occurrence Description</em>' reference.
     * @see #getFailureType__ExternalFailureOccurrenceDescription()
     * @generated
     */
    void setFailureType__ExternalFailureOccurrenceDescription(FailureType value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * ResourceTimeoutFailures&nbsp;are&nbsp;software-induced&nbsp;failure&nbsp;types,&nbsp;but&nbsp
     * ;are&nbsp;not&nbsp;annotated&nbsp;to&nbsp;system-external
     * calls&nbsp;(they&nbsp;are&nbsp;only&nbsp;meant<br />
     * for&nbsp;passive&nbsp;resource&nbsp;timeouts).
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='not self.failureType__ExternalFailureOccurrenceDescription.oclIsTypeOf(ResourceTimeoutFailureType)'"
     * @generated
     */
    boolean NoResourceTimeoutFailureAllowedForExternalFailureOccurrenceDescription(DiagnosticChain diagnostics,
            Map<Object, Object> context);

} // ExternalFailureOccurrenceDescription
