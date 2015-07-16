/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import org.palladiosimulator.pcm.core.PCMRandomVariable;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Loop Action</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> Models the repeated execution of its inner ResourceDemandingBehaviour
 * for the loop body. The number of repetitions is specified by a random variable evaluating to
 * integer or an IntPMF. The number of iterations specified by the random variable always needs to
 * be bounded, i.e., the probabilities in an IntPMF for iteration numbers above a certain threshold
 * must be zero. Otherwise, it would be possible that certain requests do not terminate, which would
 * complicate performance analyses. The stochastic expression defining the iteration random variable
 * may include references to input or component parameters to model dependencies between the usage
 * profile and the number of loop iterations. Notice, that loop actions should only be modelled if
 * the loop body contains either external service calls or resource demands directed at special
 * resources. Otherwise, control flow loops in component behaviour should be abstracted by subsuming
 * them in InternalAction, which combine a number of instructions. The influence of different
 * iterations length of such internal loops need to be reflected stochastically by the random
 * variable specifying the ParametricResource-Demand of that InternalAction. Other than Markov
 * chains, RDSEFFs do not specify control flow loops with an reentrance and exit probability on each
 * iteration. Such a specification binds the number of loop iterations to a geometrical
 * distribution, which reflects reality only in very seldom cases. But in many practical cases, the
 * number of iterations is a constant, or the probability for higher iteration numbers is higher
 * than for lower ones. This cannot be expressed directly via a Markov chain (also see [DG00]).
 * Inside the ResourceDemandingBehaviour of LoopActions, it is assumed that random variables are
 * stochastically independent. This is not true in reality, and for example leads to wrong
 * predictions if the same random variable is used twice in succession inside a loop body. In this
 * case, the second occurrence is stochastically dependent to the first occurrence, as the value
 * does not change between two occurrences. Therefore, component developers should be aware of such
 * inaccuracies when using random variables twice inside the body behaviour of a LoopAction. <!--
 * end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.LoopAction#getIterationCount_LoopAction
 * <em>Iteration Count Loop Action</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getLoopAction()
 * @model
 * @generated
 */
public interface LoopAction extends AbstractLoopAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Iteration Count Loop Action</b></em>' containment reference.
     * It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.PCMRandomVariable#getLoopAction_PCMRandomVariable
     * <em>Loop Action PCM Random Variable</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Iteration Count Loop Action</em>' containment reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Iteration Count Loop Action</em>' containment reference.
     * @see #setIterationCount_LoopAction(PCMRandomVariable)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getLoopAction_IterationCount_LoopAction()
     * @see org.palladiosimulator.pcm.core.PCMRandomVariable#getLoopAction_PCMRandomVariable
     * @model opposite="loopAction_PCMRandomVariable" containment="true" required="true"
     *        ordered="false"
     * @generated
     */
    PCMRandomVariable getIterationCount_LoopAction();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.LoopAction#getIterationCount_LoopAction
     * <em>Iteration Count Loop Action</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Iteration Count Loop Action</em>' containment reference.
     * @see #getIterationCount_LoopAction()
     * @generated
     */
    void setIterationCount_LoopAction(PCMRandomVariable value);

} // LoopAction
