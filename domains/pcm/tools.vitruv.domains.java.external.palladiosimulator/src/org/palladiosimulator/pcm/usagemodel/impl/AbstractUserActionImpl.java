/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Abstract User Action</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.impl.AbstractUserActionImpl#getSuccessor
 * <em>Successor</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.impl.AbstractUserActionImpl#getPredecessor
 * <em>Predecessor</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.usagemodel.impl.AbstractUserActionImpl#getScenarioBehaviour_AbstractUserAction
 * <em>Scenario Behaviour Abstract User Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractUserActionImpl extends EntityImpl implements AbstractUserAction {

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
    protected AbstractUserActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return UsagemodelPackage.Literals.ABSTRACT_USER_ACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AbstractUserAction getSuccessor() {
        return (AbstractUserAction) this.eDynamicGet(UsagemodelPackage.ABSTRACT_USER_ACTION__SUCCESSOR,
                UsagemodelPackage.Literals.ABSTRACT_USER_ACTION__SUCCESSOR, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AbstractUserAction basicGetSuccessor() {
        return (AbstractUserAction) this.eDynamicGet(UsagemodelPackage.ABSTRACT_USER_ACTION__SUCCESSOR,
                UsagemodelPackage.Literals.ABSTRACT_USER_ACTION__SUCCESSOR, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetSuccessor(final AbstractUserAction newSuccessor, NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newSuccessor,
                UsagemodelPackage.ABSTRACT_USER_ACTION__SUCCESSOR, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSuccessor(final AbstractUserAction newSuccessor) {
        this.eDynamicSet(UsagemodelPackage.ABSTRACT_USER_ACTION__SUCCESSOR,
                UsagemodelPackage.Literals.ABSTRACT_USER_ACTION__SUCCESSOR, newSuccessor);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AbstractUserAction getPredecessor() {
        return (AbstractUserAction) this.eDynamicGet(UsagemodelPackage.ABSTRACT_USER_ACTION__PREDECESSOR,
                UsagemodelPackage.Literals.ABSTRACT_USER_ACTION__PREDECESSOR, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AbstractUserAction basicGetPredecessor() {
        return (AbstractUserAction) this.eDynamicGet(UsagemodelPackage.ABSTRACT_USER_ACTION__PREDECESSOR,
                UsagemodelPackage.Literals.ABSTRACT_USER_ACTION__PREDECESSOR, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetPredecessor(final AbstractUserAction newPredecessor, NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newPredecessor,
                UsagemodelPackage.ABSTRACT_USER_ACTION__PREDECESSOR, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setPredecessor(final AbstractUserAction newPredecessor) {
        this.eDynamicSet(UsagemodelPackage.ABSTRACT_USER_ACTION__PREDECESSOR,
                UsagemodelPackage.Literals.ABSTRACT_USER_ACTION__PREDECESSOR, newPredecessor);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ScenarioBehaviour getScenarioBehaviour_AbstractUserAction() {
        return (ScenarioBehaviour) this.eDynamicGet(
                UsagemodelPackage.ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION,
                UsagemodelPackage.Literals.ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetScenarioBehaviour_AbstractUserAction(
            final ScenarioBehaviour newScenarioBehaviour_AbstractUserAction, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newScenarioBehaviour_AbstractUserAction,
                UsagemodelPackage.ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setScenarioBehaviour_AbstractUserAction(
            final ScenarioBehaviour newScenarioBehaviour_AbstractUserAction) {
        this.eDynamicSet(UsagemodelPackage.ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION,
                UsagemodelPackage.Literals.ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION,
                newScenarioBehaviour_AbstractUserAction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SUCCESSOR:
            final AbstractUserAction successor = this.basicGetSuccessor();
            if (successor != null) {
                msgs = ((InternalEObject) successor).eInverseRemove(this,
                        UsagemodelPackage.ABSTRACT_USER_ACTION__PREDECESSOR, AbstractUserAction.class, msgs);
            }
            return this.basicSetSuccessor((AbstractUserAction) otherEnd, msgs);
        case UsagemodelPackage.ABSTRACT_USER_ACTION__PREDECESSOR:
            final AbstractUserAction predecessor = this.basicGetPredecessor();
            if (predecessor != null) {
                msgs = ((InternalEObject) predecessor).eInverseRemove(this,
                        UsagemodelPackage.ABSTRACT_USER_ACTION__SUCCESSOR, AbstractUserAction.class, msgs);
            }
            return this.basicSetPredecessor((AbstractUserAction) otherEnd, msgs);
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetScenarioBehaviour_AbstractUserAction((ScenarioBehaviour) otherEnd, msgs);
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
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SUCCESSOR:
            return this.basicSetSuccessor(null, msgs);
        case UsagemodelPackage.ABSTRACT_USER_ACTION__PREDECESSOR:
            return this.basicSetPredecessor(null, msgs);
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION:
            return this.basicSetScenarioBehaviour_AbstractUserAction(null, msgs);
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
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION:
            return this.eInternalContainer().eInverseRemove(this,
                    UsagemodelPackage.SCENARIO_BEHAVIOUR__ACTIONS_SCENARIO_BEHAVIOUR, ScenarioBehaviour.class, msgs);
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
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SUCCESSOR:
            if (resolve) {
                return this.getSuccessor();
            }
            return this.basicGetSuccessor();
        case UsagemodelPackage.ABSTRACT_USER_ACTION__PREDECESSOR:
            if (resolve) {
                return this.getPredecessor();
            }
            return this.basicGetPredecessor();
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION:
            return this.getScenarioBehaviour_AbstractUserAction();
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
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SUCCESSOR:
            this.setSuccessor((AbstractUserAction) newValue);
            return;
        case UsagemodelPackage.ABSTRACT_USER_ACTION__PREDECESSOR:
            this.setPredecessor((AbstractUserAction) newValue);
            return;
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION:
            this.setScenarioBehaviour_AbstractUserAction((ScenarioBehaviour) newValue);
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
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SUCCESSOR:
            this.setSuccessor((AbstractUserAction) null);
            return;
        case UsagemodelPackage.ABSTRACT_USER_ACTION__PREDECESSOR:
            this.setPredecessor((AbstractUserAction) null);
            return;
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION:
            this.setScenarioBehaviour_AbstractUserAction((ScenarioBehaviour) null);
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
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SUCCESSOR:
            return this.basicGetSuccessor() != null;
        case UsagemodelPackage.ABSTRACT_USER_ACTION__PREDECESSOR:
            return this.basicGetPredecessor() != null;
        case UsagemodelPackage.ABSTRACT_USER_ACTION__SCENARIO_BEHAVIOUR_ABSTRACT_USER_ACTION:
            return this.getScenarioBehaviour_AbstractUserAction() != null;
        }
        return super.eIsSet(featureID);
    }

} // AbstractUserActionImpl
