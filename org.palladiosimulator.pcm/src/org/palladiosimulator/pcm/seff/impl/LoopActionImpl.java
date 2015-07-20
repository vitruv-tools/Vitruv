/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.SeffPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Loop Action</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.LoopActionImpl#getIterationCount_LoopAction
 * <em>Iteration Count Loop Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LoopActionImpl extends AbstractLoopActionImpl implements LoopAction {

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
    protected LoopActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.LOOP_ACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public PCMRandomVariable getIterationCount_LoopAction() {
        return (PCMRandomVariable) this.eDynamicGet(SeffPackage.LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION,
                SeffPackage.Literals.LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetIterationCount_LoopAction(final PCMRandomVariable newIterationCount_LoopAction,
            NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newIterationCount_LoopAction,
                SeffPackage.LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setIterationCount_LoopAction(final PCMRandomVariable newIterationCount_LoopAction) {
        this.eDynamicSet(SeffPackage.LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION,
                SeffPackage.Literals.LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION, newIterationCount_LoopAction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case SeffPackage.LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION:
            final PCMRandomVariable iterationCount_LoopAction = this.getIterationCount_LoopAction();
            if (iterationCount_LoopAction != null) {
                msgs = ((InternalEObject) iterationCount_LoopAction).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - SeffPackage.LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION, null, msgs);
            }
            return this.basicSetIterationCount_LoopAction((PCMRandomVariable) otherEnd, msgs);
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
        case SeffPackage.LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION:
            return this.basicSetIterationCount_LoopAction(null, msgs);
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
        case SeffPackage.LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION:
            return this.getIterationCount_LoopAction();
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
        case SeffPackage.LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION:
            this.setIterationCount_LoopAction((PCMRandomVariable) newValue);
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
        case SeffPackage.LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION:
            this.setIterationCount_LoopAction((PCMRandomVariable) null);
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
        case SeffPackage.LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION:
            return this.getIterationCount_LoopAction() != null;
        }
        return super.eIsSet(featureID);
    }

} // LoopActionImpl
