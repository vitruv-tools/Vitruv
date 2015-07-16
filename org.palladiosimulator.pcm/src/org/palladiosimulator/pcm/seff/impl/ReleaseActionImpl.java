/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

import org.eclipse.emf.ecore.EClass;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.seff.ReleaseAction;
import org.palladiosimulator.pcm.seff.SeffPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Release Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.ReleaseActionImpl#getPassiveResource_ReleaseAction
 * <em>Passive Resource Release Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReleaseActionImpl extends AbstractInternalControlFlowActionImpl implements ReleaseAction {

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
    protected ReleaseActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.RELEASE_ACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PassiveResource getPassiveResource_ReleaseAction() {
        return (PassiveResource) this.eDynamicGet(SeffPackage.RELEASE_ACTION__PASSIVE_RESOURCE_RELEASE_ACTION,
                SeffPackage.Literals.RELEASE_ACTION__PASSIVE_RESOURCE_RELEASE_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public PassiveResource basicGetPassiveResource_ReleaseAction() {
        return (PassiveResource) this.eDynamicGet(SeffPackage.RELEASE_ACTION__PASSIVE_RESOURCE_RELEASE_ACTION,
                SeffPackage.Literals.RELEASE_ACTION__PASSIVE_RESOURCE_RELEASE_ACTION, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPassiveResource_ReleaseAction(final PassiveResource newPassiveResource_ReleaseAction) {
        this.eDynamicSet(SeffPackage.RELEASE_ACTION__PASSIVE_RESOURCE_RELEASE_ACTION,
                SeffPackage.Literals.RELEASE_ACTION__PASSIVE_RESOURCE_RELEASE_ACTION, newPassiveResource_ReleaseAction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case SeffPackage.RELEASE_ACTION__PASSIVE_RESOURCE_RELEASE_ACTION:
            if (resolve) {
                return this.getPassiveResource_ReleaseAction();
            }
            return this.basicGetPassiveResource_ReleaseAction();
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
        case SeffPackage.RELEASE_ACTION__PASSIVE_RESOURCE_RELEASE_ACTION:
            this.setPassiveResource_ReleaseAction((PassiveResource) newValue);
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
        case SeffPackage.RELEASE_ACTION__PASSIVE_RESOURCE_RELEASE_ACTION:
            this.setPassiveResource_ReleaseAction((PassiveResource) null);
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
        case SeffPackage.RELEASE_ACTION__PASSIVE_RESOURCE_RELEASE_ACTION:
            return this.basicGetPassiveResource_ReleaseAction() != null;
        }
        return super.eIsSet(featureID);
    }

} // ReleaseActionImpl
