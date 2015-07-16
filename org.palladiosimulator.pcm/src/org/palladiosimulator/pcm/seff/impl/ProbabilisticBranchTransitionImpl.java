/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

import org.eclipse.emf.ecore.EClass;
import org.palladiosimulator.pcm.seff.ProbabilisticBranchTransition;
import org.palladiosimulator.pcm.seff.SeffPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Probabilistic Branch Transition</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.ProbabilisticBranchTransitionImpl#getBranchProbability
 * <em>Branch Probability</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProbabilisticBranchTransitionImpl extends AbstractBranchTransitionImpl
        implements ProbabilisticBranchTransition {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The default value of the '{@link #getBranchProbability() <em>Branch Probability</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBranchProbability()
     * @generated
     * @ordered
     */
    protected static final double BRANCH_PROBABILITY_EDEFAULT = 0.0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ProbabilisticBranchTransitionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.PROBABILISTIC_BRANCH_TRANSITION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public double getBranchProbability() {
        return (Double) this.eDynamicGet(SeffPackage.PROBABILISTIC_BRANCH_TRANSITION__BRANCH_PROBABILITY,
                SeffPackage.Literals.PROBABILISTIC_BRANCH_TRANSITION__BRANCH_PROBABILITY, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBranchProbability(final double newBranchProbability) {
        this.eDynamicSet(SeffPackage.PROBABILISTIC_BRANCH_TRANSITION__BRANCH_PROBABILITY,
                SeffPackage.Literals.PROBABILISTIC_BRANCH_TRANSITION__BRANCH_PROBABILITY, newBranchProbability);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case SeffPackage.PROBABILISTIC_BRANCH_TRANSITION__BRANCH_PROBABILITY:
            return this.getBranchProbability();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case SeffPackage.PROBABILISTIC_BRANCH_TRANSITION__BRANCH_PROBABILITY:
            this.setBranchProbability((Double) newValue);
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
        case SeffPackage.PROBABILISTIC_BRANCH_TRANSITION__BRANCH_PROBABILITY:
            this.setBranchProbability(BRANCH_PROBABILITY_EDEFAULT);
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
        case SeffPackage.PROBABILISTIC_BRANCH_TRANSITION__BRANCH_PROBABILITY:
            return this.getBranchProbability() != BRANCH_PROBABILITY_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

} // ProbabilisticBranchTransitionImpl
