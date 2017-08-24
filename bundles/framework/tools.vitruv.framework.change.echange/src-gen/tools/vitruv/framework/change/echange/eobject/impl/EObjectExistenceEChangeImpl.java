/**
 */
package tools.vitruv.framework.change.echange.eobject.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.impl.AtomicEChangeImpl;

import tools.vitruv.framework.change.echange.resolve.StagingArea;

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
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.impl.EObjectExistenceEChangeImpl#getObjectId <em>Object Id</em>}</li>
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
	protected static final StagingArea STAGING_AREA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStagingArea() <em>Staging Area</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStagingArea()
	 * @generated
	 * @ordered
	 */
	protected StagingArea stagingArea = STAGING_AREA_EDEFAULT;

	/**
	 * The default value of the '{@link #getObjectId() <em>Object Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectId()
	 * @generated
	 * @ordered
	 */
	protected static final String OBJECT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getObjectId() <em>Object Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectId()
	 * @generated
	 * @ordered
	 */
	protected String objectId = OBJECT_ID_EDEFAULT;

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
	public StagingArea getStagingArea() {
		return stagingArea;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStagingArea(StagingArea newStagingArea) {
		StagingArea oldStagingArea = stagingArea;
		stagingArea = newStagingArea;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__STAGING_AREA, oldStagingArea, stagingArea));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectId(String newObjectId) {
		String oldObjectId = objectId;
		objectId = newObjectId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__OBJECT_ID, oldObjectId, objectId));
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
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__OBJECT_ID:
				return getObjectId();
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
				setStagingArea((StagingArea)newValue);
				return;
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__OBJECT_ID:
				setObjectId((String)newValue);
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
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__OBJECT_ID:
				setObjectId(OBJECT_ID_EDEFAULT);
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
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__OBJECT_ID:
				return OBJECT_ID_EDEFAULT == null ? objectId != null : !OBJECT_ID_EDEFAULT.equals(objectId);
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
		result.append(" (stagingArea: ");
		result.append(stagingArea);
		result.append(", objectId: ");
		result.append(objectId);
		result.append(')');
		return result.toString();
	}

} //EObjectExistenceEChangeImpl
