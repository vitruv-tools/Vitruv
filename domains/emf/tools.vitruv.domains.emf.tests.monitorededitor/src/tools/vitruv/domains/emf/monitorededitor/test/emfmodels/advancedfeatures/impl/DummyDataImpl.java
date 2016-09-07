/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

/**
 */
package tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesPackage;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyData;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dummy Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.DummyDataImpl#getDummyData <em>Dummy Data</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DummyDataImpl extends MinimalEObjectImpl.Container implements DummyData {
    /**
     * The default value of the '{@link #getDummyData() <em>Dummy Data</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDummyData()
     * @generated
     * @ordered
     */
    protected static final int DUMMY_DATA_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getDummyData() <em>Dummy Data</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDummyData()
     * @generated
     * @ordered
     */
    protected int dummyData = DUMMY_DATA_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DummyDataImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return AdvancedFeaturesPackage.Literals.DUMMY_DATA;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getDummyData() {
        return dummyData;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDummyData(int newDummyData) {
        int oldDummyData = dummyData;
        dummyData = newDummyData;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AdvancedFeaturesPackage.DUMMY_DATA__DUMMY_DATA, oldDummyData, dummyData));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case AdvancedFeaturesPackage.DUMMY_DATA__DUMMY_DATA:
                return getDummyData();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case AdvancedFeaturesPackage.DUMMY_DATA__DUMMY_DATA:
                setDummyData((Integer)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case AdvancedFeaturesPackage.DUMMY_DATA__DUMMY_DATA:
                setDummyData(DUMMY_DATA_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case AdvancedFeaturesPackage.DUMMY_DATA__DUMMY_DATA:
                return dummyData != DUMMY_DATA_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (dummyData: ");
        result.append(dummyData);
        result.append(')');
        return result.toString();
    }

} //DummyDataImpl
