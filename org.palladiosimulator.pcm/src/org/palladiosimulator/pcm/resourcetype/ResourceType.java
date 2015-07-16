/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourcetype;

import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity;

import de.uka.ipd.sdq.units.UnitCarryingElement;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Resource Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Abstract superclass of any resource <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.resourcetype.ResourceType#getResourceRepository_ResourceType
 * <em>Resource Repository Resource Type</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceType()
 * @model abstract="true"
 * @generated
 */
public interface ResourceType extends Entity, UnitCarryingElement, ResourceInterfaceProvidingEntity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Resource Repository Resource Type</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceRepository#getAvailableResourceTypes_ResourceRepository
     * <em>Available Resource Types Resource Repository</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Repository Resource Type</em>' container reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Resource Repository Resource Type</em>' container reference.
     * @see #setResourceRepository_ResourceType(ResourceRepository)
     * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceType_ResourceRepository_ResourceType()
     * @see org.palladiosimulator.pcm.resourcetype.ResourceRepository#getAvailableResourceTypes_ResourceRepository
     * @model opposite="availableResourceTypes_ResourceRepository" required="true" transient="false"
     *        ordered="false"
     * @generated
     */
    ResourceRepository getResourceRepository_ResourceType();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceType#getResourceRepository_ResourceType
     * <em>Resource Repository Resource Type</em>}' container reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Resource Repository Resource Type</em>' container
     *            reference.
     * @see #getResourceRepository_ResourceType()
     * @generated
     */
    void setResourceRepository_ResourceType(ResourceRepository value);

} // ResourceType
