/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.entity;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.repository.ProvidedRole;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Interface Providing Entity</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> All Entities that provide an Interface are represented by this class.
 * Prominent inheriting classes are all component types, for example.
 *
 * Two roles can be identified a software entity can take relative to an interface. Any entity can
 * claim to implement the functionality specified in an interface as well as to request that
 * functionality. This is reflected in the Palladio Component Model by a set of abstract
 * meta-classes giving a conceptual view on interfaces, entities and their relationships. The
 * abstract meta-class InterfaceProvidingEntity is inherited by all entities which can potentially
 * offer interface implementations. Similarly, the meta-class InterfaceRequiringEntity is inherited
 * by all entities which are allowed to request functionality offered by entities providing this
 * functionality.
 *
 * See also: Interface, ProvidedRole <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity#getProvidedRoles_InterfaceProvidingEntity
 * <em>Provided Roles Interface Providing Entity</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.entity.EntityPackage#getInterfaceProvidingEntity()
 * @model abstract="true"
 * @generated
 */
public interface InterfaceProvidingEntity extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Provided Roles Interface Providing Entity</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.repository.ProvidedRole}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.repository.ProvidedRole#getProvidingEntity_ProvidedRole
     * <em>Providing Entity Provided Role</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Provided Roles Interface Providing Entity</em>' containment
     * reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Provided Roles Interface Providing Entity</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.core.entity.EntityPackage#getInterfaceProvidingEntity_ProvidedRoles_InterfaceProvidingEntity()
     * @see org.palladiosimulator.pcm.repository.ProvidedRole#getProvidingEntity_ProvidedRole
     * @model opposite="providingEntity_ProvidedRole" containment="true" ordered="false"
     * @generated
     */
    EList<ProvidedRole> getProvidedRoles_InterfaceProvidingEntity();

} // InterfaceProvidingEntity
