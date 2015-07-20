/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.reliability.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.ReliabilityPackage;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Failure Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.reliability.impl.FailureTypeImpl#getRepository__FailureType
 * <em>Repository Failure Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class FailureTypeImpl extends EntityImpl implements FailureType {

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
    protected FailureTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReliabilityPackage.Literals.FAILURE_TYPE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Repository getRepository__FailureType() {
        return (Repository) this.eDynamicGet(ReliabilityPackage.FAILURE_TYPE__REPOSITORY_FAILURE_TYPE,
                ReliabilityPackage.Literals.FAILURE_TYPE__REPOSITORY_FAILURE_TYPE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetRepository__FailureType(final Repository newRepository__FailureType,
            NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newRepository__FailureType,
                ReliabilityPackage.FAILURE_TYPE__REPOSITORY_FAILURE_TYPE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setRepository__FailureType(final Repository newRepository__FailureType) {
        this.eDynamicSet(ReliabilityPackage.FAILURE_TYPE__REPOSITORY_FAILURE_TYPE,
                ReliabilityPackage.Literals.FAILURE_TYPE__REPOSITORY_FAILURE_TYPE, newRepository__FailureType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ReliabilityPackage.FAILURE_TYPE__REPOSITORY_FAILURE_TYPE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetRepository__FailureType((Repository) otherEnd, msgs);
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
        case ReliabilityPackage.FAILURE_TYPE__REPOSITORY_FAILURE_TYPE:
            return this.basicSetRepository__FailureType(null, msgs);
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
        case ReliabilityPackage.FAILURE_TYPE__REPOSITORY_FAILURE_TYPE:
            return this.eInternalContainer().eInverseRemove(this,
                    RepositoryPackage.REPOSITORY__FAILURE_TYPES_REPOSITORY, Repository.class, msgs);
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
        case ReliabilityPackage.FAILURE_TYPE__REPOSITORY_FAILURE_TYPE:
            return this.getRepository__FailureType();
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
        case ReliabilityPackage.FAILURE_TYPE__REPOSITORY_FAILURE_TYPE:
            this.setRepository__FailureType((Repository) newValue);
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
        case ReliabilityPackage.FAILURE_TYPE__REPOSITORY_FAILURE_TYPE:
            this.setRepository__FailureType((Repository) null);
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
        case ReliabilityPackage.FAILURE_TYPE__REPOSITORY_FAILURE_TYPE:
            return this.getRepository__FailureType() != null;
        }
        return super.eIsSet(featureID);
    }

} // FailureTypeImpl
