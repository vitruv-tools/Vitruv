/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourceenvironment;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Resource Container</b></em>
 * '. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * UML-like&nbsp;container&nbsp;of&nbsp;a&nbsp;number&nbsp;of&nbsp;processing&nbsp;resources (e.g.
 * hardware server) <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer#getActiveResourceSpecifications_ResourceContainer
 * <em>Active Resource Specifications Resource Container</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer#getResourceEnvironment_ResourceContainer
 * <em>Resource Environment Resource Container</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer#getNestedResourceContainers__ResourceContainer
 * <em>Nested Resource Containers Resource Container</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer#getParentResourceContainer__ResourceContainer
 * <em>Parent Resource Container Resource Container</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage#getResourceContainer()
 * @model
 * @generated
 */
public interface ResourceContainer extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Active Resource Specifications Resource Container</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification}. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification#getResourceContainer_ProcessingResourceSpecification
     * <em>Resource Container Processing Resource Specification</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Active Resource Specifications Resource Container</em>'
     * containment reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Active Resource Specifications Resource Container</em>'
     *         containment reference list.
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage#getResourceContainer_ActiveResourceSpecifications_ResourceContainer()
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification#getResourceContainer_ProcessingResourceSpecification
     * @model opposite="resourceContainer_ProcessingResourceSpecification" containment="true"
     *        ordered="false"
     * @generated
     */
    EList<ProcessingResourceSpecification> getActiveResourceSpecifications_ResourceContainer();

    /**
     * Returns the value of the '<em><b>Resource Environment Resource Container</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment#getResourceContainer_ResourceEnvironment
     * <em>Resource Container Resource Environment</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Environment Resource Container</em>' container reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Resource Environment Resource Container</em>' container
     *         reference.
     * @see #setResourceEnvironment_ResourceContainer(ResourceEnvironment)
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage#getResourceContainer_ResourceEnvironment_ResourceContainer()
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment#getResourceContainer_ResourceEnvironment
     * @model opposite="resourceContainer_ResourceEnvironment" transient="false" ordered="false"
     * @generated
     */
    ResourceEnvironment getResourceEnvironment_ResourceContainer();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer#getResourceEnvironment_ResourceContainer
     * <em>Resource Environment Resource Container</em>}' container reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Resource Environment Resource Container</em>' container
     *            reference.
     * @see #getResourceEnvironment_ResourceContainer()
     * @generated
     */
    void setResourceEnvironment_ResourceContainer(ResourceEnvironment value);

    /**
     * Returns the value of the '<em><b>Nested Resource Containers Resource Container</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer}. It is bidirectional
     * and its opposite is '
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer#getParentResourceContainer__ResourceContainer
     * <em>Parent Resource Container Resource Container</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Nested Resource Containers Resource Container</em>' containment
     * reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Nested Resource Containers Resource Container</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage#getResourceContainer_NestedResourceContainers__ResourceContainer()
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceContainer#getParentResourceContainer__ResourceContainer
     * @model opposite="parentResourceContainer__ResourceContainer" containment="true"
     *        ordered="false"
     * @generated
     */
    EList<ResourceContainer> getNestedResourceContainers__ResourceContainer();

    /**
     * Returns the value of the '<em><b>Parent Resource Container Resource Container</b></em>'
     * container reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer#getNestedResourceContainers__ResourceContainer
     * <em>Nested Resource Containers Resource Container</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parent Resource Container Resource Container</em>' container
     * reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Parent Resource Container Resource Container</em>' container
     *         reference.
     * @see #setParentResourceContainer__ResourceContainer(ResourceContainer)
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage#getResourceContainer_ParentResourceContainer__ResourceContainer()
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceContainer#getNestedResourceContainers__ResourceContainer
     * @model opposite="nestedResourceContainers__ResourceContainer" transient="false"
     *        ordered="false"
     * @generated
     */
    ResourceContainer getParentResourceContainer__ResourceContainer();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer#getParentResourceContainer__ResourceContainer
     * <em>Parent Resource Container Resource Container</em>}' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Parent Resource Container Resource Container</em>'
     *            container reference.
     * @see #getParentResourceContainer__ResourceContainer()
     * @generated
     */
    void setParentResourceContainer__ResourceContainer(ResourceContainer value);

} // ResourceContainer
