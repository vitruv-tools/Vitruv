/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourcetype;

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
 * <!-- end-user-doc --> <!-- begin-model-doc --> Package containing all resource types supported by
 * the PCM <!-- end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.resourcetype.ResourcetypeFactory
 * @model kind="package"
 * @generated
 */
public interface ResourcetypePackage extends EPackage {

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
    String eNAME = "resourcetype";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://palladiosimulator.org/PalladioComponentModel/ResourceType/5.1";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "resourcetype";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    ResourcetypePackage eINSTANCE = org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl.init();

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceSignatureImpl
     * <em>Resource Signature</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.resourcetype.impl.ResourceSignatureImpl
     * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getResourceSignature()
     * @generated
     */
    int RESOURCE_SIGNATURE = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_SIGNATURE__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_SIGNATURE__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parameter Resource Signature</b></em>' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Resource Service Id</b></em>' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_SIGNATURE__RESOURCE_SERVICE_ID = EntityPackage.ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Resource Interface Resource Signature</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE = EntityPackage.ENTITY_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Resource Signature</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_SIGNATURE_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceTypeImpl <em>Resource Type</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.resourcetype.impl.ResourceTypeImpl
     * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getResourceType()
     * @generated
     */
    int RESOURCE_TYPE = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Unit</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__UNIT = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '
     * <em><b>Resource Provided Roles Resource Interface Providing Entity</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY = EntityPackage.ENTITY_FEATURE_COUNT
            + 1;

    /**
     * The feature id for the '<em><b>Resource Repository Resource Type</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE = EntityPackage.ENTITY_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Resource Type</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.resourcetype.impl.ProcessingResourceTypeImpl
     * <em>Processing Resource Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.resourcetype.impl.ProcessingResourceTypeImpl
     * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getProcessingResourceType()
     * @generated
     */
    int PROCESSING_RESOURCE_TYPE = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROCESSING_RESOURCE_TYPE__ID = RESOURCE_TYPE__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROCESSING_RESOURCE_TYPE__ENTITY_NAME = RESOURCE_TYPE__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Unit</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROCESSING_RESOURCE_TYPE__UNIT = RESOURCE_TYPE__UNIT;

    /**
     * The feature id for the '
     * <em><b>Resource Provided Roles Resource Interface Providing Entity</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROCESSING_RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY = RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY;

    /**
     * The feature id for the '<em><b>Resource Repository Resource Type</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROCESSING_RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE = RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE;

    /**
     * The feature id for the '
     * <em><b>Hardware Induced Failure Type Processing Resource Type</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROCESSING_RESOURCE_TYPE__HARDWARE_INDUCED_FAILURE_TYPE_PROCESSING_RESOURCE_TYPE = RESOURCE_TYPE_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '<em>Processing Resource Type</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROCESSING_RESOURCE_TYPE_FEATURE_COUNT = RESOURCE_TYPE_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceRepositoryImpl
     * <em>Resource Repository</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.resourcetype.impl.ResourceRepositoryImpl
     * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getResourceRepository()
     * @generated
     */
    int RESOURCE_REPOSITORY = 3;

    /**
     * The feature id for the '<em><b>Resource Interfaces Resource Repository</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_REPOSITORY__RESOURCE_INTERFACES_RESOURCE_REPOSITORY = 0;

    /**
     * The feature id for the '<em><b>Scheduling Policies Resource Repository</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_REPOSITORY__SCHEDULING_POLICIES_RESOURCE_REPOSITORY = 1;

    /**
     * The feature id for the '<em><b>Available Resource Types Resource Repository</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_REPOSITORY__AVAILABLE_RESOURCE_TYPES_RESOURCE_REPOSITORY = 2;

    /**
     * The number of structural features of the '<em>Resource Repository</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_REPOSITORY_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.resourcetype.impl.SchedulingPolicyImpl
     * <em>Scheduling Policy</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.resourcetype.impl.SchedulingPolicyImpl
     * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getSchedulingPolicy()
     * @generated
     */
    int SCHEDULING_POLICY = 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SCHEDULING_POLICY__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SCHEDULING_POLICY__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Resource Repository Scheduling Policy</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SCHEDULING_POLICY__RESOURCE_REPOSITORY_SCHEDULING_POLICY = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Scheduling Policy</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SCHEDULING_POLICY_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.resourcetype.impl.CommunicationLinkResourceTypeImpl
     * <em>Communication Link Resource Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see org.palladiosimulator.pcm.resourcetype.impl.CommunicationLinkResourceTypeImpl
     * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getCommunicationLinkResourceType()
     * @generated
     */
    int COMMUNICATION_LINK_RESOURCE_TYPE = 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMMUNICATION_LINK_RESOURCE_TYPE__ID = RESOURCE_TYPE__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMMUNICATION_LINK_RESOURCE_TYPE__ENTITY_NAME = RESOURCE_TYPE__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Unit</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMMUNICATION_LINK_RESOURCE_TYPE__UNIT = RESOURCE_TYPE__UNIT;

    /**
     * The feature id for the '
     * <em><b>Resource Provided Roles Resource Interface Providing Entity</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMMUNICATION_LINK_RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY = RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY;

    /**
     * The feature id for the '<em><b>Resource Repository Resource Type</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMMUNICATION_LINK_RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE = RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE;

    /**
     * The feature id for the '
     * <em><b>Network Induced Failure Type Communication Link Resource Type</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMMUNICATION_LINK_RESOURCE_TYPE__NETWORK_INDUCED_FAILURE_TYPE_COMMUNICATION_LINK_RESOURCE_TYPE = RESOURCE_TYPE_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '<em>Communication Link Resource Type</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMMUNICATION_LINK_RESOURCE_TYPE_FEATURE_COUNT = RESOURCE_TYPE_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceInterfaceImpl
     * <em>Resource Interface</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.resourcetype.impl.ResourceInterfaceImpl
     * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getResourceInterface()
     * @generated
     */
    int RESOURCE_INTERFACE = 6;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Resource Repository Resource Interface</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Resource Signatures Resource Interface</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE__RESOURCE_SIGNATURES_RESOURCE_INTERFACE = EntityPackage.ENTITY_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Resource Interface</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 2;

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceSignature <em>Resource Signature</em>}
     * '. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Resource Signature</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceSignature
     * @generated
     */
    EClass getResourceSignature();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceSignature#getParameter__ResourceSignature
     * <em>Parameter Resource Signature</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '
     *         <em>Parameter Resource Signature</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceSignature#getParameter__ResourceSignature()
     * @see #getResourceSignature()
     * @generated
     */
    EReference getResourceSignature_Parameter__ResourceSignature();

    /**
     * Returns the meta object for the attribute '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceSignature#getResourceServiceId
     * <em>Resource Service Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Resource Service Id</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceSignature#getResourceServiceId()
     * @see #getResourceSignature()
     * @generated
     */
    EAttribute getResourceSignature_ResourceServiceId();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceSignature#getResourceInterface__ResourceSignature
     * <em>Resource Interface Resource Signature</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the container reference '
     *         <em>Resource Interface Resource Signature</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceSignature#getResourceInterface__ResourceSignature()
     * @see #getResourceSignature()
     * @generated
     */
    EReference getResourceSignature_ResourceInterface__ResourceSignature();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.resourcetype.ProcessingResourceType
     * <em>Processing Resource Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Processing Resource Type</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ProcessingResourceType
     * @generated
     */
    EClass getProcessingResourceType();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.resourcetype.ProcessingResourceType#getHardwareInducedFailureType__ProcessingResourceType
     * <em>Hardware Induced Failure Type Processing Resource Type</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Hardware Induced Failure Type Processing Resource Type</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ProcessingResourceType#getHardwareInducedFailureType__ProcessingResourceType()
     * @see #getProcessingResourceType()
     * @generated
     */
    EReference getProcessingResourceType_HardwareInducedFailureType__ProcessingResourceType();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.resourcetype.ResourceType
     * <em>Resource Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Resource Type</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceType
     * @generated
     */
    EClass getResourceType();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceType#getResourceRepository_ResourceType
     * <em>Resource Repository Resource Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the container reference '
     *         <em>Resource Repository Resource Type</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceType#getResourceRepository_ResourceType()
     * @see #getResourceType()
     * @generated
     */
    EReference getResourceType_ResourceRepository_ResourceType();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceRepository <em>Resource Repository</em>
     * }'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Resource Repository</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceRepository
     * @generated
     */
    EClass getResourceRepository();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceRepository#getResourceInterfaces__ResourceRepository
     * <em>Resource Interfaces Resource Repository</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the containment reference list '
     *         <em>Resource Interfaces Resource Repository</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceRepository#getResourceInterfaces__ResourceRepository()
     * @see #getResourceRepository()
     * @generated
     */
    EReference getResourceRepository_ResourceInterfaces__ResourceRepository();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceRepository#getSchedulingPolicies__ResourceRepository
     * <em>Scheduling Policies Resource Repository</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the containment reference list '
     *         <em>Scheduling Policies Resource Repository</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceRepository#getSchedulingPolicies__ResourceRepository()
     * @see #getResourceRepository()
     * @generated
     */
    EReference getResourceRepository_SchedulingPolicies__ResourceRepository();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceRepository#getAvailableResourceTypes_ResourceRepository
     * <em>Available Resource Types Resource Repository</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '
     *         <em>Available Resource Types Resource Repository</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceRepository#getAvailableResourceTypes_ResourceRepository()
     * @see #getResourceRepository()
     * @generated
     */
    EReference getResourceRepository_AvailableResourceTypes_ResourceRepository();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.resourcetype.SchedulingPolicy <em>Scheduling Policy</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Scheduling Policy</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.SchedulingPolicy
     * @generated
     */
    EClass getSchedulingPolicy();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.resourcetype.SchedulingPolicy#getResourceRepository__SchedulingPolicy
     * <em>Resource Repository Scheduling Policy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the container reference '
     *         <em>Resource Repository Scheduling Policy</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.SchedulingPolicy#getResourceRepository__SchedulingPolicy()
     * @see #getSchedulingPolicy()
     * @generated
     */
    EReference getSchedulingPolicy_ResourceRepository__SchedulingPolicy();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType
     * <em>Communication Link Resource Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Communication Link Resource Type</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType
     * @generated
     */
    EClass getCommunicationLinkResourceType();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType#getNetworkInducedFailureType__CommunicationLinkResourceType
     * <em>Network Induced Failure Type Communication Link Resource Type</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Network Induced Failure Type Communication Link Resource Type</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType#getNetworkInducedFailureType__CommunicationLinkResourceType()
     * @see #getCommunicationLinkResourceType()
     * @generated
     */
    EReference getCommunicationLinkResourceType_NetworkInducedFailureType__CommunicationLinkResourceType();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface <em>Resource Interface</em>}
     * '. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Resource Interface</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceInterface
     * @generated
     */
    EClass getResourceInterface();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface#getResourceRepository__ResourceInterface
     * <em>Resource Repository Resource Interface</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the container reference '
     *         <em>Resource Repository Resource Interface</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceInterface#getResourceRepository__ResourceInterface()
     * @see #getResourceInterface()
     * @generated
     */
    EReference getResourceInterface_ResourceRepository__ResourceInterface();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface#getResourceSignatures__ResourceInterface
     * <em>Resource Signatures Resource Interface</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the containment reference list '
     *         <em>Resource Signatures Resource Interface</em>'.
     * @see org.palladiosimulator.pcm.resourcetype.ResourceInterface#getResourceSignatures__ResourceInterface()
     * @see #getResourceInterface()
     * @generated
     */
    EReference getResourceInterface_ResourceSignatures__ResourceInterface();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ResourcetypeFactory getResourcetypeFactory();

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
         * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceSignatureImpl
         * <em>Resource Signature</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.resourcetype.impl.ResourceSignatureImpl
         * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getResourceSignature()
         * @generated
         */
        EClass RESOURCE_SIGNATURE = eINSTANCE.getResourceSignature();

        /**
         * The meta object literal for the '<em><b>Parameter Resource Signature</b></em>'
         * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE = eINSTANCE
                .getResourceSignature_Parameter__ResourceSignature();

        /**
         * The meta object literal for the '<em><b>Resource Service Id</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute RESOURCE_SIGNATURE__RESOURCE_SERVICE_ID = eINSTANCE.getResourceSignature_ResourceServiceId();

        /**
         * The meta object literal for the '<em><b>Resource Interface Resource Signature</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE = eINSTANCE
                .getResourceSignature_ResourceInterface__ResourceSignature();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.resourcetype.impl.ProcessingResourceTypeImpl
         * <em>Processing Resource Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.resourcetype.impl.ProcessingResourceTypeImpl
         * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getProcessingResourceType()
         * @generated
         */
        EClass PROCESSING_RESOURCE_TYPE = eINSTANCE.getProcessingResourceType();

        /**
         * The meta object literal for the '
         * <em><b>Hardware Induced Failure Type Processing Resource Type</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PROCESSING_RESOURCE_TYPE__HARDWARE_INDUCED_FAILURE_TYPE_PROCESSING_RESOURCE_TYPE = eINSTANCE
                .getProcessingResourceType_HardwareInducedFailureType__ProcessingResourceType();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceTypeImpl
         * <em>Resource Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.resourcetype.impl.ResourceTypeImpl
         * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getResourceType()
         * @generated
         */
        EClass RESOURCE_TYPE = eINSTANCE.getResourceType();

        /**
         * The meta object literal for the '<em><b>Resource Repository Resource Type</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE = eINSTANCE
                .getResourceType_ResourceRepository_ResourceType();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceRepositoryImpl
         * <em>Resource Repository</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.resourcetype.impl.ResourceRepositoryImpl
         * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getResourceRepository()
         * @generated
         */
        EClass RESOURCE_REPOSITORY = eINSTANCE.getResourceRepository();

        /**
         * The meta object literal for the '<em><b>Resource Interfaces Resource Repository</b></em>'
         * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RESOURCE_REPOSITORY__RESOURCE_INTERFACES_RESOURCE_REPOSITORY = eINSTANCE
                .getResourceRepository_ResourceInterfaces__ResourceRepository();

        /**
         * The meta object literal for the '<em><b>Scheduling Policies Resource Repository</b></em>'
         * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RESOURCE_REPOSITORY__SCHEDULING_POLICIES_RESOURCE_REPOSITORY = eINSTANCE
                .getResourceRepository_SchedulingPolicies__ResourceRepository();

        /**
         * The meta object literal for the '
         * <em><b>Available Resource Types Resource Repository</b></em>' containment reference list
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RESOURCE_REPOSITORY__AVAILABLE_RESOURCE_TYPES_RESOURCE_REPOSITORY = eINSTANCE
                .getResourceRepository_AvailableResourceTypes_ResourceRepository();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.resourcetype.impl.SchedulingPolicyImpl
         * <em>Scheduling Policy</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.resourcetype.impl.SchedulingPolicyImpl
         * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getSchedulingPolicy()
         * @generated
         */
        EClass SCHEDULING_POLICY = eINSTANCE.getSchedulingPolicy();

        /**
         * The meta object literal for the '<em><b>Resource Repository Scheduling Policy</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SCHEDULING_POLICY__RESOURCE_REPOSITORY_SCHEDULING_POLICY = eINSTANCE
                .getSchedulingPolicy_ResourceRepository__SchedulingPolicy();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.resourcetype.impl.CommunicationLinkResourceTypeImpl
         * <em>Communication Link Resource Type</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.resourcetype.impl.CommunicationLinkResourceTypeImpl
         * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getCommunicationLinkResourceType()
         * @generated
         */
        EClass COMMUNICATION_LINK_RESOURCE_TYPE = eINSTANCE.getCommunicationLinkResourceType();

        /**
         * The meta object literal for the '
         * <em><b>Network Induced Failure Type Communication Link Resource Type</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMMUNICATION_LINK_RESOURCE_TYPE__NETWORK_INDUCED_FAILURE_TYPE_COMMUNICATION_LINK_RESOURCE_TYPE = eINSTANCE
                .getCommunicationLinkResourceType_NetworkInducedFailureType__CommunicationLinkResourceType();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceInterfaceImpl
         * <em>Resource Interface</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.resourcetype.impl.ResourceInterfaceImpl
         * @see org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl#getResourceInterface()
         * @generated
         */
        EClass RESOURCE_INTERFACE = eINSTANCE.getResourceInterface();

        /**
         * The meta object literal for the '<em><b>Resource Repository Resource Interface</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE = eINSTANCE
                .getResourceInterface_ResourceRepository__ResourceInterface();

        /**
         * The meta object literal for the '<em><b>Resource Signatures Resource Interface</b></em>'
         * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RESOURCE_INTERFACE__RESOURCE_SIGNATURES_RESOURCE_INTERFACE = eINSTANCE
                .getResourceInterface_ResourceSignatures__ResourceInterface();

    }

} // ResourcetypePackage
