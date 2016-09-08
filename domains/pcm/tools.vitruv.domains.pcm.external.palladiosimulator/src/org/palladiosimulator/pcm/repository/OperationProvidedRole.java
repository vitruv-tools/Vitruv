/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Operation Provided Role</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> This entity represents the provided operation interfaces. The PCM uses
 * the association of an operation interface to a component to determine its role. <!--
 * end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.OperationProvidedRole#getProvidedInterface__OperationProvidedRole
 * <em>Provided Interface Operation Provided Role</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getOperationProvidedRole()
 * @model
 * @generated
 */
public interface OperationProvidedRole extends ProvidedRole {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Provided Interface Operation Provided Role</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> This
     * property represents the corresponding interface that is provided by this role. <!--
     * end-model-doc -->
     *
     * @return the value of the '<em>Provided Interface Operation Provided Role</em>' reference.
     * @see #setProvidedInterface__OperationProvidedRole(OperationInterface)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getOperationProvidedRole_ProvidedInterface__OperationProvidedRole()
     * @model required="true"
     * @generated
     */
    OperationInterface getProvidedInterface__OperationProvidedRole();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.OperationProvidedRole#getProvidedInterface__OperationProvidedRole
     * <em>Provided Interface Operation Provided Role</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Provided Interface Operation Provided Role</em>'
     *            reference.
     * @see #getProvidedInterface__OperationProvidedRole()
     * @generated
     */
    void setProvidedInterface__OperationProvidedRole(OperationInterface value);

} // OperationProvidedRole
