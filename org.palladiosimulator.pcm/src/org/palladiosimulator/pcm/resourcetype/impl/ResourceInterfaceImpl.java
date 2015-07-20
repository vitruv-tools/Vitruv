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
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.resourcetype.ResourcetypePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Resource Interface</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceInterfaceImpl#getResourceRepository__ResourceInterface
 * <em>Resource Repository Resource Interface</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourcetype.impl.ResourceInterfaceImpl#getResourceSignatures__ResourceInterface
 * <em>Resource Signatures Resource Interface</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResourceInterfaceImpl extends EntityImpl implements ResourceInterface {

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
    protected ResourceInterfaceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ResourcetypePackage.Literals.RESOURCE_INTERFACE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceRepository getResourceRepository__ResourceInterface() {
        return (ResourceRepository) this.eDynamicGet(
                ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE,
                ResourcetypePackage.Literals.RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetResourceRepository__ResourceInterface(
            final ResourceRepository newResourceRepository__ResourceInterface, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newResourceRepository__ResourceInterface,
                ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setResourceRepository__ResourceInterface(
            final ResourceRepository newResourceRepository__ResourceInterface) {
        this.eDynamicSet(ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE,
                ResourcetypePackage.Literals.RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE,
                newResourceRepository__ResourceInterface);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<ResourceSignature> getResourceSignatures__ResourceInterface() {
        return (EList<ResourceSignature>) this.eDynamicGet(
                ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_SIGNATURES_RESOURCE_INTERFACE,
                ResourcetypePackage.Literals.RESOURCE_INTERFACE__RESOURCE_SIGNATURES_RESOURCE_INTERFACE, true, true);
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
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetResourceRepository__ResourceInterface((ResourceRepository) otherEnd, msgs);
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_SIGNATURES_RESOURCE_INTERFACE:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getResourceSignatures__ResourceInterface())
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
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE:
            return this.basicSetResourceRepository__ResourceInterface(null, msgs);
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_SIGNATURES_RESOURCE_INTERFACE:
            return ((InternalEList<?>) this.getResourceSignatures__ResourceInterface()).basicRemove(otherEnd, msgs);
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
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE:
            return this.eInternalContainer().eInverseRemove(this,
                    ResourcetypePackage.RESOURCE_REPOSITORY__RESOURCE_INTERFACES_RESOURCE_REPOSITORY,
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
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE:
            return this.getResourceRepository__ResourceInterface();
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_SIGNATURES_RESOURCE_INTERFACE:
            return this.getResourceSignatures__ResourceInterface();
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
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE:
            this.setResourceRepository__ResourceInterface((ResourceRepository) newValue);
            return;
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_SIGNATURES_RESOURCE_INTERFACE:
            this.getResourceSignatures__ResourceInterface().clear();
            this.getResourceSignatures__ResourceInterface().addAll((Collection<? extends ResourceSignature>) newValue);
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
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE:
            this.setResourceRepository__ResourceInterface((ResourceRepository) null);
            return;
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_SIGNATURES_RESOURCE_INTERFACE:
            this.getResourceSignatures__ResourceInterface().clear();
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
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE:
            return this.getResourceRepository__ResourceInterface() != null;
        case ResourcetypePackage.RESOURCE_INTERFACE__RESOURCE_SIGNATURES_RESOURCE_INTERFACE:
            return !this.getResourceSignatures__ResourceInterface().isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // ResourceInterfaceImpl
