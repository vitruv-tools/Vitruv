/**
 */
package attribute_to_structure_struct_1.impl;

import attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package;
import attribute_to_structure_struct_1.FloatContainer;
import attribute_to_structure_struct_1.IntContainer;
import attribute_to_structure_struct_1.StrContainer;
import attribute_to_structure_struct_1.Structured;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Structured</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link attribute_to_structure_struct_1.impl.StructuredImpl#getName <em>Name</em>}</li>
 *   <li>{@link attribute_to_structure_struct_1.impl.StructuredImpl#getIntContainer <em>Int Container</em>}</li>
 *   <li>{@link attribute_to_structure_struct_1.impl.StructuredImpl#getStrContainer <em>Str Container</em>}</li>
 *   <li>{@link attribute_to_structure_struct_1.impl.StructuredImpl#getFloatContainer <em>Float Container</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StructuredImpl extends IdentifiedImpl implements Structured {
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
	 * The cached value of the '{@link #getIntContainer() <em>Int Container</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntContainer()
	 * @generated
	 * @ordered
	 */
	protected IntContainer intContainer;

	/**
	 * The cached value of the '{@link #getStrContainer() <em>Str Container</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrContainer()
	 * @generated
	 * @ordered
	 */
	protected StrContainer strContainer;

	/**
	 * The cached value of the '{@link #getFloatContainer() <em>Float Container</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatContainer()
	 * @generated
	 * @ordered
	 */
	protected FloatContainer floatContainer;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StructuredImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Attribute_to_structure_struct_1Package.Literals.STRUCTURED;
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
			eNotify(new ENotificationImpl(this, Notification.SET, Attribute_to_structure_struct_1Package.STRUCTURED__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntContainer getIntContainer() {
		return intContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIntContainer(IntContainer newIntContainer, NotificationChain msgs) {
		IntContainer oldIntContainer = intContainer;
		intContainer = newIntContainer;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Attribute_to_structure_struct_1Package.STRUCTURED__INT_CONTAINER, oldIntContainer, newIntContainer);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntContainer(IntContainer newIntContainer) {
		if (newIntContainer != intContainer) {
			NotificationChain msgs = null;
			if (intContainer != null)
				msgs = ((InternalEObject)intContainer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Attribute_to_structure_struct_1Package.STRUCTURED__INT_CONTAINER, null, msgs);
			if (newIntContainer != null)
				msgs = ((InternalEObject)newIntContainer).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Attribute_to_structure_struct_1Package.STRUCTURED__INT_CONTAINER, null, msgs);
			msgs = basicSetIntContainer(newIntContainer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Attribute_to_structure_struct_1Package.STRUCTURED__INT_CONTAINER, newIntContainer, newIntContainer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StrContainer getStrContainer() {
		return strContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStrContainer(StrContainer newStrContainer, NotificationChain msgs) {
		StrContainer oldStrContainer = strContainer;
		strContainer = newStrContainer;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Attribute_to_structure_struct_1Package.STRUCTURED__STR_CONTAINER, oldStrContainer, newStrContainer);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStrContainer(StrContainer newStrContainer) {
		if (newStrContainer != strContainer) {
			NotificationChain msgs = null;
			if (strContainer != null)
				msgs = ((InternalEObject)strContainer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Attribute_to_structure_struct_1Package.STRUCTURED__STR_CONTAINER, null, msgs);
			if (newStrContainer != null)
				msgs = ((InternalEObject)newStrContainer).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Attribute_to_structure_struct_1Package.STRUCTURED__STR_CONTAINER, null, msgs);
			msgs = basicSetStrContainer(newStrContainer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Attribute_to_structure_struct_1Package.STRUCTURED__STR_CONTAINER, newStrContainer, newStrContainer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FloatContainer getFloatContainer() {
		return floatContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFloatContainer(FloatContainer newFloatContainer, NotificationChain msgs) {
		FloatContainer oldFloatContainer = floatContainer;
		floatContainer = newFloatContainer;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Attribute_to_structure_struct_1Package.STRUCTURED__FLOAT_CONTAINER, oldFloatContainer, newFloatContainer);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFloatContainer(FloatContainer newFloatContainer) {
		if (newFloatContainer != floatContainer) {
			NotificationChain msgs = null;
			if (floatContainer != null)
				msgs = ((InternalEObject)floatContainer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Attribute_to_structure_struct_1Package.STRUCTURED__FLOAT_CONTAINER, null, msgs);
			if (newFloatContainer != null)
				msgs = ((InternalEObject)newFloatContainer).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Attribute_to_structure_struct_1Package.STRUCTURED__FLOAT_CONTAINER, null, msgs);
			msgs = basicSetFloatContainer(newFloatContainer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Attribute_to_structure_struct_1Package.STRUCTURED__FLOAT_CONTAINER, newFloatContainer, newFloatContainer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case Attribute_to_structure_struct_1Package.STRUCTURED__INT_CONTAINER:
				return basicSetIntContainer(null, msgs);
			case Attribute_to_structure_struct_1Package.STRUCTURED__STR_CONTAINER:
				return basicSetStrContainer(null, msgs);
			case Attribute_to_structure_struct_1Package.STRUCTURED__FLOAT_CONTAINER:
				return basicSetFloatContainer(null, msgs);
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
			case Attribute_to_structure_struct_1Package.STRUCTURED__NAME:
				return getName();
			case Attribute_to_structure_struct_1Package.STRUCTURED__INT_CONTAINER:
				return getIntContainer();
			case Attribute_to_structure_struct_1Package.STRUCTURED__STR_CONTAINER:
				return getStrContainer();
			case Attribute_to_structure_struct_1Package.STRUCTURED__FLOAT_CONTAINER:
				return getFloatContainer();
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
			case Attribute_to_structure_struct_1Package.STRUCTURED__NAME:
				setName((String)newValue);
				return;
			case Attribute_to_structure_struct_1Package.STRUCTURED__INT_CONTAINER:
				setIntContainer((IntContainer)newValue);
				return;
			case Attribute_to_structure_struct_1Package.STRUCTURED__STR_CONTAINER:
				setStrContainer((StrContainer)newValue);
				return;
			case Attribute_to_structure_struct_1Package.STRUCTURED__FLOAT_CONTAINER:
				setFloatContainer((FloatContainer)newValue);
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
			case Attribute_to_structure_struct_1Package.STRUCTURED__NAME:
				setName(NAME_EDEFAULT);
				return;
			case Attribute_to_structure_struct_1Package.STRUCTURED__INT_CONTAINER:
				setIntContainer((IntContainer)null);
				return;
			case Attribute_to_structure_struct_1Package.STRUCTURED__STR_CONTAINER:
				setStrContainer((StrContainer)null);
				return;
			case Attribute_to_structure_struct_1Package.STRUCTURED__FLOAT_CONTAINER:
				setFloatContainer((FloatContainer)null);
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
			case Attribute_to_structure_struct_1Package.STRUCTURED__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case Attribute_to_structure_struct_1Package.STRUCTURED__INT_CONTAINER:
				return intContainer != null;
			case Attribute_to_structure_struct_1Package.STRUCTURED__STR_CONTAINER:
				return strContainer != null;
			case Attribute_to_structure_struct_1Package.STRUCTURED__FLOAT_CONTAINER:
				return floatContainer != null;
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
		result.append(')');
		return result.toString();
	}

} //StructuredImpl
