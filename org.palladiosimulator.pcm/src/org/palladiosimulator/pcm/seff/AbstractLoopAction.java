/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Abstract Loop Action</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * <p>
 * Abstract parent class of any loop (e.g. LoopAction)
 * </p>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.AbstractLoopAction#getBodyBehaviour_Loop
 * <em>Body Behaviour Loop</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getAbstractLoopAction()
 * @model abstract="true"
 * @generated
 */
public interface AbstractLoopAction extends AbstractInternalControlFlowAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Body Behaviour Loop</b></em>' containment reference. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour#getAbstractLoopAction_ResourceDemandingBehaviour
     * <em>Abstract Loop Action Resource Demanding Behaviour</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Body Behaviour Loop</em>' containment reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Body Behaviour Loop</em>' containment reference.
     * @see #setBodyBehaviour_Loop(ResourceDemandingBehaviour)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getAbstractLoopAction_BodyBehaviour_Loop()
     * @see org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour#getAbstractLoopAction_ResourceDemandingBehaviour
     * @model opposite="abstractLoopAction_ResourceDemandingBehaviour" containment="true"
     *        required="true" ordered="false"
     * @generated
     */
    ResourceDemandingBehaviour getBodyBehaviour_Loop();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.AbstractLoopAction#getBodyBehaviour_Loop
     * <em>Body Behaviour Loop</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Body Behaviour Loop</em>' containment reference.
     * @see #getBodyBehaviour_Loop()
     * @generated
     */
    void setBodyBehaviour_Loop(ResourceDemandingBehaviour value);

} // AbstractLoopAction
