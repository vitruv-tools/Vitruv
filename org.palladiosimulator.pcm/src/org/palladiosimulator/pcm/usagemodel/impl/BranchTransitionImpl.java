/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Branch Transition</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.impl.BranchTransitionImpl#getBranchProbability
 * <em>Branch Probability</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.usagemodel.impl.BranchTransitionImpl#getBranch_BranchTransition
 * <em>Branch Branch Transition</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.usagemodel.impl.BranchTransitionImpl#getBranchedBehaviour_BranchTransition
 * <em>Branched Behaviour Branch Transition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BranchTransitionImpl extends CDOObjectImpl implements BranchTransition {

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
    protected BranchTransitionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return UsagemodelPackage.Literals.BRANCH_TRANSITION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected int eStaticFeatureCount() {
        return 0;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public double getBranchProbability() {
        return (Double) this.eDynamicGet(UsagemodelPackage.BRANCH_TRANSITION__BRANCH_PROBABILITY,
                UsagemodelPackage.Literals.BRANCH_TRANSITION__BRANCH_PROBABILITY, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBranchProbability(final double newBranchProbability) {
        this.eDynamicSet(UsagemodelPackage.BRANCH_TRANSITION__BRANCH_PROBABILITY,
                UsagemodelPackage.Literals.BRANCH_TRANSITION__BRANCH_PROBABILITY, newBranchProbability);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Branch getBranch_BranchTransition() {
        return (Branch) this.eDynamicGet(UsagemodelPackage.BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION,
                UsagemodelPackage.Literals.BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetBranch_BranchTransition(final Branch newBranch_BranchTransition,
            NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newBranch_BranchTransition,
                UsagemodelPackage.BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBranch_BranchTransition(final Branch newBranch_BranchTransition) {
        this.eDynamicSet(UsagemodelPackage.BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION,
                UsagemodelPackage.Literals.BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION, newBranch_BranchTransition);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ScenarioBehaviour getBranchedBehaviour_BranchTransition() {
        return (ScenarioBehaviour) this.eDynamicGet(
                UsagemodelPackage.BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION,
                UsagemodelPackage.Literals.BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetBranchedBehaviour_BranchTransition(
            final ScenarioBehaviour newBranchedBehaviour_BranchTransition, NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newBranchedBehaviour_BranchTransition,
                UsagemodelPackage.BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBranchedBehaviour_BranchTransition(final ScenarioBehaviour newBranchedBehaviour_BranchTransition) {
        this.eDynamicSet(UsagemodelPackage.BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION,
                UsagemodelPackage.Literals.BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION,
                newBranchedBehaviour_BranchTransition);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetBranch_BranchTransition((Branch) otherEnd, msgs);
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION:
            final ScenarioBehaviour branchedBehaviour_BranchTransition = this.getBranchedBehaviour_BranchTransition();
            if (branchedBehaviour_BranchTransition != null) {
                msgs = ((InternalEObject) branchedBehaviour_BranchTransition).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - UsagemodelPackage.BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION,
                        null, msgs);
            }
            return this.basicSetBranchedBehaviour_BranchTransition((ScenarioBehaviour) otherEnd, msgs);
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
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION:
            return this.basicSetBranch_BranchTransition(null, msgs);
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION:
            return this.basicSetBranchedBehaviour_BranchTransition(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(final NotificationChain msgs) {
        switch (this.eContainerFeatureID()) {
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION:
            return this.eInternalContainer().eInverseRemove(this, UsagemodelPackage.BRANCH__BRANCH_TRANSITIONS_BRANCH,
                    Branch.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCH_PROBABILITY:
            return this.getBranchProbability();
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION:
            return this.getBranch_BranchTransition();
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION:
            return this.getBranchedBehaviour_BranchTransition();
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
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCH_PROBABILITY:
            this.setBranchProbability((Double) newValue);
            return;
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION:
            this.setBranch_BranchTransition((Branch) newValue);
            return;
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION:
            this.setBranchedBehaviour_BranchTransition((ScenarioBehaviour) newValue);
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
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCH_PROBABILITY:
            this.setBranchProbability(BRANCH_PROBABILITY_EDEFAULT);
            return;
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION:
            this.setBranch_BranchTransition((Branch) null);
            return;
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION:
            this.setBranchedBehaviour_BranchTransition((ScenarioBehaviour) null);
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
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCH_PROBABILITY:
            return this.getBranchProbability() != BRANCH_PROBABILITY_EDEFAULT;
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCH_BRANCH_TRANSITION:
            return this.getBranch_BranchTransition() != null;
        case UsagemodelPackage.BRANCH_TRANSITION__BRANCHED_BEHAVIOUR_BRANCH_TRANSITION:
            return this.getBranchedBehaviour_BranchTransition() != null;
        }
        return super.eIsSet(featureID);
    }

} // BranchTransitionImpl
