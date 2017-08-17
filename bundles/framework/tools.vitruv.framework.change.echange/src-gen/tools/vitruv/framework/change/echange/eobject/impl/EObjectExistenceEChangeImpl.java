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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EObject Existence EChange</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.impl.EObjectExistenceEChangeImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.impl.EObjectExistenceEChangeImpl#getIdAttributeValue <em>Id Attribute Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.impl.EObjectExistenceEChangeImpl#getAffectedEObjectID <em>Affected EObject ID</em>}</li>
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
	 * The default value of the '{@link #getIdAttributeValue() <em>Id Attribute Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdAttributeValue()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_ATTRIBUTE_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIdAttributeValue() <em>Id Attribute Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdAttributeValue()
	 * @generated
	 * @ordered
	 */
	protected String idAttributeValue = ID_ATTRIBUTE_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAffectedEObjectID() <em>Affected EObject ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffectedEObjectID()
	 * @generated
	 * @ordered
	 */
	protected static final String AFFECTED_EOBJECT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAffectedEObjectID() <em>Affected EObject ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffectedEObjectID()
	 * @generated
	 * @ordered
	 */
	protected String affectedEObjectID = AFFECTED_EOBJECT_ID_EDEFAULT;

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
		if (affectedEObject != null && affectedEObject.eIsProxy()) {
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
	public String getIdAttributeValue() {
		return idAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIdAttributeValue(String newIdAttributeValue) {
		String oldIdAttributeValue = idAttributeValue;
		idAttributeValue = newIdAttributeValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__ID_ATTRIBUTE_VALUE, oldIdAttributeValue, idAttributeValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAffectedEObjectID() {
		return affectedEObjectID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAffectedEObjectID(String newAffectedEObjectID) {
		String oldAffectedEObjectID = affectedEObjectID;
		affectedEObjectID = newAffectedEObjectID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT_ID, oldAffectedEObjectID, affectedEObjectID));
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
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__ID_ATTRIBUTE_VALUE:
				return getIdAttributeValue();
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT_ID:
				return getAffectedEObjectID();
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
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__ID_ATTRIBUTE_VALUE:
				setIdAttributeValue((String)newValue);
				return;
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT_ID:
				setAffectedEObjectID((String)newValue);
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
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__ID_ATTRIBUTE_VALUE:
				setIdAttributeValue(ID_ATTRIBUTE_VALUE_EDEFAULT);
				return;
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT_ID:
				setAffectedEObjectID(AFFECTED_EOBJECT_ID_EDEFAULT);
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
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__ID_ATTRIBUTE_VALUE:
				return ID_ATTRIBUTE_VALUE_EDEFAULT == null ? idAttributeValue != null : !ID_ATTRIBUTE_VALUE_EDEFAULT.equals(idAttributeValue);
			case EobjectPackage.EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT_ID:
				return AFFECTED_EOBJECT_ID_EDEFAULT == null ? affectedEObjectID != null : !AFFECTED_EOBJECT_ID_EDEFAULT.equals(affectedEObjectID);
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
		result.append(" (idAttributeValue: ");
		result.append(idAttributeValue);
		result.append(", affectedEObjectID: ");
		result.append(affectedEObjectID);
		result.append(')');
		return result.toString();
	}

} //EObjectExistenceEChangeImpl
