/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition;

import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Assembly Infrastructure Connector</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getProvidedRole__AssemblyInfrastructureConnector
 * <em>Provided Role Assembly Infrastructure Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getRequiredRole__AssemblyInfrastructureConnector
 * <em>Required Role Assembly Infrastructure Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getProvidingAssemblyContext__AssemblyInfrastructureConnector
 * <em>Providing Assembly Context Assembly Infrastructure Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getRequiringAssemblyContext__AssemblyInfrastructureConnector
 * <em>Requiring Assembly Context Assembly Infrastructure Connector</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyInfrastructureConnector()
 * @model
 * @generated
 */
public interface AssemblyInfrastructureConnector extends Connector {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Provided Role Assembly Infrastructure Connector</b></em>'
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Provided Role Assembly Infrastructure Connector</em>' reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Provided Role Assembly Infrastructure Connector</em>'
     *         reference.
     * @see #setProvidedRole__AssemblyInfrastructureConnector(InfrastructureProvidedRole)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyInfrastructureConnector_ProvidedRole__AssemblyInfrastructureConnector()
     * @model required="true" ordered="false"
     * @generated
     */
    InfrastructureProvidedRole getProvidedRole__AssemblyInfrastructureConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getProvidedRole__AssemblyInfrastructureConnector
     * <em>Provided Role Assembly Infrastructure Connector</em>}' reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Provided Role Assembly Infrastructure Connector</em>'
     *            reference.
     * @see #getProvidedRole__AssemblyInfrastructureConnector()
     * @generated
     */
    void setProvidedRole__AssemblyInfrastructureConnector(InfrastructureProvidedRole value);

    /**
     * Returns the value of the '<em><b>Required Role Assembly Infrastructure Connector</b></em>'
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Required Role Assembly Infrastructure Connector</em>' reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Required Role Assembly Infrastructure Connector</em>'
     *         reference.
     * @see #setRequiredRole__AssemblyInfrastructureConnector(InfrastructureRequiredRole)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyInfrastructureConnector_RequiredRole__AssemblyInfrastructureConnector()
     * @model required="true" ordered="false"
     * @generated
     */
    InfrastructureRequiredRole getRequiredRole__AssemblyInfrastructureConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getRequiredRole__AssemblyInfrastructureConnector
     * <em>Required Role Assembly Infrastructure Connector</em>}' reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Required Role Assembly Infrastructure Connector</em>'
     *            reference.
     * @see #getRequiredRole__AssemblyInfrastructureConnector()
     * @generated
     */
    void setRequiredRole__AssemblyInfrastructureConnector(InfrastructureRequiredRole value);

    /**
     * Returns the value of the '
     * <em><b>Providing Assembly Context Assembly Infrastructure Connector</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Providing Assembly Context Assembly Infrastructure Connector</em>'
     * reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '
     *         <em>Providing Assembly Context Assembly Infrastructure Connector</em>' reference.
     * @see #setProvidingAssemblyContext__AssemblyInfrastructureConnector(AssemblyContext)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyInfrastructureConnector_ProvidingAssemblyContext__AssemblyInfrastructureConnector()
     * @model required="true"
     * @generated
     */
    AssemblyContext getProvidingAssemblyContext__AssemblyInfrastructureConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getProvidingAssemblyContext__AssemblyInfrastructureConnector
     * <em>Providing Assembly Context Assembly Infrastructure Connector</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Providing Assembly Context Assembly Infrastructure Connector</em>' reference.
     * @see #getProvidingAssemblyContext__AssemblyInfrastructureConnector()
     * @generated
     */
    void setProvidingAssemblyContext__AssemblyInfrastructureConnector(AssemblyContext value);

    /**
     * Returns the value of the '
     * <em><b>Requiring Assembly Context Assembly Infrastructure Connector</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Requiring Assembly Context Assembly Infrastructure Connector</em>'
     * reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '
     *         <em>Requiring Assembly Context Assembly Infrastructure Connector</em>' reference.
     * @see #setRequiringAssemblyContext__AssemblyInfrastructureConnector(AssemblyContext)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyInfrastructureConnector_RequiringAssemblyContext__AssemblyInfrastructureConnector()
     * @model
     * @generated
     */
    AssemblyContext getRequiringAssemblyContext__AssemblyInfrastructureConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getRequiringAssemblyContext__AssemblyInfrastructureConnector
     * <em>Requiring Assembly Context Assembly Infrastructure Connector</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Requiring Assembly Context Assembly Infrastructure Connector</em>' reference.
     * @see #getRequiringAssemblyContext__AssemblyInfrastructureConnector()
     * @generated
     */
    void setRequiringAssemblyContext__AssemblyInfrastructureConnector(AssemblyContext value);

} // AssemblyInfrastructureConnector
