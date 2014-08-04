/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateMultiValuedEFeature;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.UpdateSingleEListEntry;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.UpdateContainmentEReference;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.DeleteEObjectImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delete Non Root EObject In List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectInListImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectInListImpl#getIndex <em>Index</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectInListImpl#getRemovedObjectURIFragment <em>Removed Object URI Fragment</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectInListImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.DeleteNonRootEObjectInListImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeleteNonRootEObjectInListImpl<T extends EObject> extends DeleteEObjectImpl<T> implements DeleteNonRootEObjectInList<T> {
	/**
	 * The cached value of the '{@link #getOldValue() <em>Old Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldValue()
	 * @generated
	 * @ordered
	 */
	protected T oldValue;

	/**
	 * The default value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected int index = INDEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getRemovedObjectURIFragment() <em>Removed Object URI Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemovedObjectURIFragment()
	 * @generated
	 * @ordered
	 */
	protected static final String REMOVED_OBJECT_URI_FRAGMENT_EDEFAULT = "0";

	/**
	 * The cached value of the '{@link #getRemovedObjectURIFragment() <em>Removed Object URI Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemovedObjectURIFragment()
	 * @generated
	 * @ordered
	 */
	protected String removedObjectURIFragment = REMOVED_OBJECT_URI_FRAGMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAffectedFeature() <em>Affected Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffectedFeature()
	 * @generated
	 * @ordered
	 */
	protected EReference affectedFeature;

	/**
	 * The cached value of the '{@link #getAffectedEObject() <em>Affected EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffectedEObject()
	 * @generated
	 * @ordered
	 */
	protected EObject affectedEObject;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeleteNonRootEObjectInListImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ContainmentPackage.Literals.DELETE_NON_ROOT_EOBJECT_IN_LIST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T getOldValue() {
		return oldValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldValue(T newOldValue) {
		T oldOldValue = oldValue;
		oldValue = newOldValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE, oldOldValue, oldValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndex(int newIndex) {
		int oldIndex = index;
		index = newIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__INDEX, oldIndex, index));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRemovedObjectURIFragment() {
		return removedObjectURIFragment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemovedObjectURIFragment(String newRemovedObjectURIFragment) {
		String oldRemovedObjectURIFragment = removedObjectURIFragment;
		removedObjectURIFragment = newRemovedObjectURIFragment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__REMOVED_OBJECT_URI_FRAGMENT, oldRemovedObjectURIFragment, removedObjectURIFragment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAffectedFeature() {
		if (affectedFeature != null && affectedFeature.eIsProxy()) {
			InternalEObject oldAffectedFeature = (InternalEObject)affectedFeature;
			affectedFeature = (EReference)eResolveProxy(oldAffectedFeature);
			if (affectedFeature != oldAffectedFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
			}
		}
		return affectedFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference basicGetAffectedFeature() {
		return affectedFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAffectedFeature(EReference newAffectedFeature) {
		EReference oldAffectedFeature = affectedFeature;
		affectedFeature = newAffectedFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getAffectedEObject() {
		if (affectedEObject != null && affectedEObject.eIsProxy()) {
			InternalEObject oldAffectedEObject = (InternalEObject)affectedEObject;
			affectedEObject = eResolveProxy(oldAffectedEObject);
			if (affectedEObject != oldAffectedEObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
			}
		}
		return affectedEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetAffectedEObject() {
		return affectedEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAffectedEObject(EObject newAffectedEObject) {
		EObject oldAffectedEObject = affectedEObject;
		affectedEObject = newAffectedEObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void EOperation0() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE:
				return getOldValue();
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__INDEX:
				return getIndex();
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__REMOVED_OBJECT_URI_FRAGMENT:
				return getRemovedObjectURIFragment();
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE:
				if (resolve) return getAffectedFeature();
				return basicGetAffectedFeature();
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT:
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
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE:
				setOldValue((T)newValue);
				return;
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__INDEX:
				setIndex((Integer)newValue);
				return;
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__REMOVED_OBJECT_URI_FRAGMENT:
				setRemovedObjectURIFragment((String)newValue);
				return;
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE:
				setAffectedFeature((EReference)newValue);
				return;
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT:
				setAffectedEObject((EObject)newValue);
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
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE:
				setOldValue((T)null);
				return;
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__INDEX:
				setIndex(INDEX_EDEFAULT);
				return;
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__REMOVED_OBJECT_URI_FRAGMENT:
				setRemovedObjectURIFragment(REMOVED_OBJECT_URI_FRAGMENT_EDEFAULT);
				return;
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE:
				setAffectedFeature((EReference)null);
				return;
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT:
				setAffectedEObject((EObject)null);
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
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE:
				return oldValue != null;
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__INDEX:
				return index != INDEX_EDEFAULT;
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__REMOVED_OBJECT_URI_FRAGMENT:
				return REMOVED_OBJECT_URI_FRAGMENT_EDEFAULT == null ? removedObjectURIFragment != null : !REMOVED_OBJECT_URI_FRAGMENT_EDEFAULT.equals(removedObjectURIFragment);
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE:
				return affectedFeature != null;
			case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT:
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == UpdateEFeature.class) {
			switch (derivedFeatureID) {
				case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE: return FeaturePackage.UPDATE_EFEATURE__OLD_VALUE;
				default: return -1;
			}
		}
		if (baseClass == UpdateMultiValuedEFeature.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == UpdateSingleEListEntry.class) {
			switch (derivedFeatureID) {
				case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__INDEX: return ListPackage.UPDATE_SINGLE_ELIST_ENTRY__INDEX;
				default: return -1;
			}
		}
		if (baseClass == RemoveFromEList.class) {
			switch (derivedFeatureID) {
				case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__REMOVED_OBJECT_URI_FRAGMENT: return ListPackage.REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT;
				default: return -1;
			}
		}
		if (baseClass == EFeatureChange.class) {
			switch (derivedFeatureID) {
				case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
				case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;
				default: return -1;
			}
		}
		if (baseClass == UpdateEReference.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == UpdateContainmentEReference.class) {
			switch (derivedFeatureID) {
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
		if (baseClass == UpdateEFeature.class) {
			switch (baseFeatureID) {
				case FeaturePackage.UPDATE_EFEATURE__OLD_VALUE: return ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE;
				default: return -1;
			}
		}
		if (baseClass == UpdateMultiValuedEFeature.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == UpdateSingleEListEntry.class) {
			switch (baseFeatureID) {
				case ListPackage.UPDATE_SINGLE_ELIST_ENTRY__INDEX: return ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__INDEX;
				default: return -1;
			}
		}
		if (baseClass == RemoveFromEList.class) {
			switch (baseFeatureID) {
				case ListPackage.REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT: return ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__REMOVED_OBJECT_URI_FRAGMENT;
				default: return -1;
			}
		}
		if (baseClass == EFeatureChange.class) {
			switch (baseFeatureID) {
				case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE: return ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE;
				case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT: return ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT;
				default: return -1;
			}
		}
		if (baseClass == UpdateEReference.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == UpdateContainmentEReference.class) {
			switch (baseFeatureID) {
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
		result.append(" (oldValue: ");
		result.append(oldValue);
		result.append(", index: ");
		result.append(index);
		result.append(", removedObjectURIFragment: ");
		result.append(removedObjectURIFragment);
		result.append(')');
		return result.toString();
	}

} //DeleteNonRootEObjectInListImpl
