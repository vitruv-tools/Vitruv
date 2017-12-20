/**
 */
package attribute_to_structure_attr.impl;

import attribute_to_structure_attr.Attribute_to_structure_attrPackage;
import attribute_to_structure_attr.Attributed;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attributed</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link attribute_to_structure_attr.impl.AttributedImpl#getName <em>Name</em>}</li>
 *   <li>{@link attribute_to_structure_attr.impl.AttributedImpl#getIntAttr <em>Int Attr</em>}</li>
 *   <li>{@link attribute_to_structure_attr.impl.AttributedImpl#getStrAttr <em>Str Attr</em>}</li>
 *   <li>{@link attribute_to_structure_attr.impl.AttributedImpl#getFloatAttr <em>Float Attr</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributedImpl extends IdentifiedImpl implements Attributed {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getIntAttr() <em>Int Attr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntAttr()
	 * @generated
	 * @ordered
	 */
	protected static final Integer INT_ATTR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIntAttr() <em>Int Attr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntAttr()
	 * @generated
	 * @ordered
	 */
	protected Integer intAttr = INT_ATTR_EDEFAULT;

	/**
	 * The default value of the '{@link #getStrAttr() <em>Str Attr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrAttr()
	 * @generated
	 * @ordered
	 */
	protected static final String STR_ATTR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStrAttr() <em>Str Attr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrAttr()
	 * @generated
	 * @ordered
	 */
	protected String strAttr = STR_ATTR_EDEFAULT;

	/**
	 * The default value of the '{@link #getFloatAttr() <em>Float Attr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatAttr()
	 * @generated
	 * @ordered
	 */
	protected static final Float FLOAT_ATTR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFloatAttr() <em>Float Attr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatAttr()
	 * @generated
	 * @ordered
	 */
	protected Float floatAttr = FLOAT_ATTR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributedImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Attribute_to_structure_attrPackage.Literals.ATTRIBUTED;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Attribute_to_structure_attrPackage.ATTRIBUTED__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getIntAttr() {
		return intAttr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntAttr(Integer newIntAttr) {
		Integer oldIntAttr = intAttr;
		intAttr = newIntAttr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Attribute_to_structure_attrPackage.ATTRIBUTED__INT_ATTR, oldIntAttr, intAttr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStrAttr() {
		return strAttr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStrAttr(String newStrAttr) {
		String oldStrAttr = strAttr;
		strAttr = newStrAttr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Attribute_to_structure_attrPackage.ATTRIBUTED__STR_ATTR, oldStrAttr, strAttr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Float getFloatAttr() {
		return floatAttr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFloatAttr(Float newFloatAttr) {
		Float oldFloatAttr = floatAttr;
		floatAttr = newFloatAttr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Attribute_to_structure_attrPackage.ATTRIBUTED__FLOAT_ATTR, oldFloatAttr, floatAttr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Attribute_to_structure_attrPackage.ATTRIBUTED__NAME:
				return getName();
			case Attribute_to_structure_attrPackage.ATTRIBUTED__INT_ATTR:
				return getIntAttr();
			case Attribute_to_structure_attrPackage.ATTRIBUTED__STR_ATTR:
				return getStrAttr();
			case Attribute_to_structure_attrPackage.ATTRIBUTED__FLOAT_ATTR:
				return getFloatAttr();
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
			case Attribute_to_structure_attrPackage.ATTRIBUTED__NAME:
				setName((String)newValue);
				return;
			case Attribute_to_structure_attrPackage.ATTRIBUTED__INT_ATTR:
				setIntAttr((Integer)newValue);
				return;
			case Attribute_to_structure_attrPackage.ATTRIBUTED__STR_ATTR:
				setStrAttr((String)newValue);
				return;
			case Attribute_to_structure_attrPackage.ATTRIBUTED__FLOAT_ATTR:
				setFloatAttr((Float)newValue);
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
			case Attribute_to_structure_attrPackage.ATTRIBUTED__NAME:
				setName(NAME_EDEFAULT);
				return;
			case Attribute_to_structure_attrPackage.ATTRIBUTED__INT_ATTR:
				setIntAttr(INT_ATTR_EDEFAULT);
				return;
			case Attribute_to_structure_attrPackage.ATTRIBUTED__STR_ATTR:
				setStrAttr(STR_ATTR_EDEFAULT);
				return;
			case Attribute_to_structure_attrPackage.ATTRIBUTED__FLOAT_ATTR:
				setFloatAttr(FLOAT_ATTR_EDEFAULT);
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
			case Attribute_to_structure_attrPackage.ATTRIBUTED__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case Attribute_to_structure_attrPackage.ATTRIBUTED__INT_ATTR:
				return INT_ATTR_EDEFAULT == null ? intAttr != null : !INT_ATTR_EDEFAULT.equals(intAttr);
			case Attribute_to_structure_attrPackage.ATTRIBUTED__STR_ATTR:
				return STR_ATTR_EDEFAULT == null ? strAttr != null : !STR_ATTR_EDEFAULT.equals(strAttr);
			case Attribute_to_structure_attrPackage.ATTRIBUTED__FLOAT_ATTR:
				return FLOAT_ATTR_EDEFAULT == null ? floatAttr != null : !FLOAT_ATTR_EDEFAULT.equals(floatAttr);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", intAttr: ");
		result.append(intAttr);
		result.append(", strAttr: ");
		result.append(strAttr);
		result.append(", floatAttr: ");
		result.append(floatAttr);
		result.append(')');
		return result.toString();
	}

} //AttributedImpl
