/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;
import org.palladiosimulator.pcm.usagemodel.Workload;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Workload</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.impl.WorkloadImpl#getUsageScenario_Workload
 * <em>Usage Scenario Workload</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class WorkloadImpl extends CDOObjectImpl implements Workload {

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
    protected WorkloadImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return UsagemodelPackage.Literals.WORKLOAD;
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
    public UsageScenario getUsageScenario_Workload() {
        return (UsageScenario) this.eDynamicGet(UsagemodelPackage.WORKLOAD__USAGE_SCENARIO_WORKLOAD,
                UsagemodelPackage.Literals.WORKLOAD__USAGE_SCENARIO_WORKLOAD, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetUsageScenario_Workload(final UsageScenario newUsageScenario_Workload,
            NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newUsageScenario_Workload,
                UsagemodelPackage.WORKLOAD__USAGE_SCENARIO_WORKLOAD, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setUsageScenario_Workload(final UsageScenario newUsageScenario_Workload) {
        this.eDynamicSet(UsagemodelPackage.WORKLOAD__USAGE_SCENARIO_WORKLOAD,
                UsagemodelPackage.Literals.WORKLOAD__USAGE_SCENARIO_WORKLOAD, newUsageScenario_Workload);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case UsagemodelPackage.WORKLOAD__USAGE_SCENARIO_WORKLOAD:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetUsageScenario_Workload((UsageScenario) otherEnd, msgs);
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
        case UsagemodelPackage.WORKLOAD__USAGE_SCENARIO_WORKLOAD:
            return this.basicSetUsageScenario_Workload(null, msgs);
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
        case UsagemodelPackage.WORKLOAD__USAGE_SCENARIO_WORKLOAD:
            return this.eInternalContainer().eInverseRemove(this,
                    UsagemodelPackage.USAGE_SCENARIO__WORKLOAD_USAGE_SCENARIO, UsageScenario.class, msgs);
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
        case UsagemodelPackage.WORKLOAD__USAGE_SCENARIO_WORKLOAD:
            return this.getUsageScenario_Workload();
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
        case UsagemodelPackage.WORKLOAD__USAGE_SCENARIO_WORKLOAD:
            this.setUsageScenario_Workload((UsageScenario) newValue);
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
        case UsagemodelPackage.WORKLOAD__USAGE_SCENARIO_WORKLOAD:
            this.setUsageScenario_Workload((UsageScenario) null);
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
        case UsagemodelPackage.WORKLOAD__USAGE_SCENARIO_WORKLOAD:
            return this.getUsageScenario_Workload() != null;
        }
        return super.eIsSet(featureID);
    }

} // WorkloadImpl
