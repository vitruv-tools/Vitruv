/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.seff.ForkAction;
import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.SynchronisationPoint;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Forked Behaviour</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.ForkedBehaviourImpl#getSynchronisationPoint_ForkedBehaviour
 * <em>Synchronisation Point Forked Behaviour</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.ForkedBehaviourImpl#getForkAction_ForkedBehaivour
 * <em>Fork Action Forked Behaivour</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ForkedBehaviourImpl extends ResourceDemandingBehaviourImpl implements ForkedBehaviour {

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
    protected ForkedBehaviourImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.FORKED_BEHAVIOUR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SynchronisationPoint getSynchronisationPoint_ForkedBehaviour() {
        return (SynchronisationPoint) this.eDynamicGet(
                SeffPackage.FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR,
                SeffPackage.Literals.FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetSynchronisationPoint_ForkedBehaviour(
            final SynchronisationPoint newSynchronisationPoint_ForkedBehaviour, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newSynchronisationPoint_ForkedBehaviour,
                SeffPackage.FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSynchronisationPoint_ForkedBehaviour(
            final SynchronisationPoint newSynchronisationPoint_ForkedBehaviour) {
        this.eDynamicSet(SeffPackage.FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR,
                SeffPackage.Literals.FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR,
                newSynchronisationPoint_ForkedBehaviour);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ForkAction getForkAction_ForkedBehaivour() {
        return (ForkAction) this.eDynamicGet(SeffPackage.FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR,
                SeffPackage.Literals.FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetForkAction_ForkedBehaivour(final ForkAction newForkAction_ForkedBehaivour,
            NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newForkAction_ForkedBehaivour,
                SeffPackage.FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setForkAction_ForkedBehaivour(final ForkAction newForkAction_ForkedBehaivour) {
        this.eDynamicSet(SeffPackage.FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR,
                SeffPackage.Literals.FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR, newForkAction_ForkedBehaivour);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case SeffPackage.FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetSynchronisationPoint_ForkedBehaviour((SynchronisationPoint) otherEnd, msgs);
        case SeffPackage.FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetForkAction_ForkedBehaivour((ForkAction) otherEnd, msgs);
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
        case SeffPackage.FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR:
            return this.basicSetSynchronisationPoint_ForkedBehaviour(null, msgs);
        case SeffPackage.FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR:
            return this.basicSetForkAction_ForkedBehaivour(null, msgs);
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
        case SeffPackage.FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR:
            return this.eInternalContainer().eInverseRemove(this,
                    SeffPackage.SYNCHRONISATION_POINT__SYNCHRONOUS_FORKED_BEHAVIOURS_SYNCHRONISATION_POINT,
                    SynchronisationPoint.class, msgs);
        case SeffPackage.FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR:
            return this.eInternalContainer().eInverseRemove(this,
                    SeffPackage.FORK_ACTION__ASYNCHRONOUS_FORKED_BEHAVIOURS_FORK_ACTION, ForkAction.class, msgs);
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
        case SeffPackage.FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR:
            return this.getSynchronisationPoint_ForkedBehaviour();
        case SeffPackage.FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR:
            return this.getForkAction_ForkedBehaivour();
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
        case SeffPackage.FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR:
            this.setSynchronisationPoint_ForkedBehaviour((SynchronisationPoint) newValue);
            return;
        case SeffPackage.FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR:
            this.setForkAction_ForkedBehaivour((ForkAction) newValue);
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
        case SeffPackage.FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR:
            this.setSynchronisationPoint_ForkedBehaviour((SynchronisationPoint) null);
            return;
        case SeffPackage.FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR:
            this.setForkAction_ForkedBehaivour((ForkAction) null);
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
        case SeffPackage.FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR:
            return this.getSynchronisationPoint_ForkedBehaviour() != null;
        case SeffPackage.FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR:
            return this.getForkAction_ForkedBehaivour() != null;
        }
        return super.eIsSet(featureID);
    }

} // ForkedBehaviourImpl
