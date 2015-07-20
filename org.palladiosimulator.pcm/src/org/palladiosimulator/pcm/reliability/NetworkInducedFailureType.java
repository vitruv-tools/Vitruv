/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.reliability;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Network Induced Failure Type</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * <p>
 * Type definition for a network-induced failure, i.e. a failure-on-demand occurrence&nbsp;due to a
 * communication link fault. One standard type&nbsp;is pre-defined according to the pre-defined
 * CommunicationLinkResourceType: LAN.
 * </p>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.reliability.NetworkInducedFailureType#getCommunicationLinkResourceType__NetworkInducedFailureType
 * <em>Communication Link Resource Type Network Induced Failure Type</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.reliability.ReliabilityPackage#getNetworkInducedFailureType()
 * @model
 * @generated
 */
public interface NetworkInducedFailureType extends FailureType {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '
     * <em><b>Communication Link Resource Type Network Induced Failure Type</b></em>' reference. It
     * is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType#getNetworkInducedFailureType__CommunicationLinkResourceType
     * <em>Network Induced Failure Type Communication Link Resource Type</em>}'. <!-- begin-user-doc
     * -->
     * <p>
     * If the meaning of the '<em>Communication Link Resource Type Network Induced Failure Type</em>
     * ' reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '
     *         <em>Communication Link Resource Type Network Induced Failure Type</em>' reference.
     * @see #setCommunicationLinkResourceType__NetworkInducedFailureType(CommunicationLinkResourceType)
     * @see org.palladiosimulator.pcm.reliability.ReliabilityPackage#getNetworkInducedFailureType_CommunicationLinkResourceType__NetworkInducedFailureType()
     * @see org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType#getNetworkInducedFailureType__CommunicationLinkResourceType
     * @model opposite="networkInducedFailureType__CommunicationLinkResourceType" required="true"
     *        ordered="false"
     * @generated
     */
    CommunicationLinkResourceType getCommunicationLinkResourceType__NetworkInducedFailureType();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.reliability.NetworkInducedFailureType#getCommunicationLinkResourceType__NetworkInducedFailureType
     * <em>Communication Link Resource Type Network Induced Failure Type</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Communication Link Resource Type Network Induced Failure Type</em>' reference.
     * @see #getCommunicationLinkResourceType__NetworkInducedFailureType()
     * @generated
     */
    void setCommunicationLinkResourceType__NetworkInducedFailureType(CommunicationLinkResourceType value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.communicationLinkResourceType__NetworkInducedFailureType <> null'"
     * @generated
     */
    boolean NetworkInducedFailureTypeHasCommunicationLinkResourceType(DiagnosticChain diagnostics,
            Map<Object, Object> context);

} // NetworkInducedFailureType
