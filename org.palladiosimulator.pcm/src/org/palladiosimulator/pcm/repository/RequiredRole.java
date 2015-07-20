/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Required Role</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Required roles list the interfaces required by a component. <!--
 * end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.RequiredRole#getRequiringEntity_RequiredRole
 * <em>Requiring Entity Required Role</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getRequiredRole()
 * @model abstract="true"
 * @generated
 */
public interface RequiredRole extends Role {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Requiring Entity Required Role</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity#getRequiredRoles_InterfaceRequiringEntity
     * <em>Required Roles Interface Requiring Entity</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc --> This property represents the interface requiring
     * entity that requires this interface. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Requiring Entity Required Role</em>' container reference.
     * @see #setRequiringEntity_RequiredRole(InterfaceRequiringEntity)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getRequiredRole_RequiringEntity_RequiredRole()
     * @see org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity#getRequiredRoles_InterfaceRequiringEntity
     * @model opposite="requiredRoles_InterfaceRequiringEntity" required="true" transient="false"
     * @generated
     */
    InterfaceRequiringEntity getRequiringEntity_RequiredRole();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.RequiredRole#getRequiringEntity_RequiredRole
     * <em>Requiring Entity Required Role</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Requiring Entity Required Role</em>' container
     *            reference.
     * @see #getRequiringEntity_RequiredRole()
     * @generated
     */
    void setRequiringEntity_RequiredRole(InterfaceRequiringEntity value);

} // RequiredRole
