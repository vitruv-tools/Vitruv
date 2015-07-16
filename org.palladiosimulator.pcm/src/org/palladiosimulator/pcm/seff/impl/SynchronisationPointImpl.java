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
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.seff.ForkAction;
import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.SynchronisationPoint;

import de.uka.ipd.sdq.identifier.impl.IdentifierImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Synchronisation Point</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.SynchronisationPointImpl#getOutputParameterUsage_SynchronisationPoint
 * <em>Output Parameter Usage Synchronisation Point</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.SynchronisationPointImpl#getForkAction_SynchronisationPoint
 * <em>Fork Action Synchronisation Point</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.SynchronisationPointImpl#getSynchronousForkedBehaviours_SynchronisationPoint
 * <em>Synchronous Forked Behaviours Synchronisation Point</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SynchronisationPointImpl extends IdentifierImpl implements SynchronisationPoint {

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
    protected SynchronisationPointImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.SYNCHRONISATION_POINT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<VariableUsage> getOutputParameterUsage_SynchronisationPoint() {
        return (EList<VariableUsage>) this.eDynamicGet(
                SeffPackage.SYNCHRONISATION_POINT__OUTPUT_PARAMETER_USAGE_SYNCHRONISATION_POINT,
                SeffPackage.Literals.SYNCHRONISATION_POINT__OUTPUT_PARAMETER_USAGE_SYNCHRONISATION_POINT, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ForkAction getForkAction_SynchronisationPoint() {
        return (ForkAction) this.eDynamicGet(SeffPackage.SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT,
                SeffPackage.Literals.SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetForkAction_SynchronisationPoint(
            final ForkAction newForkAction_SynchronisationPoint, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newForkAction_SynchronisationPoint,
                SeffPackage.SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setForkAction_SynchronisationPoint(final ForkAction newForkAction_SynchronisationPoint) {
        this.eDynamicSet(SeffPackage.SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT,
                SeffPackage.Literals.SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT,
                newForkAction_SynchronisationPoint);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<ForkedBehaviour> getSynchronousForkedBehaviours_SynchronisationPoint() {
        return (EList<ForkedBehaviour>) this.eDynamicGet(
                SeffPackage.SYNCHRONISATION_POINT__SYNCHRONOUS_FORKED_BEHAVIOURS_SYNCHRONISATION_POINT,
                SeffPackage.Literals.SYNCHRONISATION_POINT__SYNCHRONOUS_FORKED_BEHAVIOURS_SYNCHRONISATION_POINT, true,
                true);
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
        case SeffPackage.SYNCHRONISATION_POINT__OUTPUT_PARAMETER_USAGE_SYNCHRONISATION_POINT:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this
                    .getOutputParameterUsage_SynchronisationPoint()).basicAdd(otherEnd, msgs);
        case SeffPackage.SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetForkAction_SynchronisationPoint((ForkAction) otherEnd, msgs);
        case SeffPackage.SYNCHRONISATION_POINT__SYNCHRONOUS_FORKED_BEHAVIOURS_SYNCHRONISATION_POINT:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this
                    .getSynchronousForkedBehaviours_SynchronisationPoint()).basicAdd(otherEnd, msgs);
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
        case SeffPackage.SYNCHRONISATION_POINT__OUTPUT_PARAMETER_USAGE_SYNCHRONISATION_POINT:
            return ((InternalEList<?>) this.getOutputParameterUsage_SynchronisationPoint()).basicRemove(otherEnd, msgs);
        case SeffPackage.SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT:
            return this.basicSetForkAction_SynchronisationPoint(null, msgs);
        case SeffPackage.SYNCHRONISATION_POINT__SYNCHRONOUS_FORKED_BEHAVIOURS_SYNCHRONISATION_POINT:
            return ((InternalEList<?>) this.getSynchronousForkedBehaviours_SynchronisationPoint()).basicRemove(otherEnd,
                    msgs);
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
        case SeffPackage.SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT:
            return this.eInternalContainer().eInverseRemove(this,
                    SeffPackage.FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION, ForkAction.class, msgs);
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
        case SeffPackage.SYNCHRONISATION_POINT__OUTPUT_PARAMETER_USAGE_SYNCHRONISATION_POINT:
            return this.getOutputParameterUsage_SynchronisationPoint();
        case SeffPackage.SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT:
            return this.getForkAction_SynchronisationPoint();
        case SeffPackage.SYNCHRONISATION_POINT__SYNCHRONOUS_FORKED_BEHAVIOURS_SYNCHRONISATION_POINT:
            return this.getSynchronousForkedBehaviours_SynchronisationPoint();
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
        case SeffPackage.SYNCHRONISATION_POINT__OUTPUT_PARAMETER_USAGE_SYNCHRONISATION_POINT:
            this.getOutputParameterUsage_SynchronisationPoint().clear();
            this.getOutputParameterUsage_SynchronisationPoint().addAll((Collection<? extends VariableUsage>) newValue);
            return;
        case SeffPackage.SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT:
            this.setForkAction_SynchronisationPoint((ForkAction) newValue);
            return;
        case SeffPackage.SYNCHRONISATION_POINT__SYNCHRONOUS_FORKED_BEHAVIOURS_SYNCHRONISATION_POINT:
            this.getSynchronousForkedBehaviours_SynchronisationPoint().clear();
            this.getSynchronousForkedBehaviours_SynchronisationPoint()
                    .addAll((Collection<? extends ForkedBehaviour>) newValue);
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
        case SeffPackage.SYNCHRONISATION_POINT__OUTPUT_PARAMETER_USAGE_SYNCHRONISATION_POINT:
            this.getOutputParameterUsage_SynchronisationPoint().clear();
            return;
        case SeffPackage.SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT:
            this.setForkAction_SynchronisationPoint((ForkAction) null);
            return;
        case SeffPackage.SYNCHRONISATION_POINT__SYNCHRONOUS_FORKED_BEHAVIOURS_SYNCHRONISATION_POINT:
            this.getSynchronousForkedBehaviours_SynchronisationPoint().clear();
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
        case SeffPackage.SYNCHRONISATION_POINT__OUTPUT_PARAMETER_USAGE_SYNCHRONISATION_POINT:
            return !this.getOutputParameterUsage_SynchronisationPoint().isEmpty();
        case SeffPackage.SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT:
            return this.getForkAction_SynchronisationPoint() != null;
        case SeffPackage.SYNCHRONISATION_POINT__SYNCHRONOUS_FORKED_BEHAVIOURS_SYNCHRONISATION_POINT:
            return !this.getSynchronousForkedBehaviours_SynchronisationPoint().isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // SynchronisationPointImpl
