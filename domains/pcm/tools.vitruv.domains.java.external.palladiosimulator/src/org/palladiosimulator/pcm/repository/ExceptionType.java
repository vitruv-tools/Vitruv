/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Exception Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> This entity represents a type of an exception. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.ExceptionType#getExceptionName
 * <em>Exception Name</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.ExceptionType#getExceptionMessage
 * <em>Exception Message</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getExceptionType()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ExceptionType extends CDOObject {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Exception Name</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc --> <!-- begin-model-doc --> This property denotes the name of the
     * exception. In addition to the exception message, this is another piece of information that
     * can be used for identification of the exception that has appeared. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Exception Name</em>' attribute.
     * @see #setExceptionName(String)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getExceptionType_ExceptionName()
     * @model required="true"
     * @generated
     */
    String getExceptionName();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.ExceptionType#getExceptionName
     * <em>Exception Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Exception Name</em>' attribute.
     * @see #getExceptionName()
     * @generated
     */
    void setExceptionName(String value);

    /**
     * Returns the value of the '<em><b>Exception Message</b></em>' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc --> <!-- begin-model-doc --> This property holds the text message of
     * the exception. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Exception Message</em>' attribute.
     * @see #setExceptionMessage(String)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getExceptionType_ExceptionMessage()
     * @model required="true"
     * @generated
     */
    String getExceptionMessage();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.ExceptionType#getExceptionMessage
     * <em>Exception Message</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Exception Message</em>' attribute.
     * @see #getExceptionMessage()
     * @generated
     */
    void setExceptionMessage(String value);

} // ExceptionType
