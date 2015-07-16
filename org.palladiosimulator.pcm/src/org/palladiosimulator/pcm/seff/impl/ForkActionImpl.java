/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.palladiosimulator.pcm.seff.ForkAction;
import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.SynchronisationPoint;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Fork Action</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.ForkActionImpl#getAsynchronousForkedBehaviours_ForkAction
 * <em>Asynchronous Forked Behaviours Fork Action</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.ForkActionImpl#getSynchronisingBehaviours_ForkAction
 * <em>Synchronising Behaviours Fork Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ForkActionImpl extends AbstractInternalControlFlowActionImpl implements ForkAction {

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
    protected ForkActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.FORK_ACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<ForkedBehaviour> getAsynchronousForkedBehaviours_ForkAction() {
        return (EList<ForkedBehaviour>) this.eDynamicGet(
                SeffPackage.FORK_ACTION__ASYNCHRONOUS_FORKED_BEHAVIOURS_FORK_ACTION,
                SeffPackage.Literals.FORK_ACTION__ASYNCHRONOUS_FORKED_BEHAVIOURS_FORK_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SynchronisationPoint getSynchronisingBehaviours_ForkAction() {
        return (SynchronisationPoint) this.eDynamicGet(SeffPackage.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION,
                SeffPackage.Literals.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetSynchronisingBehaviours_ForkAction(
            final SynchronisationPoint newSynchronisingBehaviours_ForkAction, NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newSynchronisingBehaviours_ForkAction,
                SeffPackage.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSynchronisingBehaviours_ForkAction(
            final SynchronisationPoint newSynchronisingBehaviours_ForkAction) {
        this.eDynamicSet(SeffPackage.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION,
                SeffPackage.Literals.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION,
                newSynchronisingBehaviours_ForkAction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case SeffPackage.FORK_ACTION__ASYNCHRONOUS_FORKED_BEHAVIOURS_FORK_ACTION:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this
                    .getAsynchronousForkedBehaviours_ForkAction()).basicAdd(otherEnd, msgs);
        case SeffPackage.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION:
            final SynchronisationPoint synchronisingBehaviours_ForkAction = this
                    .getSynchronisingBehaviours_ForkAction();
            if (synchronisingBehaviours_ForkAction != null) {
                msgs = ((InternalEObject) synchronisingBehaviours_ForkAction).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - SeffPackage.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION, null,
                        msgs);
            }
            return this.basicSetSynchronisingBehaviours_ForkAction((SynchronisationPoint) otherEnd, msgs);
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
        case SeffPackage.FORK_ACTION__ASYNCHRONOUS_FORKED_BEHAVIOURS_FORK_ACTION:
            return ((InternalEList<?>) this.getAsynchronousForkedBehaviours_ForkAction()).basicRemove(otherEnd, msgs);
        case SeffPackage.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION:
            return this.basicSetSynchronisingBehaviours_ForkAction(null, msgs);
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
        case SeffPackage.FORK_ACTION__ASYNCHRONOUS_FORKED_BEHAVIOURS_FORK_ACTION:
            return this.getAsynchronousForkedBehaviours_ForkAction();
        case SeffPackage.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION:
            return this.getSynchronisingBehaviours_ForkAction();
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
        case SeffPackage.FORK_ACTION__ASYNCHRONOUS_FORKED_BEHAVIOURS_FORK_ACTION:
            this.getAsynchronousForkedBehaviours_ForkAction().clear();
            this.getAsynchronousForkedBehaviours_ForkAction().addAll((Collection<? extends ForkedBehaviour>) newValue);
            return;
        case SeffPackage.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION:
            this.setSynchronisingBehaviours_ForkAction((SynchronisationPoint) newValue);
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
        case SeffPackage.FORK_ACTION__ASYNCHRONOUS_FORKED_BEHAVIOURS_FORK_ACTION:
            this.getAsynchronousForkedBehaviours_ForkAction().clear();
            return;
        case SeffPackage.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION:
            this.setSynchronisingBehaviours_ForkAction((SynchronisationPoint) null);
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
        case SeffPackage.FORK_ACTION__ASYNCHRONOUS_FORKED_BEHAVIOURS_FORK_ACTION:
            return !this.getAsynchronousForkedBehaviours_ForkAction().isEmpty();
        case SeffPackage.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION:
            return this.getSynchronisingBehaviours_ForkAction() != null;
        }
        return super.eIsSet(featureID);
    }

} // ForkActionImpl
