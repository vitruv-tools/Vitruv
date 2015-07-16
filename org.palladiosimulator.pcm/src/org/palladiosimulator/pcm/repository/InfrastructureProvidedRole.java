/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Infrastructure Provided Role</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole#getProvidedInterface__InfrastructureProvidedRole
 * <em>Provided Interface Infrastructure Provided Role</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getInfrastructureProvidedRole()
 * @model
 * @generated
 */
public interface InfrastructureProvidedRole extends ProvidedRole {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Provided Interface Infrastructure Provided Role</b></em>'
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Provided Interface Infrastructure Provided Role</em>' reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Provided Interface Infrastructure Provided Role</em>'
     *         reference.
     * @see #setProvidedInterface__InfrastructureProvidedRole(InfrastructureInterface)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getInfrastructureProvidedRole_ProvidedInterface__InfrastructureProvidedRole()
     * @model required="true" ordered="false"
     * @generated
     */
    InfrastructureInterface getProvidedInterface__InfrastructureProvidedRole();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole#getProvidedInterface__InfrastructureProvidedRole
     * <em>Provided Interface Infrastructure Provided Role</em>}' reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Provided Interface Infrastructure Provided Role</em>'
     *            reference.
     * @see #getProvidedInterface__InfrastructureProvidedRole()
     * @generated
     */
    void setProvidedInterface__InfrastructureProvidedRole(InfrastructureInterface value);

} // InfrastructureProvidedRole
