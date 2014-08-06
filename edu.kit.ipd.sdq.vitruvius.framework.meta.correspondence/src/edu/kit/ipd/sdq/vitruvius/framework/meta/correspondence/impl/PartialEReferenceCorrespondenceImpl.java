/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Partial EReference Correspondence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl#getDependentCorrespondences <em>Dependent Correspondences</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl#getElementATUID <em>Element ATUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl#getElementBTUID <em>Element BTUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl#getType <em>Type</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl#getFeatureA <em>Feature A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl#getFeatureB <em>Feature B</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl#getValueATUID <em>Value ATUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl#getValueBTUID <em>Value BTUID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PartialEReferenceCorrespondenceImpl<TValue extends EObject> extends PartialEFeatureCorrespondenceImpl<TValue> implements PartialEReferenceCorrespondence<TValue> {
	/**
	 * The cached value of the '{@link #getDependentCorrespondences() <em>Dependent Correspondences</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependentCorrespondences()
	 * @generated
	 * @ordered
	 */
	protected EList<Correspondence> dependentCorrespondences;

	/**
	 * The default value of the '{@link #getElementATUID() <em>Element ATUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementATUID()
	 * @generated
	 * @ordered
	 */
	protected static final String ELEMENT_ATUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getElementATUID() <em>Element ATUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementATUID()
	 * @generated
	 * @ordered
	 */
	protected String elementATUID = ELEMENT_ATUID_EDEFAULT;

	/**
	 * The default value of the '{@link #getElementBTUID() <em>Element BTUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementBTUID()
	 * @generated
	 * @ordered
	 */
	protected static final String ELEMENT_BTUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getElementBTUID() <em>Element BTUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementBTUID()
	 * @generated
	 * @ordered
	 */
	protected String elementBTUID = ELEMENT_BTUID_EDEFAULT;

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
	protected EReference featureA;

	/**
	 * The cached value of the '{@link #getFeatureB() <em>Feature B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureB()
	 * @generated
	 * @ordered
	 */
	protected EReference featureB;

	/**
	 * The default value of the '{@link #getValueATUID() <em>Value ATUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueATUID()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_ATUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValueATUID() <em>Value ATUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueATUID()
	 * @generated
	 * @ordered
	 */
	protected String valueATUID = VALUE_ATUID_EDEFAULT;

	/**
	 * The default value of the '{@link #getValueBTUID() <em>Value BTUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueBTUID()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_BTUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValueBTUID() <em>Value BTUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueBTUID()
	 * @generated
	 * @ordered
	 */
	protected String valueBTUID = VALUE_BTUID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PartialEReferenceCorrespondenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorrespondencePackage.Literals.PARTIAL_EREFERENCE_CORRESPONDENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Correspondence> getDependentCorrespondences() {
		if (dependentCorrespondences == null) {
			dependentCorrespondences = new EObjectContainmentWithInverseEList<Correspondence>(Correspondence.class, this, CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES, CorrespondencePackage.CORRESPONDENCE__PARENT);
		}
		return dependentCorrespondences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Correspondence getParent() {
		if (eContainerFeatureID() != CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT) return null;
		return (Correspondence)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(Correspondence newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(Correspondence newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES, Correspondence.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getElementATUID() {
		return elementATUID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElementATUID(String newElementATUID) {
		String oldElementATUID = elementATUID;
		elementATUID = newElementATUID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_ATUID, oldElementATUID, elementATUID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getElementBTUID() {
		return elementBTUID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElementBTUID(String newElementBTUID) {
		String oldElementBTUID = elementBTUID;
		elementBTUID = newElementBTUID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_BTUID, oldElementBTUID, elementBTUID));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFeatureA() {
		if (featureA != null && featureA.eIsProxy()) {
			InternalEObject oldFeatureA = (InternalEObject)featureA;
			featureA = (EReference)eResolveProxy(oldFeatureA);
			if (featureA != oldFeatureA) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_A, oldFeatureA, featureA));
			}
		}
		return featureA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference basicGetFeatureA() {
		return featureA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureA(EReference newFeatureA) {
		EReference oldFeatureA = featureA;
		featureA = newFeatureA;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_A, oldFeatureA, featureA));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFeatureB() {
		if (featureB != null && featureB.eIsProxy()) {
			InternalEObject oldFeatureB = (InternalEObject)featureB;
			featureB = (EReference)eResolveProxy(oldFeatureB);
			if (featureB != oldFeatureB) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_B, oldFeatureB, featureB));
			}
		}
		return featureB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference basicGetFeatureB() {
		return featureB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureB(EReference newFeatureB) {
		EReference oldFeatureB = featureB;
		featureB = newFeatureB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_B, oldFeatureB, featureB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValueATUID() {
		return valueATUID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueATUID(String newValueATUID) {
		String oldValueATUID = valueATUID;
		valueATUID = newValueATUID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_ATUID, oldValueATUID, valueATUID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValueBTUID() {
		return valueBTUID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueBTUID(String newValueBTUID) {
		String oldValueBTUID = valueBTUID;
		valueBTUID = newValueBTUID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_BTUID, oldValueBTUID, valueBTUID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDependentCorrespondences()).basicAdd(otherEnd, msgs);
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((Correspondence)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
				return ((InternalEList<?>)getDependentCorrespondences()).basicRemove(otherEnd, msgs);
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT:
				return basicSetParent(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT:
				return eInternalContainer().eInverseRemove(this, CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES, Correspondence.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
				return getDependentCorrespondences();
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT:
				return getParent();
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_ATUID:
				return getElementATUID();
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_BTUID:
				return getElementBTUID();
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__TYPE:
				return getType();
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_A:
				if (resolve) return getFeatureA();
				return basicGetFeatureA();
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_B:
				if (resolve) return getFeatureB();
				return basicGetFeatureB();
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_ATUID:
				return getValueATUID();
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_BTUID:
				return getValueBTUID();
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
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
				getDependentCorrespondences().clear();
				getDependentCorrespondences().addAll((Collection<? extends Correspondence>)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT:
				setParent((Correspondence)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_ATUID:
				setElementATUID((String)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_BTUID:
				setElementBTUID((String)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__TYPE:
				setType((CorrespondenceType)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_A:
				setFeatureA((EReference)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_B:
				setFeatureB((EReference)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_ATUID:
				setValueATUID((String)newValue);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_BTUID:
				setValueBTUID((String)newValue);
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
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
				getDependentCorrespondences().clear();
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT:
				setParent((Correspondence)null);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_ATUID:
				setElementATUID(ELEMENT_ATUID_EDEFAULT);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_BTUID:
				setElementBTUID(ELEMENT_BTUID_EDEFAULT);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_A:
				setFeatureA((EReference)null);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_B:
				setFeatureB((EReference)null);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_ATUID:
				setValueATUID(VALUE_ATUID_EDEFAULT);
				return;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_BTUID:
				setValueBTUID(VALUE_BTUID_EDEFAULT);
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
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES:
				return dependentCorrespondences != null && !dependentCorrespondences.isEmpty();
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT:
				return getParent() != null;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_ATUID:
				return ELEMENT_ATUID_EDEFAULT == null ? elementATUID != null : !ELEMENT_ATUID_EDEFAULT.equals(elementATUID);
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_BTUID:
				return ELEMENT_BTUID_EDEFAULT == null ? elementBTUID != null : !ELEMENT_BTUID_EDEFAULT.equals(elementBTUID);
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__TYPE:
				return type != TYPE_EDEFAULT;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_A:
				return featureA != null;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_B:
				return featureB != null;
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_ATUID:
				return VALUE_ATUID_EDEFAULT == null ? valueATUID != null : !VALUE_ATUID_EDEFAULT.equals(valueATUID);
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_BTUID:
				return VALUE_BTUID_EDEFAULT == null ? valueBTUID != null : !VALUE_BTUID_EDEFAULT.equals(valueBTUID);
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
				case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES: return CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;
				case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT: return CorrespondencePackage.CORRESPONDENCE__PARENT;
				default: return -1;
			}
		}
		if (baseClass == SameTypeCorrespondence.class) {
			switch (derivedFeatureID) {
				case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_ATUID: return CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_ATUID;
				case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_BTUID: return CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_BTUID;
				default: return -1;
			}
		}
		if (baseClass == EFeatureCorrespondence.class) {
			switch (derivedFeatureID) {
				case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__TYPE: return CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE;
				case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_A: return CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_A;
				case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_B: return CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_B;
				default: return -1;
			}
		}
		if (baseClass == EReferenceCorrespondence.class) {
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
				case CorrespondencePackage.CORRESPONDENCE__DEPENDENT_CORRESPONDENCES: return CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;
				case CorrespondencePackage.CORRESPONDENCE__PARENT: return CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT;
				default: return -1;
			}
		}
		if (baseClass == SameTypeCorrespondence.class) {
			switch (baseFeatureID) {
				case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_ATUID: return CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_ATUID;
				case CorrespondencePackage.SAME_TYPE_CORRESPONDENCE__ELEMENT_BTUID: return CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_BTUID;
				default: return -1;
			}
		}
		if (baseClass == EFeatureCorrespondence.class) {
			switch (baseFeatureID) {
				case CorrespondencePackage.EFEATURE_CORRESPONDENCE__TYPE: return CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__TYPE;
				case CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_A: return CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_A;
				case CorrespondencePackage.EFEATURE_CORRESPONDENCE__FEATURE_B: return CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_B;
				default: return -1;
			}
		}
		if (baseClass == EReferenceCorrespondence.class) {
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
		result.append(", valueATUID: ");
		result.append(valueATUID);
		result.append(", valueBTUID: ");
		result.append(valueBTUID);
		result.append(')');
		return result.toString();
	}

} //PartialEReferenceCorrespondenceImpl
