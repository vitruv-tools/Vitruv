/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition;

import org.palladiosimulator.pcm.repository.SourceRole;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Event Channel Source Connector</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getSourceRole__EventChannelSourceRole
 * <em>Source Role Event Channel Source Role</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getAssemblyContext__EventChannelSourceConnector
 * <em>Assembly Context Event Channel Source Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getEventChannel__EventChannelSourceConnector
 * <em>Event Channel Event Channel Source Connector</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getEventChannelSourceConnector()
 * @model
 * @generated
 */
public interface EventChannelSourceConnector extends Connector {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Source Role Event Channel Source Role</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Role Event Channel Source Role</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Source Role Event Channel Source Role</em>' reference.
     * @see #setSourceRole__EventChannelSourceRole(SourceRole)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getEventChannelSourceConnector_SourceRole__EventChannelSourceRole()
     * @model required="true" ordered="false"
     * @generated
     */
    SourceRole getSourceRole__EventChannelSourceRole();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getSourceRole__EventChannelSourceRole
     * <em>Source Role Event Channel Source Role</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Source Role Event Channel Source Role</em>' reference.
     * @see #getSourceRole__EventChannelSourceRole()
     * @generated
     */
    void setSourceRole__EventChannelSourceRole(SourceRole value);

    /**
     * Returns the value of the '<em><b>Assembly Context Event Channel Source Connector</b></em>'
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Assembly Context Event Channel Source Connector</em>' reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Assembly Context Event Channel Source Connector</em>'
     *         reference.
     * @see #setAssemblyContext__EventChannelSourceConnector(AssemblyContext)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getEventChannelSourceConnector_AssemblyContext__EventChannelSourceConnector()
     * @model required="true" ordered="false"
     * @generated
     */
    AssemblyContext getAssemblyContext__EventChannelSourceConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getAssemblyContext__EventChannelSourceConnector
     * <em>Assembly Context Event Channel Source Connector</em>}' reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Assembly Context Event Channel Source Connector</em>'
     *            reference.
     * @see #getAssemblyContext__EventChannelSourceConnector()
     * @generated
     */
    void setAssemblyContext__EventChannelSourceConnector(AssemblyContext value);

    /**
     * Returns the value of the '<em><b>Event Channel Event Channel Source Connector</b></em>'
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannel#getEventChannelSourceConnector__EventChannel
     * <em>Event Channel Source Connector Event Channel</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event Channel Event Channel Source Connector</em>' reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Event Channel Event Channel Source Connector</em>' reference.
     * @see #setEventChannel__EventChannelSourceConnector(EventChannel)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getEventChannelSourceConnector_EventChannel__EventChannelSourceConnector()
     * @see org.palladiosimulator.pcm.core.composition.EventChannel#getEventChannelSourceConnector__EventChannel
     * @model opposite="eventChannelSourceConnector__EventChannel" required="true" ordered="false"
     * @generated
     */
    EventChannel getEventChannel__EventChannelSourceConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getEventChannel__EventChannelSourceConnector
     * <em>Event Channel Event Channel Source Connector</em>}' reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Event Channel Event Channel Source Connector</em>'
     *            reference.
     * @see #getEventChannel__EventChannelSourceConnector()
     * @generated
     */
    void setEventChannel__EventChannelSourceConnector(EventChannel value);

} // EventChannelSourceConnector
