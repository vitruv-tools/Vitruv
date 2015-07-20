/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourceenvironment;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage
 * @generated
 */
public interface ResourceenvironmentFactory extends EFactory {

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
    ResourceenvironmentFactory eINSTANCE = org.palladiosimulator.pcm.resourceenvironment.impl.ResourceenvironmentFactoryImpl
            .init();

    /**
     * Returns a new object of class '<em>Resource Environment</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Resource Environment</em>'.
     * @generated
     */
    ResourceEnvironment createResourceEnvironment();

    /**
     * Returns a new object of class '<em>Linking Resource</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Linking Resource</em>'.
     * @generated
     */
    LinkingResource createLinkingResource();

    /**
     * Returns a new object of class '<em>Resource Container</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Resource Container</em>'.
     * @generated
     */
    ResourceContainer createResourceContainer();

    /**
     * Returns a new object of class '<em>Processing Resource Specification</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Processing Resource Specification</em>'.
     * @generated
     */
    ProcessingResourceSpecification createProcessingResourceSpecification();

    /**
     * Returns a new object of class '<em>Communication Link Resource Specification</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Communication Link Resource Specification</em>'.
     * @generated
     */
    CommunicationLinkResourceSpecification createCommunicationLinkResourceSpecification();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the package supported by this factory.
     * @generated
     */
    ResourceenvironmentPackage getResourceenvironmentPackage();

} // ResourceenvironmentFactory
