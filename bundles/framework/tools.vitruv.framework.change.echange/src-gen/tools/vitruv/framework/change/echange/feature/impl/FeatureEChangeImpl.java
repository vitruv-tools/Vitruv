/**
 */
package tools.vitruv.framework.change.echange.feature.impl;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.emf.ecore.util.EcoreUtil;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.change.echange.feature.FeaturePackage;

import tools.vitruv.framework.change.echange.impl.AtomicEChangeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.impl.FeatureEChangeImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.impl.FeatureEChangeImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class FeatureEChangeImpl<A extends EObject, F extends EStructuralFeature> extends AtomicEChangeImpl implements FeatureEChange<A, F> {
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
	 * The cached value of the '{@link #getAffectedEObject() <em>Affected EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffectedEObject()
	 * @generated
	 * @ordered
	 */
	protected A affectedEObject;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureEChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeaturePackage.Literals.FEATURE_ECHANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public F getAffectedFeature() {
		if (affectedFeature != null && ((EObject)affectedFeature).eIsProxy()) {
			InternalEObject oldAffectedFeature = (InternalEObject)affectedFeature;
			affectedFeature = (F)eResolveProxy(oldAffectedFeature);
			if (affectedFeature != oldAffectedFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isResolved() {
		return ((!Objects.equal(this.getAffectedEObject(), null)) && (!this.getAffectedEObject().eIsProxy()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolve(final ResourceSet resourceSet) {
		if ((Objects.equal(this.getAffectedEObject(), null) || Objects.equal(this.getAffectedFeature(), null))) {
			return null;
		}
		boolean _isResolved = this.isResolved();
		boolean _not = (!_isResolved);
		if (_not) {
			EChange _resolve = super.resolve(resourceSet);
			final FeatureEChange<A, F> resolvedChange = ((FeatureEChange<A, F>) _resolve);
			boolean _equals = Objects.equal(resolvedChange, null);
			if (_equals) {
				return null;
			}
			A _affectedEObject = this.getAffectedEObject();
			EObject _resolve_1 = EcoreUtil.resolve(_affectedEObject, resourceSet);
			resolvedChange.setAffectedEObject(((A) _resolve_1));
			A _affectedEObject_1 = resolvedChange.getAffectedEObject();
			boolean _eIsProxy = _affectedEObject_1.eIsProxy();
			if (_eIsProxy) {
				return this;
			}
			return resolvedChange;
		}
		return this;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE:
				if (resolve) return getAffectedFeature();
				return basicGetAffectedFeature();
			case FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT:
				if (resolve) return getAffectedEObject();
				return basicGetAffectedEObject();
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
			case FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE:
				setAffectedFeature((F)newValue);
				return;
			case FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT:
				setAffectedEObject((A)newValue);
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
			case FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE:
				setAffectedFeature((F)null);
				return;
			case FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT:
				setAffectedEObject((A)null);
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
			case FeaturePackage.FEATURE_ECHANGE__AFFECTED_FEATURE:
				return affectedFeature != null;
			case FeaturePackage.FEATURE_ECHANGE__AFFECTED_EOBJECT:
				return affectedEObject != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == EChange.class) {
			switch (baseOperationID) {
				case EChangePackage.ECHANGE___IS_RESOLVED: return FeaturePackage.FEATURE_ECHANGE___IS_RESOLVED;
				case EChangePackage.ECHANGE___RESOLVE__RESOURCESET: return FeaturePackage.FEATURE_ECHANGE___RESOLVE__RESOURCESET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
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
			case FeaturePackage.FEATURE_ECHANGE___IS_RESOLVED:
				return isResolved();
			case FeaturePackage.FEATURE_ECHANGE___RESOLVE__RESOURCESET:
				return resolve((ResourceSet)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //FeatureEChangeImpl
