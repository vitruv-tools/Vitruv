/**
 */
package SimpleAtoBTest_SimplestB.impl;

import SimpleAtoBTest_SimplestB.B;
import SimpleAtoBTest_SimplestB.BChild;
import SimpleAtoBTest_SimplestB.SimpleAtoBTest_SimplestBPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>B</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link SimpleAtoBTest_SimplestB.impl.BImpl#getB_name <em>Bname</em>}</li>
 *   <li>{@link SimpleAtoBTest_SimplestB.impl.BImpl#getB_children <em>Bchildren</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BImpl extends MinimalEObjectImpl.Container implements B {
	/**
	 * The default value of the '{@link #getB_name() <em>Bname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getB_name()
	 * @generated
	 * @ordered
	 */
	protected static final String BNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getB_name() <em>Bname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getB_name()
	 * @generated
	 * @ordered
	 */
	protected String b_name = BNAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getB_children() <em>Bchildren</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getB_children()
	 * @generated
	 * @ordered
	 */
	protected EList<BChild> b_children;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimpleAtoBTest_SimplestBPackage.Literals.B;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getB_name() {
		return b_name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setB_name(String newB_name) {
		String oldB_name = b_name;
		b_name = newB_name;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimpleAtoBTest_SimplestBPackage.B__BNAME, oldB_name, b_name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BChild> getB_children() {
		if (b_children == null) {
			b_children = new EObjectResolvingEList<BChild>(BChild.class, this, SimpleAtoBTest_SimplestBPackage.B__BCHILDREN);
		}
		return b_children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SimpleAtoBTest_SimplestBPackage.B__BNAME:
				return getB_name();
			case SimpleAtoBTest_SimplestBPackage.B__BCHILDREN:
				return getB_children();
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
			case SimpleAtoBTest_SimplestBPackage.B__BNAME:
				setB_name((String)newValue);
				return;
			case SimpleAtoBTest_SimplestBPackage.B__BCHILDREN:
				getB_children().clear();
				getB_children().addAll((Collection<? extends BChild>)newValue);
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
			case SimpleAtoBTest_SimplestBPackage.B__BNAME:
				setB_name(BNAME_EDEFAULT);
				return;
			case SimpleAtoBTest_SimplestBPackage.B__BCHILDREN:
				getB_children().clear();
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
			case SimpleAtoBTest_SimplestBPackage.B__BNAME:
				return BNAME_EDEFAULT == null ? b_name != null : !BNAME_EDEFAULT.equals(b_name);
			case SimpleAtoBTest_SimplestBPackage.B__BCHILDREN:
				return b_children != null && !b_children.isEmpty();
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
		result.append(" (b_name: ");
		result.append(b_name);
		result.append(')');
		return result.toString();
	}

} //BImpl
