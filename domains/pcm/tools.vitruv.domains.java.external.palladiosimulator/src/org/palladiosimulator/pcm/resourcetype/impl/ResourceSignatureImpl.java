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
import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.resourcetype.ResourcetypePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Resource Signature</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceSignatureImpl#getParameter__ResourceSignature
 * <em>Parameter Resource Signature</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.resourcetype.impl.ResourceSignatureImpl#getResourceServiceId
 * <em>Resource Service Id</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceSignatureImpl#getResourceInterface__ResourceSignature
 * <em>Resource Interface Resource Signature</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResourceSignatureImpl extends EntityImpl implements ResourceSignature {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The default value of the '{@link #getResourceServiceId() <em>Resource Service Id</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getResourceServiceId()
     * @generated
     * @ordered
     */
    protected static final int RESOURCE_SERVICE_ID_EDEFAULT = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected ResourceSignatureImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ResourcetypePackage.Literals.RESOURCE_SIGNATURE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<Parameter> getParameter__ResourceSignature() {
        return (EList<Parameter>) this.eDynamicGet(ResourcetypePackage.RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE,
                ResourcetypePackage.Literals.RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public int getResourceServiceId() {
        return (Integer) this.eDynamicGet(ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_SERVICE_ID,
                ResourcetypePackage.Literals.RESOURCE_SIGNATURE__RESOURCE_SERVICE_ID, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setResourceServiceId(final int newResourceServiceId) {
        this.eDynamicSet(ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_SERVICE_ID,
                ResourcetypePackage.Literals.RESOURCE_SIGNATURE__RESOURCE_SERVICE_ID, newResourceServiceId);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceInterface getResourceInterface__ResourceSignature() {
        return (ResourceInterface) this.eDynamicGet(
                ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE,
                ResourcetypePackage.Literals.RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetResourceInterface__ResourceSignature(
            final ResourceInterface newResourceInterface__ResourceSignature, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newResourceInterface__ResourceSignature,
                ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setResourceInterface__ResourceSignature(
            final ResourceInterface newResourceInterface__ResourceSignature) {
        this.eDynamicSet(ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE,
                ResourcetypePackage.Literals.RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE,
                newResourceInterface__ResourceSignature);
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
        case ResourcetypePackage.RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getParameter__ResourceSignature())
                    .basicAdd(otherEnd, msgs);
        case ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetResourceInterface__ResourceSignature((ResourceInterface) otherEnd, msgs);
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
        case ResourcetypePackage.RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE:
            return ((InternalEList<?>) this.getParameter__ResourceSignature()).basicRemove(otherEnd, msgs);
        case ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE:
            return this.basicSetResourceInterface__ResourceSignature(null, msgs);
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
        case ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE:
            return this.eInternalContainer().eInverseRemove(this,
                    ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_SIGNATURES_RESOURCE_INTERFACE,
                    ResourceInterface.class, msgs);
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
        case ResourcetypePackage.RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE:
            return this.getParameter__ResourceSignature();
        case ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_SERVICE_ID:
            return this.getResourceServiceId();
        case ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE:
            return this.getResourceInterface__ResourceSignature();
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
        case ResourcetypePackage.RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE:
            this.getParameter__ResourceSignature().clear();
            this.getParameter__ResourceSignature().addAll((Collection<? extends Parameter>) newValue);
            return;
        case ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_SERVICE_ID:
            this.setResourceServiceId((Integer) newValue);
            return;
        case ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE:
            this.setResourceInterface__ResourceSignature((ResourceInterface) newValue);
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
        case ResourcetypePackage.RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE:
            this.getParameter__ResourceSignature().clear();
            return;
        case ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_SERVICE_ID:
            this.setResourceServiceId(RESOURCE_SERVICE_ID_EDEFAULT);
            return;
        case ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE:
            this.setResourceInterface__ResourceSignature((ResourceInterface) null);
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
        case ResourcetypePackage.RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE:
            return !this.getParameter__ResourceSignature().isEmpty();
        case ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_SERVICE_ID:
            return this.getResourceServiceId() != RESOURCE_SERVICE_ID_EDEFAULT;
        case ResourcetypePackage.RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE:
            return this.getResourceInterface__ResourceSignature() != null;
        }
        return super.eIsSet(featureID);
    }

} // ResourceSignatureImpl
