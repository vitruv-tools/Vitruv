/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Event Type</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> The EventType represents a single class of events that can be emited by
 * a source or consumed by a sink. The characteristics of the event are defined by the variable
 * included in the EventType. Due to the extension of the signature class, the EventType is also
 * accessible for ServiceEffectSpecifications (SEFF). By this, a SEFF is able to represent an event
 * handler for this type of events. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.EventType#getParameter__EventType
 * <em>Parameter Event Type</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.EventType#getEventGroup__EventType
 * <em>Event Group Event Type</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getEventType()
 * @model
 * @generated
 */
public interface EventType extends Signature {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Parameter Event Type</b></em>' containment reference. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.Parameter#getEventType__Parameter
     * <em>Event Type Parameter</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter Event Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Parameter Event Type</em>' containment reference.
     * @see #setParameter__EventType(Parameter)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getEventType_Parameter__EventType()
     * @see org.palladiosimulator.pcm.repository.Parameter#getEventType__Parameter
     * @model opposite="eventType__Parameter" containment="true" required="true" ordered="false"
     * @generated
     */
    Parameter getParameter__EventType();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.EventType#getParameter__EventType
     * <em>Parameter Event Type</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Parameter Event Type</em>' containment reference.
     * @see #getParameter__EventType()
     * @generated
     */
    void setParameter__EventType(Parameter value);

    /**
     * Returns the value of the '<em><b>Event Group Event Type</b></em>' container reference. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.EventGroup#getEventTypes__EventGroup
     * <em>Event Types Event Group</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event Group Event Type</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Event Group Event Type</em>' container reference.
     * @see #setEventGroup__EventType(EventGroup)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getEventType_EventGroup__EventType()
     * @see org.palladiosimulator.pcm.repository.EventGroup#getEventTypes__EventGroup
     * @model opposite="eventTypes__EventGroup" required="true" transient="false" ordered="false"
     * @generated
     */
    EventGroup getEventGroup__EventType();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.EventType#getEventGroup__EventType
     * <em>Event Group Event Type</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Event Group Event Type</em>' container reference.
     * @see #getEventGroup__EventType()
     * @generated
     */
    void setEventGroup__EventType(EventGroup value);

} // EventType
