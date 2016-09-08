/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel;

import org.palladiosimulator.pcm.core.PCMRandomVariable;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Loop</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> A Loop models a repeated sequence of actions in the user flow. It
 * contains a nested ScenarioBehaviour specifying the loop body, and a RandomVariable specifying the
 * number of iterations. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.Loop#getLoopIteration_Loop
 * <em>Loop Iteration Loop</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.Loop#getBodyBehaviour_Loop
 * <em>Body Behaviour Loop</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getLoop()
 * @model
 * @generated
 */
public interface Loop extends AbstractUserAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Loop Iteration Loop</b></em>' containment reference. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.PCMRandomVariable#getLoop_LoopIteration
     * <em>Loop Loop Iteration</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Loop Iteration Loop</em>' containment reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Loop Iteration Loop</em>' containment reference.
     * @see #setLoopIteration_Loop(PCMRandomVariable)
     * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getLoop_LoopIteration_Loop()
     * @see org.palladiosimulator.pcm.core.PCMRandomVariable#getLoop_LoopIteration
     * @model opposite="loop_LoopIteration" containment="true" required="true" ordered="false"
     * @generated
     */
    PCMRandomVariable getLoopIteration_Loop();

    /**
     * Sets the value of the '{@link org.palladiosimulator.pcm.usagemodel.Loop#getLoopIteration_Loop
     * <em>Loop Iteration Loop</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Loop Iteration Loop</em>' containment reference.
     * @see #getLoopIteration_Loop()
     * @generated
     */
    void setLoopIteration_Loop(PCMRandomVariable value);

    /**
     * Returns the value of the '<em><b>Body Behaviour Loop</b></em>' containment reference. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour#getLoop_ScenarioBehaviour
     * <em>Loop Scenario Behaviour</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Body Behaviour Loop</em>' containment reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Body Behaviour Loop</em>' containment reference.
     * @see #setBodyBehaviour_Loop(ScenarioBehaviour)
     * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getLoop_BodyBehaviour_Loop()
     * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour#getLoop_ScenarioBehaviour
     * @model opposite="loop_ScenarioBehaviour" containment="true" required="true" ordered="false"
     * @generated
     */
    ScenarioBehaviour getBodyBehaviour_Loop();

    /**
     * Sets the value of the '{@link org.palladiosimulator.pcm.usagemodel.Loop#getBodyBehaviour_Loop
     * <em>Body Behaviour Loop</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Body Behaviour Loop</em>' containment reference.
     * @see #getBodyBehaviour_Loop()
     * @generated
     */
    void setBodyBehaviour_Loop(ScenarioBehaviour value);

} // Loop
