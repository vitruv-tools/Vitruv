/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition;

import org.palladiosimulator.pcm.repository.SinkRole;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Sink Delegation Connector</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> A SinkDelegationConnector delegates an incoming event to the
 * encapsulated assembly contexts to inner sink roles. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector#getAssemblyContext__SinkDelegationConnector
 * <em>Assembly Context Sink Delegation Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector#getInnerSinkRole__SinkRole
 * <em>Inner Sink Role Sink Role</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector#getOuterSinkRole__SinkRole
 * <em>Outer Sink Role Sink Role</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getSinkDelegationConnector()
 * @model
 * @generated
 */
public interface SinkDelegationConnector extends DelegationConnector {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Assembly Context Sink Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Assembly Context Sink Delegation Connector</em>' reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Assembly Context Sink Delegation Connector</em>' reference.
     * @see #setAssemblyContext__SinkDelegationConnector(AssemblyContext)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getSinkDelegationConnector_AssemblyContext__SinkDelegationConnector()
     * @model required="true" ordered="false"
     * @generated
     */
    AssemblyContext getAssemblyContext__SinkDelegationConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector#getAssemblyContext__SinkDelegationConnector
     * <em>Assembly Context Sink Delegation Connector</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Assembly Context Sink Delegation Connector</em>'
     *            reference.
     * @see #getAssemblyContext__SinkDelegationConnector()
     * @generated
     */
    void setAssemblyContext__SinkDelegationConnector(AssemblyContext value);

    /**
     * Returns the value of the '<em><b>Inner Sink Role Sink Role</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Inner Sink Role Sink Role</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Inner Sink Role Sink Role</em>' reference.
     * @see #setInnerSinkRole__SinkRole(SinkRole)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getSinkDelegationConnector_InnerSinkRole__SinkRole()
     * @model required="true"
     * @generated
     */
    SinkRole getInnerSinkRole__SinkRole();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector#getInnerSinkRole__SinkRole
     * <em>Inner Sink Role Sink Role</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Inner Sink Role Sink Role</em>' reference.
     * @see #getInnerSinkRole__SinkRole()
     * @generated
     */
    void setInnerSinkRole__SinkRole(SinkRole value);

    /**
     * Returns the value of the '<em><b>Outer Sink Role Sink Role</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Outer Sink Role Sink Role</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Outer Sink Role Sink Role</em>' reference.
     * @see #setOuterSinkRole__SinkRole(SinkRole)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getSinkDelegationConnector_OuterSinkRole__SinkRole()
     * @model required="true"
     * @generated
     */
    SinkRole getOuterSinkRole__SinkRole();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector#getOuterSinkRole__SinkRole
     * <em>Outer Sink Role Sink Role</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Outer Sink Role Sink Role</em>' reference.
     * @see #getOuterSinkRole__SinkRole()
     * @generated
     */
    void setOuterSinkRole__SinkRole(SinkRole value);

} // SinkDelegationConnector
