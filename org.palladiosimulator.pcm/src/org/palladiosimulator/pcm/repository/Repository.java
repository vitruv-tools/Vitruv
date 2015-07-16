/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.reliability.FailureType;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Repository</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> The repository entity allows storing components, data types, and
 * interfaces to be fetched and reused for construction of component instances as well as new
 * component types. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.Repository#getRepositoryDescription
 * <em>Repository Description</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.Repository#getComponents__Repository
 * <em>Components Repository</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.Repository#getInterfaces__Repository
 * <em>Interfaces Repository</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.Repository#getFailureTypes__Repository
 * <em>Failure Types Repository</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.Repository#getDataTypes__Repository
 * <em>Data Types Repository</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getRepository()
 * @model
 * @generated
 */
public interface Repository extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Repository Description</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> This property represents a
     * description of the repository. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Repository Description</em>' attribute.
     * @see #setRepositoryDescription(String)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getRepository_RepositoryDescription()
     * @model
     * @generated
     */
    String getRepositoryDescription();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.Repository#getRepositoryDescription
     * <em>Repository Description</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Repository Description</em>' attribute.
     * @see #getRepositoryDescription()
     * @generated
     */
    void setRepositoryDescription(String value);

    /**
     * Returns the value of the '<em><b>Components Repository</b></em>' containment reference list.
     * The list contents are of type
     * {@link org.palladiosimulator.pcm.repository.RepositoryComponent}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.repository.RepositoryComponent#getRepository__RepositoryComponent
     * <em>Repository Repository Component</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * <!-- begin-model-doc --> This property represents the provides component types stored in the
     * repository. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Components Repository</em>' containment reference list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getRepository_Components__Repository()
     * @see org.palladiosimulator.pcm.repository.RepositoryComponent#getRepository__RepositoryComponent
     * @model opposite="repository__RepositoryComponent" containment="true" ordered="false"
     * @generated
     */
    EList<RepositoryComponent> getComponents__Repository();

    /**
     * Returns the value of the '<em><b>Interfaces Repository</b></em>' containment reference list.
     * The list contents are of type {@link org.palladiosimulator.pcm.repository.Interface}. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.Interface#getRepository__Interface
     * <em>Repository Interface</em>}'. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> This property represents the interfaces stored in the repository. <!--
     * end-model-doc -->
     *
     * @return the value of the '<em>Interfaces Repository</em>' containment reference list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getRepository_Interfaces__Repository()
     * @see org.palladiosimulator.pcm.repository.Interface#getRepository__Interface
     * @model opposite="repository__Interface" containment="true" ordered="false"
     * @generated
     */
    EList<Interface> getInterfaces__Repository();

    /**
     * Returns the value of the '<em><b>Failure Types Repository</b></em>' containment reference
     * list. The list contents are of type {@link org.palladiosimulator.pcm.reliability.FailureType}
     * . It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.reliability.FailureType#getRepository__FailureType
     * <em>Repository Failure Type</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Failure Types Repository</em>' containment reference list isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Failure Types Repository</em>' containment reference list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getRepository_FailureTypes__Repository()
     * @see org.palladiosimulator.pcm.reliability.FailureType#getRepository__FailureType
     * @model opposite="repository__FailureType" containment="true" ordered="false"
     * @generated
     */
    EList<FailureType> getFailureTypes__Repository();

    /**
     * Returns the value of the '<em><b>Data Types Repository</b></em>' containment reference list.
     * The list contents are of type {@link org.palladiosimulator.pcm.repository.DataType}. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.DataType#getRepository__DataType
     * <em>Repository Data Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> This property represents the data types stored in the repository. <!--
     * end-model-doc -->
     *
     * @return the value of the '<em>Data Types Repository</em>' containment reference list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getRepository_DataTypes__Repository()
     * @see org.palladiosimulator.pcm.repository.DataType#getRepository__DataType
     * @model opposite="repository__DataType" containment="true" ordered="false"
     * @generated
     */
    EList<DataType> getDataTypes__Repository();

} // Repository
