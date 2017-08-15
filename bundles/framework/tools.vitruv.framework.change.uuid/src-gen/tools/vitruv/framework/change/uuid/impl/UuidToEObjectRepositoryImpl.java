/**
 */
package tools.vitruv.framework.change.uuid.impl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import tools.vitruv.framework.change.uuid.UuidPackage;
import tools.vitruv.framework.change.uuid.UuidToEObjectRepository;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>To EObject Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.uuid.impl.UuidToEObjectRepositoryImpl#getUuidToEObject <em>Uuid To EObject</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.uuid.impl.UuidToEObjectRepositoryImpl#getEObjectToUuid <em>EObject To Uuid</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UuidToEObjectRepositoryImpl extends MinimalEObjectImpl.Container implements UuidToEObjectRepository {
	/**
	 * The cached value of the '{@link #getUuidToEObject() <em>Uuid To EObject</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUuidToEObject()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, EObject> uuidToEObject;
	/**
	 * The cached value of the '{@link #getEObjectToUuid() <em>EObject To Uuid</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEObjectToUuid()
	 * @generated
	 * @ordered
	 */
	protected EMap<EObject, String> eObjectToUuid;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UuidToEObjectRepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UuidPackage.Literals.UUID_TO_EOBJECT_REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, EObject> getUuidToEObject() {
		if (uuidToEObject == null) {
			uuidToEObject = new EcoreEMap<String,EObject>(UuidPackage.Literals.UUID_TO_EOBJECT_MAP_ENTRY, UuidToEObjectMapEntryImpl.class, this, UuidPackage.UUID_TO_EOBJECT_REPOSITORY__UUID_TO_EOBJECT);
		}
		return uuidToEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<EObject, String> getEObjectToUuid() {
		if (eObjectToUuid == null) {
			eObjectToUuid = new EcoreEMap<EObject,String>(UuidPackage.Literals.EOBJECT_TO_UUID_MAP_ENTRY, EObjectToUuidMapEntryImpl.class, this, UuidPackage.UUID_TO_EOBJECT_REPOSITORY__EOBJECT_TO_UUID);
		}
		return eObjectToUuid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case UuidPackage.UUID_TO_EOBJECT_REPOSITORY__UUID_TO_EOBJECT:
				return ((InternalEList<?>)getUuidToEObject()).basicRemove(otherEnd, msgs);
			case UuidPackage.UUID_TO_EOBJECT_REPOSITORY__EOBJECT_TO_UUID:
				return ((InternalEList<?>)getEObjectToUuid()).basicRemove(otherEnd, msgs);
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
			case UuidPackage.UUID_TO_EOBJECT_REPOSITORY__UUID_TO_EOBJECT:
				if (coreType) return getUuidToEObject();
				else return getUuidToEObject().map();
			case UuidPackage.UUID_TO_EOBJECT_REPOSITORY__EOBJECT_TO_UUID:
				if (coreType) return getEObjectToUuid();
				else return getEObjectToUuid().map();
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
			case UuidPackage.UUID_TO_EOBJECT_REPOSITORY__UUID_TO_EOBJECT:
				((EStructuralFeature.Setting)getUuidToEObject()).set(newValue);
				return;
			case UuidPackage.UUID_TO_EOBJECT_REPOSITORY__EOBJECT_TO_UUID:
				((EStructuralFeature.Setting)getEObjectToUuid()).set(newValue);
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
			case UuidPackage.UUID_TO_EOBJECT_REPOSITORY__UUID_TO_EOBJECT:
				getUuidToEObject().clear();
				return;
			case UuidPackage.UUID_TO_EOBJECT_REPOSITORY__EOBJECT_TO_UUID:
				getEObjectToUuid().clear();
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
			case UuidPackage.UUID_TO_EOBJECT_REPOSITORY__UUID_TO_EOBJECT:
				return uuidToEObject != null && !uuidToEObject.isEmpty();
			case UuidPackage.UUID_TO_EOBJECT_REPOSITORY__EOBJECT_TO_UUID:
				return eObjectToUuid != null && !eObjectToUuid.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //UuidToEObjectRepositoryImpl
