/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourceenvironment;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.NamedElement;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Resource Environment</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * <p>
 * Repository element of&nbsp;the resource environment
 * </p>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment#getLinkingResources__ResourceEnvironment
 * <em>Linking Resources Resource Environment</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment#getResourceContainer_ResourceEnvironment
 * <em>Resource Container Resource Environment</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage#getResourceEnvironment()
 * @model
 * @generated
 */
public interface ResourceEnvironment extends NamedElement {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Linking Resources Resource Environment</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.resourceenvironment.LinkingResource}. It is bidirectional
     * and its opposite is '
     * {@link org.palladiosimulator.pcm.resourceenvironment.LinkingResource#getResourceEnvironment_LinkingResource
     * <em>Resource Environment Linking Resource</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Linking Resources Resource Environment</em>' containment reference
     * list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Linking Resources Resource Environment</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage#getResourceEnvironment_LinkingResources__ResourceEnvironment()
     * @see org.palladiosimulator.pcm.resourceenvironment.LinkingResource#getResourceEnvironment_LinkingResource
     * @model opposite="resourceEnvironment_LinkingResource" containment="true" ordered="false"
     * @generated
     */
    EList<LinkingResource> getLinkingResources__ResourceEnvironment();

    /**
     * Returns the value of the '<em><b>Resource Container Resource Environment</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer}. It is bidirectional
     * and its opposite is '
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer#getResourceEnvironment_ResourceContainer
     * <em>Resource Environment Resource Container</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Container Resource Environment</em>' containment
     * reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Resource Container Resource Environment</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage#getResourceEnvironment_ResourceContainer_ResourceEnvironment()
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceContainer#getResourceEnvironment_ResourceContainer
     * @model opposite="resourceEnvironment_ResourceContainer" containment="true" ordered="false"
     * @generated
     */
    EList<ResourceContainer> getResourceContainer_ResourceEnvironment();

} // ResourceEnvironment
