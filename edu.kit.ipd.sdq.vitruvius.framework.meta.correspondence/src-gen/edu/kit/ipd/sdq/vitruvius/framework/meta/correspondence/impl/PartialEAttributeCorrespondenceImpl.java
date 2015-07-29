/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EAttributeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Partial EAttribute Correspondence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEAttributeCorrespondenceImpl#getElementATUID <em>Element ATUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEAttributeCorrespondenceImpl#getElementBTUID <em>Element BTUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEAttributeCorrespondenceImpl#getType <em>Type</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEAttributeCorrespondenceImpl#getFeatureA <em>Feature A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEAttributeCorrespondenceImpl#getFeatureB <em>Feature B</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEAttributeCorrespondenceImpl#getValueA <em>Value A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEAttributeCorrespondenceImpl#getValueB <em>Value B</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PartialEAttributeCorrespondenceImpl<TValue extends Object> extends PartialEFeatureCorrespondenceImpl<TValue> implements PartialEAttributeCorrespondence<TValue> {
	/**
	 * The default value of the '{@link #getElementATUID() <em>Element ATUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementATUID()
	 * @generated
	 * @ordered
	 */
	protected static final TUID ELEMENT_ATUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getElementATUID() <em>Element ATUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementATUID()
	 * @generated
	 * @ordered
	 */
	protected TUID elementATUID = ELEMENT_ATUID_EDEFAULT;

	/**
	 * The default value of the '{@link #getElementBTUID() <em>Element BTUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementBTUID()
	 * @generated
	 * @ordered
	 */
	protected static final TUID ELEMENT_BTUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getElementBTUID() <em>Element BTUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementBTUID()
	 * @generated
	 * @ordered
	 */
	protected TUID elementBTUID = ELEMENT_BTUID_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final CorrespondenceType TYPE_EDEFAULT = CorrespondenceType.IDENTITY;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected CorrespondenceType type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFeatureA() <em>Feature A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureA()
	 * @generated
	 * @ordered
	 */
	protected EAttribute featureA;

	/**
	 * The cached value of the '{@link #getFeatureB() <em>Feature B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureB()
	 * @generated
	 * @ordered
	 */
	protected EAttribute featureB;

	/**
	 * The cached value of the '{@link #getValueA() <em>Value A</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueA()
	 * @generated
	 * @ordered
	 */
	protected TValue valueA;

	/**
	 * The cached value of the '{@link #getValueB() <em>Value B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueB()
	 * @generated
	 * @ordered
	 */
	protected TValue valueB;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PartialEAttributeCorrespondenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorrespondencePackage.Literals.PARTIAL_EATTRIBUTE_CORRESPONDENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TUID getElementATUID() {
		return elementATUID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElementATUID(TUID newElementATUID) {
		TUID oldElementATUID = elementATUID;
		elementATUID = newElementATUID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_ATUID, oldElementATUID, elementATUID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TUID getElementBTUID() {
		return elementBTUID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElementBTUID(TUID newElementBTUID) {
		TUID oldElementBTUID = elementBTUID;
		elementBTUID = newElementBTUID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_BTUID, oldElementBTUID, elementBTUID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrespondenceType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(CorrespondenceType newType) {
		CorrespondenceType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeatureA() {
		if (featureA != null && featureA.eIsProxy()) {
			InternalEObject oldFeatureA = (InternalEObject)featureA;
			featureA = (EAttribute)eResolveProxy(oldFeatureA);
			if (featureA != oldFeatureA) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_A, oldFeatureA, featureA));
			}
		}
		return featureA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute basicGetFeatureA() {
		return featureA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureA(EAttribute newFeatureA) {
		EAttribute oldFeatureA = featureA;
		featureA = newFeatureA;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_A, oldFeatureA, featureA));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeatureB() {
		if (featureB != null && featureB.eIsProxy()) {
			InternalEObject oldFeatureB = (InternalEObject)featureB;
			featureB = (EAttribute)eResolveProxy(oldFeatureB);
			if (featureB != oldFeatureB) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_B, oldFeatureB, featureB));
			}
		}
		return featureB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute basicGetFeatureB() {
		return featureB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureB(EAttribute newFeatureB) {
		EAttribute oldFeatureB = featureB;
		featureB = newFeatureB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_B, oldFeatureB, featureB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TValue getValueA() {
		return valueA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueA(TValue newValueA) {
		TValue oldValueA = valueA;
		valueA = newValueA;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_A, oldValueA, valueA));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TValue getValueB() {
		return valueB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueB(TValue newValueB) {
		TValue oldValueB = valueB;
		valueB = newValueB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_B, oldValueB, valueB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_ATUID:
				return getElementATUID();
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_BTUID:
				return getElementBTUID();
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__TYPE:
				return getType();
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_A:
				if (resolve) return getFeatureA();
				return basicGetFeatureA();
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_B:
				if (resolve) return getFeatureB();
				return basicGetFeatureB();
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_A:
				return getValueA();
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_B:
				return getValueB();
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
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_ATUID:
				setElementATUID((TUID)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_BTUID:
				setElementBTUID((TUID)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__TYPE:
				setType((CorrespondenceType)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_A:
				setFeatureA((EAttribute)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_B:
				setFeatureB((EAttribute)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_A:
				setValueA((TValue)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_B:
				setValueB((TValue)newValue);
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
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_ATUID:
				setElementATUID(ELEMENT_ATUID_EDEFAULT);
				return;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_BTUID:
				setElementBTUID(ELEMENT_BTUID_EDEFAULT);
				return;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_A:
				setFeatureA((EAttribute)null);
				return;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_B:
				setFeatureB((EAttribute)null);
				return;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_A:
				setValueA((TValue)null);
				return;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_B:
				setValueB((TValue)null);
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
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_ATUID:
				return ELEMENT_ATUID_EDEFAULT == null ? elementATUID != null : !ELEMENT_ATUID_EDEFAULT.equals(elementATUID);
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_BTUID:
				return ELEMENT_BTUID_EDEFAULT == null ? elementBTUID != null : !ELEMENT_BTUID_EDEFAULT.equals(elementBTUID);
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__TYPE:
				return type != TYPE_EDEFAULT;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_A:
				return featureA != null;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_B:
				return featureB != null;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_A:
				return valueA != null;
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_B:
				return valueB != null;
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
		if (baseClass == Correspondence.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == SameTypeCorrespondence.class) {
			switch (derivedFeatureID) {
				case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_ATUID: return CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_ATUID;
				case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_BTUID: return CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_BTUID;
				default: return -1;
			}
		}
		if (baseClass == EFeatureCorrespondence.class) {
			switch (derivedFeatureID) {
				case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__TYPE: return CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE;
				case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_A: return CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_A;
				case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_B: return CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_B;
				default: return -1;
			}
		}
		if (baseClass == EAttributeCorrespondence.class) {
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
		if (baseClass == Correspondence.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == SameTypeCorrespondence.class) {
			switch (baseFeatureID) {
				case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_ATUID: return CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_ATUID;
				case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_BTUID: return CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_BTUID;
				default: return -1;
			}
		}
		if (baseClass == EFeatureCorrespondence.class) {
			switch (baseFeatureID) {
				case CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE: return CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__TYPE;
				case CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_A: return CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_A;
				case CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_B: return CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_B;
				default: return -1;
			}
		}
		if (baseClass == EAttributeCorrespondence.class) {
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
		result.append(" (elementATUID: ");
		result.append(elementATUID);
		result.append(", elementBTUID: ");
		result.append(elementBTUID);
		result.append(", type: ");
		result.append(type);
		result.append(", valueA: ");
		result.append(valueA);
		result.append(", valueB: ");
		result.append(valueB);
		result.append(')');
		return result.toString();
	}

} //PartialEAttributeCorrespondenceImpl
