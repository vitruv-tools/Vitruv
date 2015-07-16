/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourcetype;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Resource Repository</b></em>
 * '. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Extendable repository of resource types of the PCM. The resource type
 * repository is intentionally left open to support arbitrary resources in the future <!--
 * end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.ResourceRepository#getResourceInterfaces__ResourceRepository
 * <em>Resource Interfaces Resource Repository</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.ResourceRepository#getSchedulingPolicies__ResourceRepository
 * <em>Scheduling Policies Resource Repository</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.ResourceRepository#getAvailableResourceTypes_ResourceRepository
 * <em>Available Resource Types Resource Repository</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceRepository()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ResourceRepository extends CDOObject {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Resource Interfaces Resource Repository</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface#getResourceRepository__ResourceInterface
     * <em>Resource Repository Resource Interface</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Interfaces Resource Repository</em>' containment
     * reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Resource Interfaces Resource Repository</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceRepository_ResourceInterfaces__ResourceRepository()
     * @see org.palladiosimulator.pcm.resourcetype.ResourceInterface#getResourceRepository__ResourceInterface
     * @model opposite="resourceRepository__ResourceInterface" containment="true" ordered="false"
     * @generated
     */
    EList<ResourceInterface> getResourceInterfaces__ResourceRepository();

    /**
     * Returns the value of the '<em><b>Scheduling Policies Resource Repository</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.resourcetype.SchedulingPolicy}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.resourcetype.SchedulingPolicy#getResourceRepository__SchedulingPolicy
     * <em>Resource Repository Scheduling Policy</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Scheduling Policies Resource Repository</em>' containment
     * reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Scheduling Policies Resource Repository</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceRepository_SchedulingPolicies__ResourceRepository()
     * @see org.palladiosimulator.pcm.resourcetype.SchedulingPolicy#getResourceRepository__SchedulingPolicy
     * @model opposite="resourceRepository__SchedulingPolicy" containment="true" ordered="false"
     * @generated
     */
    EList<SchedulingPolicy> getSchedulingPolicies__ResourceRepository();

    /**
     * Returns the value of the '<em><b>Available Resource Types Resource Repository</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceType}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceType#getResourceRepository_ResourceType
     * <em>Resource Repository Resource Type</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Available Resource Types Resource Repository</em>' containment
     * reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Available Resource Types Resource Repository</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceRepository_AvailableResourceTypes_ResourceRepository()
     * @see org.palladiosimulator.pcm.resourcetype.ResourceType#getResourceRepository_ResourceType
     * @model opposite="resourceRepository_ResourceType" containment="true" ordered="false"
     * @generated
     */
    EList<ResourceType> getAvailableResourceTypes_ResourceRepository();

} // ResourceRepository
