/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.reliability;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.reliability.ReliabilityPackage
 * @generated
 */
public interface ReliabilityFactory extends EFactory {

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
    ReliabilityFactory eINSTANCE = org.palladiosimulator.pcm.reliability.impl.ReliabilityFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Hardware Induced Failure Type</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Hardware Induced Failure Type</em>'.
     * @generated
     */
    HardwareInducedFailureType createHardwareInducedFailureType();

    /**
     * Returns a new object of class '<em>Software Induced Failure Type</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Software Induced Failure Type</em>'.
     * @generated
     */
    SoftwareInducedFailureType createSoftwareInducedFailureType();

    /**
     * Returns a new object of class '<em>Internal Failure Occurrence Description</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Internal Failure Occurrence Description</em>'.
     * @generated
     */
    InternalFailureOccurrenceDescription createInternalFailureOccurrenceDescription();

    /**
     * Returns a new object of class '<em>Network Induced Failure Type</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Network Induced Failure Type</em>'.
     * @generated
     */
    NetworkInducedFailureType createNetworkInducedFailureType();

    /**
     * Returns a new object of class '<em>External Failure Occurrence Description</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>External Failure Occurrence Description</em>'.
     * @generated
     */
    ExternalFailureOccurrenceDescription createExternalFailureOccurrenceDescription();

    /**
     * Returns a new object of class '<em>Resource Timeout Failure Type</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Resource Timeout Failure Type</em>'.
     * @generated
     */
    ResourceTimeoutFailureType createResourceTimeoutFailureType();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the package supported by this factory.
     * @generated
     */
    ReliabilityPackage getReliabilityPackage();

} // ReliabilityFactory
