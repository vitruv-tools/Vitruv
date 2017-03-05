/**
 */
package tools.vitruv.framework.change.echange.eobject.impl;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.emf.ecore.util.EcoreUtil;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.impl.AtomicEChangeImpl;

import tools.vitruv.framework.change.echange.util.EChangeUtil;
import tools.vitruv.framework.change.echange.util.StagingArea;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EObject Existence EChange</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.impl.EObjectExistenceEChangeImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.impl.EObjectExistenceEChangeImpl#getStagingArea <em>Staging Area</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class EObjectExistenceEChangeImpl<A extends EObject> extends AtomicEChangeImpl implements EObjectExistenceEChange<A> {
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
	 * The default value of the '{@link #getStagingArea() <em>Staging Area</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStagingArea()
	 * @generated
	 * @ordered
	 */
	protected static final Resource STAGING_AREA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStagingArea() <em>Staging Area</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStagingArea()
	 * @generated
	 * @ordered
	 */
	protected Resource stagingArea = STAGING_AREA_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EObjectExistenceEChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EobjectPackage.Literals.EOBJECT_EXISTENCE_ECHANGE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
			eNotify(new ENotificationImpl(this, Notification.SET, EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource getStagingArea() {
		return stagingArea;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStagingArea(Resource newStagingArea) {
		Resource oldStagingArea = stagingArea;
		stagingArea = newStagingArea;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__STAGING_AREA, oldStagingArea, stagingArea));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isResolved() {
		return (((super.isResolved() && (!Objects.equal(this.getAffectedEObject(), null))) && (!this.getAffectedEObject().eIsProxy())) && (!Objects.equal(this.getStagingArea(), null)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EChange resolve(final ResourceSet resourceSet, final boolean newObject) {
		if (((!Objects.equal(this.getAffectedEObject(), null)) && (!this.isResolved()))) {
			EChange _resolveApply = super.resolveApply(resourceSet);
			final EObjectExistenceEChange<A> resolvedChange = ((EObjectExistenceEChange<A>) _resolveApply);
			boolean _equals = Objects.equal(resolvedChange, null);
			if (_equals) {
				return null;
			}
			if (newObject) {
				A _affectedEObject = this.getAffectedEObject();
				A _copy = EcoreUtil.<A>copy(_affectedEObject);
				resolvedChange.setAffectedEObject(_copy);
				A _affectedEObject_1 = resolvedChange.getAffectedEObject();
				((InternalEObject) _affectedEObject_1).eSetProxyURI(null);
			}
			else {
				resolvedChange.setAffectedEObject(((A) EChangeUtil.objectInProgress));
			}
			Resource _stagingArea = StagingArea.getStagingArea(resourceSet);
			resolvedChange.setStagingArea(_stagingArea);
			if ((((!Objects.equal(resolvedChange.getAffectedEObject(), null)) && (!resolvedChange.getAffectedEObject().eIsProxy())) && (!Objects.equal(resolvedChange.getStagingArea(), null)))) {
				if (newObject) {
					A _affectedEObject_2 = resolvedChange.getAffectedEObject();
					EChangeUtil.objectInProgress = _affectedEObject_2;
				}
				else {
					EChangeUtil.objectInProgress = null;
				}
				return resolvedChange;
			}
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
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT:
				if (resolve) return getAffectedEObject();
				return basicGetAffectedEObject();
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__STAGING_AREA:
				return getStagingArea();
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
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT:
				setAffectedEObject((A)newValue);
				return;
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__STAGING_AREA:
				setStagingArea((Resource)newValue);
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
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT:
				setAffectedEObject((A)null);
				return;
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__STAGING_AREA:
				setStagingArea(STAGING_AREA_EDEFAULT);
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
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT:
				return affectedEObject != null;
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__STAGING_AREA:
				return STAGING_AREA_EDEFAULT == null ? stagingArea != null : !STAGING_AREA_EDEFAULT.equals(stagingArea);
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
				case EChangePackage.ECHANGE___IS_RESOLVED: return EobjectPackage.EOBJECT_EXISTENCE_ECHANGE___IS_RESOLVED;
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
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE___IS_RESOLVED:
				return isResolved();
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE___RESOLVE__RESOURCESET_BOOLEAN:
				return resolve((ResourceSet)arguments.get(0), (Boolean)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (stagingArea: ");
		result.append(stagingArea);
		result.append(')');
		return result.toString();
	}

} //EObjectExistenceEChangeImpl
