/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.RepositoryPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Exception Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.ExceptionTypeImpl#getExceptionName
 * <em>Exception Name</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.ExceptionTypeImpl#getExceptionMessage
 * <em>Exception Message</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExceptionTypeImpl extends CDOObjectImpl implements ExceptionType {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The default value of the '{@link #getExceptionName() <em>Exception Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getExceptionName()
     * @generated
     * @ordered
     */
    protected static final String EXCEPTION_NAME_EDEFAULT = null;

    /**
     * The default value of the '{@link #getExceptionMessage() <em>Exception Message</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getExceptionMessage()
     * @generated
     * @ordered
     */
    protected static final String EXCEPTION_MESSAGE_EDEFAULT = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected ExceptionTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.EXCEPTION_TYPE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected int eStaticFeatureCount() {
        return 0;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getExceptionName() {
        return (String) this.eDynamicGet(RepositoryPackage.EXCEPTION_TYPE__EXCEPTION_NAME,
                RepositoryPackage.Literals.EXCEPTION_TYPE__EXCEPTION_NAME, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setExceptionName(final String newExceptionName) {
        this.eDynamicSet(RepositoryPackage.EXCEPTION_TYPE__EXCEPTION_NAME,
                RepositoryPackage.Literals.EXCEPTION_TYPE__EXCEPTION_NAME, newExceptionName);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getExceptionMessage() {
        return (String) this.eDynamicGet(RepositoryPackage.EXCEPTION_TYPE__EXCEPTION_MESSAGE,
                RepositoryPackage.Literals.EXCEPTION_TYPE__EXCEPTION_MESSAGE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setExceptionMessage(final String newExceptionMessage) {
        this.eDynamicSet(RepositoryPackage.EXCEPTION_TYPE__EXCEPTION_MESSAGE,
                RepositoryPackage.Literals.EXCEPTION_TYPE__EXCEPTION_MESSAGE, newExceptionMessage);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case RepositoryPackage.EXCEPTION_TYPE__EXCEPTION_NAME:
            return this.getExceptionName();
        case RepositoryPackage.EXCEPTION_TYPE__EXCEPTION_MESSAGE:
            return this.getExceptionMessage();
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
        case RepositoryPackage.EXCEPTION_TYPE__EXCEPTION_NAME:
            this.setExceptionName((String) newValue);
            return;
        case RepositoryPackage.EXCEPTION_TYPE__EXCEPTION_MESSAGE:
            this.setExceptionMessage((String) newValue);
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
        case RepositoryPackage.EXCEPTION_TYPE__EXCEPTION_NAME:
            this.setExceptionName(EXCEPTION_NAME_EDEFAULT);
            return;
        case RepositoryPackage.EXCEPTION_TYPE__EXCEPTION_MESSAGE:
            this.setExceptionMessage(EXCEPTION_MESSAGE_EDEFAULT);
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
        case RepositoryPackage.EXCEPTION_TYPE__EXCEPTION_NAME:
            return EXCEPTION_NAME_EDEFAULT == null ? this.getExceptionName() != null
                    : !EXCEPTION_NAME_EDEFAULT.equals(this.getExceptionName());
        case RepositoryPackage.EXCEPTION_TYPE__EXCEPTION_MESSAGE:
            return EXCEPTION_MESSAGE_EDEFAULT == null ? this.getExceptionMessage() != null
                    : !EXCEPTION_MESSAGE_EDEFAULT.equals(this.getExceptionMessage());
        }
        return super.eIsSet(featureID);
    }

} // ExceptionTypeImpl
