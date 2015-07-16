/**
 */
package SimpleAtoBTest_SimplestA.impl;

import SimpleAtoBTest_SimplestA.AChild;
import SimpleAtoBTest_SimplestA.SimpleAtoBTest_SimplestAPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>AChild</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link SimpleAtoBTest_SimplestA.impl.AChildImpl#getAChild_name <em>AChild name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AChildImpl extends MinimalEObjectImpl.Container implements AChild {
	/**
	 * The default value of the '{@link #getAChild_name() <em>AChild name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAChild_name()
	 * @generated
	 * @ordered
	 */
	protected static final String ACHILD_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAChild_name() <em>AChild name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAChild_name()
	 * @generated
	 * @ordered
	 */
	protected String aChild_name = ACHILD_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AChildImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimpleAtoBTest_SimplestAPackage.Literals.ACHILD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAChild_name() {
		return aChild_name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAChild_name(String newAChild_name) {
		String oldAChild_name = aChild_name;
		aChild_name = newAChild_name;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimpleAtoBTest_SimplestAPackage.ACHILD__ACHILD_NAME, oldAChild_name, aChild_name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SimpleAtoBTest_SimplestAPackage.ACHILD__ACHILD_NAME:
				return getAChild_name();
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
			case SimpleAtoBTest_SimplestAPackage.ACHILD__ACHILD_NAME:
				setAChild_name((String)newValue);
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
			case SimpleAtoBTest_SimplestAPackage.ACHILD__ACHILD_NAME:
				setAChild_name(ACHILD_NAME_EDEFAULT);
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
			case SimpleAtoBTest_SimplestAPackage.ACHILD__ACHILD_NAME:
				return ACHILD_NAME_EDEFAULT == null ? aChild_name != null : !ACHILD_NAME_EDEFAULT.equals(aChild_name);
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
		result.append(" (aChild_name: ");
		result.append(aChild_name);
		result.append(')');
		return result.toString();
	}

} //AChildImpl
