/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.RepositoryPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Infrastructure Interface</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.InfrastructureInterfaceImpl#getInfrastructureSignatures__InfrastructureInterface
 * <em>Infrastructure Signatures Infrastructure Interface</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InfrastructureInterfaceImpl extends InterfaceImpl implements InfrastructureInterface {

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
    protected InfrastructureInterfaceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.INFRASTRUCTURE_INTERFACE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<InfrastructureSignature> getInfrastructureSignatures__InfrastructureInterface() {
        return (EList<InfrastructureSignature>) this.eDynamicGet(
                RepositoryPackage.INFRASTRUCTURE_INTERFACE__INFRASTRUCTURE_SIGNATURES_INFRASTRUCTURE_INTERFACE,
                RepositoryPackage.Literals.INFRASTRUCTURE_INTERFACE__INFRASTRUCTURE_SIGNATURES_INFRASTRUCTURE_INTERFACE,
                true, true);
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
        case RepositoryPackage.INFRASTRUCTURE_INTERFACE__INFRASTRUCTURE_SIGNATURES_INFRASTRUCTURE_INTERFACE:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this
                    .getInfrastructureSignatures__InfrastructureInterface()).basicAdd(otherEnd, msgs);
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
        case RepositoryPackage.INFRASTRUCTURE_INTERFACE__INFRASTRUCTURE_SIGNATURES_INFRASTRUCTURE_INTERFACE:
            return ((InternalEList<?>) this.getInfrastructureSignatures__InfrastructureInterface())
                    .basicRemove(otherEnd, msgs);
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
        case RepositoryPackage.INFRASTRUCTURE_INTERFACE__INFRASTRUCTURE_SIGNATURES_INFRASTRUCTURE_INTERFACE:
            return this.getInfrastructureSignatures__InfrastructureInterface();
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
        case RepositoryPackage.INFRASTRUCTURE_INTERFACE__INFRASTRUCTURE_SIGNATURES_INFRASTRUCTURE_INTERFACE:
            this.getInfrastructureSignatures__InfrastructureInterface().clear();
            this.getInfrastructureSignatures__InfrastructureInterface()
                    .addAll((Collection<? extends InfrastructureSignature>) newValue);
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
        case RepositoryPackage.INFRASTRUCTURE_INTERFACE__INFRASTRUCTURE_SIGNATURES_INFRASTRUCTURE_INTERFACE:
            this.getInfrastructureSignatures__InfrastructureInterface().clear();
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
        case RepositoryPackage.INFRASTRUCTURE_INTERFACE__INFRASTRUCTURE_SIGNATURES_INFRASTRUCTURE_INTERFACE:
            return !this.getInfrastructureSignatures__InfrastructureInterface().isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // InfrastructureInterfaceImpl
