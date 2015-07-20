/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import org.eclipse.emf.ecore.EClass;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.RepositoryPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Primitive Data Type</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.PrimitiveDataTypeImpl#getType <em>Type</em>}
 * </li>
 * </ul>
 *
 * @generated
 */
public class PrimitiveDataTypeImpl extends DataTypeImpl implements PrimitiveDataType {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final PrimitiveTypeEnum TYPE_EDEFAULT = PrimitiveTypeEnum.INT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected PrimitiveDataTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.PRIMITIVE_DATA_TYPE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public PrimitiveTypeEnum getType() {
        return (PrimitiveTypeEnum) this.eDynamicGet(RepositoryPackage.PRIMITIVE_DATA_TYPE__TYPE,
                RepositoryPackage.Literals.PRIMITIVE_DATA_TYPE__TYPE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setType(final PrimitiveTypeEnum newType) {
        this.eDynamicSet(RepositoryPackage.PRIMITIVE_DATA_TYPE__TYPE,
                RepositoryPackage.Literals.PRIMITIVE_DATA_TYPE__TYPE, newType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case RepositoryPackage.PRIMITIVE_DATA_TYPE__TYPE:
            return this.getType();
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
        case RepositoryPackage.PRIMITIVE_DATA_TYPE__TYPE:
            this.setType((PrimitiveTypeEnum) newValue);
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
        case RepositoryPackage.PRIMITIVE_DATA_TYPE__TYPE:
            this.setType(TYPE_EDEFAULT);
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
        case RepositoryPackage.PRIMITIVE_DATA_TYPE__TYPE:
            return this.getType() != TYPE_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

} // PrimitiveDataTypeImpl
