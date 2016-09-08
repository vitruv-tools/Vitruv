/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.reliability;

import org.palladiosimulator.pcm.repository.PassiveResource;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Resource Timeout Failure Type</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * <p>
 * Type definition for a timeout failure-on-demand related to an AcquireAction for a certain
 * PassiveResource (see the documentation of the "timeout" attribute of the "AcquireAction" for
 * further information).
 * </p>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType#getPassiveResource__ResourceTimeoutFailureType
 * <em>Passive Resource Resource Timeout Failure Type</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.reliability.ReliabilityPackage#getResourceTimeoutFailureType()
 * @model
 * @generated
 */
public interface ResourceTimeoutFailureType extends SoftwareInducedFailureType {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Passive Resource Resource Timeout Failure Type</b></em>'
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.PassiveResource#getResourceTimeoutFailureType__PassiveResource
     * <em>Resource Timeout Failure Type Passive Resource</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc --> The passive resource&nbsp;whose&nbsp;acquiring
     * timeout is represented by this failure type&nbsp;(see the documentation of the "timeout"
     * attribute of the "AcquireAction" for further information). <!-- end-model-doc -->
     *
     * @return the value of the '<em>Passive Resource Resource Timeout Failure Type</em>' reference.
     * @see #setPassiveResource__ResourceTimeoutFailureType(PassiveResource)
     * @see org.palladiosimulator.pcm.reliability.ReliabilityPackage#getResourceTimeoutFailureType_PassiveResource__ResourceTimeoutFailureType()
     * @see org.palladiosimulator.pcm.repository.PassiveResource#getResourceTimeoutFailureType__PassiveResource
     * @model opposite="resourceTimeoutFailureType__PassiveResource" required="true"
     * @generated
     */
    PassiveResource getPassiveResource__ResourceTimeoutFailureType();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType#getPassiveResource__ResourceTimeoutFailureType
     * <em>Passive Resource Resource Timeout Failure Type</em>}' reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Passive Resource Resource Timeout Failure Type</em>'
     *            reference.
     * @see #getPassiveResource__ResourceTimeoutFailureType()
     * @generated
     */
    void setPassiveResource__ResourceTimeoutFailureType(PassiveResource value);

} // ResourceTimeoutFailureType
