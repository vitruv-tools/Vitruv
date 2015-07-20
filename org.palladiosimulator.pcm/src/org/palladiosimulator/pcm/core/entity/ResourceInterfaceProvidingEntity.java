/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.entity;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Resource Interface Providing Entity</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity#getResourceProvidedRoles__ResourceInterfaceProvidingEntity
 * <em>Resource Provided Roles Resource Interface Providing Entity</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.entity.EntityPackage#getResourceInterfaceProvidingEntity()
 * @model
 * @generated
 */
public interface ResourceInterfaceProvidingEntity extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '
     * <em><b>Resource Provided Roles Resource Interface Providing Entity</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.core.entity.ResourceProvidedRole}. It is bidirectional and
     * its opposite is '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceProvidedRole#getResourceInterfaceProvidingEntity__ResourceProvidedRole
     * <em>Resource Interface Providing Entity Resource Provided Role</em>}'. <!-- begin-user-doc
     * -->
     * <p>
     * If the meaning of the '<em>Resource Provided Roles Resource Interface Providing Entity</em>'
     * containment reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '
     *         <em>Resource Provided Roles Resource Interface Providing Entity</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.core.entity.EntityPackage#getResourceInterfaceProvidingEntity_ResourceProvidedRoles__ResourceInterfaceProvidingEntity()
     * @see org.palladiosimulator.pcm.core.entity.ResourceProvidedRole#getResourceInterfaceProvidingEntity__ResourceProvidedRole
     * @model opposite="resourceInterfaceProvidingEntity__ResourceProvidedRole" containment="true"
     *        ordered="false"
     * @generated
     */
    EList<ResourceProvidedRole> getResourceProvidedRoles__ResourceInterfaceProvidingEntity();

} // ResourceInterfaceProvidingEntity
