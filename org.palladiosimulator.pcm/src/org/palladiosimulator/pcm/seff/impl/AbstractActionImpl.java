/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.SeffPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Abstract Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.AbstractActionImpl#getPredecessor_AbstractAction
 * <em>Predecessor Abstract Action</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.AbstractActionImpl#getSuccessor_AbstractAction
 * <em>Successor Abstract Action</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.AbstractActionImpl#getResourceDemandingBehaviour_AbstractAction
 * <em>Resource Demanding Behaviour Abstract Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractActionImpl extends EntityImpl implements AbstractAction {

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
    protected AbstractActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.ABSTRACT_ACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AbstractAction getPredecessor_AbstractAction() {
        return (AbstractAction) this.eDynamicGet(SeffPackage.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION,
                SeffPackage.Literals.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AbstractAction basicGetPredecessor_AbstractAction() {
        return (AbstractAction) this.eDynamicGet(SeffPackage.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION,
                SeffPackage.Literals.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetPredecessor_AbstractAction(final AbstractAction newPredecessor_AbstractAction,
            NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newPredecessor_AbstractAction,
                SeffPackage.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setPredecessor_AbstractAction(final AbstractAction newPredecessor_AbstractAction) {
        this.eDynamicSet(SeffPackage.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION,
                SeffPackage.Literals.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION, newPredecessor_AbstractAction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AbstractAction getSuccessor_AbstractAction() {
        return (AbstractAction) this.eDynamicGet(SeffPackage.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION,
                SeffPackage.Literals.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AbstractAction basicGetSuccessor_AbstractAction() {
        return (AbstractAction) this.eDynamicGet(SeffPackage.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION,
                SeffPackage.Literals.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetSuccessor_AbstractAction(final AbstractAction newSuccessor_AbstractAction,
            NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newSuccessor_AbstractAction,
                SeffPackage.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSuccessor_AbstractAction(final AbstractAction newSuccessor_AbstractAction) {
        this.eDynamicSet(SeffPackage.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION,
                SeffPackage.Literals.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION, newSuccessor_AbstractAction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceDemandingBehaviour getResourceDemandingBehaviour_AbstractAction() {
        return (ResourceDemandingBehaviour) this.eDynamicGet(
                SeffPackage.ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION,
                SeffPackage.Literals.ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetResourceDemandingBehaviour_AbstractAction(
            final ResourceDemandingBehaviour newResourceDemandingBehaviour_AbstractAction, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newResourceDemandingBehaviour_AbstractAction,
                SeffPackage.ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setResourceDemandingBehaviour_AbstractAction(
            final ResourceDemandingBehaviour newResourceDemandingBehaviour_AbstractAction) {
        this.eDynamicSet(SeffPackage.ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION,
                SeffPackage.Literals.ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION,
                newResourceDemandingBehaviour_AbstractAction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case SeffPackage.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION:
            final AbstractAction predecessor_AbstractAction = this.basicGetPredecessor_AbstractAction();
            if (predecessor_AbstractAction != null) {
                msgs = ((InternalEObject) predecessor_AbstractAction).eInverseRemove(this,
                        SeffPackage.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION, AbstractAction.class, msgs);
            }
            return this.basicSetPredecessor_AbstractAction((AbstractAction) otherEnd, msgs);
        case SeffPackage.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION:
            final AbstractAction successor_AbstractAction = this.basicGetSuccessor_AbstractAction();
            if (successor_AbstractAction != null) {
                msgs = ((InternalEObject) successor_AbstractAction).eInverseRemove(this,
                        SeffPackage.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION, AbstractAction.class, msgs);
            }
            return this.basicSetSuccessor_AbstractAction((AbstractAction) otherEnd, msgs);
        case SeffPackage.ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetResourceDemandingBehaviour_AbstractAction((ResourceDemandingBehaviour) otherEnd, msgs);
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
        case SeffPackage.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION:
            return this.basicSetPredecessor_AbstractAction(null, msgs);
        case SeffPackage.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION:
            return this.basicSetSuccessor_AbstractAction(null, msgs);
        case SeffPackage.ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION:
            return this.basicSetResourceDemandingBehaviour_AbstractAction(null, msgs);
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
        case SeffPackage.ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION:
            return this.eInternalContainer().eInverseRemove(this,
                    SeffPackage.RESOURCE_DEMANDING_BEHAVIOUR__STEPS_BEHAVIOUR, ResourceDemandingBehaviour.class, msgs);
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
        case SeffPackage.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION:
            if (resolve) {
                return this.getPredecessor_AbstractAction();
            }
            return this.basicGetPredecessor_AbstractAction();
        case SeffPackage.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION:
            if (resolve) {
                return this.getSuccessor_AbstractAction();
            }
            return this.basicGetSuccessor_AbstractAction();
        case SeffPackage.ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION:
            return this.getResourceDemandingBehaviour_AbstractAction();
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
        case SeffPackage.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION:
            this.setPredecessor_AbstractAction((AbstractAction) newValue);
            return;
        case SeffPackage.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION:
            this.setSuccessor_AbstractAction((AbstractAction) newValue);
            return;
        case SeffPackage.ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION:
            this.setResourceDemandingBehaviour_AbstractAction((ResourceDemandingBehaviour) newValue);
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
        case SeffPackage.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION:
            this.setPredecessor_AbstractAction((AbstractAction) null);
            return;
        case SeffPackage.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION:
            this.setSuccessor_AbstractAction((AbstractAction) null);
            return;
        case SeffPackage.ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION:
            this.setResourceDemandingBehaviour_AbstractAction((ResourceDemandingBehaviour) null);
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
        case SeffPackage.ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION:
            return this.basicGetPredecessor_AbstractAction() != null;
        case SeffPackage.ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION:
            return this.basicGetSuccessor_AbstractAction() != null;
        case SeffPackage.ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION:
            return this.getResourceDemandingBehaviour_AbstractAction() != null;
        }
        return super.eIsSet(featureID);
    }

} // AbstractActionImpl
