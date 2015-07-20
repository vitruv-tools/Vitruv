/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.entity.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.entity.EntityPackage;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.impl.RoleImpl;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Resource Required Role</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.entity.impl.ResourceRequiredRoleImpl#getRequiredResourceInterface__ResourceRequiredRole
 * <em>Required Resource Interface Resource Required Role</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.entity.impl.ResourceRequiredRoleImpl#getResourceInterfaceRequiringEntity__ResourceRequiredRole
 * <em>Resource Interface Requiring Entity Resource Required Role</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResourceRequiredRoleImpl extends RoleImpl implements ResourceRequiredRole {

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
    protected ResourceRequiredRoleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return EntityPackage.Literals.RESOURCE_REQUIRED_ROLE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceInterface getRequiredResourceInterface__ResourceRequiredRole() {
        return (ResourceInterface) this.eDynamicGet(
                EntityPackage.RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE,
                EntityPackage.Literals.RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE, true,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ResourceInterface basicGetRequiredResourceInterface__ResourceRequiredRole() {
        return (ResourceInterface) this.eDynamicGet(
                EntityPackage.RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE,
                EntityPackage.Literals.RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setRequiredResourceInterface__ResourceRequiredRole(
            final ResourceInterface newRequiredResourceInterface__ResourceRequiredRole) {
        this.eDynamicSet(EntityPackage.RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE,
                EntityPackage.Literals.RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE,
                newRequiredResourceInterface__ResourceRequiredRole);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceInterfaceRequiringEntity getResourceInterfaceRequiringEntity__ResourceRequiredRole() {
        return (ResourceInterfaceRequiringEntity) this.eDynamicGet(
                EntityPackage.RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE,
                EntityPackage.Literals.RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetResourceInterfaceRequiringEntity__ResourceRequiredRole(
            final ResourceInterfaceRequiringEntity newResourceInterfaceRequiringEntity__ResourceRequiredRole,
            NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newResourceInterfaceRequiringEntity__ResourceRequiredRole,
                EntityPackage.RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setResourceInterfaceRequiringEntity__ResourceRequiredRole(
            final ResourceInterfaceRequiringEntity newResourceInterfaceRequiringEntity__ResourceRequiredRole) {
        this.eDynamicSet(
                EntityPackage.RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE,
                EntityPackage.Literals.RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE,
                newResourceInterfaceRequiringEntity__ResourceRequiredRole);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case EntityPackage.RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetResourceInterfaceRequiringEntity__ResourceRequiredRole(
                    (ResourceInterfaceRequiringEntity) otherEnd, msgs);
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
        case EntityPackage.RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE:
            return this.basicSetResourceInterfaceRequiringEntity__ResourceRequiredRole(null, msgs);
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
        case EntityPackage.RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE:
            return this.eInternalContainer().eInverseRemove(this,
                    EntityPackage.RESOURCE_INTERFACE_REQUIRING_ENTITY__RESOURCE_REQUIRED_ROLES_RESOURCE_INTERFACE_REQUIRING_ENTITY,
                    ResourceInterfaceRequiringEntity.class, msgs);
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
        case EntityPackage.RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE:
            if (resolve) {
                return this.getRequiredResourceInterface__ResourceRequiredRole();
            }
            return this.basicGetRequiredResourceInterface__ResourceRequiredRole();
        case EntityPackage.RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE:
            return this.getResourceInterfaceRequiringEntity__ResourceRequiredRole();
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
        case EntityPackage.RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE:
            this.setRequiredResourceInterface__ResourceRequiredRole((ResourceInterface) newValue);
            return;
        case EntityPackage.RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE:
            this.setResourceInterfaceRequiringEntity__ResourceRequiredRole((ResourceInterfaceRequiringEntity) newValue);
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
        case EntityPackage.RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE:
            this.setRequiredResourceInterface__ResourceRequiredRole((ResourceInterface) null);
            return;
        case EntityPackage.RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE:
            this.setResourceInterfaceRequiringEntity__ResourceRequiredRole((ResourceInterfaceRequiringEntity) null);
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
        case EntityPackage.RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE:
            return this.basicGetRequiredResourceInterface__ResourceRequiredRole() != null;
        case EntityPackage.RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE:
            return this.getResourceInterfaceRequiringEntity__ResourceRequiredRole() != null;
        }
        return super.eIsSet(featureID);
    }

} // ResourceRequiredRoleImpl
