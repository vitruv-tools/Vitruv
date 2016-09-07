/**
 */
package uml_mockup.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import uml_mockup.UClass;
import uml_mockup.UInterface;
import uml_mockup.UNamedElement;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>UPackage</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uml_mockup.impl.UPackageImpl#getName <em>Name</em>}</li>
 *   <li>{@link uml_mockup.impl.UPackageImpl#getInterfaces <em>Interfaces</em>}</li>
 *   <li>{@link uml_mockup.impl.UPackageImpl#getClasses <em>Classes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UPackageImpl extends IdentifiedImpl implements UPackage {
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
	 * The cached value of the '{@link #getInterfaces() <em>Interfaces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterfaces()
	 * @generated
	 * @ordered
	 */
	protected EList<UInterface> interfaces;

	/**
	 * The cached value of the '{@link #getClasses() <em>Classes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClasses()
	 * @generated
	 * @ordered
	 */
	protected EList<UClass> classes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UPackageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Uml_mockupPackage.Literals.UPACKAGE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, Uml_mockupPackage.UPACKAGE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UInterface> getInterfaces() {
		if (interfaces == null) {
			interfaces = new EObjectContainmentEList<UInterface>(UInterface.class, this, Uml_mockupPackage.UPACKAGE__INTERFACES);
		}
		return interfaces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UClass> getClasses() {
		if (classes == null) {
			classes = new EObjectContainmentEList<UClass>(UClass.class, this, Uml_mockupPackage.UPACKAGE__CLASSES);
		}
		return classes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case Uml_mockupPackage.UPACKAGE__INTERFACES:
				return ((InternalEList<?>)getInterfaces()).basicRemove(otherEnd, msgs);
			case Uml_mockupPackage.UPACKAGE__CLASSES:
				return ((InternalEList<?>)getClasses()).basicRemove(otherEnd, msgs);
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
			case Uml_mockupPackage.UPACKAGE__NAME:
				return getName();
			case Uml_mockupPackage.UPACKAGE__INTERFACES:
				return getInterfaces();
			case Uml_mockupPackage.UPACKAGE__CLASSES:
				return getClasses();
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
			case Uml_mockupPackage.UPACKAGE__NAME:
				setName((String)newValue);
				return;
			case Uml_mockupPackage.UPACKAGE__INTERFACES:
				getInterfaces().clear();
				getInterfaces().addAll((Collection<? extends UInterface>)newValue);
				return;
			case Uml_mockupPackage.UPACKAGE__CLASSES:
				getClasses().clear();
				getClasses().addAll((Collection<? extends UClass>)newValue);
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
			case Uml_mockupPackage.UPACKAGE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case Uml_mockupPackage.UPACKAGE__INTERFACES:
				getInterfaces().clear();
				return;
			case Uml_mockupPackage.UPACKAGE__CLASSES:
				getClasses().clear();
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
			case Uml_mockupPackage.UPACKAGE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case Uml_mockupPackage.UPACKAGE__INTERFACES:
				return interfaces != null && !interfaces.isEmpty();
			case Uml_mockupPackage.UPACKAGE__CLASSES:
				return classes != null && !classes.isEmpty();
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
				case Uml_mockupPackage.UPACKAGE__NAME: return Uml_mockupPackage.UNAMED_ELEMENT__NAME;
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
				case Uml_mockupPackage.UNAMED_ELEMENT__NAME: return Uml_mockupPackage.UPACKAGE__NAME;
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
		result.append(')');
		return result.toString();
	}

} //UPackageImpl
