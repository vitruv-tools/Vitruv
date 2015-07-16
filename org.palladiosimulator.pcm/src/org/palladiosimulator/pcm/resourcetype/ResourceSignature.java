/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourcetype;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.repository.Parameter;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Resource Signature</b></em>
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
 * {@link org.palladiosimulator.pcm.resourcetype.ResourceSignature#getParameter__ResourceSignature
 * <em>Parameter Resource Signature</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.resourcetype.ResourceSignature#getResourceServiceId
 * <em>Resource Service Id</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.ResourceSignature#getResourceInterface__ResourceSignature
 * <em>Resource Interface Resource Signature</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceSignature()
 * @model
 * @generated
 */
public interface ResourceSignature extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Parameter Resource Signature</b></em>' containment reference
     * list. The list contents are of type {@link org.palladiosimulator.pcm.repository.Parameter}.
     * It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.Parameter#getResourceSignature__Parameter
     * <em>Resource Signature Parameter</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter Resource Signature</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Parameter Resource Signature</em>' containment reference list.
     * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceSignature_Parameter__ResourceSignature()
     * @see org.palladiosimulator.pcm.repository.Parameter#getResourceSignature__Parameter
     * @model opposite="resourceSignature__Parameter" containment="true" ordered="false"
     * @generated
     */
    EList<Parameter> getParameter__ResourceSignature();

    /**
     * Returns the value of the '<em><b>Resource Service Id</b></em>' attribute. <!-- begin-user-doc
     * -->
     * <p>
     * If the meaning of the '<em>Resource Service Id</em>' attribute isn't clear, there really
     * should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Resource Service Id</em>' attribute.
     * @see #setResourceServiceId(int)
     * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceSignature_ResourceServiceId()
     * @model required="true" ordered="false"
     * @generated
     */
    int getResourceServiceId();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceSignature#getResourceServiceId
     * <em>Resource Service Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Resource Service Id</em>' attribute.
     * @see #getResourceServiceId()
     * @generated
     */
    void setResourceServiceId(int value);

    /**
     * Returns the value of the '<em><b>Resource Interface Resource Signature</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface#getResourceSignatures__ResourceInterface
     * <em>Resource Signatures Resource Interface</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Interface Resource Signature</em>' container reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Resource Interface Resource Signature</em>' container
     *         reference.
     * @see #setResourceInterface__ResourceSignature(ResourceInterface)
     * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#getResourceSignature_ResourceInterface__ResourceSignature()
     * @see org.palladiosimulator.pcm.resourcetype.ResourceInterface#getResourceSignatures__ResourceInterface
     * @model opposite="resourceSignatures__ResourceInterface" required="true" transient="false"
     *        ordered="false"
     * @generated
     */
    ResourceInterface getResourceInterface__ResourceSignature();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceSignature#getResourceInterface__ResourceSignature
     * <em>Resource Interface Resource Signature</em>}' container reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Resource Interface Resource Signature</em>' container
     *            reference.
     * @see #getResourceInterface__ResourceSignature()
     * @generated
     */
    void setResourceInterface__ResourceSignature(ResourceInterface value);

} // ResourceSignature
