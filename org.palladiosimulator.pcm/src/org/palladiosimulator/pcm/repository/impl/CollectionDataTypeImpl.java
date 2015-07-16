/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Collection Data Type</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.CollectionDataTypeImpl#getRepository__DataType
 * <em>Repository Data Type</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.CollectionDataTypeImpl#getInnerType_CollectionDataType
 * <em>Inner Type Collection Data Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CollectionDataTypeImpl extends EntityImpl implements CollectionDataType {

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
    protected CollectionDataTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.COLLECTION_DATA_TYPE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Repository getRepository__DataType() {
        return (Repository) this.eDynamicGet(RepositoryPackage.COLLECTION_DATA_TYPE__REPOSITORY_DATA_TYPE,
                RepositoryPackage.Literals.DATA_TYPE__REPOSITORY_DATA_TYPE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetRepository__DataType(final Repository newRepository__DataType,
            NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newRepository__DataType,
                RepositoryPackage.COLLECTION_DATA_TYPE__REPOSITORY_DATA_TYPE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setRepository__DataType(final Repository newRepository__DataType) {
        this.eDynamicSet(RepositoryPackage.COLLECTION_DATA_TYPE__REPOSITORY_DATA_TYPE,
                RepositoryPackage.Literals.DATA_TYPE__REPOSITORY_DATA_TYPE, newRepository__DataType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DataType getInnerType_CollectionDataType() {
        return (DataType) this.eDynamicGet(RepositoryPackage.COLLECTION_DATA_TYPE__INNER_TYPE_COLLECTION_DATA_TYPE,
                RepositoryPackage.Literals.COLLECTION_DATA_TYPE__INNER_TYPE_COLLECTION_DATA_TYPE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DataType basicGetInnerType_CollectionDataType() {
        return (DataType) this.eDynamicGet(RepositoryPackage.COLLECTION_DATA_TYPE__INNER_TYPE_COLLECTION_DATA_TYPE,
                RepositoryPackage.Literals.COLLECTION_DATA_TYPE__INNER_TYPE_COLLECTION_DATA_TYPE, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setInnerType_CollectionDataType(final DataType newInnerType_CollectionDataType) {
        this.eDynamicSet(RepositoryPackage.COLLECTION_DATA_TYPE__INNER_TYPE_COLLECTION_DATA_TYPE,
                RepositoryPackage.Literals.COLLECTION_DATA_TYPE__INNER_TYPE_COLLECTION_DATA_TYPE,
                newInnerType_CollectionDataType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case RepositoryPackage.COLLECTION_DATA_TYPE__REPOSITORY_DATA_TYPE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetRepository__DataType((Repository) otherEnd, msgs);
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
        case RepositoryPackage.COLLECTION_DATA_TYPE__REPOSITORY_DATA_TYPE:
            return this.basicSetRepository__DataType(null, msgs);
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
        case RepositoryPackage.COLLECTION_DATA_TYPE__REPOSITORY_DATA_TYPE:
            return this.eInternalContainer().eInverseRemove(this, RepositoryPackage.REPOSITORY__DATA_TYPES_REPOSITORY,
                    Repository.class, msgs);
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
        case RepositoryPackage.COLLECTION_DATA_TYPE__REPOSITORY_DATA_TYPE:
            return this.getRepository__DataType();
        case RepositoryPackage.COLLECTION_DATA_TYPE__INNER_TYPE_COLLECTION_DATA_TYPE:
            if (resolve) {
                return this.getInnerType_CollectionDataType();
            }
            return this.basicGetInnerType_CollectionDataType();
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
        case RepositoryPackage.COLLECTION_DATA_TYPE__REPOSITORY_DATA_TYPE:
            this.setRepository__DataType((Repository) newValue);
            return;
        case RepositoryPackage.COLLECTION_DATA_TYPE__INNER_TYPE_COLLECTION_DATA_TYPE:
            this.setInnerType_CollectionDataType((DataType) newValue);
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
        case RepositoryPackage.COLLECTION_DATA_TYPE__REPOSITORY_DATA_TYPE:
            this.setRepository__DataType((Repository) null);
            return;
        case RepositoryPackage.COLLECTION_DATA_TYPE__INNER_TYPE_COLLECTION_DATA_TYPE:
            this.setInnerType_CollectionDataType((DataType) null);
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
        case RepositoryPackage.COLLECTION_DATA_TYPE__REPOSITORY_DATA_TYPE:
            return this.getRepository__DataType() != null;
        case RepositoryPackage.COLLECTION_DATA_TYPE__INNER_TYPE_COLLECTION_DATA_TYPE:
            return this.basicGetInnerType_CollectionDataType() != null;
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
        if (baseClass == DataType.class) {
            switch (derivedFeatureID) {
            case RepositoryPackage.COLLECTION_DATA_TYPE__REPOSITORY_DATA_TYPE:
                return RepositoryPackage.DATA_TYPE__REPOSITORY_DATA_TYPE;
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
        if (baseClass == DataType.class) {
            switch (baseFeatureID) {
            case RepositoryPackage.DATA_TYPE__REPOSITORY_DATA_TYPE:
                return RepositoryPackage.COLLECTION_DATA_TYPE__REPOSITORY_DATA_TYPE;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} // CollectionDataTypeImpl
