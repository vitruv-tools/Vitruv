/**
 */
package uml_mockup.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import uml_mockup.UAttribute;
import uml_mockup.UClass;
import uml_mockup.UNamedElement;
import uml_mockup.Uml_mockupPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>UClass</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uml_mockup.impl.UClassImpl#getName <em>Name</em>}</li>
 *   <li>{@link uml_mockup.impl.UClassImpl#getClassCount <em>Class Count</em>}</li>
 *   <li>{@link uml_mockup.impl.UClassImpl#getAttributes <em>Attributes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UClassImpl extends IdentifiedImpl implements UClass {
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
	 * The default value of the '{@link #getClassCount() <em>Class Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassCount()
	 * @generated
	 * @ordered
	 */
	protected static final int CLASS_COUNT_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getClassCount() <em>Class Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassCount()
	 * @generated
	 * @ordered
	 */
	protected int classCount = CLASS_COUNT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<UAttribute> attributes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Uml_mockupPackage.Literals.UCLASS;
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
			eNotify(new ENotificationImpl(this, Notification.SET, Uml_mockupPackage.UCLASS__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getClassCount() {
		return classCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClassCount(int newClassCount) {
		int oldClassCount = classCount;
		classCount = newClassCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Uml_mockupPackage.UCLASS__CLASS_COUNT, oldClassCount, classCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectResolvingEList<UAttribute>(UAttribute.class, this, Uml_mockupPackage.UCLASS__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Uml_mockupPackage.UCLASS__NAME:
				return getName();
			case Uml_mockupPackage.UCLASS__CLASS_COUNT:
				return getClassCount();
			case Uml_mockupPackage.UCLASS__ATTRIBUTES:
				return getAttributes();
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
			case Uml_mockupPackage.UCLASS__NAME:
				setName((String)newValue);
				return;
			case Uml_mockupPackage.UCLASS__CLASS_COUNT:
				setClassCount((Integer)newValue);
				return;
			case Uml_mockupPackage.UCLASS__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends UAttribute>)newValue);
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
			case Uml_mockupPackage.UCLASS__NAME:
				setName(NAME_EDEFAULT);
				return;
			case Uml_mockupPackage.UCLASS__CLASS_COUNT:
				setClassCount(CLASS_COUNT_EDEFAULT);
				return;
			case Uml_mockupPackage.UCLASS__ATTRIBUTES:
				getAttributes().clear();
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
			case Uml_mockupPackage.UCLASS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case Uml_mockupPackage.UCLASS__CLASS_COUNT:
				return classCount != CLASS_COUNT_EDEFAULT;
			case Uml_mockupPackage.UCLASS__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == UNamedElement.class) {
			switch (derivedFeatureID) {
				case Uml_mockupPackage.UCLASS__NAME: return Uml_mockupPackage.UNAMED_ELEMENT__NAME;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == UNamedElement.class) {
			switch (baseFeatureID) {
				case Uml_mockupPackage.UNAMED_ELEMENT__NAME: return Uml_mockupPackage.UCLASS__NAME;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(", classCount: ");
		result.append(classCount);
		result.append(')');
		return result.toString();
	}

} //UClassImpl
