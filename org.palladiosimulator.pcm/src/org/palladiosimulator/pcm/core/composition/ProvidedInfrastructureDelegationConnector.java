/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition;

import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Provided Infrastructure Delegation Connector</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector#getInnerProvidedRole__ProvidedInfrastructureDelegationConnector
 * <em>Inner Provided Role Provided Infrastructure Delegation Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector#getOuterProvidedRole__ProvidedInfrastructureDelegationConnector
 * <em>Outer Provided Role Provided Infrastructure Delegation Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector#getAssemblyContext__ProvidedInfrastructureDelegationConnector
 * <em>Assembly Context Provided Infrastructure Delegation Connector</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getProvidedInfrastructureDelegationConnector()
 * @model
 * @generated
 */
public interface ProvidedInfrastructureDelegationConnector extends DelegationConnector {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '
     * <em><b>Inner Provided Role Provided Infrastructure Delegation Connector</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '
     * <em>Inner Provided Role Provided Infrastructure Delegation Connector</em>' reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '
     *         <em>Inner Provided Role Provided Infrastructure Delegation Connector</em>' reference.
     * @see #setInnerProvidedRole__ProvidedInfrastructureDelegationConnector(InfrastructureProvidedRole)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getProvidedInfrastructureDelegationConnector_InnerProvidedRole__ProvidedInfrastructureDelegationConnector()
     * @model required="true" ordered="false"
     * @generated
     */
    InfrastructureProvidedRole getInnerProvidedRole__ProvidedInfrastructureDelegationConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector#getInnerProvidedRole__ProvidedInfrastructureDelegationConnector
     * <em>Inner Provided Role Provided Infrastructure Delegation Connector</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Inner Provided Role Provided Infrastructure Delegation Connector</em>'
     *            reference.
     * @see #getInnerProvidedRole__ProvidedInfrastructureDelegationConnector()
     * @generated
     */
    void setInnerProvidedRole__ProvidedInfrastructureDelegationConnector(InfrastructureProvidedRole value);

    /**
     * Returns the value of the '
     * <em><b>Outer Provided Role Provided Infrastructure Delegation Connector</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '
     * <em>Outer Provided Role Provided Infrastructure Delegation Connector</em>' reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '
     *         <em>Outer Provided Role Provided Infrastructure Delegation Connector</em>' reference.
     * @see #setOuterProvidedRole__ProvidedInfrastructureDelegationConnector(InfrastructureProvidedRole)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getProvidedInfrastructureDelegationConnector_OuterProvidedRole__ProvidedInfrastructureDelegationConnector()
     * @model required="true" ordered="false"
     * @generated
     */
    InfrastructureProvidedRole getOuterProvidedRole__ProvidedInfrastructureDelegationConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector#getOuterProvidedRole__ProvidedInfrastructureDelegationConnector
     * <em>Outer Provided Role Provided Infrastructure Delegation Connector</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Outer Provided Role Provided Infrastructure Delegation Connector</em>'
     *            reference.
     * @see #getOuterProvidedRole__ProvidedInfrastructureDelegationConnector()
     * @generated
     */
    void setOuterProvidedRole__ProvidedInfrastructureDelegationConnector(InfrastructureProvidedRole value);

    /**
     * Returns the value of the '
     * <em><b>Assembly Context Provided Infrastructure Delegation Connector</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Assembly Context Provided Infrastructure Delegation Connector</em>
     * ' reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '
     *         <em>Assembly Context Provided Infrastructure Delegation Connector</em>' reference.
     * @see #setAssemblyContext__ProvidedInfrastructureDelegationConnector(AssemblyContext)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getProvidedInfrastructureDelegationConnector_AssemblyContext__ProvidedInfrastructureDelegationConnector()
     * @model required="true" ordered="false"
     * @generated
     */
    AssemblyContext getAssemblyContext__ProvidedInfrastructureDelegationConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector#getAssemblyContext__ProvidedInfrastructureDelegationConnector
     * <em>Assembly Context Provided Infrastructure Delegation Connector</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Assembly Context Provided Infrastructure Delegation Connector</em>' reference.
     * @see #getAssemblyContext__ProvidedInfrastructureDelegationConnector()
     * @generated
     */
    void setAssemblyContext__ProvidedInfrastructureDelegationConnector(AssemblyContext value);

} // ProvidedInfrastructureDelegationConnector
