/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import org.eclipse.emf.ecore.EClass;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.SourceRole;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Source Role</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.SourceRoleImpl#getEventGroup__SourceRole
 * <em>Event Group Source Role</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SourceRoleImpl extends RequiredRoleImpl implements SourceRole {

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
    protected SourceRoleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.SOURCE_ROLE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EventGroup getEventGroup__SourceRole() {
        return (EventGroup) this.eDynamicGet(RepositoryPackage.SOURCE_ROLE__EVENT_GROUP_SOURCE_ROLE,
                RepositoryPackage.Literals.SOURCE_ROLE__EVENT_GROUP_SOURCE_ROLE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EventGroup basicGetEventGroup__SourceRole() {
        return (EventGroup) this.eDynamicGet(RepositoryPackage.SOURCE_ROLE__EVENT_GROUP_SOURCE_ROLE,
                RepositoryPackage.Literals.SOURCE_ROLE__EVENT_GROUP_SOURCE_ROLE, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setEventGroup__SourceRole(final EventGroup newEventGroup__SourceRole) {
        this.eDynamicSet(RepositoryPackage.SOURCE_ROLE__EVENT_GROUP_SOURCE_ROLE,
                RepositoryPackage.Literals.SOURCE_ROLE__EVENT_GROUP_SOURCE_ROLE, newEventGroup__SourceRole);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case RepositoryPackage.SOURCE_ROLE__EVENT_GROUP_SOURCE_ROLE:
            if (resolve) {
                return this.getEventGroup__SourceRole();
            }
            return this.basicGetEventGroup__SourceRole();
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
        case RepositoryPackage.SOURCE_ROLE__EVENT_GROUP_SOURCE_ROLE:
            this.setEventGroup__SourceRole((EventGroup) newValue);
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
        case RepositoryPackage.SOURCE_ROLE__EVENT_GROUP_SOURCE_ROLE:
            this.setEventGroup__SourceRole((EventGroup) null);
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
        case RepositoryPackage.SOURCE_ROLE__EVENT_GROUP_SOURCE_ROLE:
            return this.basicGetEventGroup__SourceRole() != null;
        }
        return super.eIsSet(featureID);
    }

} // SourceRoleImpl
