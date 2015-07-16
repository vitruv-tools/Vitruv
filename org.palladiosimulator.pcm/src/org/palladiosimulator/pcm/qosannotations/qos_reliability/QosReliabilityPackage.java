/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_reliability;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.palladiosimulator.pcm.qosannotations.QosannotationsPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta
 * objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc --> <!-- begin-model-doc -->
 * Reliability&nbsp;aspects&nbsp;of&nbsp;QoS&nbsp;annotations. <!-- end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.qosannotations.qos_reliability.QosReliabilityFactory
 * @model kind="package" annotation=
 *        "http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface QosReliabilityPackage extends EPackage {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "qos_reliability";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://palladiosimulator.org/PalladioComponentModel/QoSAnnotations/QoS_Reliability/5.1";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "qos_reliability";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    QosReliabilityPackage eINSTANCE = org.palladiosimulator.pcm.qosannotations.qos_reliability.impl.QosReliabilityPackageImpl
            .init();

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.qosannotations.qos_reliability.impl.SpecifiedReliabilityAnnotationImpl
     * <em>Specified Reliability Annotation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see org.palladiosimulator.pcm.qosannotations.qos_reliability.impl.
     *      SpecifiedReliabilityAnnotationImpl
     * @see org.palladiosimulator.pcm.qosannotations.qos_reliability.impl.QosReliabilityPackageImpl#getSpecifiedReliabilityAnnotation()
     * @generated
     */
    int SPECIFIED_RELIABILITY_ANNOTATION = 0;

    /**
     * The feature id for the '<em><b>Signature Specified Qo SAnnation</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SPECIFIED_RELIABILITY_ANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION = QosannotationsPackage.SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION;

    /**
     * The feature id for the '<em><b>Role Specified Qo SAnnotation</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SPECIFIED_RELIABILITY_ANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION = QosannotationsPackage.SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION;

    /**
     * The feature id for the '<em><b>Qos Annotations Specified Qo SAnnotation</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SPECIFIED_RELIABILITY_ANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION = QosannotationsPackage.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION;

    /**
     * The feature id for the '
     * <em><b>External Failure Occurrence Descriptions Specified Reliability Annotation</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SPECIFIED_RELIABILITY_ANNOTATION__EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTIONS_SPECIFIED_RELIABILITY_ANNOTATION = QosannotationsPackage.SPECIFIED_QO_SANNOTATION_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '<em>Specified Reliability Annotation</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SPECIFIED_RELIABILITY_ANNOTATION_FEATURE_COUNT = QosannotationsPackage.SPECIFIED_QO_SANNOTATION_FEATURE_COUNT
            + 1;

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.qosannotations.qos_reliability.SpecifiedReliabilityAnnotation
     * <em>Specified Reliability Annotation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Specified Reliability Annotation</em>'.
     * @see org.palladiosimulator.pcm.qosannotations.qos_reliability.SpecifiedReliabilityAnnotation
     * @generated
     */
    EClass getSpecifiedReliabilityAnnotation();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.qosannotations.qos_reliability.SpecifiedReliabilityAnnotation#getExternalFailureOccurrenceDescriptions__SpecifiedReliabilityAnnotation
     * <em>External Failure Occurrence Descriptions Specified Reliability Annotation</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '
     *         <em>External Failure Occurrence Descriptions Specified Reliability Annotation</em>'.
     * @see org.palladiosimulator.pcm.qosannotations.qos_reliability.SpecifiedReliabilityAnnotation#getExternalFailureOccurrenceDescriptions__SpecifiedReliabilityAnnotation()
     * @see #getSpecifiedReliabilityAnnotation()
     * @generated
     */
    EReference getSpecifiedReliabilityAnnotation_ExternalFailureOccurrenceDescriptions__SpecifiedReliabilityAnnotation();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    QosReliabilityFactory getQosReliabilityFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     *
     * @generated
     */
    interface Literals {

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.qosannotations.qos_reliability.impl.SpecifiedReliabilityAnnotationImpl
         * <em>Specified Reliability Annotation</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.qosannotations.qos_reliability.impl.
         *      SpecifiedReliabilityAnnotationImpl
         * @see org.palladiosimulator.pcm.qosannotations.qos_reliability.impl.QosReliabilityPackageImpl#getSpecifiedReliabilityAnnotation()
         * @generated
         */
        EClass SPECIFIED_RELIABILITY_ANNOTATION = eINSTANCE.getSpecifiedReliabilityAnnotation();

        /**
         * The meta object literal for the '
         * <em><b>External Failure Occurrence Descriptions Specified Reliability Annotation</b></em>
         * ' containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SPECIFIED_RELIABILITY_ANNOTATION__EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTIONS_SPECIFIED_RELIABILITY_ANNOTATION = eINSTANCE
                .getSpecifiedReliabilityAnnotation_ExternalFailureOccurrenceDescriptions__SpecifiedReliabilityAnnotation();

    }

} // QosReliabilityPackage
