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
import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Repository</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.RepositoryImpl#getRepositoryDescription
 * <em>Repository Description</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.RepositoryImpl#getComponents__Repository
 * <em>Components Repository</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.RepositoryImpl#getInterfaces__Repository
 * <em>Interfaces Repository</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.RepositoryImpl#getFailureTypes__Repository
 * <em>Failure Types Repository</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.RepositoryImpl#getDataTypes__Repository
 * <em>Data Types Repository</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RepositoryImpl extends EntityImpl implements Repository {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The default value of the '{@link #getRepositoryDescription() <em>Repository Description</em>}
     * ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getRepositoryDescription()
     * @generated
     * @ordered
     */
    protected static final String REPOSITORY_DESCRIPTION_EDEFAULT = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected RepositoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.REPOSITORY;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getRepositoryDescription() {
        return (String) this.eDynamicGet(RepositoryPackage.REPOSITORY__REPOSITORY_DESCRIPTION,
                RepositoryPackage.Literals.REPOSITORY__REPOSITORY_DESCRIPTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setRepositoryDescription(final String newRepositoryDescription) {
        this.eDynamicSet(RepositoryPackage.REPOSITORY__REPOSITORY_DESCRIPTION,
                RepositoryPackage.Literals.REPOSITORY__REPOSITORY_DESCRIPTION, newRepositoryDescription);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<RepositoryComponent> getComponents__Repository() {
        return (EList<RepositoryComponent>) this.eDynamicGet(RepositoryPackage.REPOSITORY__COMPONENTS_REPOSITORY,
                RepositoryPackage.Literals.REPOSITORY__COMPONENTS_REPOSITORY, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<Interface> getInterfaces__Repository() {
        return (EList<Interface>) this.eDynamicGet(RepositoryPackage.REPOSITORY__INTERFACES_REPOSITORY,
                RepositoryPackage.Literals.REPOSITORY__INTERFACES_REPOSITORY, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<FailureType> getFailureTypes__Repository() {
        return (EList<FailureType>) this.eDynamicGet(RepositoryPackage.REPOSITORY__FAILURE_TYPES_REPOSITORY,
                RepositoryPackage.Literals.REPOSITORY__FAILURE_TYPES_REPOSITORY, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<DataType> getDataTypes__Repository() {
        return (EList<DataType>) this.eDynamicGet(RepositoryPackage.REPOSITORY__DATA_TYPES_REPOSITORY,
                RepositoryPackage.Literals.REPOSITORY__DATA_TYPES_REPOSITORY, true, true);
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
        case RepositoryPackage.REPOSITORY__COMPONENTS_REPOSITORY:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getComponents__Repository())
                    .basicAdd(otherEnd, msgs);
        case RepositoryPackage.REPOSITORY__INTERFACES_REPOSITORY:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getInterfaces__Repository())
                    .basicAdd(otherEnd, msgs);
        case RepositoryPackage.REPOSITORY__FAILURE_TYPES_REPOSITORY:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getFailureTypes__Repository())
                    .basicAdd(otherEnd, msgs);
        case RepositoryPackage.REPOSITORY__DATA_TYPES_REPOSITORY:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getDataTypes__Repository())
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
        case RepositoryPackage.REPOSITORY__COMPONENTS_REPOSITORY:
            return ((InternalEList<?>) this.getComponents__Repository()).basicRemove(otherEnd, msgs);
        case RepositoryPackage.REPOSITORY__INTERFACES_REPOSITORY:
            return ((InternalEList<?>) this.getInterfaces__Repository()).basicRemove(otherEnd, msgs);
        case RepositoryPackage.REPOSITORY__FAILURE_TYPES_REPOSITORY:
            return ((InternalEList<?>) this.getFailureTypes__Repository()).basicRemove(otherEnd, msgs);
        case RepositoryPackage.REPOSITORY__DATA_TYPES_REPOSITORY:
            return ((InternalEList<?>) this.getDataTypes__Repository()).basicRemove(otherEnd, msgs);
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
        case RepositoryPackage.REPOSITORY__REPOSITORY_DESCRIPTION:
            return this.getRepositoryDescription();
        case RepositoryPackage.REPOSITORY__COMPONENTS_REPOSITORY:
            return this.getComponents__Repository();
        case RepositoryPackage.REPOSITORY__INTERFACES_REPOSITORY:
            return this.getInterfaces__Repository();
        case RepositoryPackage.REPOSITORY__FAILURE_TYPES_REPOSITORY:
            return this.getFailureTypes__Repository();
        case RepositoryPackage.REPOSITORY__DATA_TYPES_REPOSITORY:
            return this.getDataTypes__Repository();
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
        case RepositoryPackage.REPOSITORY__REPOSITORY_DESCRIPTION:
            this.setRepositoryDescription((String) newValue);
            return;
        case RepositoryPackage.REPOSITORY__COMPONENTS_REPOSITORY:
            this.getComponents__Repository().clear();
            this.getComponents__Repository().addAll((Collection<? extends RepositoryComponent>) newValue);
            return;
        case RepositoryPackage.REPOSITORY__INTERFACES_REPOSITORY:
            this.getInterfaces__Repository().clear();
            this.getInterfaces__Repository().addAll((Collection<? extends Interface>) newValue);
            return;
        case RepositoryPackage.REPOSITORY__FAILURE_TYPES_REPOSITORY:
            this.getFailureTypes__Repository().clear();
            this.getFailureTypes__Repository().addAll((Collection<? extends FailureType>) newValue);
            return;
        case RepositoryPackage.REPOSITORY__DATA_TYPES_REPOSITORY:
            this.getDataTypes__Repository().clear();
            this.getDataTypes__Repository().addAll((Collection<? extends DataType>) newValue);
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
        case RepositoryPackage.REPOSITORY__REPOSITORY_DESCRIPTION:
            this.setRepositoryDescription(REPOSITORY_DESCRIPTION_EDEFAULT);
            return;
        case RepositoryPackage.REPOSITORY__COMPONENTS_REPOSITORY:
            this.getComponents__Repository().clear();
            return;
        case RepositoryPackage.REPOSITORY__INTERFACES_REPOSITORY:
            this.getInterfaces__Repository().clear();
            return;
        case RepositoryPackage.REPOSITORY__FAILURE_TYPES_REPOSITORY:
            this.getFailureTypes__Repository().clear();
            return;
        case RepositoryPackage.REPOSITORY__DATA_TYPES_REPOSITORY:
            this.getDataTypes__Repository().clear();
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
        case RepositoryPackage.REPOSITORY__REPOSITORY_DESCRIPTION:
            return REPOSITORY_DESCRIPTION_EDEFAULT == null ? this.getRepositoryDescription() != null
                    : !REPOSITORY_DESCRIPTION_EDEFAULT.equals(this.getRepositoryDescription());
        case RepositoryPackage.REPOSITORY__COMPONENTS_REPOSITORY:
            return !this.getComponents__Repository().isEmpty();
        case RepositoryPackage.REPOSITORY__INTERFACES_REPOSITORY:
            return !this.getInterfaces__Repository().isEmpty();
        case RepositoryPackage.REPOSITORY__FAILURE_TYPES_REPOSITORY:
            return !this.getFailureTypes__Repository().isEmpty();
        case RepositoryPackage.REPOSITORY__DATA_TYPES_REPOSITORY:
            return !this.getDataTypes__Repository().isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // RepositoryImpl
