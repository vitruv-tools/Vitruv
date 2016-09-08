/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Collection Data Type</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> This entity represents a collection data type, e.g.,. a list, array,
 * set, of items of the a particular type. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.CollectionDataType#getInnerType_CollectionDataType
 * <em>Inner Type Collection Data Type</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getCollectionDataType()
 * @model
 * @generated
 */
public interface CollectionDataType extends Entity, DataType {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Inner Type Collection Data Type</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> This property represents
     * the type of items contained in the collection data type. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Inner Type Collection Data Type</em>' reference.
     * @see #setInnerType_CollectionDataType(DataType)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getCollectionDataType_InnerType_CollectionDataType()
     * @model required="true"
     * @generated
     */
    DataType getInnerType_CollectionDataType();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.CollectionDataType#getInnerType_CollectionDataType
     * <em>Inner Type Collection Data Type</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Inner Type Collection Data Type</em>' reference.
     * @see #getInnerType_CollectionDataType()
     * @generated
     */
    void setInnerType_CollectionDataType(DataType value);

} // CollectionDataType
