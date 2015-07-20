/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Operation Required Role</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> This entity represents the required operation interfaces. The PCM uses
 * the association of an operation interface to a component to determine its role. <!--
 * end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.OperationRequiredRole#getRequiredInterface__OperationRequiredRole
 * <em>Required Interface Operation Required Role</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getOperationRequiredRole()
 * @model
 * @generated
 */
public interface OperationRequiredRole extends RequiredRole {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Required Interface Operation Required Role</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> This
     * property represents the interfaces that is required by this role. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Required Interface Operation Required Role</em>' reference.
     * @see #setRequiredInterface__OperationRequiredRole(OperationInterface)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getOperationRequiredRole_RequiredInterface__OperationRequiredRole()
     * @model required="true"
     * @generated
     */
    OperationInterface getRequiredInterface__OperationRequiredRole();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.OperationRequiredRole#getRequiredInterface__OperationRequiredRole
     * <em>Required Interface Operation Required Role</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Required Interface Operation Required Role</em>'
     *            reference.
     * @see #getRequiredInterface__OperationRequiredRole()
     * @generated
     */
    void setRequiredInterface__OperationRequiredRole(OperationInterface value);

} // OperationRequiredRole
