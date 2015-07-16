/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;
import org.palladiosimulator.pcm.reliability.ReliabilityPackage;
import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.RepositoryPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Passive Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.PassiveResourceImpl#getCapacity_PassiveResource
 * <em>Capacity Passive Resource</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.PassiveResourceImpl#getBasicComponent_PassiveResource
 * <em>Basic Component Passive Resource</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.PassiveResourceImpl#getResourceTimeoutFailureType__PassiveResource
 * <em>Resource Timeout Failure Type Passive Resource</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PassiveResourceImpl extends EntityImpl implements PassiveResource {

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
    protected PassiveResourceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.PASSIVE_RESOURCE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PCMRandomVariable getCapacity_PassiveResource() {
        return (PCMRandomVariable) this.eDynamicGet(RepositoryPackage.PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE,
                RepositoryPackage.Literals.PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetCapacity_PassiveResource(final PCMRandomVariable newCapacity_PassiveResource,
            NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newCapacity_PassiveResource,
                RepositoryPackage.PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCapacity_PassiveResource(final PCMRandomVariable newCapacity_PassiveResource) {
        this.eDynamicSet(RepositoryPackage.PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE,
                RepositoryPackage.Literals.PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE, newCapacity_PassiveResource);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public BasicComponent getBasicComponent_PassiveResource() {
        return (BasicComponent) this.eDynamicGet(RepositoryPackage.PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE,
                RepositoryPackage.Literals.PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetBasicComponent_PassiveResource(
            final BasicComponent newBasicComponent_PassiveResource, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newBasicComponent_PassiveResource,
                RepositoryPackage.PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBasicComponent_PassiveResource(final BasicComponent newBasicComponent_PassiveResource) {
        this.eDynamicSet(RepositoryPackage.PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE,
                RepositoryPackage.Literals.PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE,
                newBasicComponent_PassiveResource);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceTimeoutFailureType getResourceTimeoutFailureType__PassiveResource() {
        return (ResourceTimeoutFailureType) this.eDynamicGet(
                RepositoryPackage.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE,
                RepositoryPackage.Literals.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE, true,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ResourceTimeoutFailureType basicGetResourceTimeoutFailureType__PassiveResource() {
        return (ResourceTimeoutFailureType) this.eDynamicGet(
                RepositoryPackage.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE,
                RepositoryPackage.Literals.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE, false,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetResourceTimeoutFailureType__PassiveResource(
            final ResourceTimeoutFailureType newResourceTimeoutFailureType__PassiveResource, NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newResourceTimeoutFailureType__PassiveResource,
                RepositoryPackage.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setResourceTimeoutFailureType__PassiveResource(
            final ResourceTimeoutFailureType newResourceTimeoutFailureType__PassiveResource) {
        this.eDynamicSet(RepositoryPackage.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE,
                RepositoryPackage.Literals.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE,
                newResourceTimeoutFailureType__PassiveResource);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case RepositoryPackage.PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE:
            final PCMRandomVariable capacity_PassiveResource = this.getCapacity_PassiveResource();
            if (capacity_PassiveResource != null) {
                msgs = ((InternalEObject) capacity_PassiveResource).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - RepositoryPackage.PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE, null,
                        msgs);
            }
            return this.basicSetCapacity_PassiveResource((PCMRandomVariable) otherEnd, msgs);
        case RepositoryPackage.PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetBasicComponent_PassiveResource((BasicComponent) otherEnd, msgs);
        case RepositoryPackage.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE:
            final ResourceTimeoutFailureType resourceTimeoutFailureType__PassiveResource = this
                    .basicGetResourceTimeoutFailureType__PassiveResource();
            if (resourceTimeoutFailureType__PassiveResource != null) {
                msgs = ((InternalEObject) resourceTimeoutFailureType__PassiveResource).eInverseRemove(this,
                        ReliabilityPackage.RESOURCE_TIMEOUT_FAILURE_TYPE__PASSIVE_RESOURCE_RESOURCE_TIMEOUT_FAILURE_TYPE,
                        ResourceTimeoutFailureType.class, msgs);
            }
            return this.basicSetResourceTimeoutFailureType__PassiveResource((ResourceTimeoutFailureType) otherEnd,
                    msgs);
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
        case RepositoryPackage.PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE:
            return this.basicSetCapacity_PassiveResource(null, msgs);
        case RepositoryPackage.PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE:
            return this.basicSetBasicComponent_PassiveResource(null, msgs);
        case RepositoryPackage.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE:
            return this.basicSetResourceTimeoutFailureType__PassiveResource(null, msgs);
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
        case RepositoryPackage.PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE:
            return this.eInternalContainer().eInverseRemove(this,
                    RepositoryPackage.BASIC_COMPONENT__PASSIVE_RESOURCE_BASIC_COMPONENT, BasicComponent.class, msgs);
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
        case RepositoryPackage.PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE:
            return this.getCapacity_PassiveResource();
        case RepositoryPackage.PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE:
            return this.getBasicComponent_PassiveResource();
        case RepositoryPackage.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE:
            if (resolve) {
                return this.getResourceTimeoutFailureType__PassiveResource();
            }
            return this.basicGetResourceTimeoutFailureType__PassiveResource();
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
        case RepositoryPackage.PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE:
            this.setCapacity_PassiveResource((PCMRandomVariable) newValue);
            return;
        case RepositoryPackage.PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE:
            this.setBasicComponent_PassiveResource((BasicComponent) newValue);
            return;
        case RepositoryPackage.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE:
            this.setResourceTimeoutFailureType__PassiveResource((ResourceTimeoutFailureType) newValue);
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
        case RepositoryPackage.PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE:
            this.setCapacity_PassiveResource((PCMRandomVariable) null);
            return;
        case RepositoryPackage.PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE:
            this.setBasicComponent_PassiveResource((BasicComponent) null);
            return;
        case RepositoryPackage.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE:
            this.setResourceTimeoutFailureType__PassiveResource((ResourceTimeoutFailureType) null);
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
        case RepositoryPackage.PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE:
            return this.getCapacity_PassiveResource() != null;
        case RepositoryPackage.PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE:
            return this.getBasicComponent_PassiveResource() != null;
        case RepositoryPackage.PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE:
            return this.basicGetResourceTimeoutFailureType__PassiveResource() != null;
        }
        return super.eIsSet(featureID);
    }

} // PassiveResourceImpl
