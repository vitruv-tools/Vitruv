/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.entity;

import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Resource Provided Role</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.entity.ResourceProvidedRole#getResourceInterfaceProvidingEntity__ResourceProvidedRole
 * <em>Resource Interface Providing Entity Resource Provided Role</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.entity.ResourceProvidedRole#getProvidedResourceInterface__ResourceProvidedRole
 * <em>Provided Resource Interface Resource Provided Role</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.entity.EntityPackage#getResourceProvidedRole()
 * @model
 * @generated
 */
public interface ResourceProvidedRole extends Role {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '
     * <em><b>Resource Interface Providing Entity Resource Provided Role</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity#getResourceProvidedRoles__ResourceInterfaceProvidingEntity
     * <em>Resource Provided Roles Resource Interface Providing Entity</em>}'. <!-- begin-user-doc
     * -->
     * <p>
     * If the meaning of the '<em>Resource Interface Providing Entity Resource Provided Role</em>'
     * container reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Resource Interface Providing Entity Resource Provided Role</em>
     *         ' container reference.
     * @see #setResourceInterfaceProvidingEntity__ResourceProvidedRole(ResourceInterfaceProvidingEntity)
     * @see org.palladiosimulator.pcm.core.entity.EntityPackage#getResourceProvidedRole_ResourceInterfaceProvidingEntity__ResourceProvidedRole()
     * @see org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity#getResourceProvidedRoles__ResourceInterfaceProvidingEntity
     * @model opposite="resourceProvidedRoles__ResourceInterfaceProvidingEntity" required="true"
     *        transient="false" ordered="false"
     * @generated
     */
    ResourceInterfaceProvidingEntity getResourceInterfaceProvidingEntity__ResourceProvidedRole();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceProvidedRole#getResourceInterfaceProvidingEntity__ResourceProvidedRole
     * <em>Resource Interface Providing Entity Resource Provided Role</em>}' container reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Resource Interface Providing Entity Resource Provided Role</em>' container
     *            reference.
     * @see #getResourceInterfaceProvidingEntity__ResourceProvidedRole()
     * @generated
     */
    void setResourceInterfaceProvidingEntity__ResourceProvidedRole(ResourceInterfaceProvidingEntity value);

    /**
     * Returns the value of the '<em><b>Provided Resource Interface Resource Provided Role</b></em>'
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Provided Resource Interface Resource Provided Role</em>' reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Provided Resource Interface Resource Provided Role</em>'
     *         reference.
     * @see #setProvidedResourceInterface__ResourceProvidedRole(ResourceInterface)
     * @see org.palladiosimulator.pcm.core.entity.EntityPackage#getResourceProvidedRole_ProvidedResourceInterface__ResourceProvidedRole()
     * @model required="true" ordered="false"
     * @generated
     */
    ResourceInterface getProvidedResourceInterface__ResourceProvidedRole();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceProvidedRole#getProvidedResourceInterface__ResourceProvidedRole
     * <em>Provided Resource Interface Resource Provided Role</em>}' reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Provided Resource Interface Resource Provided Role</em>'
     *            reference.
     * @see #getProvidedResourceInterface__ResourceProvidedRole()
     * @generated
     */
    void setProvidedResourceInterface__ResourceProvidedRole(ResourceInterface value);

} // ResourceProvidedRole
