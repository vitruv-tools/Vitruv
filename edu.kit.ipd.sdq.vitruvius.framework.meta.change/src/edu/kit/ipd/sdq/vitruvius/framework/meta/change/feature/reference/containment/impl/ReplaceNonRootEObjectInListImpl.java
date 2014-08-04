/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateMultiValuedEFeature;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ReplaceInEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.UpdateSingleEListEntry;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.UpdateContainmentEReference;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ReplaceEObjectImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replace Non Root EObject In List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectInListImpl#getIndex <em>Index</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectInListImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectInListImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectInListImpl#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ReplaceNonRootEObjectInListImpl#getAffectedEObject <em>Affected EObject</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReplaceNonRootEObjectInListImpl<T extends EObject> extends ReplaceEObjectImpl<T> implements ReplaceNonRootEObjectInList<T> {
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
	 * The cached value of the '{@link #getOldValue() <em>Old Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldValue()
	 * @generated
	 * @ordered
	 */
	protected T oldValue;

	/**
	 * The cached value of the '{@link #getNewValue() <em>New Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewValue()
	 * @generated
	 * @ordered
	 */
	protected T newValue;

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
	protected ReplaceNonRootEObjectInListImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ContainmentPackage.Literals.REPLACE_NON_ROOT_EOBJECT_IN_LIST;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE, oldOldValue, oldValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__INDEX, oldIndex, index));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T getNewValue() {
		return newValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewValue(T newNewValue) {
		T oldNewValue = newValue;
		newValue = newNewValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__NEW_VALUE, oldNewValue, newValue));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE, oldAffectedFeature, affectedFeature));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT, oldAffectedEObject, affectedEObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__INDEX:
				return getIndex();
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE:
				return getOldValue();
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__NEW_VALUE:
				return getNewValue();
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE:
				if (resolve) return getAffectedFeature();
				return basicGetAffectedFeature();
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT:
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
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__INDEX:
				setIndex((Integer)newValue);
				return;
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE:
				setOldValue((T)newValue);
				return;
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__NEW_VALUE:
				setNewValue((T)newValue);
				return;
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE:
				setAffectedFeature((EReference)newValue);
				return;
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT:
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
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__INDEX:
				setIndex(INDEX_EDEFAULT);
				return;
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE:
				setOldValue((T)null);
				return;
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__NEW_VALUE:
				setNewValue((T)null);
				return;
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE:
				setAffectedFeature((EReference)null);
				return;
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT:
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
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__INDEX:
				return index != INDEX_EDEFAULT;
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE:
				return oldValue != null;
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__NEW_VALUE:
				return newValue != null;
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE:
				return affectedFeature != null;
			case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT:
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
				case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__INDEX: return ListPackage.UPDATE_SINGLE_ELIST_ENTRY__INDEX;
				default: return -1;
			}
		}
		if (baseClass == ReplaceInEList.class) {
			switch (derivedFeatureID) {
				case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE: return ListPackage.REPLACE_IN_ELIST__OLD_VALUE;
				case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__NEW_VALUE: return ListPackage.REPLACE_IN_ELIST__NEW_VALUE;
				default: return -1;
			}
		}
		if (baseClass == EFeatureChange.class) {
			switch (derivedFeatureID) {
				case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE;
				case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT: return FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT;
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
				case ListPackage.UPDATE_SINGLE_ELIST_ENTRY__INDEX: return ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__INDEX;
				default: return -1;
			}
		}
		if (baseClass == ReplaceInEList.class) {
			switch (baseFeatureID) {
				case ListPackage.REPLACE_IN_ELIST__OLD_VALUE: return ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__OLD_VALUE;
				case ListPackage.REPLACE_IN_ELIST__NEW_VALUE: return ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__NEW_VALUE;
				default: return -1;
			}
		}
		if (baseClass == EFeatureChange.class) {
			switch (baseFeatureID) {
				case FeaturePackage.EFEATURE_CHANGE__AFFECTED_FEATURE: return ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_FEATURE;
				case FeaturePackage.EFEATURE_CHANGE__AFFECTED_EOBJECT: return ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST__AFFECTED_EOBJECT;
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
		result.append(" (index: ");
		result.append(index);
		result.append(", oldValue: ");
		result.append(oldValue);
		result.append(", newValue: ");
		result.append(newValue);
		result.append(')');
		return result.toString();
	}

} //ReplaceNonRootEObjectInListImpl
