/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.entity.impl.NamedElementImpl;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.RepositoryPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Inner Declaration</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.InnerDeclarationImpl#getDatatype_InnerDeclaration
 * <em>Datatype Inner Declaration</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.InnerDeclarationImpl#getCompositeDataType_InnerDeclaration
 * <em>Composite Data Type Inner Declaration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InnerDeclarationImpl extends NamedElementImpl implements InnerDeclaration {

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
    protected InnerDeclarationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.INNER_DECLARATION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DataType getDatatype_InnerDeclaration() {
        return (DataType) this.eDynamicGet(RepositoryPackage.INNER_DECLARATION__DATATYPE_INNER_DECLARATION,
                RepositoryPackage.Literals.INNER_DECLARATION__DATATYPE_INNER_DECLARATION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DataType basicGetDatatype_InnerDeclaration() {
        return (DataType) this.eDynamicGet(RepositoryPackage.INNER_DECLARATION__DATATYPE_INNER_DECLARATION,
                RepositoryPackage.Literals.INNER_DECLARATION__DATATYPE_INNER_DECLARATION, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDatatype_InnerDeclaration(final DataType newDatatype_InnerDeclaration) {
        this.eDynamicSet(RepositoryPackage.INNER_DECLARATION__DATATYPE_INNER_DECLARATION,
                RepositoryPackage.Literals.INNER_DECLARATION__DATATYPE_INNER_DECLARATION, newDatatype_InnerDeclaration);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CompositeDataType getCompositeDataType_InnerDeclaration() {
        return (CompositeDataType) this.eDynamicGet(
                RepositoryPackage.INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION,
                RepositoryPackage.Literals.INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetCompositeDataType_InnerDeclaration(
            final CompositeDataType newCompositeDataType_InnerDeclaration, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newCompositeDataType_InnerDeclaration,
                RepositoryPackage.INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCompositeDataType_InnerDeclaration(final CompositeDataType newCompositeDataType_InnerDeclaration) {
        this.eDynamicSet(RepositoryPackage.INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION,
                RepositoryPackage.Literals.INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION,
                newCompositeDataType_InnerDeclaration);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case RepositoryPackage.INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetCompositeDataType_InnerDeclaration((CompositeDataType) otherEnd, msgs);
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
        case RepositoryPackage.INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION:
            return this.basicSetCompositeDataType_InnerDeclaration(null, msgs);
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
        case RepositoryPackage.INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION:
            return this.eInternalContainer().eInverseRemove(this,
                    RepositoryPackage.COMPOSITE_DATA_TYPE__INNER_DECLARATION_COMPOSITE_DATA_TYPE,
                    CompositeDataType.class, msgs);
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
        case RepositoryPackage.INNER_DECLARATION__DATATYPE_INNER_DECLARATION:
            if (resolve) {
                return this.getDatatype_InnerDeclaration();
            }
            return this.basicGetDatatype_InnerDeclaration();
        case RepositoryPackage.INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION:
            return this.getCompositeDataType_InnerDeclaration();
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
        case RepositoryPackage.INNER_DECLARATION__DATATYPE_INNER_DECLARATION:
            this.setDatatype_InnerDeclaration((DataType) newValue);
            return;
        case RepositoryPackage.INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION:
            this.setCompositeDataType_InnerDeclaration((CompositeDataType) newValue);
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
        case RepositoryPackage.INNER_DECLARATION__DATATYPE_INNER_DECLARATION:
            this.setDatatype_InnerDeclaration((DataType) null);
            return;
        case RepositoryPackage.INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION:
            this.setCompositeDataType_InnerDeclaration((CompositeDataType) null);
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
        case RepositoryPackage.INNER_DECLARATION__DATATYPE_INNER_DECLARATION:
            return this.basicGetDatatype_InnerDeclaration() != null;
        case RepositoryPackage.INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION:
            return this.getCompositeDataType_InnerDeclaration() != null;
        }
        return super.eIsSet(featureID);
    }

} // InnerDeclarationImpl
