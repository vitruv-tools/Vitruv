/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import org.palladiosimulator.pcm.core.entity.NamedElement;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Inner Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> This entity represent a declaration of a data type inside of a composite
 * data type. It enriches the data type it declares with a name to allow multiple declarations of
 * the same type within a composite data type. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.InnerDeclaration#getDatatype_InnerDeclaration
 * <em>Datatype Inner Declaration</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.InnerDeclaration#getCompositeDataType_InnerDeclaration
 * <em>Composite Data Type Inner Declaration</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getInnerDeclaration()
 * @model
 * @generated
 */
public interface InnerDeclaration extends NamedElement {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Datatype Inner Declaration</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> This property represents
     * the data type that the inner declaration declares. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Datatype Inner Declaration</em>' reference.
     * @see #setDatatype_InnerDeclaration(DataType)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getInnerDeclaration_Datatype_InnerDeclaration()
     * @model required="true"
     * @generated
     */
    DataType getDatatype_InnerDeclaration();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.InnerDeclaration#getDatatype_InnerDeclaration
     * <em>Datatype Inner Declaration</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @param value
     *            the new value of the '<em>Datatype Inner Declaration</em>' reference.
     * @see #getDatatype_InnerDeclaration()
     * @generated
     */
    void setDatatype_InnerDeclaration(DataType value);

    /**
     * Returns the value of the '<em><b>Composite Data Type Inner Declaration</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.CompositeDataType#getInnerDeclaration_CompositeDataType
     * <em>Inner Declaration Composite Data Type</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Composite Data Type Inner Declaration</em>' container reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Composite Data Type Inner Declaration</em>' container
     *         reference.
     * @see #setCompositeDataType_InnerDeclaration(CompositeDataType)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getInnerDeclaration_CompositeDataType_InnerDeclaration()
     * @see org.palladiosimulator.pcm.repository.CompositeDataType#getInnerDeclaration_CompositeDataType
     * @model opposite="innerDeclaration_CompositeDataType" required="true" transient="false"
     *        ordered="false"
     * @generated
     */
    CompositeDataType getCompositeDataType_InnerDeclaration();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.InnerDeclaration#getCompositeDataType_InnerDeclaration
     * <em>Composite Data Type Inner Declaration</em>}' container reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Composite Data Type Inner Declaration</em>' container
     *            reference.
     * @see #getCompositeDataType_InnerDeclaration()
     * @generated
     */
    void setCompositeDataType_InnerDeclaration(CompositeDataType value);

} // InnerDeclaration
