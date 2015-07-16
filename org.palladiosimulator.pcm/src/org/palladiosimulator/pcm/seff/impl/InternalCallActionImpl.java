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
import org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction;
import org.palladiosimulator.pcm.seff.InternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.ResourceCall;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Internal Call Action</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.InternalCallActionImpl#getResourceDemand_Action
 * <em>Resource Demand Action</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.InternalCallActionImpl#getInfrastructureCall__Action
 * <em>Infrastructure Call Action</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.InternalCallActionImpl#getResourceCall__Action
 * <em>Resource Call Action</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.InternalCallActionImpl#getCalledResourceDemandingInternalBehaviour
 * <em>Called Resource Demanding Internal Behaviour</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InternalCallActionImpl extends CallActionImpl implements InternalCallAction {

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
    protected InternalCallActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.INTERNAL_CALL_ACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<ParametricResourceDemand> getResourceDemand_Action() {
        return (EList<ParametricResourceDemand>) this.eDynamicGet(
                SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_DEMAND_ACTION,
                SeffPackage.Literals.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__RESOURCE_DEMAND_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<InfrastructureCall> getInfrastructureCall__Action() {
        return (EList<InfrastructureCall>) this.eDynamicGet(
                SeffPackage.INTERNAL_CALL_ACTION__INFRASTRUCTURE_CALL_ACTION,
                SeffPackage.Literals.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__INFRASTRUCTURE_CALL_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<ResourceCall> getResourceCall__Action() {
        return (EList<ResourceCall>) this.eDynamicGet(SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_CALL_ACTION,
                SeffPackage.Literals.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__RESOURCE_CALL_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceDemandingInternalBehaviour getCalledResourceDemandingInternalBehaviour() {
        return (ResourceDemandingInternalBehaviour) this.eDynamicGet(
                SeffPackage.INTERNAL_CALL_ACTION__CALLED_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR,
                SeffPackage.Literals.INTERNAL_CALL_ACTION__CALLED_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ResourceDemandingInternalBehaviour basicGetCalledResourceDemandingInternalBehaviour() {
        return (ResourceDemandingInternalBehaviour) this.eDynamicGet(
                SeffPackage.INTERNAL_CALL_ACTION__CALLED_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR,
                SeffPackage.Literals.INTERNAL_CALL_ACTION__CALLED_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCalledResourceDemandingInternalBehaviour(
            final ResourceDemandingInternalBehaviour newCalledResourceDemandingInternalBehaviour) {
        this.eDynamicSet(SeffPackage.INTERNAL_CALL_ACTION__CALLED_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR,
                SeffPackage.Literals.INTERNAL_CALL_ACTION__CALLED_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR,
                newCalledResourceDemandingInternalBehaviour);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs) {
        switch (featureID) {
        case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_DEMAND_ACTION:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getResourceDemand_Action())
                    .basicAdd(otherEnd, msgs);
        case SeffPackage.INTERNAL_CALL_ACTION__INFRASTRUCTURE_CALL_ACTION:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getInfrastructureCall__Action())
                    .basicAdd(otherEnd, msgs);
        case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_CALL_ACTION:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getResourceCall__Action())
                    .basicAdd(otherEnd, msgs);
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
        case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_DEMAND_ACTION:
            return ((InternalEList<?>) this.getResourceDemand_Action()).basicRemove(otherEnd, msgs);
        case SeffPackage.INTERNAL_CALL_ACTION__INFRASTRUCTURE_CALL_ACTION:
            return ((InternalEList<?>) this.getInfrastructureCall__Action()).basicRemove(otherEnd, msgs);
        case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_CALL_ACTION:
            return ((InternalEList<?>) this.getResourceCall__Action()).basicRemove(otherEnd, msgs);
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
        case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_DEMAND_ACTION:
            return this.getResourceDemand_Action();
        case SeffPackage.INTERNAL_CALL_ACTION__INFRASTRUCTURE_CALL_ACTION:
            return this.getInfrastructureCall__Action();
        case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_CALL_ACTION:
            return this.getResourceCall__Action();
        case SeffPackage.INTERNAL_CALL_ACTION__CALLED_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR:
            if (resolve) {
                return this.getCalledResourceDemandingInternalBehaviour();
            }
            return this.basicGetCalledResourceDemandingInternalBehaviour();
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
        case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_DEMAND_ACTION:
            this.getResourceDemand_Action().clear();
            this.getResourceDemand_Action().addAll((Collection<? extends ParametricResourceDemand>) newValue);
            return;
        case SeffPackage.INTERNAL_CALL_ACTION__INFRASTRUCTURE_CALL_ACTION:
            this.getInfrastructureCall__Action().clear();
            this.getInfrastructureCall__Action().addAll((Collection<? extends InfrastructureCall>) newValue);
            return;
        case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_CALL_ACTION:
            this.getResourceCall__Action().clear();
            this.getResourceCall__Action().addAll((Collection<? extends ResourceCall>) newValue);
            return;
        case SeffPackage.INTERNAL_CALL_ACTION__CALLED_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR:
            this.setCalledResourceDemandingInternalBehaviour((ResourceDemandingInternalBehaviour) newValue);
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
        case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_DEMAND_ACTION:
            this.getResourceDemand_Action().clear();
            return;
        case SeffPackage.INTERNAL_CALL_ACTION__INFRASTRUCTURE_CALL_ACTION:
            this.getInfrastructureCall__Action().clear();
            return;
        case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_CALL_ACTION:
            this.getResourceCall__Action().clear();
            return;
        case SeffPackage.INTERNAL_CALL_ACTION__CALLED_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR:
            this.setCalledResourceDemandingInternalBehaviour((ResourceDemandingInternalBehaviour) null);
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
        case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_DEMAND_ACTION:
            return !this.getResourceDemand_Action().isEmpty();
        case SeffPackage.INTERNAL_CALL_ACTION__INFRASTRUCTURE_CALL_ACTION:
            return !this.getInfrastructureCall__Action().isEmpty();
        case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_CALL_ACTION:
            return !this.getResourceCall__Action().isEmpty();
        case SeffPackage.INTERNAL_CALL_ACTION__CALLED_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR:
            return this.basicGetCalledResourceDemandingInternalBehaviour() != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(final int derivedFeatureID, final Class<?> baseClass) {
        if (baseClass == AbstractInternalControlFlowAction.class) {
            switch (derivedFeatureID) {
            case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_DEMAND_ACTION:
                return SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__RESOURCE_DEMAND_ACTION;
            case SeffPackage.INTERNAL_CALL_ACTION__INFRASTRUCTURE_CALL_ACTION:
                return SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__INFRASTRUCTURE_CALL_ACTION;
            case SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_CALL_ACTION:
                return SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__RESOURCE_CALL_ACTION;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(final int baseFeatureID, final Class<?> baseClass) {
        if (baseClass == AbstractInternalControlFlowAction.class) {
            switch (baseFeatureID) {
            case SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__RESOURCE_DEMAND_ACTION:
                return SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_DEMAND_ACTION;
            case SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__INFRASTRUCTURE_CALL_ACTION:
                return SeffPackage.INTERNAL_CALL_ACTION__INFRASTRUCTURE_CALL_ACTION;
            case SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__RESOURCE_CALL_ACTION:
                return SeffPackage.INTERNAL_CALL_ACTION__RESOURCE_CALL_ACTION;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} // InternalCallActionImpl
