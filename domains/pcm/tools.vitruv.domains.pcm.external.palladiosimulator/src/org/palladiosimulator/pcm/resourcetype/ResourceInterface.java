/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourcetype;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Resource Interface</b></em>
 * '. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> TODO: This structure still has to be finalized by Henning. There is no
 * influence on other model elements yet. so this can be done later on. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface#getResourceRepository__ResourceInterface
 * <em>Resource Repository Resource Interface</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface#getResourceSignatures__ResourceInterface
 * <em>Resource Signatures Resource Interface</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceInterface()
 * @model
 * @generated
 */
public interface ResourceInterface extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Resource Repository Resource Interface</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceRepository#getResourceInterfaces__ResourceRepository
     * <em>Resource Interfaces Resource Repository</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Repository Resource Interface</em>' container reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Resource Repository Resource Interface</em>' container
     *         reference.
     * @see #setResourceRepository__ResourceInterface(ResourceRepository)
     * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceInterface_ResourceRepository__ResourceInterface()
     * @see org.palladiosimulator.pcm.resourcetype.ResourceRepository#getResourceInterfaces__ResourceRepository
     * @model opposite="resourceInterfaces__ResourceRepository" required="true" transient="false"
     *        ordered="false"
     * @generated
     */
    ResourceRepository getResourceRepository__ResourceInterface();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface#getResourceRepository__ResourceInterface
     * <em>Resource Repository Resource Interface</em>}' container reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Resource Repository Resource Interface</em>' container
     *            reference.
     * @see #getResourceRepository__ResourceInterface()
     * @generated
     */
    void setResourceRepository__ResourceInterface(ResourceRepository value);

    /**
     * Returns the value of the '<em><b>Resource Signatures Resource Interface</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceSignature}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceSignature#getResourceInterface__ResourceSignature
     * <em>Resource Interface Resource Signature</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Signatures Resource Interface</em>' containment reference
     * list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Resource Signatures Resource Interface</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceInterface_ResourceSignatures__ResourceInterface()
     * @see org.palladiosimulator.pcm.resourcetype.ResourceSignature#getResourceInterface__ResourceSignature
     * @model opposite="resourceInterface__ResourceSignature" containment="true" ordered="false"
     * @generated
     */
    EList<ResourceSignature> getResourceSignatures__ResourceInterface();

} // ResourceInterface
