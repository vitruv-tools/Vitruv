/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Loop</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.impl.LoopImpl#getLoopIteration_Loop
 * <em>Loop Iteration Loop</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.impl.LoopImpl#getBodyBehaviour_Loop
 * <em>Body Behaviour Loop</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LoopImpl extends AbstractUserActionImpl implements Loop {

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
    protected LoopImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return UsagemodelPackage.Literals.LOOP;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public PCMRandomVariable getLoopIteration_Loop() {
        return (PCMRandomVariable) this.eDynamicGet(UsagemodelPackage.LOOP__LOOP_ITERATION_LOOP,
                UsagemodelPackage.Literals.LOOP__LOOP_ITERATION_LOOP, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetLoopIteration_Loop(final PCMRandomVariable newLoopIteration_Loop,
            NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newLoopIteration_Loop,
                UsagemodelPackage.LOOP__LOOP_ITERATION_LOOP, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setLoopIteration_Loop(final PCMRandomVariable newLoopIteration_Loop) {
        this.eDynamicSet(UsagemodelPackage.LOOP__LOOP_ITERATION_LOOP,
                UsagemodelPackage.Literals.LOOP__LOOP_ITERATION_LOOP, newLoopIteration_Loop);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ScenarioBehaviour getBodyBehaviour_Loop() {
        return (ScenarioBehaviour) this.eDynamicGet(UsagemodelPackage.LOOP__BODY_BEHAVIOUR_LOOP,
                UsagemodelPackage.Literals.LOOP__BODY_BEHAVIOUR_LOOP, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetBodyBehaviour_Loop(final ScenarioBehaviour newBodyBehaviour_Loop,
            NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newBodyBehaviour_Loop,
                UsagemodelPackage.LOOP__BODY_BEHAVIOUR_LOOP, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setBodyBehaviour_Loop(final ScenarioBehaviour newBodyBehaviour_Loop) {
        this.eDynamicSet(UsagemodelPackage.LOOP__BODY_BEHAVIOUR_LOOP,
                UsagemodelPackage.Literals.LOOP__BODY_BEHAVIOUR_LOOP, newBodyBehaviour_Loop);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case UsagemodelPackage.LOOP__LOOP_ITERATION_LOOP:
            final PCMRandomVariable loopIteration_Loop = this.getLoopIteration_Loop();
            if (loopIteration_Loop != null) {
                msgs = ((InternalEObject) loopIteration_Loop).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - UsagemodelPackage.LOOP__LOOP_ITERATION_LOOP, null, msgs);
            }
            return this.basicSetLoopIteration_Loop((PCMRandomVariable) otherEnd, msgs);
        case UsagemodelPackage.LOOP__BODY_BEHAVIOUR_LOOP:
            final ScenarioBehaviour bodyBehaviour_Loop = this.getBodyBehaviour_Loop();
            if (bodyBehaviour_Loop != null) {
                msgs = ((InternalEObject) bodyBehaviour_Loop).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - UsagemodelPackage.LOOP__BODY_BEHAVIOUR_LOOP, null, msgs);
            }
            return this.basicSetBodyBehaviour_Loop((ScenarioBehaviour) otherEnd, msgs);
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
        case UsagemodelPackage.LOOP__LOOP_ITERATION_LOOP:
            return this.basicSetLoopIteration_Loop(null, msgs);
        case UsagemodelPackage.LOOP__BODY_BEHAVIOUR_LOOP:
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
        case UsagemodelPackage.LOOP__LOOP_ITERATION_LOOP:
            return this.getLoopIteration_Loop();
        case UsagemodelPackage.LOOP__BODY_BEHAVIOUR_LOOP:
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
        case UsagemodelPackage.LOOP__LOOP_ITERATION_LOOP:
            this.setLoopIteration_Loop((PCMRandomVariable) newValue);
            return;
        case UsagemodelPackage.LOOP__BODY_BEHAVIOUR_LOOP:
            this.setBodyBehaviour_Loop((ScenarioBehaviour) newValue);
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
        case UsagemodelPackage.LOOP__LOOP_ITERATION_LOOP:
            this.setLoopIteration_Loop((PCMRandomVariable) null);
            return;
        case UsagemodelPackage.LOOP__BODY_BEHAVIOUR_LOOP:
            this.setBodyBehaviour_Loop((ScenarioBehaviour) null);
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
        case UsagemodelPackage.LOOP__LOOP_ITERATION_LOOP:
            return this.getLoopIteration_Loop() != null;
        case UsagemodelPackage.LOOP__BODY_BEHAVIOUR_LOOP:
            return this.getBodyBehaviour_Loop() != null;
        }
        return super.eIsSet(featureID);
    }

} // LoopImpl
