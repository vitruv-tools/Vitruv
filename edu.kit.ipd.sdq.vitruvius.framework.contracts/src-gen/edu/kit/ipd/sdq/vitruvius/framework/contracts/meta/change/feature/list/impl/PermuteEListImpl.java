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
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.PermuteEListImpl#getOldIndex <em>Old Index</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.PermuteEListImpl#getNewIndex <em>New Index</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class PermuteEListImpl extends UpdateMultiValuedEFeatureImpl implements PermuteEList {
    /**
     * The default value of the '{@link #getOldIndex() <em>Old Index</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOldIndex()
     * @generated
     * @ordered
     */
    protected static final int OLD_INDEX_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getOldIndex() <em>Old Index</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOldIndex()
     * @generated
     * @ordered
     */
    protected int oldIndex = OLD_INDEX_EDEFAULT;

    /**
     * The default value of the '{@link #getNewIndex() <em>New Index</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNewIndex()
     * @generated
     * @ordered
     */
    protected static final int NEW_INDEX_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getNewIndex() <em>New Index</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNewIndex()
     * @generated
     * @ordered
     */
    protected int newIndex = NEW_INDEX_EDEFAULT;

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
    public int getOldIndex() {
        return oldIndex;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOldIndex(int newOldIndex) {
        int oldOldIndex = oldIndex;
        oldIndex = newOldIndex;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ListPackage.PERMUTE_ELIST__OLD_INDEX, oldOldIndex, oldIndex));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getNewIndex() {
        return newIndex;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNewIndex(int newNewIndex) {
        int oldNewIndex = newIndex;
        newIndex = newNewIndex;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ListPackage.PERMUTE_ELIST__NEW_INDEX, oldNewIndex, newIndex));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ListPackage.PERMUTE_ELIST__OLD_INDEX:
                return getOldIndex();
            case ListPackage.PERMUTE_ELIST__NEW_INDEX:
                return getNewIndex();
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
            case ListPackage.PERMUTE_ELIST__OLD_INDEX:
                setOldIndex((Integer)newValue);
                return;
            case ListPackage.PERMUTE_ELIST__NEW_INDEX:
                setNewIndex((Integer)newValue);
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
            case ListPackage.PERMUTE_ELIST__OLD_INDEX:
                setOldIndex(OLD_INDEX_EDEFAULT);
                return;
            case ListPackage.PERMUTE_ELIST__NEW_INDEX:
                setNewIndex(NEW_INDEX_EDEFAULT);
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
            case ListPackage.PERMUTE_ELIST__OLD_INDEX:
                return oldIndex != OLD_INDEX_EDEFAULT;
            case ListPackage.PERMUTE_ELIST__NEW_INDEX:
                return newIndex != NEW_INDEX_EDEFAULT;
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
        result.append(" (oldIndex: ");
        result.append(oldIndex);
        result.append(", newIndex: ");
        result.append(newIndex);
        result.append(')');
        return result.toString();
    }

} //PermuteEListImpl
