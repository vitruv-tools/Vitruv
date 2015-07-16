/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_reliability;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation;
import org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Specified Reliability Annotation</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> A SpecifiedFailureProbability of a service resembles its
 * "Probability of Failure on Demand" (POFOD). Whenever the service is called, this values states
 * the probability that it returns successfully. For reliability prediction, the PCMRandomVariable
 * specified in its superclass SpecifiedQoSAnnotation must evaluate to a real number between 0 and
 * 1.
 *
 *
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.qos_reliability.SpecifiedReliabilityAnnotation#getExternalFailureOccurrenceDescriptions__SpecifiedReliabilityAnnotation
 * <em>External Failure Occurrence Descriptions Specified Reliability Annotation</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.qosannotations.qos_reliability.QosReliabilityPackage#getSpecifiedReliabilityAnnotation()
 * @model
 * @generated
 */
public interface SpecifiedReliabilityAnnotation extends SpecifiedQoSAnnotation {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '
     * <em><b>External Failure Occurrence Descriptions Specified Reliability Annotation</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription}. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription#getSpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription
     * <em>Specified Reliability Annotation External Failure Occurrence Description</em>}'. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '
     * <em>External Failure Occurrence Descriptions Specified Reliability Annotation</em>'
     * containment reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '
     *         <em>External Failure Occurrence Descriptions Specified Reliability Annotation</em>'
     *         containment reference list.
     * @see org.palladiosimulator.pcm.qosannotations.qos_reliability.QosReliabilityPackage#getSpecifiedReliabilityAnnotation_ExternalFailureOccurrenceDescriptions__SpecifiedReliabilityAnnotation()
     * @see org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription#getSpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription
     * @model opposite="specifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription"
     *        containment="true" required="true" ordered="false"
     * @generated
     */
    EList<ExternalFailureOccurrenceDescription> getExternalFailureOccurrenceDescriptions__SpecifiedReliabilityAnnotation();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='(self.role_SpecifiedQoSAnnotation.oclIsTypeOf(pcm::repository::OperationRequiredRole)) and (self.role_SpecifiedQoSAnnotation.oclAsType(pcm::repository::OperationRequiredRole).requiringEntity_RequiredRole.oclIsTypeOf(pcm::system::System))\r\n\r\n\r\n'"
     * @generated
     */
    boolean SpecifiedReliabilityAnnotationMustReferenceRequiredRoleOfASystem(DiagnosticChain diagnostics,
            Map<Object, Object> context);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.externalFailureOccurrenceDescriptions__SpecifiedReliabilityAnnotation.failureProbability.oclAsType(Real)->sum()<=1.0'"
     * @generated
     */
    boolean SumOfReliabilityAnnotationFailureProbabilitiesMustNotExceed1(DiagnosticChain diagnostics,
            Map<Object, Object> context);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.externalFailureOccurrenceDescriptions__SpecifiedReliabilityAnnotation->forAll(x:pcm::reliability::ExternalFailureOccurrenceDescription,y:pcm::reliability::ExternalFailureOccurrenceDescription  | x<>y implies x.failureType__ExternalFailureOccurrenceDescription <> y.failureType__ExternalFailureOccurrenceDescription )'"
     * @generated
     */
    boolean MultipleExternalOccurrenceDescriptionsPerFailureTypeNotAllowed(DiagnosticChain diagnostics,
            Map<Object, Object> context);

} // SpecifiedReliabilityAnnotation
