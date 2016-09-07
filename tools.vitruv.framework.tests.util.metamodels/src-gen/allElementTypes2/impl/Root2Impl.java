/**
 */
package allElementTypes2.impl;

import allElementTypes2.AllElementTypes2Package;
import allElementTypes2.NonRoot2;
import allElementTypes2.NonRootObjectContainerHelper2;
import allElementTypes2.Root2;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root2</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link allElementTypes2.impl.Root2Impl#getSingleValuedEAttribute2 <em>Single Valued EAttribute2</em>}</li>
 *   <li>{@link allElementTypes2.impl.Root2Impl#getSingleValuedNonContainmentEReference2 <em>Single Valued Non Containment EReference2</em>}</li>
 *   <li>{@link allElementTypes2.impl.Root2Impl#getSingleValuedContainmentEReference2 <em>Single Valued Containment EReference2</em>}</li>
 *   <li>{@link allElementTypes2.impl.Root2Impl#getMultiValuedEAttribute2 <em>Multi Valued EAttribute2</em>}</li>
 *   <li>{@link allElementTypes2.impl.Root2Impl#getMultiValuedNonContainmentEReference2 <em>Multi Valued Non Containment EReference2</em>}</li>
 *   <li>{@link allElementTypes2.impl.Root2Impl#getMultiValuedContainmentEReference2 <em>Multi Valued Containment EReference2</em>}</li>
 *   <li>{@link allElementTypes2.impl.Root2Impl#getNonRootObjectContainerHelper <em>Non Root Object Container Helper</em>}</li>
 * </ul>
 *
 * @generated
 */
public class Root2Impl extends Identified2Impl implements Root2 {
	/**
	 * The default value of the '{@link #getSingleValuedEAttribute2() <em>Single Valued EAttribute2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedEAttribute2()
	 * @generated
	 * @ordered
	 */
	protected static final Integer SINGLE_VALUED_EATTRIBUTE2_EDEFAULT = new Integer(0);

	/**
	 * The cached value of the '{@link #getSingleValuedEAttribute2() <em>Single Valued EAttribute2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedEAttribute2()
	 * @generated
	 * @ordered
	 */
	protected Integer singleValuedEAttribute2 = SINGLE_VALUED_EATTRIBUTE2_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSingleValuedNonContainmentEReference2() <em>Single Valued Non Containment EReference2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedNonContainmentEReference2()
	 * @generated
	 * @ordered
	 */
	protected NonRoot2 singleValuedNonContainmentEReference2;

	/**
	 * The cached value of the '{@link #getSingleValuedContainmentEReference2() <em>Single Valued Containment EReference2</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleValuedContainmentEReference2()
	 * @generated
	 * @ordered
	 */
	protected NonRoot2 singleValuedContainmentEReference2;

	/**
	 * The cached value of the '{@link #getMultiValuedEAttribute2() <em>Multi Valued EAttribute2</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiValuedEAttribute2()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> multiValuedEAttribute2;

	/**
	 * The cached value of the '{@link #getMultiValuedNonContainmentEReference2() <em>Multi Valued Non Containment EReference2</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiValuedNonContainmentEReference2()
	 * @generated
	 * @ordered
	 */
	protected EList<NonRoot2> multiValuedNonContainmentEReference2;

	/**
	 * The cached value of the '{@link #getMultiValuedContainmentEReference2() <em>Multi Valued Containment EReference2</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiValuedContainmentEReference2()
	 * @generated
	 * @ordered
	 */
	protected EList<NonRoot2> multiValuedContainmentEReference2;

	/**
	 * The cached value of the '{@link #getNonRootObjectContainerHelper() <em>Non Root Object Container Helper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonRootObjectContainerHelper()
	 * @generated
	 * @ordered
	 */
	protected NonRootObjectContainerHelper2 nonRootObjectContainerHelper;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Root2Impl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AllElementTypes2Package.Literals.ROOT2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getSingleValuedEAttribute2() {
		return singleValuedEAttribute2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleValuedEAttribute2(Integer newSingleValuedEAttribute2) {
		Integer oldSingleValuedEAttribute2 = singleValuedEAttribute2;
		singleValuedEAttribute2 = newSingleValuedEAttribute2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypes2Package.ROOT2__SINGLE_VALUED_EATTRIBUTE2, oldSingleValuedEAttribute2, singleValuedEAttribute2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRoot2 getSingleValuedNonContainmentEReference2() {
		if (singleValuedNonContainmentEReference2 != null && singleValuedNonContainmentEReference2.eIsProxy()) {
			InternalEObject oldSingleValuedNonContainmentEReference2 = (InternalEObject)singleValuedNonContainmentEReference2;
			singleValuedNonContainmentEReference2 = (NonRoot2)eResolveProxy(oldSingleValuedNonContainmentEReference2);
			if (singleValuedNonContainmentEReference2 != oldSingleValuedNonContainmentEReference2) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AllElementTypes2Package.ROOT2__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE2, oldSingleValuedNonContainmentEReference2, singleValuedNonContainmentEReference2));
			}
		}
		return singleValuedNonContainmentEReference2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRoot2 basicGetSingleValuedNonContainmentEReference2() {
		return singleValuedNonContainmentEReference2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleValuedNonContainmentEReference2(NonRoot2 newSingleValuedNonContainmentEReference2) {
		NonRoot2 oldSingleValuedNonContainmentEReference2 = singleValuedNonContainmentEReference2;
		singleValuedNonContainmentEReference2 = newSingleValuedNonContainmentEReference2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypes2Package.ROOT2__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE2, oldSingleValuedNonContainmentEReference2, singleValuedNonContainmentEReference2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRoot2 getSingleValuedContainmentEReference2() {
		return singleValuedContainmentEReference2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleValuedContainmentEReference2(NonRoot2 newSingleValuedContainmentEReference2, NotificationChain msgs) {
		NonRoot2 oldSingleValuedContainmentEReference2 = singleValuedContainmentEReference2;
		singleValuedContainmentEReference2 = newSingleValuedContainmentEReference2;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AllElementTypes2Package.ROOT2__SINGLE_VALUED_CONTAINMENT_EREFERENCE2, oldSingleValuedContainmentEReference2, newSingleValuedContainmentEReference2);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleValuedContainmentEReference2(NonRoot2 newSingleValuedContainmentEReference2) {
		if (newSingleValuedContainmentEReference2 != singleValuedContainmentEReference2) {
			NotificationChain msgs = null;
			if (singleValuedContainmentEReference2 != null)
				msgs = ((InternalEObject)singleValuedContainmentEReference2).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AllElementTypes2Package.ROOT2__SINGLE_VALUED_CONTAINMENT_EREFERENCE2, null, msgs);
			if (newSingleValuedContainmentEReference2 != null)
				msgs = ((InternalEObject)newSingleValuedContainmentEReference2).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AllElementTypes2Package.ROOT2__SINGLE_VALUED_CONTAINMENT_EREFERENCE2, null, msgs);
			msgs = basicSetSingleValuedContainmentEReference2(newSingleValuedContainmentEReference2, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypes2Package.ROOT2__SINGLE_VALUED_CONTAINMENT_EREFERENCE2, newSingleValuedContainmentEReference2, newSingleValuedContainmentEReference2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Integer> getMultiValuedEAttribute2() {
		if (multiValuedEAttribute2 == null) {
			multiValuedEAttribute2 = new EDataTypeUniqueEList<Integer>(Integer.class, this, AllElementTypes2Package.ROOT2__MULTI_VALUED_EATTRIBUTE2);
		}
		return multiValuedEAttribute2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NonRoot2> getMultiValuedNonContainmentEReference2() {
		if (multiValuedNonContainmentEReference2 == null) {
			multiValuedNonContainmentEReference2 = new EObjectResolvingEList<NonRoot2>(NonRoot2.class, this, AllElementTypes2Package.ROOT2__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE2);
		}
		return multiValuedNonContainmentEReference2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NonRoot2> getMultiValuedContainmentEReference2() {
		if (multiValuedContainmentEReference2 == null) {
			multiValuedContainmentEReference2 = new EObjectContainmentEList<NonRoot2>(NonRoot2.class, this, AllElementTypes2Package.ROOT2__MULTI_VALUED_CONTAINMENT_EREFERENCE2);
		}
		return multiValuedContainmentEReference2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRootObjectContainerHelper2 getNonRootObjectContainerHelper() {
		return nonRootObjectContainerHelper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNonRootObjectContainerHelper(NonRootObjectContainerHelper2 newNonRootObjectContainerHelper, NotificationChain msgs) {
		NonRootObjectContainerHelper2 oldNonRootObjectContainerHelper = nonRootObjectContainerHelper;
		nonRootObjectContainerHelper = newNonRootObjectContainerHelper;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AllElementTypes2Package.ROOT2__NON_ROOT_OBJECT_CONTAINER_HELPER, oldNonRootObjectContainerHelper, newNonRootObjectContainerHelper);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNonRootObjectContainerHelper(NonRootObjectContainerHelper2 newNonRootObjectContainerHelper) {
		if (newNonRootObjectContainerHelper != nonRootObjectContainerHelper) {
			NotificationChain msgs = null;
			if (nonRootObjectContainerHelper != null)
				msgs = ((InternalEObject)nonRootObjectContainerHelper).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AllElementTypes2Package.ROOT2__NON_ROOT_OBJECT_CONTAINER_HELPER, null, msgs);
			if (newNonRootObjectContainerHelper != null)
				msgs = ((InternalEObject)newNonRootObjectContainerHelper).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AllElementTypes2Package.ROOT2__NON_ROOT_OBJECT_CONTAINER_HELPER, null, msgs);
			msgs = basicSetNonRootObjectContainerHelper(newNonRootObjectContainerHelper, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AllElementTypes2Package.ROOT2__NON_ROOT_OBJECT_CONTAINER_HELPER, newNonRootObjectContainerHelper, newNonRootObjectContainerHelper));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_CONTAINMENT_EREFERENCE2:
				return basicSetSingleValuedContainmentEReference2(null, msgs);
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_CONTAINMENT_EREFERENCE2:
				return ((InternalEList<?>)getMultiValuedContainmentEReference2()).basicRemove(otherEnd, msgs);
			case AllElementTypes2Package.ROOT2__NON_ROOT_OBJECT_CONTAINER_HELPER:
				return basicSetNonRootObjectContainerHelper(null, msgs);
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
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_EATTRIBUTE2:
				return getSingleValuedEAttribute2();
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE2:
				if (resolve) return getSingleValuedNonContainmentEReference2();
				return basicGetSingleValuedNonContainmentEReference2();
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_CONTAINMENT_EREFERENCE2:
				return getSingleValuedContainmentEReference2();
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_EATTRIBUTE2:
				return getMultiValuedEAttribute2();
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE2:
				return getMultiValuedNonContainmentEReference2();
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_CONTAINMENT_EREFERENCE2:
				return getMultiValuedContainmentEReference2();
			case AllElementTypes2Package.ROOT2__NON_ROOT_OBJECT_CONTAINER_HELPER:
				return getNonRootObjectContainerHelper();
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
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_EATTRIBUTE2:
				setSingleValuedEAttribute2((Integer)newValue);
				return;
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE2:
				setSingleValuedNonContainmentEReference2((NonRoot2)newValue);
				return;
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_CONTAINMENT_EREFERENCE2:
				setSingleValuedContainmentEReference2((NonRoot2)newValue);
				return;
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_EATTRIBUTE2:
				getMultiValuedEAttribute2().clear();
				getMultiValuedEAttribute2().addAll((Collection<? extends Integer>)newValue);
				return;
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE2:
				getMultiValuedNonContainmentEReference2().clear();
				getMultiValuedNonContainmentEReference2().addAll((Collection<? extends NonRoot2>)newValue);
				return;
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_CONTAINMENT_EREFERENCE2:
				getMultiValuedContainmentEReference2().clear();
				getMultiValuedContainmentEReference2().addAll((Collection<? extends NonRoot2>)newValue);
				return;
			case AllElementTypes2Package.ROOT2__NON_ROOT_OBJECT_CONTAINER_HELPER:
				setNonRootObjectContainerHelper((NonRootObjectContainerHelper2)newValue);
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
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_EATTRIBUTE2:
				setSingleValuedEAttribute2(SINGLE_VALUED_EATTRIBUTE2_EDEFAULT);
				return;
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE2:
				setSingleValuedNonContainmentEReference2((NonRoot2)null);
				return;
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_CONTAINMENT_EREFERENCE2:
				setSingleValuedContainmentEReference2((NonRoot2)null);
				return;
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_EATTRIBUTE2:
				getMultiValuedEAttribute2().clear();
				return;
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE2:
				getMultiValuedNonContainmentEReference2().clear();
				return;
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_CONTAINMENT_EREFERENCE2:
				getMultiValuedContainmentEReference2().clear();
				return;
			case AllElementTypes2Package.ROOT2__NON_ROOT_OBJECT_CONTAINER_HELPER:
				setNonRootObjectContainerHelper((NonRootObjectContainerHelper2)null);
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
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_EATTRIBUTE2:
				return SINGLE_VALUED_EATTRIBUTE2_EDEFAULT == null ? singleValuedEAttribute2 != null : !SINGLE_VALUED_EATTRIBUTE2_EDEFAULT.equals(singleValuedEAttribute2);
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE2:
				return singleValuedNonContainmentEReference2 != null;
			case AllElementTypes2Package.ROOT2__SINGLE_VALUED_CONTAINMENT_EREFERENCE2:
				return singleValuedContainmentEReference2 != null;
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_EATTRIBUTE2:
				return multiValuedEAttribute2 != null && !multiValuedEAttribute2.isEmpty();
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE2:
				return multiValuedNonContainmentEReference2 != null && !multiValuedNonContainmentEReference2.isEmpty();
			case AllElementTypes2Package.ROOT2__MULTI_VALUED_CONTAINMENT_EREFERENCE2:
				return multiValuedContainmentEReference2 != null && !multiValuedContainmentEReference2.isEmpty();
			case AllElementTypes2Package.ROOT2__NON_ROOT_OBJECT_CONTAINER_HELPER:
				return nonRootObjectContainerHelper != null;
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
		result.append(" (singleValuedEAttribute2: ");
		result.append(singleValuedEAttribute2);
		result.append(", multiValuedEAttribute2: ");
		result.append(multiValuedEAttribute2);
		result.append(')');
		return result.toString();
	}

} //Root2Impl
