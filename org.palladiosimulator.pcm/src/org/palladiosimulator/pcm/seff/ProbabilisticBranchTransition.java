/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Probabilistic Branch Transition</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> a GuardedBranchTransition, this transition provides a link between a
 * BranchAction and a nested ResourceDemandingBehaviour, which includes the actions executed inside
 * the branch. But instead of using a guard, it specifies a branching probability without parameter
 * dependencies. Analysis tools may directly use it to determine the transition where the control
 * flow continues. The probabilities of all ProbabilisticBranchTransitions belonging to a single
 * BranchAction must sum up to 1.0. Although a probabilistic choice at a branch usually does not
 * happen in a computer program, ProbabilisticBranchTransitions provide a convenient way of
 * modelling in case the actual parameter dependency is too hard to determine or too complex to
 * integrate into a guard. It can also be useful for newly designed components, where the parameter
 * dependency on the control flow guard is still be unknown. However, this construct potentially
 * introduces inaccuracies into the performance model, because it does not reflect the influence of
 * input parameters. Therefore, predictions based on this model can be misleading, if the used input
 * parameters would result in different branching probabilities. The component developer cannot
 * foresee this, when specifying the RDSEFF using ProbabilisticBranchTransitions. <!-- end-model-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.ProbabilisticBranchTransition#getBranchProbability
 * <em>Branch Probability</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getProbabilisticBranchTransition()
 * @model
 * @generated
 */
public interface ProbabilisticBranchTransition extends AbstractBranchTransition {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Branch Probability</b></em>' attribute. <!-- begin-user-doc
     * -->
     * <p>
     * If the meaning of the '<em>Branch Probability</em>' attribute isn't clear, there really
     * should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Branch Probability</em>' attribute.
     * @see #setBranchProbability(double)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getProbabilisticBranchTransition_BranchProbability()
     * @model required="true" ordered="false"
     * @generated
     */
    double getBranchProbability();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.ProbabilisticBranchTransition#getBranchProbability
     * <em>Branch Probability</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Branch Probability</em>' attribute.
     * @see #getBranchProbability()
     * @generated
     */
    void setBranchProbability(double value);

} // ProbabilisticBranchTransition
