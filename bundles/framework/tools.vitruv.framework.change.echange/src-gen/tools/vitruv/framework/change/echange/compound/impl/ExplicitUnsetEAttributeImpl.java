/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;

import tools.vitruv.framework.change.echange.AtomicEChange;

import tools.vitruv.framework.change.echange.compound.CompoundEChange;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.CompoundSubtraction;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature;

import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Explicit Unset EAttribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEAttributeImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEAttributeImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExplicitUnsetEAttributeImpl<A extends EObject, T extends Object> extends CompoundSubtractionImpl<T, SubtractiveAttributeEChange<A, T>> implements ExplicitUnsetEAttribute<A, T> {
	/**
	 * The cached value of the '{@link #getAffectedEObject() <em>Affected EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffectedEObject()
	 * @generated
	 * @ordered
	 */
	protected A affectedEObject;

	/**
	 * The cached value of the '{@link #getAffectedFeature() <em>Affected Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffectedFeature()
	 * @generated
	 * @ordered
	 */
	protected EAttribute affectedFeature;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExplicitUnsetEAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.EXPLICIT_UNSET_EATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<SubtractiveAttributeEChange<A, T>> getSubtractiveChanges() {
		if (subtractiveChanges == null) {
			subtractiveChanges = new EObjectContainmentEList<SubtractiveAttributeEChange<A, T>>(SubtractiveAttributeEChange.class, this, CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__SUBTRACTIVE_CHANGES);
		}
		return subtractiveChanges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public A getAffectedEObject() {
		if (affectedEObject != null && ((EObject)affectedEObject).eIsProxy()) {
			InternalEObject oldAffectedEObject = (InternalEObject)affectedEObject;
			affectedEObject = (A)eResolveProxy(oldAffectedEObject);
			if (affectedEObject != oldAffectedEObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
			}
		}
		return affectedEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public A basicGetAffectedEObject() {
		return affectedEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAffectedEObject(A newAffectedEObject) {
		A oldAffectedEObject = affectedEObject;
		affectedEObject = newAffectedEObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAffectedFeature() {
		if (affectedFeature != null && affectedFeature.eIsProxy()) {
			InternalEObject oldAffectedFeature = (InternalEObject)affectedFeature;
			affectedFeature = (EAttribute)eResolveProxy(oldAffectedFeature);
			if (affectedFeature != oldAffectedFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
			}
		}
		return affectedFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute basicGetAffectedFeature() {
		return affectedFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAffectedFeature(EAttribute newAffectedFeature) {
		EAttribute oldAffectedFeature = affectedFeature;
		affectedFeature = newAffectedFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AtomicEChange> getAtomicChanges() {
		final BasicEList<AtomicEChange> result = new BasicEList<AtomicEChange>();
		EList<SubtractiveAttributeEChange<A, T>> _subtractiveChanges = this.getSubtractiveChanges();
		result.addAll(_subtractiveChanges);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_EOBJECT:
				if (resolve) return getAffectedEObject();
				return basicGetAffectedEObject();
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_FEATURE:
				if (resolve) return getAffectedFeature();
				return basicGetAffectedFeature();
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
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_EOBJECT:
				setAffectedEObject((A)newValue);
				return;
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_FEATURE:
				setAffectedFeature((EAttribute)newValue);
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
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_EOBJECT:
				setAffectedEObject((A)null);
				return;
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_FEATURE:
				setAffectedFeature((EAttribute)null);
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
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_EOBJECT:
				return affectedEObject != null;
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_FEATURE:
				return affectedFeature != null;
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
		if (baseClass == ExplicitUnsetEFeature.class) {
			switch (derivedFeatureID) {
				case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_EOBJECT: return CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT;
				case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_FEATURE: return CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_FEATURE;
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
		if (baseClass == ExplicitUnsetEFeature.class) {
			switch (baseFeatureID) {
				case CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT: return CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_EOBJECT;
				case CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_FEATURE: return CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_FEATURE;
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
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == CompoundEChange.class) {
			switch (baseOperationID) {
				case CompoundPackage.COMPOUND_ECHANGE___GET_ATOMIC_CHANGES: return CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE___GET_ATOMIC_CHANGES;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == CompoundSubtraction.class) {
			switch (baseOperationID) {
				case CompoundPackage.COMPOUND_SUBTRACTION___GET_ATOMIC_CHANGES: return CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE___GET_ATOMIC_CHANGES;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ExplicitUnsetEFeature.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE___GET_ATOMIC_CHANGES:
				return getAtomicChanges();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ExplicitUnsetEAttributeImpl
