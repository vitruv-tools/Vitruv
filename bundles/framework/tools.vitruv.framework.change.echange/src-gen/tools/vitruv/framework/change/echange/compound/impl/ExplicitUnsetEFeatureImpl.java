/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Explicit Unset EFeature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ExplicitUnsetEFeatureImpl<A extends EObject, F extends EStructuralFeature> extends CompoundEChangeImpl implements ExplicitUnsetEFeature<A, F> {
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
	protected F affectedFeature;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExplicitUnsetEFeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.EXPLICIT_UNSET_EFEATURE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public F getAffectedFeature() {
		if (affectedFeature != null && affectedFeature.eIsProxy()) {
			InternalEObject oldAffectedFeature = (InternalEObject)affectedFeature;
			affectedFeature = (F)eResolveProxy(oldAffectedFeature);
			if (affectedFeature != oldAffectedFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
			}
		}
		return affectedFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public F basicGetAffectedFeature() {
		return affectedFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAffectedFeature(F newAffectedFeature) {
		F oldAffectedFeature = affectedFeature;
		affectedFeature = newAffectedFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT:
				if (resolve) return getAffectedEObject();
				return basicGetAffectedEObject();
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_FEATURE:
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
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT:
				setAffectedEObject((A)newValue);
				return;
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_FEATURE:
				setAffectedFeature((F)newValue);
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
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT:
				setAffectedEObject((A)null);
				return;
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_FEATURE:
				setAffectedFeature((F)null);
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
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT:
				return affectedEObject != null;
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE__AFFECTED_FEATURE:
				return affectedFeature != null;
		}
		return super.eIsSet(featureID);
	}

} //ExplicitUnsetEFeatureImpl
