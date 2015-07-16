/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import org.eclipse.emf.ecore.EClass;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.SinkRole;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Sink Role</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.SinkRoleImpl#getEventGroup__SinkRole
 * <em>Event Group Sink Role</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SinkRoleImpl extends ProvidedRoleImpl implements SinkRole {

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
    protected SinkRoleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.SINK_ROLE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EventGroup getEventGroup__SinkRole() {
        return (EventGroup) this.eDynamicGet(RepositoryPackage.SINK_ROLE__EVENT_GROUP_SINK_ROLE,
                RepositoryPackage.Literals.SINK_ROLE__EVENT_GROUP_SINK_ROLE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public EventGroup basicGetEventGroup__SinkRole() {
        return (EventGroup) this.eDynamicGet(RepositoryPackage.SINK_ROLE__EVENT_GROUP_SINK_ROLE,
                RepositoryPackage.Literals.SINK_ROLE__EVENT_GROUP_SINK_ROLE, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setEventGroup__SinkRole(final EventGroup newEventGroup__SinkRole) {
        this.eDynamicSet(RepositoryPackage.SINK_ROLE__EVENT_GROUP_SINK_ROLE,
                RepositoryPackage.Literals.SINK_ROLE__EVENT_GROUP_SINK_ROLE, newEventGroup__SinkRole);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case RepositoryPackage.SINK_ROLE__EVENT_GROUP_SINK_ROLE:
            if (resolve) {
                return this.getEventGroup__SinkRole();
            }
            return this.basicGetEventGroup__SinkRole();
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
        case RepositoryPackage.SINK_ROLE__EVENT_GROUP_SINK_ROLE:
            this.setEventGroup__SinkRole((EventGroup) newValue);
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
        case RepositoryPackage.SINK_ROLE__EVENT_GROUP_SINK_ROLE:
            this.setEventGroup__SinkRole((EventGroup) null);
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
        case RepositoryPackage.SINK_ROLE__EVENT_GROUP_SINK_ROLE:
            return this.basicGetEventGroup__SinkRole() != null;
        }
        return super.eIsSet(featureID);
    }

} // SinkRoleImpl
