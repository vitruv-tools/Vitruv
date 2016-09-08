/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import org.palladiosimulator.pcm.repository.PassiveResource;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Release Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> The ReleaseAction increases the number of available item for the given
 * passive resource type, before the current request can continue. It should be to execute by one of
 * the other concurrent requests. Acquisition and release of passive resources happen
 * instantaneously and do not consume any time except for waiting delays before actual acquisition.
 * Resource locking may introduce deadlocks when simulating the model, however, for performance
 * analysis with the PCM it is assumed that no deadlocks occur. Otherwise, the model first needs to
 * be fixed accordingly before carrying out the performance prediction. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.ReleaseAction#getPassiveResource_ReleaseAction
 * <em>Passive Resource Release Action</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getReleaseAction()
 * @model
 * @generated
 */
public interface ReleaseAction extends AbstractInternalControlFlowAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Passive Resource Release Action</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Passive Resource Release Action</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Passive Resource Release Action</em>' reference.
     * @see #setPassiveResource_ReleaseAction(PassiveResource)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getReleaseAction_PassiveResource_ReleaseAction()
     * @model required="true"
     * @generated
     */
    PassiveResource getPassiveResource_ReleaseAction();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.ReleaseAction#getPassiveResource_ReleaseAction
     * <em>Passive Resource Release Action</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Passive Resource Release Action</em>' reference.
     * @see #getPassiveResource_ReleaseAction()
     * @generated
     */
    void setPassiveResource_ReleaseAction(PassiveResource value);

} // ReleaseAction
