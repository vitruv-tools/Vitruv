/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UpdateMultiValuedEFeatureImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteEList;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Permute EList</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.PermuteEListImpl#getNewIndexForElementAt <em>New Index For Element At</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class PermuteEListImpl<T extends Object> extends UpdateMultiValuedEFeatureImpl<T> implements PermuteEList<T> {
    /**
     * The default value of the '{@link #getNewIndexForElementAt() <em>New Index For Element At</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNewIndexForElementAt()
     * @generated
     * @ordered
     */
    protected static final int NEW_INDEX_FOR_ELEMENT_AT_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getNewIndexForElementAt() <em>New Index For Element At</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNewIndexForElementAt()
     * @generated
     * @ordered
     */
    protected int newIndexForElementAt = NEW_INDEX_FOR_ELEMENT_AT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PermuteEListImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ListPackage.Literals.PERMUTE_ELIST;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getNewIndexForElementAt() {
        return newIndexForElementAt;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNewIndexForElementAt(int newNewIndexForElementAt) {
        int oldNewIndexForElementAt = newIndexForElementAt;
        newIndexForElementAt = newNewIndexForElementAt;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT, oldNewIndexForElementAt, newIndexForElementAt));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT:
                return getNewIndexForElementAt();
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
            case ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT:
                setNewIndexForElementAt((Integer)newValue);
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
            case ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT:
                setNewIndexForElementAt(NEW_INDEX_FOR_ELEMENT_AT_EDEFAULT);
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
            case ListPackage.PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT:
                return newIndexForElementAt != NEW_INDEX_FOR_ELEMENT_AT_EDEFAULT;
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
        result.append(" (newIndexForElementAt: ");
        result.append(newIndexForElementAt);
        result.append(')');
        return result.toString();
    }

} //PermuteEListImpl
