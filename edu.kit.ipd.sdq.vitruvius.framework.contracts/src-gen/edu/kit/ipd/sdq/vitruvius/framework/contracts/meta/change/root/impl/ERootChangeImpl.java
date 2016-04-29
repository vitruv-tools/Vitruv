/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.EAtomicChangeImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.ERootChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ERoot Change</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.ERootChangeImpl#getUri <em>Uri</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ERootChangeImpl extends EAtomicChangeImpl implements ERootChange {
    /**
     * The default value of the '{@link #getUri() <em>Uri</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUri()
     * @generated
     * @ordered
     */
    protected static final String URI_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUri()
     * @generated
     * @ordered
     */
    protected String uri = URI_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ERootChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RootPackage.Literals.EROOT_CHANGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getUri() {
        return uri;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUri(String newUri) {
        String oldUri = uri;
        uri = newUri;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RootPackage.EROOT_CHANGE__URI, oldUri, uri));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case RootPackage.EROOT_CHANGE__URI:
                return getUri();
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
            case RootPackage.EROOT_CHANGE__URI:
                setUri((String)newValue);
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
            case RootPackage.EROOT_CHANGE__URI:
                setUri(URI_EDEFAULT);
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
            case RootPackage.EROOT_CHANGE__URI:
                return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
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
        result.append(" (uri: ");
        result.append(uri);
        result.append(')');
        return result.toString();
    }

} //ERootChangeImpl
