/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.reliability;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.palladiosimulator.pcm.core.entity.EntityPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta
 * objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc --> <!-- begin-model-doc --> This package contains reliability-relevant classes
 * such as the definition of failure types. <!-- end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.reliability.ReliabilityFactory
 * @model kind="package" annotation=
 *        "http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface ReliabilityPackage extends EPackage {

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
    String eNAME = "reliability";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_URI = "http://palladiosimulator.org/PalladioComponentModel/Reliability/5.1";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_PREFIX = "reliability";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    ReliabilityPackage eINSTANCE = org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl.init();

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.reliability.impl.FailureOccurrenceDescriptionImpl
     * <em>Failure Occurrence Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see org.palladiosimulator.pcm.reliability.impl.FailureOccurrenceDescriptionImpl
     * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getFailureOccurrenceDescription()
     * @generated
     */
    int FAILURE_OCCURRENCE_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Failure Probability</b></em>' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int FAILURE_OCCURRENCE_DESCRIPTION__FAILURE_PROBABILITY = 0;

    /**
     * The number of structural features of the '<em>Failure Occurrence Description</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int FAILURE_OCCURRENCE_DESCRIPTION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.reliability.impl.FailureTypeImpl
     * <em>Failure Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.reliability.impl.FailureTypeImpl
     * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getFailureType()
     * @generated
     */
    int FAILURE_TYPE = 7;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int FAILURE_TYPE__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int FAILURE_TYPE__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Repository Failure Type</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FAILURE_TYPE__REPOSITORY_FAILURE_TYPE = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Failure Type</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int FAILURE_TYPE_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.reliability.impl.HardwareInducedFailureTypeImpl
     * <em>Hardware Induced Failure Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.reliability.impl.HardwareInducedFailureTypeImpl
     * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getHardwareInducedFailureType()
     * @generated
     */
    int HARDWARE_INDUCED_FAILURE_TYPE = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int HARDWARE_INDUCED_FAILURE_TYPE__ID = FAILURE_TYPE__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int HARDWARE_INDUCED_FAILURE_TYPE__ENTITY_NAME = FAILURE_TYPE__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Repository Failure Type</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int HARDWARE_INDUCED_FAILURE_TYPE__REPOSITORY_FAILURE_TYPE = FAILURE_TYPE__REPOSITORY_FAILURE_TYPE;

    /**
     * The feature id for the '
     * <em><b>Processing Resource Type Hardware Induced Failure Type</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int HARDWARE_INDUCED_FAILURE_TYPE__PROCESSING_RESOURCE_TYPE_HARDWARE_INDUCED_FAILURE_TYPE = FAILURE_TYPE_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '<em>Hardware Induced Failure Type</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int HARDWARE_INDUCED_FAILURE_TYPE_FEATURE_COUNT = FAILURE_TYPE_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.reliability.impl.SoftwareInducedFailureTypeImpl
     * <em>Software Induced Failure Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.reliability.impl.SoftwareInducedFailureTypeImpl
     * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getSoftwareInducedFailureType()
     * @generated
     */
    int SOFTWARE_INDUCED_FAILURE_TYPE = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SOFTWARE_INDUCED_FAILURE_TYPE__ID = FAILURE_TYPE__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SOFTWARE_INDUCED_FAILURE_TYPE__ENTITY_NAME = FAILURE_TYPE__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Repository Failure Type</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOFTWARE_INDUCED_FAILURE_TYPE__REPOSITORY_FAILURE_TYPE = FAILURE_TYPE__REPOSITORY_FAILURE_TYPE;

    /**
     * The feature id for the '
     * <em><b>Internal Failure Occurrence Descriptions Software Induced Failure Type</b></em>'
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SOFTWARE_INDUCED_FAILURE_TYPE__INTERNAL_FAILURE_OCCURRENCE_DESCRIPTIONS_SOFTWARE_INDUCED_FAILURE_TYPE = FAILURE_TYPE_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '<em>Software Induced Failure Type</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOFTWARE_INDUCED_FAILURE_TYPE_FEATURE_COUNT = FAILURE_TYPE_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.reliability.impl.InternalFailureOccurrenceDescriptionImpl
     * <em>Internal Failure Occurrence Description</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.reliability.impl.InternalFailureOccurrenceDescriptionImpl
     * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getInternalFailureOccurrenceDescription()
     * @generated
     */
    int INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = 3;

    /**
     * The feature id for the '<em><b>Failure Probability</b></em>' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION__FAILURE_PROBABILITY = FAILURE_OCCURRENCE_DESCRIPTION__FAILURE_PROBABILITY;

    /**
     * The feature id for the '
     * <em><b>Internal Action Internal Failure Occurrence Description</b></em>' container reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION__INTERNAL_ACTION_INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = FAILURE_OCCURRENCE_DESCRIPTION_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '
     * <em><b>Software Induced Failure Type Internal Failure Occurrence Description</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION__SOFTWARE_INDUCED_FAILURE_TYPE_INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = FAILURE_OCCURRENCE_DESCRIPTION_FEATURE_COUNT
            + 1;

    /**
     * The number of structural features of the '<em>Internal Failure Occurrence Description</em>'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION_FEATURE_COUNT = FAILURE_OCCURRENCE_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.reliability.impl.NetworkInducedFailureTypeImpl
     * <em>Network Induced Failure Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.reliability.impl.NetworkInducedFailureTypeImpl
     * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getNetworkInducedFailureType()
     * @generated
     */
    int NETWORK_INDUCED_FAILURE_TYPE = 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NETWORK_INDUCED_FAILURE_TYPE__ID = FAILURE_TYPE__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NETWORK_INDUCED_FAILURE_TYPE__ENTITY_NAME = FAILURE_TYPE__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Repository Failure Type</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NETWORK_INDUCED_FAILURE_TYPE__REPOSITORY_FAILURE_TYPE = FAILURE_TYPE__REPOSITORY_FAILURE_TYPE;

    /**
     * The feature id for the '
     * <em><b>Communication Link Resource Type Network Induced Failure Type</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NETWORK_INDUCED_FAILURE_TYPE__COMMUNICATION_LINK_RESOURCE_TYPE_NETWORK_INDUCED_FAILURE_TYPE = FAILURE_TYPE_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '<em>Network Induced Failure Type</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NETWORK_INDUCED_FAILURE_TYPE_FEATURE_COUNT = FAILURE_TYPE_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.reliability.impl.ExternalFailureOccurrenceDescriptionImpl
     * <em>External Failure Occurrence Description</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.reliability.impl.ExternalFailureOccurrenceDescriptionImpl
     * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getExternalFailureOccurrenceDescription()
     * @generated
     */
    int EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = 5;

    /**
     * The feature id for the '<em><b>Failure Probability</b></em>' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION__FAILURE_PROBABILITY = FAILURE_OCCURRENCE_DESCRIPTION__FAILURE_PROBABILITY;

    /**
     * The feature id for the '
     * <em><b>Specified Reliability Annotation External Failure Occurrence Description</b></em>'
     * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION__SPECIFIED_RELIABILITY_ANNOTATION_EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = FAILURE_OCCURRENCE_DESCRIPTION_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '<em><b>Failure Type External Failure Occurrence Description</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION__FAILURE_TYPE_EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = FAILURE_OCCURRENCE_DESCRIPTION_FEATURE_COUNT
            + 1;

    /**
     * The number of structural features of the '<em>External Failure Occurrence Description</em>'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION_FEATURE_COUNT = FAILURE_OCCURRENCE_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.reliability.impl.ResourceTimeoutFailureTypeImpl
     * <em>Resource Timeout Failure Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.reliability.impl.ResourceTimeoutFailureTypeImpl
     * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getResourceTimeoutFailureType()
     * @generated
     */
    int RESOURCE_TIMEOUT_FAILURE_TYPE = 6;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_TIMEOUT_FAILURE_TYPE__ID = SOFTWARE_INDUCED_FAILURE_TYPE__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_TIMEOUT_FAILURE_TYPE__ENTITY_NAME = SOFTWARE_INDUCED_FAILURE_TYPE__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Repository Failure Type</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_TIMEOUT_FAILURE_TYPE__REPOSITORY_FAILURE_TYPE = SOFTWARE_INDUCED_FAILURE_TYPE__REPOSITORY_FAILURE_TYPE;

    /**
     * The feature id for the '
     * <em><b>Internal Failure Occurrence Descriptions Software Induced Failure Type</b></em>'
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_TIMEOUT_FAILURE_TYPE__INTERNAL_FAILURE_OCCURRENCE_DESCRIPTIONS_SOFTWARE_INDUCED_FAILURE_TYPE = SOFTWARE_INDUCED_FAILURE_TYPE__INTERNAL_FAILURE_OCCURRENCE_DESCRIPTIONS_SOFTWARE_INDUCED_FAILURE_TYPE;

    /**
     * The feature id for the '<em><b>Passive Resource Resource Timeout Failure Type</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_TIMEOUT_FAILURE_TYPE__PASSIVE_RESOURCE_RESOURCE_TIMEOUT_FAILURE_TYPE = SOFTWARE_INDUCED_FAILURE_TYPE_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '<em>Resource Timeout Failure Type</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_TIMEOUT_FAILURE_TYPE_FEATURE_COUNT = SOFTWARE_INDUCED_FAILURE_TYPE_FEATURE_COUNT + 1;

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.reliability.FailureOccurrenceDescription
     * <em>Failure Occurrence Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Failure Occurrence Description</em>'.
     * @see org.palladiosimulator.pcm.reliability.FailureOccurrenceDescription
     * @generated
     */
    EClass getFailureOccurrenceDescription();

    /**
     * Returns the meta object for the attribute '
     * {@link org.palladiosimulator.pcm.reliability.FailureOccurrenceDescription#getFailureProbability
     * <em>Failure Probability</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the attribute '<em>Failure Probability</em>'.
     * @see org.palladiosimulator.pcm.reliability.FailureOccurrenceDescription#getFailureProbability()
     * @see #getFailureOccurrenceDescription()
     * @generated
     */
    EAttribute getFailureOccurrenceDescription_FailureProbability();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.reliability.HardwareInducedFailureType
     * <em>Hardware Induced Failure Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Hardware Induced Failure Type</em>'.
     * @see org.palladiosimulator.pcm.reliability.HardwareInducedFailureType
     * @generated
     */
    EClass getHardwareInducedFailureType();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.reliability.HardwareInducedFailureType#getProcessingResourceType__HardwareInducedFailureType
     * <em>Processing Resource Type Hardware Induced Failure Type</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the meta object for the reference '
     *         <em>Processing Resource Type Hardware Induced Failure Type</em>'.
     * @see org.palladiosimulator.pcm.reliability.HardwareInducedFailureType#getProcessingResourceType__HardwareInducedFailureType()
     * @see #getHardwareInducedFailureType()
     * @generated
     */
    EReference getHardwareInducedFailureType_ProcessingResourceType__HardwareInducedFailureType();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType
     * <em>Software Induced Failure Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Software Induced Failure Type</em>'.
     * @see org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType
     * @generated
     */
    EClass getSoftwareInducedFailureType();

    /**
     * Returns the meta object for the reference list '
     * {@link org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType#getInternalFailureOccurrenceDescriptions__SoftwareInducedFailureType
     * <em>Internal Failure Occurrence Descriptions Software Induced Failure Type</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '
     *         <em>Internal Failure Occurrence Descriptions Software Induced Failure Type</em>'.
     * @see org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType#getInternalFailureOccurrenceDescriptions__SoftwareInducedFailureType()
     * @see #getSoftwareInducedFailureType()
     * @generated
     */
    EReference getSoftwareInducedFailureType_InternalFailureOccurrenceDescriptions__SoftwareInducedFailureType();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription
     * <em>Internal Failure Occurrence Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for class '<em>Internal Failure Occurrence Description</em>'.
     * @see org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription
     * @generated
     */
    EClass getInternalFailureOccurrenceDescription();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription#getInternalAction__InternalFailureOccurrenceDescription
     * <em>Internal Action Internal Failure Occurrence Description</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '
     *         <em>Internal Action Internal Failure Occurrence Description</em>'.
     * @see org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription#getInternalAction__InternalFailureOccurrenceDescription()
     * @see #getInternalFailureOccurrenceDescription()
     * @generated
     */
    EReference getInternalFailureOccurrenceDescription_InternalAction__InternalFailureOccurrenceDescription();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription#getSoftwareInducedFailureType__InternalFailureOccurrenceDescription
     * <em>Software Induced Failure Type Internal Failure Occurrence Description</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Software Induced Failure Type Internal Failure Occurrence Description</em>'.
     * @see org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription#getSoftwareInducedFailureType__InternalFailureOccurrenceDescription()
     * @see #getInternalFailureOccurrenceDescription()
     * @generated
     */
    EReference getInternalFailureOccurrenceDescription_SoftwareInducedFailureType__InternalFailureOccurrenceDescription();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.reliability.NetworkInducedFailureType
     * <em>Network Induced Failure Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Network Induced Failure Type</em>'.
     * @see org.palladiosimulator.pcm.reliability.NetworkInducedFailureType
     * @generated
     */
    EClass getNetworkInducedFailureType();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.reliability.NetworkInducedFailureType#getCommunicationLinkResourceType__NetworkInducedFailureType
     * <em>Communication Link Resource Type Network Induced Failure Type</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return the meta object for the reference '
     *         <em>Communication Link Resource Type Network Induced Failure Type</em>'.
     * @see org.palladiosimulator.pcm.reliability.NetworkInducedFailureType#getCommunicationLinkResourceType__NetworkInducedFailureType()
     * @see #getNetworkInducedFailureType()
     * @generated
     */
    EReference getNetworkInducedFailureType_CommunicationLinkResourceType__NetworkInducedFailureType();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription
     * <em>External Failure Occurrence Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for class '<em>External Failure Occurrence Description</em>'.
     * @see org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription
     * @generated
     */
    EClass getExternalFailureOccurrenceDescription();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription#getSpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription
     * <em>Specified Reliability Annotation External Failure Occurrence Description</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the container reference '
     *         <em>Specified Reliability Annotation External Failure Occurrence Description</em>'.
     * @see org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription#getSpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription()
     * @see #getExternalFailureOccurrenceDescription()
     * @generated
     */
    EReference getExternalFailureOccurrenceDescription_SpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription#getFailureType__ExternalFailureOccurrenceDescription
     * <em>Failure Type External Failure Occurrence Description</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the reference '
     *         <em>Failure Type External Failure Occurrence Description</em>'.
     * @see org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription#getFailureType__ExternalFailureOccurrenceDescription()
     * @see #getExternalFailureOccurrenceDescription()
     * @generated
     */
    EReference getExternalFailureOccurrenceDescription_FailureType__ExternalFailureOccurrenceDescription();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType
     * <em>Resource Timeout Failure Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Resource Timeout Failure Type</em>'.
     * @see org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType
     * @generated
     */
    EClass getResourceTimeoutFailureType();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType#getPassiveResource__ResourceTimeoutFailureType
     * <em>Passive Resource Resource Timeout Failure Type</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the reference '
     *         <em>Passive Resource Resource Timeout Failure Type</em>'.
     * @see org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType#getPassiveResource__ResourceTimeoutFailureType()
     * @see #getResourceTimeoutFailureType()
     * @generated
     */
    EReference getResourceTimeoutFailureType_PassiveResource__ResourceTimeoutFailureType();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.reliability.FailureType
     * <em>Failure Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Failure Type</em>'.
     * @see org.palladiosimulator.pcm.reliability.FailureType
     * @generated
     */
    EClass getFailureType();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.reliability.FailureType#getRepository__FailureType
     * <em>Repository Failure Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '<em>Repository Failure Type</em>'.
     * @see org.palladiosimulator.pcm.reliability.FailureType#getRepository__FailureType()
     * @see #getFailureType()
     * @generated
     */
    EReference getFailureType_Repository__FailureType();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ReliabilityFactory getReliabilityFactory();

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
         * {@link org.palladiosimulator.pcm.reliability.impl.FailureOccurrenceDescriptionImpl
         * <em>Failure Occurrence Description</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.reliability.impl.FailureOccurrenceDescriptionImpl
         * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getFailureOccurrenceDescription()
         * @generated
         */
        EClass FAILURE_OCCURRENCE_DESCRIPTION = eINSTANCE.getFailureOccurrenceDescription();

        /**
         * The meta object literal for the '<em><b>Failure Probability</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EAttribute FAILURE_OCCURRENCE_DESCRIPTION__FAILURE_PROBABILITY = eINSTANCE
                .getFailureOccurrenceDescription_FailureProbability();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.reliability.impl.HardwareInducedFailureTypeImpl
         * <em>Hardware Induced Failure Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see org.palladiosimulator.pcm.reliability.impl.HardwareInducedFailureTypeImpl
         * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getHardwareInducedFailureType()
         * @generated
         */
        EClass HARDWARE_INDUCED_FAILURE_TYPE = eINSTANCE.getHardwareInducedFailureType();

        /**
         * The meta object literal for the '
         * <em><b>Processing Resource Type Hardware Induced Failure Type</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference HARDWARE_INDUCED_FAILURE_TYPE__PROCESSING_RESOURCE_TYPE_HARDWARE_INDUCED_FAILURE_TYPE = eINSTANCE
                .getHardwareInducedFailureType_ProcessingResourceType__HardwareInducedFailureType();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.reliability.impl.SoftwareInducedFailureTypeImpl
         * <em>Software Induced Failure Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see org.palladiosimulator.pcm.reliability.impl.SoftwareInducedFailureTypeImpl
         * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getSoftwareInducedFailureType()
         * @generated
         */
        EClass SOFTWARE_INDUCED_FAILURE_TYPE = eINSTANCE.getSoftwareInducedFailureType();

        /**
         * The meta object literal for the '
         * <em><b>Internal Failure Occurrence Descriptions Software Induced Failure Type</b></em>'
         * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference SOFTWARE_INDUCED_FAILURE_TYPE__INTERNAL_FAILURE_OCCURRENCE_DESCRIPTIONS_SOFTWARE_INDUCED_FAILURE_TYPE = eINSTANCE
                .getSoftwareInducedFailureType_InternalFailureOccurrenceDescriptions__SoftwareInducedFailureType();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.reliability.impl.InternalFailureOccurrenceDescriptionImpl
         * <em>Internal Failure Occurrence Description</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.reliability.impl.InternalFailureOccurrenceDescriptionImpl
         * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getInternalFailureOccurrenceDescription()
         * @generated
         */
        EClass INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = eINSTANCE.getInternalFailureOccurrenceDescription();

        /**
         * The meta object literal for the '
         * <em><b>Internal Action Internal Failure Occurrence Description</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION__INTERNAL_ACTION_INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = eINSTANCE
                .getInternalFailureOccurrenceDescription_InternalAction__InternalFailureOccurrenceDescription();

        /**
         * The meta object literal for the '
         * <em><b>Software Induced Failure Type Internal Failure Occurrence Description</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION__SOFTWARE_INDUCED_FAILURE_TYPE_INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = eINSTANCE
                .getInternalFailureOccurrenceDescription_SoftwareInducedFailureType__InternalFailureOccurrenceDescription();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.reliability.impl.NetworkInducedFailureTypeImpl
         * <em>Network Induced Failure Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see org.palladiosimulator.pcm.reliability.impl.NetworkInducedFailureTypeImpl
         * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getNetworkInducedFailureType()
         * @generated
         */
        EClass NETWORK_INDUCED_FAILURE_TYPE = eINSTANCE.getNetworkInducedFailureType();

        /**
         * The meta object literal for the '
         * <em><b>Communication Link Resource Type Network Induced Failure Type</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference NETWORK_INDUCED_FAILURE_TYPE__COMMUNICATION_LINK_RESOURCE_TYPE_NETWORK_INDUCED_FAILURE_TYPE = eINSTANCE
                .getNetworkInducedFailureType_CommunicationLinkResourceType__NetworkInducedFailureType();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.reliability.impl.ExternalFailureOccurrenceDescriptionImpl
         * <em>External Failure Occurrence Description</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.reliability.impl.ExternalFailureOccurrenceDescriptionImpl
         * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getExternalFailureOccurrenceDescription()
         * @generated
         */
        EClass EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = eINSTANCE.getExternalFailureOccurrenceDescription();

        /**
         * The meta object literal for the '
         * <em><b>Specified Reliability Annotation External Failure Occurrence Description</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION__SPECIFIED_RELIABILITY_ANNOTATION_EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = eINSTANCE
                .getExternalFailureOccurrenceDescription_SpecifiedReliabilityAnnotation__ExternalFailureOccurrenceDescription();

        /**
         * The meta object literal for the '
         * <em><b>Failure Type External Failure Occurrence Description</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION__FAILURE_TYPE_EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION = eINSTANCE
                .getExternalFailureOccurrenceDescription_FailureType__ExternalFailureOccurrenceDescription();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.reliability.impl.ResourceTimeoutFailureTypeImpl
         * <em>Resource Timeout Failure Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see org.palladiosimulator.pcm.reliability.impl.ResourceTimeoutFailureTypeImpl
         * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getResourceTimeoutFailureType()
         * @generated
         */
        EClass RESOURCE_TIMEOUT_FAILURE_TYPE = eINSTANCE.getResourceTimeoutFailureType();

        /**
         * The meta object literal for the '
         * <em><b>Passive Resource Resource Timeout Failure Type</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RESOURCE_TIMEOUT_FAILURE_TYPE__PASSIVE_RESOURCE_RESOURCE_TIMEOUT_FAILURE_TYPE = eINSTANCE
                .getResourceTimeoutFailureType_PassiveResource__ResourceTimeoutFailureType();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.reliability.impl.FailureTypeImpl <em>Failure Type</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.reliability.impl.FailureTypeImpl
         * @see org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl#getFailureType()
         * @generated
         */
        EClass FAILURE_TYPE = eINSTANCE.getFailureType();

        /**
         * The meta object literal for the '<em><b>Repository Failure Type</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference FAILURE_TYPE__REPOSITORY_FAILURE_TYPE = eINSTANCE.getFailureType_Repository__FailureType();

    }

} // ReliabilityPackage
