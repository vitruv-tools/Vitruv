/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Source Role</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.SourceRole#getEventGroup__SourceRole
 * <em>Event Group Source Role</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getSourceRole()
 * @model
 * @generated
 */
public interface SourceRole extends RequiredRole {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Event Group Source Role</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event Group Source Role</em>' reference isn't clear, there really
     * should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Event Group Source Role</em>' reference.
     * @see #setEventGroup__SourceRole(EventGroup)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getSourceRole_EventGroup__SourceRole()
     * @model required="true"
     * @generated
     */
    EventGroup getEventGroup__SourceRole();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.SourceRole#getEventGroup__SourceRole
     * <em>Event Group Source Role</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Event Group Source Role</em>' reference.
     * @see #getEventGroup__SourceRole()
     * @generated
     */
    void setEventGroup__SourceRole(EventGroup value);

} // SourceRole
