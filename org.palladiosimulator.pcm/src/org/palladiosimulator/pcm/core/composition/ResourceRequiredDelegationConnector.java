/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition;

import org.eclipse.emf.cdo.CDOObject;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Resource Required Delegation Connector</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> TODO Michael Hauck <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getInnerResourceRequiredRole_ResourceRequiredDelegationConnector
 * <em>Inner Resource Required Role Resource Required Delegation Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getOuterResourceRequiredRole_ResourceRequiredDelegationConnector
 * <em>Outer Resource Required Role Resource Required Delegation Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getParentStructure_ResourceRequiredDelegationConnector
 * <em>Parent Structure Resource Required Delegation Connector</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getResourceRequiredDelegationConnector()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ResourceRequiredDelegationConnector extends CDOObject {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '
     * <em><b>Inner Resource Required Role Resource Required Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '
     * <em>Inner Resource Required Role Resource Required Delegation Connector</em>' reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '
     *         <em>Inner Resource Required Role Resource Required Delegation Connector</em>'
     *         reference.
     * @see #setInnerResourceRequiredRole_ResourceRequiredDelegationConnector(ResourceRequiredRole)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getResourceRequiredDelegationConnector_InnerResourceRequiredRole_ResourceRequiredDelegationConnector()
     * @model required="true" ordered="false"
     * @generated
     */
    ResourceRequiredRole getInnerResourceRequiredRole_ResourceRequiredDelegationConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getInnerResourceRequiredRole_ResourceRequiredDelegationConnector
     * <em>Inner Resource Required Role Resource Required Delegation Connector</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Inner Resource Required Role Resource Required Delegation Connector</em>'
     *            reference.
     * @see #getInnerResourceRequiredRole_ResourceRequiredDelegationConnector()
     * @generated
     */
    void setInnerResourceRequiredRole_ResourceRequiredDelegationConnector(ResourceRequiredRole value);

    /**
     * Returns the value of the '
     * <em><b>Outer Resource Required Role Resource Required Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '
     * <em>Outer Resource Required Role Resource Required Delegation Connector</em>' reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '
     *         <em>Outer Resource Required Role Resource Required Delegation Connector</em>'
     *         reference.
     * @see #setOuterResourceRequiredRole_ResourceRequiredDelegationConnector(ResourceRequiredRole)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getResourceRequiredDelegationConnector_OuterResourceRequiredRole_ResourceRequiredDelegationConnector()
     * @model required="true" ordered="false"
     * @generated
     */
    ResourceRequiredRole getOuterResourceRequiredRole_ResourceRequiredDelegationConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getOuterResourceRequiredRole_ResourceRequiredDelegationConnector
     * <em>Outer Resource Required Role Resource Required Delegation Connector</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Outer Resource Required Role Resource Required Delegation Connector</em>'
     *            reference.
     * @see #getOuterResourceRequiredRole_ResourceRequiredDelegationConnector()
     * @generated
     */
    void setOuterResourceRequiredRole_ResourceRequiredDelegationConnector(ResourceRequiredRole value);

    /**
     * Returns the value of the '
     * <em><b>Parent Structure Resource Required Delegation Connector</b></em>' container reference.
     * It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure#getResourceRequiredDelegationConnectors_ComposedStructure
     * <em>Resource Required Delegation Connectors Composed Structure</em>}'. <!-- begin-user-doc
     * -->
     * <p>
     * If the meaning of the '<em>Parent Structure Resource Required Delegation Connector</em>'
     * container reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Parent Structure Resource Required Delegation Connector</em>'
     *         container reference.
     * @see #setParentStructure_ResourceRequiredDelegationConnector(ComposedStructure)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getResourceRequiredDelegationConnector_ParentStructure_ResourceRequiredDelegationConnector()
     * @see org.palladiosimulator.pcm.core.composition.ComposedStructure#getResourceRequiredDelegationConnectors_ComposedStructure
     * @model opposite="resourceRequiredDelegationConnectors_ComposedStructure" required="true"
     *        transient="false" ordered="false"
     * @generated
     */
    ComposedStructure getParentStructure_ResourceRequiredDelegationConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getParentStructure_ResourceRequiredDelegationConnector
     * <em>Parent Structure Resource Required Delegation Connector</em>}' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Parent Structure Resource Required Delegation Connector</em>' container
     *            reference.
     * @see #getParentStructure_ResourceRequiredDelegationConnector()
     * @generated
     */
    void setParentStructure_ResourceRequiredDelegationConnector(ComposedStructure value);

} // ResourceRequiredDelegationConnector
