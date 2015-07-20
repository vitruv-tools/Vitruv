/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_performance;

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
 * <!-- end-user-doc --> <!-- begin-model-doc --> Performance aspects of QoS annotations. <!--
 * end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.qosannotations.qos_performance.QosPerformanceFactory
 * @model kind="package" annotation=
 *        "http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface QosPerformancePackage extends EPackage {

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
    String eNAME = "qos_performance";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_URI = "http://palladiosimulator.org/PalladioComponentModel/QoSAnnotations/QoS_Performance/5.1";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_PREFIX = "qos_performance";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    QosPerformancePackage eINSTANCE = org.palladiosimulator.pcm.qosannotations.qos_performance.impl.QosPerformancePackageImpl
            .init();

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.impl.SpecifiedExecutionTimeImpl
     * <em>Specified Execution Time</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.qosannotations.qos_performance.impl.SpecifiedExecutionTimeImpl
     * @see org.palladiosimulator.pcm.qosannotations.qos_performance.impl.QosPerformancePackageImpl#getSpecifiedExecutionTime()
     * @generated
     */
    int SPECIFIED_EXECUTION_TIME = 1;

    /**
     * The feature id for the '<em><b>Signature Specified Qo SAnnation</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SPECIFIED_EXECUTION_TIME__SIGNATURE_SPECIFIED_QO_SANNATION = QosannotationsPackage.SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION;

    /**
     * The feature id for the '<em><b>Role Specified Qo SAnnotation</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SPECIFIED_EXECUTION_TIME__ROLE_SPECIFIED_QO_SANNOTATION = QosannotationsPackage.SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION;

    /**
     * The feature id for the '<em><b>Qos Annotations Specified Qo SAnnotation</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SPECIFIED_EXECUTION_TIME__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION = QosannotationsPackage.SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION;

    /**
     * The feature id for the '<em><b>Specification Specified Execution Time</b></em>' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME = QosannotationsPackage.SPECIFIED_QO_SANNOTATION_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '<em>Specified Execution Time</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SPECIFIED_EXECUTION_TIME_FEATURE_COUNT = QosannotationsPackage.SPECIFIED_QO_SANNOTATION_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.impl.SystemSpecifiedExecutionTimeImpl
     * <em>System Specified Execution Time</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see org.palladiosimulator.pcm.qosannotations.qos_performance.impl.
     *      SystemSpecifiedExecutionTimeImpl
     * @see org.palladiosimulator.pcm.qosannotations.qos_performance.impl.QosPerformancePackageImpl#getSystemSpecifiedExecutionTime()
     * @generated
     */
    int SYSTEM_SPECIFIED_EXECUTION_TIME = 0;

    /**
     * The feature id for the '<em><b>Signature Specified Qo SAnnation</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SYSTEM_SPECIFIED_EXECUTION_TIME__SIGNATURE_SPECIFIED_QO_SANNATION = SPECIFIED_EXECUTION_TIME__SIGNATURE_SPECIFIED_QO_SANNATION;

    /**
     * The feature id for the '<em><b>Role Specified Qo SAnnotation</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SYSTEM_SPECIFIED_EXECUTION_TIME__ROLE_SPECIFIED_QO_SANNOTATION = SPECIFIED_EXECUTION_TIME__ROLE_SPECIFIED_QO_SANNOTATION;

    /**
     * The feature id for the '<em><b>Qos Annotations Specified Qo SAnnotation</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SYSTEM_SPECIFIED_EXECUTION_TIME__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION = SPECIFIED_EXECUTION_TIME__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION;

    /**
     * The feature id for the '<em><b>Specification Specified Execution Time</b></em>' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SYSTEM_SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME = SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME;

    /**
     * The number of structural features of the '<em>System Specified Execution Time</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SYSTEM_SPECIFIED_EXECUTION_TIME_FEATURE_COUNT = SPECIFIED_EXECUTION_TIME_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.impl.ComponentSpecifiedExecutionTimeImpl
     * <em>Component Specified Execution Time</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.qosannotations.qos_performance.impl.
     *      ComponentSpecifiedExecutionTimeImpl
     * @see org.palladiosimulator.pcm.qosannotations.qos_performance.impl.QosPerformancePackageImpl#getComponentSpecifiedExecutionTime()
     * @generated
     */
    int COMPONENT_SPECIFIED_EXECUTION_TIME = 2;

    /**
     * The feature id for the '<em><b>Signature Specified Qo SAnnation</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPONENT_SPECIFIED_EXECUTION_TIME__SIGNATURE_SPECIFIED_QO_SANNATION = SPECIFIED_EXECUTION_TIME__SIGNATURE_SPECIFIED_QO_SANNATION;

    /**
     * The feature id for the '<em><b>Role Specified Qo SAnnotation</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPONENT_SPECIFIED_EXECUTION_TIME__ROLE_SPECIFIED_QO_SANNOTATION = SPECIFIED_EXECUTION_TIME__ROLE_SPECIFIED_QO_SANNOTATION;

    /**
     * The feature id for the '<em><b>Qos Annotations Specified Qo SAnnotation</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPONENT_SPECIFIED_EXECUTION_TIME__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION = SPECIFIED_EXECUTION_TIME__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION;

    /**
     * The feature id for the '<em><b>Specification Specified Execution Time</b></em>' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPONENT_SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME = SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME;

    /**
     * The feature id for the '<em><b>Assembly Context Component Specified Execution Time</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPONENT_SPECIFIED_EXECUTION_TIME__ASSEMBLY_CONTEXT_COMPONENT_SPECIFIED_EXECUTION_TIME = SPECIFIED_EXECUTION_TIME_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '<em>Component Specified Execution Time</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPONENT_SPECIFIED_EXECUTION_TIME_FEATURE_COUNT = SPECIFIED_EXECUTION_TIME_FEATURE_COUNT + 1;

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.SystemSpecifiedExecutionTime
     * <em>System Specified Execution Time</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>System Specified Execution Time</em>'.
     * @see org.palladiosimulator.pcm.qosannotations.qos_performance.SystemSpecifiedExecutionTime
     * @generated
     */
    EClass getSystemSpecifiedExecutionTime();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.SpecifiedExecutionTime
     * <em>Specified Execution Time</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Specified Execution Time</em>'.
     * @see org.palladiosimulator.pcm.qosannotations.qos_performance.SpecifiedExecutionTime
     * @generated
     */
    EClass getSpecifiedExecutionTime();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.SpecifiedExecutionTime#getSpecification_SpecifiedExecutionTime
     * <em>Specification Specified Execution Time</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the containment reference '
     *         <em>Specification Specified Execution Time</em>'.
     * @see org.palladiosimulator.pcm.qosannotations.qos_performance.SpecifiedExecutionTime#getSpecification_SpecifiedExecutionTime()
     * @see #getSpecifiedExecutionTime()
     * @generated
     */
    EReference getSpecifiedExecutionTime_Specification_SpecifiedExecutionTime();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.ComponentSpecifiedExecutionTime
     * <em>Component Specified Execution Time</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Component Specified Execution Time</em>'.
     * @see org.palladiosimulator.pcm.qosannotations.qos_performance.ComponentSpecifiedExecutionTime
     * @generated
     */
    EClass getComponentSpecifiedExecutionTime();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.ComponentSpecifiedExecutionTime#getAssemblyContext_ComponentSpecifiedExecutionTime
     * <em>Assembly Context Component Specified Execution Time</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the reference '
     *         <em>Assembly Context Component Specified Execution Time</em>'.
     * @see org.palladiosimulator.pcm.qosannotations.qos_performance.ComponentSpecifiedExecutionTime#getAssemblyContext_ComponentSpecifiedExecutionTime()
     * @see #getComponentSpecifiedExecutionTime()
     * @generated
     */
    EReference getComponentSpecifiedExecutionTime_AssemblyContext_ComponentSpecifiedExecutionTime();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the factory that creates the instances of the model.
     * @generated
     */
    QosPerformanceFactory getQosPerformanceFactory();

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
         * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.impl.SystemSpecifiedExecutionTimeImpl
         * <em>System Specified Execution Time</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.qosannotations.qos_performance.impl.
         *      SystemSpecifiedExecutionTimeImpl
         * @see org.palladiosimulator.pcm.qosannotations.qos_performance.impl.QosPerformancePackageImpl#getSystemSpecifiedExecutionTime()
         * @generated
         */
        EClass SYSTEM_SPECIFIED_EXECUTION_TIME = eINSTANCE.getSystemSpecifiedExecutionTime();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.impl.SpecifiedExecutionTimeImpl
         * <em>Specified Execution Time</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.qosannotations.qos_performance.impl.
         *      SpecifiedExecutionTimeImpl
         * @see org.palladiosimulator.pcm.qosannotations.qos_performance.impl.QosPerformancePackageImpl#getSpecifiedExecutionTime()
         * @generated
         */
        EClass SPECIFIED_EXECUTION_TIME = eINSTANCE.getSpecifiedExecutionTime();

        /**
         * The meta object literal for the '<em><b>Specification Specified Execution Time</b></em>'
         * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference SPECIFIED_EXECUTION_TIME__SPECIFICATION_SPECIFIED_EXECUTION_TIME = eINSTANCE
                .getSpecifiedExecutionTime_Specification_SpecifiedExecutionTime();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.qosannotations.qos_performance.impl.ComponentSpecifiedExecutionTimeImpl
         * <em>Component Specified Execution Time</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.qosannotations.qos_performance.impl.
         *      ComponentSpecifiedExecutionTimeImpl
         * @see org.palladiosimulator.pcm.qosannotations.qos_performance.impl.QosPerformancePackageImpl#getComponentSpecifiedExecutionTime()
         * @generated
         */
        EClass COMPONENT_SPECIFIED_EXECUTION_TIME = eINSTANCE.getComponentSpecifiedExecutionTime();

        /**
         * The meta object literal for the '
         * <em><b>Assembly Context Component Specified Execution Time</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference COMPONENT_SPECIFIED_EXECUTION_TIME__ASSEMBLY_CONTEXT_COMPONENT_SPECIFIED_EXECUTION_TIME = eINSTANCE
                .getComponentSpecifiedExecutionTime_AssemblyContext_ComponentSpecifiedExecutionTime();

    }

} // QosPerformancePackage
