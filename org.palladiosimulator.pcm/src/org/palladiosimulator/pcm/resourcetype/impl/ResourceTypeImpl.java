/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourcetype.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.palladiosimulator.pcm.core.entity.EntityPackage;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity;
import org.palladiosimulator.pcm.core.entity.ResourceProvidedRole;
import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.ResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourcetypePackage;

import de.uka.ipd.sdq.units.Unit;
import de.uka.ipd.sdq.units.UnitCarryingElement;
import de.uka.ipd.sdq.units.UnitsPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Resource Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.resourcetype.impl.ResourceTypeImpl#getUnit <em>Unit</em>}
 * </li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceTypeImpl#getResourceProvidedRoles__ResourceInterfaceProvidingEntity
 * <em>Resource Provided Roles Resource Interface Providing Entity</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceTypeImpl#getResourceRepository_ResourceType
 * <em>Resource Repository Resource Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ResourceTypeImpl extends EntityImpl implements ResourceType {

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
    protected ResourceTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ResourcetypePackage.Literals.RESOURCE_TYPE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Unit getUnit() {
        return (Unit) this.eDynamicGet(ResourcetypePackage.RESOURCE_TYPE__UNIT,
                UnitsPackage.Literals.UNIT_CARRYING_ELEMENT__UNIT, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetUnit(final Unit newUnit, NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newUnit, ResourcetypePackage.RESOURCE_TYPE__UNIT, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void setUnit(final Unit newUnit) {
        this.eDynamicSet(ResourcetypePackage.RESOURCE_TYPE__UNIT, UnitsPackage.Literals.UNIT_CARRYING_ELEMENT__UNIT,
                newUnit);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<ResourceProvidedRole> getResourceProvidedRoles__ResourceInterfaceProvidingEntity() {
        return (EList<ResourceProvidedRole>) this.eDynamicGet(
                ResourcetypePackage.RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY,
                EntityPackage.Literals.RESOURCE_INTERFACE_PROVIDING_ENTITY__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceRepository getResourceRepository_ResourceType() {
        return (ResourceRepository) this.eDynamicGet(
                ResourcetypePackage.RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE,
                ResourcetypePackage.Literals.RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetResourceRepository_ResourceType(
            final ResourceRepository newResourceRepository_ResourceType, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newResourceRepository_ResourceType,
                ResourcetypePackage.RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setResourceRepository_ResourceType(final ResourceRepository newResourceRepository_ResourceType) {
        this.eDynamicSet(ResourcetypePackage.RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE,
                ResourcetypePackage.Literals.RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE,
                newResourceRepository_ResourceType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this
                    .getResourceProvidedRoles__ResourceInterfaceProvidingEntity()).basicAdd(otherEnd, msgs);
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetResourceRepository_ResourceType((ResourceRepository) otherEnd, msgs);
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
        case ResourcetypePackage.RESOURCE_TYPE__UNIT:
            return this.basicSetUnit(null, msgs);
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY:
            return ((InternalEList<?>) this.getResourceProvidedRoles__ResourceInterfaceProvidingEntity())
                    .basicRemove(otherEnd, msgs);
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE:
            return this.basicSetResourceRepository_ResourceType(null, msgs);
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
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE:
            return this.eInternalContainer().eInverseRemove(this,
                    ResourcetypePackage.RESOURCE_REPOSITORY__AVAILABLE_RESOURCE_TYPES_RESOURCE_REPOSITORY,
                    ResourceRepository.class, msgs);
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
        case ResourcetypePackage.RESOURCE_TYPE__UNIT:
            return this.getUnit();
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY:
            return this.getResourceProvidedRoles__ResourceInterfaceProvidingEntity();
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE:
            return this.getResourceRepository_ResourceType();
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
        case ResourcetypePackage.RESOURCE_TYPE__UNIT:
            this.setUnit((Unit) newValue);
            return;
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY:
            this.getResourceProvidedRoles__ResourceInterfaceProvidingEntity().clear();
            this.getResourceProvidedRoles__ResourceInterfaceProvidingEntity()
                    .addAll((Collection<? extends ResourceProvidedRole>) newValue);
            return;
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE:
            this.setResourceRepository_ResourceType((ResourceRepository) newValue);
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
        case ResourcetypePackage.RESOURCE_TYPE__UNIT:
            this.setUnit((Unit) null);
            return;
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY:
            this.getResourceProvidedRoles__ResourceInterfaceProvidingEntity().clear();
            return;
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE:
            this.setResourceRepository_ResourceType((ResourceRepository) null);
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
        case ResourcetypePackage.RESOURCE_TYPE__UNIT:
            return this.getUnit() != null;
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY:
            return !this.getResourceProvidedRoles__ResourceInterfaceProvidingEntity().isEmpty();
        case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE:
            return this.getResourceRepository_ResourceType() != null;
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
        if (baseClass == UnitCarryingElement.class) {
            switch (derivedFeatureID) {
            case ResourcetypePackage.RESOURCE_TYPE__UNIT:
                return UnitsPackage.UNIT_CARRYING_ELEMENT__UNIT;
            default:
                return -1;
            }
        }
        if (baseClass == ResourceInterfaceProvidingEntity.class) {
            switch (derivedFeatureID) {
            case ResourcetypePackage.RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY:
                return EntityPackage.RESOURCE_INTERFACE_PROVIDING_ENTITY__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY;
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
        if (baseClass == UnitCarryingElement.class) {
            switch (baseFeatureID) {
            case UnitsPackage.UNIT_CARRYING_ELEMENT__UNIT:
                return ResourcetypePackage.RESOURCE_TYPE__UNIT;
            default:
                return -1;
            }
        }
        if (baseClass == ResourceInterfaceProvidingEntity.class) {
            switch (baseFeatureID) {
            case EntityPackage.RESOURCE_INTERFACE_PROVIDING_ENTITY__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY:
                return ResourcetypePackage.RESOURCE_TYPE__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} // ResourceTypeImpl
