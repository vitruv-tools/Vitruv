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

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesPackage;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AttributeListContaining;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute List Containing</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.AttributeListContainingImpl#getAttrList <em>Attr List</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeListContainingImpl extends MinimalEObjectImpl.Container implements AttributeListContaining {
    /**
     * The cached value of the '{@link #getAttrList() <em>Attr List</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttrList()
     * @generated
     * @ordered
     */
    protected EList<String> attrList;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AttributeListContainingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return AdvancedFeaturesPackage.Literals.ATTRIBUTE_LIST_CONTAINING;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getAttrList() {
        if (attrList == null) {
            attrList = new EDataTypeUniqueEList<String>(String.class, this, AdvancedFeaturesPackage.ATTRIBUTE_LIST_CONTAINING__ATTR_LIST);
        }
        return attrList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case AdvancedFeaturesPackage.ATTRIBUTE_LIST_CONTAINING__ATTR_LIST:
                return getAttrList();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case AdvancedFeaturesPackage.ATTRIBUTE_LIST_CONTAINING__ATTR_LIST:
                getAttrList().clear();
                getAttrList().addAll((Collection<? extends String>)newValue);
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
            case AdvancedFeaturesPackage.ATTRIBUTE_LIST_CONTAINING__ATTR_LIST:
                getAttrList().clear();
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
            case AdvancedFeaturesPackage.ATTRIBUTE_LIST_CONTAINING__ATTR_LIST:
                return attrList != null && !attrList.isEmpty();
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
        result.append(" (attrList: ");
        result.append(attrList);
        result.append(')');
        return result.toString();
    }

} //AttributeListContainingImpl
