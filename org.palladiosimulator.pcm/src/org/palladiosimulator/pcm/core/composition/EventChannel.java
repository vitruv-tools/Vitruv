/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.repository.EventGroup;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Event Channel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.core.composition.EventChannel#getEventGroup__EventChannel
 * <em>Event Group Event Channel</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.EventChannel#getEventChannelSourceConnector__EventChannel
 * <em>Event Channel Source Connector Event Channel</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.EventChannel#getEventChannelSinkConnector__EventChannel
 * <em>Event Channel Sink Connector Event Channel</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.EventChannel#getParentStructure__EventChannel
 * <em>Parent Structure Event Channel</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getEventChannel()
 * @model
 * @generated
 */
public interface EventChannel extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Event Group Event Channel</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event Group Event Channel</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Event Group Event Channel</em>' reference.
     * @see #setEventGroup__EventChannel(EventGroup)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getEventChannel_EventGroup__EventChannel()
     * @model required="true"
     * @generated
     */
    EventGroup getEventGroup__EventChannel();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannel#getEventGroup__EventChannel
     * <em>Event Group Event Channel</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Event Group Event Channel</em>' reference.
     * @see #getEventGroup__EventChannel()
     * @generated
     */
    void setEventGroup__EventChannel(EventGroup value);

    /**
     * Returns the value of the '<em><b>Event Channel Source Connector Event Channel</b></em>'
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector}. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getEventChannel__EventChannelSourceConnector
     * <em>Event Channel Event Channel Source Connector</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event Channel Source Connector Event Channel</em>' reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Event Channel Source Connector Event Channel</em>' reference
     *         list.
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getEventChannel_EventChannelSourceConnector__EventChannel()
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getEventChannel__EventChannelSourceConnector
     * @model opposite="eventChannel__EventChannelSourceConnector" ordered="false"
     * @generated
     */
    EList<EventChannelSourceConnector> getEventChannelSourceConnector__EventChannel();

    /**
     * Returns the value of the '<em><b>Event Channel Sink Connector Event Channel</b></em>'
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector}. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector#getEventChannel__EventChannelSinkConnector
     * <em>Event Channel Event Channel Sink Connector</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event Channel Sink Connector Event Channel</em>' reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Event Channel Sink Connector Event Channel</em>' reference
     *         list.
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getEventChannel_EventChannelSinkConnector__EventChannel()
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector#getEventChannel__EventChannelSinkConnector
     * @model opposite="eventChannel__EventChannelSinkConnector" ordered="false"
     * @generated
     */
    EList<EventChannelSinkConnector> getEventChannelSinkConnector__EventChannel();

    /**
     * Returns the value of the '<em><b>Parent Structure Event Channel</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure#getEventChannel__ComposedStructure
     * <em>Event Channel Composed Structure</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parent Structure Event Channel</em>' container reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Parent Structure Event Channel</em>' container reference.
     * @see #setParentStructure__EventChannel(ComposedStructure)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getEventChannel_ParentStructure__EventChannel()
     * @see org.palladiosimulator.pcm.core.composition.ComposedStructure#getEventChannel__ComposedStructure
     * @model opposite="eventChannel__ComposedStructure" required="true" transient="false"
     *        ordered="false"
     * @generated
     */
    ComposedStructure getParentStructure__EventChannel();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannel#getParentStructure__EventChannel
     * <em>Parent Structure Event Channel</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Parent Structure Event Channel</em>' container
     *            reference.
     * @see #getParentStructure__EventChannel()
     * @generated
     */
    void setParentStructure__EventChannel(ComposedStructure value);

} // EventChannel
