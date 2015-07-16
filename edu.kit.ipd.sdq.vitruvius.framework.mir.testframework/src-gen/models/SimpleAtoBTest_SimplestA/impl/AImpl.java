/**
 */
package SimpleAtoBTest_SimplestA.impl;

import SimpleAtoBTest_SimplestA.A;
import SimpleAtoBTest_SimplestA.AChild;
import SimpleAtoBTest_SimplestA.SimpleAtoBTest_SimplestAPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>A</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link SimpleAtoBTest_SimplestA.impl.AImpl#getA_name <em>Aname</em>}</li>
 *   <li>{@link SimpleAtoBTest_SimplestA.impl.AImpl#getA_children <em>Achildren</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AImpl extends MinimalEObjectImpl.Container implements A {
	/**
	 * The default value of the '{@link #getA_name() <em>Aname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getA_name()
	 * @generated
	 * @ordered
	 */
	protected static final String ANAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getA_name() <em>Aname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getA_name()
	 * @generated
	 * @ordered
	 */
	protected String a_name = ANAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getA_children() <em>Achildren</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getA_children()
	 * @generated
	 * @ordered
	 */
	protected EList<AChild> a_children;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimpleAtoBTest_SimplestAPackage.Literals.A;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getA_name() {
		return a_name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setA_name(String newA_name) {
		String oldA_name = a_name;
		a_name = newA_name;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimpleAtoBTest_SimplestAPackage.A__ANAME, oldA_name, a_name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AChild> getA_children() {
		if (a_children == null) {
			a_children = new EObjectContainmentEList<AChild>(AChild.class, this, SimpleAtoBTest_SimplestAPackage.A__ACHILDREN);
		}
		return a_children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SimpleAtoBTest_SimplestAPackage.A__ACHILDREN:
				return ((InternalEList<?>)getA_children()).basicRemove(otherEnd, msgs);
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
			case SimpleAtoBTest_SimplestAPackage.A__ANAME:
				return getA_name();
			case SimpleAtoBTest_SimplestAPackage.A__ACHILDREN:
				return getA_children();
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
			case SimpleAtoBTest_SimplestAPackage.A__ANAME:
				setA_name((String)newValue);
				return;
			case SimpleAtoBTest_SimplestAPackage.A__ACHILDREN:
				getA_children().clear();
				getA_children().addAll((Collection<? extends AChild>)newValue);
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
			case SimpleAtoBTest_SimplestAPackage.A__ANAME:
				setA_name(ANAME_EDEFAULT);
				return;
			case SimpleAtoBTest_SimplestAPackage.A__ACHILDREN:
				getA_children().clear();
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
			case SimpleAtoBTest_SimplestAPackage.A__ANAME:
				return ANAME_EDEFAULT == null ? a_name != null : !ANAME_EDEFAULT.equals(a_name);
			case SimpleAtoBTest_SimplestAPackage.A__ACHILDREN:
				return a_children != null && !a_children.isEmpty();
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
		result.append(" (a_name: ");
		result.append(a_name);
		result.append(')');
		return result.toString();
	}

} //AImpl
