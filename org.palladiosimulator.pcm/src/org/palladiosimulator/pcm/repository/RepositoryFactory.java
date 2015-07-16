/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model. <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage
 * @generated
 */
public interface RepositoryFactory extends EFactory {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    RepositoryFactory eINSTANCE = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Passive Resource</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Passive Resource</em>'.
     * @generated
     */
    PassiveResource createPassiveResource();

    /**
     * Returns a new object of class '<em>Basic Component</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Basic Component</em>'.
     * @generated
     */
    BasicComponent createBasicComponent();

    /**
     * Returns a new object of class '<em>Parameter</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Parameter</em>'.
     * @generated
     */
    Parameter createParameter();

    /**
     * Returns a new object of class '<em>Repository</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Repository</em>'.
     * @generated
     */
    Repository createRepository();

    /**
     * Returns a new object of class '<em>Required Characterisation</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Required Characterisation</em>'.
     * @generated
     */
    RequiredCharacterisation createRequiredCharacterisation();

    /**
     * Returns a new object of class '<em>Event Group</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Event Group</em>'.
     * @generated
     */
    EventGroup createEventGroup();

    /**
     * Returns a new object of class '<em>Event Type</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Event Type</em>'.
     * @generated
     */
    EventType createEventType();

    /**
     * Returns a new object of class '<em>Exception Type</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Exception Type</em>'.
     * @generated
     */
    ExceptionType createExceptionType();

    /**
     * Returns a new object of class '<em>Infrastructure Signature</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Infrastructure Signature</em>'.
     * @generated
     */
    InfrastructureSignature createInfrastructureSignature();

    /**
     * Returns a new object of class '<em>Infrastructure Interface</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Infrastructure Interface</em>'.
     * @generated
     */
    InfrastructureInterface createInfrastructureInterface();

    /**
     * Returns a new object of class '<em>Infrastructure Required Role</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Infrastructure Required Role</em>'.
     * @generated
     */
    InfrastructureRequiredRole createInfrastructureRequiredRole();

    /**
     * Returns a new object of class '<em>Operation Signature</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Operation Signature</em>'.
     * @generated
     */
    OperationSignature createOperationSignature();

    /**
     * Returns a new object of class '<em>Operation Interface</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Operation Interface</em>'.
     * @generated
     */
    OperationInterface createOperationInterface();

    /**
     * Returns a new object of class '<em>Operation Required Role</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Operation Required Role</em>'.
     * @generated
     */
    OperationRequiredRole createOperationRequiredRole();

    /**
     * Returns a new object of class '<em>Source Role</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Source Role</em>'.
     * @generated
     */
    SourceRole createSourceRole();

    /**
     * Returns a new object of class '<em>Sink Role</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Sink Role</em>'.
     * @generated
     */
    SinkRole createSinkRole();

    /**
     * Returns a new object of class '<em>Operation Provided Role</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Operation Provided Role</em>'.
     * @generated
     */
    OperationProvidedRole createOperationProvidedRole();

    /**
     * Returns a new object of class '<em>Infrastructure Provided Role</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Infrastructure Provided Role</em>'.
     * @generated
     */
    InfrastructureProvidedRole createInfrastructureProvidedRole();

    /**
     * Returns a new object of class '<em>Complete Component Type</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Complete Component Type</em>'.
     * @generated
     */
    CompleteComponentType createCompleteComponentType();

    /**
     * Returns a new object of class '<em>Provides Component Type</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Provides Component Type</em>'.
     * @generated
     */
    ProvidesComponentType createProvidesComponentType();

    /**
     * Returns a new object of class '<em>Composite Component</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Composite Component</em>'.
     * @generated
     */
    CompositeComponent createCompositeComponent();

    /**
     * Returns a new object of class '<em>Primitive Data Type</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Primitive Data Type</em>'.
     * @generated
     */
    PrimitiveDataType createPrimitiveDataType();

    /**
     * Returns a new object of class '<em>Collection Data Type</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Collection Data Type</em>'.
     * @generated
     */
    CollectionDataType createCollectionDataType();

    /**
     * Returns a new object of class '<em>Composite Data Type</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Composite Data Type</em>'.
     * @generated
     */
    CompositeDataType createCompositeDataType();

    /**
     * Returns a new object of class '<em>Inner Declaration</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Inner Declaration</em>'.
     * @generated
     */
    InnerDeclaration createInnerDeclaration();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    RepositoryPackage getRepositoryPackage();

} // RepositoryFactory
