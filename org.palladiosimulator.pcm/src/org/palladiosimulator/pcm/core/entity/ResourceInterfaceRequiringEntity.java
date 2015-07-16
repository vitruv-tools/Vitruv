/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.entity;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Resource Interface Requiring Entity</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity#getResourceRequiredRoles__ResourceInterfaceRequiringEntity
 * <em>Resource Required Roles Resource Interface Requiring Entity</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.entity.EntityPackage#getResourceInterfaceRequiringEntity()
 * @model
 * @generated
 */
public interface ResourceInterfaceRequiringEntity extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '
     * <em><b>Resource Required Roles Resource Interface Requiring Entity</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole}. It is bidirectional and
     * its opposite is '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole#getResourceInterfaceRequiringEntity__ResourceRequiredRole
     * <em>Resource Interface Requiring Entity Resource Required Role</em>}'. <!-- begin-user-doc
     * -->
     * <p>
     * If the meaning of the '<em>Resource Required Roles Resource Interface Requiring Entity</em>'
     * containment reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '
     *         <em>Resource Required Roles Resource Interface Requiring Entity</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.core.entity.EntityPackage#getResourceInterfaceRequiringEntity_ResourceRequiredRoles__ResourceInterfaceRequiringEntity()
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole#getResourceInterfaceRequiringEntity__ResourceRequiredRole
     * @model opposite="resourceInterfaceRequiringEntity__ResourceRequiredRole" containment="true"
     *        ordered="false"
     * @generated
     */
    EList<ResourceRequiredRole> getResourceRequiredRoles__ResourceInterfaceRequiringEntity();

} // ResourceInterfaceRequiringEntity
