/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Branch Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> The BranchAction splits the RDSEFF control flow with an XOR-semantic,
 * meaning that the control flow continues on exactly one of its attached AbstractBranchTransitions.
 * The RDSEFF supports two different kinds of branch transitions, GuardedBranchTransitions, and
 * ProbabilisticBranchTransitions. RDSEFFs do not allow to use both kinds of transitions on a single
 * BranchAction. Analysis or simulation tools must select exactly one transition based on the
 * included guard or probability, before continuing at a BranchAction. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.BranchAction#getBranches_Branch
 * <em>Branches Branch</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getBranchAction()
 * @model
 * @generated
 */
public interface BranchAction extends AbstractInternalControlFlowAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Branches Branch</b></em>' containment reference list. The
     * list contents are of type {@link org.palladiosimulator.pcm.seff.AbstractBranchTransition}. It
     * is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.AbstractBranchTransition#getBranchAction_AbstractBranchTransition
     * <em>Branch Action Abstract Branch Transition</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Branches Branch</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Branches Branch</em>' containment reference list.
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getBranchAction_Branches_Branch()
     * @see org.palladiosimulator.pcm.seff.AbstractBranchTransition#getBranchAction_AbstractBranchTransition
     * @model opposite="branchAction_AbstractBranchTransition" containment="true" ordered="false"
     * @generated
     */
    EList<AbstractBranchTransition> getBranches_Branch();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.branches_Branch->forAll(bt|bt.oclIsTypeOf(ProbabilisticBranchTransition)) \r\nor self.branches_Branch->forAll(bt|bt.oclIsTypeOf(GuardedBranchTransition))'"
     * @generated
     */
    boolean EitherGuardedBranchesOrProbabilisiticBranchTransitions(DiagnosticChain diagnostics,
            Map<Object, Object> context);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='if self.branches_Branch->forAll(oclIsTypeOf(ProbabilisticBranchTransition)) then \r\n\tself.branches_Branch->select(pbt|pbt.oclIsTypeOf(ProbabilisticBranchTransition))->collect(pbt|pbt.oclAsType(ProbabilisticBranchTransition).branchProbability)->sum() > 0.9999 \r\n\tand self.branches_Branch->select(pbt|pbt.oclIsTypeOf(ProbabilisticBranchTransition))->collect(pbt|pbt.oclAsType(ProbabilisticBranchTransition).branchProbability)->sum() < 1.0001 \r\n\telse true \r\nendif'"
     * @generated
     */
    boolean AllProbabilisticBranchProbabilitiesMustSumUpTo1(DiagnosticChain diagnostics, Map<Object, Object> context);

} // BranchAction
