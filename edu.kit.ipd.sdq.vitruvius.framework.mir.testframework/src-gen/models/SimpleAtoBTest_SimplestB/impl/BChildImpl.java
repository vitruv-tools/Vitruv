/**
 */
package SimpleAtoBTest_SimplestB.impl;

import SimpleAtoBTest_SimplestB.BChild;
import SimpleAtoBTest_SimplestB.SimpleAtoBTest_SimplestBPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>BChild</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link SimpleAtoBTest_SimplestB.impl.BChildImpl#getBChild_name <em>BChild name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BChildImpl extends MinimalEObjectImpl.Container implements BChild {
	/**
	 * The default value of the '{@link #getBChild_name() <em>BChild name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBChild_name()
	 * @generated
	 * @ordered
	 */
	protected static final String BCHILD_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBChild_name() <em>BChild name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBChild_name()
	 * @generated
	 * @ordered
	 */
	protected String bChild_name = BCHILD_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BChildImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimpleAtoBTest_SimplestBPackage.Literals.BCHILD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBChild_name() {
		return bChild_name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBChild_name(String newBChild_name) {
		String oldBChild_name = bChild_name;
		bChild_name = newBChild_name;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimpleAtoBTest_SimplestBPackage.BCHILD__BCHILD_NAME, oldBChild_name, bChild_name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SimpleAtoBTest_SimplestBPackage.BCHILD__BCHILD_NAME:
				return getBChild_name();
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
			case SimpleAtoBTest_SimplestBPackage.BCHILD__BCHILD_NAME:
				setBChild_name((String)newValue);
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
			case SimpleAtoBTest_SimplestBPackage.BCHILD__BCHILD_NAME:
				setBChild_name(BCHILD_NAME_EDEFAULT);
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
			case SimpleAtoBTest_SimplestBPackage.BCHILD__BCHILD_NAME:
				return BCHILD_NAME_EDEFAULT == null ? bChild_name != null : !BCHILD_NAME_EDEFAULT.equals(bChild_name);
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
		result.append(" (bChild_name: ");
		result.append(bChild_name);
		result.append(')');
		return result.toString();
	}

} //BChildImpl
