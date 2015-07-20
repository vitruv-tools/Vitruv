/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourcetype;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Scheduling Policy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.SchedulingPolicy#getResourceRepository__SchedulingPolicy
 * <em>Resource Repository Scheduling Policy</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getSchedulingPolicy()
 * @model
 * @generated
 */
public interface SchedulingPolicy extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Resource Repository Scheduling Policy</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceRepository#getSchedulingPolicies__ResourceRepository
     * <em>Scheduling Policies Resource Repository</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Repository Scheduling Policy</em>' container reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Resource Repository Scheduling Policy</em>' container
     *         reference.
     * @see #setResourceRepository__SchedulingPolicy(ResourceRepository)
     * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getSchedulingPolicy_ResourceRepository__SchedulingPolicy()
     * @see org.palladiosimulator.pcm.resourcetype.ResourceRepository#getSchedulingPolicies__ResourceRepository
     * @model opposite="schedulingPolicies__ResourceRepository" required="true" transient="false"
     *        ordered="false"
     * @generated
     */
    ResourceRepository getResourceRepository__SchedulingPolicy();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.resourcetype.SchedulingPolicy#getResourceRepository__SchedulingPolicy
     * <em>Resource Repository Scheduling Policy</em>}' container reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Resource Repository Scheduling Policy</em>' container
     *            reference.
     * @see #getResourceRepository__SchedulingPolicy()
     * @generated
     */
    void setResourceRepository__SchedulingPolicy(ResourceRepository value);

} // SchedulingPolicy
