/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.seff.AbstractLoopAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.SeffPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Abstract Loop Action</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.AbstractLoopActionImpl#getBodyBehaviour_Loop
 * <em>Body Behaviour Loop</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractLoopActionImpl extends AbstractInternalControlFlowActionImpl
        implements AbstractLoopAction {

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
    protected AbstractLoopActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.ABSTRACT_LOOP_ACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceDemandingBehaviour getBodyBehaviour_Loop() {
        return (ResourceDemandingBehaviour) this.eDynamicGet(SeffPackage.ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP,
                SeffPackage.Literals.ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetBodyBehaviour_Loop(final ResourceDemandingBehaviour newBodyBehaviour_Loop,
            NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newBodyBehaviour_Loop,
                SeffPackage.ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setBodyBehaviour_Loop(final ResourceDemandingBehaviour newBodyBehaviour_Loop) {
        this.eDynamicSet(SeffPackage.ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP,
                SeffPackage.Literals.ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP, newBodyBehaviour_Loop);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case SeffPackage.ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP:
            final ResourceDemandingBehaviour bodyBehaviour_Loop = this.getBodyBehaviour_Loop();
            if (bodyBehaviour_Loop != null) {
                msgs = ((InternalEObject) bodyBehaviour_Loop).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - SeffPackage.ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP, null, msgs);
            }
            return this.basicSetBodyBehaviour_Loop((ResourceDemandingBehaviour) otherEnd, msgs);
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
        case SeffPackage.ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP:
            return this.basicSetBodyBehaviour_Loop(null, msgs);
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
        case SeffPackage.ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP:
            return this.getBodyBehaviour_Loop();
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
        case SeffPackage.ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP:
            this.setBodyBehaviour_Loop((ResourceDemandingBehaviour) newValue);
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
        case SeffPackage.ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP:
            this.setBodyBehaviour_Loop((ResourceDemandingBehaviour) null);
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
        case SeffPackage.ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP:
            return this.getBodyBehaviour_Loop() != null;
        }
        return super.eIsSet(featureID);
    }

} // AbstractLoopActionImpl
