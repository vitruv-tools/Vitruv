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
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AdvancedFeaturesPackage;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.AttributeListContaining;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.DummyDataContainer;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.FeatureMapContaining;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.ReferenceListContaining;
import tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.RootContainer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.RootContainerImpl#getFeatureMapContaining <em>Feature Map Containing</em>}</li>
 *   <li>{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.RootContainerImpl#getReferenceListContaining <em>Reference List Containing</em>}</li>
 *   <li>{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.RootContainerImpl#getDummyDataContainerContaining <em>Dummy Data Container Containing</em>}</li>
 *   <li>{@link tools.vitruv.domains.emf.monitorededitor.test.emfmodels.advancedfeatures.impl.RootContainerImpl#getAttributeListContaining <em>Attribute List Containing</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RootContainerImpl extends MinimalEObjectImpl.Container implements RootContainer {
    /**
     * The cached value of the '{@link #getFeatureMapContaining() <em>Feature Map Containing</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFeatureMapContaining()
     * @generated
     * @ordered
     */
    protected FeatureMapContaining featureMapContaining;

    /**
     * The cached value of the '{@link #getReferenceListContaining() <em>Reference List Containing</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReferenceListContaining()
     * @generated
     * @ordered
     */
    protected ReferenceListContaining referenceListContaining;

    /**
     * The cached value of the '{@link #getDummyDataContainerContaining() <em>Dummy Data Container Containing</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDummyDataContainerContaining()
     * @generated
     * @ordered
     */
    protected DummyDataContainer dummyDataContainerContaining;

    /**
     * The cached value of the '{@link #getAttributeListContaining() <em>Attribute List Containing</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeListContaining()
     * @generated
     * @ordered
     */
    protected AttributeListContaining attributeListContaining;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RootContainerImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return AdvancedFeaturesPackage.Literals.ROOT_CONTAINER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMapContaining getFeatureMapContaining() {
        return featureMapContaining;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetFeatureMapContaining(FeatureMapContaining newFeatureMapContaining, NotificationChain msgs) {
        FeatureMapContaining oldFeatureMapContaining = featureMapContaining;
        featureMapContaining = newFeatureMapContaining;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AdvancedFeaturesPackage.ROOT_CONTAINER__FEATURE_MAP_CONTAINING, oldFeatureMapContaining, newFeatureMapContaining);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFeatureMapContaining(FeatureMapContaining newFeatureMapContaining) {
        if (newFeatureMapContaining != featureMapContaining) {
            NotificationChain msgs = null;
            if (featureMapContaining != null)
                msgs = ((InternalEObject)featureMapContaining).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AdvancedFeaturesPackage.ROOT_CONTAINER__FEATURE_MAP_CONTAINING, null, msgs);
            if (newFeatureMapContaining != null)
                msgs = ((InternalEObject)newFeatureMapContaining).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AdvancedFeaturesPackage.ROOT_CONTAINER__FEATURE_MAP_CONTAINING, null, msgs);
            msgs = basicSetFeatureMapContaining(newFeatureMapContaining, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AdvancedFeaturesPackage.ROOT_CONTAINER__FEATURE_MAP_CONTAINING, newFeatureMapContaining, newFeatureMapContaining));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReferenceListContaining getReferenceListContaining() {
        return referenceListContaining;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetReferenceListContaining(ReferenceListContaining newReferenceListContaining, NotificationChain msgs) {
        ReferenceListContaining oldReferenceListContaining = referenceListContaining;
        referenceListContaining = newReferenceListContaining;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AdvancedFeaturesPackage.ROOT_CONTAINER__REFERENCE_LIST_CONTAINING, oldReferenceListContaining, newReferenceListContaining);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReferenceListContaining(ReferenceListContaining newReferenceListContaining) {
        if (newReferenceListContaining != referenceListContaining) {
            NotificationChain msgs = null;
            if (referenceListContaining != null)
                msgs = ((InternalEObject)referenceListContaining).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AdvancedFeaturesPackage.ROOT_CONTAINER__REFERENCE_LIST_CONTAINING, null, msgs);
            if (newReferenceListContaining != null)
                msgs = ((InternalEObject)newReferenceListContaining).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AdvancedFeaturesPackage.ROOT_CONTAINER__REFERENCE_LIST_CONTAINING, null, msgs);
            msgs = basicSetReferenceListContaining(newReferenceListContaining, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AdvancedFeaturesPackage.ROOT_CONTAINER__REFERENCE_LIST_CONTAINING, newReferenceListContaining, newReferenceListContaining));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DummyDataContainer getDummyDataContainerContaining() {
        return dummyDataContainerContaining;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDummyDataContainerContaining(DummyDataContainer newDummyDataContainerContaining, NotificationChain msgs) {
        DummyDataContainer oldDummyDataContainerContaining = dummyDataContainerContaining;
        dummyDataContainerContaining = newDummyDataContainerContaining;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AdvancedFeaturesPackage.ROOT_CONTAINER__DUMMY_DATA_CONTAINER_CONTAINING, oldDummyDataContainerContaining, newDummyDataContainerContaining);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDummyDataContainerContaining(DummyDataContainer newDummyDataContainerContaining) {
        if (newDummyDataContainerContaining != dummyDataContainerContaining) {
            NotificationChain msgs = null;
            if (dummyDataContainerContaining != null)
                msgs = ((InternalEObject)dummyDataContainerContaining).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AdvancedFeaturesPackage.ROOT_CONTAINER__DUMMY_DATA_CONTAINER_CONTAINING, null, msgs);
            if (newDummyDataContainerContaining != null)
                msgs = ((InternalEObject)newDummyDataContainerContaining).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AdvancedFeaturesPackage.ROOT_CONTAINER__DUMMY_DATA_CONTAINER_CONTAINING, null, msgs);
            msgs = basicSetDummyDataContainerContaining(newDummyDataContainerContaining, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AdvancedFeaturesPackage.ROOT_CONTAINER__DUMMY_DATA_CONTAINER_CONTAINING, newDummyDataContainerContaining, newDummyDataContainerContaining));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeListContaining getAttributeListContaining() {
        return attributeListContaining;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAttributeListContaining(AttributeListContaining newAttributeListContaining, NotificationChain msgs) {
        AttributeListContaining oldAttributeListContaining = attributeListContaining;
        attributeListContaining = newAttributeListContaining;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AdvancedFeaturesPackage.ROOT_CONTAINER__ATTRIBUTE_LIST_CONTAINING, oldAttributeListContaining, newAttributeListContaining);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttributeListContaining(AttributeListContaining newAttributeListContaining) {
        if (newAttributeListContaining != attributeListContaining) {
            NotificationChain msgs = null;
            if (attributeListContaining != null)
                msgs = ((InternalEObject)attributeListContaining).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AdvancedFeaturesPackage.ROOT_CONTAINER__ATTRIBUTE_LIST_CONTAINING, null, msgs);
            if (newAttributeListContaining != null)
                msgs = ((InternalEObject)newAttributeListContaining).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AdvancedFeaturesPackage.ROOT_CONTAINER__ATTRIBUTE_LIST_CONTAINING, null, msgs);
            msgs = basicSetAttributeListContaining(newAttributeListContaining, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AdvancedFeaturesPackage.ROOT_CONTAINER__ATTRIBUTE_LIST_CONTAINING, newAttributeListContaining, newAttributeListContaining));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case AdvancedFeaturesPackage.ROOT_CONTAINER__FEATURE_MAP_CONTAINING:
                return basicSetFeatureMapContaining(null, msgs);
            case AdvancedFeaturesPackage.ROOT_CONTAINER__REFERENCE_LIST_CONTAINING:
                return basicSetReferenceListContaining(null, msgs);
            case AdvancedFeaturesPackage.ROOT_CONTAINER__DUMMY_DATA_CONTAINER_CONTAINING:
                return basicSetDummyDataContainerContaining(null, msgs);
            case AdvancedFeaturesPackage.ROOT_CONTAINER__ATTRIBUTE_LIST_CONTAINING:
                return basicSetAttributeListContaining(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case AdvancedFeaturesPackage.ROOT_CONTAINER__FEATURE_MAP_CONTAINING:
                return getFeatureMapContaining();
            case AdvancedFeaturesPackage.ROOT_CONTAINER__REFERENCE_LIST_CONTAINING:
                return getReferenceListContaining();
            case AdvancedFeaturesPackage.ROOT_CONTAINER__DUMMY_DATA_CONTAINER_CONTAINING:
                return getDummyDataContainerContaining();
            case AdvancedFeaturesPackage.ROOT_CONTAINER__ATTRIBUTE_LIST_CONTAINING:
                return getAttributeListContaining();
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
            case AdvancedFeaturesPackage.ROOT_CONTAINER__FEATURE_MAP_CONTAINING:
                setFeatureMapContaining((FeatureMapContaining)newValue);
                return;
            case AdvancedFeaturesPackage.ROOT_CONTAINER__REFERENCE_LIST_CONTAINING:
                setReferenceListContaining((ReferenceListContaining)newValue);
                return;
            case AdvancedFeaturesPackage.ROOT_CONTAINER__DUMMY_DATA_CONTAINER_CONTAINING:
                setDummyDataContainerContaining((DummyDataContainer)newValue);
                return;
            case AdvancedFeaturesPackage.ROOT_CONTAINER__ATTRIBUTE_LIST_CONTAINING:
                setAttributeListContaining((AttributeListContaining)newValue);
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
            case AdvancedFeaturesPackage.ROOT_CONTAINER__FEATURE_MAP_CONTAINING:
                setFeatureMapContaining((FeatureMapContaining)null);
                return;
            case AdvancedFeaturesPackage.ROOT_CONTAINER__REFERENCE_LIST_CONTAINING:
                setReferenceListContaining((ReferenceListContaining)null);
                return;
            case AdvancedFeaturesPackage.ROOT_CONTAINER__DUMMY_DATA_CONTAINER_CONTAINING:
                setDummyDataContainerContaining((DummyDataContainer)null);
                return;
            case AdvancedFeaturesPackage.ROOT_CONTAINER__ATTRIBUTE_LIST_CONTAINING:
                setAttributeListContaining((AttributeListContaining)null);
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
            case AdvancedFeaturesPackage.ROOT_CONTAINER__FEATURE_MAP_CONTAINING:
                return featureMapContaining != null;
            case AdvancedFeaturesPackage.ROOT_CONTAINER__REFERENCE_LIST_CONTAINING:
                return referenceListContaining != null;
            case AdvancedFeaturesPackage.ROOT_CONTAINER__DUMMY_DATA_CONTAINER_CONTAINING:
                return dummyDataContainerContaining != null;
            case AdvancedFeaturesPackage.ROOT_CONTAINER__ATTRIBUTE_LIST_CONTAINING:
                return attributeListContaining != null;
        }
        return super.eIsSet(featureID);
    }

} //RootContainerImpl
