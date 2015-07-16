/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;
import org.palladiosimulator.pcm.seff.AbstractBranchTransition;
import org.palladiosimulator.pcm.seff.BranchAction;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.util.SeffValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Branch Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.BranchActionImpl#getBranches_Branch
 * <em>Branches Branch</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BranchActionImpl extends AbstractInternalControlFlowActionImpl implements BranchAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected BranchActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.BRANCH_ACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<AbstractBranchTransition> getBranches_Branch() {
        return (EList<AbstractBranchTransition>) this.eDynamicGet(SeffPackage.BRANCH_ACTION__BRANCHES_BRANCH,
                SeffPackage.Literals.BRANCH_ACTION__BRANCHES_BRANCH, true, true);
    }

    /**
     * The cached OCL expression body for the '
     * {@link #EitherGuardedBranchesOrProbabilisiticBranchTransitions(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Either Guarded Branches Or Probabilisitic Branch Transitions</em>}' operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #EitherGuardedBranchesOrProbabilisiticBranchTransitions(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String EITHER_GUARDED_BRANCHES_OR_PROBABILISITIC_BRANCH_TRANSITIONS__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.branches_Branch->forAll(bt|bt.oclIsTypeOf(ProbabilisticBranchTransition)) \n"
            + "or self.branches_Branch->forAll(bt|bt.oclIsTypeOf(GuardedBranchTransition))";
    /**
     * The cached OCL invariant for the '
     * {@link #EitherGuardedBranchesOrProbabilisiticBranchTransitions(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Either Guarded Branches Or Probabilisitic Branch Transitions</em>}' invariant operation.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #EitherGuardedBranchesOrProbabilisiticBranchTransitions(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint EITHER_GUARDED_BRANCHES_OR_PROBABILISITIC_BRANCH_TRANSITIONS__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean EitherGuardedBranchesOrProbabilisiticBranchTransitions(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (EITHER_GUARDED_BRANCHES_OR_PROBABILISITIC_BRANCH_TRANSITIONS__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(SeffPackage.Literals.BRANCH_ACTION);
            try {
                EITHER_GUARDED_BRANCHES_OR_PROBABILISITIC_BRANCH_TRANSITIONS__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                EITHER_GUARDED_BRANCHES_OR_PROBABILISITIC_BRANCH_TRANSITIONS__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV
                .createQuery(
                        EITHER_GUARDED_BRANCHES_OR_PROBABILISITIC_BRANCH_TRANSITIONS__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, SeffValidator.DIAGNOSTIC_SOURCE,
                                SeffValidator.BRANCH_ACTION__EITHER_GUARDED_BRANCHES_OR_PROBABILISITIC_BRANCH_TRANSITIONS,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "EitherGuardedBranchesOrProbabilisiticBranchTransitions",
                                                EObjectValidator.getObjectLabel(this, context) }),
                        new Object[] { this }));
            }
            return false;
        }
        return true;
    }

    /**
     * The cached OCL expression body for the '
     * {@link #AllProbabilisticBranchProbabilitiesMustSumUpTo1(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>All Probabilistic Branch Probabilities Must Sum Up To1</em>}' operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #AllProbabilisticBranchProbabilitiesMustSumUpTo1(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String ALL_PROBABILISTIC_BRANCH_PROBABILITIES_MUST_SUM_UP_TO1__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "if self.branches_Branch->forAll(oclIsTypeOf(ProbabilisticBranchTransition)) then \n"
            + "	self.branches_Branch->select(pbt|pbt.oclIsTypeOf(ProbabilisticBranchTransition))->collect(pbt|pbt.oclAsType(ProbabilisticBranchTransition).branchProbability)->sum() > 0.9999 \n"
            + "	and self.branches_Branch->select(pbt|pbt.oclIsTypeOf(ProbabilisticBranchTransition))->collect(pbt|pbt.oclAsType(ProbabilisticBranchTransition).branchProbability)->sum() < 1.0001 \n"
            + "	else true \n" + "endif";
    /**
     * The cached OCL invariant for the '
     * {@link #AllProbabilisticBranchProbabilitiesMustSumUpTo1(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>All Probabilistic Branch Probabilities Must Sum Up To1</em>}' invariant operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #AllProbabilisticBranchProbabilitiesMustSumUpTo1(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint ALL_PROBABILISTIC_BRANCH_PROBABILITIES_MUST_SUM_UP_TO1__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean AllProbabilisticBranchProbabilitiesMustSumUpTo1(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (ALL_PROBABILISTIC_BRANCH_PROBABILITIES_MUST_SUM_UP_TO1__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(SeffPackage.Literals.BRANCH_ACTION);
            try {
                ALL_PROBABILISTIC_BRANCH_PROBABILITIES_MUST_SUM_UP_TO1__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                ALL_PROBABILISTIC_BRANCH_PROBABILITIES_MUST_SUM_UP_TO1__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV
                .createQuery(ALL_PROBABILISTIC_BRANCH_PROBABILITIES_MUST_SUM_UP_TO1__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, SeffValidator.DIAGNOSTIC_SOURCE,
                                SeffValidator.BRANCH_ACTION__ALL_PROBABILISTIC_BRANCH_PROBABILITIES_MUST_SUM_UP_TO1,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "AllProbabilisticBranchProbabilitiesMustSumUpTo1",
                                                EObjectValidator.getObjectLabel(this, context) }),
                        new Object[] { this }));
            }
            return false;
        }
        return true;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs) {
        switch (featureID) {
        case SeffPackage.BRANCH_ACTION__BRANCHES_BRANCH:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getBranches_Branch()).basicAdd(otherEnd,
                    msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs) {
        switch (featureID) {
        case SeffPackage.BRANCH_ACTION__BRANCHES_BRANCH:
            return ((InternalEList<?>) this.getBranches_Branch()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case SeffPackage.BRANCH_ACTION__BRANCHES_BRANCH:
            return this.getBranches_Branch();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case SeffPackage.BRANCH_ACTION__BRANCHES_BRANCH:
            this.getBranches_Branch().clear();
            this.getBranches_Branch().addAll((Collection<? extends AbstractBranchTransition>) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(final int featureID) {
        switch (featureID) {
        case SeffPackage.BRANCH_ACTION__BRANCHES_BRANCH:
            this.getBranches_Branch().clear();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(final int featureID) {
        switch (featureID) {
        case SeffPackage.BRANCH_ACTION__BRANCHES_BRANCH:
            return !this.getBranches_Branch().isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * The cached environment for evaluating OCL expressions. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    protected static final OCL EOCL_ENV = OCL.newInstance();

} // BranchActionImpl
