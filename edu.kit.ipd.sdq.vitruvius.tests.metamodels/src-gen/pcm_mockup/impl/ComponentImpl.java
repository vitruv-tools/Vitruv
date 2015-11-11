/**
 */
package pcm_mockup.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import pcm_mockup.Component;
import pcm_mockup.Interface;
import pcm_mockup.PNamedElement;
import pcm_mockup.Pcm_mockupPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link pcm_mockup.impl.ComponentImpl#getName <em>Name</em>}</li>
 *   <li>{@link pcm_mockup.impl.ComponentImpl#getProvidedInterface <em>Provided Interface</em>}</li>
 *   <li>{@link pcm_mockup.impl.ComponentImpl#getRepositoryFactor <em>Repository Factor</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComponentImpl extends IdentifiedImpl implements Component {
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
	 * The cached value of the '{@link #getProvidedInterface() <em>Provided Interface</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvidedInterface()
	 * @generated
	 * @ordered
	 */
	protected Interface providedInterface;
	/**
	 * The default value of the '{@link #getRepositoryFactor() <em>Repository Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositoryFactor()
	 * @generated
	 * @ordered
	 */
	protected static final int REPOSITORY_FACTOR_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getRepositoryFactor() <em>Repository Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositoryFactor()
	 * @generated
	 * @ordered
	 */
	protected int repositoryFactor = REPOSITORY_FACTOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Pcm_mockupPackage.Literals.COMPONENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, Pcm_mockupPackage.COMPONENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interface getProvidedInterface() {
		if (providedInterface != null && providedInterface.eIsProxy()) {
			InternalEObject oldProvidedInterface = (InternalEObject)providedInterface;
			providedInterface = (Interface)eResolveProxy(oldProvidedInterface);
			if (providedInterface != oldProvidedInterface) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, Pcm_mockupPackage.COMPONENT__PROVIDED_INTERFACE, oldProvidedInterface, providedInterface));
			}
		}
		return providedInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interface basicGetProvidedInterface() {
		return providedInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvidedInterface(Interface newProvidedInterface) {
		Interface oldProvidedInterface = providedInterface;
		providedInterface = newProvidedInterface;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Pcm_mockupPackage.COMPONENT__PROVIDED_INTERFACE, oldProvidedInterface, providedInterface));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getRepositoryFactor() {
		return repositoryFactor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRepositoryFactor(int newRepositoryFactor) {
		int oldRepositoryFactor = repositoryFactor;
		repositoryFactor = newRepositoryFactor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Pcm_mockupPackage.COMPONENT__REPOSITORY_FACTOR, oldRepositoryFactor, repositoryFactor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Pcm_mockupPackage.COMPONENT__NAME:
				return getName();
			case Pcm_mockupPackage.COMPONENT__PROVIDED_INTERFACE:
				if (resolve) return getProvidedInterface();
				return basicGetProvidedInterface();
			case Pcm_mockupPackage.COMPONENT__REPOSITORY_FACTOR:
				return getRepositoryFactor();
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
			case Pcm_mockupPackage.COMPONENT__NAME:
				setName((String)newValue);
				return;
			case Pcm_mockupPackage.COMPONENT__PROVIDED_INTERFACE:
				setProvidedInterface((Interface)newValue);
				return;
			case Pcm_mockupPackage.COMPONENT__REPOSITORY_FACTOR:
				setRepositoryFactor((Integer)newValue);
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
			case Pcm_mockupPackage.COMPONENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case Pcm_mockupPackage.COMPONENT__PROVIDED_INTERFACE:
				setProvidedInterface((Interface)null);
				return;
			case Pcm_mockupPackage.COMPONENT__REPOSITORY_FACTOR:
				setRepositoryFactor(REPOSITORY_FACTOR_EDEFAULT);
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
			case Pcm_mockupPackage.COMPONENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case Pcm_mockupPackage.COMPONENT__PROVIDED_INTERFACE:
				return providedInterface != null;
			case Pcm_mockupPackage.COMPONENT__REPOSITORY_FACTOR:
				return repositoryFactor != REPOSITORY_FACTOR_EDEFAULT;
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
		if (baseClass == PNamedElement.class) {
			switch (derivedFeatureID) {
				case Pcm_mockupPackage.COMPONENT__NAME: return Pcm_mockupPackage.PNAMED_ELEMENT__NAME;
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
		if (baseClass == PNamedElement.class) {
			switch (baseFeatureID) {
				case Pcm_mockupPackage.PNAMED_ELEMENT__NAME: return Pcm_mockupPackage.COMPONENT__NAME;
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
		result.append(", repositoryFactor: ");
		result.append(repositoryFactor);
		result.append(')');
		return result.toString();
	}

} //ComponentImpl
