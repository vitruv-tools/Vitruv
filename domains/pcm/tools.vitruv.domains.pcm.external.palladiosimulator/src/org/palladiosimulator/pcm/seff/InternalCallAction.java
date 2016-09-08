/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Internal Call Action</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> A "SubSEFF"-Action: Realises an internal method call within a SEFF. <!--
 * end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.InternalCallAction#getCalledResourceDemandingInternalBehaviour
 * <em>Called Resource Demanding Internal Behaviour</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getInternalCallAction()
 * @model
 * @generated
 */
public interface InternalCallAction extends CallAction, AbstractInternalControlFlowAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Called Resource Demanding Internal Behaviour</b></em>'
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Called Resource Demanding Internal Behaviour</em>' reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Called Resource Demanding Internal Behaviour</em>' reference.
     * @see #setCalledResourceDemandingInternalBehaviour(ResourceDemandingBehaviour)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getInternalCallAction_CalledResourceDemandingInternalBehaviour()
     * @model required="true" ordered="false"
     * @generated
     */
    ResourceDemandingBehaviour getCalledResourceDemandingInternalBehaviour();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.InternalCallAction#getCalledResourceDemandingInternalBehaviour
     * <em>Called Resource Demanding Internal Behaviour</em>}' reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Called Resource Demanding Internal Behaviour</em>'
     *            reference.
     * @see #getCalledResourceDemandingInternalBehaviour()
     * @generated
     */
    void setCalledResourceDemandingInternalBehaviour(ResourceDemandingBehaviour value);

} // InternalCallAction
