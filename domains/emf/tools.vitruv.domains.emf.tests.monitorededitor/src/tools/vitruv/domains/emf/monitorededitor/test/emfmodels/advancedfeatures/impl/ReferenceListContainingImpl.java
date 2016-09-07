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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesPackage;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyData;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.ReferenceListContaining;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference List Containing</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.ReferenceListContainingImpl#getNonContainmentRefList <em>Non Containment Ref List</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReferenceListContainingImpl extends MinimalEObjectImpl.Container implements ReferenceListContaining {
    /**
     * The cached value of the '{@link #getNonContainmentRefList() <em>Non Containment Ref List</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNonContainmentRefList()
     * @generated
     * @ordered
     */
    protected EList<DummyData> nonContainmentRefList;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ReferenceListContainingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return AdvancedFeaturesPackage.Literals.REFERENCE_LIST_CONTAINING;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<DummyData> getNonContainmentRefList() {
        if (nonContainmentRefList == null) {
            nonContainmentRefList = new EObjectResolvingEList<DummyData>(DummyData.class, this, AdvancedFeaturesPackage.REFERENCE_LIST_CONTAINING__NON_CONTAINMENT_REF_LIST);
        }
        return nonContainmentRefList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case AdvancedFeaturesPackage.REFERENCE_LIST_CONTAINING__NON_CONTAINMENT_REF_LIST:
                return getNonContainmentRefList();
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
            case AdvancedFeaturesPackage.REFERENCE_LIST_CONTAINING__NON_CONTAINMENT_REF_LIST:
                getNonContainmentRefList().clear();
                getNonContainmentRefList().addAll((Collection<? extends DummyData>)newValue);
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
            case AdvancedFeaturesPackage.REFERENCE_LIST_CONTAINING__NON_CONTAINMENT_REF_LIST:
                getNonContainmentRefList().clear();
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
            case AdvancedFeaturesPackage.REFERENCE_LIST_CONTAINING__NON_CONTAINMENT_REF_LIST:
                return nonContainmentRefList != null && !nonContainmentRefList.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //ReferenceListContainingImpl
