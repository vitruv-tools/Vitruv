/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage
 * @generated
 */
public interface CompositionFactory extends EFactory {

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
    CompositionFactory eINSTANCE = org.palladiosimulator.pcm.core.composition.impl.CompositionFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Resource Required Delegation Connector</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Resource Required Delegation Connector</em>'.
     * @generated
     */
    ResourceRequiredDelegationConnector createResourceRequiredDelegationConnector();

    /**
     * Returns a new object of class '<em>Event Channel</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Event Channel</em>'.
     * @generated
     */
    EventChannel createEventChannel();

    /**
     * Returns a new object of class '<em>Event Channel Source Connector</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Event Channel Source Connector</em>'.
     * @generated
     */
    EventChannelSourceConnector createEventChannelSourceConnector();

    /**
     * Returns a new object of class '<em>Event Channel Sink Connector</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Event Channel Sink Connector</em>'.
     * @generated
     */
    EventChannelSinkConnector createEventChannelSinkConnector();

    /**
     * Returns a new object of class '<em>Provided Delegation Connector</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Provided Delegation Connector</em>'.
     * @generated
     */
    ProvidedDelegationConnector createProvidedDelegationConnector();

    /**
     * Returns a new object of class '<em>Required Delegation Connector</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Required Delegation Connector</em>'.
     * @generated
     */
    RequiredDelegationConnector createRequiredDelegationConnector();

    /**
     * Returns a new object of class '<em>Assembly Connector</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Assembly Connector</em>'.
     * @generated
     */
    AssemblyConnector createAssemblyConnector();

    /**
     * Returns a new object of class '<em>Assembly Event Connector</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Assembly Event Connector</em>'.
     * @generated
     */
    AssemblyEventConnector createAssemblyEventConnector();

    /**
     * Returns a new object of class '<em>Source Delegation Connector</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Source Delegation Connector</em>'.
     * @generated
     */
    SourceDelegationConnector createSourceDelegationConnector();

    /**
     * Returns a new object of class '<em>Sink Delegation Connector</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Sink Delegation Connector</em>'.
     * @generated
     */
    SinkDelegationConnector createSinkDelegationConnector();

    /**
     * Returns a new object of class '<em>Assembly Infrastructure Connector</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Assembly Infrastructure Connector</em>'.
     * @generated
     */
    AssemblyInfrastructureConnector createAssemblyInfrastructureConnector();

    /**
     * Returns a new object of class '<em>Provided Infrastructure Delegation Connector</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Provided Infrastructure Delegation Connector</em>'.
     * @generated
     */
    ProvidedInfrastructureDelegationConnector createProvidedInfrastructureDelegationConnector();

    /**
     * Returns a new object of class '<em>Required Infrastructure Delegation Connector</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Required Infrastructure Delegation Connector</em>'.
     * @generated
     */
    RequiredInfrastructureDelegationConnector createRequiredInfrastructureDelegationConnector();

    /**
     * Returns a new object of class '<em>Required Resource Delegation Connector</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Required Resource Delegation Connector</em>'.
     * @generated
     */
    RequiredResourceDelegationConnector createRequiredResourceDelegationConnector();

    /**
     * Returns a new object of class '<em>Assembly Context</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Assembly Context</em>'.
     * @generated
     */
    AssemblyContext createAssemblyContext();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the package supported by this factory.
     * @generated
     */
    CompositionPackage getCompositionPackage();

} // CompositionFactory
