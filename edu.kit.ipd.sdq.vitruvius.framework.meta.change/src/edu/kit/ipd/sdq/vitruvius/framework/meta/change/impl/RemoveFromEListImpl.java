/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.RemoveFromEList;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remove From EList</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.RemoveFromEListImpl#getRemovedObjectURIFragment <em>Removed Object URI Fragment</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RemoveFromEListImpl<T extends EStructuralFeature> extends UpdateEListImpl<T> implements RemoveFromEList<T> {
    /**
     * The default value of the '{@link #getRemovedObjectURIFragment() <em>Removed Object URI Fragment</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRemovedObjectURIFragment()
     * @generated
     * @ordered
     */
    protected static final String REMOVED_OBJECT_URI_FRAGMENT_EDEFAULT = "0";

    /**
     * The cached value of the '{@link #getRemovedObjectURIFragment() <em>Removed Object URI Fragment</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRemovedObjectURIFragment()
     * @generated
     * @ordered
     */
    protected String removedObjectURIFragment = REMOVED_OBJECT_URI_FRAGMENT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RemoveFromEListImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChangePackage.Literals.REMOVE_FROM_ELIST;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRemovedObjectURIFragment() {
        return removedObjectURIFragment;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRemovedObjectURIFragment(String newRemovedObjectURIFragment) {
        String oldRemovedObjectURIFragment = removedObjectURIFragment;
        removedObjectURIFragment = newRemovedObjectURIFragment;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChangePackage.REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT, oldRemovedObjectURIFragment, removedObjectURIFragment));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ChangePackage.REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT:
                return getRemovedObjectURIFragment();
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
            case ChangePackage.REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT:
                setRemovedObjectURIFragment((String)newValue);
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
            case ChangePackage.REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT:
                setRemovedObjectURIFragment(REMOVED_OBJECT_URI_FRAGMENT_EDEFAULT);
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
            case ChangePackage.REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT:
                return REMOVED_OBJECT_URI_FRAGMENT_EDEFAULT == null ? removedObjectURIFragment != null : !REMOVED_OBJECT_URI_FRAGMENT_EDEFAULT.equals(removedObjectURIFragment);
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
        result.append(" (removedObjectURIFragment: ");
        result.append(removedObjectURIFragment);
        result.append(')');
        return result.toString();
    }

} //RemoveFromEListImpl
