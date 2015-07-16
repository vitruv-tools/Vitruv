/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Event Group</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> An EventGroup combines a set of EventTypes that are supported by a Sink
 * and/or a Source. This is comparable to an operation interface combining a set of operation
 * signatures. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.EventGroup#getEventTypes__EventGroup
 * <em>Event Types Event Group</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getEventGroup()
 * @model
 * @generated
 */
public interface EventGroup extends Interface {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Event Types Event Group</b></em>' containment reference
     * list. The list contents are of type {@link org.palladiosimulator.pcm.repository.EventType}.
     * It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.EventType#getEventGroup__EventType
     * <em>Event Group Event Type</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event Types Event Group</em>' containment reference list isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Event Types Event Group</em>' containment reference list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getEventGroup_EventTypes__EventGroup()
     * @see org.palladiosimulator.pcm.repository.EventType#getEventGroup__EventType
     * @model opposite="eventGroup__EventType" containment="true" ordered="false"
     * @generated
     */
    EList<EventType> getEventTypes__EventGroup();

} // EventGroup
